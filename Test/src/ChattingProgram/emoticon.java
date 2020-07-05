package ChattingProgram;


import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import ChattingProgram.*;


public class emoticon extends JFrame {
   private JPanel a = new JPanel();
      emoticon(){
      Emoticon_init();
   }
   void Emoticon_init() {
      JFrame b = new JFrame("이모티콘박스");
      b.setBounds(100, 100, 365, 549);
      a.setBackground(Color.WHITE);
      a.setBorder(new EmptyBorder(5, 5, 5, 5));
      a.setLayout(null);
      b.getContentPane().add(a);
      JButton Image1 = new JButton("");
      Image1.setIcon(new ImageIcon("C:\\Users\\36175\\Desktop\\이모티콘\\푸\\1.gif"));
      Image1.setHorizontalAlignment(SwingConstants.CENTER);
      Image1.setBounds(12, 105, 100, 100);
      a.add(Image1);
            
      JButton Image2 = new JButton("");
      Image2.setIcon(new ImageIcon("C:\\Users\\36175\\Desktop\\이모티콘\\푸\\2.gif"));
      Image2.setHorizontalAlignment(SwingConstants.CENTER);
      Image2.setBounds(124, 105, 100, 100);
      a.add(Image2);
      
      JButton Image3 = new JButton("");
      Image3.setIcon(new ImageIcon("C:\\Users\\36175\\Desktop\\이모티콘\\푸\\3.gif"));
      Image3.setHorizontalAlignment(SwingConstants.CENTER);
      Image3.setBounds(236, 105, 100, 100);
      a.add(Image3);
      
      JButton Image4 = new JButton("");
      Image4.setIcon(new ImageIcon("C:\\Users\\36175\\Desktop\\이모티콘\\푸\\4.gif"));
      Image4.setHorizontalAlignment(SwingConstants.CENTER);
      Image4.setBounds(12, 240, 100, 100);
      a.add(Image4);
      
      JLabel LB1 = new JLabel("1");
      LB1.setFont(new Font("210 맨발의청춘 R", Font.PLAIN, 14));
      LB1.setHorizontalAlignment(SwingConstants.CENTER);
      LB1.setBounds(12, 215, 100, 15);
      a.add(LB1);
      
      JLabel LB2 = new JLabel("2");
      LB2.setFont(new Font("210 맨발의청춘 R", Font.PLAIN, 14));
      LB2.setHorizontalAlignment(SwingConstants.CENTER);
      LB2.setBounds(124, 215, 100, 15);
      a.add(LB2);
      
      JLabel LB3 = new JLabel("3");
      LB3.setFont(new Font("210 맨발의청춘 R", Font.PLAIN, 14));
      LB3.setHorizontalAlignment(SwingConstants.CENTER);
      LB3.setBounds(236, 215, 100, 15);
      a.add(LB3);
      
      JLabel LB4 = new JLabel("4");
      LB4.setFont(new Font("210 맨발의청춘 R", Font.PLAIN, 14));
      LB4.setHorizontalAlignment(SwingConstants.CENTER);
      LB4.setBounds(12, 350, 100, 15);
      a.add(LB4);
      
      JButton Image5 = new JButton("");
      Image5.setIcon(new ImageIcon("C:\\Users\\36175\\Desktop\\이모티콘\\푸\\5.gif"));
      Image5.setHorizontalAlignment(SwingConstants.CENTER);
      Image5.setBounds(124, 240, 100, 100);
      a.add(Image5);
      
      JButton Image6 = new JButton("");
      Image6.setIcon(new ImageIcon("C:\\Users\\36175\\Desktop\\이모티콘\\푸\\6.gif"));
      Image6.setHorizontalAlignment(SwingConstants.CENTER);
      Image6.setBounds(236, 240, 100, 100);
      a.add(Image6);
      
      JButton Image7 = new JButton("");
      Image7.setIcon(new ImageIcon("C:\\Users\\36175\\Desktop\\이모티콘\\푸\\7.gif"));
      Image7.setHorizontalAlignment(SwingConstants.CENTER);
      Image7.setBounds(12, 375, 100, 100);
      a.add(Image7);
      
      JButton Image8 = new JButton("");
      Image8.setIcon(new ImageIcon("C:\\Users\\36175\\Desktop\\이모티콘\\푸\\8.gif"));
      Image8.setHorizontalAlignment(SwingConstants.CENTER);
      Image8.setBounds(124, 375, 100, 100);
      a.add(Image8);
      
      JButton Image9 = new JButton("");
      Image9.setIcon(new ImageIcon("C:\\Users\\36175\\Desktop\\이모티콘\\푸\\9.gif"));
      Image9.setHorizontalAlignment(SwingConstants.CENTER);
      Image9.setBounds(236, 375, 100, 100);
      a.add(Image9);
      
      
      JLabel LB5 = new JLabel("5");
      LB5.setFont(new Font("210 맨발의청춘 R", Font.PLAIN, 14));
      LB5.setHorizontalAlignment(SwingConstants.CENTER);
      LB5.setBounds(124, 350, 100, 15); 
      a.add(LB5);
      
      JLabel LB6 = new JLabel("6");
      LB6.setFont(new Font("210 맨발의청춘 R", Font.PLAIN, 14));
      LB6.setHorizontalAlignment(SwingConstants.CENTER);
      LB6.setBounds(236, 350, 100, 15);
      a.add(LB6);
      
      JLabel LB7 = new JLabel("7");
      LB7.setFont(new Font("210 맨발의청춘 R", Font.PLAIN, 14));
      LB7.setHorizontalAlignment(SwingConstants.CENTER);
      LB7.setBounds(12, 485, 100, 15);
      a.add(LB7);
      
      JLabel LB8 = new JLabel("8");
      LB8.setFont(new Font("210 맨발의청춘 R", Font.PLAIN, 14));
      LB8.setHorizontalAlignment(SwingConstants.CENTER);
      LB8.setBounds(124, 485, 100, 15);
      a.add(LB8);
      
      JLabel label = new JLabel("9");
      label.setHorizontalAlignment(SwingConstants.CENTER);
      label.setFont(new Font("210 맨발의청춘 R", Font.PLAIN, 14));
      label.setBounds(236, 485, 100, 15);
      a.add(label);
      
      JLabel lblNewLabel = new JLabel("\uD478 \uD2F0 \uCF58 \u2661");
      lblNewLabel.setFont(new Font("휴먼모음T", Font.PLAIN, 57));
      lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
      lblNewLabel.setBounds(12, 10, 324, 85);
      a.add(lblNewLabel);
      
      Image1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
               Client.send_message("Chatting/"+Client.My_Room+"/"+"C:\\Users\\36175\\Desktop\\이모티콘\\푸\\1.gif");
            }  
         }); 
      Image2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
               Client.send_message("Chatting/"+Client.My_Room+"/"+"C:\\Users\\36175\\Desktop\\이모티콘\\푸\\2.gif");
            }  
         });
      Image3.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
               Client.send_message("Chatting/"+Client.My_Room+"/"+"C:\\Users\\36175\\Desktop\\이모티콘\\푸\\3.gif");
               
            }
         });
      Image4.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
               Client.send_message("Chatting/"+Client.My_Room+"/"+"C:\\Users\\36175\\Desktop\\이모티콘\\푸\\4.gif");
            } 
         });
      Image5.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
               Client.send_message("Chatting/"+Client.My_Room+"/"+"C:\\Users\\36175\\Desktop\\이모티콘\\푸\\5.gif");
            }
         });
      Image6.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
               Client.send_message("Chatting/"+Client.My_Room+"/"+"C:\\Users\\36175\\Desktop\\이모티콘\\푸\\6.gif");
            }
         });
      Image7.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
               Client.send_message("Chatting/"+Client.My_Room+"/"+"C:\\Users\\36175\\Desktop\\이모티콘\\푸\\7.gif");
            }
         });
      Image8.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
               Client.send_message("Chatting/"+Client.My_Room+"/"+"C:\\Users\\36175\\Desktop\\이모티콘\\푸\\8.gif");
            }
         });
      Image9.addActionListener(new ActionListener(){
          public void actionPerformed(ActionEvent e) {
             Client.send_message("Chatting/"+Client.My_Room+"/"+"C:\\Users\\36175\\Desktop\\이모티콘\\푸\\9.gif");
          }
       });
      
      b.setVisible(true);
      
   }
   public static ImageIcon ResizeImage(String ImagePath,int a,int b)//크기를 직접 입력해 줄때
   {
      ImageIcon MyImage = new ImageIcon(ImagePath);
      Image img = MyImage.getImage();
      Image newImg = img.getScaledInstance(a, b, Image.SCALE_SMOOTH);
      ImageIcon image = new ImageIcon(newImg);
      return image;
   }
   
public static void main(String[] args) {
      
      new emoticon();

   }
}