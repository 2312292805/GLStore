<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@page import="java.util.*" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">

    <script type="text/javascript">
        if ("${msg}" != "") {
            alert("${msg}");
        }
    </script>

    <c:remove var="msg"></c:remove>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bright.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/addBook.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
    <title></title>
</head>
<script type="text/javascript">
    function allClick() {
        //取得全选复选框的选中未选中状态
        var flag=$("#all").prop("checked");
        //将此状态赋值给每个商品列表里的复选框，遍历同一个页面的5个复选框，进行复选框的赋值
        $("input[name='ck']").each(function () {
            this.checked=flag;
        });
    }

    function ckClick() {
        //取得所有name=ck的被选中的复选框
        var fiveLength=$("input[name='ck']").length;//获取复选框数组的长度
        //取得所有name=ck的复选框
        var checkedLength=$("input[name='ck']:checked").length;//获取被选中的复选框的数组长度
        //比较
        if(fiveLength == checkedLength){
            $("#all").prop("checked",true);
        }else
        {
            $("#all").prop("checked",false);
        }
    }
</script>
<body>
<div id="brall">
    <div id="nav">
        <p>商品管理>商品列表</p>
    </div>
    <div id="condition" style="text-align: center">
        <form id="myform">
            商品名称：<input name="pname" id="pname">&nbsp;&nbsp;&nbsp;
            商品类型：<select name="typeid" id="typeid">
            <option value="-1">请选择</option>
            <c:forEach items="${typeList}" var="pt">
                <option value="${pt.typeId}">${pt.typeName}</option>
            </c:forEach>
        </select>&nbsp;&nbsp;&nbsp;
            价格：<input name="lprice" id="lprice">-<input name="hprice" id="hprice">
            <input type="button" value="查询" onclick="condition()">
        </form>
    </div>
    <br>


    <!--实现真正分页操作的完整流程，包括添加商品和批量删除按钮，都是跟着刷新-->
    <div id="table">

        <c:choose>
            <c:when test="${list.size()!=0}">

                <div id="top">
                    <input type="checkbox" id="all" onclick="allClick()" style="margin-left: 50px">&nbsp;&nbsp;全选
                    <a href="${pageContext.request.contextPath}/admin/addproduct.jsp">

                        <input type="button" class="btn btn-warning" id="btn1"
                               value="新增商品">
                    </a>
                    <input type="button" class="btn btn-warning" id="btn1"
                           value="批量删除" onclick="deleteBatch()">
                </div>
                <!--显示分页后的商品-->
                <div id="middle">
                    <table class="table table-bordered table-striped">
                        <tr>
                            <th></th>
                            <th>商品名</th>
                            <th>商品介绍</th>
                            <th>定价（元）</th>
                            <th>商品图片</th>
                            <th>商品数量</th>
                            <th>操作</th>
                        </tr>
                        <%--foreach表示循环所有商品，var表示某一个商品信息--%>
                        <c:forEach items="${info.list}" var="p">
                            <tr>
                                <%--checkbox表示商品前端的的复选框，适用于批量删除数据--%>
                                <td valign="center" align="center"><input type="checkbox" name="ck" id="ck" value="${p.pId}" onclick="ckClick()"></td>
                                <td>${p.pName}</td>
                                <td>${p.pContent}</td>
                                <td>${p.pPrice}</td>
                                <td><img width="55px" height="45px"
                                         src="${pageContext.request.contextPath}/image_big/${p.pImage}"></td>
                                <td>${p.pNumber}</td>
                                    <%--<td><a href="${pageContext.request.contextPath}/admin/product?flag=delete&pid=${p.pId}" onclick="return confirm('确定删除吗？')">删除</a>--%>
                                    <%--&nbsp;&nbsp;&nbsp;<a href="${pageContext.request.contextPath}/admin/product?flag=one&pid=${p.pId}">修改</a></td>--%>
                                <td>
                                    <button type="button" class="btn btn-info "
                                            onclick="one(${p.pId})">编辑
                                    </button>
                                    <button type="button" class="btn btn-warning" id="mydel"
                                            onclick="del(${p.pId})">删除
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                    <!--分页栏-->
                    <div id="bottom">
                        <div>
                            <nav aria-label="..." style="text-align:center;">
                                <ul class="pagination">
                                    <li>
                                            <%--                                        <a href="${pageContext.request.contextPath}/prod/split.action?page=${info.prePage}" aria-label="Previous">--%>
                                        <a href="javascript:ajaxsplit(${info.prePage})" aria-label="Previous">
                                            <%--实现向前翻页的功能--%>
                                            <span aria-hidden="true">«</span></a>
                                    </li>
                                    <c:forEach begin="1" end="${info.pages}" var="i">
                                        <%--<li>${i}</li>--%>
                                        <c:if test="${info.pageNum==i}">
                                            <li>
                                                <a href="javascript:ajaxsplit(${i})"
                                                   style="background-color: #0e90d2">${i}</a>
                                                <%--如果是跳转到当前页面，则相对应的页数背景颜色会发生变化，进而可以进行区分--%>
                                            </li>
                                        </c:if>
                                        <c:if test="${info.pageNum!=i}">
                                            <li>
                                                <a href="javascript:ajaxsplit(${i})">${i}</a>
                                            </li>
                                        </c:if>
                                    </c:forEach>
                                    <li>
                                        <%--  <a href="${pageContext.request.contextPath}/prod/split.action?page=1" aria-label="Next">--%>
                                        <a href="javascript:ajaxsplit(${info.nextPage})" aria-label="Next">
                                            <span aria-hidden="true">»</span></a><%--实现向后进行跳转的超链接--%>
                                    </li>
                                    <li style=" margin-left:150px;color: #0e90d2;height: 35px; line-height: 35px;">总共&nbsp;&nbsp;&nbsp;<font
                                            style="color:orange;">${info.pages}</font>&nbsp;&nbsp;&nbsp;页&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                        <c:if test="${info.pageNum!=0}">
                                            当前&nbsp;&nbsp;&nbsp;<font
                                            style="color:orange;">${info.pageNum}</font>&nbsp;&nbsp;&nbsp;页&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                        </c:if>
                                        <!--如果是在最后一页点击向后翻的按钮，那么对于跳转后的页面，需要设置直接跳转到第一
                                        页，而不是自动跳转到第0页-->
                                        <c:if test="${info.pageNum==0}">
                                            当前&nbsp;&nbsp;&nbsp;<font
                                            style="color:orange;">1</font>&nbsp;&nbsp;&nbsp;页&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                        </c:if>
                                    </li>
                                </ul>
                            </nav>
                        </div>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <div>
                    <h2 style="width:1200px; text-align: center;color: orangered;margin-top: 100px">暂时没有符合条件的商品！</h2>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</body>

<script type="text/javascript">
    function mysubmit() {
        $("#myform").submit();
    }

    //批量删除
    function deleteBatch() {
            //取得所有被选中删除商品的pid,根据数组长度进行判断是否有选中商品,:check加过滤器
            var cks=$("input[name='ck']:checked");
            if(cks.length==0){
                //alter弹框提示，请选择要删除的商品
                alert("请选择将要删除的商品！");
            }else{
                var str="";
                var id="";
                // 有选中的商品，则取出每个选中商品的ID，拼提交的ID的数据
                if(confirm("您确定删除"+cks.length+"条商品吗？")){
                //进行提交商品ID字符串数组的拼接
                    $.each(cks,function () {
                        pid=$(this).val(); //22 33,获取每一个被选中商品得ID
                        //进行费控判断，避免出错
                        if(pid!=null){
                            str += pid+",";  //22,33,44
                        }
                    });
                    //alert(str);
                    //发送请求到服务器端,发送Ajax请求，进行删除操作
                    $.ajax({
                        url:"${pageContext.request.contextPath}/prod/deleteBatch.action",
                        data:{"pids":str},
                        type:"post",
                        dataType:"text",
                        success:function (msg){
                            alter(msg);//回显提示信息，显示批量删除成功或者失败
                            //将页面上显示的商品数据进行重新加载
                            $("#table").load("http://localhost:8080/admin/product.jsp #table");
                        }
                    });
                }
        }
    }
    //单个删除
    function del(pid) {
        if (confirm("确定删除吗")) {
          //向服务器提交请求完成删除
            $.ajax({
                url:"${pageContext.request.contextPath}/prod/delete.action",
                data:{"pid":pid},
                type:"post",
                dataType:"text",
                success:function (msg) {
                    alert(msg);//实现弹窗显示的功能
                    //获取当前容器的信息
                   // alert("单个删除成功")
                    $("#table").load("http://localhost:8080/admin/product.jsp #table");
                }
            })
        }
    }

    function one(pid,ispage) {
        //向服务器进行提交请求，传递商品id
        location.href = "${pageContext.request.contextPath}/prod/one.action?pid=" + pid + "&page=" + ispage;
    }
</script>
<!--分页的AJAX实现-->
<script type="text/javascript">
    function ajaxsplit(page) {
        //异步ajax分页请求
        //向服务器端发出ajax请求，请求page页面中的所有数据，在当前页面上进行显示

        $.ajax({
        url:"${pageContext.request.contextPath}/prod/ajaxSplit.action",
            //提交不同的数据之间需要使用逗号隔开
            data:{"page":page},
            type:"post",//post请求能够实现安全提交，只需要进行跳转，而无需进行返回值的操作
            success:function () {
                //重新加载分页显示的组件table
                //location.href---->http://localhost:8080/admin/login.action
                //#table表示重新加载一遍table容器
                $("#table").load("http://localhost:8080/admin/product.jsp #table");
            }
        })
    };

    function condition(){
        //取出查询的条件
        var pname=$("#pname").val();
        var typeid=$("#typeid").val();
        var lprice=$("#lprice").val();
        var hprice=$("#hprice").val();
        //实现异步Ajax功能的请求
        $.ajax({
            type:"post",
            url:"${pageContext.request.contextPath}/prod/ajaxSplit.action",
            data:{"pname":pname,"typeid":typeid,"lprice":lprice,"hprice":hprice},
            success:function () {
                //刷新显示数据的容器
                $("#table").load("http://localhost:8080/admin/product.jsp #table");
            }
        });
    }
</script>

</html>