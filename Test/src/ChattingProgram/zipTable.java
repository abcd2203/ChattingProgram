package ChattingProgram;

import javax.swing.table.AbstractTableModel;

public class zipTable extends AbstractTableModel {
        //�÷��� �̸�
        String[] columNames = {"�Ϸù�ȣ","�����ȣ","��.��","��.��","��","��","���۹���","����������"};
        //������
        Object[][] data = {{" ", " "," "," "," "," "," "," "}};
        
        
        
        public zipTable(){
               
        }
 
        public zipTable(Object[][] data) {
               this.data = data;
        }
 
        @Override
        public int getColumnCount() {
               // TODO Auto-generated method stub
               return columNames.length;
        }
 
        @Override
        public int getRowCount() {
               // TODO Auto-generated method stub
               return data.length;           //2�� �迭�� ����
        }
 
        @Override
        public Object getValueAt(int arg0, int arg1) {
               // TODO Auto-generated method stub
               return data[arg0][arg1];
        }
 
        @Override
        public String getColumnName(int arg0) {
               // TODO Auto-generated method stub
               return columNames[arg0];
        }

}