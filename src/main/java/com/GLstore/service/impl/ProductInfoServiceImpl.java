package com.GLstore.service.impl;

import com.GLstore.mapper.ProductInfoMapper;
import com.GLstore.pojo.ProductInfo;
import com.GLstore.pojo.ProductInfoExample;
import com.GLstore.pojo.vo.ProductInfoVo;
import com.GLstore.service.ProductInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//实现类功能
@Service
//由service进行创建对象
public class ProductInfoServiceImpl implements ProductInfoService {

    //业务逻辑层中必定有数据层的对象,自动注入
    @Autowired
    ProductInfoMapper productInfoMapper;

    @Override
    public List<ProductInfo> getAll() {
        //没有条件的直接查询就比较简单，如果是有条件的查询，那么必须进行相对应的正则表达式的方式进行查询
        return productInfoMapper.selectByExample(new ProductInfoExample());
    }
/*
-- 当前是第二页的内容，显示分页以后的第二页的信息
-- SELECT * FROM product_info limit 起始记录数=((当前页-1)*每页的条数),每页取几条
SELECT * FROM product_info limit 10,5
 */
    @Override
    public PageInfo splitPage(int pageNum,int pageSize) {
        //page分页插件使用PageHelper工具类进行完成分页设置
        PageHelper.startPage(pageNum,pageSize);//pageNum当前页面的页数，pageSize一个页面的总条数

        //进行PageInfo的数据封装
        //进行有条件的查询操作，必须要创建ProductInfoExample对象
        ProductInfoExample example=new ProductInfoExample();
        //设置排序的方式，按照主键降序的方式进行排序
        //SELECT * FROM product_info order by p_id DESC
        example.setOrderByClause("p_id desc");
        //上述实现排序的功能，调用setOrderByClause的方法
        //设置好排序以后，需要取集合的操作，重点：在去集合之前，一定要注意先配置PageHelper
        //PageHelper.startPage(pageNum,pageSize);
        List<ProductInfo> list= productInfoMapper.selectByExample(example);
        //将查询到的集合，封装到PageInfo对象内部
        PageInfo<ProductInfo> pageInfo=new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public int save(ProductInfo info) {
        return productInfoMapper.insert(info);
    }
    @Override
    public ProductInfo getByID(int pid){
        return productInfoMapper.selectByPrimaryKey(pid);
    }
    @Override
    public int update(ProductInfo info){
        return productInfoMapper.updateByPrimaryKey(info);
    }
//实现实现类
    @Override
    public int delete(int pid) {
        //根据主键进行删除的功能
        return productInfoMapper.deleteByPrimaryKey(pid);
    }

    //重写批量删除的方法
    @Override
    public int deleteBatch(String[] ids) {

        return productInfoMapper.deleteBatch(ids);
    }

    @Override
    public List<ProductInfo> selectCondition(ProductInfoVo vo) {

        return productInfoMapper.selectCondition(vo);
    }

    @Override
    public PageInfo<ProductInfo> splitPageVo(ProductInfoVo vo, int pageSize) {
        //取出集合之前，需要先设置PageHelper.startPage（）属性
        PageHelper.startPage(vo.getPage(),pageSize);
        List<ProductInfo> list= productInfoMapper.selectCondition(vo);
        return new PageInfo<>(list);
    }
}
