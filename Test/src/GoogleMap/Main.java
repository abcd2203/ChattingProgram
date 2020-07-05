//package GoogleMap;
//
//import static com.teamdev.jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;
//
//import java.awt.Graphics;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.image.BufferedImage;
//import java.io.File;
//
//import javax.imageio.ImageIO;
//import javax.swing.JOptionPane;
//
//import com.teamdev.jxbrowser.browser.Browser;
//import com.teamdev.jxbrowser.engine.Engine;
//import com.teamdev.jxbrowser.engine.EngineOptions;
//import com.teamdev.jxbrowser.view.javafx.BrowserView;
//
//import ChattingProgram.Client;
//import javafx.application.Application;
//import javafx.event.EventHandler;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.layout.BorderPane;
//import javafx.stage.Stage;
//
//import java.awt.Color;
//import java.awt.EventQueue;
//import java.awt.Graphics2D;
//import java.awt.Image;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
//
///** ... */
//public final class Main extends Application  {
//   @Override
//   public void start(Stage primaryStage) {
//// Create and initialize the Engine
//      System.setProperty("jxbrowser.license.key", "1BNDHFSC1FTTRN1LIPTQVZAV14YNC5M80KPHSW5CJQE4HJ4TFQ099EVJ1TLC40X68LU3NG"); // jxbrowser 라이센스 key
//      EngineOptions options = EngineOptions.newBuilder(HARDWARE_ACCELERATED).build();
//      Engine engine = Engine.newInstance(options);
//
//// Create the Browser
//      Browser browser = engine.newBrowser();
//
//// Create the JavaFX BrowserView component
//      BrowserView view = BrowserView.newInstance(browser);
//      BorderPane pane = new BorderPane();
//      Button button = new Button("전송");
//      
//      pane.setCenter(view);
//      pane.setBottom(button);
//      Scene scene = new Scene(pane, 700, 700);
//      
//      primaryStage.setTitle("Google Map");
//      primaryStage.setScene(scene);
//      primaryStage.show();
//
//      browser.navigation().loadUrl("http://maps.google.com");
//
//      button.setOnAction(e -> {
//          System.out.println("전송 버튼");
//          BufferedImage image = new BufferedImage(700, 700, BufferedImage.TYPE_INT_BGR); 
//			Graphics g = image.createGraphics();  // Image를 얻고자 하는 Panel 
//			pane.paint(g); 	// 이미지로 저장
//			g.drawImage(image, 0, 0, 100, 100, null);
//
//          
//		try {
//		           //버퍼 이미지를 파일로 출력해서 저장한다.
//		            File outFile = new File(test.Main.path);
//		            ImageIO.write(image,"jpg", outFile);
//
//		            
//		}	
//		catch (Exception f) {
//		            System.out.println(f.getMessage());
//		}
//			
//		System.out.println("위치 전송하기");
//		System.out.println(test.Main.calendar);
//		
//		Client.send_message("Chatting/" + Client.My_Room + "/" + "위치 전송 <" + test.Main.textField.getText() +">");	//위치 채팅 전송
//		Client.send_message("Chatting/" + Client.My_Room + "/" + test.Main.path);
//			// 하이퍼링크
//
//		System.exit(0);	// 현재 창 닫기
//      });
//
//
//
//   }
//}