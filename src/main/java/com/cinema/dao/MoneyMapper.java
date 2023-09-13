package com.cinema.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cinema.domain.Money;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface MoneyMapper extends BaseMapper<Money> {
    @Update("UPDATE tb_money set money = money + #{integral} where username = #{username}")
    void updateMoneyByUsername(@Param("username") String username, @Param("integral") int integral);

    @Update("UPDATE tb_money set money = #{integral} where username = #{username}")
    void changeMoneyByUsername(@Param("username") String username, @Param("integral") int integral);

    @Update("delete from tb_money where username = #{username}")
    void removeByUsername(@Param("username") String username);

    @Update("UPDATE tb_money set money = money - #{integral} where username = #{username}")
    void updateMoneyByUsername2(@Param("username") String username, @Param("integral") int integral);

    @Select("SELECT money from tb_money where username = #{username}")
    int selectMoneyByUsername(@Param("username") String username);

    @Select("SELECT username, sum(money) as total_money from tb_money GROUP BY username ORDER BY SUM(money) DESC LIMIT 5")
    List<Map<String,Object>> selectTopLimit5();

    @Select("select sum(money) from tb_money")
    int selectSumMoney();

    @Select("select max(money) from tb_money")
    int selectMaxMoney();
}
