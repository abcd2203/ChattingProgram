package test;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.StyledDocument;

import ChattingProgram.Client;


public class Main extends JFrame {
	
	private static final int MIN_ZOOM = 0;	// 최소줌
	private static final int MAX_ZOOM = 20;	// 최대줌
	private int b = 16;	// 확대, 축소 카운트 숫자 디폴트 10

	
	public static JTextField textField = new JTextField(30);
	private JPanel panel = new JPanel();
	private JButton button = new JButton("검색");
	static StyledDocument doc;
	private GoogleAPI googleAPI = new GoogleAPI();
	private JLabel googleMap = new JLabel();
	private final JButton button_1 = new JButton("\uC804\uC1A1");
	private int count=0;	// 검색 버튼 카운트
	
	public static GregorianCalendar calendar = new GregorianCalendar();
	static int year = calendar.get(Calendar.YEAR);				//년
	static int month = calendar.get(Calendar.MONTH)+1;			//월
	static int amPm = calendar.get(Calendar.AM_PM);				//오전,오후
	static int date = calendar.get(Calendar.DATE);				//날
	static int hour = calendar.get(Calendar.HOUR);				//시
	static int min = calendar.get(Calendar.MINUTE);				//분
	static int sec = calendar.get(Calendar.SECOND);				//초
	static String sAmPm = amPm == Calendar.AM ? "오전": "오후";
	public static String path = "C:\\Users\\36175\\Desktop\\이모티콘\\맵\\"+
//	"[" +year + "년 " + month + "월 " + date + "일 " + 
//			sAmPm + " " + hour + "시 " + min + "분 " + sec + "초" +"] "
	Client.id+".jpg";		// 파일명이 날짜,시간
	//public static String a = "C:\\Users\\36175\\Desktop\\이모티콘\\1.png";
	private final JLabel x = new JLabel("x");
	private final JLabel b_count = new JLabel("");
	private final JLabel lblNewLabel = new JLabel(" (0~20)");


	
	public void setMap(String location, int a) {
		
		googleAPI.downloadMap(location,a);
		googleMap.setIcon(googleAPI.getMap(location));
		googleAPI.fileDelete(location);
		//getContentPane().add(BorderLayout.CENTER,googleMap);		 
		pack(); 
		b_count.setText(b+"");
	}	

	public Main() {

		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\36175\\Desktop\\이모티콘\\검색.png"));
		getContentPane().setLayout(new BorderLayout());
		
		setResizable(false);	//크기 조정 불가
		setTitle("Google Maps");
		setVisible(true);
		
		panel.add(textField);
		button.addActionListener(new ActionListener() {			// 검색버튼 누를시
			public void actionPerformed(ActionEvent e) {
					if (textField.getText().length() == 0) {
					JOptionPane.showMessageDialog(null, "위치를 입력해주세요!" , "경고", JOptionPane.INFORMATION_MESSAGE);
					}
					count++;
					
					setMap(textField.getText(),b);
			}
		});
		panel.add(button);

		getContentPane().add(BorderLayout.CENTER,panel);
		
		JButton zoomInButton = new JButton("확대");
		zoomInButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (count >0) {		// 검색 버튼 누를 시 실행
				if (b < MAX_ZOOM)
				setMap(textField.getText(), ++b);
				}
			}
		});
        

        JButton zoomOutButton = new JButton("축소");
        zoomOutButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		if (count >0) {		// 검색 버튼 누를 시 실행
        		if (b > MIN_ZOOM)
        		setMap(textField.getText(), --b);
        		}
        	}
        });
        
        
        JPanel toolBar = new JPanel();
        toolBar.add(zoomInButton);
        toolBar.add(zoomOutButton);
        
        getContentPane().add(toolBar, BorderLayout.SOUTH);
        
        toolBar.add(x);
        
        toolBar.add(b_count);
        toolBar.add(lblNewLabel);
        
        JPanel toolBar2 = new JPanel();
        FlowLayout flowLayout = (FlowLayout) toolBar2.getLayout();
        flowLayout.setVgap(10);
        flowLayout.setHgap(10);
        toolBar2.add(googleMap);
        getContentPane().add(toolBar2, BorderLayout.NORTH);
        
        //toolBar2.add(view, BorderLayout.CENTER);
		
		
		button_1.addActionListener(new ActionListener() {	// 전송 버튼 클릭
			public void actionPerformed(ActionEvent e) {
				
				if (count >0) {
					
					BufferedImage image = new BufferedImage(toolBar2.getPreferredSize().width, 
							toolBar2.getPreferredSize().height, BufferedImage.TYPE_INT_BGR); 
					Graphics g = image.createGraphics();  // Image를 얻고자 하는 Panel 
					getContentPane().paint(g); 	// 이미지로 저장
					g.drawImage(image, 0, 0, 100, 100, null);				// 지도 이미지 저장

		            
				try {
				           //버퍼 이미지를 파일로 출력해서 저장한다.
				            File outFile = new File(path);
				            ImageIO.write(image,"jpg", outFile);

				            
				}	
				catch (Exception f) {
				            System.out.println(f.getMessage());
				}
					
				System.out.println("위치 전송하기");
				
				if (textField.getText().length() == 0) {
					JOptionPane.showMessageDialog(null, "위치를 입력해주세요!" , "경고", JOptionPane.INFORMATION_MESSAGE);
				}
				else if (count >0) {	// 검색 버튼을 누르면 실행
					Client.send_message("Chatting/" + Client.My_Room + "/" + "지도 전송 <" + textField.getText() +">");	//위치 채팅 전송
					Client.send_message("Chatting/" + Client.My_Room + "/" + path);	// 이미지 전송
					textField.setText(""); // 대화방에서 대화메시지 보내고나면 채팅내용 입력창에서 내용이 사라진다.

					dispose();	// 창 끄기
				}
				else {
				
					}
				}
			}

		});
		
		panel.add(button_1);

		

		pack();
	

	}
	public static ImageIcon ResizeImage(String ImagePath,int a,int b)//크기를 직접 입력해 줄때
	{
	   ImageIcon MyImage = new ImageIcon(ImagePath);
	   Image img = MyImage.getImage();
	   Image newImg = img.getScaledInstance(a, b, Image.SCALE_SMOOTH);
	   ImageIcon image = new ImageIcon(newImg);
	   return image;
	}


	public Main(String location){
		location = textField.getText();
	}
}