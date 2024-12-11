<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Artist</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #222424;
            color: white;
            height: 100vh; /* Full viewport height */
            display: flex;
            justify-content: center; /* Centers horizontally */
            align-items: center; /* Centers vertically */
            margin: 0; /* Remove any default margin */
        }
        .container {
            max-width: 400px; /* Max width of form container */
            width: 100%; /* Ensure responsiveness */
            padding: 30px;
            border: 1px solid #ddd;
            border-radius: 5px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
            background-color: #131616;
        }
        input[type="text"], input[type="file"], input[type="url"], textarea {
            width: 100%; /* Full width of input fields */
            padding: 10px;
            margin-bottom: 10px;
            box-sizing: border-box; /* Ensure padding does not increase width */
            display: block; /* Block-level elements for inputs */
            background-color: #333333;
            border: 1px solid #444444;
            color: white;
        }
        input[type="submit"] {
            width: 100%; /* Full width for the submit button */
            padding: 10px 0;
            background-color: #5CAF50;
            color: white;
            border: none;
            cursor: pointer;
            text-align: center;
            border-radius: 5px;
            transition: background-color 0.3s;
        }
        input[type="submit"]:hover {
            background-color: #4A9C3F; /* Darker green on hover */
        }
        img {
            display: block;
            margin: 10px auto;
            max-width: 150px; /* Preview image width */
            height: auto;
        }
        button {
            margin-top: 10px;
            display: block;
        }
        h2 {
            text-align: center;
            margin-bottom: 20px;
        }
        .alert {
            padding: 10px;
            border-radius: 5px;
            margin-bottom: 10px;
        }
        .alert-success {
            background-color: #28a745;
            color: white;
        }
        .alert-danger {
            background-color: #dc3545;
            color: white;
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
    <h2>Create Artist</h2>
	
	<c:if test="${not empty ok}">
	    <c:choose>
	        <c:when test="${ok}">
	            <div class="alert alert-success">
	                Artist creation was successful!
	            </div>
	        </c:when>
	        <c:otherwise>
	            <div class="alert alert-danger">
	               ${message}
	            </div>
	        </c:otherwise>
	    </c:choose>
	</c:if>
    <!-- Artist Creation Form -->
    <form action="artist" method="POST" enctype="multipart/form-data" id="CREATEARTIST">
        <input type="hidden" name="mode" value="REGISTER" required>

        <!-- Artist Name -->
        <label for="artistName">Artist Name</label>
        <input type="text" id="artistName" name="name" placeholder="Enter artist name"
        value="${name }"
         required>

        <!-- Artist Image Upload -->
        <label for="image">Upload Image</label>
        <input type="file" id="image" name="image" accept="image/*" onchange="previewImage(event)">
        <img id="imagePreview" src="" alt="Image Preview" style="display:none; max-width: 300px; margin-top: 20px;">

        <!-- Submit Button -->
        <input type="submit" value="Create Artist">
        <a href="admin">Back</a>
    </form>
</div>

<!-- JavaScript for dynamic image preview -->
<script>
    function previewImage(event) {
        const file = event.target.files[0]; // Get the selected file
        const image = document.getElementById('imagePreview');
        
        if (file) {
            const reader = new FileReader(); // Create a FileReader object
            reader.onload = function(e) {
                image.src = e.target.result; // Set the image source to the loaded file data
                image.style.display = 'block'; // Display the image
            };
            reader.readAsDataURL(file); // Read the image file as a data URL
        }
    }
</script>

</body>
</html>
