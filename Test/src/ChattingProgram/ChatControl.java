package ChattingProgram;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChatControl {
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;

	// 데이터베이스 연결
	public void connection() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:ORCL", "root", "0000");

		} catch (ClassNotFoundException e) {
		} catch (SQLException e) {
		}
	}

	// 데이터베이스 연결종료
	public void disconnection() {
		try {
			if (pstmt != null)
				pstmt.close();
			if (rs != null)
				rs.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
		}
	}

	// 방이름데이터=============================================
	public ArrayList<ChatDTO> searchRoom() {
		ArrayList<ChatDTO> roomList = new ArrayList<>();
		try {
			String query = "select distinct(roomname) from log order by roomname asc";
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ChatDTO chatcode = new ChatDTO();
				chatcode.setRoomname(rs.getString("ROOMNAME"));
				roomList.add(chatcode);
			}
		} catch (SQLException e) {
		}

		return roomList;

	}

	// 닉네임데이터=============================================
	public ArrayList<ChatDTO> searchNick() {
		ArrayList<ChatDTO> nickList = new ArrayList<>();

		try {
			String query = "select distinct(nickname) from log order by nickname asc";
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ChatDTO chatcode = new ChatDTO();
				chatcode.setNickname(rs.getString("NICKNAME"));
				nickList.add(chatcode);
			}
		} catch (SQLException e) {
		}

		return nickList;
	}

}