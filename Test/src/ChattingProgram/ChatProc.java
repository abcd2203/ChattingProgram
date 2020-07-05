package ChattingProgram;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import javafx.scene.control.ComboBox;

public class ChatProc extends JFrame implements MouseListener, ActionListener {

	private Vector v;
	private Vector cols;
	private DefaultTableModel model;
	private JFrame frame = new JFrame("ä�÷α� ����");
	private JPanel contentPane;
	private JScrollPane scrollPane = new JScrollPane();
	private JTable jTable = new JTable();
	private int room_check = 0;
	private int nick_check = 0;

	private JComboBox<String> comboBox_room;
	private JComboBox<String> comboBox_nick;

	public ChatProc() {
		frame.setBounds(500, 100, 858, 750);// �������� ũ��� ���� ��ġ�� �����Ѵ�.(x, y, width, height)
		contentPane = new JPanel();// 3���� �г��� ����ִ� ��� �г�
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);// JFrameŬ������ �޼ҵ�� �Ķ���ͷ� ���� String�� �������� Ÿ��Ʋ�� �����Ѵ�.

		// @@@@@@@@@@@@@@@@@@@@@@@@@@@@
		ChatDAO dao = new ChatDAO();// ���Ŀ� ����
		v = dao.getChatList();// �����غ��ڸ� v��� ���� �ȿ� DB���� ã�� ������ �ϳ��� ���Խ�Ű�� ������ �Ѵ�.
		System.out.println("v=" + "\n" + v); // ���Ϳ� ���� ������ Ȯ���غ���.
		cols = getColumn();// JTable���� ù �ٿ� �ش��ϴ� �κ�����, �� ���̺��� Į���� �̸��� ǥ���Ѵ�.
		model = new DefaultTableModel(v, cols);
		// @@@@@@@@@@@@@@@@@@@@@@@@@@@@
		// JTable�� �Ķ���ͷ� model�� �ʿ�� �ϴµ�,
		// DefaultTableModel(,)�޼ҵ�� �����ͷν� ������ ���Ϳ�, Į���� �̸����� ������ ���͸� �Ķ���ͷ� ����Ѵ�.
		scrollPane.setBounds(10, 80, 820, 600);
		contentPane.add(scrollPane);// ���� �����ӿ� �г��� �߰��Ͽ���.
		jTable.setModel(model);
		scrollPane.setViewportView(jTable);// �ӽ������� �г��� �����, �� ���� JTable�� �÷ȴ�.
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "����� ä�÷α� �˻�", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(6, 10, 780, 60);
		contentPane.add(panel_1);

		// comboBox
		comboBox_room = new JComboBox<String>();
		comboBox_room.addItem("�� �̸�");

		comboBox_nick = new JComboBox<String>();
		comboBox_nick.addItem("�̸�");

		comboBox_room.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					room_check = 1;
					ChatDAO dao = new ChatDAO(); // MemberDAOŬ���� Ÿ���� dao��ü�� ����
					if (nick_check == 0) {
						DefaultTableModel model = new DefaultTableModel(
								dao.getChatList_Room1(comboBox_room.getSelectedItem().toString()), getColumn());
						jTable.setModel(model); // JTable�� ���� �缳���Ѵ�.(���ŵ� �𵨷�)
						System.out.println("�� �̸����� �˻�");
					} else if (nick_check == 1) {
						DefaultTableModel model = new DefaultTableModel(
								dao.getChatList_Room2(comboBox_room.getSelectedItem().toString(),
										comboBox_nick.getSelectedItem().toString()),
								getColumn());
						jTable.setModel(model); // JTable�� ���� �缳���Ѵ�.(���ŵ� �𵨷�)
						System.out.println("�� �̸����� �˻�");
					} else {
						System.out.println("���� �߻�");
					}
					if(comboBox_room.getSelectedItem().toString() == "�� �̸�")
						jTableRefresh();
				}

			}
		});

		comboBox_nick.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					nick_check = 1;
					ChatDAO dao = new ChatDAO(); // MemberDAOŬ���� Ÿ���� dao��ü�� ����
					if (room_check == 0) {
						DefaultTableModel model = new DefaultTableModel(
								dao.getChatList_User1(comboBox_nick.getSelectedItem().toString()), getColumn());
						jTable.setModel(model); // JTable�� ���� �缳���Ѵ�.(���ŵ� �𵨷�)
						System.out.println("�г������� �˻�");
					} else if (room_check == 1) {
						DefaultTableModel model = new DefaultTableModel(
								dao.getChatList_User2(comboBox_nick.getSelectedItem().toString(),
										comboBox_room.getSelectedItem().toString()),
								getColumn());
						jTable.setModel(model); // JTable�� ���� �缳���Ѵ�.(���ŵ� �𵨷�)
						System.out.println("�г������� �˻�");
					} else {
						System.out.println("���� �߻�");
					}
					if(comboBox_nick.getSelectedItem().toString() == "�̸�")
						jTableRefresh();
				}

			}
		});

		displayroomName();
		displaynickName();

		panel_1.add(comboBox_room);
		panel_1.add(comboBox_nick);

		jTable.addMouseListener(this); // ������ ���
		frame.setVisible(true);
	}// end ������

	// JTable�� �÷�
	public Vector getColumn() {// ������ ǥ Ʋ�� ����� �޼ҵ��̴�.
		// ù��° Į���� "���̸�", �ι�° Į���� "�ð�" ����° Į����.......
		// | ���̸� | �г��� | �ð� | �޼��� |
		Vector col = new Vector();
		col.add("���̸�");
		col.add("���̵�");
		col.add("�ð�");
		col.add("�޼���");
		return col;
	}// getColumn

	// Jtable ���� ���� �޼���
	public void jTableRefresh() {
		ChatDAO dao = new ChatDAO(); // MemberDAOŬ���� Ÿ���� dao��ü�� ����
		DefaultTableModel model = new DefaultTableModel(dao.getChatList(), getColumn());
		// ���ο� ���� ����µ�, DefaultTableModel(���ο� ��ü���� ������ �����͸� �ҷ��´�, ������ ���� ǥ Ʋ�� �����´�.)
		jTable.setModel(model); // JTable�� ���� �缳���Ѵ�.(���ŵ� �𵨷�)
	}

	public static void main(String[] args) {
		new ChatProc();
	}// main

	@Override
	public void actionPerformed(ActionEvent e) {

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		int r = jTable.getSelectedRow();
		String Roomname = (String) jTable.getValueAt(r, 0);
		// System.out.println("Roomname="+Roomname);

		int x = JOptionPane.showConfirmDialog(this, "[" + Roomname + "]���� ��� ä�� ����� �����Ͻðڽ��ϱ�?", "����",
				JOptionPane.YES_NO_OPTION);

		if (x == JOptionPane.OK_OPTION) {
			ChatDAO.deleteChat(Roomname);
		} else {
			JOptionPane.showMessageDialog(this, "������ ����Ͽ����ϴ�.");

		}
		jTableRefresh();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	// ���̸� ����
	public void displayroomName() {
		// ����
		ChatControl controller = new ChatControl();
		// DB����
		controller.connection();
		//
		ArrayList<ChatDTO> roomList = controller.searchRoom();
		for (int i = 0; i < roomList.size(); i++) {
			ChatDTO chatcode = roomList.get(i);
			comboBox_room.addItem(chatcode.getRoomname());
		}
		// DB���� ����
		controller.disconnection();
	}

	// �г��� ����
	public void displaynickName() {
		// ����
		ChatControl controller = new ChatControl();
		// DB����
		controller.connection();
		//
		ArrayList<ChatDTO> nickList = controller.searchNick();
		for (int i = 0; i < nickList.size(); i++) {
			ChatDTO chatcode = nickList.get(i);
			comboBox_nick.addItem(chatcode.getNickname());
		}
		// DB���� ����
		controller.disconnection();
	}

}
