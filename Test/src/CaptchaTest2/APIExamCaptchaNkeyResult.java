package CaptchaTest2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import CaptchaTest2.*;

// ���̹� ĸ�� API ���� - Ű�߱�, Ű ��
public class APIExamCaptchaNkeyResult {

    public static String value;
    public static String key;
	public static void main(String[] args) {
		//keyResult(key,value);
	}
		public static void keyResult(String a, String b) {
			APIExamCaptchaNkey.getkey();
			String clientId = "LTL6us7eV35EEBzdn12l";	//���ø����̼� Ŭ���̾�Ʈ ���̵�";
			String clientSecret = "iF3w_M8tAT";			//���ø����̼� Ŭ���̾�Ʈ ��ũ����";
			//String key = APIExamCaptchaNkey.getkey(); // ĸ�� Ű �߱޽� ���� Ű ��
			try {
	            String code = "1"; // Ű �߱޽� 0,  ĸ�� �̹��� �񱳽� 1�� ����
	            //key = "";
	            //value = ""; // ����ڰ� �Է��� ĸ�� �̹��� ���ڰ�
	            String apiURL = "https://openapi.naver.com/v1/captcha/nkey?code=" + 1 +"&key="+ a + "&value="+ b;
	
	            URL url = new URL(apiURL);
	            HttpURLConnection con = (HttpURLConnection)url.openConnection();
	            con.setRequestMethod("GET");
	            con.setRequestProperty("X-Naver-Client-Id", clientId);
	            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
	            int responseCode = con.getResponseCode();
	            BufferedReader br;
	            if(responseCode==200) { // ���� ȣ��
	                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
	            } else {  // ���� �߻�
	                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
	            }
	            String inputLine;
	            StringBuffer response = new StringBuffer();
	            while ((inputLine = br.readLine()) != null) {
	                response.append(inputLine);
	            }
	            br.close();
	            System.out.println(response.toString());
	        } catch (Exception e) {
	            System.out.println(e);
        }
    }
}
