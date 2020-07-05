package ChattingProgram;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
 
public class zipControl {
         Connection conn;
         PreparedStatement pstmt;
         ResultSet rs;
         
         
         // �����ͺ��̽� ����
         public void connection() {
                  try {
                           Class.forName("oracle.jdbc.driver.OracleDriver");
                           conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:orcl", "root", "0000");
                           
                  } catch (ClassNotFoundException e) {
                  } catch (SQLException e) {
                  }
         }
         
         // �����ͺ��̽� ��������
         public void disconnection() {
                  try {
                           if(pstmt != null) pstmt.close();
                           if(rs != null) rs.close();
                           if(conn != null) conn.close();
                  } catch (SQLException e) {
                  }
         }
         
         // �õ�������=============================================
         public ArrayList<zipDAO> searchSido() {
                  ArrayList<zipDAO> sidoList = new ArrayList<>();
                  try {
                           String query = "select distinct(sido) from zipcode";
                           pstmt = conn.prepareStatement(query);
                           rs = pstmt.executeQuery();
                           while(rs.next()){
                                   zipDAO zipcode = new zipDAO();
                                   zipcode.setSido(rs.getString("SIDO"));
                                   sidoList.add(zipcode);
                           }
                  } catch (SQLException e) {
                  }
 
                  return sidoList;
                  
         }
         
         // ����������=============================================
         public ArrayList<zipDAO> searchGugun(String sido) {
                  ArrayList<zipDAO> gugunList = new ArrayList<>();
                  
                  try {
                           String query = "select distinct(gugun) from zipcode where sido = \'" + sido + "\' ";
                           pstmt = conn.prepareStatement(query);
                           rs = pstmt.executeQuery();
                           while(rs.next()){
                                   zipDAO zipcode = new zipDAO();
                                   zipcode.setGugun(rs.getString("GUGUN"));
                                   gugunList.add(zipcode);
                           }
                  } catch (SQLException e) {
                  }
                                   
                  return gugunList;          
         }
         
         // ��������=============================================
         public ArrayList<zipDAO> searchDong(String sido, String gugun) {
                  ArrayList<zipDAO> dongList = new ArrayList<>();
                  
                  try {
                           String query = "select distinct(dong) from zipcode where sido = \'" + sido + "\'  and gugun = \'" + gugun + "\'";
                           pstmt = conn.prepareStatement(query);
                           rs = pstmt.executeQuery();
                           while(rs.next()){
                                   zipDAO zipcode = new zipDAO();
                                   zipcode.setDong(rs.getString("DONG"));
                                   dongList.add(zipcode);
                           }
                  } catch (SQLException e) {
                  }
                  
                  
                  return dongList;           
         }
         
         // �����ּ� ������ =============================================
         public ArrayList<zipDAO> searchAddress(String sido, String gugun, String dong) {
                  ArrayList<zipDAO> addressList = new ArrayList<>();
                  
                  
                  try {
                           String query = "select * from zipcode where sido = \'" + sido + "\'  and gugun = \'" + gugun + "\' and dong = \'" + dong +"\'";
                           pstmt = conn.prepareStatement(query);
                           rs = pstmt.executeQuery();
                           while(rs.next()){
                                   zipDAO zipcode = new zipDAO();
                                   zipcode.setSeq(rs.getString("seq"));
                                   zipcode.setZipcode(rs.getString("zipcode"));
                                   zipcode.setSido(rs.getString("sido"));
                                   zipcode.setGugun(rs.getString("gugun"));
                                   zipcode.setDong(rs.getString("dong"));
                                   zipcode.setRi(rs.getString("ri"));
                                   zipcode.setSt_bunji(rs.getString("st_bunji"));
                                   zipcode.setEd_bunji(rs.getString("ed_bunji"));
                                   addressList.add(zipcode);
                           }
                  } catch (SQLException e) {
                  }
                  
                  
                  return addressList;                
         }
}