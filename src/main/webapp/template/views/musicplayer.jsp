<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css"
	rel="stylesheet">
<!-- Font Awesome for icons -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">

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

a {
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
	width: 250px;
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
	height: 100%;
	border-radius: 12px;
}

.card h3 {
	font-size: 16px;
	color: #FFF;
	margin-top: 10px;
}

@media ( max-width : 768px) {
	.song-list {
		grid-template-columns: repeat(2, 1fr);
	}
	.card-container {
		grid-template-columns: repeat(2, 1fr);
	}
}

@media ( max-width : 576px) {
	.song-list, .card-container {
		grid-template-columns: 1fr;
	}
}

////////////
.player-container {
	background-color: #1e1e1e;
	border-radius: 10px;
	padding: 20px;
	diplay: block;
}

.song-list {
	display: block;
}

.song-list li {
	padding: 10px;
	cursor: pointer;
	border-bottom: 1px solid #333;
}

.song-list li:hover {
	background-color: #333;
}

.controls {
	display: flex;
	justify-content: center;
	align-items: center;
	margin-top: 20px;
}

.controls button {
	background-color: transparent;
	border: none;
	color: white;
	margin: 0 15px;
	font-size: 24px;
}

.controls button:hover {
	color: #1DB954;
}

.progress-container {
	margin-top: 10px;
}

.progress-bar-custom {
	background-color: #1DB954;
	height: 5px;
}

.song-title {
	font-size: 20px;
	font-weight: bold;
	text-align: center;
	margin-bottom: 15px;
}

.song-list li.active {
	background-color: #1DB954;
}
</style>
</head>
<body>
	<div class="navbar">
		<div>
			<a href="home" class="logo">MUSIC 4 U</a> <a href="home"><img
				src="${pageContext.request.contextPath}/template/views/icons/right-arrow.png"></a>
			<a href="home"><img
				src="${pageContext.request.contextPath}/template/views/icons/left-arrow.png"></a>
			<a href="home"><img
				src="${pageContext.request.contextPath}/template/views/icons/home2.png"></a>

		</div>
		 <form action="home" class="d-flex" method="post">
					 <input type="hidden" name="mode" value="SEARCH"> 
						
						 <input type="text" placeholder="Search..." name="query" value="${query }">
						
						<button  class="btn" type="submit"><img  class="search" src="${pageContext.request.contextPath}/template/views/icons/search2.png"></button>
		</form>

		<c:if test="${sessionScope.user.role eq 'admin' }">
			<a href="admin"> Admin Panel </a>
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
		<a href="#" id="library-icon"> <span class="toggle-btn"
			onclick="toggleNav()"> <img
				src="${pageContext.request.contextPath}/template/views/icons/library1.png">
		</span> <span class="sidebar-text">Your Library</span>
		</a>
		<c:forEach var="artist" items="${artists}">
			<a href="home?mode=ALBUM&artist_id=${artist.id }"> <img
				src="${pageContext.request.contextPath}/template/artist/artistImg/${artist.image}">
				<span class="sidebar-text">${artist.name}</span>
			</a>
		</c:forEach>
	</div>

	<div class="container mt-5">
		<div class="row justify-content-center">

			<div class="col-md-2 "></div>

			<div class="col-md-8 ">

				<ul class="song-list mt-4" id="song-list">
					<!-- Song list will be generated dynamically -->
				</ul>
				
				<div class="player-container col-md-  ">

					<!-- Song Title -->
					<div class="song-title" id="song-title">Song Title</div>

					<!-- Audio Player -->
					<audio id="audio-player" src="songs/song1.mp3"></audio>

					<!-- Player Controls -->
					<div class="controls">
						<button id="prev-btn">
							<i class="fas fa-step-backward"></i>
						</button>
						<button id="play-btn">
							<i class="fas fa-play"></i>
						</button>
						<button id="next-btn">
							<i class="fas fa-step-forward"></i>
						</button>
					</div>

					<!-- Progress Bar -->
					<div class="progress-container">
						<input type="range" id="progress-bar"
							class="form-range progress-bar-custom" value="0" max="100">
					</div>

				</div>

			</div>
			
			 
            

			<div class="col-md-2 ">
			
			
			</div>

		</div>
		
	</div>
	


	
	
	

	<!-- Bootstrap JS -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>

	<script>
    const audioPlayer = document.getElementById("audio-player");
    const playBtn = document.getElementById("play-btn");
    const prevBtn = document.getElementById("prev-btn");
    const nextBtn = document.getElementById("next-btn");
    const progressBar = document.getElementById("progress-bar");
    const songTitle = document.getElementById("song-title");
    const songListElement = document.getElementById("song-list");

    // Array of songs (Replace with your songs)
    const songs = [
        <c:forEach var="song" items="${songs}">
            { title: "${song.name}", file: "${pageContext.request.contextPath}/template/song/songFiles/${song.path}" },
        </c:forEach>
    ];

    let currentSongIndex = 0;
    let isPlaying = false;

    // Load song
    function loadSong(song) {
        songTitle.innerText = song.title;
        audioPlayer.src = song.file;
        updateActiveSong();
    }

    // Play song
    function playSong() {
        audioPlayer.play();
        playBtn.innerHTML = '<i class="fas fa-pause"></i>';
        isPlaying = true;
    }

    // Pause song
    function pauseSong() {
        audioPlayer.pause();
        playBtn.innerHTML = '<i class="fas fa-play"></i>';
        isPlaying = false;
    }

    // Toggle play/pause
    playBtn.addEventListener("click", () => {
        isPlaying ? pauseSong() : playSong();
    });

    // Next song
    nextBtn.addEventListener("click", () => {
        currentSongIndex = (currentSongIndex + 1) % songs.length;
        loadSong(songs[currentSongIndex]);
        playSong();
    });

    // Previous song
    prevBtn.addEventListener("click", () => {
        currentSongIndex = (currentSongIndex - 1 + songs.length) % songs.length;
        loadSong(songs[currentSongIndex]);
        playSong();
    });

    // Update progress bar
    audioPlayer.addEventListener("timeupdate", () => {
        const progressPercent = (audioPlayer.currentTime / audioPlayer.duration) * 100;
        progressBar.value = progressPercent;
    });

    // Change song time on progress bar input
    progressBar.addEventListener("input", () => {
        const newTime = (progressBar.value * audioPlayer.duration) / 100;
        audioPlayer.currentTime = newTime;
    });

    // Detect when the song ends and play the next one
    audioPlayer.addEventListener("ended", () => {
        currentSongIndex = (currentSongIndex + 1) % songs.length;
        loadSong(songs[currentSongIndex]);
        playSong();
    });

    // Load the initial song
    loadSong(songs[currentSongIndex]);

    // Generate song list
    songs.forEach((song, index) => {
        const li = document.createElement("li");
        li.innerText = song.title;
        li.addEventListener("click", () => {
            currentSongIndex = index;
            loadSong(songs[currentSongIndex]);
            playSong();
        });
        songListElement.appendChild(li);
    });

    // Update active song in the list
    function updateActiveSong() {
        const listItems = songListElement.querySelectorAll("li");
        listItems.forEach((item, index) => {
            item.classList.remove("active");
            if (index === currentSongIndex) {
                item.classList.add("active");
            }
        });
    }
</script>

	<script>
    function toggleNav() {
        var sidebar = document.getElementById("mySidebar");
        sidebar.classList.toggle("expanded");
    }
</script>
</body>
</html>
