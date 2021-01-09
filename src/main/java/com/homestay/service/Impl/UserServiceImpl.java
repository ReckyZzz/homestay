package com.homestay.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.homestay.mapper.*;
import com.homestay.pojo.*;
import com.homestay.response.CommonResponse;
import com.homestay.service.UserService;
import com.homestay.util.EncryptUtil;
import com.homestay.util.SessionUtil;
import com.homestay.vo.OrderVO;
import com.homestay.vo.RoomVO;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private RoomMapper roomMapper;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private CommentMapper commentMapper;
    @Resource
    private RoomCollectionMapper roomCollectionMapper;

    @Override
    public CommonResponse<User> login(User user) {
        HttpSession session = SessionUtil.getSession();
        SessionUtil.removeSession();
        User userInfo = userMapper.getUserByUsername(user.getUserName());
        if(userInfo != null){
            if(EncryptUtil.check(user.getUserPwd(),userInfo.getUserPwd())) {
                if (session != null) {
                    session.setAttribute("user", userInfo);
                }
                return new CommonResponse<>(0, "登录成功", userInfo);
            }
            else{
                return new CommonResponse<>(1,"密码错误",null);
            }
        }
        else{
            return new CommonResponse<>(2,"用户名错误",null);
        }
    }

    @Override
    public CommonResponse<User> register(User user){
        if(user.getUserName()!=null && user.getUserPwd()!=null) {
            User databaseUser = userMapper.getUserByUsername(user.getUserName());
            if(databaseUser == null){
                user.setUserPwd(EncryptUtil.getEncodedString(user.getUserPwd()));
                userMapper.insertUser(user);
                return new CommonResponse<>(0,"注册成功",user);
            }
            else{
                return new CommonResponse<>(1,"用户已存在",null);
            }
        }
        else {
            return new CommonResponse<>(2,"用户名或密码为空",null);
        }
    }

    @Override
    public boolean resetPassword(User user,String oldPassword,String newPassword){
        oldPassword = EncryptUtil.getEncodedString(oldPassword);
        newPassword = EncryptUtil.getEncodedString(newPassword);
        if(oldPassword.equals(user.getUserPwd())){
            user.setUserPwd(newPassword);
            userMapper.updateUser(user);
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public boolean isOwner(int ownerId){
        User owner = userMapper.getUserById(ownerId);
        return owner.getUserType() == 1;
    }

    @Override
    public  List<Room> getRooms(){
        return roomMapper.list();
    }

    @Override
    public List<Room> searchRooms(String name){
        return roomMapper.getRoomByName(name);
    }

    @Override
    public List<Comment> getCommentsByRoom(Integer roomId){
        return commentMapper.getCommentByRoom(roomId);
    }

    @Override
    public CommonResponse<Order> reserve(Integer userId,Integer roomId,Integer days,Date reserveDate){
        boolean usable = true;
        Room room = roomMapper.getRoomByRoomId(roomId);
        List<Order> roomOrders = orderMapper.getOrderByRoom(roomId);
        Order order = new Order();
        //房间在预定时间是否可用
        for(Order o:roomOrders){
            Date beginDate = o.getReserveDate();
            Calendar ca = Calendar.getInstance();ca.setTime(beginDate);ca.add(Calendar.DATE,o.getLastDays());
            Date endDate = ca.getTime();
            if(reserveDate.after(beginDate) && reserveDate.before(endDate)){
                usable = false;
                break;
            }
        }
        //房间是否可用
        if(room.getIsAvailable() == 1 && usable){
            order.setUserId(userId);order.setOwnerId(room.getRoomOwner());order.setRoomId(roomId);
            order.setLastDays(days);order.setCreateDate(new Date());order.setReserveDate(reserveDate);
            order.setMoney(room.getRoomPrice()*days);
            orderMapper.insertOrder(order);
            return new CommonResponse<>(0,"预订成功！",order);
        }
        else if(room.getIsAvailable() == 0){
            return new CommonResponse<>(1,"房间已不可用！",null);
        }
        else{
            return new CommonResponse<>(2,"房间已被其他用户预订！",null);
        }
    }

    @Override
    public CommonResponse<PageInfo<OrderVO>> getOrders(Integer userId, Integer pageNum, Integer pageSize){
        List<Integer> commentables = new ArrayList<>(pageSize);
        PageHelper.startPage(pageNum,pageSize);
        List<Order> orders = orderMapper.getOrderByUser(userId);
        List<Comment> comments = commentMapper.getCommentByUser(userId);
        List<OrderVO> orderVOS = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        //订单是否可评价
        for(int i = 0;i < orders.size();i++){
            boolean flag = false;
            Order o = orders.get(i);
            Date beginDate = o.getReserveDate();
            Calendar ca = Calendar.getInstance();ca.setTime(beginDate);ca.add(Calendar.DATE,o.getLastDays());
            Date endDate = ca.getTime();
            for(int j = 0;j < comments.size();j++){
                Comment c = comments.get(j);
                if(c.getUserId() == userId && c.getRoomId() == o.getRoomId() && c.getCreateDate().after(endDate)) {
                    commentables.add(2);//已评论
                    flag = true;
                    break;
                }
            }
            if(new Date().after(endDate) && !flag){
                commentables.add(1);//待评论
            }
            else if(new Date().after(beginDate) && new Date().before(endDate) && !flag){
                commentables.add(0);//入住中
            }
            else if(new Date().before(beginDate) && !flag){
                commentables.add(3);//待入住
            }
        }
        for(int i=0;i < orders.size();i++){
            OrderVO vo = new OrderVO();
            Order o = orders.get(i);
            vo.setOrderId(o.getOrderId());
            vo.setUserName(userMapper.getUserById(o.getUserId()).getUserName());
            vo.setOwnerName(userMapper.getUserById(o.getOwnerId()).getUserName());
            vo.setRoomName(roomMapper.getRoomByRoomId(o.getRoomId()).getRoomName());
            vo.setCreateDate(format.format(o.getCreateDate()));
            vo.setReserveDate(format.format(o.getReserveDate()));
            if(commentables.get(i) == 1){
                vo.setComment(1);//待评论
                vo.setLiveDate(format.format(o.getLiveDate()));
            }
            else if(commentables.get(i) == 0){
                vo.setComment(0);//入住中
                vo.setLiveDate(format.format(o.getLiveDate()));
            }
            else if(commentables.get(i) == 2){
                vo.setComment(2);//已评论
                vo.setLiveDate(format.format(o.getLiveDate()));
            }
            else if(commentables.get(i) == 3){
                vo.setComment((3));//待入住
                vo.setLiveDate(null);
            }
            vo.setMoney(o.getMoney());
            orderVOS.add(vo);
        }
        PageInfo<OrderVO> pageInfo = new PageInfo<>(orderVOS);
        return new CommonResponse<>(0,"查询成功",pageInfo);
    }

    @Override
    public CommonResponse<Object> collectRoom(User user,Integer roomId){
        boolean flag = false;
        List<RoomCollection> collections = roomCollectionMapper.getAllCollectionByUser(user.getUserId());
        Room room = roomMapper.getRoomByRoomId(roomId);
        //要收藏的房间已在数据库中
        for(RoomCollection c:collections){
            if(c.getRoomId() == room.getRoomId()) {
                flag = true;
                break;
            }
        }
        if(flag) {
            roomCollectionMapper.resetCollectionByUserRoom(user.getUserId(),room.getRoomId());
        }
        else{
            roomCollectionMapper.insertCollection(user.getUserId(), room.getRoomId());
        }
        return new CommonResponse<>(0,"收藏成功",null);
    }

    @Override
    public CommonResponse<Object> cancelCollectRoom(User user,Room room){
        boolean flag = false;
        List<RoomCollection> collections = roomCollectionMapper.getAllCollectionByUser(user.getUserId());
        //要取消收藏的房间是否在数据库中
        for(RoomCollection c:collections){
            if(c.getRoomId() == room.getRoomId()) {
                flag = true;
                break;
            }
        }
        if(flag) {
            roomCollectionMapper.deleteCollectionByUserRoom(user.getUserId(),room.getRoomId());
        }
        return new CommonResponse<>(0,"取消收藏成功",null);
    }

    @Override
    public List<RoomCollection> getCollections(Integer userId){
        return roomCollectionMapper.getCollectionByUser(userId);
    }

    @Override
    public Room getRoomById(Integer id){
        return roomMapper.getRoomByRoomId(id);
    }

    @Override
    public User getUserById(Integer id){
        return userMapper.getUserById(id);
    }

    @Override
    public Integer getRoomNumByUser(Integer id){
        List<Room> rooms = roomMapper.getRoomByOwner(id);
        return rooms.size();
    }

    @Override
    public CommonResponse<Comment> commentRoom(Integer orderId,Integer stars,String content){
        Comment comment = new Comment();
        Order order = orderMapper.getOrderByOrderId(orderId);
        comment.setUserId(order.getUserId());comment.setRoomId(order.getRoomId());
        comment.setRateStars(stars);comment.setContent(content);
        Date beginDate = order.getReserveDate();
        Calendar ca = Calendar.getInstance();ca.setTime(beginDate);ca.add(Calendar.DATE,order.getLastDays());
        Date endDate = ca.getTime();
        //使用完民宿之后才能评论
        if(new Date().after(endDate)){
            commentMapper.insertComment(comment);
            return new CommonResponse<>(0,"评论成功",comment);
        }
        return new CommonResponse<>(1,"评论失败，订单尚未完成！",null);
    }

    @Override
    public CommonResponse<Object> checkRoomInfo(Integer roomId){
        Room room = getRoomById(roomId);
        List<Comment> comments = getCommentsByRoom(roomId);
        User owner = getUserById(room.getRoomOwner());
        RoomVO roomVO = new RoomVO();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        roomVO.setOwnerName(owner.getUserName());
        roomVO.setOwnerDescription(room.getDescription());
        roomVO.setRoomNum(getRoomNumByUser(owner.getUserId()));
        roomVO.setPrice(room.getRoomPrice());
        List<String> userNames = new ArrayList<>();
        List<Integer> stars = new ArrayList<>();
        List<String> content = new ArrayList<>();
        List<String> dates = new ArrayList<>();
        for(Comment c:comments){
            User user = getUserById(c.getUserId());
            userNames.add(user.getUserName());
            stars.add(c.getRateStars());
            content.add(c.getContent());
            dates.add(format.format(c.getCreateDate()));
        }
        roomVO.setUserNames(userNames);
        roomVO.setStars(stars);
        roomVO.setContent(content);
        roomVO.setDates(dates);
        roomVO.setUrl("../img/" + room.getRoomId() + ".jpg");
        roomVO.setRoomName(room.getRoomName());
        return new CommonResponse<>(0,"查询成功",roomVO);
    }
}
