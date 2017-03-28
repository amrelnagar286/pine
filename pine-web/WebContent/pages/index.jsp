<%@page import="com.netsteadfast.base.Constants"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>

<!DOCTYPE html>
<html>
<head>
<title>Pine</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<jsp:include page="common-inc.jsp"></jsp:include>

<link rel="stylesheet" href="./toastr/toastr.min.css" crossorigin="anonymous">
<script src="./toastr/toastr.min.js" crossorigin="anonymous"></script>

<style type="text/css">

#mainContant {
    height:100vh; 
}

</style>

<script type="text/javascript">


</script>

</head>

<body>


<jsp:include page="menu.jsp"></jsp:include>

            
<div id="mainContant">
	<iframe src="./pages/home.html" id="mainFrame" name="mainFrame" scrolling="auto" frameborder="0" height="100%" width="100%"></iframe>              
</div>
            
</body>
</html>
