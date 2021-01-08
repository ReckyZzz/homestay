package com.homestay.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.homestay.mapper.*;
import com.homestay.pojo.*;
import com.homestay.response.CommonResponse;
import com.homestay.service.UserService;
import com.homestay.util.EncryptUtil;
import com.homestay.util.SessionUtil;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
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
                    session.setAttribute("user", user);
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
    public List<Comment> getCommentsByRoom(Room room){
        return commentMapper.getCommentByRoom(room.getRoomId());
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
    public CommonResponse<PageInfo<Order>> getOrders(Integer userId,Integer pageNum,Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Order> orders = orderMapper.getOrderByUser(userId);
        PageInfo<Order> pageInfo = new PageInfo<>(orders);
        return new CommonResponse<>(0,"查询成功",pageInfo);
    }

    @Override
    public CommonResponse<Object> collectRoom(User user,Room room){
        boolean flag = false;
        List<RoomCollection> collections = roomCollectionMapper.getAllCollectionByUser(user.getUserId());
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
    public List<RoomCollection> getCollections(User user){
        return roomCollectionMapper.getCollectionByUser(user.getUserId());
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
    public CommonResponse<Comment> commentRoom(Order order,Integer stars,String content){
        Comment comment = new Comment();
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
}
