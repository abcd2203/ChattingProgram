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
	
	private static final int MIN_ZOOM = 0;	// �ּ���
	private static final int MAX_ZOOM = 20;	// �ִ���
	private int b = 16;	// Ȯ��, ��� ī��Ʈ ���� ����Ʈ 10

	
	public static JTextField textField = new JTextField(30);
	private JPanel panel = new JPanel();
	private JButton button = new JButton("�˻�");
	static StyledDocument doc;
	private GoogleAPI googleAPI = new GoogleAPI();
	private JLabel googleMap = new JLabel();
	private final JButton button_1 = new JButton("\uC804\uC1A1");
	private int count=0;	// �˻� ��ư ī��Ʈ
	
	public static GregorianCalendar calendar = new GregorianCalendar();
	static int year = calendar.get(Calendar.YEAR);				//��
	static int month = calendar.get(Calendar.MONTH)+1;			//��
	static int amPm = calendar.get(Calendar.AM_PM);				//����,����
	static int date = calendar.get(Calendar.DATE);				//��
	static int hour = calendar.get(Calendar.HOUR);				//��
	static int min = calendar.get(Calendar.MINUTE);				//��
	static int sec = calendar.get(Calendar.SECOND);				//��
	static String sAmPm = amPm == Calendar.AM ? "����": "����";
	public static String path = "C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\��\\"+
//	"[" +year + "�� " + month + "�� " + date + "�� " + 
//			sAmPm + " " + hour + "�� " + min + "�� " + sec + "��" +"] "
	Client.id+".jpg";		// ���ϸ��� ��¥,�ð�
	//public static String a = "C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\1.png";
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

		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\�˻�.png"));
		getContentPane().setLayout(new BorderLayout());
		
		setResizable(false);	//ũ�� ���� �Ұ�
		setTitle("Google Maps");
		setVisible(true);
		
		panel.add(textField);
		button.addActionListener(new ActionListener() {			// �˻���ư ������
			public void actionPerformed(ActionEvent e) {
					if (textField.getText().length() == 0) {
					JOptionPane.showMessageDialog(null, "��ġ�� �Է����ּ���!" , "���", JOptionPane.INFORMATION_MESSAGE);
					}
					count++;
					
					setMap(textField.getText(),b);
			}
		});
		panel.add(button);

		getContentPane().add(BorderLayout.CENTER,panel);
		
		JButton zoomInButton = new JButton("Ȯ��");
		zoomInButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (count >0) {		// �˻� ��ư ���� �� ����
				if (b < MAX_ZOOM)
				setMap(textField.getText(), ++b);
				}
			}
		});
        

        JButton zoomOutButton = new JButton("���");
        zoomOutButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		if (count >0) {		// �˻� ��ư ���� �� ����
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
		
		
		button_1.addActionListener(new ActionListener() {	// ���� ��ư Ŭ��
			public void actionPerformed(ActionEvent e) {
				
				if (count >0) {
					
					BufferedImage image = new BufferedImage(toolBar2.getPreferredSize().width, 
							toolBar2.getPreferredSize().height, BufferedImage.TYPE_INT_BGR); 
					Graphics g = image.createGraphics();  // Image�� ����� �ϴ� Panel 
					getContentPane().paint(g); 	// �̹����� ����
					g.drawImage(image, 0, 0, 100, 100, null);				// ���� �̹��� ����

		            
				try {
				           //���� �̹����� ���Ϸ� ����ؼ� �����Ѵ�.
				            File outFile = new File(path);
				            ImageIO.write(image,"jpg", outFile);

				            
				}	
				catch (Exception f) {
				            System.out.println(f.getMessage());
				}
					
				System.out.println("��ġ �����ϱ�");
				
				if (textField.getText().length() == 0) {
					JOptionPane.showMessageDialog(null, "��ġ�� �Է����ּ���!" , "���", JOptionPane.INFORMATION_MESSAGE);
				}
				else if (count >0) {	// �˻� ��ư�� ������ ����
					Client.send_message("Chatting/" + Client.My_Room + "/" + "���� ���� <" + textField.getText() +">");	//��ġ ä�� ����
					Client.send_message("Chatting/" + Client.My_Room + "/" + path);	// �̹��� ����
					textField.setText(""); // ��ȭ�濡�� ��ȭ�޽��� �������� ä�ó��� �Է�â���� ������ �������.

					dispose();	// â ����
				}
				else {
				
					}
				}
			}

		});
		
		panel.add(button_1);

		

		pack();
	

	}
	public static ImageIcon ResizeImage(String ImagePath,int a,int b)//ũ�⸦ ���� �Է��� �ٶ�
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