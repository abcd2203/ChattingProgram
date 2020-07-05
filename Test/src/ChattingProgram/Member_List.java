package ChattingProgram;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
 
 
public class Member_List extends JFrame implements MouseListener,ActionListener{
    
    Vector v;   
    Vector cols;
    DefaultTableModel model;
    JTable jTable;
    JScrollPane pane;
    JPanel pbtn;
    JButton btnInsert;
    private JButton cancle;	//�ݱ��ư
    private JButton btnEdit;	//������ư
    private JButton btnDel;		//������ư
    private JScrollPane scrollPane;	// ���̺� ��ũ�ѹ� �ڵ����� �����ǰ� �ϱ�

    public Member_List(){
        super("ȸ������ ���α׷�");
        //v=getMemberList();
        //MemberDAO 
        MemberDAO dao = new MemberDAO();
        v = dao.getMemberList();
        System.out.println("v="+v);
        cols = getColumn();
        
        //public DefaultTableModel()
        //public DefaultTableModel(int rowCount, int columnCount)
        //public DefaultTableModel(Vector columnNames, int rowCount)
        //public DefaultTableModel(Object[] columnNames, int rowCount)
        //public DefaultTableModel(Vector data,Vector columnNames)
        //public DefaultTableModel(Object[][] data,Object[] columnNames)
        
        model = new DefaultTableModel(v, cols);
        
        //JTable() 
        //JTable(int numRows, int numColumns)
        //JTable(Object[][] rowData, Object[] columnNames) 
        //JTable(TableModel dm) 
        //JTable(TableModel dm, TableColumnModel cm) 
        //JTable(TableModel dm, TableColumnModel cm, ListSelectionModel sm) 
        //JTable(Vector rowData, Vector columnNames) 
        
        //jTable = new JTable(v,cols);
        jTable = new JTable(model);
        pane = new JScrollPane(jTable);
        getContentPane().add(pane);
        
        pbtn = new JPanel();
        btnInsert = new JButton("\uD68C\uC6D0\uCD94\uAC00");
        pbtn.add(btnInsert);
        getContentPane().add(pbtn,BorderLayout.SOUTH);
        
        cancle = new JButton("\uB2EB\uAE30");
        cancle.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Member_List.this.setVisible(false);
        	}
        });
        
        btnEdit = new JButton("\uD68C\uC6D0\uC218\uC815");

        pbtn.add(btnEdit);
        
        btnDel = new JButton("\uD68C\uC6D0\uC0AD\uC81C");
        pbtn.add(btnDel);
        pbtn.add(cancle);
        
        
        jTable.addMouseListener(this); //������ ���
        btnInsert.addActionListener(this); //ȸ�����Թ�ư ������ ���
        btnEdit.addActionListener(this); //ȸ������ ������
        btnDel.addActionListener(this); //ȸ��Ż�� ������
        setResizable(false);
        this.setSize(800,500);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }//end ������
    
    
    //JTable�� �÷�
    public Vector getColumn(){
        Vector col = new Vector();
        col.add("���̵�");
        col.add("��й�ȣ");
        col.add("�̸�");
        col.add("��ȭ");
        col.add("�ּ�");
        col.add("���ּ�");
        col.add("�����ȣ");
        col.add("����");
        col.add("����");
        col.add("���� ���");
 
        return col;
    }//getColumn
    
    public void deleteMember() {
        String id = (String) jTable.getValueAt(r, 0);
        String pwd = (String) jTable.getValueAt(r, 1);
        int x = JOptionPane.showConfirmDialog(this,"���� �����Ͻðڽ��ϱ�?","����",JOptionPane.YES_NO_OPTION);
        if (x == JOptionPane.OK_OPTION){
                 JOptionPane.showMessageDialog(this, "�����Ϸ�");
                 MemberDAO dao = new MemberDAO();
                 boolean ok = dao.deleteMember(id, pwd);    
        }
        else {
             JOptionPane.showMessageDialog(this, "������ ����Ͽ����ϴ�.");
             }
        //System.out.println(mList);
    }
    
    //Jtable ���� ���� �޼��� 
    public void jTableRefresh(){
        
        MemberDAO dao = new MemberDAO();
        DefaultTableModel model= new DefaultTableModel(dao.getMemberList(), getColumn());
        jTable.setModel(model);     
        
    }
    public static void main(String[] args) {
        new Member_List();
    }//main
    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
    int r;
    String id,pwd,ProfilePath;
    @Override
    public void mouseClicked(MouseEvent e) {
        // mouseClicked �� ���
        r = jTable.getSelectedRow();
        id = (String) jTable.getValueAt(r, 0);
        ProfilePath = (String)jTable.getValueAt(r, 9);
        
        //System.out.println("id="+id);
        //MemberProc mem = new MemberProc(id,this); //���̵� ���ڷ� ����â ����
                
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //��ư�� Ŭ���ϸ�
        if(e.getSource() == btnInsert ){
            new MemberProc(this);
            
            /*�׽�Ʈ*/
            //dao = new MemberDAO();            
            //dao.userSelectAll(model);
            //model.fireTableDataChanged();
            //jTable.updateUI();            
            //jTable.requestFocusInWindow();
            
        }
        //��ư�� Ŭ���ϸ�
        else if(e.getSource() == btnEdit ){		// ���� ��ư Ŭ��
            //new MemberProc(this);
            System.out.println(r+" "+id);
            id = (String) jTable.getValueAt(r, 0);
            MemberProc mem = new MemberProc(id,ProfilePath,this); //���̵� ���ڷ� ����â ����
        }
        
        else if(e.getSource()== btnDel) {	// ���� ��ư Ŭ��
        	id = (String) jTable.getValueAt(r, 0);
        	deleteMember();
        }
        jTableRefresh();	// �̰� ���ϸ� ���� �ȵ�
    
    }
    

	public boolean getIdByCheck(String text) {
		// TODO Auto-generated method stub
		return false;
	}
    
}