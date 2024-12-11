package com.music4u.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;


import com.music4u.dao.AlbumDAO;
import com.music4u.dao.ArtistDAO;
import com.music4u.dao.SongDAO;
import com.music4u.dao.UserDAO;
import com.music4u.models.Album;
import com.music4u.models.Artist;
import com.music4u.models.Song;
import com.music4u.models.User;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/home")
public class PageController  extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource(name="jdbc/music4u_site")
	private DataSource dataSource;
	
	private UserDAO userDAO;
	private SongDAO songDAO;
	private ArtistDAO artistDAO;
	private AlbumDAO albumDAO;
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		userDAO = new UserDAO(dataSource);
		songDAO = new SongDAO(dataSource);
		artistDAO = new ArtistDAO(dataSource);
		albumDAO = new AlbumDAO(dataSource);
	}


	public PageController() {
		
	}
	
	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String mode = req.getParameter("mode");
		
		if(mode == null)
		{
			mode = "HOME";
		}
		
		switch(mode) {
		
		case "HOME":
			showHomePage(req, resp);
			break;
		case "ALBUM":
			showAlbum(req, resp);
			break;
			
		case "SONGLIST":
			playSong(req, resp);
			break;
		case "SEARCH":
			search(req, resp);
			break;
		
		default:
			showHomePage(req, resp);
			break;
		}
	}
	
	
	private void search(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    // Get the search query from the request
	    String query = req.getParameter("query");

	    // If the query is null, show the home page and stop further execution
	    if (query == null || query.isEmpty()) {
	        showHomePage(req, resp);
	        return;
	    }

	    // Fetch all artists, albums, and songs from the database
	    List<Artist> artists = artistDAO.getAllArtist(); 
	    List<Album> albums = albumDAO.getAllAlbum(); 
	    List<Song> songs = songDAO.getAllSong(); 

	    // Filter the artists and albums based on the search query
	    List<Artist> filteredArtists = artists.stream()
	                                          .filter(artist -> artist.getName().contains(query))
	                                          .toList();
	    List<Album> filteredAlbums = albums.stream()
	                                       .filter(album -> album.getName().contains(query))
	                                       .toList();

	    // Create a map of artist IDs to artist names for quick lookup
	    Map<Long, String> artistMap = new HashMap<>();
	    for (Artist artist : artists) {
	        artistMap.put(artist.getId(), artist.getName());
	    }

	    // Add artist names to the filtered albums
	    for (Album album : albums) {
	        String artistName = artistMap.get(album.getArtistId()); // Assuming getArtistId() gives the artist ID for the album
	        album.setArtistName(artistName); // Assuming the Album class has a setArtistName method
	    }

	    // Set the filtered artists and albums as attributes for the JSP
	    req.setAttribute("query", query);
	    req.setAttribute("artists", filteredArtists);
	    req.setAttribute("albums", filteredAlbums);

	    // Forward the request to the index.jsp page
	    RequestDispatcher dispatcher = req.getRequestDispatcher("template/views/index.jsp");
	    dispatcher.forward(req, resp);
	}


	
	
	protected void showHomePage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    List<Artist> artists = artistDAO.getAllArtist(); 
	    List<Album> albums = albumDAO.getAllAlbum(); 
	    List<Song> songs = songDAO.getAllSong(); 

	    // Create a Map for quick lookup of artist names by ID
	    Map<Long, String> artistMap = new HashMap<>();
	    for (Artist artist : artists) {
	        artistMap.put(artist.getId(), artist.getName());
	    }

	    // Add artist names to albums
	    for (Album album : albums) {
	        String artistName = artistMap.get(album.getArtistId()); // Assuming getArtistId() gives the artist ID for the album
	        album.setArtistName(artistName); // Assuming you have a setter for artistName in the Album class
	    }

	    // Set attributes for the request
	    req.setAttribute("artists", artists);
	    req.setAttribute("albums", albums);
	    req.setAttribute("songs", songs);
	    
	    // Forward the request to the JSP page
	    RequestDispatcher dispatcher = req.getRequestDispatcher("template/views/index.jsp");
	    dispatcher.forward(req, resp);
	}

	
 protected void showAlbum(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		Long artist_id = Long.parseLong(req.getParameter("artist_id"));
		List<Artist> artists = artistDAO.getAllArtist(); 
		List<Album> albums = albumDAO.getAlbumByArtistId(artist_id);
		List<Song>songs = songDAO.getAllSong();
		
		
		 // Create a Map for quick lookup of artist names by ID
	    Map<Long, String> artistMap = new HashMap<>();
	    for (Artist artist : artists) {
	        artistMap.put(artist.getId(), artist.getName());
	    }

	    // Add artist names to albums
	    for (Album album : albums) {
	        String artistName = artistMap.get(album.getArtistId()); // Assuming getArtistId() gives the artist ID for the album
	        album.setArtistName(artistName); // Assuming you have a setter for artistName in the Album class
	    }

		
		req.setAttribute("artists", artists);
		req.setAttribute("albums", albums);
		req.setAttribute("songs", songs);
		req.setAttribute("artist_id", artist_id);
		RequestDispatcher dispatcher = req.getRequestDispatcher("template/views/index.jsp");
		dispatcher.forward(req,resp);
		
	}

 
 	protected void playSong(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
 		
		Long album_id = Long.parseLong(req.getParameter("album_id"));
		List<Artist> artists = artistDAO.getAllArtist(); 
		List<Album> albums = albumDAO.getAllAlbum();
		List<Song>songs = songDAO.getSongByAlbumId(album_id);
		
		
		 // Create a Map for quick lookup of artist names by ID
	    Map<Long, String> artistMap = new HashMap<>();
	    for (Artist artist : artists) {
	        artistMap.put(artist.getId(), artist.getName());
	    }

	    // Add artist names to albums
	    for (Album album : albums) {
	        String artistName = artistMap.get(album.getArtistId()); // Assuming getArtistId() gives the artist ID for the album
	        album.setArtistName(artistName); // Assuming you have a setter for artistName in the Album class
	    }

		
		req.setAttribute("artists", artists);
		req.setAttribute("albums", albums);
		req.setAttribute("songs", songs);
 		RequestDispatcher dispatcher = req.getRequestDispatcher("template/views/musicplayer.jsp");
		dispatcher.forward(req,resp);
 	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
	
	

}
