package CaptchaTest3;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import ChattingProgram.MemberProc;

// ���̹� ĸ�� API ���� - Ű�߱�, Ű ��
public class APIresult extends JFrame {
	private JPanel contentPane;
	public static JTextField tf2;
	private JTextField tf;
	private JButton Re;
	private JButton check;

	private static String key;
	private static String realkey;
	public static String a;
	private String b;
	private String c;
	public static JLabel image;
	static char[] key3 = new char[1];
	public static String path = "C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\Captcha\\tempname.jpg";
	public static String path1 = "C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\Captcha\\tempname1.jpg";
	public static String path2 = "C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\Captcha\\tempname2.jpg";
	public static String path3 = "C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\Captcha\\tempname3.jpg";
	public static int count = 0; // ��߱� ���� Ƚ��

	private final JTextField tf3 = new JTextField();
	private JLabel count_label;
	private JLabel refresh_count;

	public static int number = 0;

	public APIresult() {
		this.setTitle("Captcha ���� Ȯ��");
		setBounds(100, 100, 350, 275);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel_2 = new JLabel("���ΰ�ħ�� 3������ �����մϴ�");
		lblNewLabel_2.setFont(new Font("���ʷҹ���", Font.BOLD, 12));
		lblNewLabel_2.setBounds(22, 208, 187, 15);
		contentPane.add(lblNewLabel_2);

		count_label = new JLabel("���� Ƚ�� : ");
		count_label.setFont(new Font("���ʷҹ���", Font.BOLD, 12));
		count_label.setBounds(227, 208, 79, 15);
		contentPane.add(count_label);

		tf2 = new JTextField();
		tf2.setBounds(12, 205, 215, 21);
		contentPane.add(tf2);
		tf2.setColumns(10);
		tf2.setVisible(false);

		Re = new JButton("");
		Re.setIcon(new ImageIcon("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\re.png"));
		Re.addActionListener(new ActionListener() { // ��߱� ��ư
			public void actionPerformed(ActionEvent arg0) {
				count++;

				APIExamCaptchaImage.image();
				tf2.setText(APIExamCaptchaNkey.realkey);
				System.out.println(APIExamCaptchaNkey.realkey);

				repaint();
				validate();

				if (count == 1) { // 1��
					image.setIcon(new ImageIcon(path1));
					System.out.println(count);
					refresh_count.setText("2");
				} else if (count == 2) { // 2��
					image.setIcon(new ImageIcon(path2));
					System.out.println(count);
					refresh_count.setText("1");
				} else if (count == 3) { // 3��
					image.setIcon(new ImageIcon(path3));
					System.out.println(count);
					refresh_count.setText("0");
				} else if (count >= 4) {
					dispose();
					JOptionPane.showMessageDialog(null, "�ٽ� �õ����ּ���!");
					count = 0; // ī��Ʈ �ʱ�ȭ
				}

			}
		});
		Re.setBounds(231, 173, 23, 23);
		contentPane.add(Re);

		tf = new JTextField();
		tf.setBounds(12, 174, 215, 21);
		contentPane.add(tf);
		tf.setColumns(10);

		check = new JButton("Ȯ��");
		check.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				a = tf2.getText().trim();
				b = tf.getText().trim();
				result(a, b);
				repaint();
				validate();
				tf3.setText(realkey);
				c = tf3.getText().trim();
				char d = key3[0];
				System.out.println("d : " + d + " vs " + realkey);

				if (d == 't') { // key3[0]�� t (true)
					System.out.println("���� �Ϸ�");
					// JOptionPane.showMessageDialog(null, "������ �Ϸ�Ǿ����ϴ�!");
					MemberProc.insertMember();
					System.out.println("insertMember() ȣ�� ����");
					dispose();

				} else { // key3[0]�� f (false)
					System.out.println("���� : " + realkey);
					JOptionPane.showMessageDialog(null, "���ȹ��ڸ� Ȯ�����ּ���!");
					count++;

					APIExamCaptchaImage.image();
					tf2.setText(APIExamCaptchaNkey.realkey);
					System.out.println(APIExamCaptchaNkey.realkey);

					repaint();
					validate();

					if (count == 1) { // 1��
						image.setIcon(new ImageIcon(path1));
						System.out.println(count);
						refresh_count.setText("2");
					} else if (count == 2) { // 2��
						image.setIcon(new ImageIcon(path2));
						System.out.println(count);
						refresh_count.setText("1");
					} else if (count == 3) { // 3��
						image.setIcon(new ImageIcon(path3));
						System.out.println(count);
						refresh_count.setText("0");
					} else if (count >= 4) {
						dispose();
						JOptionPane.showMessageDialog(null, "�ٽ� �õ����ּ���!");
						count = 0; // ī��Ʈ �ʱ�ȭ
					}

				}
				// lblNewLabel.setIcon(new
				// ImageIcon("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\Captcha\\tempname.jpg"));
			}
		});
		check.setBounds(258, 173, 62, 23);
		contentPane.add(check);

		image = new JLabel("");
		image.setBounds(69, 10, 251, 126);
		contentPane.add(image);

		JLabel lblNewLabel = new JLabel("���̴� �������");
		lblNewLabel.setFont(new Font("���ʷҹ���", Font.BOLD, 14));
		lblNewLabel.setBounds(29, 132, 139, 15);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("���� �� ���ڸ� ��� �Է����ּ���.");
		lblNewLabel_1.setFont(new Font("���ʷҹ���", Font.BOLD, 14));
		lblNewLabel_1.setBounds(29, 149, 225, 15);
		contentPane.add(lblNewLabel_1);
		tf3.setBounds(88, 117, 139, 21);
		contentPane.add(tf3);
		tf3.setColumns(10);

		refresh_count = new JLabel("3");
		refresh_count.setFont(new Font("���ʷҹ���", Font.BOLD, 12));
		refresh_count.setBounds(296, 208, 24, 15);
		contentPane.add(refresh_count);
		tf3.setVisible(false);
		// lblNewLabel.setIcon(new
		// ImageIcon("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\Captcha\\tempname.jpg"));
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					APIresult frame = new APIresult();
					frame.setVisible(true);
					image.setIcon(null);
					image.setVisible(false);
					APIExamCaptchaImage.image();
					tf2.setText(APIExamCaptchaNkey.realkey);
					System.out.println(APIExamCaptchaNkey.realkey);
					image.setIcon(new ImageIcon(path));
					image.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void result(String a, String b) { // responseTime : 0~7200 -> true
		String clientId = "LTL6us7eV35EEBzdn12l"; // ���ø����̼� Ŭ���̾�Ʈ ���̵�";
		String clientSecret = "iF3w_M8tAT"; // ���ø����̼� Ŭ���̾�Ʈ ��ũ����";
		try {
			String code = "1"; // Ű �߱޽� 0, ĸ�� �̹��� �񱳽� 1�� ����
			// String key = "YOUR_CAPTCHA_KEY"; // ĸ�� Ű �߱޽� ���� Ű��
			// String value = "YOUR_INPUT"; // ����ڰ� �Է��� ĸ�� �̹��� ���ڰ�
			String apiURL = "https://openapi.naver.com/v1/captcha/nkey?code=" + code + "&key=" + a + "&value=" + b;

			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("X-Naver-Client-Id", clientId);
			con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
			int responseCode = con.getResponseCode();
			BufferedReader br;
			if (responseCode == 200) { // ���� ȣ��
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else { // ���� �߻�
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}
			br.close();
			System.out.println(response.toString());
			key = response.toString();

			char[] key2 = key.toCharArray();// char������ �̾Ƴ�
			System.out.println(key2);

//            int j = 0;
//            for (int i=10;i<=13;i++) {
//            	key3 [j] = key2[i];		// true / fals
//            	j++;
//            }			

			key3[0] = key2[10]; // true / fals

			realkey = new String(key3);
			System.out.println(realkey);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
