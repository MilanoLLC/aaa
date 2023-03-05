package com.example.demo;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

@RestController
public class APIController {
	
	
	@PostMapping("/api/postInfo")
	public Res submitInfo(
			@RequestParam(name = "email") String email,
			@RequestParam(name = "firstName") String  firstName,
			@RequestParam(name = "lastName") String  lastName,
			@RequestParam(name = "password") String password ,
			@RequestParam(name = "phone") int   phone
			) {
		try {
					OkHttpClient client = new OkHttpClient().newBuilder()
							  .build();
							MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
							RequestBody body = RequestBody.create(mediaType, "email="+email+"&firstName="+firstName+"&lastName="+lastName+"&password="+password+"&phone="+phone);
							Request request = new Request.Builder()
							  .url("https://serviceamedia.com/api/v2/leads/")
							  .method("POST", body)
							  .addHeader("Api-Key", "DE916C86-566B-8267-AE69-E7019B4E69B9")
							  .addHeader("Content-Type", "application/x-www-form-urlencoded")
							  .build();
							ResponseBody  response = client.newCall(request).execute().body();

							String json = response.string() ; 
							
							ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
							Res res = objectMapper.readValue(json, Res.class);
							
					return  res ; 
		}catch(Exception ex ) {
			ex.printStackTrace();
			return null ; 
		}
	}

}
