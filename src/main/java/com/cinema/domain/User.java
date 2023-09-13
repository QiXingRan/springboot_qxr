package com.cinema.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class User {
    @TableId(value = "id")
    private int id;
    private String username;
    private String phone;
    private String password;
    private int number;
}
