package ChattingProgram;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
 

public class zipSearch extends JFrame {
 
        private JPanel contentPane;
        private JTable table;
        private JComboBox comboBox;
        private JComboBox comboBox_1;
        private JComboBox comboBox_2;
        
        private Connection conn = null;
        private PreparedStatement pstmt = null;       
        private ResultSet rs = null;          
        private JScrollPane scrollPane;
        private JPanel panel;
        private JButton button;
        
        public Object address;
        public Object address1;
        public Object address2;
        public Object address3;
        public Object post;
        
        /**
         * Launch the application.
         */
        public static void main(String[] args) {     
               EventQueue.invokeLater(new Runnable() {
                       public void run() {
                              try {
                                      zipSearch frame = new zipSearch();
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
        public zipSearch() {
        	   setTitle("�����ȣ �˻�");
        	   ImageIcon icon = new ImageIcon("C:\\Users\\user\\Pictures\\Camera Roll\\��ü��.jpg");
    		   this.setIconImage(icon.getImage());
    		   this.setSize(628,515);
               setBounds(100, 100, 628, 515);
               
               
               contentPane = new JPanel();
               contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
               setContentPane(contentPane);
               contentPane.setLayout(null);
               
               panel = new JPanel();
               panel.setBorder(new TitledBorder(null, "�����ȣ �˻�", TitledBorder.LEADING, TitledBorder.TOP, null, null));
               panel.setBounds(6, 22, 594, 70);
               contentPane.add(panel);
               panel.setLayout(null);
               
               scrollPane = new JScrollPane();
               scrollPane.setBounds(12, 111, 588, 356);
               contentPane.add(scrollPane);
               
               table = new JTable();
               table.setModel(new DefaultTableModel(
                       new Object[][] {
                              {" ", " ", " ", " ", " ", " ", " ", " "},
                       },
                       new String[] {
                              "\uC77C\uB828\uBC88\uD638", "\uC6B0\uD3B8\uBC88\uD638", "\uC2DC.\uB3C4", "\uAD6C.\uAD70", "\uB3D9", "\uB9AC", "\uC2DC\uC791\uBC88\uC9C0", "\uB9C8\uC9C0\uB9C9\uBC88\uC9C0"
                       }
               ) {
                       boolean[] columnEditables = new boolean[] {
                              false, false, false, false, false, false, false, false
                       };
                       public boolean isCellEditable(int row, int column) {
                              return columnEditables[column];
                       }
               });
               
               scrollPane.setViewportView(table);
               
               
               //ù��° combobox ����
               comboBox = new JComboBox();   
               comboBox.setBounds(146, 40, 100, 20);
               panel.add(comboBox);
               comboBox.addItem("��.�� ����");
               
               
               displaySido();
               //��.�� �޺��ڽ�=============================================
               comboBox.addItemListener(new ItemListener() {
                       public void itemStateChanged(ItemEvent e) {
               if(e.getStateChange()==ItemEvent.SELECTED)
                       selectSido(comboBox.getSelectedItem().toString());
                              
                       }
               });
               comboBox.setToolTipText(" ");
               
               
               JLabel label = new JLabel("��.�� ");
               label.setBounds(146, 14, 100, 20);
               panel.add(label);
               label.setHorizontalAlignment(SwingConstants.CENTER);
               
               //��.�� ComboBox=============================================
               comboBox_1 = new JComboBox();
               comboBox_1.setBounds(258, 40, 100, 20);
               panel.add(comboBox_1);
               
               JLabel label_1 = new JLabel("��.�� ");
               label_1.setBounds(258, 14, 100, 20);
               panel.add(label_1);
               label_1.setHorizontalAlignment(SwingConstants.CENTER);
               
               comboBox_1.addItemListener(new ItemListener() {
                       public void itemStateChanged(ItemEvent e) {
                              if(e.getStateChange()==ItemEvent.SELECTED)
                                      selectGugun(comboBox.getSelectedItem().toString() ,comboBox_1.getSelectedItem().toString());
                       }
               });
               
               //�� ComboBox=============================================
               comboBox_2 = new JComboBox();
               comboBox_2.setBounds(370, 40, 100, 20);
               panel.add(comboBox_2);
               
               JLabel label_2 = new JLabel("�� ");
               label_2.setBounds(370, 14, 100, 20);
               panel.add(label_2);
               label_2.setHorizontalAlignment(SwingConstants.CENTER);
               
               JButton btnNewButton = new JButton("����");
               btnNewButton.addActionListener(new ActionListener() {
               	public void actionPerformed(ActionEvent arg0) {
               		zipSearch.this.setVisible(false);
               	}
               });
               btnNewButton.setBounds(484, 44, 84, 20);
               panel.add(btnNewButton);
               
               JButton button_1 = new JButton("Ȯ��");
               button_1.addActionListener(new ActionListener() {
               	public void actionPerformed(ActionEvent arg0) {
               		System.out.println("Ȯ�� ��ư Ŭ��");
               		
               		int row = table.getSelectedRow();
               		
               		
               		Object address = table.getValueAt(row, 2);
               		Object address1 = table.getValueAt(row, 3);
               		Object address2 = table.getValueAt(row, 4);
               		Object address3 = table.getValueAt(row, 5);
               		Object post1 = table.getValueAt(row, 1);
               		
               		
               		zipSearch.this.setVisible(false);
               		if(address3 == null) {
               			MemberProc.tfAddr.setText((String) address + " " +  address1 + " " + address2);
               		}
               		else {
               		MemberProc.tfAddr.setText((String) address + " " +  address1 + " " + address2 + " " + address3);
               		}
               		MemberProc.tfpost1.setText((String) post1);
               	}
               });
               button_1.setBounds(484, 11, 84, 20);
               panel.add(button_1);
               
             
               
               
               
               
               
               comboBox_2.addItemListener(new ItemListener() {
                       public void itemStateChanged(ItemEvent e) {
                              if(e.getStateChange()==ItemEvent.SELECTED)
                              
                              //table�� ����ֱ� ����=====================================
                              selectDong(comboBox.getSelectedItem().toString(), comboBox_1.getSelectedItem().toString(), comboBox_2.getSelectedItem().toString());
                       }              
               });
               
               setResizable(false);
               
        }
        //���α׷� ���۽� ��.�� �����ֱ�====================================================================
        public void displaySido(){
               //����
               zipControl controller = new zipControl();
               //DB����
               controller.connection();              
               //
               ArrayList<zipDAO> sidoList = controller.searchSido();
               for(int i = 0 ; i < sidoList.size() ; i++){
                       zipDAO zipcode = sidoList.get(i);
                       comboBox.addItem(zipcode.getSido());
               }              
               //DB���� ����
               controller.disconnection();
        }
        //sido ����(gugun ���)====================================================================
        public void selectSido(String sido){
               System.out.println(sido);
               zipControl controller = new zipControl();
               //DB����
               controller.connection();              
               //
               ArrayList<zipDAO> gugunList = controller.searchGugun(sido);
               comboBox_1.removeAllItems();
               comboBox_2.removeAllItems();
               comboBox_1.addItem("��.�� ����");
               for(int i = 0 ; i < gugunList.size() ; i++){
                       zipDAO zipcode = gugunList.get(i);
                       comboBox_1.insertItemAt(zipcode.getGugun(), i);
               }
               table.setModel(new zipTable());
               //DB���� ����
               controller.disconnection();
        }       
        //gugun ����(dong ���)====================================================================
        public void selectGugun(String sido, String gugun){
               System.out.println(gugun);
               zipControl controller = new zipControl();
               //DB����
               controller.connection();              
               //
               ArrayList<zipDAO> dongList = controller.searchDong(sido, gugun);
               comboBox_2.removeAllItems();
               comboBox_2.addItem("�� ����");
               for(int i = 0 ; i < dongList.size() ; i++){
                       zipDAO zipcode = dongList.get(i);
                       comboBox_2.insertItemAt(zipcode.getDong(),i);
               }
               table.setModel(new zipTable());
               //DB���� ����
               controller.disconnection();                  
        }
        
        //������ Dong ����(���̺� ���)====================================================================
        public void selectDong(String sido, String gugun, String dong){
               
               zipControl controller = new zipControl();
               //DB����
               controller.connection();              
               //
               ArrayList<zipDAO> addressList = controller.searchAddress(sido, gugun, dong);
               
               Object[][] arrAdd = new Object[addressList.size()][8];
               
               for(int i = 0 ; i < addressList.size() ; i++){
                       zipDAO zipcode = addressList.get(i);
                       //���!
                       System.out.println(zipcode.getSeq() + " " + zipcode.getZipcode()+ " " +zipcode.getSido()+ " "
                       +zipcode.getGugun()+ " " +zipcode.getDong() + " " + zipcode.getRi() + " " + zipcode.getSt_bunji() + " " + zipcode.getEd_bunji());                       
                       //���̺� �ֱ�!
                       arrAdd[i][0] = zipcode.getSeq();
                       arrAdd[i][1] = zipcode.getZipcode();
                       arrAdd[i][2] = zipcode.getSido();
                       arrAdd[i][3] = zipcode.getGugun();
                       arrAdd[i][4] = zipcode.getDong();
                       arrAdd[i][5] = zipcode.getRi();
                       arrAdd[i][6] = zipcode.getSt_bunji();
                       arrAdd[i][7] = zipcode.getEd_bunji();
                       
                       table.setModel(new zipTable(arrAdd));
               }
               //DB���� ����
               controller.disconnection();
               
        }
}