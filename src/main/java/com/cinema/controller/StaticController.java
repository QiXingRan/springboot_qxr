package com.cinema.controller;


import com.cinema.controller.utils.Result;
import com.cinema.dao.MoneyMapper;
import com.cinema.dao.RecordMapper;
import com.cinema.domain.StaticData1;
import com.cinema.service.IRecordService;
import com.cinema.service.IUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/static")
public class StaticController {
    @Resource
    IRecordService recordService;

    @Resource
    RecordMapper recordMapper;

    @Resource
    IUserService userService;

    @Resource
    MoneyMapper moneyMapper;

    //显示出消费积分和获取积分的总数（可做饼状图）
    @GetMapping("/getData1")
    public Result getData1(){
        StaticData1 staticData1 = new StaticData1();
        staticData1.setConsumeNum(recordService.getConsumeCount()*-1);
        staticData1.setRechargeNum(recordService.getRechargeCount());
        return Result.success(staticData1);
    }

    //显示出最近消费积分和获取积分的总数（可做饼状图）
    @GetMapping("/getData7")
    public Result getSchart1Data(){
        return Result.success(recordService.getEchartsData1());
    }

    //显示出充值积分的前五名老板
    @GetMapping("/getData2")
    public Result getData2(){
        List<Map<String,Object>> result = recordMapper.selectRechargeLimit5();
        return Result.success(result);
    }

    //显示出消费积分的前五名老板
    @GetMapping("/getData3")
    public Result getData3(){
        List<Map<String,Object>> result = recordMapper.selectConsumeLimit5();
        return Result.success(result);
    }

    //消费记录滚轮重复为Data4

    //马内最多的5个人, Data5
    @GetMapping("/getData5")
    public Result getData5(){
        List<Map<String,Object>> result = moneyMapper.selectTopLimit5();
        return Result.success(result);
    }

//    //Data6 的积分消费、获取数额
//    @GetMapping("/getData6")
//    public Result getData6(){
//        StaticData1 staticData1 = new StaticData1();
//        staticData1.setConsumeNum(recordService.getConsumeCount()*-1);
//        staticData1.setRechargeNum(recordService.getRechargeCount());
//        return Result.success(staticData1);
//    }

}
