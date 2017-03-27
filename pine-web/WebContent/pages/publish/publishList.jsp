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

function restartPublish(oid) {
	bootbox.confirm(
			"Restart publish?", 
			function(result){ 
				if (!result) {
					return;
				}
				xhrSendParameter(
						'./publishStartJson.do',
						{'oid' : oid},
						function(data) { 
							if ('Y' != data.success) {
								parent.toastrWarning( data.message );
								return;
							}
							if ('Y' == data.success) {
								parent.toastrInfo( data.message );
								parent.changePage("./publishList.do");
							}
						},
						function(){ parent.changePage("./publishList.do"); });
			}
	);
}

function stopPublish(oid) {
	bootbox.confirm(
			"Stop publish?", 
			function(result){ 
				if (!result) {
					return;
				}
				xhrSendParameter(
						'./publishStopJson.do',
						{'oid' : oid},
						function(data) { 
							if ('Y' != data.success) {
								parent.toastrWarning( data.message );
								return;
							}							
							if ('Y' == data.success) {
								parent.toastrInfo( data.message );
								parent.changePage("./publishList.do");
							}
						},
						function(){ parent.changePage("./publishList.do"); });
			}
	);	
}

function deletePublish(oid) {
	bootbox.confirm(
			"Delete?", 
			function(result){ 
				if (!result) {
					return;
				}
				xhrSendParameter(
						'./publishDeleteJson.do',
						{'oid' : oid},
						function(data) { 
							if ('Y' != data.success) {
								parent.toastrWarning( data.message );
								return;
							}							
							if ('Y' == data.success) {
								parent.toastrInfo( data.message );
								parent.changePage("./publishList.do");
							}
						},
						function(){ parent.changePage("./publishList.do"); });
			}
	);	
}


function restartAllPublish() {
	bootbox.confirm(
			"Restart all publish?", 
			function(result){ 
				if (!result) {
					return;
				}
				xhrSendParameter(
						'./publishRestartAllJson.do',
						{ },
						function(data) { 
							if ('Y' != data.success) {
								parent.toastrWarning( data.message );
								return;
							}							
							if ('Y' == data.success) {
								parent.toastrInfo( data.message );
								parent.changePage("./publishList.do");
							}
						},
						function(){ parent.changePage("./publishList.do"); });
			}
	);	
}

function stopAllPublish() {
	bootbox.confirm(
			"Stop all publish?", 
			function(result){ 
				if (!result) {
					return;
				}
				xhrSendParameter(
						'./publishStopAllJson.do',
						{ },
						function(data) { 
							if ('Y' != data.success) {
								parent.toastrWarning( data.message );
								return;
							}							
							if ('Y' == data.success) {
								parent.toastrInfo( data.message );
								parent.changePage("./publishList.do");
							}
						},
						function(){ parent.changePage("./publishList.do"); });
			}
	);	
}

</script>

</head>

<body>

<jsp:include page="../top-bar-q.jsp">
	<jsp:param name="programName" value="Publish job management"/>
	<jsp:param name="refreshUrl" value="./publishList.do"/>
	<jsp:param name="createUrl" value="./publishCreate.do"/>
</jsp:include>

<c:if test="${!empty publishList}">
<table class="table">
  <thead>
    <tr>
      <th>#</th>
      <th>ID</th>
      <th>Name</th>
      <th>Topic</th>
      <th>QoS&nbsp;/&nbsp;Interval(sec)</th>      
      <th>Description</th>
      <th>Status</th>
      <th>Edit/Delete</th>      
    </tr>
  </thead>
  <tbody>
  
<c:forEach items="${publishList}" var="item" varStatus="myIndex">
    <tr>  
      <th scope="row">${myIndex.index+1}</th> 
      <td>${item.clientId}</td>
      <td>${item.name}</td>
      <td>${item.topic}</td>
      <td>${item.qos}&nbsp;/&nbsp;${item.intervalSec}</td>
      <td>${item.description}</td>
      <td>
		<c:if test="${\"Y\".equals(item.run)}">
			<span class="badge badge-success">In publish</span>
		</c:if>
		<c:if test="${!\"Y\".equals(item.run)}">
			<span class="badge badge-warning">Stop</span>
		</c:if>
		<c:if test="${!\"Y\".equals(item.found)}">
			<span class="badge badge-danger">Container not found publish</span>
		</c:if>
      </td>
      <td>
      
		<img alt="edit" title="Edit" src="./images/edit.png" onclick="parent.changePage('./publishEdit.do?oid=${item.oid}');"/>      
		&nbsp;
		<img alt="service" title="Restart publish" src="./images/service.png" onclick="restartPublish('${item.oid}');"/>     
		&nbsp;
		<img alt="stop" title="Stop publish" src="./images/stop.png" onclick="stopPublish('${item.oid}');"/>     
		&nbsp;
		<img alt="delete" title="Delete" src="./images/delete.png" onclick="deletePublish('${item.oid}');"/>   
   	
      </td>
    </tr>    
</c:forEach>  

  </tbody>
</table>
</c:if>

<c:if test="${!empty publishList}">
<table border="0" width="100%" cellspacing="2" cellpadding="2">
	<tr valign="top" align="left">
		<td align="left" width="100%">
			<div>

				<img alt="service" title="Restart all publish" src="./images/service.png" onclick="restartAllPublish();"/>     
				&nbsp;
				<img alt="stop" title="Stop all publish" src="./images/stop.png" onclick="stopAllPublish();"/>     
				&nbsp;
				
			</div>		
		</td>
	</tr>
</table>
</c:if>

<c:if test="${empty publishList}">
	<div class="alert alert-info" role="alert">
	  <strong>${pageMessage}</strong>
	</div>	
</c:if>
            
</body>
</html>
