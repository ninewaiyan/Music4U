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
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin")
public class AdminController extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource(name="jdbc/music4u_site")
	private DataSource dataSource;
	
	private UserDAO userDAO;
	private ArtistDAO artistDAO;
	private AlbumDAO albumDAO;
	private SongDAO songDAO;
	
	
	
	
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		userDAO = new UserDAO(dataSource);
		artistDAO = new ArtistDAO(dataSource);
		albumDAO = new AlbumDAO(dataSource);
		songDAO = new SongDAO(dataSource);
		
	}


	public AdminController() {
		
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String mode = req.getParameter("mode");
		if(mode == null) {
			mode = "PORTAL";
		}
		
		switch(mode) {
		case "PORTAL":
			showPortal(req, resp);
			break;
			
		case "DISABLE":
			disable(req, resp);
			break;
			
		case "ENABLE":
			enable(req, resp);
			break;
		case "SEARCH":
			
			search(req, resp);
			break;
		case "ARTIST":
			artistManage(req, resp);
			break;
		case "ALBUM":
			albumManage(req, resp);
			break;
		case "SONG":
			songManage(req, resp);
			break;
		
		case "DELARTIST":
			deleteArtist(req, resp);
			break;
		case "DELALBUM":
			deleteAlbum(req, resp);
			break;
		case "DELSONG":
			deleteSong(req, resp);
			break;
		case "SEARCHARTIST":
			searchArtist(req, resp);
			break;
		
		case "SEARCHALBUM":
			searchAlbum(req, resp);
			break;
		case "SEARCHSONG":
			searchSong(req, resp);
			break;
		default:
			showPortal(req, resp);
			break;
		}
	}
	
	protected void enable(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long userId = Long.parseLong(req.getParameter("userId"));
		boolean ok = userDAO.enableUser(userId);
		
		if(ok) {
			showPortal(req, resp);
		}else {
			System.out.println("user enable action is filed");
		}
	}
	
	protected void disable(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long userId = Long.parseLong(req.getParameter("userId"));
		boolean ok = userDAO.disableUser(userId);
		
		if(ok) {
			showPortal(req, resp);
		}else {
			System.out.println("user disable  atcion is filed");
		}
	}
	
	protected void showPortal(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	List<User>users = userDAO.getAllUsers();
	req.setAttribute("users", users);
	RequestDispatcher dispatcher = req.getRequestDispatcher("template/admin/admin-portal.jsp");
	dispatcher.forward(req, resp);
		

	}
	
	protected void deleteArtist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    // Get the artist id from request
	    long artistId = Long.parseLong(req.getParameter("id"));
	    
	    // Step 1: Soft delete the artist
	    boolean ok = artistDAO.deleteArtist(artistId);
	    
	    // Step 2: Soft delete albums by artist ID
	    ok = albumDAO.deleteAlbumByArtistId(artistId);
	    
	    // Step 3: Get all deleted albums (to soft delete their songs)
	    List<Album> deletedAlbums = albumDAO.getAllDeletedAlbum();
	    
	    // Step 4: Loop through each deleted album and delete songs associated with it
	    for (Album deletedAlbum : deletedAlbums) {
	        long albumId = deletedAlbum.getId();
	        boolean deletedSongs = songDAO.deleteSongByAlbumId(albumId);
	        
	        // Optionally, check if songs were successfully deleted and log any issues
	        if (!deletedSongs) {
	            System.out.println("Error deleting songs for album ID: " + albumId);
	        }
	    }
	    
	    // Step 5: If the artist, albums, and songs were successfully deleted, redirect to artist management page
	    if (ok) {
	        List<Artist> artists = artistDAO.getAllArtist(); // Get the updated list of active artists
	        req.setAttribute("artists", artists); // Set the list as a request attribute
	        
	        // Forward to artist management JSP page
	        RequestDispatcher dispatcher = req.getRequestDispatcher("template/admin/artistManage.jsp");
	        dispatcher.forward(req, resp);
	    } else {
	        // Handle error cases if needed
	    	artistManage(req, resp);
	    	System.out.println("Error");
	    }
	}

	
	protected void deleteAlbum(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	long id = Long.parseLong(req.getParameter("id"));
	boolean ok = albumDAO.deleteAlbum(id);
	ok = songDAO.deleteSongByAlbumId(id);
	
	if(ok) {
		
		albumManage(req, resp);
	}else {
		albumManage(req, resp);
		System.out.println("error");
	}
	

	}
	
	protected void deleteSong(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	long id = Long.parseLong(req.getParameter("id"));
	boolean ok = songDAO.deleteSong(id);
	
	if(ok) {
		
		songManage(req, resp);
	}
	

	}
	
	
	protected void artistManage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	List<Artist>artists = artistDAO.getAllArtist();
	req.setAttribute("artists", artists);
	RequestDispatcher dispatcher = req.getRequestDispatcher("template/admin/artistManage.jsp");
	dispatcher.forward(req, resp);
		

	}
	protected void songManage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		// Fetch all songs and related artist/album names
		List<Song>songs = songDAO.getAllSongsWithDetails();
		// Set songs in the request attribute
		req.setAttribute("songs", songs);

		// Forward to the JSP
		RequestDispatcher dispatcher = req.getRequestDispatcher("template/admin/songManage.jsp");
		dispatcher.forward(req, resp);

		

	}
	
	protected void albumManage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		List<Artist> artists = artistDAO.getAllArtist(); 
	    List<Album> albums = albumDAO.getAllAlbum(); 
	    Map<Long, String> artistMap = new HashMap<>();
	    for (Artist artist : artists) {
	        artistMap.put(artist.getId(), artist.getName());
	    }

	    // Add artist names to albums
	    for (Album album : albums) {
	        String artistName = artistMap.get(album.getArtistId()); // Assuming getArtistId() gives the artist ID for the album
	        album.setArtistName(artistName); // Assuming you have a setter for artistName in the Album class
	    }
	
	req.setAttribute("albums", albums);
	RequestDispatcher dispatcher = req.getRequestDispatcher("template/admin/albumManage.jsp");
	dispatcher.forward(req, resp);
		
	}
	
	private void search(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String query = req.getParameter("query");
		System.out.println(query);
		if (query == null) {
			showPortal(req, resp);
		}
		List<User> users = userDAO.getAllUsers();
		List<User> filteredUsers = users.stream()
		    .filter(e -> e.getName().contains(query) || e.getEmail().contains(query))
		    .toList();
		req.setAttribute("users", filteredUsers);
		req.setAttribute("query",query);
		RequestDispatcher dispatcher = req.getRequestDispatcher("template/admin/admin-portal.jsp");
		dispatcher.forward(req, resp);

	}
	
	private void searchArtist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String query = req.getParameter("query");
		if (query == null) {
			artistManage(req, resp);
		}
		List<Artist> artists = artistDAO.getAllArtist();
		List<Artist> filteredArtists = artists.stream()
		    .filter(e -> e.getName().contains(query))
		    .toList();
		req.setAttribute("query",query);
		req.setAttribute("artists", filteredArtists);
		RequestDispatcher dispatcher = req.getRequestDispatcher("template/admin/artistManage.jsp");
		dispatcher.forward(req, resp);
	}
	

	
	private void searchSong(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    String query = req.getParameter("query");

	    // Check if query is null or empty
	    if (query == null || query.trim().isEmpty()) {
	        songManage(req, resp); // Redirect to song management if no query
	        return; // Exit the method to prevent further execution
	    }

	    List<Song> songs;
	    try {
	        songs = songDAO.getAllSongsWithDetails(); // Fetch all songs
	    } catch (Exception e) {
	        // Handle error, e.g., log the exception
	        e.printStackTrace();
	        req.setAttribute("error", "Unable to fetch songs."); // Set an error attribute if needed
	        req.getRequestDispatcher("template/admin/songManage.jsp").forward(req, resp); // Forward with error
	        return; // Exit after forwarding
	    }

	    // Filter songs based on the query
	    List<Song> filteredSong = songs.stream()
	        .filter(e -> e.getName().toLowerCase().contains(query.toLowerCase())) // Case-insensitive search
	        .toList();
	    req.setAttribute("query",query);
	    req.setAttribute("songs", filteredSong); // Set the filtered songs as a request attribute
	    RequestDispatcher dispatcher = req.getRequestDispatcher("template/admin/songManage.jsp");
	    dispatcher.forward(req, resp); // Forward to the song management view
	}

	
	private void searchAlbum(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String query = req.getParameter("query");
		if (query == null) {
			albumManage(req, resp);
		}
		
		List<Artist> artists = artistDAO.getAllArtist(); 
	    List<Album> albums = albumDAO.getAllAlbum(); 
	    Map<Long, String> artistMap = new HashMap<>();
	    for (Artist artist : artists) {
	        artistMap.put(artist.getId(), artist.getName());
	    }

	    // Add artist names to albums
	    for (Album album : albums) {
	        String artistName = artistMap.get(album.getArtistId()); // Assuming getArtistId() gives the artist ID for the album
	        album.setArtistName(artistName); // Assuming you have a setter for artistName in the Album class
	    }
	   
		List<Album> filteredAlbum = albums.stream()
		    .filter(e -> e.getName().contains(query))
		    .toList();
		req.setAttribute("query",query);
		req.setAttribute("albums", filteredAlbum);
		RequestDispatcher dispatcher = req.getRequestDispatcher("template/admin/albumManage.jsp");
		dispatcher.forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}

	
}
