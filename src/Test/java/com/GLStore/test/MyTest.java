package com.GLStore.test;

import com.GLstore.mapper.ProductInfoMapper;
import com.GLstore.pojo.ProductInfo;
import com.GLstore.pojo.vo.ProductInfoVo;
import com.GLstore.utils.MD5Util;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

//实现测试功能
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations =
        {"classpath:applicationContext_dao.xml","classpath:applicationContext_service.xml"})
//实现文件注入
public class MyTest {
    //测试MD5密文传输
    @Test
    public void MD5(){
        String GL= MD5Util.getMD5("123456");
        System.out.println("加密后的密码为："+GL);
    }

/*
//Spring自动注入
    @Autowired
    ProductInfoMapper mapper;
    @Test
    public void testSelectCondition(){
        ProductInfoVo vo=new ProductInfoVo();
        vo.setPname("苹果");
        vo.setTypeid(2);
        vo.setLprice(2500.0);
        vo.setHprice(15000.0);
        List<ProductInfo> list=mapper.selectCondition(vo);
        list.forEach(productInfo -> System.out.println(productInfo));
    }*/
}
