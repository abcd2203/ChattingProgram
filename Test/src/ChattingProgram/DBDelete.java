// �����ڸ���� �����ͺ��̽� ����

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
			stmt.executeUpdate("delete from tb_member"); // user_info ����Ŭ ������ ����
			JOptionPane.showMessageDialog(null,  "DB�� ��� �����Ǿ����ϴ�.", "�˸�", JOptionPane.INFORMATION_MESSAGE);
		}
		catch(ClassNotFoundException cnfe) {
			System.out.println("�ش� Ŭ������ ã�� �� �����ϴ�." + cnfe.getMessage());
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
