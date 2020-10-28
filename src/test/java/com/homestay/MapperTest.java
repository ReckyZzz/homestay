package com.homestay;

import com.homestay.entity.User;
import com.homestay.mapper.UserMapper;
import com.homestay.mapper.RoomMapper;
import com.homestay.entity.Room;
import com.homestay.mapper.OrderMapper;
import com.homestay.entity.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MapperTest {
    @Autowired
    UserMapper userMapper;
    @Autowired
    RoomMapper roomMapper;
    @Autowired
    OrderMapper orderMapper;
    @Test
    public void testUser(){
        //User user = userMapper.getUserByInfo("admin","123456");
        User user = userMapper.getUserById(1);
        if(user!=null){
            System.out.println(user.getUserId());
            System.out.println(user.getUserName());
            System.out.println(user.getUserPwd());
            System.out.println(user.getUserType());
            System.out.println(user.getGender());
            System.out.println(user.getEmail());
        }
        else{
            System.out.println("failure");
        }
    }
    @Test
    public void testRoom(){
        Room room = roomMapper.getRoomByRoomId(1);
        if(room!=null){
            System.out.println(room.getRoomId());
            System.out.println(room.getRoomName());
            System.out.println(room.getRoomOwner());
            System.out.println(room.getRoomPrice());
            System.out.println(room.getDescription());
            System.out.println(room.getIsAvailable());
        }
        else{
            System.out.println("failure");
        }
    }
    @Test
    public void testOrder(){
        Order order = orderMapper.getOrderByOrderId(1);
        if(order!=null){
            System.out.println(order.getOrderId());
            System.out.println(order.getUserId());
            System.out.println(order.getOwnerId());
            System.out.println(order.getRoomId());
            System.out.println(order.getLastDays());
            System.out.println(order.getCreateDate());
            System.out.println(order.getMoney());
        }
        else{
            System.out.println("failure");
        }
    }
}
