package com.cinema.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cinema.domain.Record;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface RecordMapper extends BaseMapper<Record> {
    void deleteRecordByUsername(String username);
    void insertRecord(Record record);
    List<Record> selectRecordByUsername(String Username);
    Long selectConsumeCount();
    Long selectRechargeCount();

    @Select("select sum(value) from tb_record where value < 0 and time between #{startDate} and #{endDate}")
    Integer selectConsumeCountByDate(@Param("startDate") String startDate, @Param("endDate") String endDate );

    @Select("select sum(value) from tb_record where value > 0 and time between #{startDate} and #{endDate}")
    Integer selectRechargeCountByDate(@Param("startDate") String startDate, @Param("endDate") String endDate );


    @Select("select value from tb_record where value < 0 and time between #{startDate} and #{endDate}")
    Integer selectConsumeByDate(@Param("startDate") String startDate, @Param("endDate") String endDate );

    @Select("select value from tb_record where value > 0 and time between #{startDate} and #{endDate}")
    Integer selectRechargeByDate(@Param("startDate") String startDate, @Param("endDate") String endDate );


    @Select("SELECT username, sum(value) as total_value from tb_record where value > 0 GROUP BY username ORDER BY SUM(value) DESC LIMIT 5")
    List<Map<String,Object>> selectRechargeLimit5();

    @Select("SELECT username, -1*sum(value) as total_value from tb_record where value < 0 GROUP BY username ORDER BY -1*SUM(value) DESC LIMIT 5")
    List<Map<String,Object>> selectConsumeLimit5();

    @Select("select * from tb_record where username = #{username}")
    List<Record> selectListByUsername(@Param("username") String username);

    @Select("select count(*) from tb_record")
    int selectCountRecord();
}
