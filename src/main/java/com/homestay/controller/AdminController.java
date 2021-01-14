package com.homestay.controller;

import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageInfo;
import com.homestay.pojo.Comment;
import com.homestay.pojo.Order;
import com.homestay.pojo.Room;
import com.homestay.pojo.User;
import com.homestay.response.CommonResponse;
import com.homestay.service.AdminService;
import com.homestay.util.SessionUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Resource
    AdminService adminService;

    @GetMapping("/getUserInfo")
    @ApiOperation("获取用户列表")
    public CommonResponse<PageInfo<User>> getStudents(Integer pageNum, Integer pageSize){
        return adminService.getUsers(pageNum,pageSize);
    }

    @GetMapping("/getRoomInfo")
    @ApiOperation("获取房间列表")
    public CommonResponse<PageInfo<Room>> getRooms(Integer pageNum, Integer pageSize){
        return adminService.getRooms(pageNum,pageSize);
    }

    @GetMapping("/getOrderInfo")
    @ApiOperation("获取订单列表")
    public CommonResponse<PageInfo<Order>> getOrders(Integer pageNum, Integer pageSize){
        return adminService.getOrders(pageNum,pageSize);
    }

    @GetMapping("/getCommentInfo")
    @ApiOperation("获取评论列表")
    public CommonResponse<PageInfo<Comment>> getComments(Integer pageNum,Integer pageSize){
        return adminService.getComments(pageNum,pageSize);
    }

    @PostMapping("/updateUserInfo")
    @ApiOperation("更新用户信息")
    public CommonResponse<User> updateUser(User user){
        //User user = adminService.getUserById(userId);
        user = adminService.updateUser(user);
        if(user != null){
            return new CommonResponse<>(0,"修改成功",user);
        }
        return new CommonResponse<>(1,"不存在的用户",null);
    }

    @PostMapping("/updateRoomInfo")
    @ApiOperation("更新房间信息")
    public CommonResponse<Room> updateRoom(Room room){
        room = adminService.updateRoom(room);
        if(room != null){
            return new CommonResponse<>(0,"修改成功",room);
        }
        return new CommonResponse<>(1,"不存在的房间",null);
    }

    @PostMapping("/updateOrderInfo")
    @ApiOperation("更新房间信息")
    public CommonResponse<Order> updateOrder(Order order){
        order = adminService.updateOrder(order);
        if(order != null){
            return new CommonResponse<>(0,"修改成功",order);
        }
        return new CommonResponse<>(1,"不存在的订单",null);
    }

    @PostMapping("/updateCommentInfo")
    @ApiOperation("更新评论信息")
    public CommonResponse<Comment> updateComment(Comment comment){
        comment = adminService.updateComment(comment);
        if(comment != null){
            return new CommonResponse<>(0,"修改成功",comment);
        }
        return new CommonResponse<>(1,"不存在的评论",null);
    }

    @PostMapping("/addUser")
    @ApiOperation("添加用户")
    public CommonResponse<User> addUser(User user){
        user = adminService.addUser(user);
        if(user != null){
            return new CommonResponse<>(0,"添加成功",user);
        }
        return new CommonResponse<>(1,"用户已存在",null);
    }

    @PostMapping("/addRoom")
    @ApiOperation("添加房间")
    public CommonResponse<Room> addRoom(Room room){
        room = adminService.addRoom(room);
        if(room != null){
            return new CommonResponse<>(0,"添加成功",room);
        }
        return new CommonResponse<>(1,"房间已存在",null);
    }

    @PostMapping("/addOrder")
    @ApiOperation("添加订单")
    public CommonResponse<Order> addOrder(Order order){
        order = adminService.addOrder(order);
        if(order != null){
            return new CommonResponse<>(0,"添加成功",order);
        }
        return new CommonResponse<>(1,"订单已存在",null);
    }

    @PostMapping("/addComment")
    @ApiOperation("添加评论")
    public CommonResponse<Comment> addComment(Comment comment){
        comment = adminService.addComment(comment);
        if(comment != null){
            return new CommonResponse<>(0,"添加成功",comment);
        }
        return new CommonResponse<>(1,"订单已存在",null);
    }

    @GetMapping("/deleteUser")
    @ApiOperation("删除用户")
    public CommonResponse<Object> deleteUser(Integer userId){
        return adminService.deleteUser(userId);
    }

    @GetMapping("/deleteRoom")
    @ApiOperation("删除房间")
    public CommonResponse<Object> deleteRoom(Integer roomId){
        return adminService.deleteRoom(roomId);
    }

    @GetMapping("/deleteOrder")
    @ApiOperation("删除订单")
    public CommonResponse<Object> deleteOrder(Integer orderId){
        return adminService.deleteOrder(orderId);
    }

    @GetMapping("/deleteComment")
    @ApiOperation("删除评论")
    public CommonResponse<Object> deleteComments(Integer commentId){
        return adminService.deleteComment(commentId);
    }

    @GetMapping("/resetUser")
    @ApiOperation("还原用户")
    public CommonResponse<Object> resetUser(Integer userId){
        return adminService.resetUser(userId);
    }

    @GetMapping("/resetRoom")
    @ApiOperation("还原房间")
    public CommonResponse<Object> resetRoom(Integer roomId){
        return adminService.resetRoom(roomId);
    }

    @GetMapping("/resetOrder")
    @ApiOperation("还原订单")
    public CommonResponse<Object> resetOrder(Integer orderId){
        return adminService.resetOrder(orderId);
    }

    @GetMapping("/resetComment")
    @ApiOperation("还原评论")
    public CommonResponse<Object> resetComment(Integer commentId){
        return adminService.resetComment(commentId);
    }
}
