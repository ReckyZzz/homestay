package com.homestay.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.homestay.pojo.*;
import com.homestay.response.CommonResponse;
import com.homestay.service.UserService;
import com.homestay.util.SessionUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Resource
    UserService userService;

    //用户是否登录
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
    @PostMapping("/login")
    @ApiOperation("用户登录")
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
        return userService.login(user);
    }

    //登出
    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    public CommonResponse<String> logout(HttpSession session){
        session.setAttribute("user",null);
        return new CommonResponse<>(0, "成功登出", null);
    }

    //注册
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public CommonResponse<User> register(String username,String password){
        User user = new User();
        user.setUserName(username);
        user.setUserPwd(password);
        return userService.register(user);
    }

    //重置密码
    @RequestMapping(value = "/resetPassword",method = RequestMethod.POST)
    public CommonResponse<User> resetPassword(String newPwd,String oldPwd,HttpSession session){
        User user = (User) session.getAttribute("user");
        if(userService.resetPassword(user,oldPwd,newPwd)){
            session.removeAttribute("user");
            return new CommonResponse<>(0, "修改成功", null);
        }
        return new CommonResponse<>(1, "原密码错误", null);
    }

    //查看房间信息
    @RequestMapping(value = "/checkRoomInfo",method = RequestMethod.GET)
    public CommonResponse<Object> checkRoomInfo(Room room){
        List<Comment> comments = userService.getCommentsByRoom(room);
        return new CommonResponse<>(0,"查询成功",comments);
    }

    //查看房间列表
    @RequestMapping(value = "/getRooms",method = RequestMethod.GET)
    public CommonResponse<PageInfo<Room>> getRooms(Integer pageNum,Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Room> rooms = userService.getRooms();
        PageInfo<Room> pageInfo = new PageInfo<>(rooms);
        return new CommonResponse<>(0,"查询成功",pageInfo);
    }

    //搜索房间
    @RequestMapping(value = "/searchRooms",method = RequestMethod.GET)
    public CommonResponse<PageInfo<Room>> searchRooms(String name,Integer pageNum,Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Room> rooms = userService.searchRooms(name);
        PageInfo<Room> pageInfo = new PageInfo<>(rooms);
        return new CommonResponse<>(0,"查询成功",pageInfo);
    }

    //预订房间
    @RequestMapping(value = "/reserve",method = RequestMethod.POST)
    public CommonResponse<Order> reserve(HttpSession session, Integer roomId, Date reserveDate, Integer days){
        User user =(User) session.getAttribute("user");
        return userService.reserve(user.getUserId(),roomId,days,reserveDate);
    }

    //收藏房间
    @RequestMapping(value = "/collectRoom",method = RequestMethod.GET)
    public CommonResponse<Object> collectRoom(HttpSession session,Room room){
        User user = (User) session.getAttribute("user");
        return userService.collectRoom(user,room);
    }

    //取消收藏房间
    @RequestMapping(value = "/cancelCollect",method = RequestMethod.GET)
    public CommonResponse<Object> cancelCollect(HttpSession session,Room room){
        User user = (User) session.getAttribute("user");
        return userService.cancelCollectRoom(user,room);
    }

    //查看收藏房间列表
    @RequestMapping(value = "/getCollections",method = RequestMethod.GET)
    public CommonResponse<PageInfo<Room>> getCollections(HttpSession session,Integer pageNum,Integer pageSize){
        User user =(User) session.getAttribute("user");
        List<RoomCollection> collections = userService.getCollections(user);
        PageHelper.startPage(pageNum,pageSize);
        List<Room> rooms = new ArrayList<>();
        for(RoomCollection c:collections){
            rooms.add(userService.getRoomById(c.getRoomId()));
        }
        PageInfo<Room> pageInfo = new PageInfo<>(rooms);
        return new CommonResponse<>(0,"查询成功",pageInfo);
    }

    //查看订单列表
    @RequestMapping(value = "/getOrders",method = RequestMethod.GET)
    public CommonResponse<PageInfo<Order>> getOrders(HttpSession session,Integer pageNum,Integer pageSize){
        User user =(User) session.getAttribute("user");
        return userService.getOrders(user.getUserId(),pageNum,pageSize);
    }

    //从订单评论房间
    @RequestMapping(value = "/commentRoom",method = RequestMethod.GET)
    public CommonResponse<Comment> commentRoom(Order order,Integer stars,String content){
        return userService.commentRoom(order,stars,content);
    }
}
