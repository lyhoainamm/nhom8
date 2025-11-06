package com.edutime.ui;
import com.edutime.util.UiTheme; import javax.swing.*; import javax.swing.table.DefaultTableModel; import java.awt.*; import java.sql.*; import com.edutime.dao.UserDao; import com.edutime.model.User;
public class UsersPanel extends JPanel{
  private final DefaultTableModel model=new DefaultTableModel(new Object[]{"ID","Tên đăng nhập","Họ tên","Email","Vai trò","Trạng thái"},0){ public boolean isCellEditable(int r,int c){return false;}};
  private final JTable table=new JTable(model); private final UserDao dao=new UserDao();
  public UsersPanel(){ setLayout(new BorderLayout(10,10)); setBackground(UiTheme.BG); JToolBar tb=new JToolBar(); tb.setFloatable(false); UiTheme.styleToolbar(tb);
    JButton btnAdd=new JButton("Thêm"), btnEdit=new JButton("Sửa"), btnDel=new JButton("Xóa"), btnReload=new JButton("Tải lại"); tb.add(btnAdd); tb.add(btnEdit); tb.add(btnDel); tb.addSeparator(); tb.add(btnReload); add(tb,BorderLayout.NORTH);
    table.setRowHeight(26); add(new JScrollPane(table),BorderLayout.CENTER); btnReload.addActionListener(e->loadData());
    btnAdd.addActionListener(e->{ JTextField u=new JTextField(); JTextField n=new JTextField(); JTextField email=new JTextField(); JTextField r=new JTextField("STUDENT"); JTextField p=new JTextField(); JCheckBox ac=new JCheckBox("Active",true);
      Object[] msg={"Username",u,"Họ tên",n,"Email",email,"Vai trò (ADMIN/LECTURER/STUDENT)",r,"Mật khẩu",p,ac};
      if(JOptionPane.showConfirmDialog(this,msg,"Thêm người dùng",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION){ try{ dao.insert(u.getText().trim(),p.getText().trim(),n.getText().trim(),r.getText().trim(),ac.isSelected(),email.getText().trim()); loadData(); }catch(SQLException ex){ JOptionPane.showMessageDialog(this,"Lỗi: "+ex.getMessage()); } }});
    btnEdit.addActionListener(e->{ int row=table.getSelectedRow(); if(row<0){JOptionPane.showMessageDialog(this,"Chọn 1 dòng.");return;} int id=(int)model.getValueAt(row,0);
      JTextField n=new JTextField(String.valueOf(model.getValueAt(row,2))); JTextField email=new JTextField(String.valueOf(model.getValueAt(row,3))); JTextField r=new JTextField(String.valueOf(model.getValueAt(row,4))); JCheckBox ac=new JCheckBox("Active","Active".equals(model.getValueAt(row,5)));
      Object[] msg={"Họ tên",n,"Email",email,"Vai trò",r,ac}; if(JOptionPane.showConfirmDialog(this,msg,"Sửa người dùng",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION){ try{ dao.update(id,n.getText().trim(),email.getText().trim(),r.getText().trim(),ac.isSelected()); loadData(); }catch(SQLException ex){ JOptionPane.showMessageDialog(this,"Lỗi: "+ex.getMessage()); } }});
    btnDel.addActionListener(e->{ int row=table.getSelectedRow(); if(row<0){JOptionPane.showMessageDialog(this,"Chọn 1 dòng.");return;} int id=(int)model.getValueAt(row,0);
      if(JOptionPane.showConfirmDialog(this,"Xóa người dùng ID="+id+"?","Xác nhận",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){ try{ dao.delete(id); loadData(); }catch(SQLException ex){ JOptionPane.showMessageDialog(this,"Lỗi: "+ex.getMessage()); } }});
    loadData();
  }
  private void loadData(){ model.setRowCount(0); try{ for(User it: dao.listAll()) model.addRow(new Object[]{it.id,it.username,it.displayName,it.email,it.role,it.active?"Active":"Inactive"}); }catch(SQLException ex){ JOptionPane.showMessageDialog(this,"Lỗi tải dữ liệu: "+ex.getMessage()); } }
}