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
    <script src="${pageContext.request.contextPath}/js/jquery-1.10.1.js"></script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
       <form action="http://testapi.reapal.com/mobile/portal" method="post">
           <input type="hidden" name="merchant_id" id="merchant_id" value="100000000000147">
           <input type="hidden" name="data" id="data" value="xdXeVez51u5ps+i38nYxgyh6N6oIUZSA4CZlcUx9XdIrbJbI32b3gHUp9b9nGlkxgjKaUZ5OQ7M+K14UvQubyHxbnXfJdvUSNAAQga1PAL3pEfyjQHQOdPoMDbvvFm0iLQldl6DleYEEGySoJiNyRGQNQg8PRs0mI12DSArPbKZ7I+pfzx/m/ARgA04cR/dCP8I9aAILyTRf0n5P6bhIdr+pxucJnRM+kcoNli8nPknjFXVCJBl9eCTCgqekLRT9BDEqWzOHVKkEbW89yrwv/dHWDzPHpMVRikKE4YxzxHJSjvucMBh+HyTHZTLu+1nqAjBJZXo7PQGo4UJA1QgpMXrAzrkuIF7NlQO6q4VoGNdfIY23WN16IcYNU0k4Msi7VZzUt7hiXumaOX2qDD90v/lNYu0BjFWo/84Y50qpV4d9wxomh4t/FuzSAY78hsx9WwoPJ/kG8waKZ1oHandLeXGi6PQfECyWdQfH2psugZ+Qnx2MHqLCkEnfEKRjG6pPaXqG4Vf6pIvFhJeFczaWgeRL6AUXkDr0lWk9fNLTJA87sFA4jCRcxb5Gb/K45BrtQEX24rzbi+GpmIriE31QIOxZ800QNMX/wfOP4TJIDdAtMTeCBtpeNYdjyhntQzqovU6MTniQf9X3szLlGHOhFZGkGYFxo9v8BTR6TAj7vpfjPFgtRevDSBaaCmqn9QDP7kdGw5E5bDLUX4ivsFxkFyiDcOr4TXortiDeVIbNJVo=">
           <input type="hidden" name="encryptkey" id="encryptkey" value="JyzuJU021J/cKcWxM885Ne1ahE0y9Ip4mk+GkIexIKAdydXpgwOTDNLVal3cO28apEQx6cG1cr/MtYkDQiU8kGBWmxpvoyP8BP9Kc/TxQHCSdp/sZ+K7r8+4KfeAvWKDIwoumDLR62nXK3KIjMN4Cc8UgelCO2Rq9b5o71s1FOLT6NluDlP41eEEYM6LLiLaCDd+Xi6qF7+6l+vXn6b90TGiZwIyKrH3FhOIOwuC9CU/7q5J+o2kGtvob63Xf4Mhdi8F7lvaLalG+a1gQRnvkfRLFc/a4a3BpvdD/aNNdvXMYQY/XU2H3R9RBE7ehRbXpkR0dYOEWN0UMuETDNAyDA==">
           <input type="submit" value="提交"/>
       </form>
  </body>
</html>
