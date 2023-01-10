package com.soohyun.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soohyun.board.dto.response.ResponseDto;
import com.soohyun.board.dto.user.GetUserResponseDto;
import com.soohyun.board.dto.user.PostUserDto;
import com.soohyun.board.dto.user.PostUserResponseDto;
import com.soohyun.board.service.UserService;

@RestController
@RequestMapping("api/user/")
public class UserController {
	
	@Autowired UserService userSerivce;
	
	//{}안에 이메일 값을 쓰면 PathVariable을 통해 "email"을 거쳐 String email에 저장된다.
	@GetMapping("{email}")
	public ResponseDto<GetUserResponseDto> getUser(@PathVariable("email") String email) {
		return userSerivce.getUser(email);
	}

	@PostMapping("")
	public ResponseDto<PostUserResponseDto> postUser(@RequestBody PostUserDto requestBody) {
		return userSerivce.postUser(requestBody);
	}
}
