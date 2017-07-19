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
    
    <title>首页</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/autocomplete/autocomplete.css">
    <script src="${pageContext.request.contextPath}/js/jquery-1.10.1.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
      <script src="${pageContext.request.contextPath}/js/autocomplete/autocomplete.js"></script>

      <style type="text/css">
          #message {
              margin-top: 40px;
              margin-bottom: 50px;
              font-size: 20px;
              text-align: center;
          }
          .proposal-box {
              border-left: 1px solid rgba(0, 0, 0, 0);
              border-right: 1px solid rgba(0, 0, 0, 0);
              left: 0px;
          }
          .proposal-list {
              margin-top: -19px;
              width: 298px;
          }

      </style>

  </head>
  <body>
    <div class="container">
        <form class="form-horizontal" role="form" id="form">
            <div class="form-group">
                <div id="search-form">
                </div>
                <div id="message"></div>
            </div>
        </form>
    </div>
  </body>
</html>
<script>
    var proposals = ['百度1', '百度2', '百度3', '百度4', '百度5', '百度6', '百度7','呵呵呵呵呵呵呵','百度','新浪','a1','a2','a3','a4','b1','b2','b3','b4'];

    $(document).ready(function(){
        $('#search-form').autocomplete({
            hints: proposals,
            width: 300,
            height: 30,
            onSubmit: function(text){
                $('#message').html('Selected: <b>' + text + '</b>');
            }
        });
    });
</script>

