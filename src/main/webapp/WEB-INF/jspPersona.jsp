<%@page import="com.happyweekend.models.*"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
WEB-INF
<% 
List<Person> persons = (List<Person>) request.getAttribute("persons"); 
%>
<%for(Person p : persons){%>
	<h1><%= p.getFirstName() %></h1>
<% } %>
</body>
</html>