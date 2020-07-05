package ChattingProgram;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;
 
//DB ó��
public class MemberDAO {
 
    private static final String DRIVER
        = "oracle.jdbc.driver.OracleDriver";
    private static final String URL
        = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
    
    private static final String USER = "root"; //DB ID
    private static final String PASS = "0000"; //DB �н�����
    Member_List mList;

    
    public MemberDAO() {
    
    }
    
    public MemberDAO(Member_List mList){
        this.mList = mList;
        System.out.println("DAO=>"+mList);
    }
    
    /**DB���� �޼ҵ�*/
    public Connection getConn(){
        Connection con = null;
        
        try {
            Class.forName(DRIVER); //1. ����̹� �ε�
            con = DriverManager.getConnection(URL,USER,PASS); //2. ����̹� ����
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return con;
    }
    
    
    /**�ѻ���� ȸ�� ������ ��� �޼ҵ�*/
    public MemberDTO getMemberDTO(String id){
        
        MemberDTO dto = new MemberDTO();
        
        Connection con = null;       //����
        PreparedStatement ps = null; //���
        ResultSet rs = null;         //���
        
        try {
            
            con = getConn();
            String sql = "select * from tb_member where id=?";
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            
            rs = ps.executeQuery();
            
            if(rs.next()){
                dto.setId(rs.getString("id"));
                dto.setPwd(rs.getString("Pwd"));
                dto.setName(rs.getString("Name"));
                dto.setTel(rs.getString("tel"));
                dto.setAddr1(rs.getString("addr1"));
                
                dto.setPost(rs.getString("post"));
                dto.setChatname(rs.getString("chatname"));
                dto.setGender(rs.getString("gender"));
                dto.setProfilePath(rs.getString("profilepath"));
                dto.setAddr2(rs.getString("addr2"));
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }       
        
        return dto;     
    }
    
    /**�������Ʈ ���*/
    public Vector getMemberList(){
        
        Vector data = new Vector();  //Jtable�� ���� ���� �ִ� ��� 1. 2�����迭   2. Vector �� vector�߰�
        
            
        Connection con = null;       //����
        PreparedStatement ps = null; //���
        ResultSet rs = null;         //���
        
        try{
            
            con = getConn();
            String sql = "select * from tb_member order by name asc";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
                String id = rs.getString("id");
                String pwd = rs.getString("pwd");
                String name = rs.getString("name");
                String tel = rs.getString("tel");
                String addr1 = rs.getString("addr1");
                String addr2 = rs.getString("addr2");
                String post = rs.getString("post");
                String gender = rs.getString("gender");
                String chatname = rs.getString("chatname");
                String profilepath = rs.getString("profilepath");
               
                
                Vector row = new Vector();
                row.add(id);
                row.add(pwd);
                row.add(name);
                row.add(tel);
                row.add(addr1);
                row.add(addr2);
                row.add(post);
                row.add(chatname);
                row.add(gender);
                row.add(profilepath);
               
                
                data.add(row);              
            }//while
        }catch(Exception e){
            e.printStackTrace();
        }
        return data;
    }//getMemberList()
    
 
 
    /**ȸ�� ���*/
    public boolean insertMember(MemberDTO dto){
        
        boolean ok = false;
        
        Connection con = null;       //����
        PreparedStatement ps = null; //���
        
        try{
        	File f = new File(dto.getProfilePath());
            FileInputStream fis = new FileInputStream(f);
            con = getConn();
            String sql = "insert into tb_member(" +
                        "id,pwd,name,tel,addr1,post," +
                        "chatname,gender,profilepath, image,addr2) "+
                        "values(?,?,?,?,?,?,?,?,?,?,?)";
            
            ps = con.prepareStatement(sql);
            ps.setString(1, dto.getId());
            ps.setString(2, dto.getPwd());
            ps.setString(3, dto.getName());
            ps.setString(4, dto.getTel());
            ps.setString(5, dto.getAddr1());
            
            ps.setString(6, dto.getPost());
            ps.setString(7, dto.getChatname());
            ps.setString(8, dto.getGender());
            ps.setString(9, dto.getProfilePath());
            ps.setBinaryStream(10, fis, (int)f.length());
            ps.setString(11, dto.getAddr2());
            int r = ps.executeUpdate(); //���� -> ����
            
            
            if(r>0){
                System.out.println("���� ����");    
                ok=true;
            }else{
                System.out.println("���� ����");
            }
            
                
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return ok;
    }//insertMmeber
    
    
    /**ȸ������ ����*/
    public boolean updateMember(MemberDTO vMem){
        System.out.println("dto="+vMem.toString());
        boolean ok = false;
        Connection con = null;
        PreparedStatement ps = null;
        try{
            
            con = getConn();            
            
            String sql = "update tb_member set id =?, pwd=?, name=?, tel=?, addr1=?, post=?, chatname=?, gender=?" +
                    ", profilepath=? , addr2=? "+ "where id=? ";
            
            ps = con.prepareStatement(sql);
            
            ps.setString(1, vMem.getId());			//ID
            ps.setString(2, vMem.getPwd());			//��й�ȣ
            ps.setString(3, vMem.getName());		//�̸�
            ps.setString(4, vMem.getTel());			//��ȭ��ȣ
            ps.setString(5, vMem.getAddr1());		//
            
            ps.setString(6, vMem.getPost());		//�����ȣ
            ps.setString(7, vMem.getChatname());	//����
            ps.setString(8, vMem.getGender());		//����
            ps.setString(9, vMem.getProfilePath());	//�������
            ps.setString(10, vMem.getAddr2());		//���ּ�
            ps.setString(11, vMem.getId());
   

            
            int r = ps.executeUpdate(); //���� -> ����
            // 1~n: ���� , 0 : ����
            
            if(r>0) ok = false; //������ �����Ǹ� ok���� true�� ����
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return ok;
    }
    
    /**ȸ������ ���� :
     *tip: �ǹ������� ȸ�������� Delete ���� �ʰ� Ż�𿩺θ� üũ�Ѵ�.*/
    public boolean deleteMember(String id, String pwd){
        
        boolean ok =false ;
        Connection con =null;
        PreparedStatement ps =null;
        
        try {
            con = getConn();
            String sql = "delete from tb_member where id=? and pwd=?";
            
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, pwd);
            int r = ps.executeUpdate(); // ���� -> ����
            
            if (r>0) ok=true; //������;
            
        } catch (Exception e) {
            System.out.println(e + "-> �����߻�");
        }       
        return ok;
    }
    
    
    /**DB������ �ٽ� �ҷ�����*/    
    public void userSelectAll(DefaultTableModel model) {
        
        
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            con = getConn();
            String sql = "select * from tb_member order by name asc";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            // DefaultTableModel�� �ִ� ������ �����
            for (int i = 0; i < model.getRowCount();) {
                model.removeRow(0);
            }
 
            while (rs.next()) {
                Object data[] = { rs.getString(1), rs.getString(2),
                        rs.getString(3), rs.getString(4),
                       rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10)};
 
                model.addRow(data);                
            }
 
        } catch (SQLException e) {
            System.out.println(e + "=> userSelectAll fail");
        } finally{
            
            if(rs!=null)
                try {
                    rs.close();
                } catch (SQLException e2) {
                    // TODO Auto-generated catch block
                    e2.printStackTrace();
                }
            if(ps!=null)
                try {
                    ps.close();
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            if(con!=null)
                try {
                    con.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
    }
}