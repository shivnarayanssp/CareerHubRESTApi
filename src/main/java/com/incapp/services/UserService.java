package com.incapp.services;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.incapp.beans.User;

public interface UserService {
	ResponseEntity<String> userRegister(User u);
	ResponseEntity<String[]> checkUserLogin(String email,String password);
	ResponseEntity<String> userPhotoUpload(MultipartFile image,String email);
	ResponseEntity<byte[]> getUserImage(String email);
	ResponseEntity<String> userResumeUpload(MultipartFile file, String email);
	ResponseEntity<String> resetPassword(String email);
	ResponseEntity<String> apply(String email);
	
}