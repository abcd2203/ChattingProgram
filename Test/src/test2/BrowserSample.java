package test2;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.teamdev.jxbrowser.browser.Browser;

public class BrowserSample {
	
		  public static void main(String[] args) {
		   //Browser browser = BrowserFactory.create();

		    JPanel pane = new JPanel();
		    pane.setLayout(new BorderLayout());
		   // pane.add(browser.getView().getComponent(), BorderLayout.CENTER)

		    JFrame frame = new JFrame();
		    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		    frame.setContentPane(pane);
		    frame.setSize(700, 500);
		    frame.setLocationRelativeTo(null);
		    frame.setVisible(true);

		    //browser.loadURL("http://www.google.com");
		  }
		}
