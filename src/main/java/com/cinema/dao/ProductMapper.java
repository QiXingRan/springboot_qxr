package com.cinema.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cinema.domain.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProductMapper extends BaseMapper<Product> {
    @Select("select * from tb_product where object like CONCAT('%', #{object}, '%')")
    List<Product> selectProductByProduct(@Param("object") String object);
}
