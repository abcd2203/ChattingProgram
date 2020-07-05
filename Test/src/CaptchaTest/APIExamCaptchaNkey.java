package CaptchaTest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

// 네이버 캡차 API 예제 - 키발급
public class APIExamCaptchaNkey {
	public static String key;
	public static String realkey;	// 최종 키
	public static String real;
	public static int loading = 0;
	public static void main(String[] args) {
		real = getkey();
		//APIExamCaptchaImage.Image();
		//APIExamCaptchaNkeyResult.keyResult();
		
	}
    public static String getkey() {
        String clientId = "LTL6us7eV35EEBzdn12l";	//애플리케이션 클라이언트 아이디값";
        String clientSecret = "iF3w_M8tAT";			//애플리케이션 클라이언트 시크릿값";

        try {
            String code = "0"; // 키 발급시 0,  캡차 이미지 비교시 1로 세팅
            String apiURL = "https://openapi.naver.com/v1/captcha/nkey?code=" + code;
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
            key = response.toString();
            char[] key2 = key.toCharArray();// char형으로 뽑아냄
//            System.out.println(key);
//            System.out.println(key2[8]);
//            System.out.println(key2[23]);// char형으로 뽑아냄
            char [] key3; key3 = new char[23];	//키는 24자리 문자
            int j = 0;
            for (int i=8;i<=23;i++) {
            	
            	key3 [j] = key2[i];
            	j++;
            }
            realkey = String.valueOf(key3);
            
             
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(realkey);
        loading = 1;
        return realkey;
        
    }
}