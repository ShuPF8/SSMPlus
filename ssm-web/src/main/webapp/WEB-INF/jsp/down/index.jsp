<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>execl导出</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
   <table>
        <thead>
            <tr>
                <th>序号</th>
                <th>姓名</th>
                <th>年龄</th>
                <th>性别</th>
                <th>手机</th>
                <th>密码</th>
            </tr>
        </thead>
       <tbody>
            <c:forEach items="${data}" var="ite">
                <tr>
                    <td>${ite.id}</td>
                    <td>${ite.name}</td>
                    <td>${ite.age}</td>
                    <td>${ite.sex}</td>
                    <td>${ite.phone}</td>
                    <td>${ite.pwd}</td>
                </tr>
            </c:forEach>
       </tbody>
   </table>
  <a href="down">导出</a>
  </body>
</html>
