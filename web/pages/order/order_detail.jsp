<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>我的订单</title>
    <%-- 静态包含，base标签，css样式，jquery文件--%>
    <%@ include file="/pages/common/head.jsp"%>
    <style type="text/css">
        h1 {
            text-align: center;
            margin-top: 200px;
        }
    </style>
</head>
<body>

<div id="header">
    <img class="logo_img" alt="" src="../../static/img/logo.gif" >
    <span class="wel_word">我的订单</span>
    <%@include file="/pages/common/login_success_menu.jsp"%>
</div>

<div id="main">

    <table>
        <tr>
            <td>商品名称</td>
            <td>数量</td>
            <td>单价</td>
            <td>总价</td>
        </tr>
        <c:forEach items="${requestScope.orderItems}" var="orderItems">
            <tr>
                <td>${orderItems.name}</td>
                <td>${orderItems.count}</td>
                <td>${orderItems.price}</td>
                <td>${orderItems.totalPrice}</td>
            </tr>
        </c:forEach>
    </table>


</div>

<%--静态包含页脚内容--%>
<%@include file="/pages/common/foot.jsp"%>
</body>
</html>