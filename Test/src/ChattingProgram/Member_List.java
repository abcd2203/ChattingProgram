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
    private JButton cancle;	//닫기버튼
    private JButton btnEdit;	//수정버튼
    private JButton btnDel;		//삭제버튼
    private JScrollPane scrollPane;	// 테이블 스크롤바 자동으로 생성되게 하기

    public Member_List(){
        super("회원관리 프로그램");
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
        
        
        jTable.addMouseListener(this); //리스너 등록
        btnInsert.addActionListener(this); //회원가입버튼 리스너 등록
        btnEdit.addActionListener(this); //회원수정 리스너
        btnDel.addActionListener(this); //회원탈퇴 리스너
        setResizable(false);
        this.setSize(800,500);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }//end 생성자
    
    
    //JTable의 컬럼
    public Vector getColumn(){
        Vector col = new Vector();
        col.add("아이디");
        col.add("비밀번호");
        col.add("이름");
        col.add("전화");
        col.add("주소");
        col.add("상세주소");
        col.add("우편번호");
        col.add("별명");
        col.add("성별");
        col.add("사진 경로");
 
        return col;
    }//getColumn
    
    public void deleteMember() {
        String id = (String) jTable.getValueAt(r, 0);
        String pwd = (String) jTable.getValueAt(r, 1);
        int x = JOptionPane.showConfirmDialog(this,"정말 삭제하시겠습니까?","삭제",JOptionPane.YES_NO_OPTION);
        if (x == JOptionPane.OK_OPTION){
                 JOptionPane.showMessageDialog(this, "삭제완료");
                 MemberDAO dao = new MemberDAO();
                 boolean ok = dao.deleteMember(id, pwd);    
        }
        else {
             JOptionPane.showMessageDialog(this, "삭제를 취소하였습니다.");
             }
        //System.out.println(mList);
    }
    
    //Jtable 내용 갱신 메서드 
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
        // mouseClicked 만 사용
        r = jTable.getSelectedRow();
        id = (String) jTable.getValueAt(r, 0);
        ProfilePath = (String)jTable.getValueAt(r, 9);
        
        //System.out.println("id="+id);
        //MemberProc mem = new MemberProc(id,this); //아이디를 인자로 수정창 생성
                
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //버튼을 클릭하면
        if(e.getSource() == btnInsert ){
            new MemberProc(this);
            
            /*테스트*/
            //dao = new MemberDAO();            
            //dao.userSelectAll(model);
            //model.fireTableDataChanged();
            //jTable.updateUI();            
            //jTable.requestFocusInWindow();
            
        }
        //버튼을 클릭하면
        else if(e.getSource() == btnEdit ){		// 수정 버튼 클릭
            //new MemberProc(this);
            System.out.println(r+" "+id);
            id = (String) jTable.getValueAt(r, 0);
            MemberProc mem = new MemberProc(id,ProfilePath,this); //아이디를 인자로 수정창 생성
        }
        
        else if(e.getSource()== btnDel) {	// 삭제 버튼 클릭
        	id = (String) jTable.getValueAt(r, 0);
        	deleteMember();
        }
        jTableRefresh();	// 이거 안하면 갱신 안됨
    
    }
    

	public boolean getIdByCheck(String text) {
		// TODO Auto-generated method stub
		return false;
	}
    
}