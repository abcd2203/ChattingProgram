package CaptchaTest2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import CaptchaTest2.*;

// 네이버 캡차 API 예제 - 키발급, 키 비교
public class APIExamCaptchaNkeyResult {

    public static String value;
    public static String key;
	public static void main(String[] args) {
		//keyResult(key,value);
	}
		public static void keyResult(String a, String b) {
			APIExamCaptchaNkey.getkey();
			String clientId = "LTL6us7eV35EEBzdn12l";	//애플리케이션 클라이언트 아이디값";
			String clientSecret = "iF3w_M8tAT";			//애플리케이션 클라이언트 시크릿값";
			//String key = APIExamCaptchaNkey.getkey(); // 캡차 키 발급시 받은 키 값
			try {
	            String code = "1"; // 키 발급시 0,  캡차 이미지 비교시 1로 세팅
	            //key = "";
	            //value = ""; // 사용자가 입력한 캡차 이미지 글자값
	            String apiURL = "https://openapi.naver.com/v1/captcha/nkey?code=" + 1 +"&key="+ a + "&value="+ b;
	
	            URL url = new URL(apiURL);
	            HttpURLConnection con = (HttpURLConnection)url.openConnection();
	            con.setRequestMethod("GET");
	            con.setRequestProperty("X-Naver-Client-Id", clientId);
	            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
	            int responseCode = con.getResponseCode();
	            BufferedReader br;
	            if(responseCode==200) { // 정상 호출
	                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
	            } else {  // 에러 발생
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
