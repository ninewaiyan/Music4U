<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Song</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            color: white;
            background-color: #222424;
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
            border-radius: 8px;
            background-color: #131616;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
        }

        .form-title {
            text-align: center;
            margin-bottom: 20px;
            color: #5CAF50; /* Changed color for better visibility */
        }

        .form-group {
            margin-bottom: 20px;
        }

        .btn-primary {
            width: 100%;
            background-color: #5CAF50; /* Primary button color */
            border: none; /* Remove border */
        }

        .btn-primary:hover {
            background-color: #4CAF50; /* Darker shade on hover */
        }

        input[type="text"], input[type="file"], select {
            background-color: #333333; /* Input background color */
            color: white; /* Input text color */
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
        <h2 class="form-title">Create Song</h2>
        
        <c:if test="${not empty ok}">
            <c:choose>
                <c:when test="${ok}">
                    <div class="alert alert-success">
                        Song Creation is Successful!
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="alert alert-danger">
                       ${message}
                    </div>
                </c:otherwise>
            </c:choose>
        </c:if>

        <form action="song" method="POST" enctype="multipart/form-data">
            <input type="hidden" class="form-control" name="mode" value="CREATE" required>

            <!-- Song Name -->
            <div class="form-group">
                <input type="text" class="form-control" name="name" placeholder="Enter Song Name" value="${name}" required>
            </div>

            <!-- Artist Selection (Dropdown) -->
            <div class="form-group">
                <label for="artistSelect">Select Album</label>
                <select class="form-select" id="artistSelect" name="album_id" required>
                    <c:forEach var="album" items="${albums}">
                        <option value="${album.id}">${album.name}</option>
                    </c:forEach>
                </select>
            </div>

            <!-- Song File Upload -->
            <div class="form-group">
                <input type="file" class="form-control" name="path" accept="audio/mpeg" required>
                <small class="form-text text-muted">Allowed file types: only mp3</small>
            </div>

            <!-- Submit Button -->
            <div class="form-group">
                <button type="submit" class="btn btn-primary">Create Song</button>
            </div>

            <a href="admin">Back</a>
        </form>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
