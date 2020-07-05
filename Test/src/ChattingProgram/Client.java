// 실제로 사용할 Client(로그인 창 + 채팅창)

package ChattingProgram;

import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import papago.Papago;

public class Client extends JFrame implements ActionListener, KeyListener {
	private static final Object Message = null;
// 추상 클래스로 구성되어있으니 재정의를함
// ctrl + shift + o (자동으로 3번째 줄과 같이 import)

// Login GUI 변수
	private JFrame Login_GUI = new JFrame(); // Login_GUI에 대한 객체를 생성
	private JPanel Login_Pane;
	private JTextField ip_tf; // ip 받는 텍스트필드
	private JTextField port_tf; // port 받는 텍스트 필드
	private JTextField id_tf; // id 받는 텍스트필드
	private JPasswordField pw_tf; // passwd 받는 텍스트필드
	private JButton login_btn = new JButton("로그인"); // 로그인버튼 (구성요소를 알아보기 쉽게 전역변수로 뺐음)
	private JButton adduser_btn = new JButton("가입하기"); // 회원가입버튼
	private JButton search_pwdbtn = new JButton("ID 비밀번호 찾기"); // 비밀번호 찾기 버튼

	public static ImageIcon icon;

// Main GUI 변수
	private JFrame Main_GUI = new JFrame(); // Main_GUI에 대한 객체를 생성
	private JPanel Main_Pane;
	private JTextField message_tf;
	private JButton notesend_btn = new JButton("쪽지보내기");
	private JButton joinroom_btn = new JButton("채팅방참여");
	private JButton createroom_btn = new JButton("\uBC29 \uB9CC\uB4E4\uAE30");
	private JButton send_btn = new JButton("전송");
	private JButton logout_btn = new JButton("\uC811\uC18D \uC885\uB8CC");
	private JButton save_btn = new JButton("채팅 저장");
	private JButton roomout_btn = new JButton("방 나가기");
	private JButton kick = new JButton("강퇴");
	private JButton invitebtn = new JButton("초대하기");
	private JButton friend_del;
	private JButton friend_add;
	private JButton Emo;

	public static StyledDocument doc;

	private JList User_list = new JList(); // 전체 접속자 List
	private JList Room_list = new JList(); // 전체 방목록 List
	private JList friend_list; // 친구목록 List

	private JTextPane Chat_Pane = new JTextPane(); // 채팅창 변수
	private JScrollPane scrollPane = new JScrollPane();

// 네트워크를 위한 자원 변수  (로그인 창에서 입력할 것들)
	private Socket socket; // 모든 상황에서의 소켓을 준비가능
	private String ip = ""; // 자기 자신의 ip주소
	private int port; // 자기 자신의 port번호
	public static String id = "";
	private String pwd = "";
	private String chatname = "";
	private InputStream is; // 클라이언트의 데이터가 서버로부터 들어온다.
	private OutputStream os; // 클라이언트의 데이터를 서버로 보냄.
	private DataInputStream dis; // 서버의 데이터가 클라이언트로부터 들어온다.
	private static DataOutputStream dos; // 서버의 데이터를 클라이언트로 보냄

// 그외 변수들
	Vector user_list = new Vector();
	Vector room_list = new Vector();
	Vector room_member_list = new Vector();
	StringTokenizer st;

	public static String My_Room; // 내가 있는 방 이름
	private JLabel Room_label;
	private JLabel LoginPanel;

	private static Properties info; // 프로퍼티 (파일읽기)

	private static java.sql.Connection con;

	private static Statement st1;

	private static String sql;

	private static ResultSet rs;
	private final JScrollBar scrollBar = new JScrollBar();
	static Clip clip;
	static Clip clip2;

	private String Admin;

	private JButton Location; // 구글API 위치 버튼
	private JButton Location2; // 구글API 더 좋은 버전 버튼
	private JButton Filebtn; // 파일전송 버튼
	private String imgsend;
	private String map_path = "C:\\Users\\36175\\Desktop\\이모티콘\\맵\\";
	private JButton Profilebtn;
	private JLabel Image1;
	private JLabel Image2;
	
	private JLabel Image3;
	private JButton ChangeBG;
	private JButton Image4;

// 필요 구성 요소를 전역변수로 빼면 좋은점 : 1. 이벤트 처리, 나중에 다른 기능을 추가할 때 편하다.
// 							  2. 개발자 프로그램 입문자들이 프로그램 이해하기 편하다.

	Client() // 생성자 메소드
	{
		Login_init(); // 로그인창 화면 구성 메소드
		Main_init(); // 채팅창 화면 구성 메소드

		start(); // 접속버튼을 누르면 "Login Button Click", "AddUser Button Click"
		// 이라는 메시지가 나오도록 이벤트가 발생하게 해줌

	}

	private void start() {
		login_btn.addActionListener(this); // 로그인 버튼 리스너 (현재 클래스에서 상속받음)
		adduser_btn.addActionListener(this); // 회원가입 버튼 리스너
		search_pwdbtn.addActionListener(this); // 비밀번호 찾기 버튼 리스너
		notesend_btn.addActionListener(this); // 쪽지 보내기 버튼 리스너
		joinroom_btn.setForeground(new Color(147, 112, 219));
		joinroom_btn.setBackground(SystemColor.info);
		joinroom_btn.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		joinroom_btn.addActionListener(this); // 채팅방 참여 버튼 리스너
		createroom_btn.setBackground(SystemColor.info);
		createroom_btn.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		createroom_btn.addActionListener(this); // 채팅방 만들기 버튼 리스너
		send_btn.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		send_btn.addActionListener(this); // 채팅 전송 버튼 리스너
		logout_btn.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		logout_btn.setBackground(SystemColor.info);
		logout_btn.setForeground(Color.RED);
		logout_btn.addActionListener(this); // 로그아웃 버튼 리스너
		message_tf.addKeyListener(this); // 채팅 내용 입력 창 리스너
		save_btn.addActionListener(this); // 채팅 저장 버튼 리스너
		roomout_btn.addActionListener(this);
		Location.addActionListener(this); // 위치버튼 리스너
		// Location2.addActionListener(this); // 위치버튼2 리스너
		Filebtn.addActionListener(this); // 파일전송버튼 리스너
		Papago.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		Papago.addActionListener(this); // 파파고버튼 리스너
		Profilebtn.addActionListener(this);
		Music_stop.setBackground(SystemColor.info);
		Music_stop.setForeground(SystemColor.textHighlight);
		Music_stop.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		Music_stop.addActionListener(this);
		Music_play.addActionListener(this);
		Music_Back.addActionListener(this);
		Music_Next.addActionListener(this);
		ChangeBG.addActionListener(this);
		protect.addActionListener(this);
	}

	private void Login_init() // 로그인창 화면 구성
	{
		
		Login_GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Login_GUI에 대한 기 버튼 구성
		Login_GUI.setBounds(100, 100, 900, 600);// Login_GUI에 대한 화면 크기
		Login_GUI.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\36175\\Desktop\\이모티콘\\박주호.jpg"));
		Login_GUI.setTitle("Login");

		ImageIcon icon = new ImageIcon("C:\\Users\\36175\\Desktop\\이모티콘\\마크1.jpg");
		this.setIconImage(icon.getImage());
		this.setSize(320, 471);
		Login_Pane = new JPanel() {
			public void paintComponent(Graphics g) {
				// Approach 1 : Dispaly image at at full size
				g.drawImage(icon.getImage(), 0, 0, null);
				setOpaque(false); // 그림을 표시하게 설정, 투명하게 조절
				super.paintComponents(g);
			}

		};

		try {
			File file = new File("C:\\Users\\36175\\Desktop\\이모티콘\\music\\wood.wav");
			AudioInputStream stream = AudioSystem.getAudioInputStream(file);
			clip = AudioSystem.getClip();
			clip.open(stream);
			clip.start();
			if (true) clip.loop(-1);	// 무한 재생
			// clip.close();

		} catch (Exception f) {

			f.printStackTrace();
		}

		Login_Pane.setBackground(Color.YELLOW);
		Login_GUI.setContentPane(Login_Pane);
		Login_Pane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Server IP");
		lblNewLabel.setBounds(39, 209, 70, 15);
		Login_Pane.add(lblNewLabel);
		lblNewLabel.setVisible(false);

		JLabel lblServerPort = new JLabel("Server Port");
		lblServerPort.setBounds(38, 244, 71, 15);
		Login_Pane.add(lblServerPort);
		lblServerPort.setVisible(false);

		LoginPanel = new JLabel("");
		LoginPanel.setBounds(200, 200, 153, 148);
		Login_Pane.add(LoginPanel);
		LoginPanel.setForeground(new Color(128, 128, 128));
		LoginPanel.setIcon(new ImageIcon(""));

		JLabel lblNewLabel_1 = new JLabel("아이디");
		lblNewLabel_1.setBounds(320, 200, 70, 15);
		Login_Pane.add(lblNewLabel_1);
		lblNewLabel_1.setForeground(new Color(128, 128, 128));

		JLabel lblPassword = new JLabel("비밀번호");
		lblPassword.setBounds(320, 250, 70, 15);
		Login_Pane.add(lblPassword);
		lblPassword.setForeground(new Color(128, 128, 128));

		JLabel lblTitle = new JLabel("돌아오신 것을 환영해요!");
		lblTitle.setBounds(308, 150, 500, 23);
		lblTitle.setFont(new Font("휴먼고딕", Font.BOLD, 21));
		Login_Pane.add(lblTitle);

		JLabel lblsetTitle = new JLabel("다시 만나다니 너무 반가워요!");
		lblsetTitle.setBounds(333, 175, 500, 15);
		lblsetTitle.setFont(new Font("휴먼고딕", Font.PLAIN, 15));
		Login_Pane.add(lblsetTitle);
		lblsetTitle.setForeground(new Color(128, 128, 128));

		ip_tf = new JTextField();
		ip_tf.setBounds(142, 206, 141, 21);
		Login_Pane.add(ip_tf);
		ip_tf.setColumns(10);
		ip_tf.setText("127.0.0.1");
		ip_tf.setVisible(false);

		port_tf = new JTextField();
		port_tf.setBounds(142, 241, 141, 21);
		Login_Pane.add(port_tf);
		port_tf.setColumns(10);
		port_tf.setText("12345");
		port_tf.setVisible(false);

		id_tf = new JTextField();
		id_tf.setBounds(310, 220, 244, 21);
		Login_Pane.add(id_tf);
		id_tf.setColumns(10);

		pw_tf = new JPasswordField();
		pw_tf.setBounds(310, 270, 244, 21);
		Login_Pane.add(pw_tf);
		pw_tf.setColumns(10);

		login_btn.setBounds(310, 320, 244, 23);
		Login_Pane.add(login_btn);

		JLabel lblNewLabel_3 = new JLabel("계정이 필요한가요?");
		lblNewLabel_3.setBounds(315, 350, 200, 12);
		Login_Pane.add(lblNewLabel_3);
		lblNewLabel_3.setForeground(new Color(128, 128, 128));

		adduser_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent args0) {
				MemberProc frame = new MemberProc();
			}

		});
		adduser_btn.setBounds(450, 350, 86, 15);
		Login_Pane.add(adduser_btn);

		search_pwdbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent args0) {
				searchPwd frame = new searchPwd();
				frame.setVisible(true);
			}

		});
		search_pwdbtn.setBounds(430, 370, 130, 15);
		Login_Pane.add(search_pwdbtn);

		Login_GUI.setVisible(true); // Login_GUI 화면이 정상적으로 구성되었다.
		Login_GUI.setResizable(false);
		this.setVisible(false);
	}

	public boolean DB_Check(String id, String pwd) {
		Connection conn = null;
		java.sql.Statement stmt = null;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = (Connection) DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:orcl", "root", "0000");

			if (conn != null) {
				System.out.println("DB연결완료");
			}

			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from tb_member where id = '" + id + "' and pwd = '" + pwd + "'");

			if (rs.next()) {
				if (id.equals(rs.getString("id")) && pwd.equals(rs.getString("pwd"))) {
					return true;
				}

			}
		}

		catch (ClassNotFoundException cnfe) {
			System.out.println("해당 클래스를 찾을 수 없습니다." + cnfe.getMessage());
		}

		catch (SQLException se) {
			System.out.println(se.getMessage());
		}

		finally {
			try {
				stmt.close();
			} catch (Exception ignored) {

			}
			try {
				conn.close();
			} catch (Exception ignored) {

			}
		}

		JOptionPane.showMessageDialog(null, "id나 비밀번호가 틀립니다!", "알림", JOptionPane.ERROR_MESSAGE);
		return false;
	}

	private void Main_init() // 채팅창 화면 구성
	{
		Main_GUI.setTitle("대기실/채팅방");
		Main_GUI.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\36175\\Desktop\\이모티콘\\박주호.jpg"));
		Main_GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Main_GUI.setBounds(100, 100, 1030, 800);
		ImageIcon icon = new ImageIcon("");
		this.setIconImage(icon.getImage());
		this.setSize(588, 531);
		Main_Pane = new JPanel() {
			public void paintComponent(Graphics g) {
				// Approach 1 : Dispaly image at at full size
				g.drawImage(icon.getImage(), 0, 0, null);
				setOpaque(false); // 그림을 표시하게 설정, 투명하게 조절
				super.paintComponents(g);
			}

		};
		
		
		Main_Pane.setBackground(Color.DARK_GRAY);
		Main_Pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		Main_GUI.setContentPane(Main_Pane);
		Main_Pane.setLayout(null);
		
		Image4 = new JButton("");		//화면 보호 이미지 버튼
		Image4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Image4.setVisible(false);
			}
		});
		Image4.setVisible(false);
		Image4.setBorderPainted( false );
		Image4.setFocusPainted(false); 
		Image4.setContentAreaFilled(false);
		Image4.setIcon(new ImageIcon("C:\\Users\\36175\\Desktop\\이모티콘\\에비츄.gif"));
		Image4.setHorizontalAlignment(SwingConstants.CENTER);
		Image4.setBounds(0, 0, 1024, 771);
		Main_Pane.add(Image4);
		User_list.setBackground(new Color(244, 164, 96));

		User_list.setBounds(751, 137, 222, 374);
		Main_Pane.add(User_list);
		User_list.setListData(user_list);
		User_list.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		scrollBar.setBounds(974, 137, 17, 374);

		Main_Pane.add(scrollBar);

		notesend_btn.setBounds(751, 519, 113, 23);
		Main_Pane.add(notesend_btn);

		Room_label = new JLabel("* \uCC44 \uD305 \uBC29 \uBAA9 \uB85D *");
		Room_label.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		Room_label.setBounds(110, 102, 142, 37);
		Main_Pane.add(Room_label);
		Room_list.setFont(new Font("굴림", Font.PLAIN, 25));

		Room_list.setBackground(new Color(51, 153, 102));
		Room_list.setBounds(45, 137, 689, 405);
		Main_Pane.add(Room_list);
		Room_list.setListData(room_list);

		joinroom_btn.setBounds(45, 568, 700, 80);
		Main_Pane.add(joinroom_btn);

		scrollPane.setBounds(45, 102, 689, 442);
		Main_Pane.add(scrollPane);
		Chat_Pane.setBackground(new Color(250, 128, 114));
		scrollPane.setViewportView(Chat_Pane);
		Chat_Pane.setEditable(true);

		message_tf = new JTextField();
		message_tf.setBackground(Color.WHITE);
		message_tf.setBounds(45, 568, 484, 170);
		Main_Pane.add(message_tf);
		message_tf.setColumns(10);
		message_tf.setVisible(false);

		send_btn.setBounds(541, 658, 204, 80);
		Main_Pane.add(send_btn);
		send_btn.setVisible(false);
		
				createroom_btn.setBounds(45, 658, 700, 80);
				Main_Pane.add(createroom_btn);

		logout_btn.setBounds(899, 10, 113, 34);
		Main_Pane.add(logout_btn);

		save_btn.setBounds(541, 742, 204, 21);
		Main_Pane.add(save_btn);

		roomout_btn.setBounds(621, 543, 113, 15);
		roomout_btn.setEnabled(false);
		Main_Pane.add(roomout_btn);

		invitebtn.setEnabled(false);
		invitebtn.setBounds(751, 545, 113, 23);
		Main_Pane.add(invitebtn);

		friend_list = new JList();
		friend_list.setBounds(751, 137, 222, 374);
		Main_Pane.add(friend_list);
		friend_list.setVisible(false);

		friend_add = new JButton("친구추가");
		friend_add.setBounds(878, 519, 113, 23);
		Main_Pane.add(friend_add);

		friend_del = new JButton("\uCE5C\uAD6C\uC0AD\uC81C");
		friend_del.setBounds(878, 545, 113, 23);
		Main_Pane.add(friend_del);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(832, 23, 2, 2);
		Main_Pane.add(scrollPane_1);

		Emo = new JButton("이모티콘");
		Emo.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		Emo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				emoticon Emoticon = new emoticon();
			}
		});
		Emo.setBounds(648, 611, 97, 37);
		Main_Pane.add(Emo);

		Music_stop.setBounds(648, 11, 113, 34);

		Main_Pane.add(Music_stop);
		Music_play.setForeground(new Color(153, 50, 204));
		Music_play.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		Music_play.setBackground(SystemColor.info);
		Music_play.setBounds(648, 11, 113, 34);
		
		Main_Pane.add(Music_play);

		Location = new JButton("지도");
		Location.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		Location.setBounds(542, 566, 97, 37);
		Main_Pane.add(Location);

		Filebtn = new JButton("\uC0AC\uC9C4\uC804\uC1A1");
		Filebtn.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		Filebtn.setBounds(648, 566, 97, 37);
		Main_Pane.add(Filebtn);

		Papago.setBounds(542, 611, 97, 37);
		Main_Pane.add(Papago);

		Profilebtn = new JButton("");
		Profilebtn.setIcon(new ImageIcon("C:\\Users\\36175\\Desktop\\이모티콘\\기본프로필.jpg"));
		Profilebtn.setBounds(800, 578, 153, 148);
		Main_Pane.add(Profilebtn);
		button.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				User_list.setVisible(true);
				friend_list.setVisible(false);
				
			}
		});
		button.setBounds(751, 102, 120, 33);
		
		Main_Pane.add(button);
		button_1.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				User_list.setVisible(false);
				friend_list.setVisible(true);
			}
		});
		
		button_1.setBounds(871, 102, 120, 33);
		
		Main_Pane.add(button_1);
		
		ChangeBG = new JButton("배경 변경");
		ChangeBG.setForeground(SystemColor.textHighlight);
		ChangeBG.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		ChangeBG.setBackground(SystemColor.info);
		ChangeBG.setBounds(774, 54, 113, 34);
		Main_Pane.add(ChangeBG);

		protect.setForeground(new Color(0, 128, 0));
		protect.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		protect.setBackground(SystemColor.info);
		protect.setBounds(899, 54, 113, 34);
		
		Main_Pane.add(protect);
		Music_Next.setForeground(SystemColor.textHighlight);
		Music_Next.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		Music_Next.setBackground(SystemColor.info);
		Music_Next.setBounds(774, 10, 113, 34);
		
		Main_Pane.add(Music_Next);
		Music_Back.setForeground(SystemColor.textHighlight);
		Music_Back.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		Music_Back.setBackground(SystemColor.info);
		Music_Back.setBounds(523, 10, 113, 34);
		
		Main_Pane.add(Music_Back);
		
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setBackground(Color.WHITE);
		lblNewLabel_2.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		lblNewLabel_2.setBounds(838, 726, 91, 28);
		
		Main_Pane.add(lblNewLabel_2);
		Music_title.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		Music_title.setBackground(Color.WHITE);
		Music_title.setBounds(571, 55, 190, 37);
		
		Main_Pane.add(Music_title);
		label.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		label.setBounds(502, 54, 71, 38);
		
		Main_Pane.add(label);
		
		Image1 = new JLabel("");

		Image1.setIcon(new ImageIcon("C:\\Users\\36175\\Desktop\\이모티콘\\크리스마스2.gif"));
		Image1.setHorizontalAlignment(SwingConstants.CENTER);

		Image1.setBounds(0, 0, 1024, 771);
		Main_Pane.add(Image1);
		
		Image2 = new JLabel("");
		Image2.setVisible(false);

		Image2.setIcon(new ImageIcon("C:\\Users\\36175\\Desktop\\이모티콘\\배경.jpg"));
		Image2.setHorizontalAlignment(SwingConstants.CENTER);

		Image2.setBounds(0, 0, 1024, 771);
		Main_Pane.add(Image2);
		
		Image3 = new JLabel("");
		Image3.setVisible(false);

		Image3.setIcon(new ImageIcon("C:\\Users\\36175\\Desktop\\이모티콘\\4.png"));
		Image3.setHorizontalAlignment(SwingConstants.CENTER);
		Image3.setBounds(0, 0, 1024, 771);
		Main_Pane.add(Image3);

//	Location2 = new JButton("\uC9C0\uB3C42");
//	Location2.setBounds(779, 483, 73, 23);
//	Main_Pane.add(Location2);

		Main_GUI.setResizable(false);

		this.setVisible(false); // 화면이 정상적으로 구성됨
		// (현재 클래스에서 JFrame을 상속받았기 때문에 앞에 "this."를 붙인다.)
	}

	private void Network() {
		Profilebtn.setIcon(new ImageIcon(checkIDImage(id, 9)));
		
		try {
			socket = new Socket(ip, port);

			if (socket != null) // 정상적으로 소켓이 연결 되었을 경우
			{
				Connection();


			} 	//

		} catch (UnknownHostException e) { // 해당 호스트를 찾을 수 없는 예외 발생

			JOptionPane.showMessageDialog(null, "연결 실패", "알림", JOptionPane.ERROR_MESSAGE); // 서버 연결 실패

		} catch (IOException e) { // 스트림에서 에러가 생긴 예외 발생

			JOptionPane.showMessageDialog(null, "연결 실패", "알림", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void Connection() // 실제적인 메소드 연결부분
	{

		try {
			is = socket.getInputStream();
			dis = new DataInputStream(is); // 클라이언트에서 받아야 할 서버의 데이터를 저장할 공간(객체 생성)

			os = socket.getOutputStream();
			dos = new DataOutputStream(os); // 클라이언트로 보낼 서버의 데이터를 저장할 공간(객체 생성)
		}

		catch (IOException e) // 에러처리부분
		{
			JOptionPane.showMessageDialog(null, "연결 실패", "알림", JOptionPane.ERROR_MESSAGE);

		} // Stream 설정 끝

		// 로그인 완료 되면
		this.setVisible(false); // 채팅창 표시
		this.Login_GUI.setVisible(false); // 로그인창 사라짐
		clip.close(); // 브금 종료
		this.Main_GUI.setVisible(true); // 채팅창 표시
		roomout_btn.setVisible(false);
		save_btn.setVisible(false);
		Chat_Pane.setVisible(false); // 채팅창 안보이게
		send_btn.setVisible(false); // 전송 버튼 안보이게
		Chat_Pane.setEditable(false); // 채팅 내용 편집 못하게
		Papago.setVisible(false);
		Emo.setVisible(false);
		Location.setVisible(false);
		Filebtn.setVisible(false);
		
		try {
			File file = new File("C:\\Users\\36175\\Desktop\\이모티콘\\music\\HappyTown.wav");
			AudioInputStream stream = AudioSystem.getAudioInputStream(file);
			clip2 = AudioSystem.getClip();
			clip2.open(stream);
			clip2.start();
			if (true) clip2.loop(-1);	// 무한 재생
			// clip2.close();

		} catch (Exception f) {

			f.printStackTrace();
		}

		// 처음 접속시에 ID 전송
		send_message(id);

		// User_list(전체 접속자 리스트)에 사용자 추가

		user_list.add(id);

		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {

				while (true) { // 메시지 수신 무한 대기

					try {
						String msg = dis.readUTF(); // 메시지 수신

						System.out.println("서버로부터 수신된 메세지: " + msg);

						in_message(msg);

					} catch (IOException e) { // 서버가 종료될 때

						try {
							os.close();
							is.close();
							dos.close();
							dis.close();
							socket.close();
						} catch (IOException e1) {
						}
						break;
					} // 메세지 수신
				}
			}
		});

		th.start(); // 서버로부터 들어오는 메시지를 받을 수 있음
	}

	public static void send_message(String str) // 서버에게 메세지를 보내는 부분
	{
		try {
			dos.writeUTF(str);
		} catch (IOException e) { // 에러처리부분

		}

	}

	private void in_message(String str) // 서버로부터 들어오는 모든 메시지
	{
		st = new StringTokenizer(str, "/");
		doc = Chat_Pane.getStyledDocument();

		String protocol = st.nextToken();
		String Message = st.nextToken();

		System.out.println("프로토콜 : " + protocol);
		System.out.println("내용 : " + Message);

		if (protocol.equals("NewUser")) // 새로운 접속자
		{
			user_list.add(Message); // 사용자의 ID 추가
		}

		else if (protocol.equals("OldUser")) // 기존 접속자
		{
			user_list.add(Message);
			// room_member_list.add(Message);
		}

		else if (protocol.equals("Note")) // 쪽지 받기
		{

			String note = st.nextToken();

			System.out.println(Message + " 사용자로부터 온 쪽지 : " + note); // Console창에 쪽지 내용 출력

			JOptionPane.showMessageDialog(null, note, Message + "님으로 부터 쪽지", JOptionPane.CLOSED_OPTION); // 널값, 메시지,
																											// OOO님으로부터
																											// 쪽지, 닫기버튼
			// 메시지 알림 창이 따로 뜨면서 메시지 내용 출력!! (Console창에도 출력)
		}

		else if (protocol.equals("Friend")) {
			String note = st.nextToken();

			System.out.println(Message + " 친구신청 : " + note); // Console창에 쪽지 내용 출력

			JOptionPane.showMessageDialog(null, note, Message + "님으로 부터 친구신청", JOptionPane.YES_NO_OPTION); // 널값, 메시지,
																											// OOO님으로부터
																											// 친구신청,
																											// 닫기버튼
			// 메시지 알림 창이 따로 뜨면서 메시지 내용 출력!! (Console창에도 출력)
		}

		else if (protocol.equals("user_list_update")) // 접속자 목록
		{
			// User_list.updateUI();
			User_list.setListData(user_list);
		}

		else if (protocol.equals("CreateRoom")) // 방을 만들었을때(방장)
		{
			My_Room = Message;

			Admin = id;

			Room_list.setVisible(false); // 채팅방목록 비활성화
			Room_label.setVisible(false); // 채팅방목록 라벨 비활성화

//			Room_member.setVisible(true); // 활성화
//			Room_Member_list.setVisible(true); // 채팅참여인원리스트 활성화
			kick.setVisible(true); // 강퇴버튼 비활성화
			invitebtn.setEnabled(true); // 초대버튼 활성화

			room_member_list.add(id);

			message_tf.setEnabled(true);
			send_btn.setEnabled(true);
			joinroom_btn.setVisible(false); // 대화방 참여 버튼 비활성화
			joinroom_btn.setEnabled(false);
			createroom_btn.setVisible(false); // 방 만들기 버튼 비활성화
			roomout_btn.setVisible(true); // 방 나가기 버튼 활성화
			roomout_btn.setEnabled(true); // 방 나가기 버튼 활성화
			save_btn.setVisible(true); // 대화 내용 저장 버튼 활성화
			Chat_Pane.setVisible(true); // 대화 내용 활성화
			Chat_Pane.setText(null); // 채팅 내용 제거
			message_tf.setVisible(true); // 메시지 텍스트 활성화
			send_btn.setVisible(true); // 대화 보내기 버튼 활성화

			Papago.setVisible(true);
			Emo.setVisible(true);
			Location.setVisible(true);
			Filebtn.setVisible(true);
			/*
			 * image1.setVisible(true); image2.setVisible(true); image3.setVisible(true);
			 * image4.setVisible(true); image5.setVisible(true); image6.setVisible(true);
			 * image7.setVisible(true); image8.setVisible(true); image9.setVisible(true);
			 * image10.setVisible(true);
			 */
			JOptionPane.showMessageDialog(null, "채팅방을 개설하셨습니다. 당신은 방장입니다.", "알림", JOptionPane.INFORMATION_MESSAGE);

		}

		else if (protocol.equals("CreateRoomFail")) // 방 만들기 실패했을 경우
		{

			JOptionPane.showMessageDialog(null, "방만들기 실패", "알림", JOptionPane.ERROR_MESSAGE);

		}

		else if (protocol.equals("New_Room")) // 새로운 방이 만들어 질 때
		{
			room_list.add(Message); // 채팅방 목록에 추가
			Room_list.setListData(room_list);
			//joinroom_btn.setVisible(true);

		}

		else if (protocol.equals("OldRoom")) // 사용자가 접속했는데, 원래 있던 방이 있을 때
		{
			room_list.add(Message); // 있는 방 목록 출력
			// room_member_list.add(Message);
		}

		else if (protocol.equals("room_list_update")) {
			Room_list.setListData(room_list);
//			Room_Member_list.setListData(room_member_list);
			// AWT List
		}

		else if (protocol.equals("JoinRoom")) // 대화방에 참여 성공 했을 때
		{
			My_Room = Message;

//			Room_member.setVisible(true); // 채팅방참여인원 활성화
//			Room_Member_list.setVisible(true); // 채팅참여인원리스트 활성화

			Room_list.setVisible(false); // 채팅방목록 비활성화
			Room_label.setVisible(false); // 채팅방목록 라벨 비활성화
			invitebtn.setEnabled(true); // 초대버튼 활성화

			room_member_list.add(id);

			message_tf.setEnabled(true); // 메시지 내용 활성화
			send_btn.setEnabled(true); // 메시지 보내기 버튼 활성화
			joinroom_btn.setVisible(false); // 대화방 참여 버튼 비활성화
			createroom_btn.setVisible(false); // 방 만들기 버튼 비활성화
			roomout_btn.setVisible(true); // 방 나가기 버튼 활성화
			roomout_btn.setEnabled(true); // 방 나가기 버튼 활성화
			save_btn.setVisible(true); // 대화 저장 내용 버튼 활성화
			message_tf.setVisible(true); // 대화 내용 입력 텍스트 활성화
			Chat_Pane.setVisible(true); // 대화 내용 활성화
			Chat_Pane.setText(null); // 채팅 내용 제거
			send_btn.setVisible(true); // 대화 전송 버튼 활성화

			Papago.setVisible(true);
			Emo.setVisible(true);
			Location.setVisible(true);
			Filebtn.setVisible(true);
			/*
			 * image1.setVisible(true); image2.setVisible(true); image3.setVisible(true);
			 * image4.setVisible(true); image5.setVisible(true); image6.setVisible(true);
			 * image7.setVisible(true); image8.setVisible(true); image9.setVisible(true);
			 * image10.setVisible(true);
			 */

			JOptionPane.showMessageDialog(null, "채팅방에 입장했습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
		}

		else if (protocol.equals("User_out")) // 사용자가 나갔을 때
		{
			user_list.remove(Message); // 사용자를 목록에서 삭제 시킨다.
			// room_member_list.remove(id); // id 삭제

		}

		else if (protocol.equals("OutRoom")) {
			My_Room = Message;

			Room_list.setVisible(true); // 채팅방목록 활성화
			Room_label.setVisible(true); // 채팅방목록 라벨 활성화

//			Room_member.setVisible(false); // 방목록리스트 비활성화
//			Room_Member_list.setVisible(false); // 채팅참여인원리스트 비활성화
			room_member_list.remove(id); // id 삭제
			kick.setVisible(false); // 강퇴버튼 비활성화
			invitebtn.setEnabled(false); // 초대버튼 비활성화

			message_tf.setEnabled(false); // 전송 버튼 비활성화
			joinroom_btn.setVisible(true); // 채팅방 참여 버튼 활성화
			createroom_btn.setVisible(true); // 채팅방 만들기 버튼 활성화
			roomout_btn.setVisible(false); // 채팅방 나가기 버튼 비활성화
			save_btn.setVisible(false);
			message_tf.setVisible(false); // 대화 내용 입력 텍스트 활성화
			Chat_Pane.setVisible(false);
			Chat_Pane.setText(null); // 채팅 내용 제거

			Papago.setVisible(false);
			Emo.setVisible(false);
			Location.setVisible(false);
			Filebtn.setVisible(false);

		}

		else if (protocol.equals("Chatting")) // 채팅을 할 경우
		{

			GregorianCalendar calendar = new GregorianCalendar();
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH) + 1;
			int amPm = calendar.get(Calendar.AM_PM);
			int date = calendar.get(Calendar.DATE);
			int hour = calendar.get(Calendar.HOUR);
			int min = calendar.get(Calendar.MINUTE);
			int sec = calendar.get(Calendar.SECOND);
			String sAmPm = amPm == Calendar.AM ? "오전" : "오후";

			Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
			Style s;
			s = doc.addStyle("red", def);
			StyleConstants.setForeground(s, Color.RED);
			s = doc.addStyle("blue", def);
			StyleConstants.setForeground(s, Color.BLUE);
			s = doc.addStyle("black", def);
			StyleConstants.setForeground(s, Color.BLACK);
			s = doc.addStyle("green", def);
			StyleConstants.setForeground(s, Color.GREEN);
			s = doc.addStyle("yellow", def);
			StyleConstants.setForeground(s, Color.YELLOW);
			s = doc.addStyle("orange", def);
			StyleConstants.setForeground(s, Color.ORANGE);
			s = doc.addStyle("right", def);
			StyleConstants.setAlignment(s, StyleConstants.ALIGN_RIGHT);
			s = doc.addStyle("left", def);
			StyleConstants.setAlignment(s, StyleConstants.ALIGN_LEFT);
			s = doc.addStyle("center", def);
			StyleConstants.setAlignment(s, StyleConstants.ALIGN_CENTER);

			try {
				doc.insertString(doc.getLength(), "\n", null);

			} catch (BadLocationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try {

				String msg = st.nextToken();
				System.out.println(Message);
				System.out.println(id);
				msg = msg.trim(); // msg trim 대입

				if (Message.equals(id)) { // 내 입장
					Style profile = doc.addStyle("프로필사진1", null);
					StyleConstants.setIcon(profile, ResizeImage(checkIDImage(id, 9), 40, 40));
					doc.insertString(doc.getLength(), "ignored text", profile);

					doc.insertString(doc.getLength(), "[" + /* year + "년 " + month + "월 " + date + "일 " + */
							sAmPm + " " + hour + "시 " + min + "분 " + "] ", doc.getStyle("time"));
					doc.insertString(doc.getLength(), " " + Message + " ", doc.getStyle("blue"));// 계정
					doc.insertString(doc.getLength(), ": ", doc.getStyle("black")); // : 색깔
					doc.setLogicalStyle(doc.getLength(), doc.getStyle("right"));

					if (msg.equals(map_path + id + ".jpg")) { // 구글 지도 전송
						Style style = doc.addStyle("ㅡㅡ", null);
						StyleConstants.setIcon(style, ResizeImage(map_path + id + ".jpg", 300, 300));
						doc.insertString(doc.getLength(), "ignored text", style);
						doc.insertString(doc.getLength(), "\n", null);
						Chat_Pane.setCaretPosition(doc.getLength());
					}
					
					else if (msg.equals("/누구 "+Message)) {		// 상대 정보 보기
						Style style = doc.addStyle("ㅡㅡ", null);
						
						Profile frame = new Profile();
						frame.setVisible(true);
						
						doc.insertString(doc.getLength(), "ignored text", style);
						doc.insertString(doc.getLength(), "\n", null);
						Chat_Pane.setCaretPosition(doc.getLength());
					}

					else if (msg.equals(imgsend)) {
						Style style = doc.addStyle("ㅡㅡ", null);
						StyleConstants.setIcon(style, ResizeImage(imgsend, 100, 100));
						doc.insertString(doc.getLength(), "ignored text", style);
						doc.insertString(doc.getLength(), "\n", null);
						Chat_Pane.setCaretPosition(doc.getLength());
					}

					else if (msg.equals("C:\\Users\\36175\\Desktop\\이모티콘\\푸\\1.gif") || msg.equals("ㅡㅡ")) {

						Style style = doc.addStyle("ㅡㅡ", null);
						StyleConstants.setIcon(style, new ImageIcon("C:\\Users\\36175\\Desktop\\이모티콘\\푸\\1.gif"));
						doc.insertString(doc.getLength(), "ignored text", style);
						doc.insertString(doc.getLength(), "\n", null);
						Chat_Pane.setCaretPosition(doc.getLength());

					}

					else if (msg.equals("C:\\Users\\36175\\Desktop\\이모티콘\\푸\\2.gif") || msg.equals("빡침")) {
						Style style = doc.addStyle("ㅎㅇㅌ", null);
						StyleConstants.setIcon(style, new ImageIcon("C:\\Users\\36175\\Desktop\\이모티콘\\푸\\2.gif"));
						doc.insertString(doc.getLength(), "ignored text", style);
						doc.insertString(doc.getLength(), "\n", null);
						Chat_Pane.setCaretPosition(doc.getLength());

					}

					else if (msg.equals("C:\\Users\\36175\\Desktop\\이모티콘\\푸\\3.gif")) {
						Style style = doc.addStyle("헉", null);
						StyleConstants.setIcon(style, new ImageIcon("C:\\Users\\36175\\Desktop\\이모티콘\\푸\\3.gif"));
						doc.insertString(doc.getLength(), "ignored text", style);
						doc.insertString(doc.getLength(), "\n", null);
						Chat_Pane.setCaretPosition(doc.getLength());

					}

					else if (msg.equals("C:\\Users\\36175\\Desktop\\이모티콘\\푸\\4.gif")) {
						Style style = doc.addStyle("사랑해", null);
						StyleConstants.setIcon(style, new ImageIcon("C:\\Users\\36175\\Desktop\\이모티콘\\푸\\4.gif"));
						doc.insertString(doc.getLength(), "ignored text", style);
						doc.insertString(doc.getLength(), "\n", null);
						Chat_Pane.setCaretPosition(doc.getLength());

					}

					else if (msg.equals("C:\\Users\\36175\\Desktop\\이모티콘\\푸\\5.gif")) {
						Style style = doc.addStyle("ㅅㄱ", null);
						StyleConstants.setIcon(style, new ImageIcon("C:\\Users\\36175\\Desktop\\이모티콘\\푸\\5.gif"));
						doc.insertString(doc.getLength(), "ignored text", style);
						doc.insertString(doc.getLength(), "\n", null);
						Chat_Pane.setCaretPosition(doc.getLength());

					}

					else if (msg.equals("C:\\Users\\36175\\Desktop\\이모티콘\\푸\\6.gif")) {
						Style style = doc.addStyle("ㅠㅠ", null);
						StyleConstants.setIcon(style, new ImageIcon("C:\\Users\\36175\\Desktop\\이모티콘\\푸\\6.gif"));
						doc.insertString(doc.getLength(), "ignored text", style);
						doc.insertString(doc.getLength(), "\n", null);
						Chat_Pane.setCaretPosition(doc.getLength());

					}

					else if (msg.equals("C:\\Users\\36175\\Desktop\\이모티콘\\푸\\7.gif")) {
						Style style = doc.addStyle("야호", null);
						StyleConstants.setIcon(style, new ImageIcon("C:\\Users\\36175\\Desktop\\이모티콘\\푸\\7.gif"));
						doc.insertString(doc.getLength(), "ignored text", style);
						doc.insertString(doc.getLength(), "\n", null);
						Chat_Pane.setCaretPosition(doc.getLength());

					}

					else if (msg.equals("C:\\Users\\36175\\Desktop\\이모티콘\\푸\\8.gif")) {
						Style style = doc.addStyle("여유", null);
						StyleConstants.setIcon(style, new ImageIcon("C:\\Users\\36175\\Desktop\\이모티콘\\푸\\8.gif"));
						doc.insertString(doc.getLength(), "ignored text", style);
						doc.insertString(doc.getLength(), "\n", null);
						Chat_Pane.setCaretPosition(doc.getLength());

					}

					else if (msg.equals("C:\\Users\\36175\\Desktop\\이모티콘\\푸\\9.gif") || msg.equals("ㅈㅅ")) {
						Style style = doc.addStyle("ㅈㅅ", null);
						StyleConstants.setIcon(style, new ImageIcon("C:\\Users\\36175\\Desktop\\이모티콘\\푸\\9.gif"));
						doc.insertString(doc.getLength(), "ignored text", style);
						doc.insertString(doc.getLength(), "\n", null);
						Chat_Pane.setCaretPosition(doc.getLength());

					}

					else if (msg.equals("C:\\행복해.png") || msg.equals("행복해")) {
						Style style = doc.addStyle("행복해", null);
						StyleConstants.setIcon(style, new ImageIcon("C:\\행복해.png"));
						doc.insertString(doc.getLength(), "ignored text", style);
						doc.insertString(doc.getLength(), "\n", null);
						Chat_Pane.setCaretPosition(doc.getLength());

					}

					else { // 본인 채팅 글씨
						doc.insertString(doc.getLength(), msg, doc.getStyle("blue"));

					}

					Chat_Pane.setCaretPosition(doc.getLength());

				}

				else if (Message.equals("알림")) {

					doc.setLogicalStyle(doc.getLength(), doc.getStyle("center")); // 글자 가운데 정렬
					doc.insertString(doc.getLength(), Message + " : " + msg + "\n", doc.getStyle("black")); // 글자 검정색
					Chat_Pane.setCaretPosition(doc.getLength());

					try {
						File file = new File("C:\\Users\\36175\\Desktop\\이모티콘\\띵동.wav");
						AudioInputStream stream = AudioSystem.getAudioInputStream(file);
						Clip clip = AudioSystem.getClip();
						clip.open(stream);
						clip.start();

					} catch (Exception f) {

						f.printStackTrace();
					}

				}

				else { // 상대 입장

					Style profile = doc.addStyle("프로필사진2", null);
					StyleConstants.setIcon(profile, ResizeImage(checkIDImage(Message, 9), 40, 40));
					doc.insertString(doc.getLength(), "ignored text", profile);

					doc.insertString(doc.getLength(), "[" + /* year + "년 " + month + "월 " + date + "일 " + */
							sAmPm + " " + hour + "시 " + min + "분 " + "] ", doc.getStyle("time"));
					doc.insertString(doc.getLength(), " " + Message + " ", doc.getStyle("yellow"));// 상대 계정
					doc.insertString(doc.getLength(), ": ", doc.getStyle("black")); // : 색깔
					doc.setLogicalStyle(doc.getLength(), doc.getStyle("left"));

					if (msg.equals(map_path + Message + ".jpg")) { // 구글 지도 전송
						Style style = doc.addStyle("ㅡㅡ", null);
						StyleConstants.setIcon(style, ResizeImage(map_path + Message + ".jpg", 300, 300));
						doc.insertString(doc.getLength(), "ignored text", style);
						doc.insertString(doc.getLength(), "\n", null);
						Chat_Pane.setCaretPosition(doc.getLength());
					}

					else if (msg.equals(imgsend)) {
						Style style = doc.addStyle("ㅡㅡ", null);
						StyleConstants.setIcon(style, ResizeImage(imgsend, 100, 100));
						doc.insertString(doc.getLength(), "ignored text", style);
						doc.insertString(doc.getLength(), "\n", null);
						Chat_Pane.setCaretPosition(doc.getLength());
					}

					else if (msg.equals("C:\\Users\\36175\\Desktop\\이모티콘\\푸\\1.gif") || msg.equals("ㅡㅡ")) {
						Style style = doc.addStyle("ㅡㅡ", null);
						StyleConstants.setIcon(style, new ImageIcon("C:\\Users\\36175\\Desktop\\이모티콘\\푸\\1.gif"));
						doc.insertString(doc.getLength(), "ignored text", style);
						doc.insertString(doc.getLength(), "\n", null);
						Chat_Pane.setCaretPosition(doc.getLength());
					}

					else if (msg.equals("C:\\Users\\36175\\Desktop\\이모티콘\\푸\\2.gif") || msg.equals("ㅎㅇㅌ")) {
						Style style = doc.addStyle("ㅎㅇㅌ", null);
						StyleConstants.setIcon(style, new ImageIcon("C:\\Users\\36175\\Desktop\\이모티콘\\푸\\2.gif"));
						doc.insertString(doc.getLength(), "ignored text", style);
						doc.insertString(doc.getLength(), "\n", null);
						Chat_Pane.setCaretPosition(doc.getLength());
					}

					else if (msg.equals("C:\\Users\\36175\\Desktop\\이모티콘\\푸\\3.gif") || msg.equals("놀람")) {
						Style style = doc.addStyle("놀람", null);
						StyleConstants.setIcon(style, new ImageIcon("C:\\Users\\36175\\Desktop\\이모티콘\\푸\\3.gif"));
						doc.insertString(doc.getLength(), "ignored text", style);
						doc.insertString(doc.getLength(), "\n", null);
						Chat_Pane.setCaretPosition(doc.getLength());
					}

					else if (msg.equals("C:\\Users\\36175\\Desktop\\이모티콘\\푸\\4.gif") || msg.equals("사랑해")) {
						Style style = doc.addStyle("사랑해", null);
						StyleConstants.setIcon(style, new ImageIcon("C:\\Users\\36175\\Desktop\\이모티콘\\푸\\4.gif"));
						doc.insertString(doc.getLength(), "ignored text", style);
						doc.insertString(doc.getLength(), "\n", null);
						Chat_Pane.setCaretPosition(doc.getLength());
					}

					else if (msg.equals("C:\\Users\\36175\\Desktop\\이모티콘\\푸\\5.gif") || msg.equals("ㅅㄱ")) {
						Style style = doc.addStyle("ㅅㄱ", null);
						StyleConstants.setIcon(style, new ImageIcon("C:\\Users\\36175\\Desktop\\이모티콘\\푸\\5.gif"));
						doc.insertString(doc.getLength(), "ignored text", style);
						doc.insertString(doc.getLength(), "\n", null);
						Chat_Pane.setCaretPosition(doc.getLength());
					}

					else if (msg.equals("C:\\Users\\36175\\Desktop\\이모티콘\\푸\\6.gif") || msg.equals("ㅠㅠ")) {
						Style style = doc.addStyle("ㅠㅠ", null);
						StyleConstants.setIcon(style, new ImageIcon("C:\\Users\\36175\\Desktop\\이모티콘\\푸\\6.gif"));
						doc.insertString(doc.getLength(), "ignored text", style);
						doc.insertString(doc.getLength(), "\n", null);
						Chat_Pane.setCaretPosition(doc.getLength());
					}

					else if (msg.equals("C:\\Users\\36175\\Desktop\\이모티콘\\푸\\7.gif") || msg.equals("신나")) {
						Style style = doc.addStyle("야호", null);
						StyleConstants.setIcon(style, new ImageIcon("C:\\Users\\36175\\Desktop\\이모티콘\\푸\\7.gif"));
						doc.insertString(doc.getLength(), "ignored text", style);
						doc.insertString(doc.getLength(), "\n", null);
						Chat_Pane.setCaretPosition(doc.getLength());
					}

					else if (msg.equals("C:\\Users\\36175\\Desktop\\이모티콘\\푸\\8.gif") || msg.equals("여유")) {
						Style style = doc.addStyle("여유", null);
						StyleConstants.setIcon(style, new ImageIcon("C:\\Users\\36175\\Desktop\\이모티콘\\푸\\8.gif"));
						doc.insertString(doc.getLength(), "ignored text", style);
						doc.insertString(doc.getLength(), "\n", null);
						Chat_Pane.setCaretPosition(doc.getLength());
					}

					else if (msg.equals("C:\\Users\\36175\\Desktop\\이모티콘\\푸\\9.gif") || msg.equals("ㅈㅅ")) {
						Style style = doc.addStyle("ㅈㅅ", null);
						StyleConstants.setIcon(style, new ImageIcon("C:\\Users\\36175\\Desktop\\이모티콘\\푸\\9.gif"));
						doc.insertString(doc.getLength(), "ignored text", style);
						doc.insertString(doc.getLength(), "\n", null);
						Chat_Pane.setCaretPosition(doc.getLength());
					}

					else if (msg.equals("C:\\Users\\36175\\Desktop\\이모티콘\\피카츄.png") || msg.equals("행복해")) {
						Style style = doc.addStyle("행복해", null);
						StyleConstants.setIcon(style, new ImageIcon("C:\\Users\\36175\\Desktop\\이모티콘\\피카츄.png"));
						doc.insertString(doc.getLength(), "ignored text", style);
						doc.insertString(doc.getLength(), "\n", null);
						Chat_Pane.setCaretPosition(doc.getLength());
					}

					else {
						doc.insertString(doc.getLength(), msg, doc.getStyle("orange"));
					}

					Chat_Pane.setCaretPosition(doc.getLength());

				}

			} catch (Exception e) {
			}
		}
	}

	public static void main(String[] args) {

		new Client(); // Client를 만들기 위해 필요한 생성자

		String str = "";
		for (int i = 0; i < 19; i++)
			str += i + "행\n";
		JTextPane Chat_Pane = new JTextPane();
		JScrollPane scrollpane = new JScrollPane(Chat_Pane);
		JScrollBar scrollbar = scrollpane.getVerticalScrollBar();

		scrollbar.setValue(scrollbar.getMaximum());

	}

	String ProfilePath;
	private final JButton Music_stop = new JButton("\u266C\uC74C\uC545\uC885\uB8CC\u266C");
	private final JButton Papago = new JButton("파파고");
	private final JButton button = new JButton("\uC804\uCCB4\uBAA9\uB85D");
	private final JButton button_1 = new JButton("\uCE5C\uAD6C\uBAA9\uB85D");
	private final JLabel lblNewLabel_2 = new JLabel("<\uB0B4 \uC815\uBCF4>");
	private int cot = 0;	// 배경 사진 변경 카운트
	private static int cot2 = 1;	// 음악 변경 카운트
	private final JButton Music_play = new JButton("\u266C\uC74C\uC545\uC7AC\uC0DD\u266C");
	private final JButton protect = new JButton("\uD654\uBA74 \uBCF4\uD638");
	private final JButton Music_Next = new JButton("\u266C\uB2E4\uC74C\uC74C\uC545\u266C");
	private final JButton Music_Back = new JButton("\u266C\uC774\uC804\uC74C\uC545\u266C");
	private final static JLabel Music_title = new JLabel("HappyTown");
	private final JLabel label = new JLabel("\uC74C\uC545 : ");

	@Override
	public void actionPerformed(ActionEvent e) { // 메소드를 재정의
		// login_btn = 로그인버튼
		if (e.getSource() == login_btn) {
			System.out.println("로그인 버튼 클릭");

			if (ip_tf.getText().length() == 0) // ip입력란에 아무 내용 입력 안 하고 로그인 시도할 때
			{
				ip_tf.setText("IP를 입력해주세요");
				ip_tf.requestFocus();

			}

			if (port_tf.getText().length() == 0) // port입력란에 아무 내용 입력 안 하고 로그인 시도할 때
			{
				port_tf.setText("Port번호를 입력해주세요");
				port_tf.requestFocus();
			}

			if (id_tf.getText().length() == 0) {
				id_tf.setText("ID를 입력해주세요");
				id_tf.requestFocus();
			}
			if (pw_tf.getText().length() == 0) {
				pw_tf.setText("비밀번호를 입력해주세요");
				pw_tf.requestFocus();
			}

			else {
				ip = ip_tf.getText().trim(); // 원래 ip주소를 로그인시 불러올 수 있음 (공백인 상태도 생각하여 trim()을 붙임)
				port = Integer.parseInt(port_tf.getText().trim()); // 받아올 때 String형이기 때문에, 포트를 int형으로 강제 변환 시킨다.
				id = id_tf.getText().trim(); // id를 받아오는 부분
				pwd = pw_tf.getText().trim(); // password를 받아오는 부분
				// MemberProc mem = new MemberProc();

				if (id.equals("root") && pwd.equals("7508")) { // 관리자모드 로그인
					Manager manager = new Manager("7508");
				}

				else if (DB_Check(id, pwd)) {
					Network(); // 로그인 버튼을 누르면 서버에 접속하게 하기 위함
				}

			}
		}

		else if (e.getSource() == friend_add) {
			System.out.println("친구 추가 버튼 클릭");
			String user = (String) User_list.getSelectedValue();
			if (user == null) {
				JOptionPane.showMessageDialog(null, "친구할 상대를 선택해주세요!", "경고", JOptionPane.INFORMATION_MESSAGE);
			} else {
				String note = JOptionPane.showInputDialog("보낼메세지"); // 메시지 창 띄우기
				if (note != null) {
					// ex = Note/User2/나는 User1이야
				}
				System.out.println("받는 사람 : " + user + " | 보낼 내용 : " + note);
				// 받는 사람의 메시지 내용 출력
			}
		}

		else if (e.getSource() == invitebtn) { // 초대 버튼 누를 시
			System.out.println("초대하기 버튼 클릭");
			String Invite = (String) User_list.getSelectedValue();

			if (Invite == null) {
				JOptionPane.showMessageDialog(null, "초대할 상대를 선택해주세요!", "경고", JOptionPane.INFORMATION_MESSAGE);
			} else {
				String note = JOptionPane.showInputDialog(null, "님이 초대하셨습니다", "초대메세지", JOptionPane.YES_NO_OPTION); // 메시지
																													// 창
																													// 띄우기
				if (note != null) {
					if (note == "1") {
						System.out.println("초대 수락");
					}
				}
				// 받는 사람의 메시지 내용 출력
			}
		}

		else if (e.getSource() == notesend_btn) {
			System.out.println("쪽지 보내기 버튼 클릭");
			String user = (String) User_list.getSelectedValue();// 쪽지 보낼 사람 리스트에서 선택

			if (user == null) {
				JOptionPane.showMessageDialog(null, "쪽지 보낼 상대를 선택해주세요!", "경고", JOptionPane.INFORMATION_MESSAGE);
			} else {
				String note = JOptionPane.showInputDialog("보낼메세지"); // 메시지 창 띄우기
				if (note != null) {
					send_message("Note/" + user + "/" + note);

					// ex = Note/User2/나는 User1이야
				}
				System.out.println("받는 사람 : " + user + " | 보낼 내용 : " + note);
				// 받는 사람의 메시지 내용 출력
			}
		}

		else if (e.getSource() == kick) { // 강퇴 버튼 누를 시
			System.out.println("강퇴 버튼 클릭");
//			String Ben = (String) Room_Member_list.getSelectedValue();

		}

		else if (e.getSource() == joinroom_btn) // 대화방 참여하기
		{

			String JoinRoom = (String) Room_list.getSelectedValue();

			send_message("JoinRoom/" + JoinRoom);
			save_btn.setVisible(true);
			/*
			 * image1.setVisible(true); image2.setVisible(true); image3.setVisible(true);
			 * image4.setVisible(true); image5.setVisible(true); image6.setVisible(true);
			 * image7.setVisible(true); image8.setVisible(true); image9.setVisible(true);
			 * image10.setVisible(true);
			 */

			System.out.println("방 참여 버튼 클릭");
		}

		else if (e.getSource() == createroom_btn) // 방 만들기
		{
			String roomname = JOptionPane.showInputDialog("방 이름");
			if (roomname != null) {
				send_message("CreateRoom/" + roomname);
			}
			System.out.println("방 만들기 버튼 클릭");
			/*
			 * image1.setVisible(true); image2.setVisible(true); image3.setVisible(true);
			 * image4.setVisible(true); image5.setVisible(true); image6.setVisible(true);
			 * image7.setVisible(true); image8.setVisible(true); image9.setVisible(true);
			 * image10.setVisible(true);
			 */
		}

		else if (e.getSource() == send_btn) // 전송 버튼
		{
			send_message("Chatting/" + My_Room + "/" + message_tf.getText().trim());
			message_tf.setText(""); // 대화방에서 대화메시지 보내고나면 채팅내용 입력창에서 내용이 사라진다.
			message_tf.requestFocus();

			// Chatting + 방이름 + 내용

			System.out.println("채팅 전송 버튼 클릭");
		}

		else if (e.getSource() == logout_btn) // 로그아웃 버튼
		{
			System.out.println("로그아웃 버튼 클릭");
			String OutRoom = (String) Room_list.getSelectedValue();
			send_message("OutRoom/" + OutRoom);
			clip2.close();
			// String OutRoom = (String)Room_list.getSelectedValue();
			// send_message("OutRoom/" + OutRoom);
			this.setVisible(false);
			this.Main_GUI.setVisible(false); // 채팅창 꺼짐
			Login_init(); // 클라이언트 채팅창 꺼지고 로그인창 실행
			try {
				os.close();
				is.close();
				dos.close();
				dis.close();
				socket.close();
				user_list.remove(Message); // 사용자를 목록에서 삭제 시킨다.
				room_list.remove(Message); // 방을 목록에서 삭제 시킨다.
			} catch (IOException e1) {
			}
			JOptionPane.showMessageDialog(null, "로그아웃을 완료하셨습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
		}

		else if (e.getSource() == adduser_btn) { // 회원가입
			System.out.println("회원가입 버튼 클릭");

		}

		else if (e.getSource() == save_btn) { // 채팅 저장
			System.out.println("채팅 저장 버튼 클릭");
			FileInputStream fis = null;
			Connection conn = null; // 데이터 연결하기 위함
			PreparedStatement pstmt = null; // 데이터를 읽어오기 위함

			FileDialog dlg = new FileDialog(this, "프로필 사진 지정하기", FileDialog.LOAD);
			dlg.show();
			dlg.setVisible(true);
			String dir = dlg.getDirectory();
			String file = dlg.getFile();

			if (file == null)
				return;

			System.out.println("dir:" + dir);
			System.out.println("file:" + file);

			MemberProc.tfprofilePath.setText(dir + file);

		}
		
		else if (e.getSource() == Profilebtn) {
			Profile frame = new Profile();
			frame.setVisible(true);
		}
		else if (e.getSource() == Music_stop) {	//정지 버튼
			Music_play.setVisible(true);
			Music_stop.setVisible(false);
			clip2.close();
			
		}
		else if (e.getSource() == Music_play) {	//재생 버튼
			Music_stop.setVisible(true);
			Music_play.setVisible(false);
			MusicChange(cot2);
		}
		else if (e.getSource() == Music_Next) {	// 음악 다음 버튼
			Music_play.setVisible(false);
			Music_stop.setVisible(true);
			cot2++;
			if(cot2>4) {
				cot2=1;
			}
			MusicChange(cot2);
			
		}
		else if (e.getSource() == Music_Back) {	// 음악 이전 버튼
			Music_play.setVisible(false);
			Music_stop.setVisible(true);
			cot2--;
			if (cot2==0) {	// cot2가 0이면 4로 바꿈
				cot2=4;
			}
			MusicChange(cot2);
		}
		
		else if (e.getSource() == ChangeBG) {		// 배경 변경 버튼
			cot++;
			if (cot == 1) {
				Image1.setVisible(false);
				Image2.setVisible(true);
			}
			else if (cot ==2 ) {
				Image2.setVisible(false);
				Image3.setVisible(true);
			}
			else if (cot ==3) {
				Image3.setVisible(false);
				Image1.setVisible(true);
				cot = 0;
			}
		}
		else if (e.getSource() == protect) {
			Image4.setVisible(true);
		}

		else if (e.getSource() == roomout_btn) // 방 나가기
		{

			String OutRoom = (String) Room_list.getSelectedValue();
			send_message("OutRoom/" + OutRoom);

			// 채팅방 만들기 버튼 활성화
			createroom_btn.setEnabled(true);
			createroom_btn.setVisible(true);

			// 채팅방 참여 버튼 활성화
			joinroom_btn.setEnabled(true);
			joinroom_btn.setVisible(true);

			// 채팅방 나가기 버튼 사라지게 만들기
			roomout_btn.setVisible(false);

			// 강퇴 버튼 비활성화
			kick.setVisible(false);
			invitebtn.setEnabled(false); // 초대버튼 비활성화

			// 채팅방 목록 활성화
			Room_list.setVisible(true); // 채팅방목록 활성화
			Room_label.setVisible(true); // 채팅방목록 라벨 활성화

			// 전송 버튼 비활성화
			send_btn.setVisible(false);

			// 채팅 저장 버튼 비활성화
			save_btn.setVisible(false);

			Chat_Pane.setText(null); // 채팅 내용 제거

			if (id == Admin) { // 방장이 나가면
				// JOptionPane.showMessageDialog(null, "방 터짐", "알림",
				// JOptionPane.INFORMATION_MESSAGE);

			}

			roomout_btn.setVisible(false); // 채팅방 나가기 버튼 비활성화
			save_btn.setVisible(false);
			message_tf.setVisible(false); // 대화 내용 입력 텍스트 활성화
			Chat_Pane.setVisible(false);

			Papago.setVisible(false);
			Emo.setVisible(false);
			Location.setVisible(false);
			Filebtn.setVisible(false);

			JOptionPane.showMessageDialog(null, "채팅방에서 퇴장하셨습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);

			if (Server.user_vc.size() + 1 == 0) { // 방에 있는 사람이 없으면
				room_list.remove(Message); // 방을 목록에서 삭제 시킨다.
			}

		}

		else if (e.getSource() == Location) {
			System.out.println("위치 버튼");
			new test.Main();
		}
//	else if (e.getSource() == Location2) {
//		System.out.println("위치2 버튼");
//		//new GoogleMap.Main();
//	}
		else if (e.getSource() == Papago) {
			System.out.println("번역기 버튼");
			Papago frame = new Papago();
			frame.setVisible(true);
		} else if (e.getSource() == Filebtn) {
			System.out.println("파일 전송 버튼");
			String Image_path;
			FileDialog fdlg = new FileDialog(new JFrame(), "file open", FileDialog.LOAD); // 파일다이얼로그를 사용하여 파일을 열기

			fdlg.setVisible(true); // 보여주기 활성화

			if (fdlg.getFile() == null) { // 파일이 null이면
				return;
			} else {
				ImageIcon originalIcon = new ImageIcon(fdlg.getDirectory() + fdlg.getFile());
				Image originalImage = originalIcon.getImage();
				Image resizeImage = originalImage.getScaledInstance(153, 148, Image.SCALE_SMOOTH); // 이미지 사이즈조절
				ImageIcon resizeIcon = new ImageIcon(resizeImage);
				Image_path = fdlg.getDirectory() + fdlg.getFile();
				imgsend = Image_path; // 사진을 텍스트필드로 받아와서 라벨에 저장
				send_message("Chatting/" + My_Room + "/" + imgsend); // 이미지 전송
			}
		}
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

	public static ImageIcon ResizeImage(String ImagePath, int a, int b)// 크기를 직접 입력해 줄때
	{
		ImageIcon MyImage = new ImageIcon(ImagePath);
		Image img = MyImage.getImage();
		Image newImg = img.getScaledInstance(a, b, Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(newImg);
		return image;
	}
	public static void MusicChange(int a) {		// 음악넘기기 함수
		clip2.close(); 	// 음악 종료
		if(a == 1) {
			System.out.println("1번트랙");
			Music_title.setText("행복한 마을");
			try {
				File file = new File("C:\\Users\\36175\\Desktop\\이모티콘\\music\\HappyTown.wav"); // 1번 트랙
				AudioInputStream stream = AudioSystem.getAudioInputStream(file);
				clip2 = AudioSystem.getClip();
				clip2.open(stream);
				clip2.start();
				if (true) clip2.loop(-1);	// 무한 재생
			} catch (Exception f) {

				f.printStackTrace();
				}
		}
		else if (a== 2) {
			System.out.println("2번트랙");
			Music_title.setText("코크타운");
			try {
				File file = new File("C:\\Users\\36175\\Desktop\\이모티콘\\music\\코크타운.wav"); // 2번 트랙
				AudioInputStream stream = AudioSystem.getAudioInputStream(file);
				clip2 = AudioSystem.getClip();
				clip2.open(stream);
				clip2.start();
				if (true) clip2.loop(-1);	// 무한 재생

			} catch (Exception f) {

				f.printStackTrace();
				}
		}
		else if (a == 3) {
			System.out.println("3번트랙");
			Music_title.setText("오르비스 가는길");
			try {
				File file = new File("C:\\Users\\36175\\Desktop\\이모티콘\\music\\엘리니아_오르비스.wav"); // 3번 트랙
				AudioInputStream stream = AudioSystem.getAudioInputStream(file);
				clip2 = AudioSystem.getClip();
				clip2.open(stream);
				clip2.start();
				if (true) clip2.loop(-1);	// 무한 재생

			} catch (Exception f) {

				f.printStackTrace();
				}
		}
		else if (a == 4) {
			System.out.println("4번트랙");
			Music_title.setText("헤네시스");
			try {
				File file = new File("C:\\Users\\36175\\Desktop\\이모티콘\\music\\헤네시스.wav"); // 4번 트랙
				AudioInputStream stream = AudioSystem.getAudioInputStream(file);
				clip2 = AudioSystem.getClip();
				clip2.open(stream);
				clip2.start();
				if (true) clip2.loop(-1);	// 무한 재생
				
			} catch (Exception f) {

				f.printStackTrace();
				}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

		if (e.getKeyCode() == 10) // Enter Key의 KeyCode는 10번이다.
		{
			send_message("Chatting/" + My_Room + "/" + message_tf.getText().trim());
			message_tf.setText(""); // 대화방에서 대화메시지 보내고나면 채팅내용 입력창에서 내용이 사라진다.
			message_tf.requestFocus();
			try {
			File file = new File("C:\\Users\\36175\\Desktop\\이모티콘\\typewriter-key-1.wav"); // 타자치는 소리
			AudioInputStream stream = AudioSystem.getAudioInputStream(file);
			Clip clip = AudioSystem.getClip();
			clip.open(stream);
			clip.start();

		} catch (Exception f) {

			f.printStackTrace();
			}
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) { // Enter Key를 눌러도 채팅 가능하게 함

	
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
