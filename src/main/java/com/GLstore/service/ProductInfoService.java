package com.GLstore.service;

import com.GLstore.pojo.ProductInfo;
import com.GLstore.pojo.vo.ProductInfoVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ProductInfoService {
    //显示全部商品不分页
    List<ProductInfo> getAll();

    //分页插件功能的开发
    //pageNum当前页的数据，pageSize每一页能够分几条
    PageInfo splitPage(int pageNum,int pageSize);

    //增加商品
    int save(ProductInfo info);

    //根据主键ID进行查询商品
    ProductInfo getByID(int pid);

    //更新商品
    int update(ProductInfo info);

    //删除单个商品
    int delete(int pid);

    //批量删除商品
    int deleteBatch(String []ids);

    //多条件商品查询功能
    List<ProductInfo> selectCondition(ProductInfoVo vo);

    //多条件查询分页
    public PageInfo splitPageVo(ProductInfoVo vo,int pageSize);

}
