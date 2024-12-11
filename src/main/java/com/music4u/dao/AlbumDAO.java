package com.music4u.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.music4u.models.Album;
import com.music4u.models.Artist;
import com.music4u.models.Song;




public class AlbumDAO {
	
	private final DataSource dataSource;
    private Connection connection;
    private PreparedStatement pStmt;
	private Statement stmt;
    private ResultSet rs;

    public AlbumDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Close resources
    private void close(Connection connection, PreparedStatement pStmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (pStmt != null) pStmt.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Create a new user
    public boolean createAlbum(Album album) {
    	boolean ok = false;
        String sql = "INSERT INTO albums(name,image,artist_id) VALUES (?,?,?)";
        try {
        	
            connection = dataSource.getConnection();
            pStmt = connection.prepareStatement(sql);
            pStmt.setString(1,album.getName());
            pStmt.setString(2,album.getImage());
            pStmt.setLong(3,album.getArtistId());

            
           
            
            int rowEffected = pStmt.executeUpdate();
    		if(rowEffected > 0 )
    		{
    			ok = true;
    		}
    		
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection, pStmt, null);
        }      
        return ok;
    }
    
   
    public boolean deleteAlbum(long Id) {
        boolean deleted = false;
        String sql = "UPDATE albums SET delete_at = CURRENT_TIMESTAMP WHERE id = ?";
        
        try {
            connection = dataSource.getConnection();
            pStmt = connection.prepareStatement(sql);
            pStmt.setLong(1, Id);
            
            int rowsAffected = pStmt.executeUpdate();
            if (rowsAffected > 0) {
                deleted = true; // Artist was successfully soft deleted
            }
            
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception
        } finally {
            close(connection, pStmt, null); // Clean up resources
        }
        
        return deleted; // Return the result of the soft delete operation
    }
    
    public boolean deleteAlbumByArtistId(long Id) {
        boolean deleted = false;
        String sql = "UPDATE albums SET delete_at = CURRENT_TIMESTAMP WHERE artist_id = ?";
        
        try {
            connection = dataSource.getConnection();
            pStmt = connection.prepareStatement(sql);
            pStmt.setLong(1, Id);
            
            int rowsAffected = pStmt.executeUpdate();
            if (rowsAffected > 0) {
                deleted = true; // Artist was successfully soft deleted
            }
            
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception
        } finally {
            close(connection, pStmt, null); // Clean up resources
        }
        
        return deleted; // Return the result of the soft delete operation
    }
    
    
    public List<Album> getAllDeletedAlbum() {
        List<Album> albums = new ArrayList<>();
        String sql = "SELECT * FROM albums WHERE delete_at is NOT NULL";
        try {
            connection = dataSource.getConnection();
            pStmt = connection.prepareStatement(sql);
            rs = pStmt.executeQuery();
            while (rs.next()) {
               albums.add(new Album(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("image"),
                    rs.getLong("artist_id")
                    
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection, pStmt, rs);
        }
        return albums;
    }
   
   	
    
    
 // Get all users
    public List<Album> getAllAlbum() {
        List<Album> albums = new ArrayList<>();
        String sql = "SELECT * FROM albums WHERE delete_at is NULL";
        try {
            connection = dataSource.getConnection();
            pStmt = connection.prepareStatement(sql);
            rs = pStmt.executeQuery();
            while (rs.next()) {
               albums.add(new Album(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("image"),
                    rs.getLong("artist_id")
                    
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection, pStmt, rs);
        }
        return albums;
    }
    
    
    
    public List<Album> getAlbumByArtistId(Long id) {
        List<Album> albums = new ArrayList<>();
        String sql = "SELECT * FROM albums WHERE artist_id = ? AND delete_at IS NULL"; // Add album_id condition
        try {
            connection = dataSource.getConnection();
            pStmt = connection.prepareStatement(sql);
            pStmt.setLong(1, id);  // Set the album_id in the prepared statement
            rs = pStmt.executeQuery();
            while (rs.next()) {
                albums.add(new Album(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("image"),
                    rs.getLong("artist_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection, pStmt, rs);  // Ensure resources are properly closed
        }
        return albums;
    }

	


}
