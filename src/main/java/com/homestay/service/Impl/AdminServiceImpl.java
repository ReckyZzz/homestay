package com.homestay.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.homestay.mapper.OrderMapper;
import com.homestay.mapper.RoomMapper;
import com.homestay.mapper.UserMapper;
import com.homestay.pojo.Order;
import com.homestay.pojo.Room;
import com.homestay.pojo.User;
import com.homestay.response.CommonResponse;
import com.homestay.service.AdminService;
import com.homestay.service.OrderService;
import com.homestay.service.RoomService;
import com.homestay.util.EncryptUtil;
import com.homestay.util.SessionUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Resource
    UserMapper userMapper;
    @Resource
    RoomMapper roomMapper;
    @Resource
    RoomService roomService;
    @Resource
    OrderMapper orderMapper;
    @Resource
    OrderService orderService;

    @Override
    public CommonResponse<User> login(User admin) {
        HttpSession session = SessionUtil.getSession();
        SessionUtil.removeSession();
        User userInfo = userMapper.getUserByUsername(admin.getUserName());
        if(userInfo != null){
            if(EncryptUtil.check(admin.getUserPwd(),userInfo.getUserPwd())) {
                if (session != null) {
                    session.setAttribute("admin", admin);
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
    public CommonResponse<PageInfo<User>> getUsers(Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<User> users = userMapper.list();
        PageInfo<User> pageInfo = new PageInfo<>(users);
        return new CommonResponse<>(0,"查询成功",pageInfo);
    }

    @Override
    public CommonResponse<PageInfo<Room>> getRooms(Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Room> rooms = roomMapper.list();
        PageInfo<Room> pageInfo = new PageInfo<>(rooms);
        return new CommonResponse<>(0,"查询成功",pageInfo);
    }

    @Override
    public CommonResponse<PageInfo<Order>> getOrders(Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Order> orders = orderMapper.list();
        PageInfo<Order> pageInfo = new PageInfo<>(orders);
        return new CommonResponse<>(0,"查询成功",pageInfo);
    }

    @Override
    public User updateUser(User user){
        if(user.getUserId() != null){
            User oldUser = userMapper.getUserById(user.getUserId());
            if(oldUser == null){
                return null;
            }
            if(user.getUserName() != null && user.getUserName() != oldUser.getUserName()){
                oldUser.setUserName(user.getUserName());
            }
            if(user.getUserPwd() != null){
                oldUser.setUserPwd(EncryptUtil.getEncodedString(user.getUserPwd()));
            }
            if(user.getGender() != null && user.getGender() != oldUser.getGender()){
                oldUser.setGender(user.getGender());
            }
            if(user.getUserType() != null && user.getUserType() != oldUser.getUserType()){
                oldUser.setUserType(user.getUserType());
            }
            userMapper.updateUser(oldUser);
            return oldUser;
        }
        return null;
    }

    @Override
    public Room updateRoom(Room room){
        if(room.getRoomId() != null){
            Room oldRoom = roomMapper.getRoomByRoomId(room.getRoomId());
            if(oldRoom == null){
                return null;
            }
            if(room.getRoomOwner() != null && room.getRoomOwner() != oldRoom.getRoomOwner()
                    && roomService.isValidRoom(room)){
                oldRoom.setRoomOwner(room.getRoomOwner());
            }
            if(room.getRoomName() != null && room.getRoomName() != oldRoom.getRoomName()){
                oldRoom.setRoomName(room.getRoomName());
            }
            if(room.getDescription() != null && room.getDescription() != oldRoom.getDescription()){
                oldRoom.setDescription(room.getDescription());
            }
            if(room.getRoomPrice() != 0 && room.getRoomPrice() != oldRoom.getRoomPrice()){
                oldRoom.setRoomPrice(room.getRoomPrice());
            }
            if(room.getIsAvailable() != null && room.getIsAvailable() != oldRoom.getIsAvailable()){
                oldRoom.setIsAvailable(room.getIsAvailable());
            }
            roomMapper.updateRoom(oldRoom);
            return oldRoom;
        }
        return null;
    }

    @Override
    public Order updateOrder(Order order){
        if(order.getOrderId() != null){
            Order oldOrder = orderMapper.getOrderByOrderId(order.getOrderId());
            if(oldOrder == null){
                return null;
            }
            if(order.getUserId() != null && order.getUserId() != oldOrder.getUserId()){
                oldOrder.setUserId(oldOrder.getUserId());
            }
            if(order.getOwnerId() != null && order.getOrderId() != oldOrder.getOrderId()
                    && orderService.isValidOrder(order)){
                oldOrder.setOwnerId(order.getOwnerId());
            }
            if(order.getRoomId() != null && order.getRoomId() != order.getRoomId()){
                oldOrder.setRoomId(order.getRoomId());
            }
            if(order.getLastDays() != null && order.getLastDays() != oldOrder.getLastDays()){
                oldOrder.setLastDays(order.getLastDays());
            }
            if(order.getCreateDate() != null && order.getCreateDate() != oldOrder.getCreateDate()){
                oldOrder.setCreateDate(order.getCreateDate());
            }
            if(order.getLiveDate() != null && order.getLiveDate() != oldOrder.getLiveDate()){
                oldOrder.setLiveDate(order.getLiveDate());
            }
            if(order.getMoney() != 0 && order.getMoney() != oldOrder.getMoney()){
                oldOrder.setMoney(order.getMoney());
            }
            orderMapper.updateOrder(oldOrder);
            return oldOrder;
        }
        return null;
    }

    @Override
    public User addUser(User user){
        if(userMapper.getUserById(user.getUserId()) != null)
            return null;
        user.setUserPwd(EncryptUtil.getEncodedString(user.getUserPwd()));
        userMapper.insertUser(user);
        return user;
    }

    @Override
    public Room addRoom(Room room){
        if(roomMapper.getRoomByRoomId(room.getRoomId()) != null)
            return null;
        roomMapper.insertRoom(room);
        return room;
    }

    @Override
    public Order addOrder(Order order){
        if(orderMapper.getOrderByOrderId(order.getOrderId()) != null)
            return null;
        orderMapper.insertOrder(order);
        return order;
    }

    @Override
    public void deleteUser(List<Integer> ids){
        for(int id:ids){
            userMapper.deleteUserById(id);
        }
    }

    @Override
    public void deleteRoom(List<Integer> ids){
        for(int id:ids){
            roomMapper.deleteRoomById(id);
        }
    }

    @Override
    public void deleteOrder(List<Integer> ids){
        for(int id:ids){
            orderMapper.deleteOrderById(id);
        }
    }

    @Override
    public void resetUser(List<Integer> ids){
        for(int id:ids){
            userMapper.resetUserById(id);
        }
    }

    @Override
    public void resetRoom(List<Integer> ids){
        for(int id:ids){
            roomMapper.resetRoomById(id);
        }
    }

    @Override
    public void resetOrder(List<Integer> ids){
        for(int id:ids){
            orderMapper.resetOrderById(id);
        }
    }
}
