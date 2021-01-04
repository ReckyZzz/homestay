package com.homestay;

import com.github.pagehelper.PageInfo;
import com.homestay.mapper.RoomMapper;
import com.homestay.pojo.Comment;
import com.homestay.pojo.Order;
import com.homestay.pojo.Room;
import com.homestay.pojo.User;
import com.homestay.response.CommonResponse;
import com.homestay.service.AdminService;
import com.homestay.service.RoomService;
import com.homestay.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.Or;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
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
        //logIn
        /*user.setUserName("15912364094");user.setUserPwd("123456");
        CommonResponse<User> result = userService.logIn(user);
        System.out.println(result.getMessage());*/

        //insert
        /*user.setUserName("15912364094");user.setUserPwd("123456");
        CommonResponse<User> result = userService.register(user);
        System.out.println(result.getMessage());*/

        //reset
        /*CommonResponse<User> result = userService.resetPassword("15912364094","1234567");
        System.out.println(result.getMessage());*/

        //register
        /*user.setUserName("159123640944");user.setUserPwd("123456");
        CommonResponse<User> result = userService.register(user);
        System.out.println(result.getMessage());*/
    }

    @Test
    public void testAdmin(){
        User user = new User();
        Room room = new Room();
        Order order = new Order();
        //updateUser
        /*user.setUserId(10);user.setUserName("159123640945");user.setUserType(1);user.setGender(1);
        adminService.updateUser(user);*/

        //addUser
        /*for(int i=0;i<10;i++){
            user.setUserName("123456" + i);
            user.setUserPwd("123456");
            userService.register(user);
        }*/

        //deleteUser
        /*List<Integer> ids = new ArrayList<>();
        ids.add(1);ids.add(2);ids.add(10);ids.add(13);
        adminService.deleteUser(ids);*/

        //resetUser
        /*List<Integer> ids = new ArrayList<>();
        ids.add(1);ids.add(2);ids.add(10);ids.add(13);
        adminService.resetUser(ids);*/

        //getUser
        /*CommonResponse<PageInfo<User>> response = adminService.getUsers(2,5);
        PageInfo<User> pageInfos = response.getData();
        for(User u:pageInfos.getList()){
            System.out.println(u.getUserName());
        }*/

        //getRoom
        /*CommonResponse<PageInfo<Room>> response = adminService.getRooms(1,6);
        PageInfo<Room> pageInfos= response.getData();
        for(Room r:pageInfos.getList()){
            System.out.println(r.getRoomName());
        }*/

        //getOrder
        /*CommonResponse<PageInfo<Order>> response = adminService.getOrders(1,2);
        PageInfo<Order> pageInfos= response.getData();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(Order o:pageInfos.getList()){
            System.out.println(format.format(o.getCreateDate()));
        }*/

        //getComment
        /*CommonResponse<PageInfo<Comment>> response = adminService.getComments(1,3);
        PageInfo<Comment> pageInfos= response.getData();
        for(Comment c:pageInfos.getList()){
            System.out.println(c.getContent());
        }*/

        //updateRoom
        /*room.setRoomId(1);room.setDescription("你加撒大声地");
        adminService.updateRoom(room);*/

        //updateUser
        /*user.setUserId(2);user.setUserName("owner");user.setUserPwd("123456");
        adminService.updateUser(user);*/

        //updateOrder
        /*order.setOrderId(1);order.setOwnerId(2);order.setUserId(24);order.setLiveDate(new Date());
        adminService.updateOrder(order);*/

        //addOrder
        /*order.setUserId(4);order.setOwnerId(2);order.setRoomId(2);order.setLastDays(5);order.setCreateDate(new Date());
        order.setLiveDate(new Date());order.setMoney(roomMapper.getRoomByRoomId(2).getRoomPrice()*5);
        adminService.addOrder(order);*/

        //deleteRoom
        /*List<Integer> ids = new ArrayList<>();
        ids.add(1);ids.add(2);ids.add(6);
        adminService.deleteRoom(ids);*/

        //deleteOrder
        /*List<Integer> ids = new ArrayList<>();
        ids.add(1);ids.add(2);ids.add(3);
        adminService.resetOrder(ids);*/

        //deleteComment
        /*List<Integer> ids = new ArrayList<>();
        ids.add(1);ids.add(2);ids.add(3);
        adminService.resetComment(ids);*/
    }

    @Test
    public void testRoom(){
        Room room = new Room();
        //adRoom
        room.setRoomOwner(2);room.setRoomName("测试");
        CommonResponse<Room> response = roomService.addRoom(room);
        System.out.println(response.getMessage());

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
