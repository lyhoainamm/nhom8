package com.edutime.ui;
import com.edutime.util.UiTheme; import com.edutime.dao.ScheduleDao; import javax.swing.*; import javax.swing.table.DefaultTableModel; import java.awt.*; import java.sql.*;
public class SchedulePanel extends JPanel{
  private final DefaultTableModel model=new DefaultTableModel(new Object[]{"ID","Lớp HP (ID)","Phòng (ID)","Khung giờ (ID)","Từ ngày","Đến ngày","Ghi chú"},0){ public boolean isCellEditable(int r,int c){return false;}};
  private final JTable table=new JTable(model); private final ScheduleDao dao=new ScheduleDao();
  public SchedulePanel(){ setLayout(new BorderLayout(10,10)); setBackground(UiTheme.BG); JToolBar tb=new JToolBar(); tb.setFloatable(false); UiTheme.styleToolbar(tb);
    JButton btnAdd=new JButton("Xếp lịch"), btnDel=new JButton("Xóa"), btnReload=new JButton("Tải lại"); tb.add(btnAdd); tb.add(btnDel); tb.addSeparator(); tb.add(btnReload); add(tb,BorderLayout.NORTH);
    table.setRowHeight(26); add(new JScrollPane(table),BorderLayout.CENTER); btnReload.addActionListener(e->loadData());
    btnAdd.addActionListener(e->{ JTextField section=new JTextField(); JTextField room=new JTextField(); JTextField ts=new JTextField(); JTextField start=new JTextField("2025-09-01"); JTextField end=new JTextField("2025-12-15"); JTextField note=new JTextField();
      Object[] msg={"Lớp HP (ID)",section,"Phòng (ID)",room,"Khung giờ (ID)",ts,"Từ ngày (YYYY-MM-DD)",start,"Đến ngày",end,"Ghi chú",note};
      if(JOptionPane.showConfirmDialog(this,msg,"Tạo lịch",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION){ try{ dao.insert(Integer.parseInt(section.getText().trim()),Integer.parseInt(room.getText().trim()),Integer.parseInt(ts.getText().trim()),start.getText().trim(),end.getText().trim(),note.getText().trim()); loadData(); }catch(Exception ex){ JOptionPane.showMessageDialog(this,"Lỗi: "+ex.getMessage()); } }});
    btnDel.addActionListener(e->{ int row=table.getSelectedRow(); if(row<0){JOptionPane.showMessageDialog(this,"Chọn 1 dòng.");return;} int id=(int)model.getValueAt(row,0);
      if(JOptionPane.showConfirmDialog(this,"Xóa lịch ID="+id+"?","Xác nhận",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){ try{ dao.delete(id); loadData(); }catch(SQLException ex){ JOptionPane.showMessageDialog(this,"Lỗi: "+ex.getMessage()); } }});
    loadData();
  }
  private void loadData(){ model.setRowCount(0); try{ for(var it: dao.list()) model.addRow(new Object[]{it.id,it.classSectionId,it.roomId,it.timeslotId,it.startDate,it.endDate,it.note}); }catch(SQLException ex){ JOptionPane.showMessageDialog(this,"Lỗi tải dữ liệu: "+ex.getMessage()); } }
}