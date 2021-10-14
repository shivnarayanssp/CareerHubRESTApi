package com.incapp.services;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.incapp.beans.MailConstants;
import com.incapp.beans.User;
import com.incapp.dao.UserRepo;

@RestController
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepo userRepo;
	@Autowired
	JavaMailSender mailSender;
	
	@Override
	@PostMapping(value = "/userRegister") 
	public ResponseEntity<String> userRegister(@RequestBody User u) {
		String r=userRepo.addUser(u);
		if(r.equalsIgnoreCase("success")) {
			return new ResponseEntity<String>(u.getName()+" is Registered Successfully!", HttpStatus.OK);
		}else {
			return new ResponseEntity<String>(u.getName()+" is Already Exist!", HttpStatus.OK);
		}
	}
	
	@Override
	@RequestMapping(value = "/userLogin/{email}/{password}")
	public ResponseEntity<String[]> checkUserLogin(@PathVariable String email, @PathVariable String password) {
		String r=userRepo.checkUserLogin(email, password);
		if(r!=null)
			return new ResponseEntity<String[]>(new String[] {email,r}, HttpStatus.OK);
		else
			return new ResponseEntity<String[]>(new String[] {}, HttpStatus.OK);
	}
	
	@Override
	@RequestMapping(value = "/userImageUpload/{email}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> userPhotoUpload(@RequestPart("photo") MultipartFile image,@PathVariable String email) {
		String r=userRepo.userImageUpload(image, email);
		return new ResponseEntity<String>(r, HttpStatus.OK);
	}
	
	@Override
	@RequestMapping(value = "/getUserImage/{email}")
	public ResponseEntity<byte[]> getUserImage(@PathVariable String email){
		byte[] b=userRepo.getUserImage(email);
		if(b!=null) {
			return new ResponseEntity<byte[]>(b, HttpStatus.OK);
		}else {
			return null;
		}
	}
	@Override
	@RequestMapping(value = "/userResumeUpload/{email}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> userResumeUpload(@RequestPart("file") MultipartFile file,@PathVariable String email) {
		String r=userRepo.userResumeUpload(file, email);
		return new ResponseEntity<String>(r, HttpStatus.OK);
	}
	@Override
	@RequestMapping(value = "/resetPassword/{email}")
	public ResponseEntity<String> resetPassword(@PathVariable String email){
		try {
		MimeMessage mailMessage=mailSender.createMimeMessage();
		boolean multiPart=true;
		MimeMessageHelper helper=new MimeMessageHelper(mailMessage,multiPart);
		
			helper.setTo(email);
		
		helper.setSubject("Hi FROM SHIV NARAYAN PRASAD");
		String htmlMSG="<h1>DO NOT REPLY</h1> <p>YOUR RESET PASSWORD is 42254420. </p>";
		helper.setText("text/html", htmlMSG);
		mailSender.send(mailMessage);
	} catch (MessagingException e) {
		e.printStackTrace();
		return new ResponseEntity<String>("failed:"+e.getMessage(), HttpStatus.OK);
	}
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
	
	@Override
	@RequestMapping(value = "/apply/{email}")
	public ResponseEntity<String> apply(@PathVariable String email){
		try {
		MimeMessage mailMessage=mailSender.createMimeMessage();
		boolean multiPart=true;
		MimeMessageHelper helper=new MimeMessageHelper(mailMessage,multiPart);
		
			helper.setTo(email);
		
		helper.setSubject("Hi FROM SHIV NARAYAN PRASAD");
		String htmlMSG="<h1>DO NOT REPLY</h1> <p>ThankYou For Apply. </p>";
		helper.setText("text/html", htmlMSG);
		mailSender.send(mailMessage);
	} catch (MessagingException e) {
		e.printStackTrace();
		return new ResponseEntity<String>("failed:"+e.getMessage(), HttpStatus.OK);
	}
		return new ResponseEntity<String>("Applied", HttpStatus.OK);
	}
}