package com.example.demo.controller;

import com.example.demo.common.Response;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService userService;

    @PostMapping("/login")
    public Response<?> login(@RequestBody User user){
        return userService.login(user);
    }

    @PostMapping("/register")
    public Response<?> register(@RequestBody User user){
        return userService.register(user);
    }

    @GetMapping("/getById")
    public Response<?> findById(Integer id){
        return userService.getUserById(id);
    }

    @PostMapping("/restart")
    public Response<?> restart(Integer uid){
        return userService.restart(uid);
    }

}



//    @PostMapping
//    public Result<?> save(@RequestBody User user){
//        if(user.getPassword()==null) user.setPassword("123456");
//        userMapper.insert(user);
//        return Result.success();
//    }
//
//    @PutMapping("/update")
//    public Result<?> update(@RequestBody User user){
//        userMapper.updateById(user);
//        return Result.success();
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public Result<?> delete(@PathVariable Long id){
//        userMapper.deleteById(id);
//        return Result.success();
//    }
//
//
//    @GetMapping("/get")
//    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
//                              @RequestParam(defaultValue = "10") Integer pageSize,
//                              @RequestParam(defaultValue = "") String search ){
//        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery();
////        if (StrUtil.isNotBlank(search)) {
////            wrapper.like(User::getNickName, search);
////        }
//        Page<User> userPage = userMapper.selectPage(new Page<>(pageNum,pageSize),wrapper);
//        return Result.success(userPage);
//    }
//    @GetMapping("/get/{id}")
//    public Result<?> findById(@PathVariable Long id){
//        return Result.success(userMapper.selectById(id));
//    }
//}
