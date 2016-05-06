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
  <script>
    function login(){
      if($("#username").val() ==''){
        $("#errormsg").html("用户名不能为空!");
        return false;
      }
      if($("#password").val()== ''){
        $("#errormsg").html("密码不能为空!");
        return false;
      }
      return true;
    }

    check();

    function check(){
      $.ajax({
        url: '<%=request.getContextPath() %>/check/enable',
        type: 'GET',
        success: function(data) {
          var result = eval('('+data+')');
          if (result.code == '0') {
            //alert(result.msg);
          } else {
            window.location.href = '<%=request.getContextPath() %>/check/verify?errormsg=Error Licence';
          }
        }
      })
    }
  </script>
</head>
<body>
<form action="<%=request.getContextPath() %>/dologin" method="post">
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
          <p>管理平台</p>
        </div>
        <div class="login_t">
          <div><strong id="errormsg" style="color:red">${errormsg}</strong></div>
          <input type="text" id="username" name="username" value="${username}" class="form-control" placeholder="用户名"/>
          <input type="password" id="password" name="password" value="${password}" class="form-control" placeholder="密码"/>
          <button type="submit" id="btnLogin" class="login_btn" onclick="return login();">登录</button>
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

