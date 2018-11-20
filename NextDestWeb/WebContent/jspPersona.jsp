<%@page import="beans.Person"%>
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
<% 
List<Person> persons = (List<Person>) session.getAttribute("persons"); 
%>
<%for(Person p : persons){%>
	<h1><%= p.getFirstName() %></h1>
<% } %>
</body>
</html>