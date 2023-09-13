package com.cinema.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cinema.controller.utils.Result;
import com.cinema.dao.ProductMapper;
import com.cinema.domain.Product;
import com.cinema.domain.User;
import com.cinema.service.IProductService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/product")
public class ProductController {
    @Resource
    IProductService productService;

    @Resource
    ProductMapper productMapper;

    @PostMapping("/add")
    public Result<?> addProduct(@RequestBody Product product){
        product.setObject(product.getObject());
        product.setPrice(product.getPrice());
        product.setDescrible(product.getDescrible());
        productService.save(product);
        return Result.success("新增成功");
    }

    @GetMapping("/all")
    public Result<List<Product>> getAllProduct(){
        List<Product> list = productService.list();
        return Result.success(list,"查询成功");
    }

    @GetMapping("/list")
    public Result<Map<String,Object>> getUserList(@RequestParam(value = "object",required = false) String object,
                                                  @RequestParam(value = "pageNo") Long pageNo,
                                                  @RequestParam(value = "pageSize") Long pageSize){

        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasLength(object),Product::getObject,object);
        wrapper.orderByDesc(Product::getObject);

        Page<Product> page = new Page<>(pageNo,pageSize);
        productService.page(page,wrapper);

        Map<String,Object> data = new HashMap<>();
        data.put("total",page.getTotal());
        data.put("rows",page.getRecords());

        return Result.success(data);
    }

    @PutMapping
    public Result<?> updateProduct(@RequestBody Product product){
        product.setObject(product.getObject());
        productService.updateById(product);
        return Result.success("修改成功");
    }

    @DeleteMapping("/{object}")
    public Result<User> deleteUserById(@PathVariable("object") String object){
        productService.removeById(object);
        return Result.success("删除成功");
    }

    //模糊搜索 /product/select
    @GetMapping("/select")
    public Result<List<Product>> selectProductByProduct(@RequestParam String object){
        List<Product> result = productMapper.selectProductByProduct(object);
        return Result.success(result,"true");
    }

    @GetMapping("/count")
    public Result selectCountOfProduct(){
        productService.count();
        return Result.success(productService.count());
    }


}
