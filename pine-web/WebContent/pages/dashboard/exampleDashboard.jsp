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

<jsp:include page="../common-inc.jsp"></jsp:include>

<script type="text/javascript" src="<%=basePath%>/js/f.js"></script>

<style type="text/css">



</style>

<script type="text/javascript">


</script>

</head>

<body>

<jsp:include page="../top-bar-q.jsp">
	<jsp:param name="programName" value="Example dashboard"/>
	<jsp:param name="refreshUrl" value="./exampleDashboard.do"/>
	<jsp:param name="createUrl" value=""/>
</jsp:include>


<div class="card card-inverse card-info mb-3 text-center">
  <div class="card-block">
    <blockquote class="card-blockquote">
      <p><h4>Power</h4></p>
      <footer><strong>value: ${power}</strong></footer>
    </blockquote>
  </div>
</div>
<div class="card card-inverse card-info mb-3 text-center">
  <div class="card-block">
    <blockquote class="card-blockquote">
      <p><h4>Temperature</h4></p>
      <footer><strong>value: ${temp}</strong></footer>
    </blockquote>
  </div>
</div>

<c:if test="${!empty pageMessage}">
	<div class="alert alert-info" role="alert">
	  <strong>${pageMessage}</strong>
	</div>	
</c:if>

<script>

function refreshPageEd() {
    setTimeout(function() {
    	parent.changePage("./exampleDashboard.do");
    }, 60000);
}
refreshPageEd();

</script>

</body>
</html>
