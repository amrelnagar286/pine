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

function removeAllLog() {
	bootbox.confirm(
			"Delete?", 
			function(result){ 
				if (!result) {
					return;
				}
				xhrSendParameter(
						'./logDeleteJson.do',
						{ },
						function(data) { 
							if ('Y' != data.success) {
								parent.toastrWarning( data.message );
								return;
							}							
							if ('Y' == data.success) {
								parent.toastrInfo( data.message );
								parent.changePage("./logList.do");
							}
						},
						function(){ parent.changePage("./logList.do"); });
			}
	);	
}


</script>

</head>

<body>

<jsp:include page="../top-bar-q.jsp">
	<jsp:param name="programName" value="Log"/>
	<jsp:param name="refreshUrl" value="./logList.do"/>
	<jsp:param name="createUrl" value=""/>
</jsp:include>

<c:if test="${!empty eventLogList}">
<table border="0" width="100%" cellspacing="2" cellpadding="2">
	<tr valign="top" align="left">
		<td align="left" width="100%">
			<div>
				
				<img alt="delete" title="Clear all log" src="./images/delete.png" onclick="removeAllLog();"/>     
				&nbsp;

			</div>		
		</td>
	</tr>
</table>
</c:if>

<c:if test="${!empty eventLogList}">
<table class="table">
  <thead>
    <tr>
      <th>#</th>
      <th>Broker</th>
      <th>Event</th>
      <th>Client</th>      
      <th>Topic</th>
      <th>Message</th>      
    </tr>
  </thead>
  <tbody>
  
<c:forEach items="${eventLogList}" var="item" varStatus="myIndex">
    <tr>  
      <th scope="row">${myIndex.index+1}</th> 
      <td>${item.brokerId}</td>
      <td>${item.eventType}</td>
      <td>${item.clientId}</td>
      <td>${item.topic}</td>
      <td>${item.msg}</td>
    </tr>    
</c:forEach>  

  </tbody>
</table>
</c:if>



<c:if test="${empty eventLogList}">
	<div class="alert alert-info" role="alert">
	  <strong>${pageMessage}</strong>
	</div>	
</c:if>
            
</body>
</html>
