package CaptchaTest3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

// ���̹� ĸ�� API ���� - ĸ�� �̹��� ����
public class APIExamCaptchaImage {
	public static int a = 0;

	public static void main(String[] args) {
	}

	public static void image() {
		String clientId = "LTL6us7eV35EEBzdn12l"; // ���ø����̼� Ŭ���̾�Ʈ ���̵�";
		String clientSecret = "iF3w_M8tAT"; // ���ø����̼� Ŭ���̾�Ʈ ��ũ����";
		try {
			APIExamCaptchaNkey.getkey();
			String key = APIExamCaptchaNkey.realkey; // https://openapi.naver.com/v1/captcha/nkey ȣ��� ���� Ű��
			System.out.println("img : " + key);
			String apiURL = "https://openapi.naver.com/v1/captcha/ncaptcha.bin?key=" + key;
			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("X-Naver-Client-Id", clientId);
			con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
			int responseCode = con.getResponseCode();
			BufferedReader br;
			if (responseCode == 200) { // ���� ȣ��
				InputStream is = con.getInputStream();
				int read = 0;
				byte[] bytes = new byte[1024];
				// ������ �̸����� ���� ����
				String tempname = Long.valueOf(new Date().getTime()).toString();
				File f = new File("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\Captcha\\tempname.jpg");
				File f1 = new File("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\Captcha\\tempname1.jpg");
				File f2 = new File("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\Captcha\\tempname2.jpg");
				File f3 = new File("C:\\Users\\36175\\Desktop\\�̸�Ƽ��\\Captcha\\tempname3.jpg"); // �� �̹����� ������ ���� �ʾ� 4����
																								// ���� �����ٰ���

				f.createNewFile();
				f1.createNewFile();
				f2.createNewFile();
				f3.createNewFile();

				OutputStream outputStream = new FileOutputStream(f);
				OutputStream outputStream1 = new FileOutputStream(f1);
				OutputStream outputStream2 = new FileOutputStream(f2);
				OutputStream outputStream3 = new FileOutputStream(f3);
				while ((read = is.read(bytes)) != -1) {
					outputStream.write(bytes, 0, read);
					outputStream1.write(bytes, 0, read);
					outputStream2.write(bytes, 0, read);
					outputStream3.write(bytes, 0, read);

				}
				is.close();
			} else { // ���� �߻�
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();
				while ((inputLine = br.readLine()) != null) {
					response.append(inputLine);
				}
				br.close();
				System.out.println(response.toString());

			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}