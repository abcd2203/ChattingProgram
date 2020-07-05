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
    private JButton  Log_btn = new JButton("���ӻ���");
    private JFrame frame = new JFrame("���Ӱ��� ���α׷� v.0.1.1");
    private JPanel contentPane;
    private JScrollPane scrollPane = new JScrollPane();
    private JTable jTable= new JTable();
    private JPanel panel;
    private JTextField User_tf = new JTextField(); 
    private JButton search = new JButton("�˻�");
    JLabel lblNewLabel_2 = new JLabel("0");
   
    //JTable�� �÷�
    public Vector getColumn(){// ������ ǥ Ʋ�� ����� �޼ҵ��̴�.
    	// ù��° Į���� "�ð�", �ι�° Į���� "�г���" ����° Į����.......
    	//|	�ð�	|	�г���	|	����	|	< �̷���
        Vector col = new Vector();
        col.add("�ð�");
        col.add("�г���");
        col.add("����");
        return col;
    }//getColumn
	
	Userinfo(){
		Userinfo_init();
		
	}
	
	void Userinfo_init() {
		frame.setBounds(500,100,800,680);//�������� ũ��� ���� ��ġ�� �����Ѵ�.(x, y, width, height)
    	contentPane = new JPanel();//3���� �г��� ����ִ� ��� �г�
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        frame. setContentPane(contentPane);
        contentPane.setLayout(null);//JFrameŬ������ �޼ҵ�� �Ķ���ͷ� ���� String�� �������� Ÿ��Ʋ�� �����Ѵ�.
        
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        UserDAO dao = new UserDAO();//���Ŀ� ����
        v = dao.getUserList();// �����غ��ڸ� v��� ���� �ȿ� DB���� ã�� ������ �ϳ��� ���Խ�Ű�� ������ �Ѵ�.
        System.out.println("v="+"\n"+v); //���Ϳ� ���� ������ Ȯ���غ���.
        cols = getColumn();//JTable���� ù �ٿ� �ش��ϴ� �κ�����, �� ���̺��� Į���� �̸��� ǥ���Ѵ�.
        model = new DefaultTableModel(v, cols); 
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        //JTable�� �Ķ���ͷ� model�� �ʿ�� �ϴµ�, 
        //DefaultTableModel(,)�޼ҵ�� �����ͷν� ������ ���Ϳ�, Į���� �̸����� ������ ���͸� �Ķ���ͷ� ����Ѵ�.
        scrollPane.setBounds(10, 80, 780, 600);
        contentPane.add(scrollPane);//���� �����ӿ� �г��� �߰��Ͽ���.
        jTable.setModel(model);
        scrollPane.setViewportView(jTable);//�ӽ������� �г��� �����, �� ���� JTable�� �÷ȴ�.        
        JPanel panel_1 = new JPanel();
        panel_1.setBorder(new TitledBorder(null, "����� ���ӷα� �˻�", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_1.setBounds(6, 10, 780, 60);
        contentPane.add(panel_1);
        panel_1.setLayout(null);
        JLabel lblNewLabel = new JLabel("����ڸ�: ");
        lblNewLabel.setBounds(198, 24, 65, 20);
        lblNewLabel.setFont(new Font("210 �ǹ���û�� L", Font.PLAIN, 16));
        panel_1.add(lblNewLabel);  
        User_tf.setBounds(268, 24, 116, 21);
        panel_1.add(User_tf);
        User_tf.setColumns(10);
        search.setBounds(389, 22, 88, 25);
        
        search.setFont(new Font("����ǹ��� �־�", Font.PLAIN, 16));
        panel_1.add(search);
        
        JLabel lblNewLabel_1 = new JLabel("���� ������ ����� �� : ");
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
	
	//Jtable ���� ���� �޼���
    public void jTableRefresh(){
        UserDAO dao = new UserDAO(); //MemberDAOŬ���� Ÿ���� dao��ü�� ����
        DefaultTableModel model= new DefaultTableModel(dao.getUserList(), getColumn());
        //���ο� ���� ����µ�, DefaultTableModel(���ο� ��ü���� ������ �����͸� �ҷ��´�, ������ ���� ǥ Ʋ�� �����´�.)
        jTable.setModel(model);  //JTable�� ���� �缳���Ѵ�.(���ŵ� �𵨷�)
        lblNewLabel_2.setText(String.valueOf(Server.temp2));
    }
	
	public static void main(String[] args) {
        new Userinfo();
    }//main

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==search) {
			UserDAO dao = new UserDAO(); //MemberDAOŬ���� Ÿ���� dao��ü�� ����
		    DefaultTableModel model= new DefaultTableModel(dao.getUserList_User(User_tf.getText()), getColumn());
		    jTable.setModel(model);  //JTable�� ���� �缳���Ѵ�.(���ŵ� �𵨷�)
		    System.out.println("�˻��ϱ�");
		}
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		int r = jTable.getSelectedRow();
        String Nickname = (String) jTable.getValueAt(r, 1);
        //System.out.println("Roomname="+Roomname);
        
        int x = JOptionPane.showConfirmDialog(this,"["+Nickname+"]���� ��� ä�� ����� �����Ͻðڽ��ϱ�?","����",JOptionPane.YES_NO_OPTION);
        
        if (x == JOptionPane.OK_OPTION){
        	UserDAO.deleteUser(Nickname);
        }else{
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
}
