<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Login Screen</title>
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

.login-container {

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
	max-width: 300px;
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
</style>
</head>
<body>


	<div class="login-container">
		 <c:choose>

					<c:when test="${not empty status and not status}">
						<div class="alert alert-danger">
						Your account is tempolarily disabled!!!
						</div>
					</c:when>
					<c:when test="${not empty ok and not ok}">
						<div class="alert alert-danger">Username or password is
							incorrect!!</div>
					</c:when>

					<c:otherwise>
					</c:otherwise>
				</c:choose>
		<form action="login" method="post">
		<input type="hidden" id="email-username" name="mode" value="LOGIN">
				
			<input type="email" id="email-username" name="email" 
				placeholder="Email " class="input-field" 
				value="${email }"
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
			<button type="submit" class="button">Log In</button>
			<br> <br> Don't have an account?<a href="user" class="link">
				Sign up for Music4u</a>
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
