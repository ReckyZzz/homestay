package com.homestay.controller;

import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageInfo;
import com.homestay.pojo.Order;
import com.homestay.pojo.Room;
import com.homestay.pojo.User;
import com.homestay.response.CommonResponse;
import com.homestay.service.AdminService;
import com.homestay.service.UserService;
import com.homestay.util.SessionUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Resource
    AdminService adminService;

    //管理员是否登录
    @RequestMapping("/isLogin")
    public CommonResponse<Object> isLogin(HttpSession session){
        if(session.getAttribute(("user")) != null){
            return new CommonResponse<>(0,"已登录",null);
        }
        else{
            return new CommonResponse<>(1,"未登录",null);
        }
    }

    //登录
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public CommonResponse<User> login(String username, String password, HttpSession session){
        User user = new User();
        user.setUserName(username);
        user.setUserPwd(password);
        SessionUtil.setSession(session);
        return adminService.login(user);
    }

    //获取用户列表
    @RequestMapping(value = "/getUserInfo",method = RequestMethod.GET)
    public CommonResponse<PageInfo<User>> getStudents(Integer pageNum, Integer pageSize){
        return adminService.getUsers(pageNum,pageSize);
    }

    //获取房间列表
    @RequestMapping(value = "/getRoomInfo",method = RequestMethod.GET)
    public CommonResponse<PageInfo<Room>> getRooms(Integer pageNum, Integer pageSize){
        return adminService.getRooms(pageNum,pageSize);
    }

    //获取订单列表
    @RequestMapping(value = "/getOrderInfo",method = RequestMethod.GET)
    public CommonResponse<PageInfo<Order>> getOrders(Integer pageNum, Integer pageSize){
        return adminService.getOrders(pageNum,pageSize);
    }

    //更新用户信息
    @RequestMapping(value = "/updateUserInfo",method = RequestMethod.POST)
    public CommonResponse<User> updateUser(User user){
        user = adminService.updateUser(user);
        if(user != null){
            return new CommonResponse<>(0,"修改成功",user);
        }
        return new CommonResponse<>(1,"不存在的用户",null);
    }

    //更新房间信息
    @RequestMapping(value = "/updateRoomInfo",method = RequestMethod.POST)
    public CommonResponse<Room> updateRoom(Room room){
        room = adminService.updateRoom(room);
        if(room != null){
            return new CommonResponse<>(0,"修改成功",room);
        }
        return new CommonResponse<>(1,"不存在的房间",null);
    }

    //添加用户
    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
    public CommonResponse<User> addUser(User user){
        user = adminService.addUser(user);
        if(user != null){
            return new CommonResponse<>(0,"添加成功",user);
        }
        return new CommonResponse<>(1,"用户已存在",null);
    }

    //添加房间
    @RequestMapping(value = "/addRoom",method = RequestMethod.POST)
    public CommonResponse<Room> addRoom(Room room){
        room = adminService.addRoom(room);
        if(room != null){
            return new CommonResponse<>(0,"添加成功",room);
        }
        return new CommonResponse<>(1,"房间已存在",null);
    }

    //批量删除用户
    @RequestMapping(value = "/deleteUsers",method = RequestMethod.GET)
    public CommonResponse<Object> deleteUsers(String userIds){
        List<Integer> ids = JSONUtil.toList(JSONUtil.parseArray(userIds),Integer.class);
        adminService.deleteUser(ids);
        return new CommonResponse<>(0,"删除成功",null);
    }

    //批量删除房间
    @RequestMapping(value = "/deleteRooms",method = RequestMethod.GET)
    public CommonResponse<Object> deleteRooms(String roomIds){
        List<Integer> ids = JSONUtil.toList(JSONUtil.parseArray(roomIds),Integer.class);
        adminService.deleteRoom(ids);
        return new CommonResponse<>(0,"删除成功",null);
    }

    //批量还原用户
    @RequestMapping(value = "/resetUsers",method = RequestMethod.GET)
    public CommonResponse<Object> resetUsers(String userIds){
        List<Integer> ids = JSONUtil.toList(JSONUtil.parseArray(userIds),Integer.class);
        adminService.resetUser(ids);
        return new CommonResponse<>(0,"还原成功",null);
    }

    //批量还原房间
    @RequestMapping(value = "resetRooms",method = RequestMethod.GET)
    public CommonResponse<Object> resetRooms(String roomIds){
        List<Integer> ids = JSONUtil.toList(JSONUtil.parseArray(roomIds),Integer.class);
        adminService.resetRoom(ids);
        return new CommonResponse<>(0,"还原成功",null);
    }
}
