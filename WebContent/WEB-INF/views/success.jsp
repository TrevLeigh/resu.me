<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h1>You Have signed in as ${user.getName()}</h1>
	<form  method="post" action="http://localhost:8080/Java2Lab2_Registration/Registration/success">
	<input type="submit" >
	</form>
</body>
</html>