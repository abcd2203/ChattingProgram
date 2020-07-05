package test2;


import com.teamdev.jxbrowser.browser.Browser;
import com.teamdev.jxbrowser.engine.Engine;
import com.teamdev.jxbrowser.engine.EngineOptions;
import com.teamdev.jxbrowser.engine.RenderingMode;

import javax.swing.*;
import java.awt.*;

public class GoogleMapsSample {
    public static void main(String[] args) {
        //Browser browser = BrowserFactory.create();
        JFrame frame = new JFrame("JxBrowser Google Maps");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
       // frame.add(browser.getView().getComponent(), BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        //browser.loadURL("http://maps.google.com"); 
        System.setProperty("jxbrowser.license.key", "1BNDHFSC1FTTRN1LIPTQVZAV14YNC5M80KPHSW5CJQE4HJ4TFQ099EVJ1TLC40X68LU3NG");
    }

	private RenderingMode HARDWARE_ACCELERATED;
   
    Engine engine = Engine.newInstance(
            EngineOptions.newBuilder(HARDWARE_ACCELERATED)
                    .licenseKey("1BNDHFSC1FTTRN1LIPTQVZAV14YNC5M80KPHSW5CJQE4HJ4TFQ099EVJ1TLC40X68LU3NG")
                    .build());

}