package com.edutime.ui;
import com.edutime.util.UiTheme; import com.edutime.dao.RoomDao; import javax.swing.*; import javax.swing.table.DefaultTableModel; import java.awt.*; import java.sql.*;
public class RoomsPanel extends JPanel{
  private final DefaultTableModel model=new DefaultTableModel(new Object[]{"ID","Mã phòng","Tên","Sức chứa","Loại"},0){ public boolean isCellEditable(int r,int c){return false;}};
  private final JTable table=new JTable(model); private final RoomDao dao=new RoomDao();
  public RoomsPanel(){ setLayout(new BorderLayout(10,10)); setBackground(UiTheme.BG); JToolBar tb=new JToolBar(); tb.setFloatable(false); UiTheme.styleToolbar(tb);
    JButton btnAdd=new JButton("Thêm"), btnEdit=new JButton("Sửa"), btnDel=new JButton("Xóa"), btnReload=new JButton("Tải lại"); tb.add(btnAdd); tb.add(btnEdit); tb.add(btnDel); tb.addSeparator(); tb.add(btnReload); add(tb,BorderLayout.NORTH);
    table.setRowHeight(26); add(new JScrollPane(table),BorderLayout.CENTER); btnReload.addActionListener(e->loadData());
    btnAdd.addActionListener(e->{ JTextField code=new JTextField(); JTextField name=new JTextField(); JSpinner cap=new JSpinner(new SpinnerNumberModel(60,10,500,10)); JTextField type=new JTextField("LT");
      Object[] msg={"Mã phòng",code,"Tên",name,"Sức chứa",cap,"Loại (LT/Lab)",type}; if(JOptionPane.showConfirmDialog(this,msg,"Thêm phòng",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION){ try{ dao.insert(code.getText().trim(),name.getText().trim(),(int)cap.getValue(),type.getText().trim()); loadData(); }catch(SQLException ex){ JOptionPane.showMessageDialog(this,"Lỗi: "+ex.getMessage()); } }});
    btnEdit.addActionListener(e->{ int row=table.getSelectedRow(); if(row<0){JOptionPane.showMessageDialog(this,"Chọn 1 dòng.");return;} int id=(int)model.getValueAt(row,0);
      JTextField name=new JTextField(String.valueOf(model.getValueAt(row,2))); JSpinner cap=new JSpinner(new SpinnerNumberModel(Integer.parseInt(model.getValueAt(row,3).toString()),10,500,10)); JTextField type=new JTextField(String.valueOf(model.getValueAt(row,4)));
      Object[] msg={"Tên",name,"Sức chứa",cap,"Loại",type}; if(JOptionPane.showConfirmDialog(this,msg,"Sửa phòng",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION){ try{ dao.update(id,name.getText().trim(),(int)cap.getValue(),type.getText().trim()); loadData(); }catch(SQLException ex){ JOptionPane.showMessageDialog(this,"Lỗi: "+ex.getMessage()); } }});
    btnDel.addActionListener(e->{ int row=table.getSelectedRow(); if(row<0){JOptionPane.showMessageDialog(this,"Chọn 1 dòng.");return;} int id=(int)model.getValueAt(row,0);
      if(JOptionPane.showConfirmDialog(this,"Xóa phòng ID="+id+"?","Xác nhận",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){ try{ dao.delete(id); loadData(); }catch(SQLException ex){ JOptionPane.showMessageDialog(this,"Lỗi: "+ex.getMessage()); } }});
    loadData();
  }
  private void loadData(){ model.setRowCount(0); try{ for(var it: dao.list()) model.addRow(new Object[]{it.id,it.code,it.name,it.capacity,it.type}); }catch(SQLException ex){ JOptionPane.showMessageDialog(this,"Lỗi tải dữ liệu: "+ex.getMessage()); } }
}