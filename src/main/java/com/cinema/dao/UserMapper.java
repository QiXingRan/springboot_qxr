package com.cinema.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cinema.domain.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    public List<String> getRoleNameByUserId(Integer userId);

    List<User> selectByUsername(String username);

    void updateUserByUsername(User user);

    @Select("SELECT number from tb_user where username = #{username}")
    int selectNumberByUsername(@Param("username") String username);

    @Update("UPDATE tb_user set number = number + #{integral} where username = #{username}")
    void updateNumberByUsername(@Param("username") String username, @Param("integral") int integral);

    @Select("select * from tb_user where username = #{username}")
    List<User> selectListByUsername(@Param("username") String username);

    @Delete("delete from tb_user where username = #{username}")
    void deleteUserByUsername(@Param("username") String username);

    @Select("select role_name,password from tb_role")
    Map<String,String> selectAdmin();



}
