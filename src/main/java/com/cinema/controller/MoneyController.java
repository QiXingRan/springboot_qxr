package com.cinema.controller;

import com.cinema.controller.utils.Result;
import com.cinema.dao.MoneyMapper;
import com.cinema.dao.UserMapper;
import com.cinema.domain.Money;
import com.cinema.domain.Record;
import com.cinema.service.IMoneyService;
import com.cinema.service.IRecordService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/money")
public class MoneyController {
    @Resource
    IMoneyService moneyService;
    @Resource
    MoneyMapper moneyMapper;

    @Resource
    UserMapper userMapper;

    @Resource
    IRecordService recordService;

    @PostMapping("/adduser")
    public Result<?> addUser(@RequestBody Money money){
        money.setUsername(money.getUsername());
        money.setRemark(money.getRemark());
        money.setMoney(money.getMoney());
        moneyService.save(money);
        return Result.success("新增成功");
    }

    @PutMapping("/addmoney")
    public Result<?> updateMoney(@RequestParam String username,@RequestParam int integral){
        moneyMapper.updateMoneyByUsername(username,integral);
        return Result.success("余额添加成功");
    }

    @PutMapping("/change")
    public Result<?> changeMoney(@RequestParam String username,@RequestParam int integral){
        moneyMapper.changeMoneyByUsername(username,integral);
        return Result.success("余额修改成功");
    }

    @DeleteMapping("/deluser")
    public Result<?> delUser(@RequestParam String username){
        moneyMapper.removeByUsername(username);
        return Result.success("用户删除成功");
    }

    //消费马内，1.money减少 2.积分增加10%的马内数 3.积分获取记录增加一条
    @PutMapping("/consume")
    public Result<?> updateMoney2(@RequestParam String username,@RequestParam int integral){
        if ( moneyMapper.selectMoneyByUsername(username) < integral){
            return Result.success("余额不足，消费失败");
        }
        Record record = new Record();
        record.setUsername(username);
        record.setValue(integral/10);
        recordService.insertRecord(record);

        userMapper.updateNumberByUsername(username,integral/10);

        moneyMapper.updateMoneyByUsername2(username,integral);
        return Result.success("true");
    }

    //数据库中money的总额
    @GetMapping("/sum")
    public Result selectCountOfMoney(){
        moneyMapper.selectSumMoney();
        return Result.success(moneyMapper.selectSumMoney());
    }

    //数据库中最大的老板余额
    @GetMapping("/max")
    public Result selectMaxMoney(){
        moneyMapper.selectMaxMoney();
        return Result.success(moneyMapper.selectMaxMoney());
    }
}
