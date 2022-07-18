package com.GLstore.service.impl;

import com.GLstore.mapper.ProductTypeMapper;
import com.GLstore.pojo.ProductInfo;
import com.GLstore.pojo.ProductType;
import com.GLstore.pojo.ProductTypeExample;
import com.GLstore.service.ProductTypeService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
//提交给Spring框架进行管理
//提交该类名为ProductTypeServiceImpl,后续调用直接使用这个名字
@Service("ProductTypeServiceImpl")

public class ProductTypeServiceImpl implements ProductTypeService {
    //实现商品封装类的功能实现
    //在业务逻辑层方面，必须具备数据访问层的对象，交由Spring框架来自动注入
    @Autowired
    ProductTypeMapper productTypeMapper;

    @Override
    public List<ProductType> getAll() {
        return productTypeMapper.selectByExample(new ProductTypeExample());

    }

    @Override
    public PageInfo splitPage(int pageNum, int pageSize) {
        return null;
    }

    @Override
    public int save(ProductInfo info) {
        return 0;
    }

    @Override
    public ProductInfo getByID(int pid) {
        return null;
    }
}
