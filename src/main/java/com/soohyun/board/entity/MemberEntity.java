package com.soohyun.board.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/* Member
* 이메일
* 비밀번호
* 닉네임
* 프로필 사진
* 전화번호
* 주소
*/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
// 해당 클래스가 Entity 클래스임을 명시
// 인자로 Entity의 이름을 지정할 수 있음.DB와 연동시킬 때 이 클래스의 이름을 바꿔주는 역할 
@Entity(name="MEMBER")
// 해당 Entity 클래스와 데이터베이스 Table을 
// 인자로 지정한 이름으로 매핑
@Table(name="MEMBER")
public class MemberEntity {
	
	//해당 필드가 primary key라는 뜻
	@Id
	// 해당 Primary key의 value 자동 생성을 지시
	//@GeneratedValue
	private String email;
	private String password;
	private String nickname;
	private String profile;
	private String telNumber;//mySQL에선tel_number지만 자바에선 그냥 카멜표기법 씀.
	private String address;
}
