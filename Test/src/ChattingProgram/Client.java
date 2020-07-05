// ������ ����� Client(�α��� â + ä��â)

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
// �߻� Ŭ������ �����Ǿ������� �����Ǹ���
// ctrl + shift + o (�ڵ����� 3��° �ٰ� ���� import)

// Login GUI ����
	private JFrame Login_GUI = new JFrame(); // Login_GUI�� ���� ��ü�� ����
	private JPanel Login_Pane;
	private JTextField ip_tf; // ip �޴� �ؽ�Ʈ�ʵ�
	private JTextField port_tf; // port �޴� �ؽ�Ʈ �ʵ�
	private JTextField id_tf; // id �޴� �ؽ�Ʈ�ʵ�
	private JPasswordField pw_tf; // passwd �޴� �ؽ�Ʈ�ʵ�
	private JButton login_btn = new JButton("�α���"); // �α��ι�ư (������Ҹ� �˾ƺ��� ���� ���������� ����)
	private JButton adduser_btn = new JButton("�����ϱ�"); // ȸ�����Թ�ư
	private JButton search_pwdbtn = new JButton("ID ��й�ȣ ã��"); // ��й�ȣ ã�� ��ư

	public static ImageIcon icon;

// Main GUI ����
	private JFrame Main_GUI = new JFrame(); // Main_GUI�� ���� ��ü�� ����
	private JPanel Main_Pane;
	private JTextField message_tf;
	private JButton notesend_btn = new JButton("����������");
	private JButton joinroom_btn = new JButton("ä�ù�����");
	private JButton createroom_btn = new JButton("\uBC29 \uB9CC\uB4E4\uAE30");
	private JButton send_btn = new JButton("����");
	private JButton logout_btn = new JButton("\uC811\uC18D \uC885\uB8CC");
	private JButton save_btn = new JButton("ä�� ����");
	private JButton roomout_btn = new JButton("�� ������");
	private JButton kick = new JButton("����");
	private JButton invitebtn = new JButton("�ʴ��ϱ�");
	private JButton friend_del;
	private JButton friend_add;
	private JButton Emo;

	public static StyledDocument doc;

	private JList User_list = new JList(); // ��ü ������ List
	private JList Room_list = new JList(); // ��ü ���� List
	private JList friend_list; // ģ����� List

	private JTextPane Chat_Pane = new JTextPane(); // ä��â ����
	private JScrollPane scrollPane = new JScrollPane();

// ��Ʈ��ũ�� ���� �ڿ� ����  (�α��� â���� �Է��� �͵�)
	private Socket socket; // ��� ��Ȳ������ ������ �غ񰡴�
	private String ip = ""; // �ڱ� �ڽ��� ip�ּ�
	private int port; // �ڱ� �ڽ��� port��ȣ
	public static String id = "";
	private String pwd = "";
	private String chatname = "";
	private InputStream is; // Ŭ���̾�Ʈ�� �����Ͱ� �����κ��� ���´�.
	private OutputStream os; // Ŭ���̾�Ʈ�� �����͸� ������ ����.
	private DataInputStream dis; // ������ �����Ͱ� Ŭ���̾�Ʈ�κ��� ���´�.
	private static DataOutputStream dos; // ������ �����͸� Ŭ���̾�Ʈ�� ����

// �׿� ������
	Vector user_list = new Vector();
	Vector room_list = new Vector();
	Vector room_member_list = new Vector();
	StringTokenizer st;

	public static String My_Room; // ���� �ִ� �� �̸�
	private JLabel Room_label;
	private JLabel LoginPanel;

	private static Properties info; // ������Ƽ (�����б�)

	private static java.sql.Connection con;

	private static Statement st1;

	private static String sql;

	private static ResultSet rs;
	private final JScrollBar scrollBar = new JScrollBar();
	static Clip clip;
	static Clip clip2;

	private String Admin;

	private JButton Location; // ����API ��ġ ��ư
	private JButton Location2; // ����API �� ���� ���� ��ư
	private JButton Filebtn; // �������� ��ư
	private String imgsend;
	private String map_path = "C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\��\\";
	private JButton Profilebtn;
	private JLabel Image1;
	private JLabel Image2;
	
	private JLabel Image3;
	private JButton ChangeBG;
	private JButton Image4;

// �ʿ� ���� ��Ҹ� ���������� ���� ������ : 1. �̺�Ʈ ó��, ���߿� �ٸ� ����� �߰��� �� ���ϴ�.
// 							  2. ������ ���α׷� �Թ��ڵ��� ���α׷� �����ϱ� ���ϴ�.

	Client() // ������ �޼ҵ�
	{
		Login_init(); // �α���â ȭ�� ���� �޼ҵ�
		Main_init(); // ä��â ȭ�� ���� �޼ҵ�

		start(); // ���ӹ�ư�� ������ "Login Button Click", "AddUser Button Click"
		// �̶�� �޽����� �������� �̺�Ʈ�� �߻��ϰ� ����

	}

	private void start() {
		login_btn.addActionListener(this); // �α��� ��ư ������ (���� Ŭ�������� ��ӹ���)
		adduser_btn.addActionListener(this); // ȸ������ ��ư ������
		search_pwdbtn.addActionListener(this); // ��й�ȣ ã�� ��ư ������
		notesend_btn.addActionListener(this); // ���� ������ ��ư ������
		joinroom_btn.setForeground(new Color(147, 112, 219));
		joinroom_btn.setBackground(SystemColor.info);
		joinroom_btn.setFont(new Font("���� ���", Font.BOLD, 25));
		joinroom_btn.addActionListener(this); // ä�ù� ���� ��ư ������
		createroom_btn.setBackground(SystemColor.info);
		createroom_btn.setFont(new Font("���� ���", Font.BOLD, 25));
		createroom_btn.addActionListener(this); // ä�ù� ����� ��ư ������
		send_btn.setFont(new Font("���� ���", Font.BOLD, 25));
		send_btn.addActionListener(this); // ä�� ���� ��ư ������
		logout_btn.setFont(new Font("���� ���", Font.BOLD, 15));
		logout_btn.setBackground(SystemColor.info);
		logout_btn.setForeground(Color.RED);
		logout_btn.addActionListener(this); // �α׾ƿ� ��ư ������
		message_tf.addKeyListener(this); // ä�� ���� �Է� â ������
		save_btn.addActionListener(this); // ä�� ���� ��ư ������
		roomout_btn.addActionListener(this);
		Location.addActionListener(this); // ��ġ��ư ������
		// Location2.addActionListener(this); // ��ġ��ư2 ������
		Filebtn.addActionListener(this); // �������۹�ư ������
		Papago.setFont(new Font("���� ���", Font.BOLD, 20));
		Papago.addActionListener(this); // ���İ��ư ������
		Profilebtn.addActionListener(this);
		Music_stop.setBackground(SystemColor.info);
		Music_stop.setForeground(SystemColor.textHighlight);
		Music_stop.setFont(new Font("���� ���", Font.BOLD, 13));
		Music_stop.addActionListener(this);
		Music_play.addActionListener(this);
		Music_Back.addActionListener(this);
		Music_Next.addActionListener(this);
		ChangeBG.addActionListener(this);
		protect.addActionListener(this);
	}

	private void Login_init() // �α���â ȭ�� ����
	{
		
		Login_GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Login_GUI�� ���� �� ��ư ����
		Login_GUI.setBounds(100, 100, 900, 600);// Login_GUI�� ���� ȭ�� ũ��
		Login_GUI.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\����ȣ.jpg"));
		Login_GUI.setTitle("Login");

		ImageIcon icon = new ImageIcon("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\��ũ1.jpg");
		this.setIconImage(icon.getImage());
		this.setSize(320, 471);
		Login_Pane = new JPanel() {
			public void paintComponent(Graphics g) {
				// Approach 1 : Dispaly image at at full size
				g.drawImage(icon.getImage(), 0, 0, null);
				setOpaque(false); // �׸��� ǥ���ϰ� ����, �����ϰ� ����
				super.paintComponents(g);
			}

		};

		try {
			File file = new File("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\music\\wood.wav");
			AudioInputStream stream = AudioSystem.getAudioInputStream(file);
			clip = AudioSystem.getClip();
			clip.open(stream);
			clip.start();
			if (true) clip.loop(-1);	// ���� ���
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

		JLabel lblNewLabel_1 = new JLabel("���̵�");
		lblNewLabel_1.setBounds(320, 200, 70, 15);
		Login_Pane.add(lblNewLabel_1);
		lblNewLabel_1.setForeground(new Color(128, 128, 128));

		JLabel lblPassword = new JLabel("��й�ȣ");
		lblPassword.setBounds(320, 250, 70, 15);
		Login_Pane.add(lblPassword);
		lblPassword.setForeground(new Color(128, 128, 128));

		JLabel lblTitle = new JLabel("���ƿ��� ���� ȯ���ؿ�!");
		lblTitle.setBounds(308, 150, 500, 23);
		lblTitle.setFont(new Font("�޸հ��", Font.BOLD, 21));
		Login_Pane.add(lblTitle);

		JLabel lblsetTitle = new JLabel("�ٽ� �����ٴ� �ʹ� �ݰ�����!");
		lblsetTitle.setBounds(333, 175, 500, 15);
		lblsetTitle.setFont(new Font("�޸հ��", Font.PLAIN, 15));
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

		JLabel lblNewLabel_3 = new JLabel("������ �ʿ��Ѱ���?");
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

		Login_GUI.setVisible(true); // Login_GUI ȭ���� ���������� �����Ǿ���.
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
				System.out.println("DB����Ϸ�");
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
			System.out.println("�ش� Ŭ������ ã�� �� �����ϴ�." + cnfe.getMessage());
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

		JOptionPane.showMessageDialog(null, "id�� ��й�ȣ�� Ʋ���ϴ�!", "�˸�", JOptionPane.ERROR_MESSAGE);
		return false;
	}

	private void Main_init() // ä��â ȭ�� ����
	{
		Main_GUI.setTitle("����/ä�ù�");
		Main_GUI.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\����ȣ.jpg"));
		Main_GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Main_GUI.setBounds(100, 100, 1030, 800);
		ImageIcon icon = new ImageIcon("");
		this.setIconImage(icon.getImage());
		this.setSize(588, 531);
		Main_Pane = new JPanel() {
			public void paintComponent(Graphics g) {
				// Approach 1 : Dispaly image at at full size
				g.drawImage(icon.getImage(), 0, 0, null);
				setOpaque(false); // �׸��� ǥ���ϰ� ����, �����ϰ� ����
				super.paintComponents(g);
			}

		};
		
		
		Main_Pane.setBackground(Color.DARK_GRAY);
		Main_Pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		Main_GUI.setContentPane(Main_Pane);
		Main_Pane.setLayout(null);
		
		Image4 = new JButton("");		//ȭ�� ��ȣ �̹��� ��ư
		Image4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Image4.setVisible(false);
			}
		});
		Image4.setVisible(false);
		Image4.setBorderPainted( false );
		Image4.setFocusPainted(false); 
		Image4.setContentAreaFilled(false);
		Image4.setIcon(new ImageIcon("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\������.gif"));
		Image4.setHorizontalAlignment(SwingConstants.CENTER);
		Image4.setBounds(0, 0, 1024, 771);
		Main_Pane.add(Image4);
		User_list.setBackground(new Color(244, 164, 96));

		User_list.setBounds(751, 137, 222, 374);
		Main_Pane.add(User_list);
		User_list.setListData(user_list);
		User_list.setFont(new Font("���� ���", Font.PLAIN, 18));
		scrollBar.setBounds(974, 137, 17, 374);

		Main_Pane.add(scrollBar);

		notesend_btn.setBounds(751, 519, 113, 23);
		Main_Pane.add(notesend_btn);

		Room_label = new JLabel("* \uCC44 \uD305 \uBC29 \uBAA9 \uB85D *");
		Room_label.setFont(new Font("���� ���", Font.BOLD, 18));
		Room_label.setBounds(110, 102, 142, 37);
		Main_Pane.add(Room_label);
		Room_list.setFont(new Font("����", Font.PLAIN, 25));

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

		friend_add = new JButton("ģ���߰�");
		friend_add.setBounds(878, 519, 113, 23);
		Main_Pane.add(friend_add);

		friend_del = new JButton("\uCE5C\uAD6C\uC0AD\uC81C");
		friend_del.setBounds(878, 545, 113, 23);
		Main_Pane.add(friend_del);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(832, 23, 2, 2);
		Main_Pane.add(scrollPane_1);

		Emo = new JButton("�̸�Ƽ��");
		Emo.setFont(new Font("���� ���", Font.BOLD, 15));
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
		Music_play.setFont(new Font("���� ���", Font.BOLD, 13));
		Music_play.setBackground(SystemColor.info);
		Music_play.setBounds(648, 11, 113, 34);
		
		Main_Pane.add(Music_play);

		Location = new JButton("����");
		Location.setFont(new Font("���� ���", Font.BOLD, 20));
		Location.setBounds(542, 566, 97, 37);
		Main_Pane.add(Location);

		Filebtn = new JButton("\uC0AC\uC9C4\uC804\uC1A1");
		Filebtn.setFont(new Font("���� ���", Font.BOLD, 15));
		Filebtn.setBounds(648, 566, 97, 37);
		Main_Pane.add(Filebtn);

		Papago.setBounds(542, 611, 97, 37);
		Main_Pane.add(Papago);

		Profilebtn = new JButton("");
		Profilebtn.setIcon(new ImageIcon("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\�⺻������.jpg"));
		Profilebtn.setBounds(800, 578, 153, 148);
		Main_Pane.add(Profilebtn);
		button.setFont(new Font("���� ���", Font.PLAIN, 15));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				User_list.setVisible(true);
				friend_list.setVisible(false);
				
			}
		});
		button.setBounds(751, 102, 120, 33);
		
		Main_Pane.add(button);
		button_1.setFont(new Font("���� ���", Font.PLAIN, 15));
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				User_list.setVisible(false);
				friend_list.setVisible(true);
			}
		});
		
		button_1.setBounds(871, 102, 120, 33);
		
		Main_Pane.add(button_1);
		
		ChangeBG = new JButton("��� ����");
		ChangeBG.setForeground(SystemColor.textHighlight);
		ChangeBG.setFont(new Font("���� ���", Font.BOLD, 15));
		ChangeBG.setBackground(SystemColor.info);
		ChangeBG.setBounds(774, 54, 113, 34);
		Main_Pane.add(ChangeBG);

		protect.setForeground(new Color(0, 128, 0));
		protect.setFont(new Font("���� ���", Font.BOLD, 15));
		protect.setBackground(SystemColor.info);
		protect.setBounds(899, 54, 113, 34);
		
		Main_Pane.add(protect);
		Music_Next.setForeground(SystemColor.textHighlight);
		Music_Next.setFont(new Font("���� ���", Font.BOLD, 13));
		Music_Next.setBackground(SystemColor.info);
		Music_Next.setBounds(774, 10, 113, 34);
		
		Main_Pane.add(Music_Next);
		Music_Back.setForeground(SystemColor.textHighlight);
		Music_Back.setFont(new Font("���� ���", Font.BOLD, 13));
		Music_Back.setBackground(SystemColor.info);
		Music_Back.setBounds(523, 10, 113, 34);
		
		Main_Pane.add(Music_Back);
		
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setBackground(Color.WHITE);
		lblNewLabel_2.setFont(new Font("���� ���", Font.BOLD, 16));
		lblNewLabel_2.setBounds(838, 726, 91, 28);
		
		Main_Pane.add(lblNewLabel_2);
		Music_title.setFont(new Font("���� ���", Font.BOLD, 20));
		Music_title.setBackground(Color.WHITE);
		Music_title.setBounds(571, 55, 190, 37);
		
		Main_Pane.add(Music_title);
		label.setFont(new Font("���� ���", Font.BOLD, 20));
		label.setBounds(502, 54, 71, 38);
		
		Main_Pane.add(label);
		
		Image1 = new JLabel("");

		Image1.setIcon(new ImageIcon("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\ũ��������2.gif"));
		Image1.setHorizontalAlignment(SwingConstants.CENTER);

		Image1.setBounds(0, 0, 1024, 771);
		Main_Pane.add(Image1);
		
		Image2 = new JLabel("");
		Image2.setVisible(false);

		Image2.setIcon(new ImageIcon("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\���.jpg"));
		Image2.setHorizontalAlignment(SwingConstants.CENTER);

		Image2.setBounds(0, 0, 1024, 771);
		Main_Pane.add(Image2);
		
		Image3 = new JLabel("");
		Image3.setVisible(false);

		Image3.setIcon(new ImageIcon("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\4.png"));
		Image3.setHorizontalAlignment(SwingConstants.CENTER);
		Image3.setBounds(0, 0, 1024, 771);
		Main_Pane.add(Image3);

//	Location2 = new JButton("\uC9C0\uB3C42");
//	Location2.setBounds(779, 483, 73, 23);
//	Main_Pane.add(Location2);

		Main_GUI.setResizable(false);

		this.setVisible(false); // ȭ���� ���������� ������
		// (���� Ŭ�������� JFrame�� ��ӹ޾ұ� ������ �տ� "this."�� ���δ�.)
	}

	private void Network() {
		Profilebtn.setIcon(new ImageIcon(checkIDImage(id, 9)));
		
		try {
			socket = new Socket(ip, port);

			if (socket != null) // ���������� ������ ���� �Ǿ��� ���
			{
				Connection();


			} 	//

		} catch (UnknownHostException e) { // �ش� ȣ��Ʈ�� ã�� �� ���� ���� �߻�

			JOptionPane.showMessageDialog(null, "���� ����", "�˸�", JOptionPane.ERROR_MESSAGE); // ���� ���� ����

		} catch (IOException e) { // ��Ʈ������ ������ ���� ���� �߻�

			JOptionPane.showMessageDialog(null, "���� ����", "�˸�", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void Connection() // �������� �޼ҵ� ����κ�
	{

		try {
			is = socket.getInputStream();
			dis = new DataInputStream(is); // Ŭ���̾�Ʈ���� �޾ƾ� �� ������ �����͸� ������ ����(��ü ����)

			os = socket.getOutputStream();
			dos = new DataOutputStream(os); // Ŭ���̾�Ʈ�� ���� ������ �����͸� ������ ����(��ü ����)
		}

		catch (IOException e) // ����ó���κ�
		{
			JOptionPane.showMessageDialog(null, "���� ����", "�˸�", JOptionPane.ERROR_MESSAGE);

		} // Stream ���� ��

		// �α��� �Ϸ� �Ǹ�
		this.setVisible(false); // ä��â ǥ��
		this.Login_GUI.setVisible(false); // �α���â �����
		clip.close(); // ��� ����
		this.Main_GUI.setVisible(true); // ä��â ǥ��
		roomout_btn.setVisible(false);
		save_btn.setVisible(false);
		Chat_Pane.setVisible(false); // ä��â �Ⱥ��̰�
		send_btn.setVisible(false); // ���� ��ư �Ⱥ��̰�
		Chat_Pane.setEditable(false); // ä�� ���� ���� ���ϰ�
		Papago.setVisible(false);
		Emo.setVisible(false);
		Location.setVisible(false);
		Filebtn.setVisible(false);
		
		try {
			File file = new File("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\music\\HappyTown.wav");
			AudioInputStream stream = AudioSystem.getAudioInputStream(file);
			clip2 = AudioSystem.getClip();
			clip2.open(stream);
			clip2.start();
			if (true) clip2.loop(-1);	// ���� ���
			// clip2.close();

		} catch (Exception f) {

			f.printStackTrace();
		}

		// ó�� ���ӽÿ� ID ����
		send_message(id);

		// User_list(��ü ������ ����Ʈ)�� ����� �߰�

		user_list.add(id);

		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {

				while (true) { // �޽��� ���� ���� ���

					try {
						String msg = dis.readUTF(); // �޽��� ����

						System.out.println("�����κ��� ���ŵ� �޼���: " + msg);

						in_message(msg);

					} catch (IOException e) { // ������ ����� ��

						try {
							os.close();
							is.close();
							dos.close();
							dis.close();
							socket.close();
						} catch (IOException e1) {
						}
						break;
					} // �޼��� ����
				}
			}
		});

		th.start(); // �����κ��� ������ �޽����� ���� �� ����
	}

	public static void send_message(String str) // �������� �޼����� ������ �κ�
	{
		try {
			dos.writeUTF(str);
		} catch (IOException e) { // ����ó���κ�

		}

	}

	private void in_message(String str) // �����κ��� ������ ��� �޽���
	{
		st = new StringTokenizer(str, "/");
		doc = Chat_Pane.getStyledDocument();

		String protocol = st.nextToken();
		String Message = st.nextToken();

		System.out.println("�������� : " + protocol);
		System.out.println("���� : " + Message);

		if (protocol.equals("NewUser")) // ���ο� ������
		{
			user_list.add(Message); // ������� ID �߰�
		}

		else if (protocol.equals("OldUser")) // ���� ������
		{
			user_list.add(Message);
			// room_member_list.add(Message);
		}

		else if (protocol.equals("Note")) // ���� �ޱ�
		{

			String note = st.nextToken();

			System.out.println(Message + " ����ڷκ��� �� ���� : " + note); // Consoleâ�� ���� ���� ���

			JOptionPane.showMessageDialog(null, note, Message + "������ ���� ����", JOptionPane.CLOSED_OPTION); // �ΰ�, �޽���,
																											// OOO�����κ���
																											// ����, �ݱ��ư
			// �޽��� �˸� â�� ���� �߸鼭 �޽��� ���� ���!! (Consoleâ���� ���)
		}

		else if (protocol.equals("Friend")) {
			String note = st.nextToken();

			System.out.println(Message + " ģ����û : " + note); // Consoleâ�� ���� ���� ���

			JOptionPane.showMessageDialog(null, note, Message + "������ ���� ģ����û", JOptionPane.YES_NO_OPTION); // �ΰ�, �޽���,
																											// OOO�����κ���
																											// ģ����û,
																											// �ݱ��ư
			// �޽��� �˸� â�� ���� �߸鼭 �޽��� ���� ���!! (Consoleâ���� ���)
		}

		else if (protocol.equals("user_list_update")) // ������ ���
		{
			// User_list.updateUI();
			User_list.setListData(user_list);
		}

		else if (protocol.equals("CreateRoom")) // ���� ���������(����)
		{
			My_Room = Message;

			Admin = id;

			Room_list.setVisible(false); // ä�ù��� ��Ȱ��ȭ
			Room_label.setVisible(false); // ä�ù��� �� ��Ȱ��ȭ

//			Room_member.setVisible(true); // Ȱ��ȭ
//			Room_Member_list.setVisible(true); // ä�������ο�����Ʈ Ȱ��ȭ
			kick.setVisible(true); // �����ư ��Ȱ��ȭ
			invitebtn.setEnabled(true); // �ʴ��ư Ȱ��ȭ

			room_member_list.add(id);

			message_tf.setEnabled(true);
			send_btn.setEnabled(true);
			joinroom_btn.setVisible(false); // ��ȭ�� ���� ��ư ��Ȱ��ȭ
			joinroom_btn.setEnabled(false);
			createroom_btn.setVisible(false); // �� ����� ��ư ��Ȱ��ȭ
			roomout_btn.setVisible(true); // �� ������ ��ư Ȱ��ȭ
			roomout_btn.setEnabled(true); // �� ������ ��ư Ȱ��ȭ
			save_btn.setVisible(true); // ��ȭ ���� ���� ��ư Ȱ��ȭ
			Chat_Pane.setVisible(true); // ��ȭ ���� Ȱ��ȭ
			Chat_Pane.setText(null); // ä�� ���� ����
			message_tf.setVisible(true); // �޽��� �ؽ�Ʈ Ȱ��ȭ
			send_btn.setVisible(true); // ��ȭ ������ ��ư Ȱ��ȭ

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
			JOptionPane.showMessageDialog(null, "ä�ù��� �����ϼ̽��ϴ�. ����� �����Դϴ�.", "�˸�", JOptionPane.INFORMATION_MESSAGE);

		}

		else if (protocol.equals("CreateRoomFail")) // �� ����� �������� ���
		{

			JOptionPane.showMessageDialog(null, "�游��� ����", "�˸�", JOptionPane.ERROR_MESSAGE);

		}

		else if (protocol.equals("New_Room")) // ���ο� ���� ����� �� ��
		{
			room_list.add(Message); // ä�ù� ��Ͽ� �߰�
			Room_list.setListData(room_list);
			//joinroom_btn.setVisible(true);

		}

		else if (protocol.equals("OldRoom")) // ����ڰ� �����ߴµ�, ���� �ִ� ���� ���� ��
		{
			room_list.add(Message); // �ִ� �� ��� ���
			// room_member_list.add(Message);
		}

		else if (protocol.equals("room_list_update")) {
			Room_list.setListData(room_list);
//			Room_Member_list.setListData(room_member_list);
			// AWT List
		}

		else if (protocol.equals("JoinRoom")) // ��ȭ�濡 ���� ���� ���� ��
		{
			My_Room = Message;

//			Room_member.setVisible(true); // ä�ù������ο� Ȱ��ȭ
//			Room_Member_list.setVisible(true); // ä�������ο�����Ʈ Ȱ��ȭ

			Room_list.setVisible(false); // ä�ù��� ��Ȱ��ȭ
			Room_label.setVisible(false); // ä�ù��� �� ��Ȱ��ȭ
			invitebtn.setEnabled(true); // �ʴ��ư Ȱ��ȭ

			room_member_list.add(id);

			message_tf.setEnabled(true); // �޽��� ���� Ȱ��ȭ
			send_btn.setEnabled(true); // �޽��� ������ ��ư Ȱ��ȭ
			joinroom_btn.setVisible(false); // ��ȭ�� ���� ��ư ��Ȱ��ȭ
			createroom_btn.setVisible(false); // �� ����� ��ư ��Ȱ��ȭ
			roomout_btn.setVisible(true); // �� ������ ��ư Ȱ��ȭ
			roomout_btn.setEnabled(true); // �� ������ ��ư Ȱ��ȭ
			save_btn.setVisible(true); // ��ȭ ���� ���� ��ư Ȱ��ȭ
			message_tf.setVisible(true); // ��ȭ ���� �Է� �ؽ�Ʈ Ȱ��ȭ
			Chat_Pane.setVisible(true); // ��ȭ ���� Ȱ��ȭ
			Chat_Pane.setText(null); // ä�� ���� ����
			send_btn.setVisible(true); // ��ȭ ���� ��ư Ȱ��ȭ

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

			JOptionPane.showMessageDialog(null, "ä�ù濡 �����߽��ϴ�.", "�˸�", JOptionPane.INFORMATION_MESSAGE);
		}

		else if (protocol.equals("User_out")) // ����ڰ� ������ ��
		{
			user_list.remove(Message); // ����ڸ� ��Ͽ��� ���� ��Ų��.
			// room_member_list.remove(id); // id ����

		}

		else if (protocol.equals("OutRoom")) {
			My_Room = Message;

			Room_list.setVisible(true); // ä�ù��� Ȱ��ȭ
			Room_label.setVisible(true); // ä�ù��� �� Ȱ��ȭ

//			Room_member.setVisible(false); // ���ϸ���Ʈ ��Ȱ��ȭ
//			Room_Member_list.setVisible(false); // ä�������ο�����Ʈ ��Ȱ��ȭ
			room_member_list.remove(id); // id ����
			kick.setVisible(false); // �����ư ��Ȱ��ȭ
			invitebtn.setEnabled(false); // �ʴ��ư ��Ȱ��ȭ

			message_tf.setEnabled(false); // ���� ��ư ��Ȱ��ȭ
			joinroom_btn.setVisible(true); // ä�ù� ���� ��ư Ȱ��ȭ
			createroom_btn.setVisible(true); // ä�ù� ����� ��ư Ȱ��ȭ
			roomout_btn.setVisible(false); // ä�ù� ������ ��ư ��Ȱ��ȭ
			save_btn.setVisible(false);
			message_tf.setVisible(false); // ��ȭ ���� �Է� �ؽ�Ʈ Ȱ��ȭ
			Chat_Pane.setVisible(false);
			Chat_Pane.setText(null); // ä�� ���� ����

			Papago.setVisible(false);
			Emo.setVisible(false);
			Location.setVisible(false);
			Filebtn.setVisible(false);

		}

		else if (protocol.equals("Chatting")) // ä���� �� ���
		{

			GregorianCalendar calendar = new GregorianCalendar();
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH) + 1;
			int amPm = calendar.get(Calendar.AM_PM);
			int date = calendar.get(Calendar.DATE);
			int hour = calendar.get(Calendar.HOUR);
			int min = calendar.get(Calendar.MINUTE);
			int sec = calendar.get(Calendar.SECOND);
			String sAmPm = amPm == Calendar.AM ? "����" : "����";

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
				msg = msg.trim(); // msg trim ����

				if (Message.equals(id)) { // �� ����
					Style profile = doc.addStyle("�����ʻ���1", null);
					StyleConstants.setIcon(profile, ResizeImage(checkIDImage(id, 9), 40, 40));
					doc.insertString(doc.getLength(), "ignored text", profile);

					doc.insertString(doc.getLength(), "[" + /* year + "�� " + month + "�� " + date + "�� " + */
							sAmPm + " " + hour + "�� " + min + "�� " + "] ", doc.getStyle("time"));
					doc.insertString(doc.getLength(), " " + Message + " ", doc.getStyle("blue"));// ����
					doc.insertString(doc.getLength(), ": ", doc.getStyle("black")); // : ����
					doc.setLogicalStyle(doc.getLength(), doc.getStyle("right"));

					if (msg.equals(map_path + id + ".jpg")) { // ���� ���� ����
						Style style = doc.addStyle("�Ѥ�", null);
						StyleConstants.setIcon(style, ResizeImage(map_path + id + ".jpg", 300, 300));
						doc.insertString(doc.getLength(), "ignored text", style);
						doc.insertString(doc.getLength(), "\n", null);
						Chat_Pane.setCaretPosition(doc.getLength());
					}
					
					else if (msg.equals("/���� "+Message)) {		// ��� ���� ����
						Style style = doc.addStyle("�Ѥ�", null);
						
						Profile frame = new Profile();
						frame.setVisible(true);
						
						doc.insertString(doc.getLength(), "ignored text", style);
						doc.insertString(doc.getLength(), "\n", null);
						Chat_Pane.setCaretPosition(doc.getLength());
					}

					else if (msg.equals(imgsend)) {
						Style style = doc.addStyle("�Ѥ�", null);
						StyleConstants.setIcon(style, ResizeImage(imgsend, 100, 100));
						doc.insertString(doc.getLength(), "ignored text", style);
						doc.insertString(doc.getLength(), "\n", null);
						Chat_Pane.setCaretPosition(doc.getLength());
					}

					else if (msg.equals("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\Ǫ\\1.gif") || msg.equals("�Ѥ�")) {

						Style style = doc.addStyle("�Ѥ�", null);
						StyleConstants.setIcon(style, new ImageIcon("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\Ǫ\\1.gif"));
						doc.insertString(doc.getLength(), "ignored text", style);
						doc.insertString(doc.getLength(), "\n", null);
						Chat_Pane.setCaretPosition(doc.getLength());

					}

					else if (msg.equals("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\Ǫ\\2.gif") || msg.equals("��ħ")) {
						Style style = doc.addStyle("������", null);
						StyleConstants.setIcon(style, new ImageIcon("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\Ǫ\\2.gif"));
						doc.insertString(doc.getLength(), "ignored text", style);
						doc.insertString(doc.getLength(), "\n", null);
						Chat_Pane.setCaretPosition(doc.getLength());

					}

					else if (msg.equals("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\Ǫ\\3.gif")) {
						Style style = doc.addStyle("��", null);
						StyleConstants.setIcon(style, new ImageIcon("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\Ǫ\\3.gif"));
						doc.insertString(doc.getLength(), "ignored text", style);
						doc.insertString(doc.getLength(), "\n", null);
						Chat_Pane.setCaretPosition(doc.getLength());

					}

					else if (msg.equals("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\Ǫ\\4.gif")) {
						Style style = doc.addStyle("�����", null);
						StyleConstants.setIcon(style, new ImageIcon("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\Ǫ\\4.gif"));
						doc.insertString(doc.getLength(), "ignored text", style);
						doc.insertString(doc.getLength(), "\n", null);
						Chat_Pane.setCaretPosition(doc.getLength());

					}

					else if (msg.equals("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\Ǫ\\5.gif")) {
						Style style = doc.addStyle("����", null);
						StyleConstants.setIcon(style, new ImageIcon("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\Ǫ\\5.gif"));
						doc.insertString(doc.getLength(), "ignored text", style);
						doc.insertString(doc.getLength(), "\n", null);
						Chat_Pane.setCaretPosition(doc.getLength());

					}

					else if (msg.equals("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\Ǫ\\6.gif")) {
						Style style = doc.addStyle("�Ф�", null);
						StyleConstants.setIcon(style, new ImageIcon("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\Ǫ\\6.gif"));
						doc.insertString(doc.getLength(), "ignored text", style);
						doc.insertString(doc.getLength(), "\n", null);
						Chat_Pane.setCaretPosition(doc.getLength());

					}

					else if (msg.equals("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\Ǫ\\7.gif")) {
						Style style = doc.addStyle("��ȣ", null);
						StyleConstants.setIcon(style, new ImageIcon("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\Ǫ\\7.gif"));
						doc.insertString(doc.getLength(), "ignored text", style);
						doc.insertString(doc.getLength(), "\n", null);
						Chat_Pane.setCaretPosition(doc.getLength());

					}

					else if (msg.equals("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\Ǫ\\8.gif")) {
						Style style = doc.addStyle("����", null);
						StyleConstants.setIcon(style, new ImageIcon("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\Ǫ\\8.gif"));
						doc.insertString(doc.getLength(), "ignored text", style);
						doc.insertString(doc.getLength(), "\n", null);
						Chat_Pane.setCaretPosition(doc.getLength());

					}

					else if (msg.equals("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\Ǫ\\9.gif") || msg.equals("����")) {
						Style style = doc.addStyle("����", null);
						StyleConstants.setIcon(style, new ImageIcon("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\Ǫ\\9.gif"));
						doc.insertString(doc.getLength(), "ignored text", style);
						doc.insertString(doc.getLength(), "\n", null);
						Chat_Pane.setCaretPosition(doc.getLength());

					}

					else if (msg.equals("C:\\�ູ��.png") || msg.equals("�ູ��")) {
						Style style = doc.addStyle("�ູ��", null);
						StyleConstants.setIcon(style, new ImageIcon("C:\\�ູ��.png"));
						doc.insertString(doc.getLength(), "ignored text", style);
						doc.insertString(doc.getLength(), "\n", null);
						Chat_Pane.setCaretPosition(doc.getLength());

					}

					else { // ���� ä�� �۾�
						doc.insertString(doc.getLength(), msg, doc.getStyle("blue"));

					}

					Chat_Pane.setCaretPosition(doc.getLength());

				}

				else if (Message.equals("�˸�")) {

					doc.setLogicalStyle(doc.getLength(), doc.getStyle("center")); // ���� ��� ����
					doc.insertString(doc.getLength(), Message + " : " + msg + "\n", doc.getStyle("black")); // ���� ������
					Chat_Pane.setCaretPosition(doc.getLength());

					try {
						File file = new File("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\��.wav");
						AudioInputStream stream = AudioSystem.getAudioInputStream(file);
						Clip clip = AudioSystem.getClip();
						clip.open(stream);
						clip.start();

					} catch (Exception f) {

						f.printStackTrace();
					}

				}

				else { // ��� ����

					Style profile = doc.addStyle("�����ʻ���2", null);
					StyleConstants.setIcon(profile, ResizeImage(checkIDImage(Message, 9), 40, 40));
					doc.insertString(doc.getLength(), "ignored text", profile);

					doc.insertString(doc.getLength(), "[" + /* year + "�� " + month + "�� " + date + "�� " + */
							sAmPm + " " + hour + "�� " + min + "�� " + "] ", doc.getStyle("time"));
					doc.insertString(doc.getLength(), " " + Message + " ", doc.getStyle("yellow"));// ��� ����
					doc.insertString(doc.getLength(), ": ", doc.getStyle("black")); // : ����
					doc.setLogicalStyle(doc.getLength(), doc.getStyle("left"));

					if (msg.equals(map_path + Message + ".jpg")) { // ���� ���� ����
						Style style = doc.addStyle("�Ѥ�", null);
						StyleConstants.setIcon(style, ResizeImage(map_path + Message + ".jpg", 300, 300));
						doc.insertString(doc.getLength(), "ignored text", style);
						doc.insertString(doc.getLength(), "\n", null);
						Chat_Pane.setCaretPosition(doc.getLength());
					}

					else if (msg.equals(imgsend)) {
						Style style = doc.addStyle("�Ѥ�", null);
						StyleConstants.setIcon(style, ResizeImage(imgsend, 100, 100));
						doc.insertString(doc.getLength(), "ignored text", style);
						doc.insertString(doc.getLength(), "\n", null);
						Chat_Pane.setCaretPosition(doc.getLength());
					}

					else if (msg.equals("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\Ǫ\\1.gif") || msg.equals("�Ѥ�")) {
						Style style = doc.addStyle("�Ѥ�", null);
						StyleConstants.setIcon(style, new ImageIcon("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\Ǫ\\1.gif"));
						doc.insertString(doc.getLength(), "ignored text", style);
						doc.insertString(doc.getLength(), "\n", null);
						Chat_Pane.setCaretPosition(doc.getLength());
					}

					else if (msg.equals("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\Ǫ\\2.gif") || msg.equals("������")) {
						Style style = doc.addStyle("������", null);
						StyleConstants.setIcon(style, new ImageIcon("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\Ǫ\\2.gif"));
						doc.insertString(doc.getLength(), "ignored text", style);
						doc.insertString(doc.getLength(), "\n", null);
						Chat_Pane.setCaretPosition(doc.getLength());
					}

					else if (msg.equals("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\Ǫ\\3.gif") || msg.equals("���")) {
						Style style = doc.addStyle("���", null);
						StyleConstants.setIcon(style, new ImageIcon("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\Ǫ\\3.gif"));
						doc.insertString(doc.getLength(), "ignored text", style);
						doc.insertString(doc.getLength(), "\n", null);
						Chat_Pane.setCaretPosition(doc.getLength());
					}

					else if (msg.equals("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\Ǫ\\4.gif") || msg.equals("�����")) {
						Style style = doc.addStyle("�����", null);
						StyleConstants.setIcon(style, new ImageIcon("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\Ǫ\\4.gif"));
						doc.insertString(doc.getLength(), "ignored text", style);
						doc.insertString(doc.getLength(), "\n", null);
						Chat_Pane.setCaretPosition(doc.getLength());
					}

					else if (msg.equals("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\Ǫ\\5.gif") || msg.equals("����")) {
						Style style = doc.addStyle("����", null);
						StyleConstants.setIcon(style, new ImageIcon("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\Ǫ\\5.gif"));
						doc.insertString(doc.getLength(), "ignored text", style);
						doc.insertString(doc.getLength(), "\n", null);
						Chat_Pane.setCaretPosition(doc.getLength());
					}

					else if (msg.equals("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\Ǫ\\6.gif") || msg.equals("�Ф�")) {
						Style style = doc.addStyle("�Ф�", null);
						StyleConstants.setIcon(style, new ImageIcon("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\Ǫ\\6.gif"));
						doc.insertString(doc.getLength(), "ignored text", style);
						doc.insertString(doc.getLength(), "\n", null);
						Chat_Pane.setCaretPosition(doc.getLength());
					}

					else if (msg.equals("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\Ǫ\\7.gif") || msg.equals("�ų�")) {
						Style style = doc.addStyle("��ȣ", null);
						StyleConstants.setIcon(style, new ImageIcon("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\Ǫ\\7.gif"));
						doc.insertString(doc.getLength(), "ignored text", style);
						doc.insertString(doc.getLength(), "\n", null);
						Chat_Pane.setCaretPosition(doc.getLength());
					}

					else if (msg.equals("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\Ǫ\\8.gif") || msg.equals("����")) {
						Style style = doc.addStyle("����", null);
						StyleConstants.setIcon(style, new ImageIcon("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\Ǫ\\8.gif"));
						doc.insertString(doc.getLength(), "ignored text", style);
						doc.insertString(doc.getLength(), "\n", null);
						Chat_Pane.setCaretPosition(doc.getLength());
					}

					else if (msg.equals("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\Ǫ\\9.gif") || msg.equals("����")) {
						Style style = doc.addStyle("����", null);
						StyleConstants.setIcon(style, new ImageIcon("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\Ǫ\\9.gif"));
						doc.insertString(doc.getLength(), "ignored text", style);
						doc.insertString(doc.getLength(), "\n", null);
						Chat_Pane.setCaretPosition(doc.getLength());
					}

					else if (msg.equals("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\��ī��.png") || msg.equals("�ູ��")) {
						Style style = doc.addStyle("�ູ��", null);
						StyleConstants.setIcon(style, new ImageIcon("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\��ī��.png"));
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

		new Client(); // Client�� ����� ���� �ʿ��� ������

		String str = "";
		for (int i = 0; i < 19; i++)
			str += i + "��\n";
		JTextPane Chat_Pane = new JTextPane();
		JScrollPane scrollpane = new JScrollPane(Chat_Pane);
		JScrollBar scrollbar = scrollpane.getVerticalScrollBar();

		scrollbar.setValue(scrollbar.getMaximum());

	}

	String ProfilePath;
	private final JButton Music_stop = new JButton("\u266C\uC74C\uC545\uC885\uB8CC\u266C");
	private final JButton Papago = new JButton("���İ�");
	private final JButton button = new JButton("\uC804\uCCB4\uBAA9\uB85D");
	private final JButton button_1 = new JButton("\uCE5C\uAD6C\uBAA9\uB85D");
	private final JLabel lblNewLabel_2 = new JLabel("<\uB0B4 \uC815\uBCF4>");
	private int cot = 0;	// ��� ���� ���� ī��Ʈ
	private static int cot2 = 1;	// ���� ���� ī��Ʈ
	private final JButton Music_play = new JButton("\u266C\uC74C\uC545\uC7AC\uC0DD\u266C");
	private final JButton protect = new JButton("\uD654\uBA74 \uBCF4\uD638");
	private final JButton Music_Next = new JButton("\u266C\uB2E4\uC74C\uC74C\uC545\u266C");
	private final JButton Music_Back = new JButton("\u266C\uC774\uC804\uC74C\uC545\u266C");
	private final static JLabel Music_title = new JLabel("HappyTown");
	private final JLabel label = new JLabel("\uC74C\uC545 : ");

	@Override
	public void actionPerformed(ActionEvent e) { // �޼ҵ带 ������
		// login_btn = �α��ι�ư
		if (e.getSource() == login_btn) {
			System.out.println("�α��� ��ư Ŭ��");

			if (ip_tf.getText().length() == 0) // ip�Է¶��� �ƹ� ���� �Է� �� �ϰ� �α��� �õ��� ��
			{
				ip_tf.setText("IP�� �Է����ּ���");
				ip_tf.requestFocus();

			}

			if (port_tf.getText().length() == 0) // port�Է¶��� �ƹ� ���� �Է� �� �ϰ� �α��� �õ��� ��
			{
				port_tf.setText("Port��ȣ�� �Է����ּ���");
				port_tf.requestFocus();
			}

			if (id_tf.getText().length() == 0) {
				id_tf.setText("ID�� �Է����ּ���");
				id_tf.requestFocus();
			}
			if (pw_tf.getText().length() == 0) {
				pw_tf.setText("��й�ȣ�� �Է����ּ���");
				pw_tf.requestFocus();
			}

			else {
				ip = ip_tf.getText().trim(); // ���� ip�ּҸ� �α��ν� �ҷ��� �� ���� (������ ���µ� �����Ͽ� trim()�� ����)
				port = Integer.parseInt(port_tf.getText().trim()); // �޾ƿ� �� String���̱� ������, ��Ʈ�� int������ ���� ��ȯ ��Ų��.
				id = id_tf.getText().trim(); // id�� �޾ƿ��� �κ�
				pwd = pw_tf.getText().trim(); // password�� �޾ƿ��� �κ�
				// MemberProc mem = new MemberProc();

				if (id.equals("root") && pwd.equals("7508")) { // �����ڸ�� �α���
					Manager manager = new Manager("7508");
				}

				else if (DB_Check(id, pwd)) {
					Network(); // �α��� ��ư�� ������ ������ �����ϰ� �ϱ� ����
				}

			}
		}

		else if (e.getSource() == friend_add) {
			System.out.println("ģ�� �߰� ��ư Ŭ��");
			String user = (String) User_list.getSelectedValue();
			if (user == null) {
				JOptionPane.showMessageDialog(null, "ģ���� ��븦 �������ּ���!", "���", JOptionPane.INFORMATION_MESSAGE);
			} else {
				String note = JOptionPane.showInputDialog("�����޼���"); // �޽��� â ����
				if (note != null) {
					// ex = Note/User2/���� User1�̾�
				}
				System.out.println("�޴� ��� : " + user + " | ���� ���� : " + note);
				// �޴� ����� �޽��� ���� ���
			}
		}

		else if (e.getSource() == invitebtn) { // �ʴ� ��ư ���� ��
			System.out.println("�ʴ��ϱ� ��ư Ŭ��");
			String Invite = (String) User_list.getSelectedValue();

			if (Invite == null) {
				JOptionPane.showMessageDialog(null, "�ʴ��� ��븦 �������ּ���!", "���", JOptionPane.INFORMATION_MESSAGE);
			} else {
				String note = JOptionPane.showInputDialog(null, "���� �ʴ��ϼ̽��ϴ�", "�ʴ�޼���", JOptionPane.YES_NO_OPTION); // �޽���
																													// â
																													// ����
				if (note != null) {
					if (note == "1") {
						System.out.println("�ʴ� ����");
					}
				}
				// �޴� ����� �޽��� ���� ���
			}
		}

		else if (e.getSource() == notesend_btn) {
			System.out.println("���� ������ ��ư Ŭ��");
			String user = (String) User_list.getSelectedValue();// ���� ���� ��� ����Ʈ���� ����

			if (user == null) {
				JOptionPane.showMessageDialog(null, "���� ���� ��븦 �������ּ���!", "���", JOptionPane.INFORMATION_MESSAGE);
			} else {
				String note = JOptionPane.showInputDialog("�����޼���"); // �޽��� â ����
				if (note != null) {
					send_message("Note/" + user + "/" + note);

					// ex = Note/User2/���� User1�̾�
				}
				System.out.println("�޴� ��� : " + user + " | ���� ���� : " + note);
				// �޴� ����� �޽��� ���� ���
			}
		}

		else if (e.getSource() == kick) { // ���� ��ư ���� ��
			System.out.println("���� ��ư Ŭ��");
//			String Ben = (String) Room_Member_list.getSelectedValue();

		}

		else if (e.getSource() == joinroom_btn) // ��ȭ�� �����ϱ�
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

			System.out.println("�� ���� ��ư Ŭ��");
		}

		else if (e.getSource() == createroom_btn) // �� �����
		{
			String roomname = JOptionPane.showInputDialog("�� �̸�");
			if (roomname != null) {
				send_message("CreateRoom/" + roomname);
			}
			System.out.println("�� ����� ��ư Ŭ��");
			/*
			 * image1.setVisible(true); image2.setVisible(true); image3.setVisible(true);
			 * image4.setVisible(true); image5.setVisible(true); image6.setVisible(true);
			 * image7.setVisible(true); image8.setVisible(true); image9.setVisible(true);
			 * image10.setVisible(true);
			 */
		}

		else if (e.getSource() == send_btn) // ���� ��ư
		{
			send_message("Chatting/" + My_Room + "/" + message_tf.getText().trim());
			message_tf.setText(""); // ��ȭ�濡�� ��ȭ�޽��� �������� ä�ó��� �Է�â���� ������ �������.
			message_tf.requestFocus();

			// Chatting + ���̸� + ����

			System.out.println("ä�� ���� ��ư Ŭ��");
		}

		else if (e.getSource() == logout_btn) // �α׾ƿ� ��ư
		{
			System.out.println("�α׾ƿ� ��ư Ŭ��");
			String OutRoom = (String) Room_list.getSelectedValue();
			send_message("OutRoom/" + OutRoom);
			clip2.close();
			// String OutRoom = (String)Room_list.getSelectedValue();
			// send_message("OutRoom/" + OutRoom);
			this.setVisible(false);
			this.Main_GUI.setVisible(false); // ä��â ����
			Login_init(); // Ŭ���̾�Ʈ ä��â ������ �α���â ����
			try {
				os.close();
				is.close();
				dos.close();
				dis.close();
				socket.close();
				user_list.remove(Message); // ����ڸ� ��Ͽ��� ���� ��Ų��.
				room_list.remove(Message); // ���� ��Ͽ��� ���� ��Ų��.
			} catch (IOException e1) {
			}
			JOptionPane.showMessageDialog(null, "�α׾ƿ��� �Ϸ��ϼ̽��ϴ�.", "�˸�", JOptionPane.INFORMATION_MESSAGE);
		}

		else if (e.getSource() == adduser_btn) { // ȸ������
			System.out.println("ȸ������ ��ư Ŭ��");

		}

		else if (e.getSource() == save_btn) { // ä�� ����
			System.out.println("ä�� ���� ��ư Ŭ��");
			FileInputStream fis = null;
			Connection conn = null; // ������ �����ϱ� ����
			PreparedStatement pstmt = null; // �����͸� �о���� ����

			FileDialog dlg = new FileDialog(this, "������ ���� �����ϱ�", FileDialog.LOAD);
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
		else if (e.getSource() == Music_stop) {	//���� ��ư
			Music_play.setVisible(true);
			Music_stop.setVisible(false);
			clip2.close();
			
		}
		else if (e.getSource() == Music_play) {	//��� ��ư
			Music_stop.setVisible(true);
			Music_play.setVisible(false);
			MusicChange(cot2);
		}
		else if (e.getSource() == Music_Next) {	// ���� ���� ��ư
			Music_play.setVisible(false);
			Music_stop.setVisible(true);
			cot2++;
			if(cot2>4) {
				cot2=1;
			}
			MusicChange(cot2);
			
		}
		else if (e.getSource() == Music_Back) {	// ���� ���� ��ư
			Music_play.setVisible(false);
			Music_stop.setVisible(true);
			cot2--;
			if (cot2==0) {	// cot2�� 0�̸� 4�� �ٲ�
				cot2=4;
			}
			MusicChange(cot2);
		}
		
		else if (e.getSource() == ChangeBG) {		// ��� ���� ��ư
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

		else if (e.getSource() == roomout_btn) // �� ������
		{

			String OutRoom = (String) Room_list.getSelectedValue();
			send_message("OutRoom/" + OutRoom);

			// ä�ù� ����� ��ư Ȱ��ȭ
			createroom_btn.setEnabled(true);
			createroom_btn.setVisible(true);

			// ä�ù� ���� ��ư Ȱ��ȭ
			joinroom_btn.setEnabled(true);
			joinroom_btn.setVisible(true);

			// ä�ù� ������ ��ư ������� �����
			roomout_btn.setVisible(false);

			// ���� ��ư ��Ȱ��ȭ
			kick.setVisible(false);
			invitebtn.setEnabled(false); // �ʴ��ư ��Ȱ��ȭ

			// ä�ù� ��� Ȱ��ȭ
			Room_list.setVisible(true); // ä�ù��� Ȱ��ȭ
			Room_label.setVisible(true); // ä�ù��� �� Ȱ��ȭ

			// ���� ��ư ��Ȱ��ȭ
			send_btn.setVisible(false);

			// ä�� ���� ��ư ��Ȱ��ȭ
			save_btn.setVisible(false);

			Chat_Pane.setText(null); // ä�� ���� ����

			if (id == Admin) { // ������ ������
				// JOptionPane.showMessageDialog(null, "�� ����", "�˸�",
				// JOptionPane.INFORMATION_MESSAGE);

			}

			roomout_btn.setVisible(false); // ä�ù� ������ ��ư ��Ȱ��ȭ
			save_btn.setVisible(false);
			message_tf.setVisible(false); // ��ȭ ���� �Է� �ؽ�Ʈ Ȱ��ȭ
			Chat_Pane.setVisible(false);

			Papago.setVisible(false);
			Emo.setVisible(false);
			Location.setVisible(false);
			Filebtn.setVisible(false);

			JOptionPane.showMessageDialog(null, "ä�ù濡�� �����ϼ̽��ϴ�.", "�˸�", JOptionPane.INFORMATION_MESSAGE);

			if (Server.user_vc.size() + 1 == 0) { // �濡 �ִ� ����� ������
				room_list.remove(Message); // ���� ��Ͽ��� ���� ��Ų��.
			}

		}

		else if (e.getSource() == Location) {
			System.out.println("��ġ ��ư");
			new test.Main();
		}
//	else if (e.getSource() == Location2) {
//		System.out.println("��ġ2 ��ư");
//		//new GoogleMap.Main();
//	}
		else if (e.getSource() == Papago) {
			System.out.println("������ ��ư");
			Papago frame = new Papago();
			frame.setVisible(true);
		} else if (e.getSource() == Filebtn) {
			System.out.println("���� ���� ��ư");
			String Image_path;
			FileDialog fdlg = new FileDialog(new JFrame(), "file open", FileDialog.LOAD); // ���ϴ��̾�α׸� ����Ͽ� ������ ����

			fdlg.setVisible(true); // �����ֱ� Ȱ��ȭ

			if (fdlg.getFile() == null) { // ������ null�̸�
				return;
			} else {
				ImageIcon originalIcon = new ImageIcon(fdlg.getDirectory() + fdlg.getFile());
				Image originalImage = originalIcon.getImage();
				Image resizeImage = originalImage.getScaledInstance(153, 148, Image.SCALE_SMOOTH); // �̹��� ����������
				ImageIcon resizeIcon = new ImageIcon(resizeImage);
				Image_path = fdlg.getDirectory() + fdlg.getFile();
				imgsend = Image_path; // ������ �ؽ�Ʈ�ʵ�� �޾ƿͼ� �󺧿� ����
				send_message("Chatting/" + My_Room + "/" + imgsend); // �̹��� ����
			}
		}
	}

	public static String checkIDImage(String id, int a) {// ���Ը��� ���̵� �Է� �� ���̵� �ش��ϴ� �����ʻ����� ���
		id = id;
		String str = null;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver"); // �˾Ƽ� ����..conn����
			String url = "jdbc:oracle:thin:@localhost:1521:orcl"; //
			// ?useSSL=false -> SSL���ἳ�� ���� �ذ�
			info = new Properties();
			info.setProperty("user", "root"); // root ��������
			info.setProperty("password", "0000"); // pw�� 0000
			con = DriverManager.getConnection(url, info); // ������ ������ �������ִ� ����̹��Ŵ����� ������
			st1 = con.createStatement(); // db����

			sql = "select * from tb_member where id='" + id + "'";
			rs = st1.executeQuery(sql); // �Է�id�� ���̺� ����� id�� ��
			// sql = "select * from (select * from joindb where id='" + id + "')";
			while (rs.next() == true) { // ��������
				str = rs.getString(a); // ������ �α��� ����
			}
		} catch (Exception ee) { // �ͼ���ó��
			System.out.println("��������");
			ee.printStackTrace();
		}
		return str;
	}

	public static ImageIcon ResizeImage(String ImagePath, int a, int b)// ũ�⸦ ���� �Է��� �ٶ�
	{
		ImageIcon MyImage = new ImageIcon(ImagePath);
		Image img = MyImage.getImage();
		Image newImg = img.getScaledInstance(a, b, Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(newImg);
		return image;
	}
	public static void MusicChange(int a) {		// ���ǳѱ�� �Լ�
		clip2.close(); 	// ���� ����
		if(a == 1) {
			System.out.println("1��Ʈ��");
			Music_title.setText("�ູ�� ����");
			try {
				File file = new File("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\music\\HappyTown.wav"); // 1�� Ʈ��
				AudioInputStream stream = AudioSystem.getAudioInputStream(file);
				clip2 = AudioSystem.getClip();
				clip2.open(stream);
				clip2.start();
				if (true) clip2.loop(-1);	// ���� ���
			} catch (Exception f) {

				f.printStackTrace();
				}
		}
		else if (a== 2) {
			System.out.println("2��Ʈ��");
			Music_title.setText("��ũŸ��");
			try {
				File file = new File("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\music\\��ũŸ��.wav"); // 2�� Ʈ��
				AudioInputStream stream = AudioSystem.getAudioInputStream(file);
				clip2 = AudioSystem.getClip();
				clip2.open(stream);
				clip2.start();
				if (true) clip2.loop(-1);	// ���� ���

			} catch (Exception f) {

				f.printStackTrace();
				}
		}
		else if (a == 3) {
			System.out.println("3��Ʈ��");
			Music_title.setText("������ ���±�");
			try {
				File file = new File("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\music\\�����Ͼ�_������.wav"); // 3�� Ʈ��
				AudioInputStream stream = AudioSystem.getAudioInputStream(file);
				clip2 = AudioSystem.getClip();
				clip2.open(stream);
				clip2.start();
				if (true) clip2.loop(-1);	// ���� ���

			} catch (Exception f) {

				f.printStackTrace();
				}
		}
		else if (a == 4) {
			System.out.println("4��Ʈ��");
			Music_title.setText("��׽ý�");
			try {
				File file = new File("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\music\\��׽ý�.wav"); // 4�� Ʈ��
				AudioInputStream stream = AudioSystem.getAudioInputStream(file);
				clip2 = AudioSystem.getClip();
				clip2.open(stream);
				clip2.start();
				if (true) clip2.loop(-1);	// ���� ���
				
			} catch (Exception f) {

				f.printStackTrace();
				}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

		if (e.getKeyCode() == 10) // Enter Key�� KeyCode�� 10���̴�.
		{
			send_message("Chatting/" + My_Room + "/" + message_tf.getText().trim());
			message_tf.setText(""); // ��ȭ�濡�� ��ȭ�޽��� �������� ä�ó��� �Է�â���� ������ �������.
			message_tf.requestFocus();
			try {
			File file = new File("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\typewriter-key-1.wav"); // Ÿ��ġ�� �Ҹ�
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
	public void keyReleased(KeyEvent e) { // Enter Key�� ������ ä�� �����ϰ� ��

	
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
