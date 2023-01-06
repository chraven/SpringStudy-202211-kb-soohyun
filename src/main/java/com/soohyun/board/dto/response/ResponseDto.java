package com.soohyun.board.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName="set")
//전체생성자(모든 멤버변수를 담는 생성자)이름을 set으로 설정.
//<D>는 HelloDto로 변경됨. 
public class ResponseDto<D> {
	private boolean status;
	private String message;
	private D data;
	
//어디서나 클래스로 접근 가능하다. 반환 자료형(위 형태)(제네릭 D는 한번더 써서 알려줌) 그다음 메소드 이름 
	public static <D> ResponseDto<D> setSuccess(String message, D data) {
		return ResponseDto.set(true, message, data);
		//위의 staticName="set"(생성자)을 set으로 static으로 가져옴. D는 helloDto가 되고 data는 requestBody가 된다. 
	}
	
	public static <D> ResponseDto<D> setFailed(String message) {
		return ResponseDto.set(false, message, null);
	}
}
