package com.homestay.controller;

import com.github.pagehelper.PageInfo;
import com.homestay.pojo.Image;
import com.homestay.pojo.Room;
import com.homestay.pojo.User;
import com.homestay.response.CommonResponse;
import com.homestay.service.OwnerService;
import com.homestay.util.SessionUtil;
import com.homestay.vo.CommentVO;
import com.homestay.vo.OrderVO;
import com.homestay.vo.RoomVO2;
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
import java.util.List;

@RestController
@RequestMapping("/owner")
public class OwnerController {
    @Resource
    OwnerService ownerService;

    @GetMapping("/getRooms")
    @ApiOperation("查看房间列表")
    public CommonResponse<List<RoomVO2>> getRooms(@ApiIgnore HttpSession session){
        User owner =(User) session.getAttribute("user");
        List<RoomVO2> roomVO2s = ownerService.getRooms(owner.getUserId());
        return new CommonResponse<>(0,"查询成功",roomVO2s);
    }

    @GetMapping("/getOrders")
    @ApiOperation("查看订单列表")
    public CommonResponse<PageInfo<OrderVO>> getOrders(@ApiIgnore HttpSession session, Integer pageNum, Integer pageSize){
        User owner =(User) session.getAttribute("user");
        return ownerService.getOrders(owner.getUserId(),pageNum,pageSize);
    }

    @GetMapping("/getComments")
    @ApiOperation("查看评论列表")
    public CommonResponse<List<CommentVO>> getComments(@ApiIgnore HttpSession session){
        User owner =(User) session.getAttribute("user");
        return ownerService.getComments(owner.getUserId());
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
