<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheet.css" type="text/css" />
<title>Welcome To resu.me</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<form name="form1" method="post">


	<div id="Name">

			<span class="bot-line"></span>
  			<span class="right-line"></span>
		<h1>${resume.getName()}</h1>
		<div id="divider"></div>
	</div>

	<div id="Contact">
		<p>${resume.getStreet()}, ${resume.getCity()},
			${resume.getState()}, ${resume.getZip()}</p>
		<p>Home: ${resume.getHomePhone()} - Cell: ${resume.getCellPhone()}
			- Email: ${resume.getEmail()}</p>
	</div>

	<div id="userContent">
		<div class="info">
			<h3>Career Overview</h3>
			<p>${resume.getText()}</p>
		</div>
		<div class="info">
			<h3>Qualifications</h3>
			<p>${resume.getQualifications()}</p>
		</div>
		<div class="info">
			<h3>TechnicalSkills</h3>
			<p>${resume.getTechSkills()}</p>
		</div>
		<div class="info">
			<h3>Accomplishments</h3>
			<p>${resume.getAccomplishments()}</p>
		</div>
		<div class="info">
			<h3>Work Experience</h3>
			<p>${resume.getWorkEx()}</p>
		</div>
		<div class="info">
			<h3>Education And Traning</h3>
			<p>${resume.getEdu()}</p>
		</div>
		<div class="info">
			<h3>Keywords</h3>
			<p>${resume.getKey()}</p>
		</div>
	</div>
	<input type="submit" value="Edit" />
</form>
</body>
</html>