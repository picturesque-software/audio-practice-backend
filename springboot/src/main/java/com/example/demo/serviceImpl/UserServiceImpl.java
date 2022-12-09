package com.example.demo.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.VO.UserVO;
import com.example.demo.common.Response;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public Response<?> login(User user) {
        // TODO 根据条件查单个
        User res=getOne(new QueryWrapper<User>().eq("username", user.getUsername()).eq("password", user.getPassword()));
        if(res==null){
            return Response.error("-1","用户名或密码错误");
        }
        return Response.success(new UserVO(res), "登录成功！欢迎");
    }

    @Override
    public Response<?> register(User user) {
        User res=getOne(new QueryWrapper<User>().eq("username", user.getUsername()));
        if(res!=null){
            return Response.error("-1","用户名已存在");
        }
        user.setStep(0);
        // TODO 插入一条记录
        save(user);
        return Response.success("注册成功！请登录");
    }

    @Override
    public Response<?> getUserById(Integer id) {
        if (!isUserExits(id)) return Response.error("-1", "用户不存在");
        return Response.success(new UserVO(getById(id)));
    }

    /**
     * 根据id判断用户是否存在
     * @param id 用户id
     * @return
     */
    public boolean isUserExits(Integer id){
        return getById(id) != null;
    }
}
