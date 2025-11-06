package com.edutime.ui;
import com.edutime.util.UiTheme; import com.edutime.dao.EnrollmentDao; import javax.swing.*; import javax.swing.table.DefaultTableModel; import java.awt.*; import java.sql.*; import com.edutime.model.User;
public class EnrollmentsPanel extends JPanel{
  private final DefaultTableModel model=new DefaultTableModel(new Object[]{"SV (UserID)","Section (ID)"},0){ public boolean isCellEditable(int r,int c){return false;}};
  private final JTable table=new JTable(model); private final EnrollmentDao dao=new EnrollmentDao();
  private final User current;
  public EnrollmentsPanel(User current){ this.current=current; setLayout(new BorderLayout(10,10)); setBackground(UiTheme.BG);
    JToolBar tb=new JToolBar(); tb.setFloatable(false); UiTheme.styleToolbar(tb);
    JButton btnEnroll=new JButton("Đăng ký"), btnUn=new JButton("Hủy đăng ký"), btnReload=new JButton("Tải lại");
    tb.add(btnEnroll); tb.add(btnUn); tb.addSeparator(); tb.add(btnReload); add(tb,BorderLayout.NORTH);
    table.setRowHeight(26); add(new JScrollPane(table),BorderLayout.CENTER);
    btnReload.addActionListener(e->loadData());
    btnEnroll.addActionListener(e->{ JTextField sec=new JTextField(); Object[] msg={"Section ID",sec};
      if(JOptionPane.showConfirmDialog(this,msg,"Đăng ký học phần",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION){
        try{ dao.enroll(current.id, Integer.parseInt(sec.getText().trim())); loadData(); }catch(Exception ex){ JOptionPane.showMessageDialog(this,"Lỗi: "+ex.getMessage()); }
      }});
    btnUn.addActionListener(e->{ JTextField sec=new JTextField(); Object[] msg={"Section ID",sec};
      if(JOptionPane.showConfirmDialog(this,msg,"Hủy đăng ký",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION){
        try{ dao.unenroll(current.id, Integer.parseInt(sec.getText().trim())); loadData(); }catch(Exception ex){ JOptionPane.showMessageDialog(this,"Lỗi: "+ex.getMessage()); }
      }});
    loadData();
  }
  private void loadData(){ model.setRowCount(0); try{
    for(Integer sid: dao.sectionsOfStudent(current.id)){ model.addRow(new Object[]{current.id, sid}); }
  }catch(SQLException ex){ JOptionPane.showMessageDialog(this,"Lỗi tải dữ liệu: "+ex.getMessage()); } }
}