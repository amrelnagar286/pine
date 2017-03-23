<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String refreshUrl = request.getParameter("refreshUrl");
String createUrl = request.getParameter("createUrl");

%>

<table border="0" width="100%" cellspacing="2" cellpadding="2">
	<tr valign="top" align="left">
		<td align="left">
			<div>
			<a class="btn btn-success" href="#" onclick="parent.changePage('<%=refreshUrl%>');" role="button">Refresh</a>
			&nbsp;
			<a class="btn btn-success" href="#" onclick="parent.changePage('<%=createUrl%>');" role="button">Create</a>
			</div>		
		</td>
	</tr>
</table>
