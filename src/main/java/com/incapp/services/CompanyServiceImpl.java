package com.incapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.incapp.beans.Company;
import com.incapp.beans.Job;
import com.incapp.dao.CompanyRepo;

@RestController
public class CompanyServiceImpl implements CompanyService {
	
	@Autowired
	CompanyRepo companyRepo;
	
	@Override
	@PostMapping(value = "/companyRegister") 
	public ResponseEntity<String> companyRegister(@RequestBody Company c) {
		String r=companyRepo.addCompany(c);
		if(r.equalsIgnoreCase("success")) {
			return new ResponseEntity<String>(c.getName()+" is Inserted Successfully!", HttpStatus.OK);
		}else {
			return new ResponseEntity<String>(c.getName()+" is Already Exist!", HttpStatus.OK);
		}
	}
	
	@Override
	@PostMapping(value = "/addJob") 
	public ResponseEntity<String> addJob(@RequestBody Job j) {
		String r=companyRepo.addJob(j);
		if(r.equalsIgnoreCase("success")) {
			return new ResponseEntity<String>(j.getJname()+" is Added Successfully!", HttpStatus.OK);
		}else {
			return new ResponseEntity<String>(j.getJname()+" insertion failed!", HttpStatus.OK);
		}
	}
	
	@Override
	@RequestMapping(value = "/companyLogin/{email}/{password}")
	public ResponseEntity<String[]> checkCompanyLogin(@PathVariable String email, @PathVariable String password) {
		String r=companyRepo.checkCompanyLogin(email, password);
		if(r!=null) 
			return new ResponseEntity<String[]>(new String[] {email,r}, HttpStatus.OK);
		else
			return new ResponseEntity<String[]>(new String[] {}, HttpStatus.OK);
			
	}
	
	@Override
	@RequestMapping(value = "/getJobs/{email}")
	public List<Job> getJobs(@PathVariable String email){
		List<Job> jobs=companyRepo.getJobs(email);
		return jobs;
	}
	
	@Override
	@RequestMapping(value = "/getJobsByCategory/{category}")
	public List<Job> getJobsByCategory(@PathVariable String category){
		List<Job> jobs=companyRepo.getJobsByCategory(category);
		return jobs;
	}

	@Override
	@RequestMapping(value = "/companyLogoUpload/{email}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> companyLogoUpload(@RequestPart("logo") MultipartFile image,@PathVariable String email) {
		String r=companyRepo.companyLogoUpload(image, email);
		return new ResponseEntity<String>(r, HttpStatus.OK);
	}
	
	
	@Override
	@RequestMapping(value = "/getImage/{email}")
	public ResponseEntity<byte[]> getImage(@PathVariable String email){
		byte[] b=companyRepo.getImage(email);
		if(b!=null) {
			return new ResponseEntity<byte[]>(b, HttpStatus.OK);
		}else {
			return null;
		}
	}
}