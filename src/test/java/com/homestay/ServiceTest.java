package com.homestay;

import com.github.pagehelper.PageInfo;
import com.homestay.pojo.Order;
import com.homestay.pojo.Room;
import com.homestay.pojo.User;
import com.homestay.response.CommonResponse;
import com.homestay.service.AdminService;
import com.homestay.service.RoomService;
import com.homestay.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        CommonResponse<PageInfo<Order>> response = adminService.getOrders(1,2);
        PageInfo<Order> pageInfos= response.getData();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(Order o:pageInfos.getList()){
            System.out.println(format.format(o.getCreateDate()));
        }

        //updateRoom
        /*room.setRoomId(1);room.setDescription("你加撒大声地");
        adminService.updateRoom(room);*/

        //deleteRoom
        /*List<Integer> ids = new ArrayList<>();
        ids.add(1);ids.add(2);ids.add(6);
        adminService.deleteRoom(ids);*/

        //resetRoom
        /*List<Integer> ids = new ArrayList<>();
        ids.add(1);ids.add(2);ids.add(6);
        adminService.resetRoom(ids);*/
    }

    @Test
    public void testRoom(){
        Room room = new Room();
        //adRoom
        room.setRoomOwner(2);room.setRoomName("测试");
        CommonResponse<Room> response = roomService.addRoom(room);
        System.out.println(response.getMessage());

    }
}
