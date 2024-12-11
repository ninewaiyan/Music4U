package com.music4u.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.music4u.models.Song;




public class SongDAO {
	
	private final DataSource dataSource;
    private Connection connection;
    private PreparedStatement pStmt;
	private Statement stmt;
    private ResultSet rs;

    public SongDAO(DataSource dataSource) {
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
    public boolean createSong(Song song) {
    	boolean ok = false;
        String sql = "INSERT INTO songs(name,path,album_id) VALUES (?,?,?)";
        try {
        	
            connection = dataSource.getConnection();
            pStmt = connection.prepareStatement(sql);
            pStmt.setString(1,song.getName());
            pStmt.setString(2,song.getPath());
            pStmt.setLong(3,song.getAlbumId());

            
           
            
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
    
   
    
   
   	
    
    
 // Get all users
    public List<Song> getAllSong() {
        List<Song> songs = new ArrayList<>();
        String sql = "SELECT * FROM songs WHERE delete_at is NULL";
        try {
            connection = dataSource.getConnection();
            pStmt = connection.prepareStatement(sql);
            rs = pStmt.executeQuery();
            while (rs.next()) {
            	songs.add(new Song(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("path"),
                    rs.getLong("album_id")
                    
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection, pStmt, rs);
        }
        return songs;
    }

    
    public List<Song> getSongByAlbumId(Long id) {
        List<Song> songs = new ArrayList<>();
        String sql = "SELECT * FROM songs WHERE album_id = ? AND delete_at IS NULL"; // Add album_id condition
        try {
            connection = dataSource.getConnection();
            pStmt = connection.prepareStatement(sql);
            pStmt.setLong(1, id);  // Set the album_id in the prepared statement
            rs = pStmt.executeQuery();
            while (rs.next()) {
                songs.add(new Song(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("path"),
                    rs.getLong("album_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection, pStmt, rs);  // Ensure resources are properly closed
        }
        return songs;
    }
    
    public List<Song> getAllSongsWithDetails() {
        List<Song> songs = new ArrayList<>();
        String sql = "SELECT s.id, s.name,s.path AS path ,s.album_id, a.name AS album_name, ar.name AS artist_name " +
                     "FROM songs s " +
                     "JOIN albums a ON s.album_id = a.id " +
                     "JOIN artists ar ON a.artist_id = ar.id " +
                     "WHERE s.delete_at IS NULL";  // Filter for songs that are not deleted

        try (Connection connection = dataSource.getConnection();
             PreparedStatement pStmt = connection.prepareStatement(sql);
             ResultSet rs = pStmt.executeQuery()) {

            while (rs.next()) {
                
            	songs.add(new Song(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("path"),
                        rs.getLong("album_id"),
                        rs.getString("album_name"),
                        rs.getString("artist_name")
                    ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return songs;
    }

    
    public boolean deleteSong(long Id) {
        boolean deleted = false;
        String sql = "UPDATE songs SET delete_at = CURRENT_TIMESTAMP WHERE id = ?";
        
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

    
    public boolean deleteSongByAlbumId(long Id) {
        boolean deleted = false;
        String sql = "UPDATE songs SET delete_at = CURRENT_TIMESTAMP WHERE album_id = ?";
        
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



}
