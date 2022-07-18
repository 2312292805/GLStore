package com.GLstore.service.impl;

import com.GLstore.mapper.AdminMapper;
import com.GLstore.pojo.Admin;
import com.GLstore.pojo.AdminExample;
import com.GLstore.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//时间业务接口连接
@Service
//实现Service,确保在调用方法的时候能顾创建相对应的对象
public class AdminServiceImpl implements com.GLstore.service.AdminService {
    //在业务逻辑层中，一定存在数据库访问层的对象
    //利用Spring容器的AutoWire属性，新建对象
    @Autowired
    AdminMapper adminMapper;

    @Override
    public Admin login(String name, String pwd) {
        //根据传入的用户名，到达相对应的数据库内部查询相对应的用户对象
        //如果有条件，那么一定要创建AdminExample的对象，用来封装条件
        AdminExample example = new AdminExample();
        /*如何添加条件
        select (用户列表） from admin where a_name="admin'
         */
        //添加用户名的条件,进行字符串的拼接，进行带条件查询
        example.createCriteria().andANameEqualTo(name);
        List<Admin> list= adminMapper.selectByExample(example);
        //查询用户是否已经查询到
        if(list.size()>0){
            Admin admin= list.get(0);
            //根据查询到的用户对象，在进行密码比对,（密码是以密文的形式存在的）
            /*
            admin.getApass=>c984aed014aec7623a54f0591da07a85fd4b762d
            pwd=>000000
            在进行密码比对的时候，要将传入的pwd进行MD5加密，在于数据库中查到的对象的密码进行对比
             */
            String miPWD= MD5Util.getMD5(pwd);
            if(miPWD.equals(admin.getaPass())){
                return admin;
            }
        }
        return null;
    }
}
