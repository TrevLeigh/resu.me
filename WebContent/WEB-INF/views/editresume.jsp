<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/editstyle.css" type="text/css" />
<title>edit resu.me</title>
</head>
<body>
	<form method="post">
		<div id="wrapper">
			<div class="group">
				<input type="text" name="name" required> <span
					class="highlight"></span> <span class="bar"></span> <label>Name</label>
			</div>

			<div class="group">
				<input type="text" name="street" required> <span
					class="highlight"></span> <span class="bar"></span> <label>Street
					Address</label>
			</div>

			<div class="group">
				<input type="text" name="city" required> <span
					class="highlight"></span> <span class="bar"></span> <label>City</label>
			</div>

			<div class="group">
				<input type="text" name="state" required> <span
					class="highlight"></span> <span class="bar"></span> <label>State</label>
			</div>

			<div class="group">
				<input type="text" name="zip" required> <span
					class="highlight"></span> <span class="bar"></span> <label>Zip
					Code</label>
			</div>

			<div class="group">
				<input type="text" name="home" required> <span
					class="highlight"></span> <span class="bar"></span> <label>Home
					Phone</label>
			</div>

			<div class="group">
				<input type="text" name="cell" required> <span
					class="highlight"></span> <span class="bar"></span> <label>Cell
					Phone</label>
			</div>

			<div class="group">
				<input type="text" name="email" required> <span
					class="highlight"></span> <span class="bar"></span> <label>Email</label>
			</div>

			<div class="group">
				<textarea required name="user_content"></textarea>
				<span class="highlight"></span> <span class="bar"></span> <label>Career
					Overview</label>
			</div>

			<div class="group">
				<textarea required name="qualifications"></textarea>
				<span class="highlight"></span> <span class="bar"></span> <label>Qualifications</label>
			</div>

			<div class="group">
				<textarea required name="techSkills"></textarea>
				<span class="highlight"></span> <span class="bar"></span> <label>Technical
					Skills</label>
			</div>

			<div class="group">
				<textarea required name="accomplishments"></textarea>
				<span class="highlight"></span> <span class="bar"></span> <label>Accomplishments</label>
			</div>

			<div class="group">
				<textarea required name="workEx"></textarea>
				<span class="highlight"></span> <span class="bar"></span> <label>Work
					Experience</label>
			</div>

			<div class="group">
				<textarea required name="edu"></textarea>
				<span class="highlight"></span> <span class="bar"></span> <label>Education</label>
			</div>

			<div class="group">
				<textarea required name="key"></textarea>
				<span class="highlight"></span> <span class="bar"></span> <label>Keywords</label>
			</div>

			<input type="submit" value="Enter" />
		</div>
	</form>
</body>
</html>