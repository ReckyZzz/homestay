package com.homestay.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.homestay.pojo.*;
import com.homestay.response.CommonResponse;
import com.homestay.service.UserService;
import com.homestay.util.SessionUtil;
import com.homestay.vo.OrderVO;
import com.homestay.vo.RoomVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    UserService userService;

    @GetMapping("/isLogin")
    @ApiOperation("用户是否登录")
    public CommonResponse<Object> isLogin(@ApiIgnore HttpSession session){
        if(session.getAttribute(("user")) != null){
            return new CommonResponse<>(0,"已登录",null);
        }
        else{
            return new CommonResponse<>(1,"未登录",null);
        }
    }

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

    @PostMapping("/logout")
    @ApiOperation("登出")
    public CommonResponse<String> logout(@ApiIgnore HttpSession session){
        session.setAttribute("user",null);
        return new CommonResponse<>(0, "成功登出", null);
    }

    @PostMapping("/register")
    @ApiOperation("注册")
    public CommonResponse<User> register(String username,String password){
        User user = new User();
        user.setUserName(username);
        user.setUserPwd(password);
        return userService.register(user);
    }

    @PostMapping("/resetPassword")
    @ApiOperation("重置密码")
    public CommonResponse<User> resetPassword(String newPwd,String oldPwd,@ApiIgnore HttpSession session){
        User user = (User) session.getAttribute("user");
        if(userService.resetPassword(user,oldPwd,newPwd)){
            session.removeAttribute("user");
            return new CommonResponse<>(0, "修改成功", null);
        }
        return new CommonResponse<>(1, "原密码错误", null);
    }

    @GetMapping("/checkRoomInfo")
    @ApiOperation("查看房间信息")
    public CommonResponse<Object> checkRoomInfo(Integer roomId){
        return userService.checkRoomInfo(roomId);
    }

    @GetMapping("/getRooms")
    @ApiOperation("查看房间列表")
    public CommonResponse<PageInfo<Room>> getRooms(Integer pageNum,Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Room> rooms = userService.getRooms();
        for(Room r:rooms){
            r.setUrl("../img/" + r.getRoomId() +".jpg");
        }
        PageInfo<Room> pageInfo = new PageInfo<>(rooms);
        return new CommonResponse<>(0,"查询成功",pageInfo);
    }

    @GetMapping("/searchRooms")
    @ApiOperation("搜索房间")
    public CommonResponse<PageInfo<Room>> searchRooms(String name,Integer pageNum,Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Room> rooms = userService.searchRooms(name);
        for(Room r:rooms){
            r.setUrl("../img/" + r.getRoomId() +".jpg");
        }
        PageInfo<Room> pageInfo = new PageInfo<>(rooms);
        return new CommonResponse<>(0,"查询成功",pageInfo);
    }

    @PostMapping("/reserve")
    @ApiOperation("预定房间")
    public CommonResponse<Order> reserve(@ApiIgnore HttpSession session, Integer roomId, Date reserveDate, Integer days){
        User user =(User) session.getAttribute("user");
        return userService.reserve(user.getUserId(),roomId,days,reserveDate);
    }

    @GetMapping("/collectRoom")
    @ApiOperation("收藏房间")
    public CommonResponse<Object> collectRoom(@ApiIgnore HttpSession session,Integer roomId){
        User user = (User) session.getAttribute("user");
        return userService.collectRoom(user,roomId);
    }

    @GetMapping("cancelCollect")
    @ApiOperation("取消收藏房间")
    @RequestMapping(value = "/cancelCollect",method = RequestMethod.GET)
    public CommonResponse<Object> cancelCollect(@ApiIgnore HttpSession session,Room room){
        User user = (User) session.getAttribute("user");
        return userService.cancelCollectRoom(user,room);
    }

    //查看收藏房间列表
    @GetMapping("/getCollection")
    @ApiOperation("查看收藏房间列表")
    public CommonResponse<PageInfo<Room>> getCollections(@ApiIgnore HttpSession session,Integer pageNum,Integer pageSize){
        User user =(User) session.getAttribute("user");
        List<RoomCollection> collections = userService.getCollections(user.getUserId());
        PageHelper.startPage(pageNum,pageSize);
        List<Room> rooms = new ArrayList<>();
        for(RoomCollection c:collections){
            rooms.add(userService.getRoomById(c.getRoomId()));
        }
        PageInfo<Room> pageInfo = new PageInfo<>(rooms);
        return new CommonResponse<>(0,"查询成功",pageInfo);
    }

    @GetMapping("/getOrders")
    @ApiOperation("查看订单列表")
    public CommonResponse<PageInfo<OrderVO>> getOrders(@ApiIgnore HttpSession session, Integer pageNum, Integer pageSize){
        User user =(User) session.getAttribute("user");
        return userService.getOrders(user.getUserId(),pageNum,pageSize);
    }

    @GetMapping("/commentRoom")
    @ApiOperation("从订单评论房间")
    public CommonResponse<Comment> commentRoom(Integer orderId,Integer stars,String content){
        return userService.commentRoom(orderId,stars,content);
    }
}
