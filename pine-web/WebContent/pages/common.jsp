<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>

<script type="text/javascript" src="<%=basePath%>/jquery/jquery-1.11.1.min.js"></script>
<link rel="stylesheet" href="./bootstrap-4.0.0-alpha.6/css/bootstrap.css" crossorigin="anonymous">
<script src="./bootstrap-4.0.0-alpha.6/js/bootstrap.js" crossorigin="anonymous"></script>
<script src="./bootbox/bootbox.js" crossorigin="anonymous"></script>
