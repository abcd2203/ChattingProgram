package CaptchaTest2;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

// 네이버 캡차 API 예제 - 캡차 이미지 수신
public class APIExamCaptchaImage {
	public static String X =APIExamCaptchaNkey.getkey();
    private static String key;
	public static void main(String[] args) {
    	Image(X);
    	APIExamCaptchaNkeyResult.keyResult(X, "");
    }
    public static void Image(String real) {
        String clientId = "LTL6us7eV35EEBzdn12l";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "iF3w_M8tAT";//애플리케이션 클라이언트 시크릿값";
        try {
        	key = real; // https://openapi.naver.com/v1/captcha/nkey 호출로 받은 키값
            String apiURL = "https://openapi.naver.com/v1/captcha/ncaptcha.bin?key=" + key;
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                InputStream is = con.getInputStream();
                int read = 0;
                byte[] bytes = new byte[1024];
                // 랜덤한 이름으로  파일 생성
                String tempname = Long.valueOf(new Date().getTime()).toString();
                File f = new File("C:\\Users\\36175\\Desktop\\이모티콘\\Captcha\\tempname.jpg");
                f.createNewFile();
                OutputStream outputStream = new FileOutputStream(f);
                while ((read =is.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }
                is.close();
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();
                //System.out.println(response.toString());
                
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}