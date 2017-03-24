<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String programName = request.getParameter("programName");
String refreshUrl = request.getParameter("refreshUrl");
String createUrl = request.getParameter("createUrl");

%>

<table border="0" width="100%" cellspacing="2" cellpadding="2">
	<tr valign="top" align="left">
		<td align="left" width="100px">
			<div>
			<img alt="refresh" title="Refresh" src="./images/refresh.png" onclick="parent.changePage('<%=refreshUrl%>');"/>
			&nbsp;
			<img alt="create" title="Create new" src="./images/create.png" onclick="parent.changePage('<%=createUrl%>');"/>
			</div>		
		</td>
		<td align="left">
			<span class="badge badge-default"><%=programName%></span>
		</td>
	</tr>
</table>
