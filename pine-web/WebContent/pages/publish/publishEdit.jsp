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

function updatePublish() {
	xhrSendParameter(
			'./publishUpdateJson.do',
			{
				'oid' : 						'${publish.oid}',
				'clientId' : 					'${publish.clientId}',
				'name' : 						$("#name").val(),			
				'topic' : 						$("#topic").val(),				
				'qos' : 						$("#qos").val(),				
				'bkBrokerAddr' : 				$("#bkBrokerAddr").val(),
				'username' : 					$("#username").val(),				
				'password' : 					$("#password").val(),				
				'content' : 					$("#content").val(),
				'contentExpr' : 				$("#contentExpr").val(),				
				'eventId' : 					$("#eventId").val(),
				'scriptType' : 					$("#scriptType").val(),
				'scriptId' : 					$("#scriptId").val(),
				'intervalSec' : 				$("#intervalSec").val(),
				'firstOnStart' : 				( $("#firstOnStart").is(':checked') ? 'Y' : 'N' ),
				'description' : 				$("#description").val()
			},
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
			function(){ parent.changePage("./publishList.do"); 
	});
}

</script>

</head>

<body>

<jsp:include page="../top-bar-c.jsp">
	<jsp:param name="programName" value="Publish (Edit)"/>
	<jsp:param name="backUrl" value="./publishList.do"/>	
	<jsp:param name="refreshUrl" value="./publishEdit.do?oid=${publish.oid}"/>
	<jsp:param name="saveFunction" value="updatePublish();"/>
</jsp:include>

  <div class="form-group">
    <label for="clientId">ID&nbsp;<font color="RED">*</font></label>
    <input type="text" class="form-control" id="clientId" placeholder="input publish id" value="${publish.clientId}" readonly="readonly">
  </div>
  <div class="form-group">
    <label for="name">Name&nbsp;<font color="RED">*</font></label>
    <input type="text" class="form-control" id="name" placeholder="input publish name" value="${publish.name}">
  </div>
  <div class="form-group">
    <label for="topic">Topic&nbsp;<font color="RED">*</font></label>
    <input type="text" class="form-control" id="topic" placeholder="input publish topic" value="${publish.topic}">
  </div> 
  <div class="form-group">
    <label for="qos">QoS&nbsp;<font color="RED">*</font></label>
	<select class="custom-select" id="qos">
	  <option value="0" <c:if test="${\"0\".equals(publish.qos)}">selected="selected"</c:if> >At most once (0)</option>
	  <option value="1" <c:if test="${\"1\".equals(publish.qos)}">selected="selected"</c:if> >At least once (1)</option>
	  <option value="2" <c:if test="${\"2\".equals(publish.qos)}">selected="selected"</c:if> >Exactly once (2)</option>
	</select>	
  </div> 
  <div class="form-group">
    <label for="bkBrokerAddr">Broker&nbsp;<font color="RED">*</font></label>
    <input type="text" class="form-control" id="bkBrokerAddr" placeholder="input broker address" value="${publish.bkBrokerAddr}">
  </div>
  <div class="form-group">
    <label for="intervalSec">Interval(sec) value 60 ~ 86400 , 0 only one&nbsp;<font color="RED">*</font></label>
    <input type="text" class="form-control" id="intervalSec" placeholder="input interval(Sec)" value="${publish.intervalSec}">
  </div>
  <div class="form-group">
	<label class="custom-control custom-checkbox">
	  <input type="checkbox" class="custom-control-input" id="firstOnStart" <c:if test="${\"Y\".equals(publish.firstOnStart)}">checked="checked"</c:if> >
	  <span class="custom-control-indicator"></span>
	  <span class="custom-control-description">First on start</span>
	</label>  
  </div>
  <div class="form-group">
    <label for="username">Username</label>
    <input type="text" class="form-control" id="username" placeholder="input broker username" value="${publish.bkUsername}">
  </div>    
   <div class="form-group">
    <label for="password">Password</label>
    <input type="text" class="form-control" id="password" placeholder="input broker password" value="${publish.bkPassword}">
  </div>    
  <div class="form-group">
    <label for="description">Content&nbsp;<font color="RED">*</font></label>
    <textarea rows="3" cols="15" class="form-control" id="content" placeholder="input content">${publish.content}</textarea>
  </div>    
  <div class="form-group">
    <label for="contentExpr">Expression&nbsp;(Priority over content)&nbsp;<font color="RED">*</font></label>
    <textarea rows="3" cols="15" class="form-control" id="contentExpr" placeholder="input expression">${publish.contentExpr}</textarea>
  </div>     

  <div class="form-group">
    <label for="eventId">Event Id&nbsp;(options for client subscribe)</label>
    <input type="text" class="form-control" id="eventId" placeholder="input event id" value="${publish.eventId}">
  </div>     
  <div class="form-group">
    <label for="scriptId">Script Id&nbsp;(options for client subscribe)</label>
    <input type="text" class="form-control" id="scriptId" placeholder="input script id" value="${publish.scriptId}">
  </div>       
  <div class="form-group">
    <label for="scriptType">Script type&nbsp;(options for client subscribe)</label>
	<select class="custom-select" id="scriptType">
	  <option value="GROOVY" <c:if test="${\"GROOVY\".equals(publish.scriptType)}">selected="selected"</c:if> >GROOVY</option>
	  <option value="BSH" <c:if test="${\"BSH\".equals(publish.scriptType)}">selected="selected"</c:if> >BSH</option>
	</select>	
  </div>     
  <div class="form-group">
    <label for="description">Description</label>
    <textarea rows="3" cols="15" class="form-control" id="description" placeholder="input description">${publish.description}</textarea>
  </div>    
  
<button type="button" class="btn btn-primary" onclick="updatePublish();">Save</button>
<button type="button" class="btn btn-primary" onclick="parent.changePage('./publishEdit.do?oid=${publish.oid}');">Clear</button>
  
</body>
</html>
