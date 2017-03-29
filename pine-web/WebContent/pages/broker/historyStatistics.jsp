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
	<jsp:param name="programName" value="Broker history statistics"/>
	<jsp:param name="refreshUrl" value="./historyStatistics.do"/>
	<jsp:param name="createUrl" value=""/>
</jsp:include>

<c:if test="${!empty brokers}">

<c:forEach items="${brokers}" var="item" varStatus="myIndex">

	<div class="card" style="width: 20rem;">
	  <div class="card-block">
	    <h4 class="card-title">${item.id} - ${item.name}</h4>
	    <p class="card-text">${item.description}</p>
	    <span class="badge badge-info">Connect: ${item.logConnect}</span>
	    <span class="badge badge-warning">Connection lost: ${item.logConnectionLost}</span>
	    <span class="badge badge-info">Disconnect: ${item.logDisconnect}</span>
	    <span class="badge badge-default">Message acknowledged: ${item.logMessageAcknowledged}</span>
	    <span class="badge badge-primary">Publish: ${item.logPublish}</span>
	    <span class="badge badge-success">Subscribe: ${item.logSubscribe}</span>
	    <span class="badge badge-success">Unsubscribe: ${item.logUnsubscribe}</span>
	    
	  </div>
	</div>
 
</c:forEach>  

</c:if>


<c:if test="${empty brokers}">
	<div class="alert alert-info" role="alert">
	  <strong>${pageMessage}</strong>
	</div>	
</c:if>
            
</body>
</html>
