package com.edutime.ui;
import com.edutime.util.UiTheme; import javax.swing.*; import javax.swing.table.DefaultTableModel; import java.awt.*; import java.sql.*; import com.edutime.dao.UserDao; import com.edutime.model.User;
public class LecturersUsersPanel extends JPanel{
  private final DefaultTableModel model=new DefaultTableModel(new Object[]{"ID","Mã đăng nhập","Tên GV","Email","Trạng thái"},0){ public boolean isCellEditable(int r,int c){return false;}};
  private final JTable table=new JTable(model); private final UserDao dao=new UserDao();
  public LecturersUsersPanel(){ setLayout(new BorderLayout(10,10)); setBackground(UiTheme.BG); JToolBar tb=new JToolBar(); tb.setFloatable(false); UiTheme.styleToolbar(tb);
    JButton btnReload=new JButton("Tải lại"); tb.add(btnReload); add(tb,BorderLayout.NORTH);
    table.setRowHeight(26); add(new JScrollPane(table),BorderLayout.CENTER); btnReload.addActionListener(e->loadData());
    loadData();
  }
  private void loadData(){ model.setRowCount(0); try{ for(User it: dao.listByRole("LECTURER")) model.addRow(new Object[]{it.id,it.username,it.displayName,it.email,it.active?"Active":"Inactive"}); }catch(SQLException ex){ JOptionPane.showMessageDialog(this,"Lỗi tải dữ liệu: "+ex.getMessage()); } }
}