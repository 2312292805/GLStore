<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--导入标准标签库，核心标签库-->
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title></title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/addBook.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath }/js/ajaxfileupload.js"></script>
	</head>
    <script type="text/javascript">
        function fileChange(){//注意：此处不能使用jQuery中的change事件，因此仅触发一次，因此使用标签的：onchange属性
           alert("change");
            $.ajaxFileUpload({
                url: "${pageContext.request.contextPath}/prod/ajaxImg.action",//用于文件上传的服务器端请求地址
                secureuri: false,//一般设置为false,用于定义是否需要遵循安全协议
                fileElementId: 'pimage',//文件上传控件的id属性，跟上传标签内部的id保持一致
				// <input type="file" id="pimage" name="pimage" />
                dataType: 'json',//返回值类型 一般设置为json，用于解析src的名称，还有图片的名称
                success: function(obj) //服务器成功响应处理函数
                {
                	alert(obj);
                    $("#imgDiv").empty();  //清空原有数据
                    //创建img 标签对象
                    var imgObj = $("<img>");
                    //给img标签对象追加属性
                    imgObj.attr("src","/image_big/"+obj.imgurl);//回显图片的路径
                    imgObj.attr("width","100px");//设置回显图片的长宽
                    imgObj.attr("height","100px");
                    //将图片img标签追加到imgDiv末尾
                    $("#imgDiv").append(imgObj);
                    //将图片的名称（从服务端返回的JSON中取得）赋值给文件本框
                    //$("#imgName").html(data.imgName);
                },
                error: function (e)//服务器响应失败处理函数
                {
                    alert(e.message);
                }
            });
        }
    </script>
	<body>
	<!--取出上一个页面上带来的page的值-->

		<div id="addAll">
			<div id="nav">
				<p>商品管理>新增商品</p>
			</div>

			<div id="table">
				<!--表单提交的行为-->
				<form id ="myform" action="${pageContext.request.contextPath}/prod/save.action" >
					<table>
						<tr>
							<td class="one">商品名称</td>
							<td><input type="text" name="pName" class="two"></td>
						</tr>
						<!--错误提示-->
						<tr class="three">
							<td class="four"></td>
							<td><span id="pnameerr"></span></td>
						</tr>
						<tr>
							<td class="one">商品介绍</td>
							<td><input type="text" name="pContent" class="two"></td>
						</tr>
						<!--错误提示-->
						<tr class="three">
							<td class="four"></td>
							<td><span id="pcontenterr"></span></td>
						</tr>
						<tr>
							<td class="one">定价</td>
							<td><input type="number" name="pPrice" class="two"></td>
						</tr>
						<!--错误提示-->
						<tr class="three">
							<td class="four"></td>
							<td><span id="priceerr"></span></td>
						</tr>
						
						<tr>
							<td class="three">图片介绍</td>
							<!--imgDiv表示上传图片以后回显图片的部分-->
                            <td><br><div id="imgDiv" style="display:block;width: 40px;height: 100px;"></div>
								<%--<input type="file" id="pimage" name="pimage" onchange="fileChange()">--%>
								<!--pimage表示图片的名称，name表示上传到服务器端我们需要接收到的图片名称-->
                            <input type="file" id="pimage" name="pimage" onchange="fileChange()">
								<span id="imgName"></span><br>
                            </td>
						</tr>
						<tr class="three">
							<td class="four"></td>
							<td><span></span></td>
						</tr>
						
						<tr>
							<td class="one">总数量</td>
							<td><input type="number" name="pNumber" class="two"></td>
						</tr>
						<!--错误提示-->
						<tr class="three">
							<td class="four"></td>
							<td><span id="numerr"></span></td>
						</tr>
						
						
						<tr>
							<td class="one">类别</td>
							<td>
								<!--name属性是需要注入到服务器端的，所以需要我们直接通过ProductInfo里面进行查询相对应的名称-->
								<select name="typeId">
									<!--items属性的值，是需要与监听器里面的属性值保持一致（即typeList），要不然可能导致没办法使用监听器内部的数据-->
									<c:forEach items="${typeList}" var="type">
										<!--使用循环，使得所有的商品信息都能够注入到商品类别里面
										<option value="商品类别的id">商品类别的名称</option>
										-->
										<option value="${type.typeId}">${type.typeName}</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<!--错误提示-->
						<tr class="three">
							<td class="four"></td>
							<td><span></span></td>
						</tr>

						<tr>
							<td>
								<input type="submit" value="提交" class="btn btn-success">
							</td>
							<td>
								<input type="reset" value="取消" class="btn btn-default" onclick="myclose(${param.page})">
								<script type="text/javascript">
									function myclose(ispage) {
										window.location="${pageContext.request.contextPath}/prod/split.action?page="+ispage;
									}
								</script>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</body>
</html>