package com.soohyun.board.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soohyun.board.dto.response.ResponseDto;
import com.soohyun.board.dto.user.GetUserResponseDto;
import com.soohyun.board.dto.user.PatchUserDto;
import com.soohyun.board.dto.user.PostUserDto;
import com.soohyun.board.dto.user.ResultResponseDto;
import com.soohyun.board.entity.MemberEntity;
import com.soohyun.board.repository.MemberRepository;

@Service
public class UserService {
	
	@Autowired MemberRepository memberRepository;
	
	public ResponseDto<List<GetUserResponseDto>> getAllUser() {
		//get으로 데이터가 들어가므로 ArrayList생성할 필요 없다. findAll은 DB 전체의 데이터를 가져옴. 한 개인별 한 인덱스.
		List<MemberEntity> memberList = memberRepository.findAll();
		
		List<GetUserResponseDto> data = new ArrayList<GetUserResponseDto>();
								//GetUserResponse에는 password가 없어서 data에 그냥 나머지가 들어간다.
		for (MemberEntity member: memberList) data.add(new GetUserResponseDto(member));
		
		return ResponseDto.setSuccess("Get User List Success", data);
	}
	
	public ResponseDto<GetUserResponseDto> getUser(String email) {
		// 해당 이메일을 데이터베이스에서 검색(DB에서 데이터를 GetUserResponseDto에 담는 것)
		MemberEntity member;
		try {
			member = memberRepository.findById(email).get();
		}
		// 존재하지 않으면 "Not Exist User" 메세지를 포함한 Failed Response 반환
		catch (Exception e) {
			return ResponseDto.setFailed("Not Exist User");
		}
		// 존재하면 User정보 반환
		// 1
//		GetUserResponseDto responseData = new GetUserResponseDto();
//		responseData.setEmail(member.getEmail());
//		responseData.setNickname(member.getNickname());
//		responseData.setProfile(member.getProfile());
//		responseData.setTelNumber(member.getTelNumber());
//		responseData.setAddress(member.getAddress());
//		
//		return ResponseDto.setSuccess("Get User Success", responseData);
		
		//2
//		GetUserResponseDto responseData = 
//				GetUserResponseDto
//				.builder()
//				.email(member.getEmail())
//				.nickname(member.getNickname())
//				.profile(member.getProfile())
//				.telNumber(member.getTelNumber())
//				.address(member.getAddress())
//				.build()
//				
//		return ResponseDto.setSuccess("Get User Success", responseData);
		
		//3
//		return ResponseDto.setSuccess(
//				"Get User Success",
//				new GetUserResponseDto(
//						member.getEmail(),
//						member.getNickname(),
//						member.getProfile(),
//						member.getTelNumber(),
//						member.getAddress()
//					)
//				);
		
		//선생님 풀이(생성자 활용)
		return ResponseDto.setSuccess("Get User Success", new GetUserResponseDto(member));
	}
	
	public ResponseDto<ResultResponseDto> postUser(PostUserDto dto) {
		
		//데이터베이스에 해당 이메일이 존재하는 체크
		//존재한다면 Failed Response를 반환
		//email을 가져와서 select from m where email = ?
		//위 쿼리로 이메일이 존재하는지 확인해서 결과가 존재하면 중복, 존재하지 않으면 중복x
		String email = dto.getEmail();
		
		try {
			if (memberRepository.existsById(email))
				return ResponseDto.setFailed("이미 존재하는 이메일입니다.");
		
			//위에서 실행되느냐 안되느냐 t/f로만 나오므로 오류가 안 나오고 밑의 오류 메세지는 안 나온다.
			//데이터베이스 오류일 때(레파지토리 연결이 안될때) 아래 문구가 뜬다	
		} catch (Exception e) {
			return ResponseDto.setFailed("데이터베이스 오류입니다.");
		}
		
		//레파지토리 쓰는 애들은 try-catch문에 넣어야 한다.
//		try {
//			 memberRepository.findById(email).get();
//		} catch(Exception e) {
//			return ResponseDto.setFailed("이미 존재하는 이메일입니다.");
//		}
//		새로운 이메일을 입력했을 때 findById를 해서 찾을 수 없으므로 오류가 생겨 catch문의 이미 존재하는 이메일입니다.가 뜬다.
		
		
		String password = dto.getPassword();
		String password2 = dto.getPassword2();
		
		if (!password.equals(password2)) return ResponseDto.setFailed("비밀번호가 서로 다릅니다.");
		
		//MemberEntity member를 다시 씀.
		MemberEntity member = MemberEntity
				.builder()
				.email(dto.getEmail())
				.password(dto.getPassword())
				.nickname(dto.getNickname())
				.telNumber(dto.getTelNumber())
				.address(dto.getAddress() + " " + dto.getAddressDetail())
				.build();
		
		//데이터가 DB에 저장됨. save는 JPA repository(memberRepository가 상속하는 애)에 있는 메소드.
		//JpaRepository.save(Entity) 메서드
		//해당 Entity Id가 데이터베이스 테이블에 존재하지 않으면!
		//Entity를 INSERT 작업을 수행
		//!!하지만!!
		//해당 Entity Id가 데이터베이스 테이블에 존재하면!
		//존재하는 Entity를 UPDATE 작업을 수행
		memberRepository.save(member);
		
		return ResponseDto.setSuccess("회원가입에 성공했습니다.", new ResultResponseDto(true));
	}
	
	public ResponseDto<GetUserResponseDto> patchUser(PatchUserDto dto) {
		// dto에서 이메일을 가져옴
		String email = dto.getEmail();
		// 그 이메일에 해당하는 user정보를 가져옴
		
		// repository를 이용해서 데이터베이스에 있는 member 테이블 중
		// 해당 email에 해당하는 데이털르 불러옴
		MemberEntity member = null;
		try {
			member = memberRepository.findById(email).get();
		} catch (Exception e) {
			// 만약 존재하지 않으면 Failed Response로 "Not Exist User" 반환
			return ResponseDto.setFailed("Not Exist User"); 
		}
		
		// Request Body로 받은 nickname과 profile로 각각 변경
		member.setNickname(dto.getNickname());
		member.setProfile(dto.getProfile());
		
		// 변경한 entity를 repository를 이용해서 데이터베이스에 적용 (혹은 저장)
		memberRepository.save(member);
		
		// 결과를 ResponseDto에 담아서 반환
		return ResponseDto.setSuccess("User Patch Success", new GetUserResponseDto(member));
	}
	
	public ResponseDto<ResultResponseDto> deleteUser(String email) {
		// repository를 이용해서 데이터베이스에 있는 Member 테이블의 중
		// email에 해당하는 데이터를 삭제
		memberRepository.deleteById(email);
		
		return ResponseDto.setSuccess("Delete User Success", new ResultResponseDto(true));
	}
}
