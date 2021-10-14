package com.incapp.dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.incapp.beans.Company;
import com.incapp.beans.Job;

@Repository
public class CompanyRepo {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public String addCompany(Company c){
		try {
			String query="insert into companies(email,name,registration,phone,password,address) values(?,?,?,?,?,?)";
			jdbcTemplate.update(query,new Object[] {c.getEmail(),c.getName(),c.getRegistration(),c.getPhone(),c.getPassword(),c.getAddress()});
			
			return "success";
		}catch(Exception ex) {
			//ex.printStackTrace();
			return "already";
		}
	}
	
	public String addJob(Job j){
		try {
			String query="insert into jobs(jname,location,category,description,expreience,email,cname) values(?,?,?,?,?,?,?)";
			jdbcTemplate.update(query,new Object[] {j.getJname(),j.getLocation(),j.getCategory(),j.getDescription(),j.getExpreience(),j.getEmail(),j.getCname()});
			
			return "success";
		}catch(Exception ex) {
			ex.printStackTrace();
			return "failed";
		}
	}
	
	public String checkCompanyLogin(String email,String password){
		class DataMapper implements RowMapper{
			public String mapRow(ResultSet rs,int rowNum)throws SQLException{
				return 	rs.getString("name");
			}
		}
		try {
			final String query ="select * from companies where email=? and password=?";
			String r=(String) jdbcTemplate.queryForObject(query,new DataMapper(),new Object[] {email,password});
			return r;
		}catch(EmptyResultDataAccessException ex) {
			return null;
		}
	}
	
	
	public List<Job> getJobs(String email){
		class DataMapper implements RowMapper{
			public Job mapRow(ResultSet rs,int rowNum)throws SQLException{
				Job j=new Job();
				j.setJname(rs.getString("jname"));
				j.setLocation(rs.getString("location"));
				j.setCategory(rs.getString("category"));
				j.setDescription(rs.getString("description"));
				j.setExpreience(rs.getString("expreience"));
				return 	j;
			}
		}
		try {
			final String query ="select * from jobs where email=?";
			List<Job> j= jdbcTemplate.query(query,new DataMapper(),new Object[] {email});
			return j;
		}catch(EmptyResultDataAccessException ex) {
			return null;
		}
	}
	
	public List<Job> getJobsByCategory(String category){
		class DataMapper implements RowMapper{
			public Job mapRow(ResultSet rs,int rowNum)throws SQLException{
				Job j=new Job();
				j.setJname(rs.getString("jname"));
				j.setLocation(rs.getString("location"));
				j.setCategory(rs.getString("category"));
				j.setDescription(rs.getString("description"));
				j.setExpreience(rs.getString("expreience"));
				return 	j;
			}
		}
		try {
			final String query ="select * from jobs where category=?";
			List<Job> j= jdbcTemplate.query(query,new DataMapper(),new Object[] {category});
			return j;
		}catch(EmptyResultDataAccessException ex) {
			return null;
		}
	}
	
	public String companyLogoUpload(MultipartFile image,String email){
		try {
			String query="update companies set logo=? where email=?";
			jdbcTemplate.update(query,new Object[] {image.getInputStream(),email});
			
			return "success";
		}catch(Exception ex) {
			ex.printStackTrace();
			return "failed";
		}
	}

	public byte[] getImage(String email){
		class DataMapper implements RowMapper{
			public byte[] mapRow(ResultSet rs,int rowNum)throws SQLException{
				return 	rs.getBytes("logo");
			}
		}
		try {
			final String query ="select logo from companies where email=?";
			byte[] r=(byte[]) jdbcTemplate.queryForObject(query,new DataMapper(),new Object[] {email});
			return r;
		}catch(EmptyResultDataAccessException ex) {
			return null;
		}
	}
	
}