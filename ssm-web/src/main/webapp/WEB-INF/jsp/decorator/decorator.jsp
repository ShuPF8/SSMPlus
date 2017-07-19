<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/5/24
  Time: 9:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>sitemech引用子标题：<sitemesh:write property='title'/></title>

    <sitemesh:write property='head'/>
</head>
<body>
    <div>sitemesh母模板</div>
    <div>
        引用子模板body:<br/>
        <sitemesh:write property="body" />
    </div>
</body>
</html>
