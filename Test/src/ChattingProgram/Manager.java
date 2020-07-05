// �����ڸ�� UI �� ��й�ȣ ����

/*
 * �����ڸ�� GUI Ŭ����
 * ������ ��й�ȣ�� �Է¹޾� ������ �����ڸ�带 �����Ų��.
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
				// 7508�� �Է¹����� ������ ��带 �����Ų��.
				setTitle("�����ڸ��");
				init();

			}
		}
		
		 void init() {
			setBounds(100, 100, 491, 581);
			ImageIcon icon = new ImageIcon("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\3.jpeg");
			this.setIconImage(icon.getImage());
			this.setSize(491,581);
			contentPane = new JPanel()
			{
				public void paintComponent(Graphics g) {
					// Approach 1 : Dispaly image at at full size
					g.drawImage(icon.getImage(), 0, 0, null);
					setOpaque(false); // �׸��� ǥ���ϰ� ����, �����ϰ� ����
					super.paintComponents(g);
					
			}
			};
			setTitle("�����ڸ��");
			contentPane.setBackground(Color.CYAN);
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			JLabel lblChatting = new JLabel("�����ڸ��");
			lblChatting.setFont(new Font("�޸տ�����", Font.BOLD, 21));
			lblChatting.setBounds(157, 57, 117, 46);
			contentPane.add(lblChatting);
			
			JButton btnNewButton = new JButton("ȸ�� DB �ʱ�ȭ"); // DB ���� ��ư
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent args0) {
					int choice = JOptionPane.showConfirmDialog(null, "���� DB�� �ʱ�ȭ�Ͻðڽ��ϱ�?","DB �ʱ�ȭ",JOptionPane.YES_NO_OPTION);
					if (choice==0) {
						new DBDelete();
					}
				}
			});
			btnNewButton.setBounds(143, 250, 136, 27);
			contentPane.add(btnNewButton);
			
			JButton button = new JButton("ȸ������ ����"); // ȸ������ ���� ��ư Ŭ�� ��
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent args0) {
					new DBSave();
					JOptionPane.showMessageDialog(null, "���������� DB�����Ͱ� txt�� ����Ǿ����ϴ�.", "�˸�", JOptionPane.INFORMATION_MESSAGE);
				}
			});
			button.setBounds(143, 307, 136, 27);
			contentPane.add(button);
			
			JButton member_btn = new JButton("ȸ�� ����"); // ȸ�� ���� ��ư Ŭ�� ��
			member_btn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent args0) {
					new Member_List();
				}
			});
			member_btn.setBounds(143, 370, 136, 27);
			contentPane.add(member_btn);
			
			JButton chat = new JButton("ä�� ����"); // ä�� ���� ��ư Ŭ�� ��
			chat.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent args0) {
					new ChatProc();
					
				}
			});
			chat.setBounds(143, 433, 136, 27);
			contentPane.add(chat);
			
			JButton chat1 = new JButton("ä�� ����"); // ä�� ���� ��ư Ŭ�� ��
			chat1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent args0) {
					
					
				}
			});
			chat1.setBounds(143, 433, 136, 27);
			contentPane.add(chat1);
			
			JButton end_btn = new JButton("����");
			end_btn.setBounds(399, 495, 60, 27);
			end_btn.addActionListener(new ActionListener() {
		        	public void actionPerformed(ActionEvent e) {
		        		Manager.this.setVisible(false);
		        	}
		        });
			contentPane.add(end_btn);
			setResizable(false);
			this.setVisible(true); // ȭ�鿡 ���̰�
	}
		
	public static void main(String args[]) {
		new ManagerLogin();
	}



}
