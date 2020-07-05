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
	private JFrame frame = new JFrame("채팅로그 관리");
	private JPanel contentPane;
	private JScrollPane scrollPane = new JScrollPane();
	private JTable jTable = new JTable();
	private int room_check = 0;
	private int nick_check = 0;

	private JComboBox<String> comboBox_room;
	private JComboBox<String> comboBox_nick;

	public ChatProc() {
		frame.setBounds(500, 100, 858, 750);// 프레임의 크기와 시작 위치를 지정한다.(x, y, width, height)
		contentPane = new JPanel();// 3개의 패널을 담고있는 배경 패널
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		contentPane.setLayout(null);// JFrame클래스의 메소드로 파라미터로 받은 String을 프레임의 타이틀로 설정한다.

		// @@@@@@@@@@@@@@@@@@@@@@@@@@@@
		ChatDAO dao = new ChatDAO();// 이후에 설명
		v = dao.getChatList();// 정리해보자면 v라는 벡터 안에 DB에서 찾은 값들을 하나씩 대입시키는 역할을 한다.
		System.out.println("v=" + "\n" + v); // 벡터에 넣은 값들을 확인해본다.
		cols = getColumn();// JTable에서 첫 줄에 해당하는 부분으로, 각 테이블의 칼럼의 이름을 표시한다.
		model = new DefaultTableModel(v, cols);
		// @@@@@@@@@@@@@@@@@@@@@@@@@@@@
		// JTable은 파라미터로 model을 필요로 하는데,
		// DefaultTableModel(,)메소드는 데이터로써 가져올 벡터와, 칼럼의 이름으로 가져올 벡터를 파라미터로 사용한다.
		scrollPane.setBounds(10, 80, 820, 600);
		contentPane.add(scrollPane);// 현재 프레임에 패널을 추가하였다.
		jTable.setModel(model);
		scrollPane.setViewportView(jTable);// 임시적으로 패널을 만들어, 그 위에 JTable을 올렸다.
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "사용자 채팅로그 검색", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(6, 10, 780, 60);
		contentPane.add(panel_1);

		// comboBox
		comboBox_room = new JComboBox<String>();
		comboBox_room.addItem("방 이름");

		comboBox_nick = new JComboBox<String>();
		comboBox_nick.addItem("이름");

		comboBox_room.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					room_check = 1;
					ChatDAO dao = new ChatDAO(); // MemberDAO클래스 타입의 dao객체를 생성
					if (nick_check == 0) {
						DefaultTableModel model = new DefaultTableModel(
								dao.getChatList_Room1(comboBox_room.getSelectedItem().toString()), getColumn());
						jTable.setModel(model); // JTable의 모델을 재설정한다.(갱신된 모델로)
						System.out.println("방 이름으로 검색");
					} else if (nick_check == 1) {
						DefaultTableModel model = new DefaultTableModel(
								dao.getChatList_Room2(comboBox_room.getSelectedItem().toString(),
										comboBox_nick.getSelectedItem().toString()),
								getColumn());
						jTable.setModel(model); // JTable의 모델을 재설정한다.(갱신된 모델로)
						System.out.println("방 이름으로 검색");
					} else {
						System.out.println("문제 발생");
					}
					if(comboBox_room.getSelectedItem().toString() == "방 이름")
						jTableRefresh();
				}

			}
		});

		comboBox_nick.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					nick_check = 1;
					ChatDAO dao = new ChatDAO(); // MemberDAO클래스 타입의 dao객체를 생성
					if (room_check == 0) {
						DefaultTableModel model = new DefaultTableModel(
								dao.getChatList_User1(comboBox_nick.getSelectedItem().toString()), getColumn());
						jTable.setModel(model); // JTable의 모델을 재설정한다.(갱신된 모델로)
						System.out.println("닉네임으로 검색");
					} else if (room_check == 1) {
						DefaultTableModel model = new DefaultTableModel(
								dao.getChatList_User2(comboBox_nick.getSelectedItem().toString(),
										comboBox_room.getSelectedItem().toString()),
								getColumn());
						jTable.setModel(model); // JTable의 모델을 재설정한다.(갱신된 모델로)
						System.out.println("닉네임으로 검색");
					} else {
						System.out.println("문제 발생");
					}
					if(comboBox_nick.getSelectedItem().toString() == "이름")
						jTableRefresh();
				}

			}
		});

		displayroomName();
		displaynickName();

		panel_1.add(comboBox_room);
		panel_1.add(comboBox_nick);

		jTable.addMouseListener(this); // 리스너 등록
		frame.setVisible(true);
	}// end 생성자

	// JTable의 컬럼
	public Vector getColumn() {// 일정한 표 틀을 만드는 메소드이다.
		// 첫번째 칼럼이 "방이름", 두번째 칼럼이 "시간" 세번째 칼럼이.......
		// | 방이름 | 닉네임 | 시간 | 메세지 |
		Vector col = new Vector();
		col.add("방이름");
		col.add("아이디");
		col.add("시간");
		col.add("메세지");
		return col;
	}// getColumn

	// Jtable 내용 갱신 메서드
	public void jTableRefresh() {
		ChatDAO dao = new ChatDAO(); // MemberDAO클래스 타입의 dao객체를 생성
		DefaultTableModel model = new DefaultTableModel(dao.getChatList(), getColumn());
		// 새로운 모델을 만드는데, DefaultTableModel(새로운 객체에서 가져온 데이터를 불러온다, 위에서 만든 표 틀을 가져온다.)
		jTable.setModel(model); // JTable의 모델을 재설정한다.(갱신된 모델로)
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

		int x = JOptionPane.showConfirmDialog(this, "[" + Roomname + "]방의 모든 채팅 기록을 삭제하시겠습니까?", "삭제",
				JOptionPane.YES_NO_OPTION);

		if (x == JOptionPane.OK_OPTION) {
			ChatDAO.deleteChat(Roomname);
		} else {
			JOptionPane.showMessageDialog(this, "삭제를 취소하였습니다.");

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

	// 방이름 선택
	public void displayroomName() {
		// 선언
		ChatControl controller = new ChatControl();
		// DB연결
		controller.connection();
		//
		ArrayList<ChatDTO> roomList = controller.searchRoom();
		for (int i = 0; i < roomList.size(); i++) {
			ChatDTO chatcode = roomList.get(i);
			comboBox_room.addItem(chatcode.getRoomname());
		}
		// DB연결 해제
		controller.disconnection();
	}

	// 닉네임 선택
	public void displaynickName() {
		// 선언
		ChatControl controller = new ChatControl();
		// DB연결
		controller.connection();
		//
		ArrayList<ChatDTO> nickList = controller.searchNick();
		for (int i = 0; i < nickList.size(); i++) {
			ChatDTO chatcode = nickList.get(i);
			comboBox_nick.addItem(chatcode.getNickname());
		}
		// DB연결 해제
		controller.disconnection();
	}

}
