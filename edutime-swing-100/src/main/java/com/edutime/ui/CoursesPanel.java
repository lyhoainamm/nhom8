package com.edutime.ui;
import com.edutime.util.UiTheme; import com.edutime.dao.CourseDao; import javax.swing.*; import javax.swing.table.DefaultTableModel; import java.awt.*; import java.sql.*;
public class CoursesPanel extends JPanel{
  private final DefaultTableModel model=new DefaultTableModel(new Object[]{"ID","Mã môn","Tên môn","Số TC"},0){ public boolean isCellEditable(int r,int c){return false;}};
  private final JTable table=new JTable(model); private final CourseDao dao=new CourseDao();
  public CoursesPanel(){ setLayout(new BorderLayout(10,10)); setBackground(UiTheme.BG); JToolBar tb=new JToolBar(); tb.setFloatable(false); UiTheme.styleToolbar(tb);
    JButton btnAdd=new JButton("Thêm"), btnEdit=new JButton("Sửa"), btnDel=new JButton("Xóa"), btnReload=new JButton("Tải lại"); tb.add(btnAdd); tb.add(btnEdit); tb.add(btnDel); tb.addSeparator(); tb.add(btnReload); add(tb,BorderLayout.NORTH);
    table.setRowHeight(26); add(new JScrollPane(table),BorderLayout.CENTER); btnReload.addActionListener(e->loadData());
    btnAdd.addActionListener(e->{ JTextField code=new JTextField(); JTextField name=new JTextField(); JSpinner credits=new JSpinner(new SpinnerNumberModel(3,1,6,1));
      Object[] msg={"Mã môn",code,"Tên môn",name,"Số tín chỉ",credits}; if(JOptionPane.showConfirmDialog(this,msg,"Thêm môn học",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION){ try{ dao.insert(code.getText().trim(),name.getText().trim(),(int)credits.getValue()); loadData(); }catch(SQLException ex){ JOptionPane.showMessageDialog(this,"Lỗi: "+ex.getMessage()); } }});
    btnEdit.addActionListener(e->{ int row=table.getSelectedRow(); if(row<0){JOptionPane.showMessageDialog(this,"Chọn 1 dòng.");return;} int id=(int)model.getValueAt(row,0);
      JTextField name=new JTextField(String.valueOf(model.getValueAt(row,2))); JSpinner credits=new JSpinner(new SpinnerNumberModel(Integer.parseInt(model.getValueAt(row,3).toString()),1,6,1));
      Object[] msg={"Tên môn",name,"Số tín chỉ",credits}; if(JOptionPane.showConfirmDialog(this,msg,"Sửa môn học",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION){ try{ dao.update(id,name.getText().trim(),(int)credits.getValue()); loadData(); }catch(SQLException ex){ JOptionPane.showMessageDialog(this,"Lỗi: "+ex.getMessage()); } }});
    btnDel.addActionListener(e->{ int row=table.getSelectedRow(); if(row<0){JOptionPane.showMessageDialog(this,"Chọn 1 dòng.");return;} int id=(int)model.getValueAt(row,0);
      if(JOptionPane.showConfirmDialog(this,"Xóa môn ID="+id+"?","Xác nhận",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){ try{ dao.delete(id); loadData(); }catch(SQLException ex){ JOptionPane.showMessageDialog(this,"Lỗi: "+ex.getMessage()); } }});
    loadData();
  }
  private void loadData(){ model.setRowCount(0); try{ for(var it: dao.list()) model.addRow(new Object[]{it.id,it.code,it.name,it.credits}); }catch(SQLException ex){ JOptionPane.showMessageDialog(this,"Lỗi tải dữ liệu: "+ex.getMessage()); } }
}