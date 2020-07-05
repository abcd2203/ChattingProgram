package ChattingProgram;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

//import MemberDTO;

public class ChatDAO {
	private static String Driver = "oracle.jdbc.driver.OracleDriver";
	private static String Url = "jdbc:oracle:thin:@localhost:1521:orcl";
	private static String User = "root";
	private static String Pass = "0000";

	public static Connection getConn() {
		Connection con = null;// DB 연동 초기화
		try {
			Class.forName(Driver); // 1. 드라이버 로딩
			con = DriverManager.getConnection(Url, User, Pass); // 2. 드라이버 연결
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public ChatDTO getUserDTO(String Roomname) {
		ChatDTO dto = new ChatDTO();
		Connection con = null; // 연결
		PreparedStatement ps = null; // 명령
		ResultSet rs = null; // 결과
		try {
			con = getConn();
			String sql = "select * from Log where Roomname=?";
			ps = con.prepareStatement(sql);// prepareStatement()메소드를 통해 동적 바인딩
			System.out.println("ps=" + ps);
			ps.setString(1, Roomname);
			// 이전에 Member_List에서 선택한 회원객체가
			// MemberProc의 수정,삭제용 파라미터를 받는 객체로 가서 이 메소드를 호출, 그 때 아이디를 불러온다.
			System.out.println("ps=" + ps);
			rs = ps.executeQuery();
			System.out.println("rs=" + rs);
			if (rs.next()) {
				dto.setRoomname(rs.getString("Roomname"));
				dto.setNickname(rs.getString("Nickname"));
				dto.setTime(rs.getString("Time"));
				dto.setMsg(rs.getString("Msg"));
				System.out.println(rs.getString("Roomname") + "여기야여기");
				// 데이터가 잘 들어갔는지 확인한다.
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

	public Vector getChatList() {// 방이름과 최근 기록된 시간
		Vector data = new Vector(); // Jtable에 값을 쉽게 넣는 방법 1. 2차원배열 2. Vector 에 vector추가
		Connection con = null; // 연결
		PreparedStatement ps = null; // 명령
		ResultSet rs = null; // 결과
		try {
			con = getConn();
			String sql = "select * from Log order by Roomname asc, Time asc";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				String Roomname = rs.getString("Roomname");
				String Nickname = rs.getString("Nickname");
				String Time = rs.getString("Time");
				String Msg = rs.getString("Msg");
				Vector row = new Vector(); //
				row.add(Roomname);
				row.add(Nickname);
				row.add(Time);
				row.add(Msg);
				data.add(row);
			} // while
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}// getMemberList1()

	public Vector getChatList_User1(String a) {// 필터 조건이 2개일 때 
		Vector data = new Vector(); // Jtable에 값을 쉽게 넣는 방법 1. 2차원배열 2. Vector 에 vector추가
		Connection con = null; // 연결
		PreparedStatement ps = null; // 명령
		ResultSet rs = null; // 결과
		try {
			con = getConn();
			String sql = "select * from Log where Nickname like '" + a + "' order by Roomname asc, Time asc";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				String Roomname = rs.getString("Roomname");
				String Nickname = rs.getString("Nickname");
				String Time = rs.getString("Time");
				String Msg = rs.getString("Msg");
				Vector row = new Vector();
				row.add(Roomname);
				row.add(Nickname);
				row.add(Time);
				row.add(Msg);
				data.add(row);
			} // while
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}// getMemberList_User
	
	public Vector getChatList_User2(String a, String b) {// 필터 조건이 2개일 때 
		Vector data = new Vector(); // Jtable에 값을 쉽게 넣는 방법 1. 2차원배열 2. Vector 에 vector추가
		Connection con = null; // 연결
		PreparedStatement ps = null; // 명령
		ResultSet rs = null; // 결과
		try {
			con = getConn();
			String sql = "select * from Log where Nickname like '" + a + "' and Roomname like '" + b + "' order by Roomname asc, Time asc";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				String Roomname = rs.getString("Roomname");
				String Nickname = rs.getString("Nickname");
				String Time = rs.getString("Time");
				String Msg = rs.getString("Msg");
				Vector row = new Vector();
				row.add(Roomname);
				row.add(Nickname);
				row.add(Time);
				row.add(Msg);
				data.add(row);
			} // while
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}// getMemberList_User

	public Vector getChatList_Room1(String a) {// 필터 조건이 1개일 때
		Vector data = new Vector(); // Jtable에 값을 쉽게 넣는 방법 1. 2차원배열 2. Vector 에 vector추가
		Connection con = null; // 연결
		PreparedStatement ps = null; // 명령
		ResultSet rs = null; // 결과
		try {
			con = getConn();
			String sql = "select * from Log where Roomname like '" + a + "' order by Nickname asc, Time asc";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				String Roomname = rs.getString("Roomname");
				String Nickname = rs.getString("Nickname");
				String Time = rs.getString("Time");
				String Msg = rs.getString("Msg");
				Vector row = new Vector();
				row.add(Roomname);
				row.add(Nickname);
				row.add(Time);
				row.add(Msg);
				data.add(row);
			} // while
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}// getMemberList_Room
	
	public Vector getChatList_Room2(String a, String b) {// 필터 조건이 2개일 때
		Vector data = new Vector(); // Jtable에 값을 쉽게 넣는 방법 1. 2차원배열 2. Vector 에 vector추가
		Connection con = null; // 연결
		PreparedStatement ps = null; // 명령
		ResultSet rs = null; // 결과
		try {
			con = getConn();
			String sql = "select * from Log where Roomname like '" + a + "' and Nickname like '" + b + "' order by Nickname asc, Time asc";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				String Roomname = rs.getString("Roomname");
				String Nickname = rs.getString("Nickname");
				String Time = rs.getString("Time");
				String Msg = rs.getString("Msg");
				Vector row = new Vector();
				row.add(Roomname);
				row.add(Nickname);
				row.add(Time);
				row.add(Msg);
				data.add(row);
			} // while
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}// getMemberList_Room

	public static boolean deleteChat(String Roomname) {
		boolean ok = false;
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = getConn();
			String sql = "delete from Log where Roomname=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, Roomname); // ID확인하고 그 행(투플)을 지운다.
			int r = ps.executeUpdate(); // 실행 -> 삭제
			if (r > 0)
				ok = true; // 삭제됨;
		} catch (Exception e) {
			System.out.println(e + "-> 오류발생");
		}
		return ok;
	}

}
