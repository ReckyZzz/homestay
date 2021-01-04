package com.homestay;

import com.homestay.mapper.CommentMapper;
import com.homestay.mapper.UserMapper;
import com.homestay.mapper.RoomMapper;
import com.homestay.mapper.OrderMapper;
import com.homestay.pojo.Comment;
import com.homestay.pojo.Order;
import com.homestay.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MapperTest {
    @Resource
    UserMapper userMapper;
    @Resource
    RoomMapper roomMapper;
    @Resource
    OrderMapper orderMapper;
    @Resource
    CommentMapper commentMapper;
    @Test
    public void testUser(){
        User user = new User();
        int result = 0;

        //testGet
        /*user = userMapper.getUserByUsername("admin@qq.com");
        if(user!=null){
            System.out.println(user.getUserId());
            System.out.println(user.getUserName());
            System.out.println(user.getUserPwd());
            System.out.println(user.getUserType());
            System.out.println(user.getGender());
            System.out.println(user.getDelFlag());
        }
        if(user == null){
            System.out.println("用户不存在");
        }*/

        //testReset
        /*//int num = userMapper.resetPasswordByUsername("244373762@qq.com","z4122262");
        int num = userMapper.resetUserByUsername("admin@qq.com");
        System.out.println(num);*/

        //testInsert
        user.setUserName("123456");user.setUserPwd("123456");
        result = userMapper.insertUser(user);
        System.out.println(result);

        //testDelete
        /*result = userMapper.deleteUserByUsername("admin@qq.com");
        System.out.println(result);/*
         */

        //testGet
        //List<User> users = userMapper.getUsersByUsername("15912364094");
        /*List<User> users = userMapper.list();
        for(User u:users){
            System.out.println(u.getUserName());
        }*/
    }
    /*@Test
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
    }*/
    /*@Test
    public void testOrder(){
        //testDelete
        orderMapper.deleteOrderById(1);
        orderMapper.resetOrderById(1);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Order order = orderMapper.getOrderByOrderId(1);
        if(order!=null){
            System.out.println("订单ID: " + order.getOrderId());
            System.out.println("用户ID: " + order.getUserId());
            System.out.println("民宿主人ID: " + order.getOwnerId());
            System.out.println("民宿ID: " + order.getRoomId());
            System.out.println("持续时间: " + order.getLastDays());
            System.out.println("创建时间: " + format.format(order.getCreateDate()));
            System.out.println("总金额: " + order.getMoney());
        }
        else{
            System.out.println("failure");
        }
    }*/
    /*@Test
    public void testComment(){
        commentMapper.deleteCommentByUserId(1);
        commentMapper.resetCommentByUserId(1);
        Comment comment = commentMapper.getCommentByUserId(1);
        if(comment != null) {
            System.out.println(comment.getCommentId());
            System.out.println(comment.getUserId());
            System.out.println(comment.getRoomId());
            System.out.println(comment.getRateStars());
            System.out.println(comment.getContent());
        }
        if(comment == null){
            System.out.println("评论不存在");
        }
    }*/
}
