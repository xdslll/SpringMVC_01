<%--
  Created by IntelliJ IDEA.
  User: xiads
  Date: 16/2/1
  Time: 下午3:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    response.sendRedirect(request.getContextPath() + "/login");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>${title}</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/icon.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/demo.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/layout.css">
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/layout.js"></script>
</head>
<body>
<style>
    #left {
        background: lawngreen;
        height: 50px;
    }
    #right {
        background: darkred;
        height: 50px;
    }
    ul {
        float:left;
        list-style-type:none;
        width:100%;
        padding:0;
        margin:0;
    }
    li {
        display:inline;
    }
    div {
        float:left;
    }
</style>
<ul>
    <li>
        <div id="left">bbb</div>
    </li>
    <li>
        <div id="right">ccc</div>
    </li>
</ul>
<a href="http://www.baidu.com">百度</a>
</body>
</html>
