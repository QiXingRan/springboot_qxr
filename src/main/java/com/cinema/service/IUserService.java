package com.cinema.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cinema.domain.User;

import java.util.Map;

public interface IUserService extends IService<User> {
    Map<String, Object> login(User user);
    void logout(String token);
    Map<String, Object> getUserInfo(String token);
    void exchangeIntegral(String username, int consumeIntegral);

}
