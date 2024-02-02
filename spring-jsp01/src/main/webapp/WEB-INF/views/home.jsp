<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Hello world!  
</h1>

<P> Server to : ${data} </P>
<P> Server to : ${num} </P>
<hr>
<P> 번호 : ${person1.code}</P>
<P> 이름 : ${person1.strData}</P>
<P> 나이 : ${person1.numData}</P>
<P> 생일 : ${person1.dateData}</P>
<hr>
</body>
</html>
