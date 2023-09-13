package com.cinema.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class EchartsData1 implements Serializable {
    Integer[] consume = new Integer[5];
    Integer[] recharge = new Integer[5];
    Integer[] integral = new Integer[5];
}
