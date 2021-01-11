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
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Resource
    AdminService adminService;

    @GetMapping("/isLogin")
    @ApiOperation("管理员是否登录")
    public CommonResponse<Object> isLogin(@ApiIgnore HttpSession session){
        if(session.getAttribute(("user")) != null){
            return new CommonResponse<>(0,"已登录",null);
        }
        else{
            return new CommonResponse<>(1,"未登录",null);
        }
    }

    @PostMapping("/login")
    @ApiOperation("管理员登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username",value = "用户账号",required = true),
            @ApiImplicitParam(name = "password",value = "用户密码",required = true),
    })
    public CommonResponse<User> login(@RequestParam String username,
                                      @RequestParam String password,
                                      @ApiIgnore HttpSession session){
        User user = new User();
        user.setUserName(username);
        user.setUserPwd(password);
        SessionUtil.setSession(session);
        return adminService.login(user);
    }

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

    @PostMapping("updateCommentInfo")
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

    @GetMapping("/addUser")
    @ApiOperation("批量删除用户")
    public CommonResponse<Object> deleteUsers(String userIds){
        List<Integer> ids = JSONUtil.toList(JSONUtil.parseArray(userIds),Integer.class);
        adminService.deleteUser(ids);
        return new CommonResponse<>(0,"删除成功",null);
    }

    @GetMapping("/deleteRooms")
    @ApiOperation("批量删除房间")
    public CommonResponse<Object> deleteRooms(String roomIds){
        List<Integer> ids = JSONUtil.toList(JSONUtil.parseArray(roomIds),Integer.class);
        adminService.deleteRoom(ids);
        return new CommonResponse<>(0,"删除成功",null);
    }

    @GetMapping("/deleteOrders")
    @ApiOperation("批量删除订单")
    public CommonResponse<Object> deleteOrders(String orderIds){
        List<Integer> ids = JSONUtil.toList(JSONUtil.parseArray(orderIds),Integer.class);
        adminService.deleteOrder(ids);
        return new CommonResponse<>(0,"删除成功",null);
    }

    @GetMapping("/deleteComments")
    @ApiOperation("批量删除评论")
    public CommonResponse<Object> deleteComments(String commentIds){
        List<Integer> ids = JSONUtil.toList(JSONUtil.parseArray(commentIds),Integer.class);
        adminService.deleteComment(ids);
        return new CommonResponse<>(0,"删除成功",null);
    }

    @GetMapping("/resetUsers")
    @ApiOperation("评论还原用户")
    public CommonResponse<Object> resetUsers(String userIds){
        List<Integer> ids = JSONUtil.toList(JSONUtil.parseArray(userIds),Integer.class);
        adminService.resetUser(ids);
        return new CommonResponse<>(0,"还原成功",null);
    }

    @GetMapping("/resetRooms")
    @ApiOperation("批量还原房间")
    public CommonResponse<Object> resetRooms(String roomIds){
        List<Integer> ids = JSONUtil.toList(JSONUtil.parseArray(roomIds),Integer.class);
        adminService.resetRoom(ids);
        return new CommonResponse<>(0,"还原成功",null);
    }

    @GetMapping("/resetOrders")
    @ApiOperation("批量还原订单")
    public CommonResponse<Object> resetOrders(String orderIds){
        List<Integer> ids = JSONUtil.toList(JSONUtil.parseArray(orderIds),Integer.class);
        adminService.resetOrder(ids);
        return new CommonResponse<>(0,"还原成功",null);
    }

    @GetMapping("/resetComments")
    @ApiOperation("批量还原评论")
    public CommonResponse<Object> resetComments(String commentIds){
        List<Integer> ids = JSONUtil.toList(JSONUtil.parseArray(commentIds),Integer.class);
        adminService.resetComment(ids);
        return new CommonResponse<>(0,"还原成功",null);
    }
}
