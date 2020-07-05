// ������ ����� Server(���� ���� â)

package ChattingProgram;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket; // �ڹ� ���� ServerSocket�� import
import java.net.Socket; // �ڹ� ���� Socket�� import
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame; // JFrame ȯ��
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


import java.awt.Font;

public class Server extends JFrame implements ActionListener{
// 1. JFrame�� �������� ���α׷��� �����ϱ� ������, JFrame�κ��� ��ӹ޴´�.
                                               // 2. ���� ��� ������ �̺�Ʈ �޼ҵ�
                                               // 3. �߻� �޼ҵ��̹Ƿ� �޼ҵ带 ������    
   // ctrl + shift + o (JFrame ȯ���� �ڵ����� import ������)
   

   
   public static ImageIcon icon;

   static JFrame Server_GUI = new JFrame(); // Server_GUI�� ���� ��ü�� ����
   private JPanel contentPane;
   private JTextArea textArea = new JTextArea(); // ���α׷��� �����ϱ� ���� textArea�� ������ ��ü�� ���������� �� 
   private JButton StartButton = new JButton("���� ����"); // ��ư �̸��� �����ڵ� ���·� �Ǿ������� �˾ƺ��� ����� ������, �˾ƺ��� ���� ��ư��� �����ϰ� �Է��Ѵ�.
   private JButton StopButton = new JButton("���� ����");
   private JButton ManagerButton = new JButton("������ ���");
   public static int temp2;
   // Network �ڿ�
   private ServerSocket server_socket;
   private Socket socket;
   private int port;
   public static Vector user_vc = new Vector(); // �����带 ������ ���� �� �� �ֵ��� ���� ��ü
                                 // (InputStream is; OutputStream os &&
                                 // DataInputStream dis; DataOutputStream dos; ������ �����带 ���� ��������)
   private Vector room_vc = new Vector();// �� ����� ���Ͷ�� �����ϰ�, ���� �ȿ� ���� ���� ���� 
   
   private StringTokenizer st;
   
   private static final JComponent ManagerLogin_GUI = null;
   private GregorianCalendar calendar = new GregorianCalendar();
   private static String Driver = "oracle.jdbc.driver.OracleDriver";
   private static String Url = "jdbc:oracle:thin:@localhost:1521:orcl";
   private static String User = "root";
   private static String Pass = "0000";
   private JTextField port_tf;
      
   Server() // Ŭ������ ��ü�� ����� ���� ������
   { 
      init(); // ȭ�� ���� �޼ҵ�
      start(); // ���� �α��� �޼ҵ� (������ ���� �޼ҵ�)
   }
   
   private void start() // start �޼ҵ� (������ �����ϱ� ����)
   {
      StartButton.addActionListener(this); // Consoleâ�� �ش� ActionEvent�� ������ �ϱ� ����
      StopButton.addActionListener(this);
   }
   private void init() // init �޼ҵ� (���� ���� â�� ȭ���� �����ϱ� ����)
   {
      
      setTitle("Server");
      Server_GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      Server_GUI.setBounds(100, 100, 445, 546);
      
      this.setSize(750,550);
      setLocation(650, 250);
      String image = "C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\2.png";
      ImageIcon icon1 = new ImageIcon(image);
      contentPane = new JPanel()
      {
         public void paintComponent(Graphics g) {
                //  Approach 1: Dispaly image at at full size
                g.drawImage(icon1.getImage(), 0, 0, null);
                //  Approach 2: Scale image to size of component
                // Dimension d = getSize();
                // g.drawImage(icon.getImage(), 0, 0, d.width, d.height, null);
                // Approach 3: Fix the image position in the scroll pane
                // Point p = scrollPane.getViewport().getViewPosition();
                // g.drawImage(icon.getImage(), p.x, p.y, null);
                setOpaque(false);
                super.paintComponent(g);
         }
      };

      
      
      
      contentPane.setBackground(Color.GRAY);
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      setContentPane(contentPane);
      contentPane.setLayout(null);
      
      
      JScrollPane scrollPane = new JScrollPane();
      scrollPane.setBounds(110, 60, 510, 300);
      contentPane.add(scrollPane);
      textArea.setEditable(false);
      
      scrollPane.setViewportView(textArea);
      
      
      /*
       * JLabel lblNewLabel = new JLabel("��Ʈ ��ȣ"); lblNewLabel.setBounds(12, 331, 67,
       * 15); contentPane.add(lblNewLabel);
       */
      
      /*
       * port_tf = new JTextField(); // ��Ʈ��ũ ���� ���� ǥ�� â�� �����ϴ� ��ü ����
       * port_tf.setBounds(101, 328, 306, 21); contentPane.add(port_tf);
       * port_tf.setColumns(10);
       */
       
   
      
      StartButton.setBounds(165, 420, 190, 25);
      contentPane.add(StartButton);
      
      
      StopButton.setBounds(375, 420, 190, 25);
      contentPane.add(StopButton);
      StopButton.setEnabled(false);
      
      ManagerButton.setBounds(165, 460, 400, 30);
      ManagerButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent args0) {
        	 Manager frame = new Manager("7508");
         }

      });
      contentPane.add(ManagerButton);
      
      JPanel panel = new JPanel();
      panel.setBackground(Color.LIGHT_GRAY);
      panel.setBounds(90, 40, 550, 340);
      contentPane.add(panel);
      
      port_tf = new JTextField();
      port_tf.setFont(new Font("����", Font.PLAIN, 15));
      port_tf.setBounds(292, 390, 135, 21);
      contentPane.add(port_tf);
      port_tf.setColumns(10);
      port_tf.setText("12345");
      
      
      setResizable(false);
      this.setVisible(true); // true = ��� ���� ��Ұ� ȭ�鿡 ���̰�, false = ��� ���� ��Ұ� ȭ�鿡 ������ �ʰ�
   }

   private void Server_start() // ���� â���� ���� ������ �ϱ� ���� �޼ҵ� (���� ����)
   {
      
      try {
         server_socket = new ServerSocket(port); // ���������� ��Ʈ��ȣ�� ����Ѵ�.
      } catch (IOException e) { // ���� ���� ���п� ���� ����ó���Ѵ�.
         JOptionPane.showMessageDialog(null, "�̹� ������� ��Ʈ", "�˸�", JOptionPane.ERROR_MESSAGE);
      } // 12345��Ʈ ���
      
      if(server_socket != null) // ���������� ��Ʈ�� ������ ���
      {
         Connection(); // �ش� ��Ʈ��ũ�� �ٽ� ���� ������
         StartButton.setEnabled(false); // ���� ����Ǹ� ��ŸƮ��ư ��Ȱ��ȭ
         port_tf.setEditable(false); // ���� ����Ǹ� ��Ʈ��ȣ ��Ȱ��ȭ
         StopButton.setEnabled(true); // ���� ����Ǹ� ��ž��ư Ȱ��ȭ
      }
      
      
   }
   
   private void Connection() // ����ڰ� �����ϱ� ���� ���� ���� �޼ҵ�
   {
      
      // 1������ �����忡���� 1������ �ϸ� ó���� �� �ִ�.
      Thread th = new Thread(new Runnable() {
         
         @Override
         public void run() { // �����忡�� ó���� ���� �����Ѵ�. (����� �����ϴ� ���� ó��)
            
            while(true) {
            try {
               
               textArea.append("����� ���� �����..."+"( "+port_tf.getText().trim()+"�� ��Ʈ )"+"\n"); // ��Ʈ��ũ ���� ���� ǥ���ٿ� �� �޽����� �߰���
               socket = server_socket.accept(); // ����ڰ� ������ ���� ������ �� ���� ������ ���Ѵ�� ������
               textArea.append("����� ����!!!\n");
               
               UserInfo user = new UserInfo(socket);
            
               user.start(); // ��ü�� ������ ����
               
               
               } catch (IOException e) {
                  break;
                } // ���� ���н� ���� ó��
 catch (SQLException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
            }
            } // while �� �� 
            
         }
         
      }); 
      
      th.start(); // ���� ����
   
   }
   
   public static void main(String[] args) {


      new Server(); // ���� Server()�����ڿ� ���� ��ü�� ����
   
   
   
      
   }

   @Override
   public void actionPerformed(ActionEvent e) { // 1. �޼ҵ带 ������
                                       
      if(e.getSource() == StartButton) // ���� �����
      {
         System.out.println("���� ���� ��ư Ŭ��"); // ���� �����ư�� ������ �� ��Ÿ���� �޽��� 
         
         port = Integer.parseInt(port_tf.getText().trim()); // ��Ʈ ��ȣ�� �Է�
         
         if(port_tf.getText().trim()==null) {
             JOptionPane.showMessageDialog(null, "��Ʈ��ȣ�� �Է����ּ���!", "���", JOptionPane.ERROR_MESSAGE);
          }
          else {
         Server_start(); // ���� ���� �� ����� ���� ��� (������ ����ȴ�)
          }

      }
      else if(e.getSource() == StopButton) // ���� ������
      {
    	  StopButton.setEnabled(false); // ���� �����Ǹ� ��ž��ư ��Ȱ��ȭ
          StartButton.setEnabled(true); // ���� �����Ǹ� ��ŸƮ��ư Ȱ��ȭ
          ManagerButton.setEnabled(true); // ���� �����Ǹ� �����ڸ�� Ȱ��ȭ
          port_tf.setEditable(true); // ���� �����Ǹ� ��Ʈ��ȣ Ȱ��ȭ
          textArea.append("( "+port_tf.getText().trim()+"�� ��Ʈ )"+" ���� ����\n");
         
         try {
            server_socket.close();
            user_vc.removeAllElements(); // user_vc�� ����� ����
            room_vc.removeAllElements(); // room_vc�� ����� ����
         } catch (IOException e1) {
            e1.printStackTrace();
         } // ���� ���� ����
         
         
         System.out.println("���� ���� ��ư Ŭ��"); // ���� ������ư�� ������ �� ��Ÿ���� �޽���
      }
      
      else if(e.getSource() == ManagerButton)
      {
         
   
      }
      
   
         
   } // �׼� �̺�Ʈ ��

   class UserInfo extends Thread
   {
      
      private InputStream is;
      private OutputStream os;
      private DataInputStream dis;
      private DataOutputStream dos;
      
      private Socket user_socket;
      private String NickName = ""; // �ڽ��� �г����� ����
      
      private boolean RoomCh = true;
      
      UserInfo(Socket soc) throws SQLException // ������ �޼ҵ� (������ ���� ����)
      {
         this.user_socket = soc;
         UserNetwork();
      }
      
      private void UserNetwork() throws SQLException // ��Ʈ��ũ �ڿ� ����
      {
         try {
            calendar = new GregorianCalendar();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH)+1;
            int date = calendar.get(Calendar.DATE);
            int amPm = calendar.get(Calendar.AM_PM);
            int hour = calendar.get(Calendar.HOUR);
            int min = calendar.get(Calendar.MINUTE);
            int sec = calendar.get(Calendar.SECOND);
            String sAmPm = amPm == Calendar.AM ? "����" : "����";
            Connection con = null;       //����
             PreparedStatement ps = null; //���
            is = user_socket.getInputStream();
            dis = new DataInputStream(is);
            os = user_socket.getOutputStream();
            dos = new DataOutputStream(os);
            
            NickName = dis.readUTF(); // ������� �г����� �޴´�.
            textArea.append(NickName + " : ����� ����!");
            con = getConn();
              String sql = "insert into Userinfo(" +"Time,Nickname,Msg)"+"values(?,?,?)";
              ps = null;
            ps = con.prepareStatement(sql);
            ps.setString(1, year+"."+month+"."+date+"."+sAmPm+hour+":"+min+":"+sec);
            ps.setString(2, NickName);
               ps.setString(3, "���� ����");
               ps.executeUpdate();
            // ���� ����ڵ鿡�� ���ο� ����� �˸�
            System.out.println("���� ���ӵ� ����� ��: " + user_vc.size()+1); // Consoleâ�� ���� ä�ù濡 ���ӵ� ����� �� ���(���� ����)
            BroadCast("NewUser/" + NickName); // ���� ����ڿ��� �ڽ��� �˸���
            
            // �ڽſ��� ���� ����ڸ� �˸�
            for(int i=0; i<user_vc.size(); i++)
            {
               UserInfo u = (UserInfo)user_vc.elementAt(i);
               send_Message("OldUser/" + u.NickName);
            }
            
            // �ڽſ��� ���� �� ����� �޾ƿ��� �κ�
            for(int i=0; i<room_vc.size(); i++)
            {
               RoomInfo r = (RoomInfo)room_vc.elementAt(i);
               
               send_Message("OldRoom/" + r.Room_name);
            }
            
            send_Message("room_list_update/ "); // ���� ���� ���� �˸�
            user_vc.add(this); // ����ڿ��� �˸� �� Vector�� �߰�
            BroadCast("user_list_update/ ");
         }
         catch(IOException e) {
            JOptionPane.showMessageDialog(null, "Stream���� ����", "�˸�", JOptionPane.ERROR_MESSAGE);
         }
      }
      
      public void run()   // Thread���� ó���� ����
      {
         Connection con = null;       //����
           PreparedStatement ps = null; //���
           con = getConn();
           String sql = "insert into Userinfo(" +"Time,Nickname,Msg)"+"values(?,?,?)";
         while(true)
         {
            try {
               String msg = dis.readUTF();
               textArea.append(NickName+ " : ����ڷκ��� ���� �޼��� : " + msg + "\n");
               InMessage(msg);
            }
            catch(IOException e) { // ������ ����������
               textArea.append(NickName + " : ����� ���� ������\n"); // ���� ���� ���� â�� �� �޽��� ���
               try
               { 
                  calendar = new GregorianCalendar();
                  int year = calendar.get(Calendar.YEAR);
                  int month = calendar.get(Calendar.MONTH)+1;
                  int date = calendar.get(Calendar.DATE);
                  int amPm = calendar.get(Calendar.AM_PM);
                  int hour = calendar.get(Calendar.HOUR);
                  int min = calendar.get(Calendar.MINUTE);
                  int sec = calendar.get(Calendar.SECOND);
                  String sAmPm = amPm == Calendar.AM ? "����" : "����";
                  // �������� �������� ���� ��� �ڿ��� ��� �ݳ��ؾ���
                  
                  dos.close();
                  dis.close();
                  user_socket.close(); // ����� ���� ����
                  
                  for(int i=0;i<user_vc.size();i++) {
                     if(user_vc.elementAt(i)==NickName) {
                        user_vc.removeElementAt(i);
                     }
                  }
                  ps = null;
                  ps = con.prepareStatement(sql);
                  ps.setString(1, year+"."+month+"."+date+"."+sAmPm+hour+":"+min+":"+sec);
                  ps.setString(2, NickName);
                     ps.setString(3, "���� ����");
                     ps.executeUpdate();               
                  user_vc.remove(this); // �������� ���� ����ڸ� ����Ʈ���� ����
                  BroadCast("User_out/" + NickName);
                  BroadCast("user_list_update/ ");
               }
               catch(IOException e1) {} 
               catch (SQLException e1) {
                  // TODO Auto-generated catch block
                  e1.printStackTrace();
               } 
               break;
               
            }   // �޼��� ����
            catch (SQLException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
            }
            
         } 
         
      } // run �޼ҵ� ��
      
      
      private void InMessage(String str) throws SQLException // Ŭ���̾�Ʈ�� ���� ������ ���� �޽��� ó��
      {
         
         st = new StringTokenizer(str,"/");
         
         String protocol = st.nextToken();
         String message = st.nextToken();
         
         System.out.println("�������� : " + protocol); // Consoleâ�� �������� ���� ���
         System.out.println("�޼��� : " + message); // Consoleâ�� �޼��� ���� ���
         
         
         if(protocol.equals("Note"))
         {
            // protocol = Note
            // message = user2
            // note = �޴� ����
            
   
            String note = st.nextToken();
            
   
            System.out.println("�޴� ��� : " + message);
            System.out.println("���� ���� : " + note);
            
            // ���Ϳ��� �ش� ����ڸ� ã�Ƽ� �޼��� ����
            
            for(int i = 0; i<user_vc.size(); i++)
            {
               UserInfo u = (UserInfo)user_vc.elementAt(i);
               
               if(u.NickName.equals(message)) // �г����� �ڽ��� ã�� ����� ������
               {
                  u.send_Message("Note/" + NickName + "/" + note);
                  // Note/User1@~~~~
               }
            }
            
            
         }// if����
         else if(protocol.equals("CreateRoom"))
         {
            //1. ���� ���� ���� �����ϴ��� Ȯ���Ѵ�.
            
            for(int i = 0; i<room_vc.size(); i++)
            {
               RoomInfo r = (RoomInfo)room_vc.elementAt(i);
               
               if(r.Room_name.equals(message)) // ������� �ϴ� ���� �̹� ���� �� ��
               {
                  send_Message("CreateRoomFail/ok");
                  RoomCh = false; // ���� ���� �� ����.
                  break;
               }
               
                
            } // for ��
            
            if(RoomCh) // �� ����
            {
               RoomInfo new_room = new RoomInfo(message, this);
               room_vc.addElement(new_room); // ��ü �� ���Ϳ� ���� �߰�
               
               send_Message("CreateRoom/" + message);
               
               
               BroadCast("New_Room/" + message); // ��� ����鿡�� ���� ���� �� ���� �˷��� �� ����
            }
            
            RoomCh = true;

         } // else if�� ��
         
         else if(protocol.equals("Chatting")) // ä���� ��
         {
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH)+1;
            int date = calendar.get(Calendar.DATE);
            int amPm = calendar.get(Calendar.AM_PM);
            int hour = calendar.get(Calendar.HOUR);
            int min = calendar.get(Calendar.MINUTE);
            int sec = calendar.get(Calendar.SECOND);
            String sAmPm = amPm == Calendar.AM ? "����" : "����";
            Connection con = null;       //����
              PreparedStatement ps = null; //���
              con = getConn();


              String sql = "insert into log(" +
                     "Roomname,Nickname,Time,Msg)"+
                           "values(?,?,?,?)";
 
            String msg = st.nextToken();
            System.out.println(msg);
            String Str=null;
            //File f = new File(dto.getProfilePath());
            //FileInputStream fis = new FileInputStream(f);
            
            
            for(int i=0; i <room_vc.size();i++)
            {
               RoomInfo r = (RoomInfo)room_vc.elementAt(i);
               if(r.Room_name.equals(message))//�ش� ���� ã������
               {
                  if(msg.length()==0) {
                     
                     ps = null;
                     ps = con.prepareStatement(sql);
                        ps.setString(1, r.Room_name);
                        ps.setString(2, NickName);
                        ps.setString(3, year+"."+month+"."+date+"."+sAmPm+hour+":"+min+":"+sec);
                        ps.setString(4, msg);
                       // ps.setBinaryStream(5, fis, (int)f.length());
                        ps.executeUpdate();

                     
            
                     }
                              
                     else {
                        //r.BroadCast_Room("Chatting/"+Nickname+"/"+msg);
         
                     /*Str="Chatting/"+r.Room_name+"/"+Nickname+"/"+msg;
                     DBUpload.DBUploader(Str);*/
                     ps = null;
                     ps = con.prepareStatement(sql);
                        ps.setString(1, r.Room_name);
                        ps.setString(2, NickName);
                        ps.setString(3, year+"."+month+"."+sAmPm+hour+":"+min+":"+sec);
                        ps.setString(4, msg);
                        //ps.setBinaryStream(5, fis, (int)f.length());
                        ps.executeUpdate();
                       
                  }
               }
            
         //else if ��
            }
         

            
            for(int i = 0; i < room_vc.size(); i++)
            {
               RoomInfo r = (RoomInfo)room_vc.elementAt(i);
               
               if(r.Room_name.equals(message)) // �ش� ���� ã������
               {
                  r.BroadCast_Room("Chatting/" + NickName + "/" + msg + "\n");
               }
            }
         } // else if
         
         else if(protocol.equals("JoinRoom")) // ��ȭ�� ����
         {
            for(int i=0; i<room_vc.size(); i++)
            {
               RoomInfo r = (RoomInfo)room_vc.elementAt(i); // ���� �� ��ȭ�� ã��
               
               if(r.Room_name.equals(message))
               {
                  // ���ο� ����ڸ� �˸���
                  r.BroadCast_Room("Chatting/�˸�/***** " + NickName + "���� �����ϼ̽��ϴ�. *****");
               
                  
                  // ����� �߰�
                  r.Add_User(this);
                  send_Message("JoinRoom/" + message);
               
               
               }
               
            }
            
         }
         

         
         else if(protocol.equals("OutRoom"))
         {
            for(int i=0; i<room_vc.size(); i++)
            {
               RoomInfo r = (RoomInfo)room_vc.elementAt(i);
               if(r.Room_name.equals(message))
               {
                  r.BroadCast_Room("Chatting/�˸�/***** " + NickName + "���� �����ϼ̽��ϴ�. *****");
               
                  r.remove_User(this);
                  send_Message("OutRoom/"+message);
                  BroadCast("user_list_update/ ");
            
                  
               }
            }
      
         }
         
         else if (protocol.equals("File"))
         {
        	 

        	 
         }
 
         
      }
      
      private void BroadCast(String str) // ��ü ����ڵ鿡�� ���ο� ����ڰ� �߰� ��ٴ� �޼��� ������ �κ�
      {
         for(int i=0; i < user_vc.size(); i++) // ���� ���ӵ� ����ڿ��� ���ο� ����� �˸�
         {
            UserInfo u = (UserInfo)user_vc.elementAt(i);
            
            u.send_Message(str);
            
            System.out.println();
         }
      }
      
      
      private void send_Message(String str) // ���ڿ��� �޾Ƽ� ����
      {
         try {
            dos.writeUTF(str);
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
   } // UserInfo class ��
   
   class RoomInfo
   {
      private String Room_name;
      private Vector Room_user_vc = new Vector();
      
      RoomInfo(String str, UserInfo u) // ��� ����� ID�� �޴´�.
      {
         this.Room_name = str; // ���� �̸�
         this.Room_user_vc.add(u); // ����� �߰�
         
      }
      
      public void remove_User(UserInfo userInfo) {
         // TODO Auto-generated method stub
         
      }

      public void BroadCast_Room(String str) // ���� ���� ��� ������� �˸���
      {
         for(int i=0; i<Room_user_vc.size(); i++)
         {
            UserInfo u = (UserInfo)Room_user_vc.elementAt(i);
            
            u.send_Message(str);
         }
      }
      
      private void Add_User(UserInfo u)
      {
         this.Room_user_vc.add(u);
      }
      
      private void Remove_User(UserInfo u)
      {
         this.Room_user_vc.remove(u);
      }

      
   }
   public Connection getConn(){
        Connection con = null;//DB ���� �ʱ�ȭ
        try {
            Class.forName(Driver); //1. ����̹� �ε�
            con = DriverManager.getConnection(Url,User,Pass); //2. ����̹� ����
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
   }
   public static ImageIcon ResizeImage(String ImagePath,int a,int b)//ũ�⸦ ���� �Է��� �ٶ�
   {
      ImageIcon MyImage = new ImageIcon(ImagePath);
      Image img = MyImage.getImage();
      Image newImg = img.getScaledInstance(a, b, Image.SCALE_SMOOTH);
      ImageIcon image = new ImageIcon(newImg);
      return image;
   }
}