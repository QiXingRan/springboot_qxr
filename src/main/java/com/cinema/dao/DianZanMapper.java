package com.cinema.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cinema.domain.DianZan;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface DianZanMapper extends BaseMapper<DianZan> {
    @Select("select object,count(*) as count from tb_dian_zan where judge = 1 GROUP BY object ORDER BY COUNT DESC LIMIT 5")
    List<Map<String,Object>> countOfGood();

    @Select("select object,count(*) as count from tb_dian_zan where judge = 0 GROUP BY object ORDER BY COUNT DESC LIMIT 5")
    List<Map<String,Object>> countOfBad();
}
