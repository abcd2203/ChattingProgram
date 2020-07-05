package ChattingProgram;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Music extends JFrame {

	private JPanel contentPane;
	private JButton stop;
	private JButton play;
	private JButton right;
	private JButton left;
	static Clip clip;
	static int count = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Music frame = new Music();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Music() {
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		play = new JButton("");
		play.setIcon(new ImageIcon("C:\\Users\\36175\\Desktop\\\uC774\uBAA8\uD2F0\uCF58\\music\\\uC7AC\uC0DD.PNG"));
		play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				play.setVisible(false);
				stop.setVisible(true);
			}
		});
		play.setFont(new Font("±¼¸²", Font.BOLD, 60));
		play.setBounds(160, 113, 100, 100);
		contentPane.add(play);
		
		stop = new JButton("");
		stop.setIcon(new ImageIcon("C:\\Users\\36175\\Desktop\\\uC774\uBAA8\uD2F0\uCF58\\music\\\uC815\uC9C0.PNG"));
		stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				play.setVisible(true);
				stop.setVisible(false);
				clip.close();
			}
		});
		stop.setFont(new Font("±¼¸²", Font.BOLD, 60));
		stop.setBounds(160, 113, 100, 100);
		contentPane.add(stop);
		
		right = new JButton("");
		right.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		right.setIcon(new ImageIcon("C:\\Users\\36175\\Desktop\\\uC774\uBAA8\uD2F0\uCF58\\music\\\uBE68\uB9AC\uAC10\uAE30.PNG"));
		right.setFont(new Font("±¼¸²", Font.BOLD, 60));
		right.setBounds(272, 113, 100, 100);
		contentPane.add(right);
		
		left = new JButton("");
		left.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		left.setIcon(new ImageIcon("C:\\Users\\36175\\Desktop\\\uC774\uBAA8\uD2F0\uCF58\\music\\\uB418\uAC10\uAE30.PNG"));
		left.setFont(new Font("±¼¸²", Font.BOLD, 60));
		left.setBounds(48, 113, 100, 100);
		contentPane.add(left);
	}
	public static void changeMusic(int a) {
		a++;
		try {
			File file = new File("C:\\Users\\36175\\Desktop\\ÀÌ¸ðÆ¼ÄÜ\\music\\wood.wav");
			AudioInputStream stream = AudioSystem.getAudioInputStream(file);
			clip = AudioSystem.getClip();
			clip.open(stream);
			clip.start();
			// clip.close();

		} catch (Exception f) {

			f.printStackTrace();
		}
	}

}
