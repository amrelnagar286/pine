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

<script type="text/javascript" src="<%=basePath%>/jquery/jquery-1.11.1.min.js"></script>
<link rel="stylesheet" href="./bootstrap-4.0.0-alpha.6/css/bootstrap.css" crossorigin="anonymous">
<script src="./bootstrap-4.0.0-alpha.6/js/bootstrap.js" crossorigin="anonymous"></script>


<style type="text/css">

body {
  padding-top: 40px;
  padding-bottom: 40px;
  background-color: #ffffff;
}

.form-signin {
  max-width: 330px;
  padding: 15px;
  margin: 0 auto;
}
.form-signin .form-signin-heading,
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

</style>

<script type="text/javascript">

function submitLoginForm() {	
	document.loginForm.submit();
	$('#myPleaseWait').modal('show');	
}

</script>

</head>

<body>

<!-- Modal Start here-->
<div class="modal fade bd-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true" id="myPleaseWait">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <h4 class="modal-title" id="mySmallModalLabel">Please wait!</h4>
      </div>
      <div class="modal-body">
        <img alt="loading" src="./images/loadingAnimation.gif" border="0">
      </div>
    </div>
  </div>
</div>
<!-- Modal ends Here -->

<div class="container">   
<form class="form-signin" name="loginForm" id="loginForm" action="./login.do" method="post">

    <div><img src="./images/pine2.png" />&nbsp;<b><font color="#75ca5b">Pine WEB console</font></b></div>
   
    <br/>
   
    <div class="form-group">
          <label for="username">Username</label>
          <input class="form-control" type="text" id="username" name="username" maxlength="12">
    </div>   
    <div class="form-group">
          <label for="password">Password</label>
          <input class="form-control" type="password" id="password" name="password" maxlength="25">
    </div>   
   	
   	
   	<c:if test="${\"\" != pageMessage && null != pageMessage}">
   	<p class="bg-warning"><c:out value="${pageMessage}" ></c:out></p>
   	</c:if>
   
    <button type="button" class="btn btn-lg btn-primary btn-block" name="btnSubmit" onclick="submitLoginForm()">Login</button>
   
    <br/>
    
    <label>Pine 0.1 version</label>

</form>
</div>

</body>
</html>
