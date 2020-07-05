package GoogleMap;

import static com.teamdev.jxbrowser.engine.RenderingMode.*;

import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.engine.EngineOptions;
import com.teamdev.jxbrowser.view.swing.BrowserView;

import ChattingProgram.Client;
import ChattingProgram.Manager;
import test.GoogleAPI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Graphics;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import java.awt.Window.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.awt.*;

public class GoogleMaps extends JFrame {
	public GoogleMaps() {
	}

	
	private static final int MIN_ZOOM = 0;
    private static final int MAX_ZOOM = 21;
    private static final String setMarkerScript =
            "var myLatlng = new google.maps.LatLng(48.4431727,23.0488126);\n" +
                    "var marker = new google.maps.Marker({\n" +
                    "    position: myLatlng,\n" +
                    "    map: map,\n" +
                    "    title: 'Hello World!'\n" +
                    "});";

    private static int zoomValue = 4;
	private static JTextField textField;
	private static GoogleAPI googleAPI = new GoogleAPI();
	private static JLabel googleMap = new JLabel();
	private static JFrame frame;
	
	public static GregorianCalendar calendar = new GregorianCalendar();
	static int year = calendar.get(Calendar.YEAR);				//��
	static int month = calendar.get(Calendar.MONTH)+1;			//��
	static int amPm = calendar.get(Calendar.AM_PM);				//����,����
	static int date = calendar.get(Calendar.DATE);				//��
	static int hour = calendar.get(Calendar.HOUR);				//��
	static int min = calendar.get(Calendar.MINUTE);				//��
	static int sec = calendar.get(Calendar.SECOND);				//��
	static String sAmPm = amPm == Calendar.AM ? "����": "����";
	public static String path = "C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\��\\"+"[" +year + "�� " + month + "�� " + date + "�� " + 
			sAmPm + " " + hour + "�� " + min + "�� " + sec + "��" +"] "+ Client.id +".jpg";
	private static JButton sendButton;
    
//    public static void setMap(String location) {
//		
//		googleAPI.downloadMap(location);
//		googleMap.setIcon(googleAPI.getMap(location));
//		googleAPI.fileDelete(location);
//		getContentPane().add(BorderLayout.SOUTH,googleMap);		
//		pack();
//	}
   
	public static void main(String[] args) {
	      // Create and initialize the Engine
	      System.setProperty("jxbrowser.license.key",
	            "6P830J66YADQY6EU708UDEGQNCL60DZB1BNSYNZ41HSY012O21P1XCF2LI0JOMTAW3LU");	// jxbrowser ���̼��� key
	      EngineOptions options = EngineOptions.newBuilder(HARDWARE_ACCELERATED).build();
	      Engine engine = Engine.newInstance(options);

	// Create the Browser
	      Browser browser = engine.newBrowser();
	      

	// Create the Swing BrowserView component
	         
	         SwingUtilities.invokeLater(() -> {
	             BrowserView view = BrowserView.newInstance(browser);

	             JButton zoomInButton = new JButton("Zoom In");
	             zoomInButton.addActionListener(e -> {
	                 if (zoomValue < MAX_ZOOM) {
	                     browser.mainFrame().ifPresent(frame ->
	                             frame.executeJavaScript("map.setZoom(" +
	                                     ++zoomValue + ")"));
	                 }
	             });

	             JButton zoomOutButton = new JButton("Zoom Out");
	             zoomOutButton.addActionListener(e -> {
	                 if (zoomValue > MIN_ZOOM) {
	                     browser.mainFrame().ifPresent(frame ->
	                             frame.executeJavaScript("map.setZoom(" +
	                                     --zoomValue + ")"));
	                 }
	             });
	             
	             textField = new JTextField();
	             textField.setColumns(10);
	             
	             JButton setMarkerButton = new JButton("Set Marker");
	             setMarkerButton.addActionListener(e ->
	                     browser.mainFrame().ifPresent(frame ->
	                             frame.executeJavaScript(setMarkerScript)));
	             
	             JButton searchButton = new JButton("�˻�");
	             searchButton.addActionListener(new ActionListener() {
	                 public void actionPerformed(ActionEvent args0) {
	                	 
	                 }
	                });

	             sendButton = new JButton("����");
	             
	             

	             JPanel toolBar = new JPanel();
	             toolBar.add(zoomInButton);
	             toolBar.add(zoomOutButton);
	             toolBar.add(setMarkerButton);
	             
	             
	             JPanel toolBar2 = new JPanel();
	             toolBar2.add(textField);
	             toolBar2.add(searchButton);
	             toolBar2.add(sendButton);
	             
	             
	             frame = new JFrame("Google Maps");
	             //frame.setLayout(null);
	             frame.add(toolBar, BorderLayout.SOUTH);
	             frame.add(toolBar2, BorderLayout.NORTH);
	             frame.add(view, BorderLayout.CENTER);
	             frame.setSize(800, 500);
	             frame.setVisible(true);
	             
	             sendButton.addActionListener(new ActionListener() {
	                 public void actionPerformed(ActionEvent args0) {
	                	BufferedImage image = new BufferedImage(800, 500, BufferedImage.TYPE_INT_BGR); 
	                	Graphics g = image.createGraphics();  // Image�� ����� �ϴ� Panel 
	                	frame.paint(g); 	// �̹����� ����
	                	//g.drawImage(image, 0, 0, 100, 100, null); 
	                	System.out.println("��");
	                	
	                	try {
					           //���� �̹����� ���Ϸ� ����ؼ� �����Ѵ�.
					            File outFile = new File(path);
					            ImageIO.write(image,"jpg", outFile);

					            
	                	}	
	                	catch (Exception f) {
					            System.out.println(f.getMessage());
	                	}
						
	                	System.out.println("��ġ �����ϱ�");
	                	System.out.println(calendar);
					
	                		// �˻� ��ư�� ������ ����
	                		Client.send_message("Chatting/" + Client.My_Room + "/" + "��ġ ���� <" + textField.getText() +">");	//��ġ ä�� ����
	                		Client.send_message("Chatting/" + Client.My_Room + "/" + path);	// �̹��� ����
						

	                		System.exit(0);	// ���� â �ݱ�
	                	}
	              });
	         
	         //browser.navigation().loadUrl("http://maps.google.com");	//���۸� ���
	         browser.navigation().loadUrl("C:\\Users\\36175\\eclipse-workspace\\Test\\src\\GoogleMap\\map.html");	//html ���
	      });

            
    }
}