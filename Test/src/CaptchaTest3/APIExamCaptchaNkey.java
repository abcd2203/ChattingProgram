package CaptchaTest3;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

// ���̹� ĸ�� API ���� - Ű�߱�
public class APIExamCaptchaNkey {

    public static String realkey;
	private static String key;

	public static void main(String[] args) {
	}
	public static void getkey() {
    	String clientId = "LTL6us7eV35EEBzdn12l";	//���ø����̼� Ŭ���̾�Ʈ ���̵�";
		String clientSecret = "iF3w_M8tAT";			//���ø����̼� Ŭ���̾�Ʈ ��ũ����";
        try {
            String code = "0"; // Ű �߱޽� 0,  ĸ�� �̹��� �񱳽� 1�� ����
            String apiURL = "https://openapi.naver.com/v1/captcha/nkey?code=" + code;
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
            key = response.toString();
            char[] key2 = key.toCharArray();// char������ �̾Ƴ�
            char [] key3; key3 = new char[23];	//Ű�� 24�ڸ� ����
            int j = 0;
            for (int i=8;i<=23;i++) {        	
            	key3 [j] = key2[i];
            	j++;
            }
            realkey = new String (key3);
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("key : " + realkey);
    }
}