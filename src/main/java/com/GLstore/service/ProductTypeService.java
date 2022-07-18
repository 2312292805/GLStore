package com.GLstore.service;

import com.GLstore.pojo.ProductInfo;
import com.GLstore.pojo.ProductType;
import com.github.pagehelper.PageInfo;
import java.util.List;

public interface ProductTypeService {
    //商品类别的查询功能
    List<ProductType> getAll();

    //分页功能的实现
    PageInfo splitPage(int pageNum,int pageSize);

    //实现页面更新功能
    int save(ProductInfo info);

    //按主键id进行查询商品
    ProductInfo getByID(int pid);
}
