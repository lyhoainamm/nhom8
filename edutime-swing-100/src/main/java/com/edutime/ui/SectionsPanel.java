package com.edutime.ui;
import com.edutime.util.UiTheme; import com.edutime.dao.ClassSectionDao; import com.edutime.dao.CourseDao; import com.edutime.dao.SemesterDao; import com.edutime.dao.UserDao; import com.edutime.model.*; import javax.swing.*; import javax.swing.table.DefaultTableModel; import java.awt.*; import java.sql.*; 
public class SectionsPanel extends JPanel{
  private final DefaultTableModel model=new DefaultTableModel(new Object[]{"ID","Mã lớp HP","Môn học (ID)","Giảng viên (UserID)","Học kỳ (ID)","Dự kiến","Sức chứa","Trạng thái"},0){ public boolean isCellEditable(int r,int c){return false;}};
  private final JTable table=new JTable(model); private final ClassSectionDao dao=new ClassSectionDao(); private final CourseDao courseDao=new CourseDao(); private final SemesterDao semDao=new SemesterDao(); private final UserDao userDao=new UserDao();
  public SectionsPanel(){ setLayout(new BorderLayout(10,10)); setBackground(UiTheme.BG); JToolBar tb=new JToolBar(); tb.setFloatable(false); UiTheme.styleToolbar(tb);
    JButton btnAdd=new JButton("Thêm"), btnEdit=new JButton("Sửa"), btnDel=new JButton("Xóa"), btnReload=new JButton("Tải lại"); tb.add(btnAdd); tb.add(btnEdit); tb.add(btnDel); tb.addSeparator(); tb.add(btnReload); add(tb,BorderLayout.NORTH);
    table.setRowHeight(26); add(new JScrollPane(table),BorderLayout.CENTER); btnReload.addActionListener(e->loadData());
    btnAdd.addActionListener(e->{ JTextField code=new JTextField(); JComboBox<Course> course=buildCourse(); JComboBox<User> lecturer=buildLecturer(); JComboBox<Semester> sem=buildSemester(); JSpinner ex=new JSpinner(new SpinnerNumberModel(50,10,300,5)); JSpinner cap=new JSpinner(new SpinnerNumberModel(60,10,300,5)); JComboBox<String> st=new JComboBox<>(new String[]{"OPEN","CLOSED"});
      Object[] msg={"Mã lớp HP",code,"Môn học",course,"Giảng viên (User)",lecturer,"Học kỳ",sem,"Dự kiến",ex,"Sức chứa",cap,"Trạng thái",st};
      if(JOptionPane.showConfirmDialog(this,msg,"Thêm lớp học phần",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION){ try{
        Course c=(Course)course.getSelectedItem(); User l=(User)lecturer.getSelectedItem(); Semester s=(Semester)sem.getSelectedItem();
        dao.insert(code.getText().trim(),c.id,l.id,s.id,(int)ex.getValue(),(int)cap.getValue(),String.valueOf(st.getSelectedItem())); loadData();
      }catch(Exception ex1){ JOptionPane.showMessageDialog(this,"Lỗi: "+ex1.getMessage()); } }});
    btnEdit.addActionListener(e->{ int row=table.getSelectedRow(); if(row<0){JOptionPane.showMessageDialog(this,"Chọn 1 dòng.");return;} int id=(int)model.getValueAt(row,0);
      JComboBox<Course> course=buildCourse(); JComboBox<User> lecturer=buildLecturer(); JComboBox<Semester> sem=buildSemester(); JSpinner ex=new JSpinner(new SpinnerNumberModel(50,10,300,5)); JSpinner cap=new JSpinner(new SpinnerNumberModel(60,10,300,5)); JComboBox<String> st=new JComboBox<>(new String[]{"OPEN","CLOSED"});
      Object[] msg={"Môn học",course,"Giảng viên",lecturer,"Học kỳ",sem,"Dự kiến",ex,"Sức chứa",cap,"Trạng thái",st};
      if(JOptionPane.showConfirmDialog(this,msg,"Sửa lớp HP",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION){ try{
        Course c=(Course)course.getSelectedItem(); User l=(User)lecturer.getSelectedItem(); Semester s=(Semester)sem.getSelectedItem();
        dao.update(id,c.id,l.id,s.id,(int)ex.getValue(),(int)cap.getValue(),String.valueOf(st.getSelectedItem())); loadData();
      }catch(Exception ex1){ JOptionPane.showMessageDialog(this,"Lỗi: "+ex1.getMessage()); } }});
    btnDel.addActionListener(e->{ int row=table.getSelectedRow(); if(row<0){JOptionPane.showMessageDialog(this,"Chọn 1 dòng.");return;} int id=(int)model.getValueAt(row,0);
      if(JOptionPane.showConfirmDialog(this,"Xóa lớp HP ID="+id+"?","Xác nhận",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){ try{ dao.delete(id); loadData(); }catch(Exception ex1){ JOptionPane.showMessageDialog(this,"Lỗi: "+ex1.getMessage()); } }});
    loadData();
  }
  private JComboBox<Course> buildCourse(){ try{ var list=courseDao.list(); DefaultComboBoxModel<Course> m=new DefaultComboBoxModel<>(); for(var it:list) m.addElement(it); return new JComboBox<>(m);}catch(Exception e){ return new JComboBox<>(); } }
  private JComboBox<User> buildLecturer(){ try{ var list=userDao.listByRole("LECTURER"); DefaultComboBoxModel<User> m=new DefaultComboBoxModel<>(); for(var it:list) m.addElement(it); return new JComboBox<>(m);}catch(Exception e){ return new JComboBox<>(); } }
  private JComboBox<Semester> buildSemester(){ try{ var list=semDao.list(); DefaultComboBoxModel<Semester> m=new DefaultComboBoxModel<>(); for(var it:list) m.addElement(it); return new JComboBox<>(m);}catch(Exception e){ return new JComboBox<>(); } }
  private void loadData(){ model.setRowCount(0); try{ for(var it: dao.list()) model.addRow(new Object[]{it.id,it.sectionCode,it.courseId,it.lecturerId,it.semesterId,it.expectedStudents,it.capacity,it.status}); }catch(SQLException ex){ JOptionPane.showMessageDialog(this,"Lỗi tải dữ liệu: "+ex.getMessage()); } }
}