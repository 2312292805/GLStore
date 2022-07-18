package com.GLstore.listener;

import com.GLstore.pojo.ProductType;
import com.GLstore.service.ProductTypeService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;

//实现全局监听的功能
@WebListener//实现监听器的注册
public class ProductTypeListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        /*
        获取所有商品的列表，注入监听器进行监听
        由于与其他业务层使用同一个监听器，因此不能够自动添加，需要手动注入,
        手工从Spring容器内部取出ProductTypeServiceImpl的对象
        由于需要同时添加applicationContext_dao.xml和applicationContext_service.xml两个配置文件
        所以需要进行通配符加载applicationContext_*.xml
        */
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext_*.xml");
        //获取所有的商品类别
        ProductTypeService productTypeService =(ProductTypeService) context.getBean("ProductTypeServiceImpl");
        //创建集合进行存储,用于储存所有商品类别信息
        List<ProductType> typeList= productTypeService.getAll();
        //放入全局作用域中，供新增页面，修改页面，前台的查询页面提供全部商品类别集合
        servletContextEvent.getServletContext().setAttribute("typeList",typeList);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
