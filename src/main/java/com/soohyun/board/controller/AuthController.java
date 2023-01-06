package com.soohyun.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soohyun.board.dto.auth.AuthPostDto;
import com.soohyun.board.dto.auth.LoginDto;
import com.soohyun.board.dto.response.ResponseDto;
import com.soohyun.board.service.AuthService;

@RestController
@RequestMapping("api/auth/")
public class AuthController {
		
		//@Autowired : 해당하는 클래스 인스턴스를 자동으로 생성(주입) 해줌
		@Autowired AuthService authService;
	
		@PostMapping("")
		public ResponseDto<LoginDto> login(@RequestBody AuthPostDto requestBody) {
			//ResponseDto<LoginDto>반환값 login이름의 메소드. 소괄호 안은 매개변수.RequestBody가 Json을 인스턴스로 변경
//			LoginDto result = new LoginDto("JWT", 3600000);
//			return ResponseDto.setSuccess("login success", result);
			
			return authService.login(requestBody);
		}
}
