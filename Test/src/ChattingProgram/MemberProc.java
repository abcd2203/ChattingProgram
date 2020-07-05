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
	public static JTextField tfprofilePath; // ������ ���� ���
	static JTextField tfTel1, tfTel2, tfTel3; // ��ȭ
	static JTextField tfchatname; // ��ȭ��
	static JPasswordField tfPwd; // ��й�ȣ
	static JPasswordField tfPwd2;

	MemberProc(JPasswordField tfPwd, JPasswordField tfPwd2) {
		this.tfPwd = tfPwd;
		this.tfPwd2 = tfPwd2;
	}

	public static JTextField tfAddr, tfpost1;// �ּ�, �����ȣ
	static JRadioButton rbMan; // ��, ��
	static JRadioButton rbWoman;
	public static JLabel profile = new JLabel(""); // ������ ����
	ImageIcon image;
	JButton btnPhoto, btnId, btnPost, btnInsert, btnCancel, btnUpdate, btnDelete; // ���̵� �ߺ�üũ, ��й�ȣ Ȯ��, �����ȣ �˻�, ����, ���,
																					// ���� , ���� ��ư

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
	private JFileChooser chooser; // J�����߼�
	private static JTextField tfAddr2;
	protected int count = 0;
	private JLabel Checkpwd;
	private JLabel Checkpwd2;
	private JLabel Checkpwd3;
	private JLabel pwdCh1;
	public static boolean result = false;

	public MemberProc() { // ���Կ� ������

		createUI(); // UI�ۼ����ִ� �޼ҵ�
		btnUpdate.setEnabled(false);
		btnUpdate.setVisible(true);
		btnDelete.setEnabled(false);
		btnDelete.setVisible(false);

	}// ������
	
	public MemberProc(String id, String ProfilePath) {		// ȸ���� ���� ������ ������
		createUI(); // UI�ۼ����ִ� �޼ҵ�
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

	public MemberProc(Member_List mList) { // ���Կ� ������

		createUI(); // UI�ۼ����ִ� �޼ҵ�
		btnUpdate.setEnabled(false);
		btnUpdate.setVisible(false);
		btnDelete.setEnabled(false);
		btnDelete.setVisible(false);
		this.mList = mList;

	}// ������

	public MemberProc(String id, Member_List mList) { // ����/������ ������
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

	public MemberProc(String id, String ProfilePath, Member_List mList) { // ����/������ ������
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

	// MemberDTO �� ȸ�� ������ ������ ȭ�鿡 �������ִ� �޼ҵ�
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

		// ȭ�鿡 ����
		tfId.setText(id);
		tfId.setEditable(false); // ���� �ȵǰ�
		tfPwd.setText(""); // ��й�ȣ�� �Ⱥ����ش�.
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
		this.setTitle("ȸ������");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 500);
		contentPane_1 = new JPanel();
		contentPane_1.setBackground(Color.WHITE);
		contentPane_1.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane_1);
		contentPane_1.setLayout(null);

		pwdCh1 = new JLabel("");
		pwdCh1.setFont(new Font("����", Font.BOLD, 12));
		pwdCh1.setForeground(Color.RED);
		pwdCh1.setBounds(63, 175, 215, 25);
		contentPane_1.add(pwdCh1);

		pwdCh = new JLabel("");
		pwdCh.setFont(new Font("����", Font.BOLD, 12));
		pwdCh.setForeground(Color.RED);
		pwdCh.setBounds(63, 218, 215, 25);
		contentPane_1.add(pwdCh);

		Checkpwd = new JLabel("\u221A");
		Checkpwd.setForeground(Color.GREEN);
		Checkpwd.setFont(new Font("���� ����� 250", Font.PLAIN, 18));
		Checkpwd.setBounds(8, 152, 39, 25);
		Checkpwd.setVisible(false);
		contentPane_1.add(Checkpwd);

		Checkpwd2 = new JLabel("\u221A");
		Checkpwd2.setForeground(Color.GREEN);
		Checkpwd2.setFont(new Font("���� ����� 250", Font.PLAIN, 18));
		Checkpwd2.setBounds(8, 121, 39, 25);
		Checkpwd2.setVisible(false);
		contentPane_1.add(Checkpwd2);

		Checkpwd3 = new JLabel("\u221A");
		Checkpwd3.setForeground(Color.GREEN);
		Checkpwd3.setFont(new Font("���� ����� 250", Font.PLAIN, 18));
		Checkpwd3.setBounds(8, 195, 39, 25);
		Checkpwd3.setVisible(false);
		contentPane_1.add(Checkpwd3);

		// ȸ������
		JLabel label = new JLabel("ȸ������");
		label.setForeground(Color.WHITE);
		label.setFont(new Font("���ʷҵ���", Font.BOLD, 24));
		label.setBounds(199, 25, 106, 29);
		contentPane_1.add(label);

		// �̸�
		JLabel bName = new JLabel("�̸�");
		bName.setFont(new Font("���ʷҵ���", Font.BOLD, 13));
		bName.setForeground(Color.WHITE);
		bName.setBounds(31, 96, 57, 15);
		contentPane_1.add(bName);

		tfName = new JTextField();
		tfName.setBounds(100, 93, 116, 21);
		contentPane_1.add(tfName);
		tfName.setColumns(10);

		// ���̵�
		JLabel bId = new JLabel("ID");
		bId.setForeground(Color.WHITE);
		bId.setFont(new Font("���ʷҵ���", Font.BOLD, 13));
		bId.setBounds(31, 127, 57, 15);
		contentPane_1.add(bId);

		tfId = new JTextField();
		tfId.setBounds(100, 124, 116, 21);
		contentPane_1.add(tfId);
		tfId.setColumns(10);

		// ��й�ȣ
		JLabel bPwd = new JLabel("��й�ȣ");
		bPwd.setForeground(Color.WHITE);
		bPwd.setFont(new Font("���ʷҵ���", Font.BOLD, 13));
		bPwd.setBounds(31, 155, 57, 15);
		contentPane_1.add(bPwd);

		tfPwd = new JPasswordField();
		tfPwd.setBounds(100, 155, 116, 21);
		contentPane_1.add(tfPwd);
		tfPwd.setColumns(10);

		// ��ȭ
		JLabel bTel = new JLabel("��ȭ��ȣ");
		bTel.setBounds(31, 384, 57, 15);
		bTel.setForeground(Color.WHITE);
		bTel.setFont(new Font("���ʷҵ���", Font.BOLD, 13));
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

		// �ּ�
		JLabel bAddr = new JLabel("�ּ�");

		tfAddr = new JTextField();
		tfAddr.setBounds(100, 333, 236, 21);
		contentPane_1.add(tfAddr);
		tfAddr.setColumns(10);

		JLabel Addr = new JLabel("���ּ�");
		Addr.setBounds(31, 336, 57, 15);
		Addr.setForeground(Color.WHITE);
		Addr.setFont(new Font("���ʷҵ���", Font.BOLD, 13));
		contentPane_1.add(Addr);

		// �����ȣ
		JLabel bpost = new JLabel("�����ȣ");
		bpost.setBounds(31, 308, 57, 15);
		bpost.setForeground(Color.WHITE);
		bpost.setFont(new Font("���ʷҵ���", Font.BOLD, 13));
		contentPane_1.add(bpost);

		JPanel ppost = new JPanel();
		tfpost1 = new JTextField();
		tfpost1.setBounds(100, 306, 116, 21);
		contentPane_1.add(tfpost1);
		tfpost1.setColumns(10);

		// ����
		JLabel bChattingName = new JLabel("����");
		bChattingName.setBounds(31, 247, 57, 15);
		bChattingName.setForeground(Color.WHITE);
		bChattingName.setFont(new Font("���ʷҵ���", Font.BOLD, 13));
		contentPane_1.add(bChattingName);

		tfchatname = new JTextField();
		tfchatname.setBounds(100, 245, 116, 21);
		contentPane_1.add(tfchatname);
		tfchatname.setColumns(10);

		// ����
		JLabel bGender = new JLabel("����");
		bGender.setForeground(Color.WHITE);
		bGender.setFont(new Font("���ʷҵ���", Font.BOLD, 13));
		bGender.setBounds(31, 272, 57, 15);
		contentPane_1.add(bGender);

		rbMan = new JRadioButton("��", true);
		rbMan.setBackground(new Color(138, 102, 68));
		rbMan.setBounds(100, 272, 57, 23);
		contentPane_1.add(rbMan);

		rbWoman = new JRadioButton("��", true);
		rbWoman.setBackground(new Color(138, 102, 68));
		rbWoman.setBounds(159, 272, 57, 23);
		contentPane_1.add(rbWoman);

		ButtonGroup group = new ButtonGroup();
		group.add(rbMan);
		group.add(rbWoman);

		/*
		 * JLabel bBirth = new JLabel("�������"); bBirth.setBounds(31, 371, 57, 15);
		 * contentPane.add(bBirth);
		 * 
		 * pBirth = new JTextField(); pBirth.setBounds(100, 368, 116, 21);
		 * contentPane.add(pBirth); pBirth.setColumns(10);
		 */ // �������

		// ���� ���
		JLabel bProfilePath = new JLabel("���� ���");
		tfprofilePath = new JTextField(40);

		JLabel picture = new JLabel("������ ����");

		ImageIcon image = new ImageIcon("C:\\ī�� ����.gif"); // �̹��� ���
		JLabel profile = new JLabel(" ", image, JLabel.CENTER); // �� : ��;
		profile.setVisible(true);

		// ��ư

		btnId = new JButton("�ߺ�");
		btnId.setBounds(229, 123, 66, 23);
		contentPane_1.add(btnId);

		btnPost = new JButton("�˻�");
		btnPost.setBounds(228, 305, 66, 23);
		contentPane_1.add(btnPost);

		btnPhoto = new JButton("���");
		btnPhoto.setFont(new Font("���ʷҵ���", Font.BOLD, 12));
		btnPhoto.setBounds(310, 230, 109, 23);
		btnPhoto.setBounds(310, 241, 71, 29);
		contentPane_1.add(btnPhoto);

		btnInsert = new JButton("����");
		btnInsert.setBounds(133, 428, 97, 23);
		contentPane_1.add(btnInsert);

		btnUpdate = new JButton("����");
		btnUpdate.setBounds(133, 428, 97, 23);
		contentPane_1.add(btnUpdate);

		btnDelete = new JButton("ȸ������");
		btnDelete.setBounds(367, 428, 97, 23);
		contentPane_1.add(btnDelete);

		btnCancel = new JButton("���");
		btnCancel.setBounds(250, 428, 97, 23);
		contentPane_1.add(btnCancel);

		btnDel = new JButton("�ʱ�ȭ");
		btnDel.setFont(new Font("���ʷҵ���", Font.BOLD, 12));
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

		JLabel pwdRe = new JLabel("��Ȯ��");
		pwdRe.setForeground(Color.WHITE);
		pwdRe.setFont(new Font("���ʷҵ���", Font.BOLD, 13));
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

		// ��ư�� �����⸦ ������
		btnId.addActionListener(this);
		btnPost.addActionListener(this);
		btnPhoto.addActionListener(this);
		btnInsert.addActionListener(this);
		btnUpdate.addActionListener(this);
		btnCancel.addActionListener(this);
		btnDelete.addActionListener(this);
		btnDel.addActionListener(this);

		tfPwd.addKeyListener(this);
		tfPwd2.addKeyListener(this); // Ű������ �߰�

		setResizable(false);
		this.setSize(500, 500);
		setVisible(true);
		// setDefaultCloseOperation(EXIT_ON_CLOSE); //System.exit(0) //���α׷�����
		setDefaultCloseOperation(DISPOSE_ON_CLOSE); // dispose(); //����â�� �ݴ´�.

	}// createUI

	// �׸���鷹�̾ƿ��� ���̴� �޼ҵ�

	public static void main(String[] args) {

		new MemberProc();
		String s = null;
		System.out.println((s == null) ? "0" : s.length());

	}

	public static int passwrodvalidate(String pwd) { // ��й�ȣ ��Ģ ����
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
		Image img = MyImage.getImage(); // �̹��� ���
		Image newImg = img.getScaledInstance(profile.getWidth(), profile.getHeight(), Image.SCALE_SMOOTH);
		// ������ ����� �󺧿� �°�
		ImageIcon image = new ImageIcon(newImg);
		return image; // �̹��� ��ȯ
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnId) { // ID �ߺ�Ȯ�� ��ư ���� ��
			String id = tfId.getText().trim();
			java.sql.Connection conn = null;
			java.sql.Statement stmt = null;

			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "root", "0000");
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("Select * from TB_MEMBER");

				if (id.equals("")) {
					JOptionPane.showMessageDialog(null, "ID �Է�ĭ�� ����ֽ��ϴ�.", "INFORMAION_MESSAGE",
							JOptionPane.ERROR_MESSAGE);
					Checkpwd2.setVisible(false);
				}

				else if (id.length() < 6) {
					JOptionPane.showMessageDialog(null, "���̵� 6���� �̻� �Է����ּ���.", "INFORMATION_MESSAGE",
							JOptionPane.ERROR_MESSAGE);
					Checkpwd2.setVisible(false);
				}

				else {
					while (rs.next()) {
						if (id.equals(rs.getString("id"))) {
							JOptionPane.showMessageDialog(null, "�̹� ������� ID�Դϴ�.", "�˸�", JOptionPane.ERROR_MESSAGE);
							tfId.requestFocus();
							Checkpwd2.setVisible(false);
							return;
						}
					}
					int y = JOptionPane.showConfirmDialog(null, "��� ������ ID�Դϴ�.\n����Ͻðڽ��ϱ�?", "�˸�",
							JOptionPane.YES_NO_OPTION);
					if (y == JOptionPane.OK_OPTION) {
						tfId.requestFocus();
						boolean ID_Check = true;
						tfId.setEnabled(false); // tfId �ؽ�Ʈ�ʵ� ��Ȱ��ȭ
						Checkpwd2.setVisible(true); // üũ Ȱ��ȭ
					}
					return;
				}
			} catch (ClassNotFoundException cnfe) {
				System.out.println("�ش� Ŭ������ ã�� �� �����ϴ�." + cnfe.getMessage());
			}

			catch (SQLException se) {

			}
		}

		else if (e.getSource() == btnPost) { // �����ȣ �˻� ��ư ���� ��
			System.out.println("�����ȣ �˻� ��ư Ŭ��");
			zipSearch frame = new zipSearch(); // �����ȣ �˻�â ����
			frame.setVisible(true);
		}

		else if (e.getSource() == btnDel) {
			System.out.println("���� ���� ��ư Ŭ��");
			ImageIcon img = new ImageIcon("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\�⺻������.jpg");
			photo.setIcon(img);
			tfprofilePath.setText(null);

		}

		else if (e.getSource() == btnPhoto) { // ���� �˻� ��ư ���� ��
			System.out.println("���� �˻� ��ư Ŭ��");

			String Image_path;
			photo.setText(""); // photo(��) ����
			FileDialog fdlg = new FileDialog(new JFrame(), "file open", FileDialog.LOAD); // ���ϴ��̾�α׸� ����Ͽ� ������ ����
			/*
			 * FileNameExtensionFilter filter=new
			 * FileNameExtensionFilter("*.Imagers","jpg","gif","png");
			 * chooser.setFileFilter(filter); int ret = chooser.showOpenDialog(null); if(ret
			 * != JFileChooser.APPROVE_OPTION){ JOptionPane.showMessageDialog(null,
			 * "������ �����ϼ���.","���",JOptionPane.WARNING_MESSAGE); }
			 */

			fdlg.setVisible(true); // �����ֱ� Ȱ��ȭ

			if (fdlg.getFile() == null) { // ������ null�̸�
				return;
			} else {
				ImageIcon originalIcon = new ImageIcon(fdlg.getDirectory() + fdlg.getFile());
				Image originalImage = originalIcon.getImage();
				Image resizeImage = originalImage.getScaledInstance(153, 148, Image.SCALE_SMOOTH);
				ImageIcon resizeIcon = new ImageIcon(resizeImage);
				Image_path = fdlg.getDirectory() + fdlg.getFile();
				imagepath_TF.setText(Image_path);
				tfprofilePath.setText(Image_path); // ������ �ؽ�Ʈ�ʵ�� �޾ƿͼ� �󺧿� ����
				photo.setIcon(resizeIcon);
			}
		}

		/*
		 * FileInputStream fis = null; Connection conn = null; // ������ �����ϱ� ����
		 * PreparedStatement pstmt = null; // �����͸� �о���� ����
		 * 
		 * FileDialog dlg = new FileDialog(this, "������ ���� �����ϱ�", FileDialog.LOAD);
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
			if (MemberProc.tfprofilePath.getText().equals("")) // �Է¾������� ����
				return;
			profile.repaint();
		}

		else if (e.getSource() == btnInsert) { // ���� ��ư ���� ��

			if (tfId.getText().length() == 0) // id�Է¶��� �ƹ� ���� �Է� �� �ϰ� �α��� �õ��� ��
			{
				tfId.setText("ID�� �Է����ּ���");
				tfId.requestFocus();
				return;
			}
			if (tfPwd.getText().length() == 0) // ��й�ȣ�Է¶��� �ƹ� ���� �Է� �� �ϰ� �α��� �õ��� ��
			{

				tfPwd.setText("��й�ȣ�� �Է����ּ���");
				tfPwd.requestFocus();
				return;
			} else if (imagepath_TF.getText().length() == 0) { // ������ �ȳ־��� ��
				JOptionPane.showMessageDialog(this, "������ �־��ּ���!");
				return;
			} else { // �ٵǸ�
				int x = JOptionPane.showConfirmDialog(null, "�����Ͻðڽ��ϱ�?", "�˸�", JOptionPane.YES_NO_OPTION);
				if (x == JOptionPane.OK_OPTION) {
					APIresult.main(null); // ĸ�� ����
					dispose();
				}

			}

//				insertMember();
//				System.out.println("insertMember() ȣ�� ����");

		} else if (e.getSource() == btnCancel) { // ��� ��ư ���� ��
			this.dispose(); // â�ݱ� (����â�� ����)
			// system.exit(0)=> ���� ��� ��� â�� �� ����
		} else if (e.getSource() == btnUpdate) { // ���� ��ư ���� ��
			updateMember();
		} else if (e.getSource() == btnDelete) { // Ż�� ��ư ���� ��

			int x = JOptionPane.showConfirmDialog(this, "���� �����Ͻðڽ��ϱ�?", "����", JOptionPane.YES_NO_OPTION);

			if (x == JOptionPane.OK_OPTION) {
				deleteMember();
			} else {
				JOptionPane.showMessageDialog(this, "������ ����Ͽ����ϴ�.");
			}
		}

		// jTable���� ���� �޼ҵ� ȣ��
		mList.jTableRefresh();
	}

	public static void messageBox(Object obj, String message) {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog((Component) obj, message);
	}

	public void deleteMember() {
		String id = tfId.getText();
		String pwd = tfPwd.getText();
		if (pwd.length() == 0) { // ���̰� 0�̸�

			JOptionPane.showMessageDialog(this, "��й�ȣ�� �� �Է��ϼ���!");
			return; // �޼ҵ� ��
		}
		// System.out.println(mList);
		MemberDAO dao = new MemberDAO();
		boolean ok = dao.deleteMember(id, pwd);

		if (ok) {
			JOptionPane.showMessageDialog(this, "�����Ϸ�");
			dispose();

		} else {
			JOptionPane.showMessageDialog(this, "��������");

		}

	}// deleteMember

	private void updateMember() {

		// 1. ȭ���� ������ ��´�.
		MemberDTO dto = getViewData();
		// 2. �������� DB�� ����
		MemberDAO dao = new MemberDAO();

		// photo.setIcon(dto.profilepath);

		boolean ok = dao.updateMember(dto);

		if (!ok) {
			JOptionPane.showMessageDialog(this, "�����Ǿ����ϴ�.");
			this.dispose();
		} else {
			JOptionPane.showMessageDialog(this, "�������� : ���� Ȯ���ϼ���");
		}
	}

	public static void insertMember() {

		// ȭ�鿡�� ����ڰ� �Է��� ������ ��´�.
		MemberDTO dto = getViewData();
		MemberDAO dao = new MemberDAO();
		boolean ok = dao.insertMember(dto);

		if (ok) { // ���� �Ϸ��

			JOptionPane.showMessageDialog(null, "������ �Ϸ�Ǿ����ϴ�.");
			// dispose();
			// Main_GUI.setVisible(false);
			// System.exit(0);

		} else {
			JOptionPane.showMessageDialog(null, "������ ���������� ó������ �ʾҽ��ϴ�.");
		}
	}
// insertMember

	private void searchPwd() {
	}

	public static MemberDTO getViewData() {

		// ȭ�鿡�� ����ڰ� �Է��� ������ ��´�.
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
			gender = "����";
		} else if (rbWoman.isSelected()) {
			gender = "����";
		}

		String profilepath = tfprofilePath.getText();

		// dto�� ��´�.
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
			pwdCh.setText("��й�ȣ�� �Է����ּ���!");
			Checkpwd3.setVisible(false);
		}

		/*
		 * else if (a.length() < 10 || a.length() > 20) {
		 * pwdCh.setForeground(Color.RED); pwdCh.setText("10~20�ڸ��� ��й�ȣ�� �Է����ּ���!");
		 * Checkpwd3.setVisible(false); }
		 */

		else if (a.equals(b)) {

			// JOptionPane.showMessageDialog(null, "��ġ�մϴ�.", "�˸�",
			// JOptionPane.INFORMATION_MESSAGE);
			pwdCh.setForeground(new Color(0, 255, 0));
			pwdCh.setText("�� ��й�ȣ�� ��ġ�մϴ�.");
			Checkpwd3.setVisible(true); // üũ Ȱ��ȭ
		} else {
			// JOptionPane.showMessageDialog(null, "��ġ ���� �ʽ��ϴ�!", "���",
			// JOptionPane.WARNING_MESSAGE);
			pwdCh.setForeground(Color.RED);
			pwdCh.setText("�� ��й�ȣ�� ��ġ�����ʽ��ϴ�!");
			Checkpwd3.setVisible(false);
		}
		// ��й�ȣ üũ
		String pwd = tfPwd.getText();

		if (pwd.length() < 10 || pwd.length() > 20) {
			// JOptionPane.showMessageDialog(null, "����, ����, Ư����ȣ �� 3���� �̻� �������� 10�ڸ����� 20�ڸ�
			// ���̷� �Է����ּ���.", "�˸�", JOptionPane.ERROR_MESSAGE);
			pwdCh1.setForeground(Color.RED);
			pwdCh1.setText("����,����,Ư����ȣ ���� 10~20�ڸ�");
			Checkpwd.setVisible(false);
			return;
		} // ��й�ȣ Ȯ��

		int regularCnt = passwrodvalidate(pwd);

		if (regularCnt == 3) {
			if (pwd.length() < 10 || pwd.length() > 20) {
				// JOptionPane.showMessageDialog(null, "����, ����, Ư����ȣ �� 3���� �̻� �������� 10�ڸ����� 20�ڸ�
				// ���̷� �Է����ּ���.", "�˸�", JOptionPane.ERROR_MESSAGE);
				pwdCh1.setForeground(Color.RED);
				pwdCh1.setText("����,����,Ư����ȣ ���� 10~20�ڸ�");
				Checkpwd.setVisible(false);
				return;
			}
		} else {
			// JOptionPane.showMessageDialog(null, "����, ����, Ư����ȣ �� 3���� �̻� �������� 10�ڸ����� 20�ڸ�
			// ���̷� �Է����ּ���.", "�˸�", JOptionPane.ERROR_MESSAGE);
			pwdCh1.setForeground(Color.RED);
			pwdCh1.setText("����,����,Ư����ȣ ���� 10~20�ڸ�");
			Checkpwd.setVisible(false);
			return;
		}

		// JOptionPane.showMessageDialog(null, "��밡���� ��й�ȣ �Դϴ�.", "�˸�",
		// JOptionPane.INFORMATION_MESSAGE);
		pwdCh1.setForeground(new Color(0, 255, 0));
		pwdCh1.setText("��밡���� ��й�ȣ�Դϴ�.");
		Checkpwd.setVisible(true);
		// count = 1; //üũ��� Ȱ��ȭ�ϱ� ���� �۾�
	}
	/*
	 * String pwd1 = String.valueOf(tfPwd.getPassword()); String pwd2 =
	 * String.valueOf(tfPwd2.getPassword()); if (!pwd1.equals(pwd2)) {
	 * pwdCh.setText("��ȣ ����ġ"); } else { pwdCh.setText("��ȣ ��ġ"); } if (e.getSource()
	 * == tfPwd) { if (pwd1.length() < 4 || pwd1.length() > 16) {
	 * pwdCh.setText("��ȣ : 4~16��"); } else { pwdCh.setText(""); } } }
	 */

	@Override
	public void keyTyped(KeyEvent e) {

		// TODO Auto-generated method stub
	}
}// end