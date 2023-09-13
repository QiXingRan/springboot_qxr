package com.cinema.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cinema.dao.UserMapper;
import com.cinema.domain.Record;
import com.cinema.domain.User;
import com.cinema.service.IRecordService;
import com.cinema.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Resource
    IRecordService recordService;

    @Resource
    UserMapper userMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public Map<String, Object> login(User user) {
        // 根据用户名查询
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername,user.getUsername());
        User loginUser = this.baseMapper.selectOne(wrapper);
        // 结果不为空，并且密码和传入密码匹配，则生成token，并将用户信息存入redis
        if(loginUser != null && passwordEncoder.matches(user.getPassword(),passwordEncoder.encode(loginUser.getPassword()))){
            // 暂时用UUID, 终极方案是jwt
            String key = "user:" + UUID.randomUUID();
            // 存入redis
            loginUser.setPassword(null);
            redisTemplate.opsForValue().set(key,loginUser,60, TimeUnit.MINUTES);
            // 返回数据
            Map<String, Object> data = new HashMap<>();
            data.put("token",key);
            return data;
        }
        return null;
    }

    @Override
    public Map<String, Object> getUserInfo(String token) {
        // 根据token获取用户信息，redis
        Object obj = redisTemplate.opsForValue().get(token);
        if(obj != null){
            User loginUser = JSON.parseObject(JSON.toJSONString(obj),User.class);
            Map<String, Object> data = new HashMap<>();
            data.put("name",loginUser.getUsername());
            // 角色
            List<String> roleList = this.baseMapper.getRoleNameByUserId(loginUser.getId());
            data.put("roles",roleList);
            return data;
        }
        return null;
    }

    @Override
    public void exchangeIntegral(String username, int consumeIntegral) {
        // 先通过username查询所有人的信息和积分
        List<User> users = userMapper.selectByUsername(username);
        for(User user:users){
        //如果消耗积分比当前卡中积分要多，将消耗积分减去当前卡积分，将卡中积分置0，并设置消费记录
        if(user.getNumber() <= consumeIntegral){
            consumeIntegral -= user.getNumber();
            Record record2 = new Record();
            record2.setUsername(user.getUsername());
            record2.setValue(user.getNumber()*-1);
            user.setNumber(0);
            userMapper.updateUserByUsername(user);
            recordService.insertRecord(record2);
            }else{
            //消耗积分比当前卡中少，卡中积分减去需消耗积分
            Record record = new Record();
            record.setUsername(user.getUsername());
            record.setValue(consumeIntegral*-1);
            user.setNumber(user.getNumber()-consumeIntegral);
            userMapper.updateUserByUsername(user);
            recordService.insertRecord(record);
                }
            }
        }





    @Override
    public void logout(String token) {
        redisTemplate.delete(token);
    }
}
