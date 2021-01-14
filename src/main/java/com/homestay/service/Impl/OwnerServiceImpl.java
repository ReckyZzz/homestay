package com.homestay.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.homestay.mapper.*;
import com.homestay.pojo.*;
import com.homestay.response.CommonResponse;
import com.homestay.service.OwnerService;
import com.homestay.vo.CommentVO;
import com.homestay.vo.OrderVO;
import com.homestay.vo.RoomVO2;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    public CommonResponse<Room> addRoom(Integer ownerId,String roomName,String description
            , String location, Double price){
        Room room = new Room();
        room.setRoomOwner(ownerId);room.setRoomName(roomName);room.setDescription(description);
        room.setLocation(location);room.setRoomPrice(price);
        roomMapper.insertRoom(room);
        return new CommonResponse<>(0,"插入成功",room);
    }

    @Override
    public CommonResponse<Object> getIn(Integer orderId){
        Order order = orderMapper.getOrderByOrderId(orderId);
        Date beginDate = order.getReserveDate();
        Calendar ca = Calendar.getInstance();ca.setTime(beginDate);ca.add(Calendar.DATE,order.getLastDays());
        Date endDate = ca.getTime();
        if(order.getLiveDate() == null && new Date().after(beginDate) && new Date().before(endDate)) {
            order.setLiveDate(new Date());
            orderMapper.updateOrder(order);
            return new CommonResponse<>(0,"修改成功",null);
        }
        return new CommonResponse<>(1,"修改失败",null);
    }

    @Override
    public CommonResponse<Object> disableRoom(Integer roomId){
        roomMapper.disableRoom(roomId);
        return new CommonResponse<>(0,"修改成功",null);
    }
    public CommonResponse<Object> enableRoom(Integer roomId){
        roomMapper.enableRoom(roomId);
        return new CommonResponse<>(0,"修改成功",null);
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
