package com.music4u.controllers;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.sql.DataSource;

import com.music4u.dao.AlbumDAO;
import com.music4u.dao.ArtistDAO;
import com.music4u.models.Album;
import com.music4u.models.Artist;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@MultipartConfig(
	    location = "C:/JavaEE_Workspace/music4u/src/main/webapp/template/album/album_img",
	    fileSizeThreshold = 1024 * 1024, // 1 MB
	    maxFileSize = 1024L * 1024L * 10000L, // 10,000 MB (use 'L' for large values)
	    maxRequestSize = 1024L * 1024L * 10000L // 10,000 MB
	)
@WebServlet("/album")
public class AlbumController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Set<String> ALLOWED_MIME_TYPES = new HashSet<>();

	// Initialize allowed media types (image and video)
	static {
		ALLOWED_MIME_TYPES.add("image/jpeg");
		ALLOWED_MIME_TYPES.add("image/png");
		ALLOWED_MIME_TYPES.add("image/jpg");
	}

	
	@Resource(name="jdbc/music4u_site")
	private DataSource dataSource;
	
	private AlbumDAO albumDAO;
	private ArtistDAO artistDAO;
	
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
	 albumDAO = new AlbumDAO(dataSource);
	 artistDAO = new ArtistDAO(dataSource);
	}

	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
      String mode = req.getParameter("mode");
		
		if(mode == null)
		{
			mode = "FORM";
		}
		
		switch(mode) {
		
		case "FORM":
			showRegisterForm(req, resp);
			break;
		case "CREATE":
			create(req, resp);
			break;
			
		default:
			showRegisterForm(req, resp);
			break;
		}
	}
	
	
	private void create(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Long artist_id = Long.parseLong(req.getParameter("artist_id"));
		String name = req.getParameter("name");
		String message;
		String fileName;
		
		System.out.println(name + artist_id);
	
		try {
			Part part = req.getPart("image");
			fileName = getFileName(part);
			System.out.println(fileName);
			String mimeType = part.getContentType();
		
			if (!ALLOWED_MIME_TYPES.contains(mimeType)) {
				req.setAttribute("ok", false);
				req.setAttribute("name", name);
				req.setAttribute("message", "Unsupported file type:  or no File Upload !!");
				req.getRequestDispatcher("album?mode=FORM").forward(req, resp);
				return;
			}

			String uniqueFileName = generateUniqueFileName(fileName);
			// Write the file
			part.write(uniqueFileName);

			Album newAlbum = new Album(name,uniqueFileName,artist_id);

			Boolean ok = albumDAO.createAlbum(newAlbum);
			if (ok) {
				req.setAttribute("ok", ok);
				req.setAttribute("message", "Artist is successfully created !!");
				req.getRequestDispatcher("album?mode=FORM").forward(req, resp);
			} else {
				req.setAttribute("ok", ok);
				message = "Create Fail";
				req.setAttribute("name", name);
				req.setAttribute("message", message);
				req.getRequestDispatcher("album?mode=FORM").forward(req, resp);

			}

		} catch (Exception ex) {
			System.out.println(ex);
			message = " Error uploading file :"  ;
			req.setAttribute("ok",false);
			req.setAttribute("name", name);
			req.setAttribute("message", message);
			req.getRequestDispatcher("album?mode=FORM").forward(req, resp);
		}

		
			
	}
	
	private String getFileName(Part part) {
		String contentDisposition = part.getHeader("content-disposition");
		if (contentDisposition.contains("filename=")) {
			return contentDisposition
					.substring(contentDisposition.indexOf("filename=") + 10, contentDisposition.length() - 1)
					.replace("\"", ""); // Remove quotes around filename
		}
		return null;
	}

	private String generateUniqueFileName(String fileName) {
		String fileExtension = "";

		// Get the file extension, if any
		int dotIndex = fileName.lastIndexOf('.');
		if (dotIndex != -1) {
			fileExtension = fileName.substring(dotIndex);
			fileName = fileName.substring(0, dotIndex); // Remove extension from name
		}

		// Append a UUID to the file name to ensure uniqueness
		String uniqueFileName = fileName + "_" + UUID.randomUUID().toString() + fileExtension;
		return uniqueFileName;
	}


	protected void showRegisterForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		 List<Artist> artists = artistDAO.getAllArtist(); // Replace artistDAO with your actual DAO/service
		  // Set artists as a request attribute
		req.setAttribute("artists", artists);
		RequestDispatcher dispatcher = req.getRequestDispatcher("template/album/create_album.jsp");
		dispatcher.forward(req,resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}

}
