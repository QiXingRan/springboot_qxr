package com.cinema.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cinema.controller.utils.Result;
import com.cinema.dao.UserMapper;
import com.cinema.domain.Record;
import com.cinema.domain.User;
import com.cinema.service.IProductService;
import com.cinema.service.IRecordService;
import com.cinema.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    IUserService userService;

    @Autowired
    IProductService productService;

    @Resource
    PasswordEncoder passwordEncoder;

    @Resource
    UserMapper userMapper;

    @Resource
    IRecordService recordService;

    @GetMapping("/all")
    public Result<List<User>> getAllUser(){
        List<User> list = userService.list();
        return Result.success(list,"查询成功");
    }

    @PostMapping("/login")
    public Result<Map<String,Object>> login(@RequestBody User user){
        Map<String,Object> data = userService.login(user);
        if(data != null){
            return Result.success(data);
        }
        return Result.fail(20002,"用户名或密码错误");
    }

    @PostMapping("/logout")
    public Result<?> logout(@RequestHeader("X-Token") String token){
        userService.logout(token);
        return Result.success();
    }

    @GetMapping("/info")
    public Result<Map<String,Object>> getUserInfo(@RequestParam("token") String token){
        // 根据token获取用户信息，redis
        Map<String,Object> data = userService.getUserInfo(token);
        if(data != null){
            return Result.success(data);
        }
        return Result.fail(20003,"登录信息无效，请重新登录");
    }

    @GetMapping("/list")
    public Result<Map<String,Object>> getUserList(@RequestParam(value = "username",required = false) String username,
                                                  @RequestParam(value = "phone",required = false) String phone,
                                                  @RequestParam(value = "pageNo") Long pageNo,
                                                  @RequestParam(value = "pageSize") Long pageSize){

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasLength(username),User::getUsername,username);
        wrapper.eq(StringUtils.hasLength(phone),User::getPhone,phone);
        wrapper.orderByDesc(User::getId);

        Page<User> page = new Page<>(pageNo,pageSize);
        userService.page(page,wrapper);

        Map<String,Object> data = new HashMap<>();
        data.put("total",page.getTotal());
        data.put("rows",page.getRecords());

        return Result.success(data);

    }

    @PostMapping("/adduser")
    public Result<?> addUser(@RequestBody User user){
        user.setPassword(user.getPassword());
        userService.save(user);
        return Result.success("新增成功");
    }

    //修改用户信息
    @PutMapping
    public Result<?> updateUser(@RequestBody User user){
        if (user.getNumber() < 0){
            // 不更改number字段的值
            user.setNumber(0);
            userMapper.updateUserByUsername(user);
            return Result.success("修改成功");
        }
        userMapper.updateUserByUsername(user);
        return Result.success("修改成功");
    }


    @GetMapping("/{id}")
    public Result<User> getUserById(@PathVariable("id") Integer id){
        User user = userService.getById(id);
        return Result.success(user);
    }

    @DeleteMapping("/{id}")
    public Result<User> deleteUserById(@PathVariable("id") Integer id){
        userService.removeById(id);
        return Result.success("删除成功");
    }

    //消费积分,并且积分消费记录登记成功，返回true
    @PutMapping("/consume")
    public Result consumeNumber(@RequestParam String username, @RequestParam int integral){
        if ( userMapper.selectNumberByUsername(username) < integral){
            return Result.success("false");
        }

        userService.exchangeIntegral(username, integral);

        return Result.success("true");
    }

    @PutMapping("/addnumber")
    public Result<?> updateNumber(@RequestParam String username,@RequestParam int integral){
        Record record = new Record();
        record.setUsername(username);
        record.setValue(integral);
        recordService.insertRecord(record);

        userMapper.updateNumberByUsername(username,integral);
        return Result.success("true");
    }

    @GetMapping("/select")
    public Result<List<User>> selectListByUsername(@RequestParam String username){
        List<User> result = userMapper.selectListByUsername(username);
        return Result.success(result,"true");
    }

    @DeleteMapping("/deluser")
    public Result DeleteUser(@RequestParam String username){
        userMapper.deleteUserByUsername(username);
        return Result.success("true");
    }

    @GetMapping("/count")
    public Result selectCountOfUser(){
        userService.count();
        return Result.success(userService.count());
    }

    @GetMapping("/login2")
    public Result<Map<String,String>> selectAdmin(){
        Map<String,String> result;
        result = userMapper.selectAdmin();
        return Result.success(result);
    }

}
