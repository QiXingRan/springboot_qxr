package com.cinema.controller;


import com.cinema.controller.utils.Result;
import com.cinema.dao.DianZanMapper;
import com.cinema.domain.DianZan;
import com.cinema.service.IDianZanService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/dianzan")
public class DianZanController {
    @Resource
    IDianZanService dianZanService;

    @Resource
    DianZanMapper dianZanMapper;

    @PostMapping("/good")
    public Result insertGood(@RequestBody DianZan dianZan){
        dianZan.setObject(dianZan.getObject());
        dianZan.setJudge(dianZan.getJudge());
        dianZanService.save(dianZan);
        return Result.success("点赞成功");
    }

    @PostMapping("/bad")
    public Result insertBad(@RequestBody DianZan dianZan){
        dianZan.setObject(dianZan.getObject());
        dianZan.setJudge(dianZan.getJudge());
        dianZanService.save(dianZan);
        return Result.success("差评成功");
    }

    @GetMapping("/goodlist")
    public Result selectGoodCount(){
        dianZanMapper.countOfGood();
        return Result.success("点赞数前五名查询成功");
    }

    @GetMapping("/badlist")
    public Result selectBadCount(){
        dianZanMapper.countOfBad();
        return Result.success("差评数前五名查询成功");
    }
}
