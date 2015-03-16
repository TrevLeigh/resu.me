<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Log In</title>
<link rel="stylesheet" type="text/css" href="styleSheet.css">
</head>
<body>
	<form method="post" action="${pageContext.request.contextPath}/Login" >
	<img id="logo" src="Logo3.png" />
    <h1 id="content">The place to get your career started</h1>
    <div class="fieldOuter" id="username">
        <input id="Name" placeholder="Username" type="text" />
        <label for="Name">Username</label>
        <div class="clickEffect"></div>
    </div>
    <div class="fieldOuter" id="password">
        <input id="LastName" placeholder="Password" type="password" />
        <label for="LastName">Password</label>
        <div class="clickEffect"></div>
    </div>
			<input type="submit" value="Login">
	</form>
</body>
</html>