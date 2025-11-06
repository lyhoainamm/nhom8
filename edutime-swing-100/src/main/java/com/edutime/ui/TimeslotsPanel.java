package com.edutime.ui;
import com.edutime.util.UiTheme; import com.edutime.dao.TimeslotDao; import javax.swing.*; import javax.swing.table.DefaultTableModel; import java.awt.*; import java.sql.*;
public class TimeslotsPanel extends JPanel{
  private final DefaultTableModel model=new DefaultTableModel(new Object[]{"ID","Thứ","Bắt đầu","Kết thúc","Week Pattern"},0){ public boolean isCellEditable(int r,int c){return false;}};
  private final JTable table=new JTable(model); private final TimeslotDao dao=new TimeslotDao();
  public TimeslotsPanel(){ setLayout(new BorderLayout(10,10)); setBackground(UiTheme.BG); JToolBar tb=new JToolBar(); tb.setFloatable(false); UiTheme.styleToolbar(tb);
    JButton btnAdd=new JButton("Thêm"), btnEdit=new JButton("Sửa"), btnDel=new JButton("Xóa"), btnReload=new JButton("Tải lại"); tb.add(btnAdd); tb.add(btnEdit); tb.add(btnDel); tb.addSeparator(); tb.add(btnReload); add(tb,BorderLayout.NORTH);
    table.setRowHeight(26); add(new JScrollPane(table),BorderLayout.CENTER); btnReload.addActionListener(e->loadData());
    btnAdd.addActionListener(e->{ JSpinner dow=new JSpinner(new SpinnerNumberModel(2,2,8,1)); JTextField start=new JTextField("07:30:00"); JTextField end=new JTextField("09:30:00"); JTextField pat=new JTextField("1-15");
      Object[] msg={"Thứ (2..8)",dow,"Bắt đầu (HH:MM:SS)",start,"Kết thúc",end,"Week Pattern (all/odd/even/1-15...)",pat};
      if(JOptionPane.showConfirmDialog(this,msg,"Thêm khung giờ",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION){ try{ dao.insert((int)dow.getValue(),start.getText().trim(),end.getText().trim(),pat.getText().trim()); loadData(); }catch(SQLException ex){ JOptionPane.showMessageDialog(this,"Lỗi: "+ex.getMessage()); } }});
    btnEdit.addActionListener(e->{ int row=table.getSelectedRow(); if(row<0){JOptionPane.showMessageDialog(this,"Chọn 1 dòng.");return;} int id=(int)model.getValueAt(row,0);
      JSpinner dow=new JSpinner(new SpinnerNumberModel(Integer.parseInt(model.getValueAt(row,1).toString()),2,8,1)); JTextField start=new JTextField(String.valueOf(model.getValueAt(row,2))); JTextField end=new JTextField(String.valueOf(model.getValueAt(row,3))); JTextField pat=new JTextField(String.valueOf(model.getValueAt(row,4)));
      Object[] msg={"Thứ",dow,"Bắt đầu",start,"Kết thúc",end,"Week Pattern",pat}; if(JOptionPane.showConfirmDialog(this,msg,"Sửa khung giờ",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION){ try{ dao.update(id,(int)dow.getValue(),start.getText().trim(),end.getText().trim(),pat.getText().trim()); loadData(); }catch(SQLException ex){ JOptionPane.showMessageDialog(this,"Lỗi: "+ex.getMessage()); } }});
    btnDel.addActionListener(e->{ int row=table.getSelectedRow(); if(row<0){JOptionPane.showMessageDialog(this,"Chọn 1 dòng.");return;} int id=(int)model.getValueAt(row,0);
      if(JOptionPane.showConfirmDialog(this,"Xóa khung giờ ID="+id+"?","Xác nhận",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){ try{ dao.delete(id); loadData(); }catch(SQLException ex){ JOptionPane.showMessageDialog(this,"Lỗi: "+ex.getMessage()); } }});
    loadData();
  }
  private void loadData(){ model.setRowCount(0); try{ for(var it: dao.list()) model.addRow(new Object[]{it.id,it.dayOfWeek,it.start,it.end,it.pattern}); }catch(SQLException ex){ JOptionPane.showMessageDialog(this,"Lỗi tải dữ liệu: "+ex.getMessage()); } }
}