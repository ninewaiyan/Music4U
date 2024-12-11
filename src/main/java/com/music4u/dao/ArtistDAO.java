package com.music4u.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.music4u.models.Artist;




public class ArtistDAO {
	
	private final DataSource dataSource;
    private Connection connection;
    private PreparedStatement pStmt;
	private Statement stmt;
    private ResultSet rs;

    public ArtistDAO(DataSource dataSource) {
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
    public boolean createArtist(Artist artist) {
    	boolean ok = false;
        String sql = "INSERT INTO artists(name,image) VALUES (?,?)";
        try {
        	
            connection = dataSource.getConnection();
            pStmt = connection.prepareStatement(sql);
            pStmt.setString(1,artist.getName());
            pStmt.setString(2,artist.getImage());
           
            
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
    
    
    public boolean deleteArtist(long artistId) {
        boolean deleted = false;
        String sql = "UPDATE artists SET delete_at = CURRENT_TIMESTAMP WHERE id = ?";
        
        try {
            connection = dataSource.getConnection();
            pStmt = connection.prepareStatement(sql);
            pStmt.setLong(1, artistId);
            
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

    
    
   
    
   
   	
    
    
 // Get all users
    public List<Artist> getAllArtist() {
        List<Artist> artists = new ArrayList<>();
        String sql = "SELECT * FROM artists WHERE delete_at is NULL";
        try {
            connection = dataSource.getConnection();
            pStmt = connection.prepareStatement(sql);
            rs = pStmt.executeQuery();
            while (rs.next()) {
               artists.add(new Artist(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("image")
                    
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection, pStmt, rs);
        }
        return artists;
    }



}
