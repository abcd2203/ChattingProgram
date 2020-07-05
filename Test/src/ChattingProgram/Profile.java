package ChattingProgram;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Profile extends JFrame {

	private static Properties info;
	private static Connection con;
	private static Statement st1;
	private static String sql;
	private static ResultSet rs;
	private JPanel Pane1;
	private JPanel d;
	private JPanel profile;
	private JPanel panel;
	private JLabel Profile;
	private JLabel photo;
	private JLabel ID;
	private JLabel ID_info;
	private JLabel Name;
	private JLabel Name_info;
	private JLabel Tel;
	private JLabel Tel_info;
	private JLabel Nick;
	private JLabel Nick_info;
	private JLabel gender;
	private JLabel gender_info;
	private JButton btnNewButton;
	private JLabel img;

	/**
	 * Launch the application.
	 */
	public Profile(JButton btn) {}
	
	public Profile() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\36175\\Desktop\\이모티콘\\설정.jpg"));
		setTitle("내 정보");
		setBounds(100, 100, 450, 480);
		Pane1 = new JPanel();
		Pane1.setBackground(Color.LIGHT_GRAY);
		Pane1.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(Pane1);
		Pane1.setLayout(null);
		
		profile = new JPanel();
		profile.setBounds(0, 0, 434, 446);
		Pane1.add(profile);
		profile.setLayout(null);
		
		Profile = new JLabel("< 내정보 >");
		Profile.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		Profile.setBounds(158, 22, 102, 34);
		profile.add(Profile);
		
		photo = new JLabel("");
		photo.setIcon(new ImageIcon("C:\\Users\\36175\\Desktop\\이모티콘\\기본프로필.jpg"));
		photo.setBounds(134, 55, 153, 160);
		profile.add(photo);
		// Client.checkIDImage(Client.id, 9);
		photo.setIcon(new ImageIcon(checkIDImage(Client.id, 9)));
		
		ID = new JLabel("아이디    :");
		ID.setBounds(134, 258, 71, 15);
		profile.add(ID);
		
		ID_info = new JLabel("");
		ID_info.setBounds(217, 258, 97, 15);
		profile.add(ID_info);
		ID_info.setText(checkIDImage(Client.id, 1));
		
		Name = new JLabel("이름       :");
		Name.setBounds(134, 283, 71, 15);
		profile.add(Name);
		
		Name_info = new JLabel("");
		Name_info.setBounds(217, 283, 97, 15);
		profile.add(Name_info);
		Name_info.setText(checkIDImage(Client.id, 3));
		
		Tel = new JLabel("전화번호 :");
		Tel.setBounds(134, 308, 71, 15);
		profile.add(Tel);
		
		Tel_info = new JLabel("");
		Tel_info.setBounds(217, 308, 97, 15);
		profile.add(Tel_info);
		Tel_info.setText(checkIDImage(Client.id, 4));
		
		Nick = new JLabel("별명       :");
		Nick.setBounds(134, 333, 71, 15);
		profile.add(Nick);
		
		Nick_info = new JLabel("");
		Nick_info.setBounds(217, 333, 97, 15);
		profile.add(Nick_info);
		Nick_info.setText(checkIDImage(Client.id, 7));
		
		gender = new JLabel("성별       :");
		gender.setBounds(134, 358, 57, 15);
		profile.add(gender);
		
		gender_info = new JLabel("");
		gender_info.setBounds(217, 358, 97, 15);
		profile.add(gender_info);
		gender_info.setText(checkIDImage(Client.id, 8));
		
		btnNewButton = new JButton("정보 수정");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MemberProc mem = new MemberProc(Client.id,checkIDImage(Client.id, 9)); //아이디를 인자로 수정창 생성
			}
		});
		btnNewButton.setBounds(163, 220, 97, 23);
		profile.add(btnNewButton);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(114, 22, 192, 364);
		profile.add(panel_1);
		
		img = new JLabel("");
		img.setIcon(new ImageIcon("C:\\Users\\36175\\Desktop\\\uC774\uBAA8\uD2F0\uCF58\\\uD504\uB85C\uD544.png"));
		img.setBounds(0, 0, 434, 446);
		profile.add(img);
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Profile frame = new Profile();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static String checkIDImage(String id, int a) {// 쉽게말해 아이디를 입력 그 아이디에 해당하는 프로필사진을 출력
		id = id;
		String str = null;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver"); // 알아서 들어간다..conn으로
			String url = "jdbc:oracle:thin:@localhost:1521:orcl"; //
			// ?useSSL=false -> SSL연결설정 오류 해결
			info = new Properties();
			info.setProperty("user", "root"); // root 계정으로
			info.setProperty("password", "0000"); // pw는 0000
			con = DriverManager.getConnection(url, info); // 연결할 정보를 가지고있는 드라이버매니저를 던진다
			st1 = con.createStatement(); // db접속

			sql = "select * from tb_member where id='" + id + "'";
			rs = st1.executeQuery(sql); // 입력id와 테이블에 저장된 id와 비교
			// sql = "select * from (select * from joindb where id='" + id + "')";
			while (rs.next() == true) { // 다음값의
				str = rs.getString(a); // 같으면 로그인 성공
			}
		} catch (Exception ee) { // 익셉션처리
			System.out.println("문제있음");
			ee.printStackTrace();
		}
		return str;
	}
}
