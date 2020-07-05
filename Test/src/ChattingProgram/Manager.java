// 관리자모드 UI 및 비밀번호 설정

/*
 * 관리자모드 GUI 클래스
 * 관리자 비밀번호를 입력받아 맞으면 관리자모드를 실행시킨다.
 */


package ChattingProgram;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Manager extends JFrame {

	private JPanel contentPane;

	public Manager(String pw) {
			if(pw.equals("7508")) {
				// 7508를 입력받으면 관리자 모드를 실행시킨다.
				setTitle("관리자모드");
				init();

			}
		}
		
		 void init() {
			setBounds(100, 100, 491, 581);
			ImageIcon icon = new ImageIcon("C:\\Users\\36175\\Desktop\\이모티콘\\3.jpeg");
			this.setIconImage(icon.getImage());
			this.setSize(491,581);
			contentPane = new JPanel()
			{
				public void paintComponent(Graphics g) {
					// Approach 1 : Dispaly image at at full size
					g.drawImage(icon.getImage(), 0, 0, null);
					setOpaque(false); // 그림을 표시하게 설정, 투명하게 조절
					super.paintComponents(g);
					
			}
			};
			setTitle("관리자모드");
			contentPane.setBackground(Color.CYAN);
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			JLabel lblChatting = new JLabel("관리자모드");
			lblChatting.setFont(new Font("휴먼엑스포", Font.BOLD, 21));
			lblChatting.setBounds(157, 57, 117, 46);
			contentPane.add(lblChatting);
			
			JButton btnNewButton = new JButton("회원 DB 초기화"); // DB 삭제 버튼
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent args0) {
					int choice = JOptionPane.showConfirmDialog(null, "정말 DB를 초기화하시겠습니까?","DB 초기화",JOptionPane.YES_NO_OPTION);
					if (choice==0) {
						new DBDelete();
					}
				}
			});
			btnNewButton.setBounds(143, 250, 136, 27);
			contentPane.add(btnNewButton);
			
			JButton button = new JButton("회원정보 저장"); // 회원정보 저장 버튼 클릭 시
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent args0) {
					new DBSave();
					JOptionPane.showMessageDialog(null, "정상적으로 DB데이터가 txt로 저장되었습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
				}
			});
			button.setBounds(143, 307, 136, 27);
			contentPane.add(button);
			
			JButton member_btn = new JButton("회원 관리"); // 회원 관리 버튼 클릭 시
			member_btn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent args0) {
					new Member_List();
				}
			});
			member_btn.setBounds(143, 370, 136, 27);
			contentPane.add(member_btn);
			
			JButton chat = new JButton("채팅 관리"); // 채팅 관리 버튼 클릭 시
			chat.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent args0) {
					new ChatProc();
					
				}
			});
			chat.setBounds(143, 433, 136, 27);
			contentPane.add(chat);
			
			JButton chat1 = new JButton("채팅 관리"); // 채팅 관리 버튼 클릭 시
			chat1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent args0) {
					
					
				}
			});
			chat1.setBounds(143, 433, 136, 27);
			contentPane.add(chat1);
			
			JButton end_btn = new JButton("종료");
			end_btn.setBounds(399, 495, 60, 27);
			end_btn.addActionListener(new ActionListener() {
		        	public void actionPerformed(ActionEvent e) {
		        		Manager.this.setVisible(false);
		        	}
		        });
			contentPane.add(end_btn);
			setResizable(false);
			this.setVisible(true); // 화면에 보이게
	}
		
	public static void main(String args[]) {
		new ManagerLogin();
	}



}
