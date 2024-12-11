<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Manage Artist</title>

<!-- Bootstrap and Font Awesome CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">

<!-- Google Fonts -->
<link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">

<style>
    /* Custom Styles for Admin Dashboard */
    body {
        font-family: 'Roboto', sans-serif;
        background-color: #f4f6f9;
        margin: 0;
        padding: 0;
    }

.navbar {
    background-color: #181818;
    position: fixed;
    top: 0;
    width: 100%;
    z-index: 1;
    padding: 10px 50px;
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.navbar a, .navbar input {
    color: white;
    text-decoration: none;
    font-size: 16px;
    margin-right: 20px;
}


a{
color: white;
text-decoration: none;
}

.navbar input[type="text"] {
    padding: 6px;
    border: none;
    font-size: 14px;
    width: 250px;
    border-radius: 20px;
    background-color: #333;
    color: white;
}

.navbar img {
    width: 25px;
    height: 25px;
}


    .sidebar {
        background-color: #121212;
        height: 100vh;
        padding-top: 20px;
        position: fixed;
        width: 220px;
        color: #fff;
    }

    .sidebar .nav-link {
        color: #bbb;
        font-size: 16px;
        margin: 10px 0;
        transition: background-color 0.3s ease;
        border-radius: 5px;
    }

    .sidebar .nav-link:hover, .sidebar .nav-link.active {
        color: #fff;
        background-color: #1db954;
    }

    .sidebar .nav-link i {
        margin-right: 12px;
    }

    main {
        margin-left: 220px;
        padding: 20px;
        background-color: #f8f9fa;
    }

    .table {
        background-color: #fff;
        border-radius: 10px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    }

    .table th, .table td {
        vertical-align: middle;
        padding: 12px 15px;
    }

    .table th {
        background-color: #f4f6f9;
        font-weight: 600;
    }

	.table img{
	width: 7%;
    height:7%;
    border-radius: 50%;
    margin-right:10px;
	}
    .btn {
        font-size: 14px;
        border-radius: 50px;
        padding: 6px 20px;
        transition: background-color 0.3s ease;
    }

    .btn-outline-success:hover {
        background-color: #1db954;
        color: #fff;
    }

    .btn-success, .btn-danger {
        border: none;
        padding: 6px 15px;
        border-radius: 50px;
        transition: background-color 0.3s ease;
    }

    .btn-success:hover {
        background-color: #17a04e;
    }

    .btn-danger:hover {
        background-color: #e60023;
    }

    .table-striped tbody tr:hover {
        background-color: #f1f1f1;
    }

    .actions {
        display: flex;
        gap: 10px;
    }
</style>
</head>
<body>

 <div class="navbar">
        <div>
            <a href="home" class="logo">MUSIC 4 U</a>
              
            <a href="home"><img src="${pageContext.request.contextPath}/template/views/icons/right-arrow.png"></a>
            <a href="home"><img src="${pageContext.request.contextPath}/template/views/icons/left-arrow.png"></a>
            <a href="home"><img src="${pageContext.request.contextPath}/template/views/icons/home2.png"></a>
            	
        </div>
         <form action="admin" class="d-flex" method="get">
					 <input type="hidden" name="mode" value="SEARCHARTIST"> 
						
						 <input type="text" placeholder="Search..." name="query" >
						
						<button  class="btn" type="submit"><img  class="search" src="${pageContext.request.contextPath}/template/views/icons/search2.png"></button>
		</form>
        
                
        
        
        <c:if test="${sessionScope.user.role eq 'admin' }">
        <a  href="admin" > Admin Panel </a>
        </c:if>
       <a>
           <c:if test="${not empty sessionScope.user}">
              ${sessionScope.user.email}
              <c:if test="${sessionScope.user.role eq 'admin' }">(Admin)</c:if>
             </c:if>
             
             </a>
        
        
         <c:if test="${not empty sessionScope.user}">
              <a  href="login?mode=LOGOUT" > Logout </a>
          </c:if>    
          
           <c:if test="${empty sessionScope.user || sessionScope.user.role ne 'admin'}">
    <c:redirect url="login"/>
</c:if>
             
        
             
    </div>

<div class="container-fluid">
    <div class="row">
        <!-- Sidebar -->
        <nav class="sidebar d-flex flex-column">
            <h4 class="text-center mb-4">Admin Panel</h4>
            <ul class="nav flex-column px-3">
            <li class="nav-item">
                    <a class="nav-link" href="home"><i class="fas fa-home"></i> Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link " href="artist"><i class="fas fa-users"></i>Add Artist</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="album"><i class="fas fa-music"></i>Add Album</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="song"><i class="fas fa-compact-disc"></i>Add Songs</a>
                </li>
                
            </ul>
        </nav>

        <!-- Main Content -->
        <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4">
              <h2 class="mb-4 mt-5">Artist Management</h2>
              <div class="mt-4 mb-4">
                <a href="admin?mode=ARTIST" class="btn btn-outline-success">Artist</a>
                <a href="admin?mode=ALBUM" class="btn btn-outline-success">Album</a>
                <a href="admin?mode=SONG" class="btn btn-outline-success">Song</a>
                <a href="home" class="btn btn-outline-success">Home</a>
            </div>
            <table class="table table-striped">
        
                <thead>
                    <tr>
                    	
                        <th>ID</th>
                        <th>Name</th>
						<th>Description</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="artist" items="${artists}">
                        <tr>
                            <td>${artist.id}</td>
                           	<td><img  src="${pageContext.request.contextPath}/template/artist/artistImg/${artist.image}" >${artist.name}</td>
                            <th>Artist</th>
                            <td><a class="btn btn-danger" data-bs-toggle="modal"
									data-bs-target="#deleteModal${artist.id}">DELETE</a>
								<td>
								
                            
                        </tr>
                    </c:forEach>
                </tbody>
                <tfoot>
                    <tr>
                    	
                        <th>ID</th>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Action</th>
                    </tr>
                </tfoot>
            </table>
           
        </main>
        <c:forEach var="artist" items="${artists}">
			
				<div class="modal fade" id="deleteModal${artist.id}" tabindex="-1"
			aria-labelledby="deleteModalLabel${artist.id}" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="deleteModalLabel${artist.id}">Confirm
							Delete</h5>
						<button type="button" class="btn-close" data-bs-dismiss="modal"
							aria-label="Close"></button>
					</div>
					<div class="modal-body">Are you sure you want to delete this
						Artist?</div>

					<c:url var="deleteLink" value="admin">
						<c:param name="mode" value="DELARTIST" />
						<c:param name="id" value="${artist.id}" />
					</c:url>
					
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							data-bs-dismiss="modal">Cancel</button>
						<a href="${deleteLink }"
							class="btn btn-danger">Delete</a>
					</div>
				</div>
			</div>
		</div>
		    </c:forEach>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
