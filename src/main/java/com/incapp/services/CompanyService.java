package com.incapp.services;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.incapp.beans.Company;
import com.incapp.beans.Job;

public interface CompanyService {
	ResponseEntity<String> companyRegister(Company c);
	ResponseEntity<String[]> checkCompanyLogin(String email,String password);
	ResponseEntity<String> addJob(Job j);
	List<Job> getJobs(String email);
	List<Job> getJobsByCategory(String category);
	ResponseEntity<String> companyLogoUpload(MultipartFile image,String email);
	ResponseEntity<byte[]> getImage(String email);
}