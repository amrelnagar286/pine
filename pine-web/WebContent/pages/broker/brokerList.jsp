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
<span class="badge badge-default">Default</span>
<span class="badge badge-primary">Primary</span>
<span class="badge badge-success">Work</span>
<span class="badge badge-info">Info</span>
<span class="badge badge-warning">Stop</span>
<span class="badge badge-danger">No found</span>      
      </td>
      <td>
<a class="btn btn-primary" href="#" onclick="parent.changePage('aa');" role="button">Edit</a>
<a class="btn btn-danger" href="#" onclick="parent.changePage('bb');" role="button">Delete</a>      	
      </td>
    </tr>    
</c:forEach>  

  </tbody>
</table>
</c:if>
<c:if test="${empty brokers}">
	
</c:if>

<script>
// test
//parent.showPleaseWait();
</script>
            
</body>
</html>
