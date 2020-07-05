package ChattingProgram;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class searchPwd extends JFrame {

	private JPanel Pane1;
	private JTextField tfpwd;
	private JButton btn;
	private JButton btn_cancle;
	private JTabbedPane tabbedPane;
	private JPanel search_pwd;
	private JPanel search_id;
	private JTextField tfid;
	private JButton button;
	private JLabel lblId;
	private JLabel label_1;
	private JButton button_1;

	/**
	 * Launch the application.
	 */
	public searchPwd(JButton btn) {}
	
	public searchPwd() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\36175\\Desktop\\\uC774\uBAA8\uD2F0\uCF58\\\uC2ED\uC790\uAC00\uBC84\uD2BC_\uC785\uB825.png"));
		setTitle("ID/\uBE44\uBC00\uBC88\uD638 \uCC3E\uAE30");
		setBounds(100, 100, 450, 290);
		Pane1 = new JPanel();
		Pane1.setBackground(Color.LIGHT_GRAY);
		Pane1.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(Pane1);
		Pane1.setLayout(null);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 434, 261);
		Pane1.add(tabbedPane);
		
		search_id = new JPanel();
		tabbedPane.addTab("ID 찾기", null, search_id, null);
		search_id.setLayout(null);
		
		tfid = new JTextField();
		tfid.setColumns(10);
		tfid.setBounds(132, 106, 116, 21);
		search_id.add(tfid);
		
		button = new JButton("찾기");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String name = tfid.getText().trim();
	         	java.sql.Connection conn = null;
	         	java.sql.Statement stmt = null;
	         	
	         	try {
	         		Class.forName("oracle.jdbc.driver.OracleDriver");
	         		conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "root", "0000");
	         		stmt = conn.createStatement();
	         		ResultSet rs = stmt.executeQuery("Select * from TB_MEMBER");

	         		while(rs.next())
	         		{
	         			if(name.equals(rs.getString("name")))
	         			{	
	         				
	         				System.out.println(rs.getString("id"));
	         				JOptionPane.showMessageDialog(null, "ID는 ["+rs.getString("id")+"] 입니다.", "ID", JOptionPane.INFORMATION_MESSAGE);
	         				tfid.requestFocus();
	         				return;
	         			}
	         		}
	         		JOptionPane.showMessageDialog(null, "이름을 확인해주세요!", "알림", JOptionPane.ERROR_MESSAGE);
	         		tfid.requestFocus();
	         		boolean ID_Check = true;
	         		return;
	         	 }

	         	catch(ClassNotFoundException cnfe) {
	        		System.out.println("해당 클래스를 찾을 수 없습니다."+cnfe.getMessage());
	        	}
	        	
	        	catch(SQLException se) {
	        		
	        	}
			}
		});
		button.setFont(new Font("함초롬돋움", Font.BOLD, 12));
		button.setBounds(256, 101, 63, 30);
		search_id.add(button);
		
		lblId = new JLabel("ID \uCC3E\uAE30");
		lblId.setFont(new Font("함초롬돋움", Font.BOLD, 21));
		lblId.setBounds(178, 41, 83, 30);
		search_id.add(lblId);
		
		label_1 = new JLabel("\uC774\uB984\uC744 \uC785\uB825\uD574\uC8FC\uC138\uC694");
		label_1.setFont(new Font("굴림", Font.PLAIN, 15));
		label_1.setBounds(132, 81, 138, 15);
		search_id.add(label_1);
		
		button_1 = new JButton("\uB2EB\uAE30");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		button_1.setBounds(320, 186, 97, 23);
		search_id.add(button_1);
		
		search_pwd = new JPanel();
		tabbedPane.addTab("비밀번호 찾기", null, search_pwd, null);
		search_pwd.setLayout(null);
		
		
		tfpwd = new JTextField();
		tfpwd.setBounds(132, 106, 116, 21);
		search_pwd.add(tfpwd);
		tfpwd.setColumns(10);
		
		btn = new JButton("\uCC3E\uAE30");
		btn.setBounds(256, 101, 63, 30);
		search_pwd.add(btn);
		btn.setFont(new Font("함초롬돋움", Font.BOLD, 12));
		
		JLabel lblNewLabel = new JLabel("비밀번호 찾기");
		lblNewLabel.setBounds(150, 38, 131, 30);
		search_pwd.add(lblNewLabel);
		lblNewLabel.setFont(new Font("함초롬돋움", Font.BOLD, 21));
		
		JLabel lblNewLabel_1 = new JLabel("ID를 입력해주세요");
		lblNewLabel_1.setBounds(132, 81, 116, 15);
		search_pwd.add(lblNewLabel_1);
		lblNewLabel_1.setFont(new Font("굴림", Font.PLAIN, 15));
		
		btn_cancle = new JButton("닫기");
		btn_cancle.setBounds(320, 186, 97, 23);
		search_pwd.add(btn_cancle);
		btn_cancle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
		         	String id = tfpwd.getText().trim();
		         	java.sql.Connection conn = null;
		         	java.sql.Statement stmt = null;
		         	
		         	try {
		         		Class.forName("oracle.jdbc.driver.OracleDriver");
		         		conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "root", "0000");
		         		stmt = conn.createStatement();
		         		ResultSet rs = stmt.executeQuery("Select * from TB_MEMBER");

		         		while(rs.next())
		         		{
		         			if(id.equals(rs.getString("id")))
		         			{	
		         				
		         				System.out.println(rs.getString("pwd"));
		         				JOptionPane.showMessageDialog(null, "비밀번호는 ["+rs.getString("pwd")+"] 입니다.", "비밀번호", JOptionPane.INFORMATION_MESSAGE);
		         				tfpwd.requestFocus();
		         				return;
		         			}
		         		}
		         		JOptionPane.showMessageDialog(null, "ID를 확인해주세요!", "알림", JOptionPane.ERROR_MESSAGE);
		         		tfpwd.requestFocus();
		         		boolean ID_Check = true;
		         		return;
		         	 }

		         	catch(ClassNotFoundException cnfe) {
		        		System.out.println("해당 클래스를 찾을 수 없습니다."+cnfe.getMessage());
		        	}
		        	
		        	catch(SQLException se) {
		        		
		        	}
		    	 }
			
		});
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					searchPwd frame = new searchPwd();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
