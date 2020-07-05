// 실제로 사용할 Server(서버 연결 창)

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
import java.net.ServerSocket; // 자바 안의 ServerSocket을 import
import java.net.Socket; // 자바 안의 Socket을 import
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
import javax.swing.JFrame; // JFrame 환경
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


import java.awt.Font;

public class Server extends JFrame implements ActionListener{
// 1. JFrame을 바탕으로 프로그램을 구상하기 때문에, JFrame로부터 상속받는다.
                                               // 2. 직접 상속 형태의 이벤트 메소드
                                               // 3. 추상 메소드이므로 메소드를 재정의    
   // ctrl + shift + o (JFrame 환경을 자동으로 import 시켜줌)
   

   
   public static ImageIcon icon;

   static JFrame Server_GUI = new JFrame(); // Server_GUI에 대한 객체를 생성
   private JPanel contentPane;
   private JTextArea textArea = new JTextArea(); // 프로그램을 이해하기 쉽게 textArea를 생성한 객체를 전역변수로 뺌 
   private JButton StartButton = new JButton("서버 실행"); // 버튼 이름이 유니코드 형태로 되어있으면 알아보기 힘들기 때문에, 알아보기 쉽게 버튼명과 동일하게 입력한다.
   private JButton StopButton = new JButton("서버 중지");
   private JButton ManagerButton = new JButton("관리자 모드");
   public static int temp2;
   // Network 자원
   private ServerSocket server_socket;
   private Socket socket;
   private int port;
   public static Vector user_vc = new Vector(); // 스레드를 여러개 저장 할 수 있도록 만든 객체
                                 // (InputStream is; OutputStream os &&
                                 // DataInputStream dis; DataOutputStream dos; 생성할 스레드를 따로 만들어야함)
   private Vector room_vc = new Vector();// 방 목록을 벡터라고 생각하고, 벡터 안에 여러 방이 구성 
   
   private StringTokenizer st;
   
   private static final JComponent ManagerLogin_GUI = null;
   private GregorianCalendar calendar = new GregorianCalendar();
   private static String Driver = "oracle.jdbc.driver.OracleDriver";
   private static String Url = "jdbc:oracle:thin:@localhost:1521:orcl";
   private static String User = "root";
   private static String Pass = "0000";
   private JTextField port_tf;
      
   Server() // 클래스의 객체를 만들기 위한 생성자
   { 
      init(); // 화면 생성 메소드
      start(); // 서버 로그인 메소드 (리스너 설정 메소드)
   }
   
   private void start() // start 메소드 (서버를 연결하기 위함)
   {
      StartButton.addActionListener(this); // Console창에 해당 ActionEvent가 나오게 하기 위함
      StopButton.addActionListener(this);
   }
   private void init() // init 메소드 (서버 연결 창의 화면을 구성하기 위함)
   {
      
      setTitle("Server");
      Server_GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      Server_GUI.setBounds(100, 100, 445, 546);
      
      this.setSize(750,550);
      setLocation(650, 250);
      String image = "C:\\Users\\36175\\Desktop\\이모티콘\\2.png";
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
       * JLabel lblNewLabel = new JLabel("포트 번호"); lblNewLabel.setBounds(12, 331, 67,
       * 15); contentPane.add(lblNewLabel);
       */
      
      /*
       * port_tf = new JTextField(); // 네트워크 진행 상태 표시 창을 구성하는 객체 생성
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
      port_tf.setFont(new Font("굴림", Font.PLAIN, 15));
      port_tf.setBounds(292, 390, 135, 21);
      contentPane.add(port_tf);
      port_tf.setColumns(10);
      port_tf.setText("12345");
      
      
      setResizable(false);
      this.setVisible(true); // true = 모든 구성 요소가 화면에 보이게, false = 모든 구성 요소가 화면에 보이지 않게
   }

   private void Server_start() // 서버 창에서 서버 연결을 하기 위한 메소드 (소켓 개념)
   {
      
      try {
         server_socket = new ServerSocket(port); // 임의적으로 포트번호를 사용한다.
      } catch (IOException e) { // 서버 연결 실패에 대한 예외처리한다.
         JOptionPane.showMessageDialog(null, "이미 사용중인 포트", "알림", JOptionPane.ERROR_MESSAGE);
      } // 12345포트 사용
      
      if(server_socket != null) // 정상적으로 포트가 열렸을 경우
      {
         Connection(); // 해당 네트워크를 다시 연결 시켜줌
         StartButton.setEnabled(false); // 서버 실행되면 스타트버튼 비활성화
         port_tf.setEditable(false); // 서버 실행되면 포트번호 비활성화
         StopButton.setEnabled(true); // 서버 실행되면 스탑버튼 활성화
      }
      
      
   }
   
   private void Connection() // 사용자가 접속하기 위한 도구 구성 메소드
   {
      
      // 1가지의 스레드에서는 1가지의 일만 처리할 수 있다.
      Thread th = new Thread(new Runnable() {
         
         @Override
         public void run() { // 스레드에서 처리할 일을 기재한다. (사용자 접속하는 것을 처리)
            
            while(true) {
            try {
               
               textArea.append("사용자 접속 대기중..."+"( "+port_tf.getText().trim()+"번 포트 )"+"\n"); // 네트워크 진행 상태 표시줄에 이 메시지를 뜨게함
               socket = server_socket.accept(); // 사용자가 소켓을 통해 접속할 때 서버 소켓을 무한대기 시켜줌
               textArea.append("사용자 접속!!!\n");
               
               UserInfo user = new UserInfo(socket);
            
               user.start(); // 객체의 스레드 실행
               
               
               } catch (IOException e) {
                  break;
                } // 접속 실패시 예외 처리
 catch (SQLException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
            }
            } // while 문 끝 
            
         }
         
      }); 
      
      th.start(); // 서버 연결
   
   }
   
   public static void main(String[] args) {


      new Server(); // 위의 Server()생성자에 대한 객체를 만듬
   
   
   
      
   }

   @Override
   public void actionPerformed(ActionEvent e) { // 1. 메소드를 재정의
                                       
      if(e.getSource() == StartButton) // 서버 실행시
      {
         System.out.println("서버 실행 버튼 클릭"); // 서버 실행버튼을 눌렀을 때 나타나는 메시지 
         
         port = Integer.parseInt(port_tf.getText().trim()); // 포트 번호를 입력
         
         if(port_tf.getText().trim()==null) {
             JOptionPane.showMessageDialog(null, "포트번호를 입력해주세요!", "경고", JOptionPane.ERROR_MESSAGE);
          }
          else {
         Server_start(); // 소켓 생성 및 사용자 접속 대기 (서버가 연결된다)
          }

      }
      else if(e.getSource() == StopButton) // 서버 중지시
      {
    	  StopButton.setEnabled(false); // 서버 중지되면 스탑버튼 비활성화
          StartButton.setEnabled(true); // 서버 중지되면 스타트버튼 활성화
          ManagerButton.setEnabled(true); // 서버 중지되면 관리자모드 활성화
          port_tf.setEditable(true); // 서버 중지되면 포트번호 활성화
          textArea.append("( "+port_tf.getText().trim()+"번 포트 )"+" 서버 중지\n");
         
         try {
            server_socket.close();
            user_vc.removeAllElements(); // user_vc의 모든요소 제거
            room_vc.removeAllElements(); // room_vc의 모든요소 제거
         } catch (IOException e1) {
            e1.printStackTrace();
         } // 서버 소켓 종료
         
         
         System.out.println("서버 중지 버튼 클릭"); // 서버 중지버튼을 눌렀을 때 나타나는 메시지
      }
      
      else if(e.getSource() == ManagerButton)
      {
         
   
      }
      
   
         
   } // 액션 이벤트 끝

   class UserInfo extends Thread
   {
      
      private InputStream is;
      private OutputStream os;
      private DataInputStream dis;
      private DataOutputStream dos;
      
      private Socket user_socket;
      private String NickName = ""; // 자신의 닉네임을 저장
      
      private boolean RoomCh = true;
      
      UserInfo(Socket soc) throws SQLException // 생성자 메소드 (소켓을 담을 공간)
      {
         this.user_socket = soc;
         UserNetwork();
      }
      
      private void UserNetwork() throws SQLException // 네트워크 자원 설정
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
            String sAmPm = amPm == Calendar.AM ? "오전" : "오후";
            Connection con = null;       //연결
             PreparedStatement ps = null; //명령
            is = user_socket.getInputStream();
            dis = new DataInputStream(is);
            os = user_socket.getOutputStream();
            dos = new DataOutputStream(os);
            
            NickName = dis.readUTF(); // 사용자의 닉네임을 받는다.
            textArea.append(NickName + " : 사용자 접속!");
            con = getConn();
              String sql = "insert into Userinfo(" +"Time,Nickname,Msg)"+"values(?,?,?)";
              ps = null;
            ps = con.prepareStatement(sql);
            ps.setString(1, year+"."+month+"."+date+"."+sAmPm+hour+":"+min+":"+sec);
            ps.setString(2, NickName);
               ps.setString(3, "접속 시작");
               ps.executeUpdate();
            // 기존 사용자들에게 새로운 사용자 알림
            System.out.println("현재 접속된 사용자 수: " + user_vc.size()+1); // Console창에 현재 채팅방에 접속된 사용자 수 출력(본인 제외)
            BroadCast("NewUser/" + NickName); // 기존 사용자에게 자신을 알린다
            
            // 자신에게 기존 사용자를 알림
            for(int i=0; i<user_vc.size(); i++)
            {
               UserInfo u = (UserInfo)user_vc.elementAt(i);
               send_Message("OldUser/" + u.NickName);
            }
            
            // 자신에게 기존 방 목록을 받아오는 부분
            for(int i=0; i<room_vc.size(); i++)
            {
               RoomInfo r = (RoomInfo)room_vc.elementAt(i);
               
               send_Message("OldRoom/" + r.Room_name);
            }
            
            send_Message("room_list_update/ "); // 방이 생긴 것을 알림
            user_vc.add(this); // 사용자에게 알린 후 Vector을 추가
            BroadCast("user_list_update/ ");
         }
         catch(IOException e) {
            JOptionPane.showMessageDialog(null, "Stream설정 에러", "알림", JOptionPane.ERROR_MESSAGE);
         }
      }
      
      public void run()   // Thread에서 처리할 내용
      {
         Connection con = null;       //연결
           PreparedStatement ps = null; //명령
           con = getConn();
           String sql = "insert into Userinfo(" +"Time,Nickname,Msg)"+"values(?,?,?)";
         while(true)
         {
            try {
               String msg = dis.readUTF();
               textArea.append(NickName+ " : 사용자로부터 들어온 메세지 : " + msg + "\n");
               InMessage(msg);
            }
            catch(IOException e) { // 접속이 끊어졌을때
               textArea.append(NickName + " : 사용자 접속 끊어짐\n"); // 서버 연결 상태 창에 이 메시지 출력
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
                  String sAmPm = amPm == Calendar.AM ? "오전" : "오후";
                  // 서버에서 나갔으니 쓰던 사용 자원을 모두 반납해야함
                  
                  dos.close();
                  dis.close();
                  user_socket.close(); // 사용자 소켓 종료
                  
                  for(int i=0;i<user_vc.size();i++) {
                     if(user_vc.elementAt(i)==NickName) {
                        user_vc.removeElementAt(i);
                     }
                  }
                  ps = null;
                  ps = con.prepareStatement(sql);
                  ps.setString(1, year+"."+month+"."+date+"."+sAmPm+hour+":"+min+":"+sec);
                  ps.setString(2, NickName);
                     ps.setString(3, "접속 종료");
                     ps.executeUpdate();               
                  user_vc.remove(this); // 끊어져서 나간 사용자를 리스트에서 제거
                  BroadCast("User_out/" + NickName);
                  BroadCast("user_list_update/ ");
               }
               catch(IOException e1) {} 
               catch (SQLException e1) {
                  // TODO Auto-generated catch block
                  e1.printStackTrace();
               } 
               break;
               
            }   // 메세지 수신
            catch (SQLException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
            }
            
         } 
         
      } // run 메소드 끝
      
      
      private void InMessage(String str) throws SQLException // 클라이언트로 부터 들어오는 수신 메시지 처리
      {
         
         st = new StringTokenizer(str,"/");
         
         String protocol = st.nextToken();
         String message = st.nextToken();
         
         System.out.println("프로토콜 : " + protocol); // Console창에 프로토콜 정보 출력
         System.out.println("메세지 : " + message); // Console창에 메세지 정보 출력
         
         
         if(protocol.equals("Note"))
         {
            // protocol = Note
            // message = user2
            // note = 받는 내용
            
   
            String note = st.nextToken();
            
   
            System.out.println("받는 사람 : " + message);
            System.out.println("보낼 내용 : " + note);
            
            // 벡터에서 해당 사용자를 찾아서 메세지 전송
            
            for(int i = 0; i<user_vc.size(); i++)
            {
               UserInfo u = (UserInfo)user_vc.elementAt(i);
               
               if(u.NickName.equals(message)) // 닉네임이 자신이 찾는 사람과 같으면
               {
                  u.send_Message("Note/" + NickName + "/" + note);
                  // Note/User1@~~~~
               }
            }
            
            
         }// if문끝
         else if(protocol.equals("CreateRoom"))
         {
            //1. 현재 같은 방이 존재하는지 확인한다.
            
            for(int i = 0; i<room_vc.size(); i++)
            {
               RoomInfo r = (RoomInfo)room_vc.elementAt(i);
               
               if(r.Room_name.equals(message)) // 만들고자 하는 방이 이미 존재 할 때
               {
                  send_Message("CreateRoomFail/ok");
                  RoomCh = false; // 방을 만들 수 없다.
                  break;
               }
               
                
            } // for 끝
            
            if(RoomCh) // 방 생성
            {
               RoomInfo new_room = new RoomInfo(message, this);
               room_vc.addElement(new_room); // 전체 방 벡터에 방을 추가
               
               send_Message("CreateRoom/" + message);
               
               
               BroadCast("New_Room/" + message); // 모든 사람들에게 방이 생성 된 것을 알려줄 수 있음
            }
            
            RoomCh = true;

         } // else if문 끝
         
         else if(protocol.equals("Chatting")) // 채팅할 때
         {
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH)+1;
            int date = calendar.get(Calendar.DATE);
            int amPm = calendar.get(Calendar.AM_PM);
            int hour = calendar.get(Calendar.HOUR);
            int min = calendar.get(Calendar.MINUTE);
            int sec = calendar.get(Calendar.SECOND);
            String sAmPm = amPm == Calendar.AM ? "오전" : "오후";
            Connection con = null;       //연결
              PreparedStatement ps = null; //명령
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
               if(r.Room_name.equals(message))//해당 방을 찾았을때
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
            
         //else if 끝
            }
         

            
            for(int i = 0; i < room_vc.size(); i++)
            {
               RoomInfo r = (RoomInfo)room_vc.elementAt(i);
               
               if(r.Room_name.equals(message)) // 해당 방을 찾았을때
               {
                  r.BroadCast_Room("Chatting/" + NickName + "/" + msg + "\n");
               }
            }
         } // else if
         
         else if(protocol.equals("JoinRoom")) // 대화방 참여
         {
            for(int i=0; i<room_vc.size(); i++)
            {
               RoomInfo r = (RoomInfo)room_vc.elementAt(i); // 내가 들어갈 대화방 찾음
               
               if(r.Room_name.equals(message))
               {
                  // 새로운 사용자를 알린다
                  r.BroadCast_Room("Chatting/알림/***** " + NickName + "님이 입장하셨습니다. *****");
               
                  
                  // 사용자 추가
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
                  r.BroadCast_Room("Chatting/알림/***** " + NickName + "님이 퇴장하셨습니다. *****");
               
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
      
      private void BroadCast(String str) // 전체 사용자들에게 새로운 사용자가 추가 됬다는 메세지 보내는 부분
      {
         for(int i=0; i < user_vc.size(); i++) // 현재 접속된 사용자에게 새로운 사용자 알림
         {
            UserInfo u = (UserInfo)user_vc.elementAt(i);
            
            u.send_Message(str);
            
            System.out.println();
         }
      }
      
      
      private void send_Message(String str) // 문자열을 받아서 전송
      {
         try {
            dos.writeUTF(str);
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
   } // UserInfo class 끝
   
   class RoomInfo
   {
      private String Room_name;
      private Vector Room_user_vc = new Vector();
      
      RoomInfo(String str, UserInfo u) // 방과 사용자 ID을 받는다.
      {
         this.Room_name = str; // 방의 이름
         this.Room_user_vc.add(u); // 사용자 추가
         
      }
      
      public void remove_User(UserInfo userInfo) {
         // TODO Auto-generated method stub
         
      }

      public void BroadCast_Room(String str) // 현재 방의 모든 사람에게 알린다
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
        Connection con = null;//DB 연동 초기화
        try {
            Class.forName(Driver); //1. 드라이버 로딩
            con = DriverManager.getConnection(Url,User,Pass); //2. 드라이버 연결
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
   }
   public static ImageIcon ResizeImage(String ImagePath,int a,int b)//크기를 직접 입력해 줄때
   {
      ImageIcon MyImage = new ImageIcon(ImagePath);
      Image img = MyImage.getImage();
      Image newImg = img.getScaledInstance(a, b, Image.SCALE_SMOOTH);
      ImageIcon image = new ImageIcon(newImg);
      return image;
   }
}