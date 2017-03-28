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

function savePublish() {
	xhrSendParameter(
			'./publishSaveJson.do',
			{
				'clientId' : 					$("#clientId").val(),
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
	<jsp:param name="programName" value="Publish (Create)"/>
	<jsp:param name="backUrl" value="./publishList.do"/>	
	<jsp:param name="refreshUrl" value="./publishCreate.do"/>
	<jsp:param name="saveFunction" value="savePublish();"/>
</jsp:include>

  <div class="form-group">
    <label for="clientId">ID&nbsp;<font color="RED">*</font></label>
    <input type="text" class="form-control" id="clientId" placeholder="input publish id">
  </div>
  <div class="form-group">
    <label for="name">Name&nbsp;<font color="RED">*</font></label>
    <input type="text" class="form-control" id="name" placeholder="input publish name">
  </div>
  <div class="form-group">
    <label for="topic">Topic&nbsp;<font color="RED">*</font></label>
    <input type="text" class="form-control" id="topic" placeholder="input publish topic">
  </div> 
  <div class="form-group">
    <label for="qos">QoS&nbsp;<font color="RED">*</font></label>
	<select class="custom-select" id="qos">
	  <option value="0">At most once (0)</option>
	  <option value="1">At least once (1)</option>
	  <option value="2" selected="selected">Exactly once (2)</option>
	</select>	
  </div> 
  <div class="form-group">
    <label for="bkBrokerAddr">Broker&nbsp;<font color="RED">*</font></label>
    <input type="text" class="form-control" id="bkBrokerAddr" placeholder="input broker address">
  </div>
  <div class="form-group">
    <label for="intervalSec">Interval(sec) value 60 ~ 86400 , 0 only one&nbsp;<font color="RED">*</font></label>
    <input type="text" class="form-control" id="intervalSec" placeholder="input interval(Sec)">
  </div>
  <div class="form-group">
	<label class="custom-control custom-checkbox">
	  <input type="checkbox" class="custom-control-input" id="firstOnStart" checked="checked">
	  <span class="custom-control-indicator"></span>
	  <span class="custom-control-description">First on start</span>
	</label>  
  </div>
  <div class="form-group">
    <label for="username">Username</label>
    <input type="text" class="form-control" id="username" placeholder="input broker username">
  </div>    
   <div class="form-group">
    <label for="password">Password</label>
    <input type="text" class="form-control" id="password" placeholder="input broker password">
  </div>    
  <div class="form-group">
    <label for="description">Content&nbsp;<font color="RED">*</font></label>
    <textarea rows="3" cols="15" class="form-control" id="content" placeholder="input content"></textarea>
  </div>    
  <div class="form-group">
    <label for="contentExpr">Expression&nbsp;(Priority over content)&nbsp;<font color="RED">*</font></label>
    <textarea rows="3" cols="15" class="form-control" id="contentExpr" placeholder="input expression"></textarea>
  </div>     

  <div class="form-group">
    <label for="eventId">Event Id&nbsp;(options for client subscribe)</label>
    <input type="text" class="form-control" id="eventId" placeholder="input event id">
  </div>     
  <div class="form-group">
    <label for="scriptId">Script Id&nbsp;(options for client subscribe)</label>
    <input type="text" class="form-control" id="scriptId" placeholder="input script id">
  </div>       
  <div class="form-group">
    <label for="scriptType">Script type&nbsp;(options for client subscribe)</label>
	<select class="custom-select" id="scriptType">
	  <option value="GROOVY">GROOVY</option>
	  <option value="BSH">BSH</option>
	</select>	
  </div>     
  <div class="form-group">
    <label for="description">Description</label>
    <textarea rows="3" cols="15" class="form-control" id="description" placeholder="input description"></textarea>
  </div>    
  
<button type="button" class="btn btn-primary" onclick="savePublish();">Save</button>
<button type="button" class="btn btn-primary" onclick="parent.changePage('./publishCreate.do');">Clear</button>
  
</body>
</html>
