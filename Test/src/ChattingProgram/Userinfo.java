package ChattingProgram;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.Action;
import javax.swing.JButton;
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

import ChattingProgram.*;
import java.awt.Color;

public class Userinfo extends JFrame implements MouseListener, ActionListener{
	
	private Vector v;
	private Vector cols;
	private DefaultTableModel model;
    private JButton  Log_btn = new JButton("접속상태");
    private JFrame frame = new JFrame("접속관리 프로그램 v.0.1.1");
    private JPanel contentPane;
    private JScrollPane scrollPane = new JScrollPane();
    private JTable jTable= new JTable();
    private JPanel panel;
    private JTextField User_tf = new JTextField(); 
    private JButton search = new JButton("검색");
    JLabel lblNewLabel_2 = new JLabel("0");
   
    //JTable의 컬럼
    public Vector getColumn(){// 일정한 표 틀을 만드는 메소드이다.
    	// 첫번째 칼럼이 "시간", 두번째 칼럼이 "닉네임" 세번째 칼럼이.......
    	//|	시간	|	닉네임	|	상태	|	< 이런식
        Vector col = new Vector();
        col.add("시간");
        col.add("닉네임");
        col.add("상태");
        return col;
    }//getColumn
	
	Userinfo(){
		Userinfo_init();
		
	}
	
	void Userinfo_init() {
		frame.setBounds(500,100,800,680);//프레임의 크기와 시작 위치를 지정한다.(x, y, width, height)
    	contentPane = new JPanel();//3개의 패널을 담고있는 배경 패널
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        frame. setContentPane(contentPane);
        contentPane.setLayout(null);//JFrame클래스의 메소드로 파라미터로 받은 String을 프레임의 타이틀로 설정한다.
        
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        UserDAO dao = new UserDAO();//이후에 설명
        v = dao.getUserList();// 정리해보자면 v라는 벡터 안에 DB에서 찾은 값들을 하나씩 대입시키는 역할을 한다.
        System.out.println("v="+"\n"+v); //벡터에 넣은 값들을 확인해본다.
        cols = getColumn();//JTable에서 첫 줄에 해당하는 부분으로, 각 테이블의 칼럼의 이름을 표시한다.
        model = new DefaultTableModel(v, cols); 
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        //JTable은 파라미터로 model을 필요로 하는데, 
        //DefaultTableModel(,)메소드는 데이터로써 가져올 벡터와, 칼럼의 이름으로 가져올 벡터를 파라미터로 사용한다.
        scrollPane.setBounds(10, 80, 780, 600);
        contentPane.add(scrollPane);//현재 프레임에 패널을 추가하였다.
        jTable.setModel(model);
        scrollPane.setViewportView(jTable);//임시적으로 패널을 만들어, 그 위에 JTable을 올렸다.        
        JPanel panel_1 = new JPanel();
        panel_1.setBorder(new TitledBorder(null, "사용자 접속로그 검색", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_1.setBounds(6, 10, 780, 60);
        contentPane.add(panel_1);
        panel_1.setLayout(null);
        JLabel lblNewLabel = new JLabel("사용자명: ");
        lblNewLabel.setBounds(198, 24, 65, 20);
        lblNewLabel.setFont(new Font("210 맨발의청춘 L", Font.PLAIN, 16));
        panel_1.add(lblNewLabel);  
        User_tf.setBounds(268, 24, 116, 21);
        panel_1.add(User_tf);
        User_tf.setColumns(10);
        search.setBounds(389, 22, 88, 25);
        
        search.setFont(new Font("배달의민족 주아", Font.PLAIN, 16));
        panel_1.add(search);
        
        JLabel lblNewLabel_1 = new JLabel("현재 접속한 사용자 수 : ");
        lblNewLabel_1.setBounds(489, 25, 128, 15);
        panel_1.add(lblNewLabel_1);
        
        
        lblNewLabel_2.setText(String.valueOf(Server.user_vc.size()));
        lblNewLabel_2.setForeground(Color.BLUE);
        lblNewLabel_2.setBounds(629, 25, 57, 15);
        panel_1.add(lblNewLabel_2);
        
        search.addActionListener(this);
        jTable.addMouseListener(this); 
        frame.setVisible(true);
	}
	
	//Jtable 내용 갱신 메서드
    public void jTableRefresh(){
        UserDAO dao = new UserDAO(); //MemberDAO클래스 타입의 dao객체를 생성
        DefaultTableModel model= new DefaultTableModel(dao.getUserList(), getColumn());
        //새로운 모델을 만드는데, DefaultTableModel(새로운 객체에서 가져온 데이터를 불러온다, 위에서 만든 표 틀을 가져온다.)
        jTable.setModel(model);  //JTable의 모델을 재설정한다.(갱신된 모델로)
        lblNewLabel_2.setText(String.valueOf(Server.temp2));
    }
	
	public static void main(String[] args) {
        new Userinfo();
    }//main

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==search) {
			UserDAO dao = new UserDAO(); //MemberDAO클래스 타입의 dao객체를 생성
		    DefaultTableModel model= new DefaultTableModel(dao.getUserList_User(User_tf.getText()), getColumn());
		    jTable.setModel(model);  //JTable의 모델을 재설정한다.(갱신된 모델로)
		    System.out.println("검색하기");
		}
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		int r = jTable.getSelectedRow();
        String Nickname = (String) jTable.getValueAt(r, 1);
        //System.out.println("Roomname="+Roomname);
        
        int x = JOptionPane.showConfirmDialog(this,"["+Nickname+"]방의 모든 채팅 기록을 삭제하시겠습니까?","삭제",JOptionPane.YES_NO_OPTION);
        
        if (x == JOptionPane.OK_OPTION){
        	UserDAO.deleteUser(Nickname);
        }else{
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
}
