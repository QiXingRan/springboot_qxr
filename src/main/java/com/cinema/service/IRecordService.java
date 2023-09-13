package com.cinema.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cinema.domain.EchartsData1;
import com.cinema.domain.Record;

import java.util.List;

public interface IRecordService extends IService<Record> {
    void deleteRecordByUsername(String username);

    void insertRecord(Record record);

    Long getConsumeCount();

    Long getRechargeCount();

    List<Record> selectRecordByUsername(String username);

    EchartsData1 getEchartsData1();

}
