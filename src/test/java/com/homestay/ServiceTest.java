package com.homestay;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.homestay.mapper.RoomMapper;
import com.homestay.pojo.*;
import com.homestay.response.CommonResponse;
import com.homestay.service.AdminService;
import com.homestay.service.RoomService;
import com.homestay.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTest {
    @Resource
    UserService userService;
    @Resource
    AdminService adminService;
    @Resource
    RoomService roomService;
    @Resource
    RoomMapper roomMapper;

    @Test
    public void testUser(){
        User user = new User();
        //login
        String username="admin";String password="123456";Integer pageNum=1;Integer pageSize=5;
        user.setUserId(1);
        user.setUserName(username);
        user.setUserPwd(password);
        CommonResponse<User> response = userService.login(user);
        user = response.getData();
        System.out.println(response.getMessage());
        System.out.println();

        //register
        /*user.setUserName("1234570");user.setUserPwd("123456");
        CommonResponse<User> result = userService.register(user);
        System.out.println(result.getMessage());*/

        //resetPassword
        /*String newPwd="1234567";String oldPwd="123456";
        if(userService.resetPassword(user,oldPwd,newPwd)){
            System.out.println("修改成功");
        }
        else {
            System.out.println("原密码错误");
        }*/

        //查看房间列表
        PageHelper.startPage(pageNum,pageSize+1);
        List<Room> rooms = userService.getRooms();
        PageInfo<Room> pageInfo = new PageInfo<>(rooms);
        System.out.println("查询成功");
        rooms = pageInfo.getList();
        System.out.println("查看房间列表结果:");
        for(Room r:rooms){
            System.out.println(r.getRoomName());
        }
        System.out.println();

        //查看房间信息
        List<Comment> comments = userService.getCommentsByRoom(rooms.get(1));
        System.out.println("查询成功");
        System.out.print("该房间的评论：");
        for(Comment c:comments){
            System.out.println(c.getContent());
        }
        System.out.println();

        //搜索房间
        /*PageHelper.startPage(pageNum,pageSize);
        List<Room> rooms = userService.searchRooms("真");
        PageInfo<Room> pageInfo = new PageInfo<>(rooms);
        System.out.println("查询成功");
        System.out.println("搜索结果：");
        rooms = pageInfo.getList();
        for(Room r:rooms){
            System.out.println(r.getRoomName());
        }*/

        //预定房间
        /*CommonResponse<Order> response2 = userService.reserve(3,4,3,new Date());
        System.out.println(response2.getMessage());*/

        //收藏房间
        /*CommonResponse<Object> response3 = userService.collectRoom(user,rooms.get(3));
        System.out.println(response3.getMessage());
        System.out.println();*/

        //取消收藏房间
        /*CommonResponse response4 = userService.cancelCollectRoom(user,rooms.get(3));
        System.out.println(response4.getMessage());
        System.out.println();*/

        //查看收藏列表
        List<RoomCollection> collections = userService.getCollections(user);
        PageHelper.startPage(pageNum,pageSize);
        rooms = new ArrayList<>();
        for(RoomCollection c:collections){
            rooms.add(userService.getRoomById(c.getRoomId()));
        }
        pageInfo = new PageInfo<>(rooms);
        System.out.println("查询成功");
        System.out.println("收藏房间列表：");
        rooms = pageInfo.getList();
        for(Room r:rooms){
            System.out.println(r.getRoomName());
        }
        System.out.println();

        //getOrders
        /*CommonResponse<PageInfo<Order>> response = userService.getOrders(4,1,5);
        List<Order> orders = response.getData().getList();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(Order o:orders){
            System.out.println(format.format(o.getCreateDate()));
        }*/
    }

    @Test
    public void testAdmin(){
        User user = new User();
        Room room = new Room();
        Order order = new Order();
        Comment comment = new Comment();

        //addUser
        /*for(int i=0;i<10;i++){
            user.setUserName("123456" + i);
            user.setUserPwd("123456");
            userService.register(user);
        }*/

        //addOrder
        /*order.setUserId(4);order.setOwnerId(2);order.setRoomId(2);order.setLastDays(5);
        order.setCreateDate(new Date());
        order.setLiveDate(new Date());order.setMoney(roomMapper.getRoomByRoomId(2).getRoomPrice()*5);
        adminService.addOrder(order);*/

        //addRoom
        /*room.setRoomOwner(2);room.setRoomName("测试3");room.setRoomPrice(998);
        adminService.addRoom(room);*/

        //addComment
        /*comment.setUserId(6);comment.setRoomId(4);comment.setRateStars(5);comment.setContent("测试2");
        adminService.addComment(comment);*/

        //getUser && deleteUser
        /*List<Integer> ids = new ArrayList<>();
        ids.add(1);ids.add(2);ids.add(10);ids.add(13);
        adminService.deleteUser(ids);
        CommonResponse<PageInfo<User>> response = adminService.getUsers(2,5);
        PageInfo<User> pageInfos = response.getData();
        for(User u:pageInfos.getList()){
            System.out.println(u.getUserName());
        }*/

        //getRoom && deleteRoom
        /*List<Integer> ids = new ArrayList<>();
        ids.add(1);ids.add(2);ids.add(6);
        adminService.deleteRoom(ids);
        CommonResponse<PageInfo<Room>> response = adminService.getRooms(1,6);
        PageInfo<Room> pageInfos= response.getData();
        for(Room r:pageInfos.getList()){
            System.out.println(r.getRoomName());
        }*/

        //getOrder && deleteOrder
        /*List<Integer> ids = new ArrayList<>();
        ids.add(1);ids.add(2);ids.add(3);
        adminService.resetOrder(ids);
        CommonResponse<PageInfo<Order>> response = adminService.getOrders(1,3);
        PageInfo<Order> pageInfos= response.getData();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(Order o:pageInfos.getList()){
            System.out.println(format.format(o.getCreateDate()));
        }*/

        //getComment && deleteCommnent
        /*List<Integer> ids = new ArrayList<>();
        ids.add(1);ids.add(2);ids.add(3);
        adminService.resetComment(ids);
        CommonResponse<PageInfo<Comment>> response = adminService.getComments(1,9);
        PageInfo<Comment> pageInfos= response.getData();
        for(Comment c:pageInfos.getList()){
            System.out.println(c.getContent());
        }*/

        //updateUser
        /*user.setUserId(2);user.setUserName("owner");user.setUserPwd("123456");
        adminService.updateUser(user);*/

        //updateRoom
        /*room.setRoomId(1);room.setDescription("你加撒大声地");
        adminService.updateRoom(room);*/

        //updateOrder
        /*order.setOrderId(1);order.setOwnerId(10);order.setUserId(3);
        adminService.updateOrder(order);*/

        //updateComment
        /*comment.setCommentId(1);comment.setRateStars(5);
        adminService.updateComment(comment);*/
    }

    @Test
    public void testRoom(){
        Room room = new Room();
        //adRoom

    }

    @Test
    public void testOrder(){
        Order order = new Order();
        //getOrder
        CommonResponse<PageInfo<Order>> response = adminService.getOrders(1,3);
        PageInfo<Order> pageInfos= response.getData();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(Order o:pageInfos.getList()){
            System.out.println(format.format(o.getCreateDate()));
        }
    }
}
