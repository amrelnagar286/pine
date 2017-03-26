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

function updateBroker() {
	xhrSendParameter(
			'./brokerUpdateJson.do',
			{
				'oid' :							'${broker.oid}',
				'id' : 							'${broker.id}',
				'name' : 						$("#name").val(),
				'port' : 						$("#port").val(),
				'websocketPort' : 		$("#websocketPort").val(),
				'username' : 				$("#username").val(),
				'password' : 				$("#password").val(),
				'description' : 			$("#description").val()
			},
			function(data) { 
				if ('Y' != data.success) {
					parent.toastrWarning( data.message );
					return;
				}
				if ('Y' == data.success) {
					parent.toastrInfo( data.message );
					parent.changePage("./brokerList.do");
				}
			},
			function(){ parent.changePage("./brokerList.do"); 
	});
}

</script>

</head>

<body>

<jsp:include page="../top-bar-c.jsp">
	<jsp:param name="programName" value="Broker (Edit)"/>
	<jsp:param name="backUrl" value="./brokerList.do"/>	
	<jsp:param name="refreshUrl" value="./brokerEdit.do?oid=${broker.oid}"/>
	<jsp:param name="saveFunction" value="updateBroker();"/>
</jsp:include>

  <div class="form-group">
    <label for="id">ID&nbsp;<font color="RED">*</font></label>
    <input type="text" class="form-control" id="id" placeholder="input broker id" value="${broker.id}" readonly="readonly">
  </div>
  <div class="form-group">
    <label for="name">Name&nbsp;<font color="RED">*</font></label>
    <input type="text" class="form-control" id="name" placeholder="input broker name" value="${broker.name}">
  </div>
  <div class="form-group">
    <label for="port">Port&nbsp;<font color="RED">*</font></label>
    <input type="text" class="form-control" id="port" placeholder="input broker port" value="${broker.bkPort}">
  </div>
  <div class="form-group">
    <label for="websocketPort">Web Socket Port&nbsp;<font color="RED">*</font></label>
    <input type="text" class="form-control" id="websocketPort" placeholder="input broker web socket port" value="${broker.bkWebsocketPort}">
  </div>  
  <div class="form-group">
    <label for="username">Username</label>
    <input type="text" class="form-control" id="username" placeholder="input broker username" value="${broker.bkUsername}">
  </div>    
   <div class="form-group">
    <label for="password">Password</label>
    <input type="text" class="form-control" id="password" placeholder="input broker password" value="${broker.bkPassword}">
  </div>    
   <div class="form-group">
    <label for="description">Description</label>
    <textarea rows="3" cols="15" class="form-control" id="description" placeholder="input description">${broker.description}</textarea>
  </div>    
  
<button type="button" class="btn btn-primary" onclick="updateBroker();">Save</button>
<button type="button" class="btn btn-primary" onclick="parent.changePage('./brokerEdit.do?oid=${broker.oid}');">Clear</button>
  
</body>
</html>
