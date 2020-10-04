<%--
  Created by IntelliJ IDEA.
  User: SunShine
  Date: 2020/7/21
  Time: 9:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() //http
            + "://"
            + request.getServerName() //服务器名
            + ":"
            + request.getServerPort() //端口
            + request.getContextPath() //工程路径
            + "/";
%>
<base href="<%=basePath%>">
<link type="text/css" rel="stylesheet" href="static/css/style.css" >
<script type="text/javascript" src="static/script/jquery-1.7.2.js"></script>
