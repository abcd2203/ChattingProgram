package papago;

import java.awt.BorderLayout;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import ChattingProgram.Client;
import papago.PapagoNMT;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class Papago extends JFrame {

	private JPanel contentPane;
	private static JTextField tf;
	private static JTextField translate_tf;
	private static JButton send_btn;
	private static JButton translate_btn;
	
	private JRadioButton Korean;
	private static JRadioButton English;
	private static JRadioButton French;
	private static JRadioButton Chinese;
	private static JRadioButton Vietnamese;
	
	private static ButtonGroup btngroup;
	private static ButtonGroup btngroup2;
	private static String text = " ";
	private static String source = PapagoNMT.KOREAN;
	private static String target = PapagoNMT.ENGLISH;
	private JLabel lblNewLabel;
	private static JRadioButton Korean1;
	private static JRadioButton Vietnamese1;
	private static JRadioButton English1;
	private static JRadioButton French1;
	private static JRadioButton Chinese1;
	
	
	/**
	 * Create the frame.
	 */
	public Papago() {

		setBounds(100, 100, 687, 310);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		contentPane.setVisible(true);
		setContentPane(contentPane);
		
		tf = new JTextField();
		tf.setBounds(12, 147, 284, 48);
		contentPane.add(tf);
		tf.setColumns(10);
		
		translate_tf = new JTextField();
		translate_tf.setBounds(376, 147, 284, 48);
		contentPane.add(translate_tf);
		translate_tf.setColumns(10);
		translate_tf.setEditable(false);
		
		translate_btn = new JButton("번역");
		translate_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//System.out.println("번역 버튼");
				if(English1.isSelected()){
			    	  //System.out.println("영어");
			            source = PapagoNMT.ENGLISH;
			        }
				else if(Korean1.isSelected()) {
					//System.out.println("한국어");
					source = PapagoNMT.KOREAN;
				}
			      else if(French1.isSelected()){
			    	  //System.out.println("프랑스어");
			    	  source = PapagoNMT.FRENCH;
			        }
			      else if(Chinese1.isSelected()) {
			    	  //System.out.println("중국어");
			    	  source = PapagoNMT.CHINESE;
			      }
			      else if (Vietnamese1.isSelected()) {
			    	  //System.out.println("베트남어");
			    	  source = PapagoNMT.VIETNAMESE;
			      }
				
				if(English.isSelected()){
			    	  //System.out.println("영어");
			            target = PapagoNMT.ENGLISH;
			        }
				else if(Korean.isSelected()) {
					//System.out.println("한국어");
		            target = PapagoNMT.KOREAN;
				}
			      else if(French.isSelected()){
			    	  //System.out.println("프랑스어");
			            target = PapagoNMT.FRENCH;
			        }
			      else if(Chinese.isSelected()) {
			    	  //System.out.println("중국어");
			    	  target = PapagoNMT.CHINESE;
			      }
			      else if (Vietnamese.isSelected()) {
			    	  //System.out.println("베트남어");
			    	  target = PapagoNMT.VIETNAMESE;
			      }
				translate(source, target, tf.getText());
			}
		});
		translate_btn.setBounds(297, 147, 77, 48);
		contentPane.add(translate_btn);
		
		send_btn = new JButton("채팅방에 전송");
		send_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(translate_tf.getText().length() == 0) {		// 공백이면 전송 X
					return;
				}
				else {
					//Client.send_message("Chatting/" + Client.My_Room + "/" + "번역  <" + tf.getText() +">");	//위치 채팅 전송
					Client.send_message("Chatting/" + Client.My_Room + "/" + translate_tf.getText());	// 채팅에 전송
					dispose();	// 창닫기
				}
			}
		});
		
		send_btn.setBounds(247, 205, 181, 31);
		contentPane.add(send_btn);
		
		Korean = new JRadioButton("Korean");
		Korean.setBounds(376, 93, 87, 23);
		contentPane.add(Korean);
		
		English = new JRadioButton("English");
		English.setSelected(true);
		English.setBounds(467, 93, 87, 23);
		contentPane.add(English);
		
		Chinese = new JRadioButton("Chinese");
		Chinese.setBounds(568, 93, 87, 23);
		contentPane.add(Chinese);
		
		
		French = new JRadioButton("French");
        French.setBounds(467, 118, 87, 23);
        contentPane.add(French);
        
        Vietnamese = new JRadioButton("Vietnamese");
        Vietnamese.setBounds(376, 118, 100, 23);
        contentPane.add(Vietnamese);
		
		btngroup = new ButtonGroup();
		btngroup.add(Korean);
		btngroup.add(English);
		btngroup.add(French);
		btngroup.add(Vietnamese);
		btngroup.add(Chinese);
		
		lblNewLabel = new JLabel("프린스송 번역기");
		lblNewLabel.setBackground(Color.YELLOW);
		lblNewLabel.setFont(new Font("휴먼편지체", Font.BOLD, 30));
		lblNewLabel.setBounds(234, 39, 207, 48);
		contentPane.add(lblNewLabel);
		
		Korean1 = new JRadioButton("한국어");
		Korean1.setSelected(true);
		Korean1.setBounds(12, 93, 87, 23);
		contentPane.add(Korean1);
		
		English1 = new JRadioButton("영어");
		English1.setBounds(103, 93, 87, 23);
		contentPane.add(English1);
		
		Chinese1 = new JRadioButton("중국어");
		Chinese1.setBounds(204, 93, 87, 23);
		contentPane.add(Chinese1);
		
		French1 = new JRadioButton("프랑스어");
		French1.setBounds(103, 118, 87, 23);
		contentPane.add(French1);
		
		Vietnamese1 = new JRadioButton("베트남어");
		Vietnamese1.setBounds(12, 118, 100, 23);
		contentPane.add(Vietnamese1);
		
		btngroup2 = new ButtonGroup();
		btngroup2.add(Korean1);
		btngroup2.add(English1);
		btngroup2.add(French1);
		btngroup2.add(Vietnamese1);
		btngroup2.add(Chinese1);
		
		
		JLabel lblNewLabel_1 = new JLabel("\u2192");
		lblNewLabel_1.setFont(new Font("휴먼편지체", Font.BOLD, 70));
		lblNewLabel_1.setBounds(298, 93, 77, 44);
		contentPane.add(lblNewLabel_1);

	}		// public() 끝

	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		Papago frame = new Papago();
		frame.setVisible(true);
	}
	
	public void translate(String source, String target, String text) {		// 번역 함수
	   String translated = PapagoNMT.translate(source, target, text);	//차례대로 '변환할 언어', '변환될 언어', 변환할 문장'
	   JsonParser parser = new JsonParser();
	   JsonElement element = parser.parse(translated);
	      if(element.getAsJsonObject().get("errorMessage") != null) {
	    	 System.out.println("번역 오류가 발생했습니다. "+"[오류 코드: "+element.getAsJsonObject()
	         .get("errorCode").getAsString()+"]");
	    	 translate_tf.setText("[오류 코드: "+element.getAsJsonObject()
	         .get("errorCode").getAsString()+"]");
	      }
	      else if (element.getAsJsonObject().get("message") != null) {
	    	 System.out.println("< 번역 결과 >");
	         translate_tf.setText(element.getAsJsonObject().get("message").getAsJsonObject().get("result")
	         .getAsJsonObject().get("translatedText").getAsString());
	      }   
	}
}
