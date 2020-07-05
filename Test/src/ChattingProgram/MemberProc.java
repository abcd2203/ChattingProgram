package ChattingProgram;

import java.awt.Color;
import java.awt.Component;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import CaptchaTest3.APIresult;

public class MemberProc extends JFrame implements ActionListener, KeyListener {

	private JPanel contentPane;
	private static JPanel contentPane_1;
	static JTextField tfId;
	static JTextField tfName;
	public static JTextField tfprofilePath; // 프로필 사진 경로
	static JTextField tfTel1, tfTel2, tfTel3; // 전화
	static JTextField tfchatname; // 대화명
	static JPasswordField tfPwd; // 비밀번호
	static JPasswordField tfPwd2;

	MemberProc(JPasswordField tfPwd, JPasswordField tfPwd2) {
		this.tfPwd = tfPwd;
		this.tfPwd2 = tfPwd2;
	}

	public static JTextField tfAddr, tfpost1;// 주소, 우편번호
	static JRadioButton rbMan; // 남, 여
	static JRadioButton rbWoman;
	public static JLabel profile = new JLabel(""); // 프로필 사진
	ImageIcon image;
	JButton btnPhoto, btnId, btnPost, btnInsert, btnCancel, btnUpdate, btnDelete; // 아이디 중복체크, 비밀번호 확인, 우편번호 검색, 가입, 취소,
																					// 수정 , 삭제 버튼

	Member_List mList;
	private JFrame Main_GUI = new JFrame();

	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JLabel photo;
	private JTextField imagepath_TF;
	private JButton btnDel;
	private JLabel pwdCh;
	private JLabel lblNewLabel;
	private JFileChooser chooser; // J파일추서
	private static JTextField tfAddr2;
	protected int count = 0;
	private JLabel Checkpwd;
	private JLabel Checkpwd2;
	private JLabel Checkpwd3;
	private JLabel pwdCh1;
	public static boolean result = false;

	public MemberProc() { // 가입용 생성자

		createUI(); // UI작성해주는 메소드
		btnUpdate.setEnabled(false);
		btnUpdate.setVisible(true);
		btnDelete.setEnabled(false);
		btnDelete.setVisible(false);

	}// 생성자
	
	public MemberProc(String id, String ProfilePath) {		// 회원이 직접 수정용 생성자
		createUI(); // UI작성해주는 메소드
		btnInsert.setEnabled(false);
		btnUpdate.setEnabled(true);
		btnUpdate.setVisible(true);
		btnInsert.setVisible(false);
		btnDelete.setVisible(false);
		btnId.setVisible(false);
		
		
		MemberDAO dao = new MemberDAO();
		MemberDTO vMem = dao.getMemberDTO(id);
		photo.setIcon(new ImageIcon(ProfilePath));
		viewData(vMem);
	}

	public MemberProc(Member_List mList) { // 가입용 생성자

		createUI(); // UI작성해주는 메소드
		btnUpdate.setEnabled(false);
		btnUpdate.setVisible(false);
		btnDelete.setEnabled(false);
		btnDelete.setVisible(false);
		this.mList = mList;

	}// 생성자

	public MemberProc(String id, Member_List mList) { // 수정/삭제용 생성자
		createUI();
		btnInsert.setEnabled(false);
		btnUpdate.setEnabled(true);
		btnUpdate.setVisible(true);
		btnInsert.setVisible(false);
		btnId.setVisible(false);

		this.mList = mList;

		System.out.println("id=" + id);

		MemberDAO dao = new MemberDAO();
		MemberDTO vMem = dao.getMemberDTO(id);
		viewData(vMem);

	}

	public MemberProc(String id, String ProfilePath, Member_List mList) { // 수정/삭제용 생성자
		createUI();
		btnInsert.setEnabled(false);
		btnUpdate.setEnabled(true);
		btnUpdate.setVisible(true);
		btnInsert.setVisible(false);

		this.mList = mList;

		System.out.println("id=" + id);

		MemberDAO dao = new MemberDAO();
		MemberDTO vMem = dao.getMemberDTO(id);
		photo.setIcon(new ImageIcon(ProfilePath));
		viewData(vMem);

	}

	// MemberDTO 의 회원 정보를 가지고 화면에 셋팅해주는 메소드
	private void viewData(MemberDTO vMem) {

		String id = vMem.getId();
		String pwd = vMem.getPwd();
		String name = vMem.getName();
		String tel = vMem.getTel();
		String addr = vMem.getAddr1();
		String post = vMem.getPost();
		String chatname = vMem.getChatname();
		String gender = vMem.getGender();
		String profilepath = vMem.getProfilePath();

		// 화면에 세팅
		tfId.setText(id);
		tfId.setEditable(false); // 편집 안되게
		tfPwd.setText(""); // 비밀번호는 안보여준다.
		tfName.setText(name);
		String[] tels = tel.split("-");
		tfTel1.setText(tels[0]);
		tfTel2.setText(tels[1]);
		tfTel3.setText(tels[2]);
		tfAddr.setText(addr);
		tfpost1.setText(post.substring(0, 7));

		tfchatname.setText(chatname);

		if (gender.equals("M")) {
			rbMan.setSelected(true);
		} else if (gender.equals("W")) {
			rbWoman.setSelected(true);
		}

		tfprofilePath.setText(profilepath);

	}// viewData

	private void createUI() {
		this.setTitle("회원가입");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 500);
		contentPane_1 = new JPanel();
		contentPane_1.setBackground(Color.WHITE);
		contentPane_1.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane_1);
		contentPane_1.setLayout(null);

		pwdCh1 = new JLabel("");
		pwdCh1.setFont(new Font("굴림", Font.BOLD, 12));
		pwdCh1.setForeground(Color.RED);
		pwdCh1.setBounds(63, 175, 215, 25);
		contentPane_1.add(pwdCh1);

		pwdCh = new JLabel("");
		pwdCh.setFont(new Font("굴림", Font.BOLD, 12));
		pwdCh.setForeground(Color.RED);
		pwdCh.setBounds(63, 218, 215, 25);
		contentPane_1.add(pwdCh);

		Checkpwd = new JLabel("\u221A");
		Checkpwd.setForeground(Color.GREEN);
		Checkpwd.setFont(new Font("한컴 윤고딕 250", Font.PLAIN, 18));
		Checkpwd.setBounds(8, 152, 39, 25);
		Checkpwd.setVisible(false);
		contentPane_1.add(Checkpwd);

		Checkpwd2 = new JLabel("\u221A");
		Checkpwd2.setForeground(Color.GREEN);
		Checkpwd2.setFont(new Font("한컴 윤고딕 250", Font.PLAIN, 18));
		Checkpwd2.setBounds(8, 121, 39, 25);
		Checkpwd2.setVisible(false);
		contentPane_1.add(Checkpwd2);

		Checkpwd3 = new JLabel("\u221A");
		Checkpwd3.setForeground(Color.GREEN);
		Checkpwd3.setFont(new Font("한컴 윤고딕 250", Font.PLAIN, 18));
		Checkpwd3.setBounds(8, 195, 39, 25);
		Checkpwd3.setVisible(false);
		contentPane_1.add(Checkpwd3);

		// 회원가입
		JLabel label = new JLabel("회원가입");
		label.setForeground(Color.WHITE);
		label.setFont(new Font("함초롬돋움", Font.BOLD, 24));
		label.setBounds(199, 25, 106, 29);
		contentPane_1.add(label);

		// 이름
		JLabel bName = new JLabel("이름");
		bName.setFont(new Font("함초롬돋움", Font.BOLD, 13));
		bName.setForeground(Color.WHITE);
		bName.setBounds(31, 96, 57, 15);
		contentPane_1.add(bName);

		tfName = new JTextField();
		tfName.setBounds(100, 93, 116, 21);
		contentPane_1.add(tfName);
		tfName.setColumns(10);

		// 아이디
		JLabel bId = new JLabel("ID");
		bId.setForeground(Color.WHITE);
		bId.setFont(new Font("함초롬돋움", Font.BOLD, 13));
		bId.setBounds(31, 127, 57, 15);
		contentPane_1.add(bId);

		tfId = new JTextField();
		tfId.setBounds(100, 124, 116, 21);
		contentPane_1.add(tfId);
		tfId.setColumns(10);

		// 비밀번호
		JLabel bPwd = new JLabel("비밀번호");
		bPwd.setForeground(Color.WHITE);
		bPwd.setFont(new Font("함초롬돋움", Font.BOLD, 13));
		bPwd.setBounds(31, 155, 57, 15);
		contentPane_1.add(bPwd);

		tfPwd = new JPasswordField();
		tfPwd.setBounds(100, 155, 116, 21);
		contentPane_1.add(tfPwd);
		tfPwd.setColumns(10);

		// 전화
		JLabel bTel = new JLabel("전화번호");
		bTel.setBounds(31, 384, 57, 15);
		bTel.setForeground(Color.WHITE);
		bTel.setFont(new Font("함초롬돋움", Font.BOLD, 13));
		contentPane_1.add(bTel);

		/*
		 * String a [] =
		 * {"010","02","031","032","033","041","042","043","044","051","052","053","054"
		 * ,"055","061","062","063","064"}; JComboBox tfTel1 = new JComboBox(a);
		 * tfTel1.setBounds(100, 323, 57, 21); contentPane.add(tfTel1);
		 */

		JLabel _1 = new JLabel("-");
		_1.setBounds(159, 385, 30, 15);
		contentPane_1.add(_1);

		JLabel _2 = new JLabel("-");
		_2.setBounds(228, 385, 39, 15);
		contentPane_1.add(_2);

		tfTel1 = new JTextField();
		tfTel1.setBounds(100, 382, 57, 21);
		contentPane_1.add(tfTel1);
		tfTel1.setColumns(10);

		tfTel2 = new JTextField();
		tfTel2.setBounds(168, 382, 57, 21);
		contentPane_1.add(tfTel2);
		tfTel2.setColumns(10);

		tfTel3 = new JTextField();
		tfTel3.setBounds(238, 382, 57, 21);
		contentPane_1.add(tfTel3);
		tfTel3.setColumns(10);

		// 주소
		JLabel bAddr = new JLabel("주소");

		tfAddr = new JTextField();
		tfAddr.setBounds(100, 333, 236, 21);
		contentPane_1.add(tfAddr);
		tfAddr.setColumns(10);

		JLabel Addr = new JLabel("상세주소");
		Addr.setBounds(31, 336, 57, 15);
		Addr.setForeground(Color.WHITE);
		Addr.setFont(new Font("함초롬돋움", Font.BOLD, 13));
		contentPane_1.add(Addr);

		// 우펴번호
		JLabel bpost = new JLabel("우편번호");
		bpost.setBounds(31, 308, 57, 15);
		bpost.setForeground(Color.WHITE);
		bpost.setFont(new Font("함초롬돋움", Font.BOLD, 13));
		contentPane_1.add(bpost);

		JPanel ppost = new JPanel();
		tfpost1 = new JTextField();
		tfpost1.setBounds(100, 306, 116, 21);
		contentPane_1.add(tfpost1);
		tfpost1.setColumns(10);

		// 별명
		JLabel bChattingName = new JLabel("별명");
		bChattingName.setBounds(31, 247, 57, 15);
		bChattingName.setForeground(Color.WHITE);
		bChattingName.setFont(new Font("함초롬돋움", Font.BOLD, 13));
		contentPane_1.add(bChattingName);

		tfchatname = new JTextField();
		tfchatname.setBounds(100, 245, 116, 21);
		contentPane_1.add(tfchatname);
		tfchatname.setColumns(10);

		// 성별
		JLabel bGender = new JLabel("성별");
		bGender.setForeground(Color.WHITE);
		bGender.setFont(new Font("함초롬돋움", Font.BOLD, 13));
		bGender.setBounds(31, 272, 57, 15);
		contentPane_1.add(bGender);

		rbMan = new JRadioButton("남", true);
		rbMan.setBackground(new Color(138, 102, 68));
		rbMan.setBounds(100, 272, 57, 23);
		contentPane_1.add(rbMan);

		rbWoman = new JRadioButton("여", true);
		rbWoman.setBackground(new Color(138, 102, 68));
		rbWoman.setBounds(159, 272, 57, 23);
		contentPane_1.add(rbWoman);

		ButtonGroup group = new ButtonGroup();
		group.add(rbMan);
		group.add(rbWoman);

		/*
		 * JLabel bBirth = new JLabel("생년월일"); bBirth.setBounds(31, 371, 57, 15);
		 * contentPane.add(bBirth);
		 * 
		 * pBirth = new JTextField(); pBirth.setBounds(100, 368, 116, 21);
		 * contentPane.add(pBirth); pBirth.setColumns(10);
		 */ // 생년월일

		// 사진 경로
		JLabel bProfilePath = new JLabel("사진 경로");
		tfprofilePath = new JTextField(40);

		JLabel picture = new JLabel("프로필 사진");

		ImageIcon image = new ImageIcon("C:\\카톡 프사.gif"); // 이미지 경로
		JLabel profile = new JLabel(" ", image, JLabel.CENTER); // 행 : 열;
		profile.setVisible(true);

		// 버튼

		btnId = new JButton("중복");
		btnId.setBounds(229, 123, 66, 23);
		contentPane_1.add(btnId);

		btnPost = new JButton("검색");
		btnPost.setBounds(228, 305, 66, 23);
		contentPane_1.add(btnPost);

		btnPhoto = new JButton("등록");
		btnPhoto.setFont(new Font("함초롬돋움", Font.BOLD, 12));
		btnPhoto.setBounds(310, 230, 109, 23);
		btnPhoto.setBounds(310, 241, 71, 29);
		contentPane_1.add(btnPhoto);

		btnInsert = new JButton("가입");
		btnInsert.setBounds(133, 428, 97, 23);
		contentPane_1.add(btnInsert);

		btnUpdate = new JButton("수정");
		btnUpdate.setBounds(133, 428, 97, 23);
		contentPane_1.add(btnUpdate);

		btnDelete = new JButton("회원삭제");
		btnDelete.setBounds(367, 428, 97, 23);
		contentPane_1.add(btnDelete);

		btnCancel = new JButton("취소");
		btnCancel.setBounds(250, 428, 97, 23);
		contentPane_1.add(btnCancel);

		btnDel = new JButton("초기화");
		btnDel.setFont(new Font("함초롬돋움", Font.BOLD, 12));
		btnDel.setBounds(393, 242, 71, 26);
		contentPane_1.add(btnDel);

		photo = new JLabel(" ");
		photo.setIcon(new ImageIcon(
				"C:\\Users\\36175\\Desktop\\\uC774\uBAA8\uD2F0\uCF58\\\uAE30\uBCF8\uD504\uB85C\uD544.jpg"));
		photo.setBackground(Color.YELLOW);
		photo.setBounds(311, 72, 153, 148);
		contentPane_1.add(photo);

		imagepath_TF = new JTextField();
		imagepath_TF.setBounds(310, 195, 116, 21);
		contentPane_1.add(imagepath_TF);
		imagepath_TF.setColumns(10);
		imagepath_TF.setVisible(false);

		JLabel pwdRe = new JLabel("재확인");
		pwdRe.setForeground(Color.WHITE);
		pwdRe.setFont(new Font("함초롬돋움", Font.BOLD, 13));
		pwdRe.setBounds(31, 200, 87, 15);
		contentPane_1.add(pwdRe);

		tfPwd2 = new JPasswordField();
		tfPwd2.setBounds(100, 198, 116, 21);
		contentPane_1.add(tfPwd2);
		tfPwd2.setColumns(10);

		tfAddr2 = new JTextField();
		tfAddr2.setBounds(100, 358, 195, 21);
		contentPane_1.add(tfAddr2);
		tfAddr2.setColumns(10);

		lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\36175\\Desktop\\\uC774\uBAA8\uD2F0\uCF58\\\uB9C8\uD06C2.jpg"));
		lblNewLabel.setBounds(0, 0, 494, 471);
		contentPane_1.add(lblNewLabel);

		// 버튼에 감지기를 붙이자
		btnId.addActionListener(this);
		btnPost.addActionListener(this);
		btnPhoto.addActionListener(this);
		btnInsert.addActionListener(this);
		btnUpdate.addActionListener(this);
		btnCancel.addActionListener(this);
		btnDelete.addActionListener(this);
		btnDel.addActionListener(this);

		tfPwd.addKeyListener(this);
		tfPwd2.addKeyListener(this); // 키리스너 추가

		setResizable(false);
		this.setSize(500, 500);
		setVisible(true);
		// setDefaultCloseOperation(EXIT_ON_CLOSE); //System.exit(0) //프로그램종료
		setDefaultCloseOperation(DISPOSE_ON_CLOSE); // dispose(); //현재창만 닫는다.

	}// createUI

	// 그리드백레이아웃에 붙이는 메소드

	public static void main(String[] args) {

		new MemberProc();
		String s = null;
		System.out.println((s == null) ? "0" : s.length());

	}

	public static int passwrodvalidate(String pwd) { // 비밀번호 규칙 지정
		String Password_PATTERN_A = "^(?=.*[a-zA-Z]+).{10,20}$";
		String Password_PATTERN_B = "^(?=.*[!@#$%^&+=-]+).{10,20}$";
		String Password_PATTERN_C = "^(?=.*[0-9]+).{10,20}$";

		Pattern pattern_a = Pattern.compile(Password_PATTERN_A);
		Pattern pattern_b = Pattern.compile(Password_PATTERN_B);
		Pattern pattern_c = Pattern.compile(Password_PATTERN_C);

		Matcher matcher_a = pattern_a.matcher(pwd);
		Matcher matcher_b = pattern_b.matcher(pwd);
		Matcher matcher_c = pattern_c.matcher(pwd);

		int RegularCnt = ((matcher_a.matches() == true) ? 1 : 0) + ((matcher_b.matches() == true) ? 1 : 0)
				+ ((matcher_c.matches() == true) ? 1 : 0);
		return RegularCnt;
	}

	public ImageIcon ResizeImage(String ImagePath) {
		// import.java.awt.Image;
		ImageIcon MyImage = new ImageIcon(ImagePath);
		Image img = MyImage.getImage(); // 이미지 얻기
		Image newImg = img.getScaledInstance(profile.getWidth(), profile.getHeight(), Image.SCALE_SMOOTH);
		// 사진의 사이즈를 라벨에 맞게
		ImageIcon image = new ImageIcon(newImg);
		return image; // 이미지 반환
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnId) { // ID 중복확인 버튼 누를 시
			String id = tfId.getText().trim();
			java.sql.Connection conn = null;
			java.sql.Statement stmt = null;

			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "root", "0000");
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("Select * from TB_MEMBER");

				if (id.equals("")) {
					JOptionPane.showMessageDialog(null, "ID 입력칸이 비어있습니다.", "INFORMAION_MESSAGE",
							JOptionPane.ERROR_MESSAGE);
					Checkpwd2.setVisible(false);
				}

				else if (id.length() < 6) {
					JOptionPane.showMessageDialog(null, "아이디를 6글자 이상 입력해주세요.", "INFORMATION_MESSAGE",
							JOptionPane.ERROR_MESSAGE);
					Checkpwd2.setVisible(false);
				}

				else {
					while (rs.next()) {
						if (id.equals(rs.getString("id"))) {
							JOptionPane.showMessageDialog(null, "이미 사용중인 ID입니다.", "알림", JOptionPane.ERROR_MESSAGE);
							tfId.requestFocus();
							Checkpwd2.setVisible(false);
							return;
						}
					}
					int y = JOptionPane.showConfirmDialog(null, "사용 가능한 ID입니다.\n사용하시겠습니까?", "알림",
							JOptionPane.YES_NO_OPTION);
					if (y == JOptionPane.OK_OPTION) {
						tfId.requestFocus();
						boolean ID_Check = true;
						tfId.setEnabled(false); // tfId 텍스트필드 비활성화
						Checkpwd2.setVisible(true); // 체크 활성화
					}
					return;
				}
			} catch (ClassNotFoundException cnfe) {
				System.out.println("해당 클래스를 찾을 수 없습니다." + cnfe.getMessage());
			}

			catch (SQLException se) {

			}
		}

		else if (e.getSource() == btnPost) { // 우편번호 검색 버튼 누를 시
			System.out.println("우편번호 검색 버튼 클릭");
			zipSearch frame = new zipSearch(); // 우편번호 검색창 보임
			frame.setVisible(true);
		}

		else if (e.getSource() == btnDel) {
			System.out.println("사진 삭제 버튼 클릭");
			ImageIcon img = new ImageIcon("C:\\Users\\36175\\Desktop\\이모티콘\\기본프로필.jpg");
			photo.setIcon(img);
			tfprofilePath.setText(null);

		}

		else if (e.getSource() == btnPhoto) { // 사진 검색 버튼 누를 시
			System.out.println("사진 검색 버튼 클릭");

			String Image_path;
			photo.setText(""); // photo(라벨) 공백
			FileDialog fdlg = new FileDialog(new JFrame(), "file open", FileDialog.LOAD); // 파일다이얼로그를 사용하여 파일을 열기
			/*
			 * FileNameExtensionFilter filter=new
			 * FileNameExtensionFilter("*.Imagers","jpg","gif","png");
			 * chooser.setFileFilter(filter); int ret = chooser.showOpenDialog(null); if(ret
			 * != JFileChooser.APPROVE_OPTION){ JOptionPane.showMessageDialog(null,
			 * "파일을 선택하세요.","경고",JOptionPane.WARNING_MESSAGE); }
			 */

			fdlg.setVisible(true); // 보여주기 활성화

			if (fdlg.getFile() == null) { // 파일이 null이면
				return;
			} else {
				ImageIcon originalIcon = new ImageIcon(fdlg.getDirectory() + fdlg.getFile());
				Image originalImage = originalIcon.getImage();
				Image resizeImage = originalImage.getScaledInstance(153, 148, Image.SCALE_SMOOTH);
				ImageIcon resizeIcon = new ImageIcon(resizeImage);
				Image_path = fdlg.getDirectory() + fdlg.getFile();
				imagepath_TF.setText(Image_path);
				tfprofilePath.setText(Image_path); // 사진을 텍스트필드로 받아와서 라벨에 저장
				photo.setIcon(resizeIcon);
			}
		}

		/*
		 * FileInputStream fis = null; Connection conn = null; // 데이터 연결하기 위함
		 * PreparedStatement pstmt = null; // 데이터를 읽어오기 위함
		 * 
		 * FileDialog dlg = new FileDialog(this, "프로필 사진 지정하기", FileDialog.LOAD);
		 * dlg.show(); dlg.setVisible(true); String dir = dlg.getDirectory(); String
		 * file = dlg.getFile();
		 * 
		 * if(file==null) return;
		 */

		// System.out.println("dir:" + dir);
		// System.out.println("file:" + file);

		/*
		 * MemberProc.tfprofilePath.setText(dir+file);
		 * profile.setIcon(ResizeImage(MemberProc.tfprofilePath.getText()));
		 */

		else if (e.getSource() == MemberProc.tfprofilePath) {
			if (MemberProc.tfprofilePath.getText().equals("")) // 입력안했으면 종료
				return;
			profile.repaint();
		}

		else if (e.getSource() == btnInsert) { // 가입 버튼 누를 시

			if (tfId.getText().length() == 0) // id입력란에 아무 내용 입력 안 하고 로그인 시도할 때
			{
				tfId.setText("ID를 입력해주세요");
				tfId.requestFocus();
				return;
			}
			if (tfPwd.getText().length() == 0) // 비밀번호입력란에 아무 내용 입력 안 하고 로그인 시도할 때
			{

				tfPwd.setText("비밀번호를 입력해주세요");
				tfPwd.requestFocus();
				return;
			} else if (imagepath_TF.getText().length() == 0) { // 사진을 안넣었을 때
				JOptionPane.showMessageDialog(this, "사진을 넣어주세요!");
				return;
			} else { // 다되면
				int x = JOptionPane.showConfirmDialog(null, "가입하시겠습니까?", "알림", JOptionPane.YES_NO_OPTION);
				if (x == JOptionPane.OK_OPTION) {
					APIresult.main(null); // 캡차 실행
					dispose();
				}

			}

//				insertMember();
//				System.out.println("insertMember() 호출 종료");

		} else if (e.getSource() == btnCancel) { // 취소 버튼 누를 시
			this.dispose(); // 창닫기 (현재창만 닫힘)
			// system.exit(0)=> 내가 띄운 모든 창이 다 닫힘
		} else if (e.getSource() == btnUpdate) { // 수정 버튼 누를 시
			updateMember();
		} else if (e.getSource() == btnDelete) { // 탈퇴 버튼 누를 시

			int x = JOptionPane.showConfirmDialog(this, "정말 삭제하시겠습니까?", "삭제", JOptionPane.YES_NO_OPTION);

			if (x == JOptionPane.OK_OPTION) {
				deleteMember();
			} else {
				JOptionPane.showMessageDialog(this, "삭제를 취소하였습니다.");
			}
		}

		// jTable내용 갱신 메소드 호출
		mList.jTableRefresh();
	}

	public static void messageBox(Object obj, String message) {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog((Component) obj, message);
	}

	public void deleteMember() {
		String id = tfId.getText();
		String pwd = tfPwd.getText();
		if (pwd.length() == 0) { // 길이가 0이면

			JOptionPane.showMessageDialog(this, "비밀번호를 꼭 입력하세요!");
			return; // 메소드 끝
		}
		// System.out.println(mList);
		MemberDAO dao = new MemberDAO();
		boolean ok = dao.deleteMember(id, pwd);

		if (ok) {
			JOptionPane.showMessageDialog(this, "삭제완료");
			dispose();

		} else {
			JOptionPane.showMessageDialog(this, "삭제실패");

		}

	}// deleteMember

	private void updateMember() {

		// 1. 화면의 정보를 얻는다.
		MemberDTO dto = getViewData();
		// 2. 그정보로 DB를 수정
		MemberDAO dao = new MemberDAO();

		// photo.setIcon(dto.profilepath);

		boolean ok = dao.updateMember(dto);

		if (!ok) {
			JOptionPane.showMessageDialog(this, "수정되었습니다.");
			this.dispose();
		} else {
			JOptionPane.showMessageDialog(this, "수정실패 : 값을 확인하세요");
		}
	}

	public static void insertMember() {

		// 화면에서 사용자가 입력한 내용을 얻는다.
		MemberDTO dto = getViewData();
		MemberDAO dao = new MemberDAO();
		boolean ok = dao.insertMember(dto);

		if (ok) { // 가입 완료시

			JOptionPane.showMessageDialog(null, "가입이 완료되었습니다.");
			// dispose();
			// Main_GUI.setVisible(false);
			// System.exit(0);

		} else {
			JOptionPane.showMessageDialog(null, "가입이 정상적으로 처리되지 않았습니다.");
		}
	}
// insertMember

	private void searchPwd() {
	}

	public static MemberDTO getViewData() {

		// 화면에서 사용자가 입력한 내용을 얻는다.
		MemberDTO dto = new MemberDTO();
		String id = tfId.getText();
		String pwd = tfPwd.getText();
		String name = tfName.getText();
		String tel1 = tfTel1.getText();
		String tel2 = tfTel2.getText();
		String tel3 = tfTel3.getText();
		String tel = tel1 + "-" + tel2 + "-" + tel3;
		String addr1 = tfAddr.getText();
		String addr2 = tfAddr2.getText();
		String post = tfpost1.getText();
		String chatname = tfchatname.getText();
		String gender = "";
		if (rbMan.isSelected()) {
			gender = "남자";
		} else if (rbWoman.isSelected()) {
			gender = "여자";
		}

		String profilepath = tfprofilePath.getText();

		// dto에 담는다.
		dto.setId(id);
		dto.setPwd(pwd);
		dto.setName(name);
		dto.setTel(tel);
		dto.setAddr1(addr1);
		dto.setAddr2(addr2);
		dto.setPost(post);
		dto.setChatname(chatname);
		dto.setGender(gender);
		dto.setProfilePath(profilepath);

		return dto;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {

		String a, b;
		a = tfPwd.getText().trim();
		b = tfPwd2.getText().trim();
		if (a.equals("") || b.equals("")) {
			pwdCh.setForeground(Color.RED);
			pwdCh.setText("비밀번호를 입력해주세요!");
			Checkpwd3.setVisible(false);
		}

		/*
		 * else if (a.length() < 10 || a.length() > 20) {
		 * pwdCh.setForeground(Color.RED); pwdCh.setText("10~20자리의 비밀번호를 입력해주세요!");
		 * Checkpwd3.setVisible(false); }
		 */

		else if (a.equals(b)) {

			// JOptionPane.showMessageDialog(null, "일치합니다.", "알림",
			// JOptionPane.INFORMATION_MESSAGE);
			pwdCh.setForeground(new Color(0, 255, 0));
			pwdCh.setText("두 비밀번호가 일치합니다.");
			Checkpwd3.setVisible(true); // 체크 활성화
		} else {
			// JOptionPane.showMessageDialog(null, "일치 하지 않습니다!", "경고",
			// JOptionPane.WARNING_MESSAGE);
			pwdCh.setForeground(Color.RED);
			pwdCh.setText("두 비밀번호가 일치하지않습니다!");
			Checkpwd3.setVisible(false);
		}
		// 비밀번호 체크
		String pwd = tfPwd.getText();

		if (pwd.length() < 10 || pwd.length() > 20) {
			// JOptionPane.showMessageDialog(null, "문자, 숫자, 특수기호 중 3종류 이상 조합으로 10자리에서 20자리
			// 사이로 입력해주세요.", "알림", JOptionPane.ERROR_MESSAGE);
			pwdCh1.setForeground(Color.RED);
			pwdCh1.setText("문자,숫자,특수기호 조합 10~20자리");
			Checkpwd.setVisible(false);
			return;
		} // 비밀번호 확인

		int regularCnt = passwrodvalidate(pwd);

		if (regularCnt == 3) {
			if (pwd.length() < 10 || pwd.length() > 20) {
				// JOptionPane.showMessageDialog(null, "문자, 숫자, 특수기호 중 3종류 이상 조합으로 10자리에서 20자리
				// 사이로 입력해주세요.", "알림", JOptionPane.ERROR_MESSAGE);
				pwdCh1.setForeground(Color.RED);
				pwdCh1.setText("문자,숫자,특수기호 조합 10~20자리");
				Checkpwd.setVisible(false);
				return;
			}
		} else {
			// JOptionPane.showMessageDialog(null, "문자, 숫자, 특수기호 중 3종류 이상 조합으로 10자리에서 20자리
			// 사이로 입력해주세요.", "알림", JOptionPane.ERROR_MESSAGE);
			pwdCh1.setForeground(Color.RED);
			pwdCh1.setText("문자,숫자,특수기호 조합 10~20자리");
			Checkpwd.setVisible(false);
			return;
		}

		// JOptionPane.showMessageDialog(null, "사용가능한 비밀번호 입니다.", "알림",
		// JOptionPane.INFORMATION_MESSAGE);
		pwdCh1.setForeground(new Color(0, 255, 0));
		pwdCh1.setText("사용가능한 비밀번호입니다.");
		Checkpwd.setVisible(true);
		// count = 1; //체크모양 활성화하기 위한 작업
	}
	/*
	 * String pwd1 = String.valueOf(tfPwd.getPassword()); String pwd2 =
	 * String.valueOf(tfPwd2.getPassword()); if (!pwd1.equals(pwd2)) {
	 * pwdCh.setText("암호 불일치"); } else { pwdCh.setText("암호 일치"); } if (e.getSource()
	 * == tfPwd) { if (pwd1.length() < 4 || pwd1.length() > 16) {
	 * pwdCh.setText("암호 : 4~16자"); } else { pwdCh.setText(""); } } }
	 */

	@Override
	public void keyTyped(KeyEvent e) {

		// TODO Auto-generated method stub
	}
}// end