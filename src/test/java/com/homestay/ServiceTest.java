package com.homestay;

import com.homestay.entity.User;
import com.homestay.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTest {

    @Autowired
    UserService userService;

    @Test
    public void testUser(){
        User user = userService.logIn("admin","123456");
        if(user!=null){
            System.out.println(user.getUserId());
            System.out.println(user.getUserName());
            System.out.println(user.getUserPwd());
        }
        else{
            System.out.println("failure");
        }
    }
}
