package CaptchaTest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

// ���̹� ĸ�� API ���� - Ű�߱�, Ű ��
public class APIExamCaptchaNkeyResult {

    public static String value;
	public static String key;

    public static void main(String[] args) throws Exception {
    	String X =APIExamCaptchaNkey.getkey();
    	Thread.sleep(5000);
    	APIExamCaptchaImage.Image(X);
    	Thread.sleep(5000);
    	APIExamCaptchaNkeyResult.keyResult(X);
	}
	public static void keyResult(String real) {
        String clientId = "LTL6us7eV35EEBzdn12l";//���ø����̼� Ŭ���̾�Ʈ ���̵�";
        String clientSecret = "iF3w_M8tAT";//���ø����̼� Ŭ���̾�Ʈ ��ũ����";
        try {
            String code = "1"; // Ű �߱޽� 0,  ĸ�� �̹��� �񱳽� 1�� ����
            key = real; // ĸ�� Ű �߱޽� ���� Ű��
            value = "dydyd"; // ����ڰ� �Է��� ĸ�� �̹��� ���ڰ�
            String apiURL = "https://openapi.naver.com/v1/captcha/nkey?code=" + code +"&key="+ key + "&value="+ value;
            System.out.println(key);
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
            //System.out.println("����");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}