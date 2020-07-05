// �����ڸ���� ȸ������ �ؽ�Ʈ�� ����

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
 * DB�� ����Ǿ� �ִ� chat_info���̺��� �����͸� txt���Ϸ� �������ִ� Ŭ����
 * (txt ���� ��ư Ŭ���� �۵�)
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
					
				

					
					writer.printf("���̵� : %s %n�̸� : %s %n��ȭ��ȣ : %s %n" + "�����ȣ : %s %n�ּ� : %s %s %n���� ��� : %s %n "+
					"---------------------------------------------------------------------%n%n", id, name, tel, post, addr1,addr2, profilepath);
			}
			
		}
		catch(ClassNotFoundException cnfe) {
				System.out.println("�ش�Ŭ������ ã�� �� �����ϴ�!!");
			
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(IOException ioe)
		{
			System.out.println("��� �Ұ�");
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
