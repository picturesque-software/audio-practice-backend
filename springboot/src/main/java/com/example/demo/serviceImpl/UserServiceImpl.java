package com.example.demo.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.VO.UserVO;
import com.example.demo.common.Response;
import com.example.demo.entity.*;
import com.example.demo.mapper.*;
import com.example.demo.service.AudioService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final ResultMapper resultMapper;
    private final ResultStep1Mapper resultStep1Mapper;
    private final ResultStep2Mapper resultStep2Mapper;
    private final ResultStep3Mapper resultStep3Mapper;
    private final ResultStep4Mapper resultStep4Mapper;
    private final ResultStep5Mapper resultStep5Mapper;

    @Autowired
    public UserServiceImpl(ResultMapper resultMapper, ResultStep1Mapper resultStep1Mapper, ResultStep2Mapper resultStep2Mapper, ResultStep3Mapper resultStep3Mapper, ResultStep4Mapper resultStep4Mapper, ResultStep5Mapper resultStep5Mapper) {
        this.resultMapper = resultMapper;
        this.resultStep1Mapper = resultStep1Mapper;
        this.resultStep2Mapper = resultStep2Mapper;
        this.resultStep3Mapper = resultStep3Mapper;
        this.resultStep4Mapper = resultStep4Mapper;
        this.resultStep5Mapper = resultStep5Mapper;
    }

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

    @Override
    public Response<?> restart(Integer uid) {
        resultMapper.delete(new QueryWrapper<Result>().eq("uid",uid));
        resultStep1Mapper.delete(new QueryWrapper<ResultStep1>().eq("uid",uid));
        resultStep2Mapper.delete(new QueryWrapper<ResultStep2>().eq("uid",uid));
        resultStep3Mapper.delete(new QueryWrapper<ResultStep3>().eq("uid",uid));
        resultStep4Mapper.delete(new QueryWrapper<ResultStep4>().eq("uid",uid));
        resultStep5Mapper.delete(new QueryWrapper<ResultStep5>().eq("uid",uid));
        User user = getById(uid);
        user.setStep(0);
        updateById(user);
        return Response.success("之前的评价记录已删除，请重新开始！");
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
