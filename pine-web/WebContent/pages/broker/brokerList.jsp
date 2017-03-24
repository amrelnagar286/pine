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

<style type="text/css">



</style>

<script type="text/javascript">

</script>

</head>

<body>

<jsp:include page="../top-bar-q.jsp">
	<jsp:param name="programName" value="Broker management"/>
	<jsp:param name="refreshUrl" value="./brokerList.do"/>
	<jsp:param name="createUrl" value="./brokerCreate.do"/>
</jsp:include>

<c:if test="${!empty brokers}">
<table class="table">
  <thead>
    <tr>
      <th>#</th>
      <th>ID</th>
      <th>Name</th>
      <th>Status</th>
      <th>Edit/Delete</th>      
    </tr>
  </thead>
  <tbody>
  
<c:forEach items="${brokers}" var="item" varStatus="myIndex">
    <tr>  
      <th scope="row">${myIndex.index+1}</th> 
      <td>${item.id}</td>
      <td>${item.name}</td>
      <td>
		<c:if test="${\"Y\".equals(item.start)}">
			<span class="badge badge-success">In service</span>
		</c:if>
		<c:if test="${!\"Y\".equals(item.start)}">
			<span class="badge badge-warning">Stop</span>
		</c:if>
		<c:if test="${!\"Y\".equals(item.found)}">
			<span class="badge badge-danger">Container not found broker</span>
		</c:if>
      </td>
      <td>
      
<img alt="edit" title="Edit" src="./images/edit.png" onclick="parent.changePage('aa');"/>      
&nbsp;
<img alt="delete" title="Delete" src="./images/delete.png" onclick="parent.changePage('bb');"/>      	
      </td>
    </tr>    
</c:forEach>  

  </tbody>
</table>
</c:if>
<c:if test="${empty brokers}">
	<div class="alert alert-info" role="alert">
	  <strong>${pageMessage}</strong>
	</div>	
</c:if>

<script>
// test
//parent.showPleaseWait();
</script>
            
</body>
</html>
