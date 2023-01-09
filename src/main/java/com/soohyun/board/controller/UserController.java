package com.soohyun.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soohyun.board.dto.response.ResponseDto;
import com.soohyun.board.dto.user.PostUserDto;
import com.soohyun.board.dto.user.PostUserResponseDto;
import com.soohyun.board.service.UserService;

@RestController
@RequestMapping("api/user/")
public class UserController {
	
	@Autowired UserService userSerivce;

	@PostMapping("")
	public ResponseDto<PostUserResponseDto> postUser(@RequestBody PostUserDto requestBody) {
		return userSerivce.postUser(requestBody);
	}
}
