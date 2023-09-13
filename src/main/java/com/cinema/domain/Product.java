package com.cinema.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Product {
    @TableId(value = "object")
    private String object;
    private int price;
    private String describle;
}
