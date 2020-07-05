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
 
//DB 처리
public class MemberDAO {
 
    private static final String DRIVER
        = "oracle.jdbc.driver.OracleDriver";
    private static final String URL
        = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
    
    private static final String USER = "root"; //DB ID
    private static final String PASS = "0000"; //DB 패스워드
    Member_List mList;

    
    public MemberDAO() {
    
    }
    
    public MemberDAO(Member_List mList){
        this.mList = mList;
        System.out.println("DAO=>"+mList);
    }
    
    /**DB연결 메소드*/
    public Connection getConn(){
        Connection con = null;
        
        try {
            Class.forName(DRIVER); //1. 드라이버 로딩
            con = DriverManager.getConnection(URL,USER,PASS); //2. 드라이버 연결
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return con;
    }
    
    
    /**한사람의 회원 정보를 얻는 메소드*/
    public MemberDTO getMemberDTO(String id){
        
        MemberDTO dto = new MemberDTO();
        
        Connection con = null;       //연결
        PreparedStatement ps = null; //명령
        ResultSet rs = null;         //결과
        
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
    
    /**멤버리스트 출력*/
    public Vector getMemberList(){
        
        Vector data = new Vector();  //Jtable에 값을 쉽게 넣는 방법 1. 2차원배열   2. Vector 에 vector추가
        
            
        Connection con = null;       //연결
        PreparedStatement ps = null; //명령
        ResultSet rs = null;         //결과
        
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
    
 
 
    /**회원 등록*/
    public boolean insertMember(MemberDTO dto){
        
        boolean ok = false;
        
        Connection con = null;       //연결
        PreparedStatement ps = null; //명령
        
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
            int r = ps.executeUpdate(); //실행 -> 저장
            
            
            if(r>0){
                System.out.println("가입 성공");    
                ok=true;
            }else{
                System.out.println("가입 실패");
            }
            
                
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return ok;
    }//insertMmeber
    
    
    /**회원정보 수정*/
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
            ps.setString(2, vMem.getPwd());			//비밀번호
            ps.setString(3, vMem.getName());		//이름
            ps.setString(4, vMem.getTel());			//전화번호
            ps.setString(5, vMem.getAddr1());		//
            
            ps.setString(6, vMem.getPost());		//우편번호
            ps.setString(7, vMem.getChatname());	//별명
            ps.setString(8, vMem.getGender());		//성별
            ps.setString(9, vMem.getProfilePath());	//사진경로
            ps.setString(10, vMem.getAddr2());		//상세주소
            ps.setString(11, vMem.getId());
   

            
            int r = ps.executeUpdate(); //실행 -> 수정
            // 1~n: 성공 , 0 : 실패
            
            if(r>0) ok = false; //수정이 성공되면 ok값을 true로 변경
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return ok;
    }
    
    /**회원정보 삭제 :
     *tip: 실무에서는 회원정보를 Delete 하지 않고 탈퇴여부만 체크한다.*/
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
            int r = ps.executeUpdate(); // 실행 -> 삭제
            
            if (r>0) ok=true; //삭제됨;
            
        } catch (Exception e) {
            System.out.println(e + "-> 오류발생");
        }       
        return ok;
    }
    
    
    /**DB데이터 다시 불러오기*/    
    public void userSelectAll(DefaultTableModel model) {
        
        
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            con = getConn();
            String sql = "select * from tb_member order by name asc";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            // DefaultTableModel에 있는 데이터 지우기
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