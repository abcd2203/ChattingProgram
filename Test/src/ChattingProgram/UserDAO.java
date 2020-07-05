package ChattingProgram;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import ChattingProgram.*;

public class UserDAO {
	private static String Driver = "oracle.jdbc.driver.OracleDriver";
	private static String Url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
	private static String User = "root";
	private static String Pass = "0000";
	
	public static Connection getConn(){
        Connection con = null;//DB ���� �ʱ�ȭ
        try {
            Class.forName(Driver); //1. ����̹� �ε�
            con = DriverManager.getConnection(Url,User,Pass); //2. ����̹� ����
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }
	
	public ChatDTO getUserDTO(String Nickname){
		ChatDTO dto = new ChatDTO();
        Connection con = null;       //����
        PreparedStatement ps = null; //���
        ResultSet rs = null;         //���
        try {
            con = getConn();
            String sql = "select * from Userinfo where Nickname=?";
            ps = con.prepareStatement(sql);//prepareStatement()�޼ҵ带 ���� ���� ���ε�
            System.out.println("ps="+ps);
            ps.setString(2, Nickname);		
          //������ Member_List���� ������ ȸ����ü��
          //MemberProc�� ����,������ �Ķ���͸� �޴� ��ü�� ���� �� �޼ҵ带 ȣ��, �� �� ���̵� �ҷ��´�.
            System.out.println("ps="+ps);	
            rs = ps.executeQuery();
            System.out.println("rs="+rs);
            if(rs.next()){
            	dto.setTime(rs.getString("Time"));
                dto.setNickname(rs.getString("Nickname"));
                dto.setMsg(rs.getString("Msg"));
               System.out.println(rs.getString("Roomname")+"����߿���");
               //�����Ͱ� �� ������ Ȯ���Ѵ�.
            }
        } catch (Exception e) {
            e.printStackTrace();
        }      
        return dto;    
    }
	
	 public Vector getUserList(){//���̸��� �ֱ� ��ϵ� �ð�
         Vector data = new Vector();  //Jtable�� ���� ���� �ִ� ��� 1. 2�����迭   2. Vector �� vector�߰�
  Connection con = null;       //����
  PreparedStatement ps = null; //���
  ResultSet rs = null;         //��� 
  try{
      con = getConn();
      String sql = "select * from Userinfo order by Time asc";
      ps = con.prepareStatement(sql);
      rs = ps.executeQuery();
      while(rs.next()){
    	  String Time = rs.getString("Time");
    	  String Nickname = rs.getString("Nickname");
          String Msg = rs.getString("Msg");
          Vector row = new Vector();                                                                                                                 //
          row.add(Time);
          row.add(Nickname);
          row.add(Msg);
          data.add(row);             
      }//while
  }catch(Exception e){
      e.printStackTrace();
  }
  return data;
}//getMemberList1()
	 public Vector getUserList_User(String a){//���̸��� �ֱ� ��ϵ� �ð�
         Vector data = new Vector();  //Jtable�� ���� ���� �ִ� ��� 1. 2�����迭   2. Vector �� vector�߰�
  Connection con = null;       //����
  PreparedStatement ps = null; //���
  ResultSet rs = null;         //��� 
  try{
      con = getConn();
      String sql = "select * from Userinfo where Nickname like '"+a+"' order by Time asc";
      ps = con.prepareStatement(sql);
      rs = ps.executeQuery();
      while(rs.next()){
    	  String Time = rs.getString("Time");
          String Nickname = rs.getString("Nickname");
          String Msg = rs.getString("Msg");
          Vector row = new Vector();
          row.add(Time);
          row.add(Nickname);
          row.add(Msg);
          data.add(row);             
      }//while
  }catch(Exception e){
      e.printStackTrace();
  }
  return data;
}//getMemberList1()
	 public static boolean deleteUser(String Nickname){
	        boolean ok =false ;
	        Connection con =null;
	        PreparedStatement ps =null;
	        try {
	            con = getConn();
	            String sql = "delete from Userinfo where Nickname=?";
	            ps = con.prepareStatement(sql);
	            ps.setString(1, Nickname);		//IDȮ���ϰ� �� ��(����)�� �����.
	            int r = ps.executeUpdate(); // ���� -> ����
	            if (r>0) ok=true; //������;
	       } catch (Exception e) {
	            System.out.println(e + "-> �����߻�");
	            }      
	        return ok;
	    }
}
