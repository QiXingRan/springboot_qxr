package com.cinema.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cinema.controller.utils.Result;
import com.cinema.dao.RecordMapper;
import com.cinema.domain.Record;
import com.cinema.service.IRecordService;
import com.cinema.service.IUserService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/record")
public class RecordController {
    @Resource
    IRecordService recordService;

    @Resource
    IUserService userService;

    @Resource
    RecordMapper recordMapper;

    @GetMapping("/list")
    public Result<Map<String,Object>> getList(@RequestParam(value = "username",required = false) String username,
                                                  @RequestParam(value = "pageNo") Long pageNo,
                                                  @RequestParam(value = "pageSize") Long pageSize){

        LambdaQueryWrapper<Record> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasLength(username),Record::getUsername,username);
        wrapper.orderByDesc(Record::getTime);

        Page<Record> page = new Page<>(pageNo,pageSize);
        recordService.page(page,wrapper);

        Map<String,Object> data = new HashMap<>();
        data.put("total",page.getTotal());
        data.put("rows",page.getRecords());

        return Result.success(data);
    }

    //消费记录增加
    @PostMapping("/sub")
    public Result<?> consumeSub(@RequestParam String username, @RequestParam String integral){
        Record record = new Record();
        record.setUsername(username);
        record.setValue(Integer.parseInt("-"+integral));
        recordService.insertRecord(record);
        return Result.success("消费登记成功");
    }

    @PostMapping("/add")
    public Result<?> consumeAdd(@RequestParam String username, @RequestParam String integral){
        Record record = new Record();
        record.setUsername(username);
        record.setValue(Integer.parseInt(integral));
        recordService.insertRecord(record);
        return Result.success("积分登记成功");
    }

    @GetMapping("/allsub")
    public Result<?> allSub(){
        recordService.getConsumeCount();
        return Result.success("获取所有消费记录成功");
    }

    @GetMapping("/alladd")
    public Result<?> allAdd(){
        recordService.getRechargeCount();
        return Result.success("获取所有增加记录成功");
    }

    @DeleteMapping("/del")
    public Result<?> deleteByUsername(@RequestParam String username){
        recordService.deleteRecordByUsername(username);
        return Result.success("删除" + username +"所有记录成功");
    }

    @GetMapping("/all")
    public Result<List<Record>> getAllRecord(){
        List<Record> list = recordService.list();
        return Result.success(list,"查询成功");
    }

    @GetMapping("/select")
    public Result<List<Record>> selectRecordByUsername(@RequestParam String username){
        List<Record> result = recordMapper.selectListByUsername(username);
        return Result.success(result,"true");
    }

    //消费记录总行数
    @GetMapping("/count")
    public Result selectCountRecord(){
        recordMapper.selectCountRecord();
        return Result.success(recordMapper.selectCountRecord());
    }

}
