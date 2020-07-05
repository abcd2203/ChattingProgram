// 관리자모드의 회원정보 텍스트에 저장

package ChattingProgram;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;

import javax.swing.JFrame;
/*
 * DB에 저장되어 있는 chat_info테이블의 데이터를 txt파일로 저장해주는 클래스
 * (txt 저장 버튼 클릭시 작동)
 */

public class DBSave extends JFrame{
	DBSave() {
		Connection conn = null;
		Statement stmt = null;
		PrintWriter writer = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn =  DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:orcl", "root", "0000");
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM tb_member");
			writer = new PrintWriter("chatting.txt");
			while(rs.next()) {
					String id = rs.getString("id");
					String name = rs.getString("name");
					String tel = rs.getString("tel");
					String post = rs.getString("post");
					String addr1 = rs.getString("addr1");
					String addr2 = rs.getString("addr2");
					String profilepath = rs.getString("profilepath");
					
				

					
					writer.printf("아이디 : %s %n이름 : %s %n전화번호 : %s %n" + "우편번호 : %s %n주소 : %s %s %n사진 경로 : %s %n "+
					"---------------------------------------------------------------------%n%n", id, name, tel, post, addr1,addr2, profilepath);
			}
			
		}
		catch(ClassNotFoundException cnfe) {
				System.out.println("해당클래스를 찾을 수 없습니다!!");
			
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(IOException ioe)
		{
			System.out.println("출력 불가");
		}
		
		finally {
			try {
				writer.close();
			}
			catch(Exception e) { }
			try {
				stmt.close();
			}
			catch(Exception ignored) {
			}
			try {
				conn.close();
			}
			catch(Exception ignored) {
			}
		}
	}
}
