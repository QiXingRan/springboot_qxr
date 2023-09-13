package com.cinema.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class StaticData1 implements Serializable {
    Long consumeNum;

    Long rechargeNum;
}
