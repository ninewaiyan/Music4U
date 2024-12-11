<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link
    href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
    rel="stylesheet">
    
<title>MUSIC4U</title>
<style>
body {
    font-family: 'Helvetica Neue', sans-serif;
    margin: 0;
    display: flex;
    background-color: #121212;
    color: #FFF;
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
    height: 100%;
    width: 100px;
    position: fixed;
    z-index: 1;
    top: 50px;
    left: 0;
    background-color: #000;
    transition: 0.5s;
    padding-top: 20px;
    overflow-y: auto;
}

.sidebar.expanded {
    width: 300px;
}

.sidebar a {
    padding: 15px;
    text-decoration: none;
    font-size: 20px;
    color: #b3b3b3;
    display: flex;
    align-items: center;
    margin-bottom: 15px;
}

.sidebar a:hover {
    color: #FFF;
}

.sidebar img {
    width: 60px;
    height: 60px;
    border-radius: 50%;
    margin-right: 10px;
}

.sidebar .sidebar-text {
    display: none;
}

.sidebar.expanded .sidebar-text {
    display: inline;
}

#main {
    flex: 1;
    margin-left: 100px;
    padding: 20px;
    margin-top: 60px;
    transition: margin-left 0.5s;
}

.sidebar.expanded+#main {
    margin-left: 300px;
}

.song-list {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    gap: 20px;
}

.song-entry {
    background-color: #333;
    padding: 15px;
    border-radius: 8px;
    display: flex;
    align-items: center;
    transition: background-color 0.3s;
}

.song-entry:hover {
    background-color: #444;
}

.song-entry img {
    width: 70px;
    height: 70px;
    border-radius: 8px;
    margin-right: 15px;
}

.song-entry .details {
    flex-grow: 1;
}

.song-entry .title {
    font-size: 16px;
    font-weight: bold;
}

.song-entry .artist {
    font-size: 14px;
    color: #b3b3b3;
}

.card-container {
    display: grid;
    grid-template-columns: repeat(5, 1fr);
    gap: 20px;
}

.card {
    background-color: #333;
    padding: 20px;
    border-radius: 12px;
    text-align: center;
    transition: background-color 0.3s;
}

.card:hover {
    background-color: #444;
}

.card img {
    width: 100%;
    height:100%;
    border-radius: 12px;
}

.card h3 {
    font-size: 16px;
    color: #FFF;
    margin-top: 10px;
}

@media (max-width: 768px) {
    .song-list {
        grid-template-columns: repeat(2, 1fr);
    }
    .card-container {
        grid-template-columns: repeat(2, 1fr);
    }
}

@media (max-width: 576px) {
    .song-list, .card-container {
        grid-template-columns: 1fr;
    }
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
         <form action="home" class="d-flex" method="post">
					 <input type="hidden" name="mode" value="SEARCH"> 
						
						 <input type="text" placeholder="Search..." name="query" value="${query }">
						
						<button  class="btn" type="submit"><img  class="search" src="${pageContext.request.contextPath}/template/views/icons/search2.png"></button>
		</form>
        
                
        
        
        <c:if test="${sessionScope.user.role eq 'admin' }">
        <a  href="admin" > Admin Panel </a>
        </c:if>
       
           <c:if test="${not empty sessionScope.user}">
              ${sessionScope.user.email}
              <c:if test="${sessionScope.user.role eq 'admin' }">(Admin)</c:if>
             </c:if>
        
        
        <c:if test="${not empty sessionScope.user}">
              <a  href="login?mode=LOGOUT" > Logout </a>
          </c:if>    
          
         
             
        
             
    </div>

    <div id="mySidebar" class="sidebar">
        <a href="#" id="library-icon">
            <span class="toggle-btn" onclick="toggleNav()">
                <img src="${pageContext.request.contextPath}/template/views/icons/library1.png">
            </span>
            <span class="sidebar-text">Your Library</span>
        </a>
        <c:forEach var="artist" items="${artists}">
            <a href="home?mode=ALBUM&artist_id=${artist.id }">
                <img src="${pageContext.request.contextPath}/template/artist/artistImg/${artist.image}">
                <span class="sidebar-text">${artist.name}</span>
            </a>
        </c:forEach>
    </div>

    <div id="main">
        <div class="song-list">
        <a href="home?mode=SONGLIST&album_id=1" >
            <div class="song-entry mb-4">
                <img src="${pageContext.request.contextPath}/template/views/song.jpg" alt="Album Cover">
                <div class="details">
                    <div class="title">Song Title</div>
                    <div class="artist">Artist Name</div>
                </div>
            </div>
           </a>
            
            <a href="home?mode=SONGLIST&album_id=1">
            <div class="song-entry mb-4">
                <img src="${pageContext.request.contextPath}/template/views/song.jpg" alt="Album Cover">
                <div class="details">
                    <div class="title">Song Title</div>
                    <div class="artist">Artist Name</div>
                </div>
            </div>
           </a>
           <a href="home?mode=SONGLIST&album_id=1" >
            <div class="song-entry mb-4">
                <img src="${pageContext.request.contextPath}/template/views/song.jpg" alt="Album Cover">
                <div class="details">
                    <div class="title">Song Title</div>
                    <div class="artist">Artist Name</div>
                </div>
            </div>
           </a>
           <a href="home?mode=SONGLIST&album_id=1" >
            <div class="song-entry mb-4">
                <img src="${pageContext.request.contextPath}/template/views/song.jpg" alt="Album Cover">
                <div class="details">
                    <div class="title">Song Title</div>
                    <div class="artist">Artist Name</div>
                </div>
            </div>
           </a>
           <a href="home?mode=SONGLIST&album_id=1" >
            <div class="song-entry mb-4">
                <img src="${pageContext.request.contextPath}/template/views/song.jpg" alt="Album Cover">
                <div class="details">
                    <div class="title">Song Title</div>
                    <div class="artist">Artist Name</div>
                </div>
            </div>
           </a>
           
           <a href="home?mode=SONGLIST&album_id=1" >
            <div class="song-entry mb-4">
                <img src="${pageContext.request.contextPath}/template/views/song.jpg" alt="Album Cover">
                <div class="details">
                    <div class="title">Song Title</div>
                    <div class="artist">Artist Name</div>
                </div>
            </div>
           </a>
          
	       <a href="home?mode=SONGLIST&album_id=1" >
            <div class="song-entry mb-4">
                <img src="${pageContext.request.contextPath}/template/views/song.jpg" alt="Album Cover">
                <div class="details">
                    <div class="title">Song Title</div>
                    <div class="artist">Artist Name</div>
                </div>
            </div>
           </a>
           
            <a href="home?mode=SONGLIST&album_id=1" >
            <div class="song-entry mb-4">
                <img src="${pageContext.request.contextPath}/template/views/song.jpg" alt="Album Cover">
                <div class="details">
                    <div class="title">Song Title</div>
                    <div class="artist">Artist Name</div>
                </div>
            </div>
           </a>
            <!-- Repeat similar song entries here -->
        </div>
		
        <div class="card-container">
            <c:forEach var="album" items="${albums}">
            
                <div class="card">
                
                    <img src="${pageContext.request.contextPath}/template/album/album_img/${album.image}" alt="Album Cover">
                  	
                    <a href="home?mode=SONGLIST&album_id=${album.id }" class="mt-3">${album.name}</a>
                    <p>${album.artistName}
                </div>
               
            </c:forEach>
            
        </div>
        
    </div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
<script>
    function toggleNav() {
        var sidebar = document.getElementById("mySidebar");
        sidebar.classList.toggle("expanded");
    }
</script>
</body>
</html>
