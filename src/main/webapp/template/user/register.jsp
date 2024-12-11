<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Spotify Sign Up Form</title>
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #222424;
	color: white;
	display: flex;
	justify-content: center;
	align-items: center;
	height: 100vh;
}

.signup-container {
	background-color: #131616;
	padding: 50px;
	border-radius: 8px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
	text-align: center;
}

.input-field {
	width: 100%;
	padding: 10px;
	margin-bottom: 20px;
	background-color: #333333;
	border: none;
	color: white;
	border-radius: 5px;
	display: inline-block;
	padding-right: 40px;
	/* Add padding to the right to make space for the icon */
	box-sizing: border-box;
}

.button {
	background-color: #1DB954;
	color: white;
	padding: 10px;
	width: 100%;
	border: none;
	cursor: pointer;
	border-radius: 5px;
}

.link {
	color: #1DB954;
	text-decoration: none;
}

.link:hover {
	text-decoration: underline;
}

.password-container {
	position: relative;
	width: 100%;
	max-width: 290px;
}

.toggle-password {
	position: absolute;
	right: 10px;
	/* Adjust this value to move the icon further from the right edge */
	top: 50%;
	transform: translateY(-50%);
	background: none;
	border: none;
	cursor: pointer;
}

.toggle-password img {
	width: 20px;
	height: 20px;
	position: relative;
	right: 1px;
	/* Adjust this value to move the icon further from the right edge */
	button: 50%;
	transform: translateY(-50%);
	cursor: pointer;
}

img {
	width: 100px;
	height: 100px;
	position
}
</style>
</head>
<body>
	<div class="signup-container">
		<img src="${pageContext.request.contextPath}/template/user/Logo.png" alt="Spotify Logo">
		<h2>Sign Up for Music4U</h2>
		
		<c:if test="${not empty emailAlreadyExists and emailAlreadyExists }">
			
			<div class="alert alert-danger">
			                Your Email Already Exists !!
			            </div>
			</c:if>
			
			<c:if test="${not empty ok}">
			    <c:choose>
			        <c:when test="${ok}">
			            <div class="alert alert-success">
			                Account Creation is Successful!
			            </div>
			        </c:when>
			        <c:otherwise>
			            <div class="alert alert-danger">
			                Account Creation Failed!
			            </div>
			        </c:otherwise>
			    </c:choose>
			</c:if>

		<form action="user" method="post">
		
		<input type="hidden" id="" name="mode" value="REGISTER">

			<input type="text" id="name" name="name" placeholder="Name"
				class="input-field" 
				value="${name}"
				required><br /> <input type="email"
				id="email" name="email" placeholder="Email" class="input-field"
				value="${email}"
				required><br />

			<div class="password-container">
				<input type="password" id="password" name="password"
					placeholder="Password" class="input-field"
									value="${password}"
					
					 required>
				<button type="button" class="toggle-password"
					onclick="togglePassword()">
					<img src="${pageContext.request.contextPath}/template/user/eye-icon.png" alt="Show/Hide Password" id="toggle-icon">
				</button>
			</div>
			<button type="submit" class="button">Sign up</button>
			<br> <br><a href="login" class="link">
				Sign in for Music4u</a>

		</form>
	</div>
	<script>
		function togglePassword() {
			var passwordField = document.getElementById('password');
			var toggleIcon = document.getElementById('toggle-icon');
			if (passwordField.type === 'password') {
				passwordField.type = 'text';
				toggleIcon.src = '${pageContext.request.contextPath}/template/user/eye-off-icon.png'; // Change to the 'eye-off' icon
			} else {
				passwordField.type = 'password';
				toggleIcon.src = '${pageContext.request.contextPath}/template/user/eye-icon.png'; // Change back to the 'eye' icon
			}
		}
	</script>
</body>
</html>

