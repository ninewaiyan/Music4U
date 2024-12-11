package com.music4u.dao;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.music4u.crypto.PasswordEncoder;
import com.music4u.crypto.PasswordValidator;
import com.music4u.models.User;


public class UserDAO {
	
	private final DataSource dataSource;
    private Connection connection;
    private PreparedStatement pStmt;
	private Statement stmt;
    private ResultSet rs;

    public UserDAO(DataSource dataSource) {
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
    public boolean createUser(User user) {
    	boolean ok = false;
        String sql = "INSERT INTO users(name,email,password, role,enable) VALUES (?,?,?,?,?)";
        try {
        	
            connection = dataSource.getConnection();
            pStmt = connection.prepareStatement(sql);
            pStmt.setString(1,user.getName());
            pStmt.setString(2,user.getEmail());
            try {
    			pStmt.setString(3, PasswordEncoder.encode(user.getPassword()));
    		} catch (Exception e) {
    			// TODO: handle exception
    			e.printStackTrace();
    		}
            pStmt.setString(4,user.getRole());
            pStmt.setBoolean(5, user.getEnable());
            
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
    
    public User getUserByEmail(String email) {
		User user = null;
		
		try {
			connection = dataSource.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery("select * from users where email ='"+ email+"';");
			
			
		    
			while (rs.next()) {
				 user = new User(
						 rs.getLong("id"),
						 rs.getString("name"), 
						 rs.getString("email"), 
						 rs.getString("password"), 
						 rs.getString("role"),
				 		 rs.getBoolean("enable"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			close(connection, pStmt, rs);
		}
		
		return user;
		
	}
    
    
    public boolean isAuthenticated(String email,String password)
   	{
   		boolean ok = false;
   		User userByEmail = getUserByEmail(email);
   		
   		
   		
   		try {
   			if((userByEmail != null && userByEmail.getEnable() && PasswordValidator.validatePassword(password, userByEmail.getPassword())))
   				
   			ok = true;
   		} catch (NoSuchAlgorithmException e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		} catch (InvalidKeySpecException e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		}
   		return ok;
   		
   	}
    
    public boolean enableUser(Long userId)
   	{
   		boolean  ok = false;
   		
   		try {
   			connection = dataSource.getConnection();
   			pStmt = connection.prepareStatement("update users set enable=? where id=?;");
   			pStmt.setBoolean(1, true);
   			pStmt.setLong(2, userId);
   			int rowEffected = pStmt.executeUpdate();
   			if(rowEffected > 0) {
   				ok = true;
   			}
   		} catch (SQLException e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		}finally {
   			close(connection, pStmt, null);
   		}
   		return ok;
   	}
   	
   	
   	public boolean disableUser(Long userId)
   	{
   		boolean  ok = false;
   			
   		try {
   			connection = dataSource.getConnection();
   			pStmt = connection.prepareStatement("update users set enable=? where id=?;");
   			pStmt.setBoolean(1, false);
   			pStmt.setLong(2, userId);
   			int rowEffected = pStmt.executeUpdate();
   			if(rowEffected > 0) {
   				ok = true;
   			}
   		} catch (SQLException e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		}finally {
   			close(connection, pStmt, null);
   		}
   		return ok;
   	}
   	
 // Get all users
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE role != 'admin'";
        try {
            connection = dataSource.getConnection();
            pStmt = connection.prepareStatement(sql);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                users.add(new User(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("role"),
                    rs.getBoolean("enable")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection, pStmt, rs);
        }
        return users;
    }



}
