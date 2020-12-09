package com.kh.member.controller;

import java.util.ArrayList;

import com.kh.member.model.dao.MemberDAO;
import com.kh.member.model.vo.Member;
import com.kh.member.view.MemberView;

public class MemberController {
	// 뷰 객체를 저장할 참조변수 생성
	private MemberView mv;
	// 모델(DAO) 객체를 저장할 참조변수 생성
	private MemberDAO md;
	
	public MemberController() {
		// 뷰 객체 생성 후 참조변수에 저장
		mv = new MemberView();
		// 모델(DAO) 객체 생성 후 참조변수에 저장
		md = new MemberDAO();
	}
	// 회원관리 프로그램 시작
	public void start() {
		int select;
		// 뷰에서 메인메뉴 출력 후 사용자에게 선택 받은 값을 반환해 주면 select 변수에 저장해서 사용
		// 반복문이 진행될때 마다 사용자에게 다시 선택 받아서 기능 수행
		while((select = mv.mainMenu()) != 7) {
			switch(select) {
			case 1 : // 회원 등록 
				insertMember();
				break;
			case 2 : // 회원 정보 수정
				updateMember();
				break;
			case 3 : // 회원 삭제
				deleteMember();
				break;
			case 4 : // 회원 목록 조회
				allListMember();
				break;
			case 5 : // 회원 정보 조회(ID)
				searchMemberId();
				break;
			case 6 : // 회원 정보 조회(이름)
				searchMemberName();
				break;
			}
		}
	}
	// 회원 등록
	public void insertMember() {
//		// 뷰에서 화면에 메뉴출력 후 사용자에 입력받기(입력받은 정보를 Member객체에 담아서 반환)
//		Member mb = mv.insertMember();
//		// 모델(DAO)에 사용자 입력값이 저장된 Member객체를 전달해서 DB에 삽입 후 결과를 받아옴
//		int result = md.insertMember(mb);
//		// 뷰에서 결과에 따라 출력 내용 결정
//		mv.printResult(result, "등록");
		
		// 위 코드를 한 줄로 작성
		// 뷰에서 입력 받은 정보를 Member객체로 받아와서 DAO에 전달해서 DB등록 후 결과값 받아와서 뷰에서 출력
		mv.printResult(md.insertMember(mv.insertMember()), "등록");
	}
	
	// 회원 정보 수정
	public void updateMember() {
//		// 뷰에서 사용자에게 수정할 아이디 입력받기
//		String id = mv.inputId("수정");
//		// 모델(DAO)에 아이디 전달해서 정보 조회 후 조회된 데이터 Member객체에 저장해서 반환
//		Member mb = md.searchMemberId(id);
//		// 뷰에서 조회된 데이터를 전달하고, Member객체에 저장해서 반환
//		Member newMb = mv.updateMember(mb);
//		// 모델(DAO)에 수정할 데이터를 전달해서 DB 수정 후 결과를 반환
//		int result = md.updateMember(newMb);
//		// 뷰에서 결과에 따라 출력 내용 결정
//		mv.printResult(result, "정보 수정");
		
		// 위 코드를 한 줄로 작정
		mv.printResult(md.updateMember(mv.updateMember(md.searchMemberId(mv.inputId("수정"))))
				,"정보 수정");
	}

	// 회원 정보 조회(ID)
	public void searchMemberId() {
//		// 뷰에서 사용자에게 조회할 아이디 입력받기
//		String id = mv.inputId("조회");
//		// 모델(DAO)에 아이디 전달해서 정보 조회 후 조회된 데이터 Member객체에 저장해서 반환
//		Member mb = md.searchMemberId(id);
//		// 뷰에서 조회해온 데이터 출력
//		mv.searchMemberId(mb);
		
		// 위 코드를 한 줄로 작성
		mv.searchMemberId(md.searchMemberId(mv.inputId("조회")));
	}

	// 회원 삭제
	public void deleteMember() {
		mv.printResult(md.deleteMember(mv.inputId("삭제")), "삭제");
	}
	
	// 회원 목록 조회
	public void allListMember() {
		mv.searchMemberAll(md.allListMember());
	}
	
	// 회원 정보 조회(이름)
	public void searchMemberName() {
		mv.searchMemberAll(md.searchMemberName(mv.inputName("조회")));
	}
}
