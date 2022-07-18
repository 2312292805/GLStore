package com.GLstore.controller;

import com.GLstore.pojo.ProductInfo;
import com.GLstore.pojo.vo.ProductInfoVo;
import com.GLstore.service.ProductInfoService;
import com.github.pagehelper.PageInfo;
import com.GLstore.utils.FileNameUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

//商业界面层的开发
@Controller
//路径映射
@RequestMapping("/prod")
public class ProducInfoAction {
    //确定每一页呈现的数据条数
    public static final int PAGE_SIZE=5;
    //异步上传的图片名称
    String saveFileName="";
    //切记：在界面层中，一定会有业务逻辑层的对象
    @Autowired
    ProductInfoService productInfoService;
    //显示全部商品不分页
    @RequestMapping("/getAll")
    public String getAll(HttpServletRequest request){
        List<ProductInfo> list= productInfoService.getAll();
        request.setAttribute("list",list);
        return "product";
    }
    //显示第一页的5条记录，实现分页展示功能
    @RequestMapping("/split")
    public String split(HttpServletRequest request){
        //获取第一页的数据,PAGE_SIZE在全局变量中以及定义
        PageInfo info= productInfoService.splitPage(1,PAGE_SIZE);
        request.setAttribute("info",info);
        return "product";
    }
    //Ajax分页翻页处理
    @ResponseBody
    @RequestMapping("/ajaxSplit")
    public void ajaxSplit(ProductInfoVo vo, HttpSession session){
        //取得当前page参数的页面数据,所有的页面数据都是存在pageInfo内部
        PageInfo info= productInfoService.splitPageVo(vo,PAGE_SIZE);
        session.setAttribute("info",info);
    }
    //多条件查询功能的实现
    //Ajax功能请求实现,以及条件查询路径
    @ResponseBody
    @RequestMapping("/condition")
    //获取得到的对象都存储到session中
    public void condition(ProductInfoVo vo,HttpSession session){
        //实现异步Ajax的访问
        List<ProductInfo> list= productInfoService.selectCondition(vo);
        session.setAttribute("list",list);
    }
    //异步ajax文件上传处理
    @ResponseBody
    @RequestMapping("ajaxImg")
    //MultipartFile对于上传的文件数据流进行接收,HttpServletRequest表示获取相对应的文件路径
    public Object ajaxImg(MultipartFile pimage,HttpServletRequest request){
        //提取生成文件名UUID+上传的图片后缀
        //pimage.getOriginalFilename()表示上传图片的原始名称，
        // getFileType表示图片的后缀名称，
        // getUUIDFileName表示生成的32位文件名的名称
        saveFileName= FileNameUtil.getUUIDFileName()+FileNameUtil.getFileType(pimage.getOriginalFilename());
        try {
        //得到图片的存储路径
        //获取图片的物理路径，本地图片一般将其存放于image_big的文件夹内部
        String path=request.getServletContext().getRealPath("/image_big");
        //转存
        //path:表示项目的物理路径，即：D:\Java Project\GLStore
        //File.separator表示路径之间的反斜杠/
        //saveFileName表示存储的32位文件名称
            pimage.transferTo(new File(path+File.separator+saveFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //返回客户端的JSON对象，封装图片的路径，为了实现页面能够实现立即回显
        JSONObject object=new JSONObject();
        object.put("imgurl",saveFileName);
        return object.toString();
    }
//新增信息的添加以及信息的提取，增加以后的页面跳转
    @RequestMapping("/save")
    public String save(ProductInfo info,HttpServletRequest request){
        info.setpImage(saveFileName);
        //获取上架日期，是根据系统的时间确定的
        info.setpDate(new Date());
        //info对象中有表单提交上来的5个数据，有异步ajax上传的图片数据名称，有上架时间的数据
        int num=-1;
        try {
            num=productInfoService.save(info);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //添加提示弹框信息，用于确认添加是否成功
        if(num>0){
            request.setAttribute("msg","增加成功!");
        }else{
            request.setAttribute("msg","增加失败!");
        }
        //清空saveFileName，为了下次增加或者修改异步Ajax的上传处理
        saveFileName="";

        //增加成功以后应该是重新访问数据库，所以跳转到分页显示的Action上

        return "forward:/prod/split.action";
    }
    //查询一个商品
    @RequestMapping("/one")
    public String one(int pid, Model model){
        ProductInfo info= productInfoService.getByID(pid);
        model.addAttribute("prod",info);
        return "update";
    }
    //实现更新商品的操作
    @RequestMapping("update")
    //request返回客户端的参数，info填入自动注入参数
    public String update(ProductInfo info,HttpServletRequest request){
        //由于ajax的异步图片上传，如果有上传过，则saveFileName里面具有上传上来的名称，
        // 否则则为空串，实体类info使用隐藏表单域提供的pImage原始图片的名称
        if(!saveFileName.equals("")){
            //如果不为空，那么贱新的图片名称加载进原先的图片名称
            info.setpImage(saveFileName);
        }
        //完成更新操作
        int num=-1;
        //进行Try,Catch捕获
        try {
            num= productInfoService.update(info);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(num>0){
            //此时表示更新成功，添加提示信息
            request.setAttribute("msg","更新成功！");
        }
        else{
            //此时表示更新失败，添加提示信息
            request.setAttribute("msg","更新失败！");
        }
        //处理完成以后，saveFileName里面可能还是包含有数据，而下一次
        // 更新仍然需要以saveFileName是否为空作为判断依据，所以需要再次置空
        saveFileName="";
        //redirect的功能会与request出现冲突，因此回显msg信息没办法显示，使用forward能够解决此问题
        return "forward:/prod/split.action";
    }
    @RequestMapping("/delete")
    //pid按照主键进行删除操作，request实现返回弹窗操作
    public String delete(int pid,HttpServletRequest request){
        int num=-1;
        //进行异常捕获处理
        try {
            num= productInfoService.delete(pid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(num>0){
            request.setAttribute("msg","删除成功！");
        }else{
            request.setAttribute("msg","删除失败！");
        }
        //删除结束后跳转到分页显示页面
        return "forward:/prod/deleteAjaxSplit.action";
    }
    //实现删除弹框功能
    @ResponseBody
    //实现响应流中的中文在弹框中进行显示value = "/deleteAjaxSplit",produces = "text/html;charset=UTF-8“
    @RequestMapping(value = "/deleteAjaxSplit",produces = "text/html;charset=UTF-8")
    //通过session进行数据的存储
    public Object deleteAjaxSplit(HttpServletRequest request){
        //获取第一页的所有数据
        PageInfo info= productInfoService.splitPage(1,PAGE_SIZE);
        request.getSession().setAttribute("info",info);
        return request.getAttribute("msg");
    }
    //批量删除商品
    @RequestMapping("/deleteBatch")
    //request用于传递当前商品是否更新数据
    public String deleteBatch(String pids,HttpServletRequest request){
        //将上传上来的字符串截开，形成商品id的字符数组
        String []ps=pids.split(",");
        int num= productInfoService.deleteBatch(ps);
        try {
            if(num>0){
                request.setAttribute("msg","批量删除成功！");
            }else{
                request.setAttribute("msg","批量删除失败！");
            }
        } catch (Exception e) {
            request.setAttribute("msg","当前商品不可删除！");
        }
        //返回与原先的单个删除操作实现功能一致
        return "forward:/prod/deleteAjaxSplit.action";
    }
}
