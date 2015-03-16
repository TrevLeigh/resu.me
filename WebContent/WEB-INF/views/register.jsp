<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="registerstyle.css">
</head>
<body>
	<form  method="post" action="http://localhost:8080/Java2Lab2_Registration/Registration/reg">
	<img id="logo" src="Logo3.png" />
    <h1 id="content">The place to get your career started</h1>
    <div class="fieldOuter" id="name">
        <input name="name" id="Person" placeholder="Name" type="text" />
        <label for="Person">Name</label>
        <div class="clickEffect"></div>
    </div>
    <div class="fieldOuter" id="username">
        <input name="username" id="Name" placeholder="Username" type="text" />
        <label for="Name">Username</label>
        <div class="clickEffect"></div>
    </div>
    <div class="fieldOuter" id="password">
        <input name="password" id="LastName" placeholder="Password" type="password" />
        <label for="LastName">Password</label>
        <div class="clickEffect"></div>
    </div>
	</form>
</body>
</html>