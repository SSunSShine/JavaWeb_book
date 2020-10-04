<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>尚硅谷会员注册页面</title>
		<%-- 静态包含，base标签，css样式，jquery文件--%>
		<%@ include file="/pages/common/head.jsp"%>
		<script type="text/javascript">
			// 页面加载完成之后
			$(function () {

				$("#username").blur(function () {
					// 获取用户名
					var username = this.value;
					$.getJSON("http://localhost:8080/book/userServlet","action=ajaxExistsUsername&username=" +
					username,function (data) {
						if (data.existsUsername){
							$("span.errorMsg").text("用户名已存在！");
						}else {
							$("span.errorMsg").text("用户名可用！");
						}
					})
				})

				//给验证码的图片绑定单击事件
				$("#code_img").click(function () {
					this.src = "${basePath}kaptcha.jpg?d=" + new Date();
				});


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
					if(passwordTxt == usernameTxt){
						$("span.errorMsg").text("密码不能与用户名一样！");
						return false;
					}

					//确认密码
					var repwdTxt = $("#repwd").val();
					if(repwdTxt != passwordTxt){
						$("span.errorMsg").text("两次输入的密码不一致！");
						return false;
					}

					//验证邮箱格式
					var emailTxt = $("#email").val();
					var emailPatt = /^[a-z\d]+(\.[a-z\d]+)*@([\da-z](-[\da-z])?)+(\.{1,2}[a-z]+)+$/;
					if(!emailPatt.test(emailTxt)){
						$("span.errorMsg").text("邮箱格式不合法！");
						return false;
					}

					//验证验证码
					var codeTxt = $("#code").val();
					if (codeTxt == null || codeTxt == "") {
						//4 提示用户
						$("span.errorMsg").text("验证码不能为空！");

						return false;
					}

					// 去掉错误信息
					$("span.errorMsg").text("");



				});

			});

		</script>
	<style type="text/css">
		.login_form{
			height:420px;
			margin-top: 25px;
		}

	</style>
	</head>
	<body>
		<div id="login_header">
			<img class="logo_img" alt="" src="static/img/logo.gif" >
		</div>

			<div class="login_banner">

				<div id="l_content">
					<span class="login_word">欢迎注册</span>
				</div>

				<div id="content">
					<div class="login_form">
						<div class="login_box">
							<div class="tit">
								<h1>注册尚硅谷会员</h1>
								<span class="errorMsg">${requestScope.msg}</span>
							</div>
							<div class="form">
								<!--将表单交给Servlet程序（Web层）处理-->
								<form action="userServlet" method="post">
									<input type="hidden" name="action" value="regist" />
									<label>用户名称：</label>
									<input class="itxt" type="text" placeholder="请输入用户名"
										   autocomplete="off" tabindex="1" name="username" id="username"
											value="${requestScope.username}"/>
									<br />
									<br />
									<label>用户密码：</label>
									<input class="itxt" type="password" placeholder="请输入密码"
										   autocomplete="off" tabindex="1" name="password" id="password" />
									<br />
									<br />
									<label>确认密码：</label>
									<input class="itxt" type="password" placeholder="确认密码"
										   autocomplete="off" tabindex="1" name="repwd" id="repwd" />
									<br />
									<br />
									<label>电子邮件：</label>
									<input class="itxt" type="text" placeholder="请输入邮箱地址"
										   autocomplete="off" tabindex="1" name="email" id="email"
											value="${requestScope.email}"/>
									<br />
									<br />
									<label>验证码：</label>
									<input class="itxt" type="text" name="code" style="width: 80px;" id="code"/>
									<img id="code_img" alt="" src="kaptcha.jpg" style="float: right; margin-right: 40px; width: 120px; height: 30px">
									<br />
									<br />
									<input type="submit" value="注册" id="sub_btn" />
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