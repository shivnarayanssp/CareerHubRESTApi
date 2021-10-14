package com.incapp.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.incapp.beans.User;

@Repository
public class UserRepo {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public String addUser(User u){
		try {
			String query="insert into user(email,name,phone,password) values(?,?,?,?)";
			jdbcTemplate.update(query,new Object[] {u.getEmail(),u.getName(),u.getPhone(),u.getPassword()});
			
			return "success";
		}catch(Exception ex) {
			//ex.printStackTrace();
			return "already";
		}
	}
	
	public String checkUserLogin(String email,String password){
		class DataMapper implements RowMapper{
			public String mapRow(ResultSet rs,int rowNum)throws SQLException{
				return 	rs.getString("name");
			}
		}
		try {
			final String query ="select * from user where email=? and password=?";
			String r=(String) jdbcTemplate.queryForObject(query,new DataMapper(),new Object[] {email,password});
			return r;
		}catch(EmptyResultDataAccessException ex) {
			return null;
		}
	}
	
	public String userImageUpload(MultipartFile image,String email){
		try {
			String query="update user set photo=? where email=?";
			jdbcTemplate.update(query,new Object[] {image.getInputStream(),email});
			
			return "success";
		}catch(Exception ex) {
			ex.printStackTrace();
			return "failed";
		}
	}
	
	public byte[] getUserImage(String email){
		class DataMapper implements RowMapper{
			public byte[] mapRow(ResultSet rs,int rowNum)throws SQLException{
				return 	rs.getBytes("photo");
			}
		}
		try {
			final String query ="select photo from user where email=?";
			byte[] r=(byte[]) jdbcTemplate.queryForObject(query,new DataMapper(),new Object[] {email});
			return r;
		}catch(EmptyResultDataAccessException ex) {
			return null;
		}
	}
	public String userResumeUpload(MultipartFile file,String email){
		try {
			String query="update user set file=? where email=?";
			jdbcTemplate.update(query,new Object[] {file.getInputStream(),email});
			
			return "success";
		}catch(Exception ex) {
			ex.printStackTrace();
			return "failed";
		}
	}
	
}