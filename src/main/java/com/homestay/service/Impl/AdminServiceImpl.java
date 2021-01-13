package com.homestay.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.homestay.mapper.CommentMapper;
import com.homestay.mapper.OrderMapper;
import com.homestay.mapper.RoomMapper;
import com.homestay.mapper.UserMapper;
import com.homestay.pojo.Comment;
import com.homestay.pojo.Order;
import com.homestay.pojo.Room;
import com.homestay.pojo.User;
import com.homestay.response.CommonResponse;
import com.homestay.service.AdminService;
import com.homestay.service.OwnerService;
import com.homestay.service.UserService;
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
    UserService userService;
    @Resource
    RoomMapper roomMapper;
    @Resource
    OwnerService ownerService;
    @Resource
    OrderMapper orderMapper;
    @Resource
    CommentMapper commentMapper;

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
        List<User> users = userMapper.getAllUsers();
        PageInfo<User> pageInfo = new PageInfo<>(users);
        return new CommonResponse<>(0,"查询成功",pageInfo);
    }

    @Override
    public CommonResponse<PageInfo<Room>> getRooms(Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Room> rooms = roomMapper.getAllRooms();
        PageInfo<Room> pageInfo = new PageInfo<>(rooms);
        return new CommonResponse<>(0,"查询成功",pageInfo);
    }

    @Override
    public CommonResponse<PageInfo<Order>> getOrders(Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Order> orders = orderMapper.getAllOrders();
        PageInfo<Order> pageInfo = new PageInfo<>(orders);
        return new CommonResponse<>(0,"查询成功",pageInfo);
    }

    @Override
    public CommonResponse<PageInfo<Comment>> getComments(Integer pageNum,Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Comment> comments = commentMapper.getAllComments();
        PageInfo<Comment> pageInfo = new PageInfo<>(comments);
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
                    && userService.isOwner(room.getRoomOwner())){
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
                oldOrder.setUserId(order.getUserId());
            }
            if(order.getOwnerId() != null && order.getOwnerId() != oldOrder.getOwnerId()
                    && userService.isOwner(order.getOwnerId())){
                oldOrder.setOwnerId(order.getOwnerId());
            }
            if(order.getRoomId() != null && order.getRoomId() != oldOrder.getRoomId()){
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

    public Comment updateComment(Comment comment){
        if(comment.getCommentId() != null){
            Comment oldComment = commentMapper.getCommentById(comment.getCommentId());
            if(oldComment == null){
                return null;
            }
            if(comment.getUserId() != null && comment.getUserId() != oldComment.getUserId()){
                oldComment.setUserId(comment.getUserId());
            }
            if(comment.getRoomId() != null && comment.getRoomId() != oldComment.getRoomId()){
                oldComment.setRoomId(comment.getRoomId());
            }
            if(comment.getRateStars() != null && comment.getRateStars() != oldComment.getRateStars()){
                oldComment.setRateStars(comment.getRateStars());
            }
            if(comment.getContent() != null && comment.getContent() != oldComment.getContent()){
                oldComment.setContent(comment.getContent());
            }
            commentMapper.updateComment(oldComment);
            return oldComment;
        }
        return null;
    }

    @Override
    public User addUser(User user){
        if(user.getUserId() != null)
            return null;
        if(user.getUserName() != null && user.getUserPwd() != null) {
            user.setUserPwd(EncryptUtil.getEncodedString(user.getUserPwd()));
            userMapper.insertUser(user);
            return user;
        }
        return null;
    }

    @Override
    public Room addRoom(Room room){
        if(room.getRoomId() != null || room.getRoomOwner() == null)
            return null;
        if(userService.isOwner(room.getRoomOwner()) && room.getRoomName() != null && room.getRoomPrice() != 0) {
            roomMapper.insertRoom(room);
            return room;
        }
        return null;
    }

    @Override
    public Order addOrder(Order order){
        if(order.getOrderId() != null || order.getOwnerId() == null)
            return null;
        if(order.getUserId() != null && userService.isOwner(order.getOwnerId()) && order.getRoomId() != null &&
                order.getLastDays() != null && order.getCreateDate() != null && order.getLiveDate() != null &&
                order.getMoney() != 0) {
            orderMapper.insertOrder(order);
            return order;
        }
        return null;
    }

    @Override
    public Comment addComment(Comment comment){
        if(comment.getCommentId() != null)
            return null;
        if(comment.getUserId() != null && comment.getRoomId() != null && comment.getRateStars() >= 0 &&
                comment.getRateStars() <= 5 && comment.getContent() != null) {
            commentMapper.insertComment(comment);
            return comment;
        }
        return null;
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
    public void deleteComment(List<Integer> ids){
        for(int id:ids){
            commentMapper.deleteCommentById(id);
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

    @Override
    public void resetComment(List<Integer> ids){
        for(int id:ids){
            commentMapper.resetCommentById(id);
        }
    }

    @Override
    public User getUserById(Integer id){
        return userMapper.getUserById(id);
    }
}
