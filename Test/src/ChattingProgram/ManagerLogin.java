// 관리자  모드 비밀번호 맞는지 체크 및 메인 UI
package ChattingProgram;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Toolkit;

public class ManagerLogin extends JFrame implements ActionListener{
	
	public static ImageIcon icon;
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField tf_pw;
	JButton btn_OK;
	JButton btn_CANCEL;
	private JTextField tf_id;
	private JLabel lblId;
	private JLabel lblNewLabel;
	
	public ManagerLogin()
	{
		init(); // 화면 구성
		start(); // 버튼 리스너 시작
		
	}

	public void init() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 678, 234);
		ImageIcon icon = new ImageIcon("C:\\Users\\user\\Pictures\\Camera Roll\\카톡카톡.jpg");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\36175\\Desktop\\\uC774\uBAA8\uD2F0\uCF58\\\uBE68\uAC04\uBC84\uD2BC_\uC785\uB825.png"));
		this.setSize(300,130);
		
		contentPane = new JPanel()
		{
			public void paintComponent(Graphics g) {
				// Approach 1 : Dispaly image at at full size
				g.drawImage(icon.getImage(), 0, 0, null);
				setOpaque(false); // 그림을 표시하게 설정, 투명하게 조절
				super.paintComponents(g);
				
		}
		};
		setTitle("관리자모드 로그인");
		contentPane.setBackground(new Color(0, 204, 153));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("관리자 모드");
		label.setFont(new Font("휴먼엑스포", Font.BOLD, 19));
		label.setBounds(92, 10, 187, 24);
		contentPane.add(label);
		
		tf_pw = new JPasswordField();
		tf_pw.setBounds(81, 65, 135, 24);
		contentPane.add(tf_pw);
		tf_pw.setColumns(10);
		
		btn_OK = new JButton("확인");
		btn_OK.setFont(new Font("굴림", Font.PLAIN, 11));
		btn_OK.setBounds(228, 38, 57, 27);
		contentPane.add(btn_OK);
		
		btn_CANCEL = new JButton("취소");
		btn_CANCEL.setFont(new Font("굴림", Font.PLAIN, 11));
		btn_CANCEL.setBounds(228, 63, 57, 27);
		contentPane.add(btn_CANCEL);
		
		tf_id = new JTextField();
		tf_id.setBounds(81, 41, 135, 21);
		contentPane.add(tf_id);
		tf_id.setColumns(10);
		
		lblId = new JLabel("관리자 ID");
		lblId.setBounds(12, 44, 57, 15);
		contentPane.add(lblId);
		setResizable(false);
		this.setVisible(true);
		
		lblNewLabel = new JLabel("관리자 PW");
		lblNewLabel.setBounds(12, 69, 66, 15);
		contentPane.add(lblNewLabel);
		setResizable(false);
		this.setVisible(true);
	}
	
	public void start() {
		btn_OK.addActionListener(this); // 확인버튼 리스너 시작
		btn_CANCEL.addActionListener(this); // 취소버튼 리스너 시작
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btn_OK) { // 확인 버튼을 누를 때
			if(tf_id.getText().trim().equals("abcd2203")) {
				if(tf_pw.getText().trim().equals("7508")) // 비밀번호를 7508을 입력
				{
					JOptionPane.showMessageDialog(null, "관리자 접속을 성공하셨습니다.", "알림" , JOptionPane.INFORMATION_MESSAGE);
					Manager manager = new Manager("7508"); // 접속이 성공한  Manager 객체 생성
					setVisible(false);
				
				}
				else {
					JOptionPane.showMessageDialog(null, "비밀번호를 잘못 입력하셨습니다.(관리자만 접속 가능)", "알림", JOptionPane.WARNING_MESSAGE);
				}
			}
			
			else  // ID를 틀림
			{
				JOptionPane.showMessageDialog(null, "ID를 잘못 입력하셨습니다.(관리자만 접속 가능)", "알림", JOptionPane.WARNING_MESSAGE);
				tf_pw.requestFocus();
			}
		}
		
		else if(e.getSource() == btn_CANCEL) // 취소 버튼을 눌렀을 때
		{
			setVisible(false); // 전체 화면 가리기
		}
	}

}
