<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta name="description" content="">
  <meta name="author" content="">
  <title>登录</title>
  <script src="<%=request.getContextPath() %>/js/jquery.min.js" type="text/javascript"></script>
  <script src="<%=request.getContextPath() %>/js/jquery.cookie.js" type="text/javascript"></script>
  <script src="<%=request.getContextPath() %>/js/jquery.md5.js" type="text/javascript"></script>
  <script src="<%=request.getContextPath() %>/js/tooltips.js" type="text/javascript"></script>
  <script src="<%=request.getContextPath() %>/js/login.js" type="text/javascript"></script>
  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/login.css">
  <link rel="stylesheet" href="<%=request.getContextPath() %>/css/bootstrap.min.css" >
</head>
<body>
<form action="<%=request.getContextPath() %>/check/update" method="get">
  <div class="top">
    <div class="container">
      <div class="logo">

      </div>
    </div>
  </div>
  <div class="main">
    <div class="main-bg">
       <img src="<%=request.getContextPath() %>/img/logo-bg.jpg"/>
    </div>
    <div class="container">
      <div class="login_input">
        <div class="title" >
          <p>请输入Licence</p>
        </div>
        <div class="login_t">
          <div><strong id="errormsg" style="color:red">${errormsg}</strong></div>
          <textarea maxlength="100" id="licence" name="licence" class="form-control" placeholder="Licence"></textarea>
          <button type="submit" id="btnLogin" class="login_btn" onclick="return check();">验证</button>
        </div>
      </div>
    </div>   
  </div>
  <footer>
    <div class="container">
      <div class="under">
        <p>@Copyrigt 2015-2016</p>
      </div>
    </div>
  </footer>
  </form>
</body>
</html>

