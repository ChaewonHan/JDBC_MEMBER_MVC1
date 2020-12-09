package com.kh.member.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.kh.member.model.vo.Member;

public class MemberDAO {
	// 회원 등록 
	public int insertMember(Member mb) {
		// DB에 저장
		Connection conn = null;
		String query = null;
		Statement stmt = null;
		int result = 0;
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","STUDENT","STUDENTPASS");
			query = "INSERT INTO MEMBER VALUES('"+mb.getMemberId()+"','"+mb.getMemberPwd()+"','"+mb.getMemberName()
			+"','"+mb.getGender()+"',"+mb.getAge()+",'"+mb.getEmail()+"','"+mb.getPhone()+
			"','"+mb.getAddress()+"','"+mb.getHobby()+"','"+mb.getEnrollDate()+"')";
			stmt = conn.createStatement();
			result = stmt.executeUpdate(query);
			
		} catch (SQLException e) {
			System.out.println("오류발생 : 관리자에게 문의하세요~!");
			e.printStackTrace();
		}finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				System.out.println("오류발생 : 관리자에게 문의하세요~!");
				e.printStackTrace();
			}
		}
		return result;
	}

	// 회원 정보 수정
	public int updateMember(Member mb) {
		Connection conn = null;
		String query = null;
		PreparedStatement pstmt = null;
		int result = 0;
		// 조회된 데이터가 없는 경우
		if(mb == null) {
			return result;
		}
		
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","STUDENT","STUDENTPASS");
			query = "UPDATE MEMBER SET MEMBER_PWD=?, GENDER=?, AGE=?, EMAIL=?, PHONE=?, ADDRESS=?, HOBBY=? WHERE MEMBER_ID=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, mb.getMemberPwd());
			pstmt.setString(2, String.valueOf(mb.getGender()));	// gender는 단일문자여서 문자열 형태로 변환
			pstmt.setInt(3, mb.getAge());
			pstmt.setString(4, mb.getEmail());
			pstmt.setString(5, mb.getPhone());
			pstmt.setString(6, mb.getAddress());
			pstmt.setString(7, mb.getHobby());
			pstmt.setString(8, mb.getMemberId());
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("오류발생 : 관리자에게 문의하세요");
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e2) {
				System.out.println("오류발생 : 관리자에게 문의하세요");
			}
		}
		return result;
	}
	
	// 회원정보 조회(ID)
	public Member searchMemberId(String id) {
		Connection conn = null;
		String query = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		// 조회해온 데이터를 저장할 Member 참조변수 생성
		// 조회해온 데이터가 있을 때는 Member객체를 만들어서 저장, 없을때는 null
		Member mb = null;
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","STUDENT","STUDENTPASS");
			// DB에서 아이디로 회원 조회
			query = "SELECT * FROM MEMBER WHERE MEMBER_ID = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			rset = pstmt.executeQuery();

			if(rset.next()) {
				// 조회해 온 데이터 Member 객체에 저장
				mb = new Member(rset.getString(1),rset.getString(2),rset.getString(3)
						,(rset.getString(4)==null) ? 'X':rset.getString(4).charAt(0),rset.getInt(5),rset.getString(6),rset.getString(7)
						,rset.getString(8),rset.getString(9),rset.getDate(10));			
			}

		} catch (SQLException e) {
			System.out.println("오류발생 : 관리자에게 문의하세요");
		} finally {
			try {
				rset.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e2) {
				System.out.println("오류발생 : 관리자에게 문의하세요");
			}
		}
		return mb;
	}

	// 회원 정보 조회(name)
	public ArrayList<Member> searchMemberName(String name) {
		Connection conn = null;
		String query = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		ArrayList<Member> list = new ArrayList<Member>();
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","STUDENT","STUDENTPASS");
			query = "SELECT * FROM MEMBER WHERE MEMBER_NAME = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, name);
			rset = pstmt.executeQuery();

			while(rset.next()) {
				Member mb = new Member(rset.getString(1),rset.getString(2),rset.getString(3)
						,(rset.getString(4)==null)?'X':rset.getString(4).charAt(0),rset.getInt(5),rset.getString(6),rset.getString(7)
						,rset.getString(8),rset.getString(9),rset.getDate(10));
				list.add(mb);
			}
			
		} catch (SQLException e) {
			System.out.println("오류발생 : 관리자에게 문의하세요");
		} finally {
			try {
				rset.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e2) {
				System.out.println("오류발생 : 관리자에게 문의하세요");
			}
		}
		return list;
	}
	// 회원 삭제
	public int deleteMember(String id) {
		Connection conn = null;
		String query = null;
		PreparedStatement pstmt = null;
		int result = 0;

		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","STUDENT","STUDENTPASS");
			query = "DELETE FROM MEMBER WHERE MEMBER_ID = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			result = pstmt.executeUpdate();
			
			if(result > 0) {
				System.out.println("회원 정보 삭제 완료");
			}else {
				System.out.println("회원 정보 삭제 실패");
			}
		}catch (SQLException e) {
			System.out.println("오류발생 : 관리자에게 문의하세요");
		}finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e2) {
				System.out.println("오류발생 : 관리자에게 문의하세요");
			}
		}	
		return result;
	}

	// 회원 목록 조회
	public ArrayList<Member> allListMember() {
		ArrayList<Member> list = new ArrayList<Member>();

		Connection conn = null;
		String query = null;
		Statement stmt = null;
		ResultSet rset = null;
		Member mb = new Member();
		
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","STUDENT","STUDENTPASS");
			query = "SELECT * FROM MEMBER";
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			
			// 리스트에 조회해온 데이터 저장
			while(rset.next()) {
				mb = new Member(rset.getString(1),rset.getString(2),rset.getString(3)
						,(rset.getString(4)==null)?'X':rset.getString(4).charAt(0),rset.getInt(5),rset.getString(6),rset.getString(7)
						,rset.getString(8),rset.getString(9),rset.getDate(10));
				list.add(mb);
			}
		} catch (SQLException e) {
			System.out.println("오류 발생 : 관리자에게 문의하세요");
		} finally {
			try {
				rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e2) {
				System.out.println("오류 발생 : 관리자에게 문의하세요");
			}
		}
		return list;
	}

}
