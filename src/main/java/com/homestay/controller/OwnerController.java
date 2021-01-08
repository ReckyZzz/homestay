package com.homestay.controller;

import com.homestay.pojo.Image;
import com.homestay.pojo.User;
import com.homestay.response.CommonResponse;
import com.homestay.service.OwnerService;
import com.homestay.util.SessionUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.InputStream;

@RestController
@RequestMapping("/owner")
public class OwnerController {
    @Resource
    OwnerService ownerService;

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
        return ownerService.login(user);
    }

    @PostMapping("/logout")
    @ApiOperation("登出")
    public CommonResponse<String> logout(@ApiIgnore HttpSession session){
        session.setAttribute("user",null);
        return new CommonResponse<>(0, "成功登出", null);
    }

    @PostMapping("/upload")
    @ApiOperation("上传图片")
    public CommonResponse<Object> saveImage(@RequestParam("file")MultipartFile file){
        if(file.isEmpty()){
            return new CommonResponse<>(1,"上传失败，请选择文件",null);
        }
        try{
            InputStream is = file.getInputStream();
            Image image = new Image();
            image.setRoomId(1);
            byte[] pic= new byte[(int)file.getSize()];
            is.read(pic);
            image.setImageData(pic);
            ownerService.addImage(image);
            return new CommonResponse<>(0,"上传成功",null);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new CommonResponse<>(1,"上传失败，请选择文件",null);
    }
}
