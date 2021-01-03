package com.homestay.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.homestay.mapper.UserMapper;
import com.homestay.pojo.User;
import com.homestay.response.CommonResponse;
import com.homestay.service.AdminService;
import com.homestay.util.EncryptUtil;
import com.homestay.util.SessionUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Resource
    UserMapper userMapper;

    @Override
    public CommonResponse<User> login(User admin) {
        HttpSession session = SessionUtil.getSession();
        SessionUtil.removeSession();
        User userInfo = userMapper.getUserByUsername(admin.getUserName());
        if(userInfo != null){
            if(EncryptUtil.check(admin.getUserPwd(),userInfo.getUserPwd())) {
                if (session != null) {
                    session.setAttribute("admin", admin);
                }
                return new CommonResponse<>(0, "登录成功", userInfo);
            }
            else{
                return new CommonResponse<>(1,"密码错误",null);
            }
        }
        else{
            return new CommonResponse<>(2,"用户名错误",null);
        }
    }

    @Override
    public CommonResponse<PageInfo<User>> getUsers(Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<User> users = userMapper.list();
        PageInfo<User> pageInfo = new PageInfo<>(users);
        return new CommonResponse<>(0,"查询成功",pageInfo);
    }

    @Override
    public User updateUser(User user){
        if(user.getUserId() != null){
            User oldUser = userMapper.getUserById(user.getUserId());
            if(oldUser == null){
                return null;
            }
            if(user.getUserName() != oldUser.getUserName()){
                oldUser.setUserName(user.getUserName());
            }
            if(user.getUserPwd() != null){
                oldUser.setUserPwd(EncryptUtil.getEncodedString(user.getUserPwd()));
            }
            if(user.getGender() != oldUser.getGender()){
                oldUser.setGender(user.getGender());
            }
            if(user.getUserType() != oldUser.getUserType()){
                oldUser.setUserType(user.getUserType());
            }
            userMapper.updateUser(oldUser);
            return oldUser;
        }
        return null;
    }

    @Override
    public User addUser(User user){
        if(userMapper.getUserById(user.getUserId()) != null)
            return null;
        user.setUserPwd(EncryptUtil.getEncodedString(user.getUserPwd()));
        userMapper.insertUser(user);
        return user;
    }

    @Override
    public void deleteUser(List<Integer> ids){
        for(int id:ids){
            userMapper.deleteUserById(id);
        }
    }

    @Override
    public void resetUser(List<Integer> ids){
        for(int id:ids){
            userMapper.resetUserById(id);
        }
    }
}
