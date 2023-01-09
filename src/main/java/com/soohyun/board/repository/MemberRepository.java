package com.soohyun.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.soohyun.board.entity.MemberEntity;

// @Repository : 해당 인터페이스가 Repository임을 명시
@Repository
// Repository는 interface로 작성
// JpaRespository interface를 상속받아야 함
// JpaRepository<Table(EntityClass), Primary key type>
public interface MemberRepository extends JpaRepository<MemberEntity, String> {

	// @Query : 커스텀 ORM 메서드를 작성 
	// 테이블 명을 alias 로 지정해서 사용
	// ?1, ?2, ... : 매개변수로 받아온 변수를 해당 위치로 넣기 위한 구문
	@Query("SELECT m from MEMBER m where m.email = ?1")
	//해당 파라미터 왼-오 볼 때 가장 첫번째 오는 애가 ?1
	List<MemberEntity> myFindAll(String email);//select를 하면 자동 list가 나옴.
}
