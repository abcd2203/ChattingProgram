// 관리자모드의 데이터베이스 삭제

package ChattingProgram;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;


public class DBDelete {

	public DBDelete() {
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "root", "0000");
			
			stmt = conn.createStatement();
			stmt.executeUpdate("delete from tb_member"); // user_info 오라클 데이터 삭제
			JOptionPane.showMessageDialog(null,  "DB가 모두 삭제되었습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
		}
		catch(ClassNotFoundException cnfe) {
			System.out.println("해당 클래스를 찾을 수 없습니다." + cnfe.getMessage());
		}
		catch(SQLException se) {
			System.out.println(se.getMessage());
		}
		finally {
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
