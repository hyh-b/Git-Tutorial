<%@page import="com.example.model1.ZipcodeTO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	ArrayList<ZipcodeTO> lists = (ArrayList)request.getAttribute("lists");
	
	StringBuilder sbHtml = new StringBuilder();
	sbHtml.append("<table width='800' border='1'>");
	for(ZipcodeTO to : lists){
		sbHtml.append("<tr>");
		sbHtml.append("<td>" + to.getZipcode()+"</td>");
		sbHtml.append("<td>" + to.getSido()+"</td>");
		sbHtml.append("<td>" + to.getGugun()+"</td>");
		sbHtml.append("<td>" + to.getDong()+"</td>");
		sbHtml.append("<td>" + to.getRi()+"</td>");
		sbHtml.append("<td>" + to.getBunji()+"</td>");
		sbHtml.append("<tr>");
	}
	sbHtml.append("</table>");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

</head>
<body>
zipcode_ok.jsp<br><br>
zipcode : <%=sbHtml %>
</body>
</html>