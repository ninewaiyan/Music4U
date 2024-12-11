<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Create Album</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<style>
body {
	color: white;
	background-color: #222424; /* Consistent with other pages */
	font-family: Arial, sans-serif;
	height: 100vh; /* Full viewport height */
	display: flex;
	justify-content: center; /* Centers horizontally */
	align-items: center; /* Centers vertically */
	margin: 0;
}

.container {
	max-width: 400px; /* Max width of form container */
	width: 100%; /* Ensure responsiveness */
	padding: 30px;
	border: 1px solid #ddd;
	border-radius: 5px;
	box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
	background-color: #2A2D2E; /* Dark background for consistency */
}

.form-container {
	background-color: #202324; /* Darker container for better contrast */
	padding: 30px;
	border-radius: 8px;
	box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.form-title {
	text-align: center;
	margin-bottom: 20px;
}

.btn-primary {
	width: 100%;
	background-color: #5CAF50; /* Adjusted button color */
	border: none; /* Remove border */
}

input[type="text"], input[type="file"], input[type="url"], textarea {
	width: 100%; /* Full width of input fields */
	padding: 10px;
	margin-bottom: 10px;
	box-sizing: border-box; /* Ensure padding does not increase width */
	background-color: #333; /* Dark input background */
	color: white; /* Text color */
}

input::placeholder {
	color: #bbb; /* Placeholder color */
}

.form-text {
	color: #bbb; /* Muted text color */
}

a {
            display: block; /* Full width for Home link */
            text-align: center; /* Center text */
            margin-top: 20px; /* Spacing above link */
            color: #5CAF50; /* Link color */
            text-decoration: none; /* Remove underline */
        }

        a:hover {
            text-decoration: underline; /* Underline on hover */
        }
</style>
</head>
<body>

<c:if
	test="${empty sessionScope.user || sessionScope.user.role ne 'admin'}">
			<c:redirect url="login" />
		</c:if>
	<div class="container">
		<div class="form-container">
			<h2 class="form-title">Create Album</h2>
			
			<c:if test="${not empty ok}">
			    <c:choose>
			        <c:when test="${ok}">
			            <div class="alert alert-success">
			                Album Creation is Successful!
			            </div>
			        </c:when>
			        <c:otherwise>
			            <div class="alert alert-danger">
			               ${message}
			            </div>
			        </c:otherwise>
			    </c:choose>
			</c:if>
			
			<form action="album" method="post" enctype="multipart/form-data">
				<input type="hidden" class="form-control" name="mode" value="CREATE" required>

				<!-- Album Name -->
				<div class="form-group">
					<label for="albumName">Album Name</label>
					<input type="text" class="form-control" id="albumName" name="name" placeholder="Enter Album Name" 
						value="${name}" required>
				</div>

				<!-- Artist Selection (Dropdown) -->
				<div class="form-group">
					<label for="artistSelect">Select Artist</label>
					<select class="form-select" id="artistSelect" name="artist_id" required>
						<c:forEach var="artist" items="${artists}">
							<option value="${artist.id}">${artist.name}</option>
						</c:forEach>
					</select>
				</div>

				<!-- Album Cover Upload -->
				<div class="form-group">
					<label for="albumCover">Album Cover Image</label>
					<input type="file" class="form-control" id="albumCover" name="image" accept="image/*" required>
					<small class="form-text text-muted">Allowed file types: jpg, jpeg, png</small>
				</div>

				<!-- Submit Button -->
				<div class="form-group">
					<button type="submit" class="btn btn-primary">Create Album</button>
				</div>
				<a href="admin">Back</a>
			</form>
		</div>
	</div>

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
