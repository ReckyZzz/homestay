package com.homestay;

import com.homestay.pojo.User;
import com.homestay.response.CommonResponse;
import com.homestay.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTest {

    @Resource
    UserService userService;

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
        user.setUserName("159123640944");user.setUserPwd("123456");
        CommonResponse<User> result = userService.register(user);
        System.out.println(result.getMessage());
    }
}
