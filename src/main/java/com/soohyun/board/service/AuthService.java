package com.soohyun.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soohyun.board.dto.auth.AuthPostDto;
import com.soohyun.board.dto.auth.LoginDto;
import com.soohyun.board.dto.response.ResponseDto;
import com.soohyun.board.entity.MemberEntity;
import com.soohyun.board.repository.MemberRepository;

//@Service : 해당 클래스가 Service 레이어 역할을 함. 
@Service
public class AuthService {
	
	@Autowired MemberRepository memberRepository;
	
	public String hello() {
		// Entity Class로 entity 빌드
		MemberEntity memberEntity = 
				MemberEntity
				.builder()
				.email("qwe@qwe.com")
				.password("qwe123")
				.nickname("jiraynor")
				.telNumber("010-1234-5678")
				.address("busan")
				.build();
		//빌드한 Entity를 데이터베이스에 저장
		memberRepository.save(memberEntity);
		
		// MemberRepository가 상속받은 JpaRepository 메서드를 사용하여 데이터 검색
		MemberEntity savedMemberEntity =
				memberRepository.findById("qwe@qwe.com").get();//get은 memberEntity의 build 멤버변수 모두 가져옴.
		
		// MemberRepository에 작성한 커스텀 메서드를 사용
		List<MemberEntity> list = memberRepository.myFindAll("qwe@qwe.com");
		System.out.println(list.toString());
		
		return savedMemberEntity.getNickname();
	}
	
	public ResponseDto<LoginDto> login(AuthPostDto dto) {
		
		// 입력받은 email로 데이터베이스에서 검색
		String email = dto.getEmail();
		MemberEntity member;
		// 존재하지 않는다면 없는 아이디 "로그인 실패" 반환
		try {
			member = memberRepository.findById(email).get();
		} catch (Exception e) {
			return ResponseDto.setFailed("Login Failed");
		}
		// 존재한다면 입력받은 패스워드와 데이터베이스의 패스워드와 동일한지 검사
		String password = dto.getPassword();
		String dbPassword = member.getPassword();
		if (!password.equals(dbPassword)) 
			return ResponseDto.setFailed("Login Failed");
		
		// 동일하지 않다면 "로그인 실패" 반환
		
		// 토큰 생성 후 토큰 및 만료시간 반환
				
		LoginDto result = new LoginDto("JWT", 3600000);
		return ResponseDto.setSuccess("", result);
	}
}
