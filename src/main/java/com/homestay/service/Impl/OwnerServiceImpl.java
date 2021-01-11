package com.homestay.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.homestay.mapper.*;
import com.homestay.pojo.*;
import com.homestay.response.CommonResponse;
import com.homestay.service.OwnerService;
import com.homestay.util.EncryptUtil;
import com.homestay.util.SessionUtil;
import com.homestay.vo.CommentVO;
import com.homestay.vo.OrderVO;
import com.homestay.vo.RoomVO2;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class OwnerServiceImpl implements OwnerService {
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
    @Resource
    private ImageMapper imageMapper;

    @Override
    public  List<RoomVO2> getRooms(Integer ownerId){
        List<Room> rooms = roomMapper.getRoomByOwner(ownerId);
        List<RoomVO2> roomVO2s = new ArrayList<>();
        for(int i=0;i < rooms.size();i++){
            Room r = rooms.get(i);
            RoomVO2 vo = new RoomVO2();
            vo.setRoomName(r.getRoomName());
            User owner = userMapper.getUserById(r.getRoomOwner());
            vo.setOwnerName(owner.getUserName());
            vo.setDescription(r.getDescription());
            vo.setRoomPrice(r.getRoomPrice());
            vo.setAvailable(r.getIsAvailable());
            vo.setRoomId(r.getRoomId());
            vo.setLocation(r.getLocation());
            roomVO2s.add(vo);
        }
        return roomVO2s;
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
    public CommonResponse<PageInfo<OrderVO>> getOrders(Integer ownerId, Integer pageNum, Integer pageSize){
        List<Integer> inable = new ArrayList<>(pageSize);
        PageHelper.startPage(pageNum,pageSize);
        List<Order> orders = orderMapper.getOrderByOwner(ownerId);
        List<OrderVO> orderVOS = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //用户是否入住
        for(Order o:orders){
            Date beginDate = o.getReserveDate();
            Calendar ca = Calendar.getInstance();ca.setTime(beginDate);ca.add(Calendar.DATE,o.getLastDays());
            Date endDate = ca.getTime();
            if(o.getLiveDate() == null){
                inable.add(1);//待入住
            }
            else if(o.getLiveDate() != null && new Date().after(beginDate) && new Date().before(endDate)){
                inable.add(0);//入住中
            }
            else{
                inable.add(2);//已完成
            }
        }
        for(int i=0;i < orders.size();i++){
            OrderVO vo = new OrderVO();
            Order o = orders.get(i);
            vo.setOrderId(o.getOrderId());
            vo.setUserName(userMapper.getUserById(o.getUserId()).getUserName());
            vo.setOwnerName(userMapper.getUserById(o.getOwnerId()).getUserName());
            vo.setRoomName(roomMapper.getRoomByRoomId(o.getRoomId()).getRoomName());
            vo.setCreateDate(format2.format(o.getCreateDate()));
            vo.setReserveDate(format.format(o.getReserveDate()));
            vo.setDays(o.getLastDays());
            if(o.getLiveDate() == null){
                vo.setLiveDate(null);
            }
            else{
                vo.setLiveDate(format2.format(o.getLiveDate()));
            }
            if(inable.get(i) == 1){
                vo.setComment(1);//待入住
            }
            else if(inable.get(i) == 0){
                vo.setComment(0);//入住中
            }
            else{
                vo.setComment(2);//已完成
            }
            vo.setMoney(o.getMoney());
            vo.setRoomId(o.getRoomId());
            orderVOS.add(vo);
        }
        PageInfo<OrderVO> pageInfo = new PageInfo<>(orderVOS);
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

    @Override
    public CommonResponse<List<CommentVO>> getComments(Integer ownerId){
        List<Comment> comments = commentMapper.list();
        List<CommentVO> commentVOS = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        for(Comment c:comments){
            User user = userMapper.getUserById(c.getUserId());
            Room room = roomMapper.getRoomByRoomId(c.getRoomId());
            CommentVO vo = new CommentVO();
            vo.setCommentId(c.getCommentId());
            vo.setCommentDate(format.format(c.getCreateDate()));
            vo.setUserName(user.getUserName());
            vo.setRoomName(room.getRoomName());
            vo.setStars(c.getRateStars());
            vo.setContent(c.getContent());
            commentVOS.add(vo);
        }
        return new CommonResponse<>(0,"查询成功",commentVOS);
    }

    @Override
    public int addImage(Image image){
        return imageMapper.insertImage(image);
    }
}
