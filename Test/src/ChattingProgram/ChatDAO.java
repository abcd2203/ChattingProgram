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
		Connection con = null;// DB ���� �ʱ�ȭ
		try {
			Class.forName(Driver); // 1. ����̹� �ε�
			con = DriverManager.getConnection(Url, User, Pass); // 2. ����̹� ����
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public ChatDTO getUserDTO(String Roomname) {
		ChatDTO dto = new ChatDTO();
		Connection con = null; // ����
		PreparedStatement ps = null; // ���
		ResultSet rs = null; // ���
		try {
			con = getConn();
			String sql = "select * from Log where Roomname=?";
			ps = con.prepareStatement(sql);// prepareStatement()�޼ҵ带 ���� ���� ���ε�
			System.out.println("ps=" + ps);
			ps.setString(1, Roomname);
			// ������ Member_List���� ������ ȸ����ü��
			// MemberProc�� ����,������ �Ķ���͸� �޴� ��ü�� ���� �� �޼ҵ带 ȣ��, �� �� ���̵� �ҷ��´�.
			System.out.println("ps=" + ps);
			rs = ps.executeQuery();
			System.out.println("rs=" + rs);
			if (rs.next()) {
				dto.setRoomname(rs.getString("Roomname"));
				dto.setNickname(rs.getString("Nickname"));
				dto.setTime(rs.getString("Time"));
				dto.setMsg(rs.getString("Msg"));
				System.out.println(rs.getString("Roomname") + "����߿���");
				// �����Ͱ� �� ������ Ȯ���Ѵ�.
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

	public Vector getChatList() {// ���̸��� �ֱ� ��ϵ� �ð�
		Vector data = new Vector(); // Jtable�� ���� ���� �ִ� ��� 1. 2�����迭 2. Vector �� vector�߰�
		Connection con = null; // ����
		PreparedStatement ps = null; // ���
		ResultSet rs = null; // ���
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

	public Vector getChatList_User1(String a) {// ���� ������ 2���� �� 
		Vector data = new Vector(); // Jtable�� ���� ���� �ִ� ��� 1. 2�����迭 2. Vector �� vector�߰�
		Connection con = null; // ����
		PreparedStatement ps = null; // ���
		ResultSet rs = null; // ���
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
	
	public Vector getChatList_User2(String a, String b) {// ���� ������ 2���� �� 
		Vector data = new Vector(); // Jtable�� ���� ���� �ִ� ��� 1. 2�����迭 2. Vector �� vector�߰�
		Connection con = null; // ����
		PreparedStatement ps = null; // ���
		ResultSet rs = null; // ���
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

	public Vector getChatList_Room1(String a) {// ���� ������ 1���� ��
		Vector data = new Vector(); // Jtable�� ���� ���� �ִ� ��� 1. 2�����迭 2. Vector �� vector�߰�
		Connection con = null; // ����
		PreparedStatement ps = null; // ���
		ResultSet rs = null; // ���
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
	
	public Vector getChatList_Room2(String a, String b) {// ���� ������ 2���� ��
		Vector data = new Vector(); // Jtable�� ���� ���� �ִ� ��� 1. 2�����迭 2. Vector �� vector�߰�
		Connection con = null; // ����
		PreparedStatement ps = null; // ���
		ResultSet rs = null; // ���
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
			ps.setString(1, Roomname); // IDȮ���ϰ� �� ��(����)�� �����.
			int r = ps.executeUpdate(); // ���� -> ����
			if (r > 0)
				ok = true; // ������;
		} catch (Exception e) {
			System.out.println(e + "-> �����߻�");
		}
		return ok;
	}

}
