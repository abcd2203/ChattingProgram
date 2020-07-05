// ������  ��� ��й�ȣ �´��� üũ �� ���� UI
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
		init(); // ȭ�� ����
		start(); // ��ư ������ ����
		
	}

	public void init() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 678, 234);
		ImageIcon icon = new ImageIcon("C:\\Users\\user\\Pictures\\Camera Roll\\ī��ī��.jpg");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\36175\\Desktop\\\uC774\uBAA8\uD2F0\uCF58\\\uBE68\uAC04\uBC84\uD2BC_\uC785\uB825.png"));
		this.setSize(300,130);
		
		contentPane = new JPanel()
		{
			public void paintComponent(Graphics g) {
				// Approach 1 : Dispaly image at at full size
				g.drawImage(icon.getImage(), 0, 0, null);
				setOpaque(false); // �׸��� ǥ���ϰ� ����, �����ϰ� ����
				super.paintComponents(g);
				
		}
		};
		setTitle("�����ڸ�� �α���");
		contentPane.setBackground(new Color(0, 204, 153));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("������ ���");
		label.setFont(new Font("�޸տ�����", Font.BOLD, 19));
		label.setBounds(92, 10, 187, 24);
		contentPane.add(label);
		
		tf_pw = new JPasswordField();
		tf_pw.setBounds(81, 65, 135, 24);
		contentPane.add(tf_pw);
		tf_pw.setColumns(10);
		
		btn_OK = new JButton("Ȯ��");
		btn_OK.setFont(new Font("����", Font.PLAIN, 11));
		btn_OK.setBounds(228, 38, 57, 27);
		contentPane.add(btn_OK);
		
		btn_CANCEL = new JButton("���");
		btn_CANCEL.setFont(new Font("����", Font.PLAIN, 11));
		btn_CANCEL.setBounds(228, 63, 57, 27);
		contentPane.add(btn_CANCEL);
		
		tf_id = new JTextField();
		tf_id.setBounds(81, 41, 135, 21);
		contentPane.add(tf_id);
		tf_id.setColumns(10);
		
		lblId = new JLabel("������ ID");
		lblId.setBounds(12, 44, 57, 15);
		contentPane.add(lblId);
		setResizable(false);
		this.setVisible(true);
		
		lblNewLabel = new JLabel("������ PW");
		lblNewLabel.setBounds(12, 69, 66, 15);
		contentPane.add(lblNewLabel);
		setResizable(false);
		this.setVisible(true);
	}
	
	public void start() {
		btn_OK.addActionListener(this); // Ȯ�ι�ư ������ ����
		btn_CANCEL.addActionListener(this); // ��ҹ�ư ������ ����
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btn_OK) { // Ȯ�� ��ư�� ���� ��
			if(tf_id.getText().trim().equals("abcd2203")) {
				if(tf_pw.getText().trim().equals("7508")) // ��й�ȣ�� 7508�� �Է�
				{
					JOptionPane.showMessageDialog(null, "������ ������ �����ϼ̽��ϴ�.", "�˸�" , JOptionPane.INFORMATION_MESSAGE);
					Manager manager = new Manager("7508"); // ������ ������  Manager ��ü ����
					setVisible(false);
				
				}
				else {
					JOptionPane.showMessageDialog(null, "��й�ȣ�� �߸� �Է��ϼ̽��ϴ�.(�����ڸ� ���� ����)", "�˸�", JOptionPane.WARNING_MESSAGE);
				}
			}
			
			else  // ID�� Ʋ��
			{
				JOptionPane.showMessageDialog(null, "ID�� �߸� �Է��ϼ̽��ϴ�.(�����ڸ� ���� ����)", "�˸�", JOptionPane.WARNING_MESSAGE);
				tf_pw.requestFocus();
			}
		}
		
		else if(e.getSource() == btn_CANCEL) // ��� ��ư�� ������ ��
		{
			setVisible(false); // ��ü ȭ�� ������
		}
	}

}
