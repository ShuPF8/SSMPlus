<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE>
<html>
<head>
    <base href="<%=basePath%>">
    <title>接口验证</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

    <meta name="viewport" content="width=device-width, initial-scale=1.0,  maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/js/jquery-1.10.1.js"></script>
    <script src="${pageContext.request.contextPath}/js/crypto-js.js"></script>
    <script src="${pageContext.request.contextPath}/js/aes.js"></script>
    <script src="${pageContext.request.contextPath}/js/mode-ecb.js"></script>
    <script src="${pageContext.request.contextPath}/js/verify.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

</head>

<body>
<div class="container">
    <div class="row">
        <form class="form-horizontal" role="form" id="form">
            <div class="form-group">
                <label for="firstname" class="col-sm-2 control-label">名字</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" name="name" id="firstname" placeholder="请输入名字">
                </div>
            </div>
            <div class="form-group">
                <label for="sex" class="col-sm-2 control-label">性别</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" name="sex" id="sex" placeholder="请输入名字">
                </div>
            </div>
            <div class="form-group">
                <label for="age" class="col-sm-2 control-label">年龄</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" name="age" id="age" placeholder="请输入名字">
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="button" onclick="dl()" class="btn btn-default">登录</button>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>
<script>
    <%--(function() {--%>
        <%--var dynamicScript = document.createElement('script');--%>
        <%--dynamicScript.type = 'text/javascript';--%>
        <%--dynamicScript.async = true;--%>
        <%--dynamicScript.src = ${pageContext.request.contextPath} + '/js/verify.js';--%>
        <%--var firstScript = document.getElementsByTagName('script')[0];--%>
    <%--})();--%>
</script>
