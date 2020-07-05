package CaptchaTest2;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import CaptchaTest.*;

public class Captcha extends JFrame {

	private JPanel contentPane;
	private JTextField tf;
	private JLabel CaptchaImg;
	private JButton check_btn;
	private JButton re;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Captcha frame = new Captcha();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the frame.
	 */
	public Captcha() {
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		CaptchaImg = new JLabel("");
		CaptchaImg.setBounds(53, 58, 200, 99);
		CaptchaImg.setIcon(new ImageIcon("C:\\Users\\36175\\Desktop\\이모티콘\\Captcha\\tempname.jpg"));
		contentPane.add(CaptchaImg);
		
		tf = new JTextField();
		tf.setBounds(42, 162, 262, 21);
		contentPane.add(tf);
		tf.setColumns(10);
		
		check_btn = new JButton("확인");
		check_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				APIExamCaptchaNkeyResult.keyResult(tf.getText(),textField.getText());
			}
		});
		check_btn.setBounds(309, 129, 57, 54);
		contentPane.add(check_btn);
		
		re = new JButton("재발급");
		re.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//dispose();
				try {
					Thread.sleep(1000);
					String a = APIExamCaptchaNkey.getkey();
					tf.setText(a);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// 키얻기
				
				if (APIExamCaptchaNkey.loading==1) {
					//APIExamCaptchaImage.Image();
				}
					// 이미지 얻기
				//APIExamCaptchaNkeyResult.keyResult();	// 비교
				
//				Captcha frame = new Captcha();
//				frame.setVisible(true);
				
				//System.out.println(APIExamCaptchaNkey.key);
				CaptchaImg.setIcon(new ImageIcon("C:\\Users\\36175\\Desktop\\이모티콘\\Captcha\\tempname.jpg"));	// 이미지 새로고침
			}
		});
		re.setBounds(265, 96, 97, 23);
		contentPane.add(re);
		
		textField = new JTextField();
		textField.setBounds(42, 188, 262, 21);
		contentPane.add(textField);
		textField.setColumns(10);
	}
}
