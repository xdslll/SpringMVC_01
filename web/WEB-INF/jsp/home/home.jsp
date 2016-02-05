<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    if(request.getSession().getAttribute("user") == null) {
        response.sendRedirect(request.getContextPath() + "/login");
    }
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
<div class="easyui-layout" id="main">
    <div data-options="region:'north',border:false" id="north">
        <ul>
            <li>
                <div id="north_title_div">
                    <span id="north_title_span">${product_name}</span>
                </div>
            </li>
            <li>
                <div id="north_username_div">
                    <span>${user.name}</span>
                    <% if(request.getSession().getAttribute("user") != null) { %>
                        <a href='<%=request.getContextPath()%>/logout'>退出登录</a>
                    <% } %>
                </div>
            </li>
        </ul>
    </div>
    <div data-options="region:'west',split:true" id="west" title="菜单">
        <ul id="menu" class="easyui-tree" data-options="
            <c:if test="${user.roleId==0}">
                url:'<%=request.getContextPath()%>/menu/get',
            </c:if>
            <c:if test="${user.roleId!=0}">
                url:'<%=request.getContextPath()%>/role_menu/get?roleId=' + ${user.roleId},
            </c:if>
            method:'get',
            animate:true
            ">
        </ul>
    </div>
    <div data-options="region:'center'" id="center" >
        <iframe id="main_frame" style="height:100%;width:100%" scrolling="no" frameborder="0"></iframe>
    </div>
    <div data-options="region:'south', border:false" id="south">
        <div id="south_title_div">
            <span id="south_title_span">${foot_info}</span>
        </div>
    </div>
</div>
<script>
    $('#menu').tree({
        onClick: function(node) {
            var url = node.url;
            var startIndex = url.indexOf('http://');
            if (startIndex != 0) {
                $("#main_frame").attr('src', '<%=request.getContextPath()%>' + url);
            } else {
                $("#main_frame").attr('src', url);
            }
        }
    });
</script>
</body>
</html>