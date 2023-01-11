package com.soohyun.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soohyun.board.dto.response.ResponseDto;
import com.soohyun.board.dto.user.GetUserResponseDto;
import com.soohyun.board.dto.user.PatchUserDto;
import com.soohyun.board.dto.user.PostUserDto;
import com.soohyun.board.dto.user.ResultResponseDto;
import com.soohyun.board.service.UserService;

@RestController
@RequestMapping("api/user/")
public class UserController {
	//위에서부터 CRUD
	@Autowired UserService userSerivce;
	
	@PostMapping("")
	public ResponseDto<ResultResponseDto> postUser(@RequestBody PostUserDto requestBody) {
		return userSerivce.postUser(requestBody);
	}
	
	//{}안에 이메일 값을 쓰면 PathVariable을 통해 "email"을 거쳐 String email에 저장된다.
	@GetMapping("{email}")
	public ResponseDto<GetUserResponseDto> getUser(@PathVariable("email") String email) {
		return userSerivce.getUser(email);
	}

	
	@PatchMapping("")
	public ResponseDto<GetUserResponseDto> patchUser(@RequestBody PatchUserDto requestBody) {
		return userSerivce.patchUser(requestBody);
	}

	@DeleteMapping("{email}")
	public ResponseDto<ResultResponseDto> deleteUser(@PathVariable("email") String email) {
		return userSerivce.deleteUser(email);
	}
}
