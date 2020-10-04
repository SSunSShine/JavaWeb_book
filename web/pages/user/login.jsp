<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>尚硅谷会员登录页面</title>
	<%-- 静态包含，base标签，css样式，jquery文件--%>
	<%@ include file="/pages/common/head.jsp"%>
	<script type="text/javascript">
		// 页面加载完成之后
		$(function () {
			$("#sub_btn").click(function () {
				//获取用户名
				var usernameTxt = $("#username").val();
				var usernamePatt = /^\w{5,12}$/;
				if(!usernamePatt.test(usernameTxt)){
					$("span.errorMsg").text("用户名不合法！");

					return false;
				}

				//验证密码是否合法
				var passwordTxt = $("#password").val();
				var passwordPatt = /^\w{5,12}$/;
				if(!passwordPatt.test(passwordTxt)){
					$("span.errorMsg").text("密码不合法！");
					return false;
				}

				if (usernameTxt == null || usernameTxt == "" || passwordTxt == null || passwordTxt == "") {
					//4 提示用户
					$("span.errorMsg").text("用户名或密码不能为空！");

					return false;
				}

				// 去掉错误信息
				$("span.errorMsg").text("");



			});

		});
		</script>
</head>
<body>
		<div id="login_header">
			<img class="logo_img" alt="" src="static/img/logo.gif" >
		</div>
		
			<div class="login_banner">
			
				<div id="l_content">
					<span class="login_word">欢迎登录</span>
				</div>
				
				<div id="content">
					<div class="login_form">
						<div class="login_box">
							<div class="tit">
								<h1>尚硅谷会员</h1>
								<a href="pages/user/regist.jsp">立即注册</a>
							</div>
							<div class="msg_cont">
								<b></b>
								<span class="errorMsg">
<%--									<%=request.getAttribute("msg")==null?"请输入用户名和密码！":request.getAttribute("msg")%>--%>
									${empty requestScope.msg ? "请输入用户名和密码!" : requestScope.msg}
								</span>
							</div>
							<div class="form">
								<form action="userServlet" method="post">
									<input type="hidden" name="action" value="login" />
									<label>用户名称：</label>
									<input class="itxt" id="username" type="text" placeholder="请输入用户名"
										   autocomplete="off" tabindex="1" name="username"
											value="${requestScope.username}"/>
									<br />
									<br />
									<label>用户密码：</label>
									<input class="itxt" id="password" type="password" placeholder="请输入密码" autocomplete="off" tabindex="1" name="password" />
									<br />
									<br />
									<input type="submit" value="登录" id="sub_btn" />
								</form>
							</div>
							
						</div>
					</div>
				</div>
			</div>
		<%--静态包含页脚内容--%>
		<%@include file="/pages/common/foot.jsp"%>
</body>
</html>