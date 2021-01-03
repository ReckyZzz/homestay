package com.homestay;

import com.github.pagehelper.PageInfo;
import com.homestay.pojo.User;
import com.homestay.response.CommonResponse;
import com.homestay.service.AdminService;
import com.homestay.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTest {
    @Resource
    UserService userService;
    @Resource
    AdminService adminService;

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
        //update
        /*user.setUserId(10);user.setUserName("159123640945");user.setUserType(1);user.setGender(1);
        adminService.updateUser(user);*/

        //add
        /*for(int i=0;i<10;i++){
            user.setUserName("123456" + i);
            user.setUserPwd("123456");
            userService.register(user);
        }*/

        //delete
        /*List<Integer> ids = new ArrayList<>();
        ids.add(1);ids.add(2);ids.add(10);ids.add(13);
        adminService.deleteUser(ids);*/

        //reset
        /*List<Integer> ids = new ArrayList<>();
        ids.add(1);ids.add(2);ids.add(10);ids.add(13);
        adminService.resetUser(ids);*/

        //get
        /*CommonResponse<PageInfo<User>> response = adminService.getUsers(2,5);
        PageInfo<User> pageInfos = response.getData();
        for(User u:pageInfos.getList()){
            System.out.println(u.getUserName());
        }*/
    }
}
