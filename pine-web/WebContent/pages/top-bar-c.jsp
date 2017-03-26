<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String programName = request.getParameter("programName");
String backUrl = request.getParameter("backUrl");
String refreshUrl = request.getParameter("refreshUrl");
String saveFunction = request.getParameter("saveFunction");

%>

<table border="0" width="100%" cellspacing="2" cellpadding="2">
	<tr valign="top" align="left">
		<td align="left" width="100%">
			<div>
			<img alt="back" title="Back list page" src="./images/back.png" onclick="parent.changePage('<%=backUrl%>');"/>
			&nbsp;			
			<img alt="refresh" title="Refresh" src="./images/refresh.png" onclick="parent.changePage('<%=refreshUrl%>');"/>
			&nbsp;
			<img alt="save" title="Save" src="./images/save.png" onclick="<%=saveFunction%>"/>
			</div>		
		</td>
	</tr>
	<tr valign="top" align="left">
		<td align="left" width="100%">
			<span class="badge badge-default"><%=programName%></span>
		</td>
	</tr>	
</table>
