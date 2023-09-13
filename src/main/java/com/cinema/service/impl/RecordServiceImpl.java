package com.cinema.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cinema.dao.RecordMapper;
import com.cinema.domain.EchartsData1;
import com.cinema.domain.Record;
import com.cinema.service.IRecordService;
import com.cinema.utils.DateManageSystem;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements IRecordService {
    @Resource
    RecordMapper recordMapper;

    @Override
    public void deleteRecordByUsername(String username) {
        recordMapper.deleteRecordByUsername(username);
    }

    @Override
    public void insertRecord(Record record) {
        recordMapper.insertRecord(record);
    }

    @Override
    public Long getConsumeCount() {
        return recordMapper.selectConsumeCount();
    }


    @Override
    public Long getRechargeCount() {
        return recordMapper.selectRechargeCount();
    }

    @Override
    public List<Record> selectRecordByUsername(String username) {
        return recordMapper.selectRecordByUsername(username);
    }

    @Override
    public EchartsData1 getEchartsData1() {
        EchartsData1 echartsData1 = new EchartsData1();
        Integer[] consume = new Integer[5];
        Integer[] recharge = new Integer[5];
        //1.获得上一个周的周一到周日的日期
        DateManageSystem dateManageSystem = new DateManageSystem();
        String[] DateLimit = dateManageSystem.getLastWeek();
        //2.循环上一个周一到周日，查询在当日0点到24点之间交易的记录,并计算相对应的数据之和
        for(int i = 0;i<5;i++){
            String startDate = DateLimit[i];
            String endDate = DateLimit[i+1];
            //积分消费
            consume[i] = recordMapper.selectConsumeCountByDate(startDate,endDate)==null?0:recordMapper.selectConsumeCountByDate(startDate,endDate);
            consume[i]*=-1;
            //积分充值
            recharge[i] = recordMapper.selectRechargeCountByDate(startDate,endDate)==null?0:recordMapper.selectRechargeCountByDate(startDate,endDate);
        }
        //3.存入echartsData1中
        echartsData1.setConsume(consume);
        echartsData1.setRecharge(recharge);
        return echartsData1;
    }



}
