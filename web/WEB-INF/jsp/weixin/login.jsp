<%--
  Created by IntelliJ IDEA.
  User: xiads
  Date: 16/2/25
  Time: 下午4:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="">
	<meta name="author" content="">
	<title>登录</title>
	<script src="<%=request.getContextPath()%>/js/jquery.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/assets/js/ie-emulation-modes-warning.js"></script>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.min.css" >
	<link href="<%=request.getContextPath()%>/assets/css/ie10-viewport-bug-workaround.css" rel="stylesheet">
	<style>
		body {
			padding-top: 40px;
			padding-bottom: 40px;
			background-color: #eee;
		}

		.form-signin {
			max-width: 330px;
			padding: 15px;
			margin: 0 auto;
		}
		.form-signin .form-signin-heading,
		.form-signin .checkbox {
			margin-bottom: 10px;
		}
		.form-signin .checkbox {
			font-weight: normal;
		}
		.form-signin .form-control {
			position: relative;
			height: auto;
			-webkit-box-sizing: border-box;
			-moz-box-sizing: border-box;
			box-sizing: border-box;
			padding: 10px;
			font-size: 16px;
		}
		.form-signin .form-control:focus {
			z-index: 2;
		}
		.form-signin input[type="email"] {
			margin-bottom: -1px;
			border-bottom-right-radius: 0;
			border-bottom-left-radius: 0;
		}
		.form-signin input[type="password"] {
			margin-bottom: 10px;
			border-top-left-radius: 0;
			border-top-right-radius: 0;
		}
		.footer {
			position: absolute;
			bottom: 0;
			width: 100%;
			text-align: center;
			/* Set the fixed height of the footer here */
			height: 60px;
			background-color: #f5f5f5;
		}
		.container .text-muted {
			margin: 20px 0;
		}
		#errormsg {
			text-align: center;
		}
	</style>
</head>
<body>
	<div class="container">

		<form class="form-signin" action="<%=request.getContextPath()%>/weixin/login.do" method="post">
			<h3 class="form-signin-heading">请绑定您的会员账号</h3>
			<label for="inputEmail" class="sr-only">Email address</label>
			<input type="text" id="inputEmail" class="form-control" placeholder="请输入商户名称" name="username" required autofocus>
			<label for="inputPassword" class="sr-only">Password</label>
			<input type="password" id="inputPassword" class="form-control" placeholder="请输入授权密码" name="password" required>
			<button class="btn btn-lg btn-primary btn-block" type="submit">绑定</button>
		</form>
		<div id="errormsg"><strong style="color:red">${errormsg}</strong></div>
	</div> <!-- /container -->
	<footer class="footer">
		<div class="container">
			<p class="text-muted">© 2015-2016 enford.com.cn All rights reserved.</p>
		</div>
	</footer>
	<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
	<script src="<%=request.getContextPath()%>/assets/js/ie10-viewport-bug-workaround.js"></script>
</body>
</html>
