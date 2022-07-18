package com.GLstore.controller;
//控制层对象
import com.GLstore.pojo.Admin;
import com.GLstore.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
//声明控制器对象，交由Spring进行实现

@Controller
//注解为后台管理员
@RequestMapping("/admin")
public class AdminAction {
    //在所有的界面层，都是存在业务逻辑层的对象
    //在Spring内部，自动注入对象
    @Autowired
    AdminService adminService;
    //实现登录判断功能，并且进行相对应的跳转
    //添加注解对象
    @RequestMapping("/login")
    //查询login.jsp对象，寻找用户名的密码的’name‘属性对象
    public String login(String name, String pwd, HttpServletRequest request){
        Admin admin= adminService.login(name,pwd);
        //登录业务逻辑的判断
        if(admin!=null){
            //登录成功的页面
            request.setAttribute("admin",admin);//登陆成功时，出现的提示
            return "main";//如果登陆成功，那么直接跳转到main.jsp页面，进行接下来的操作

        }
        else{
            //登录失败的页面
            request.setAttribute("errmsg","用户名或密码不正确");
            //先修改无论密码正确与否，都可以跳转到main页面
            return "login";
        }
    }
}
