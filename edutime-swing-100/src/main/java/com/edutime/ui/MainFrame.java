package com.edutime.ui;
import com.edutime.model.User; import com.edutime.util.UiTheme; import javax.swing.*; import java.awt.*;
public class MainFrame extends JFrame{
  private final CardLayout card=new CardLayout(); private final JPanel content=new JPanel(card);
  private final User current;
  public MainFrame(User current){ this.current=current; setTitle("EduTime – Trang chính"); setDefaultCloseOperation(EXIT_ON_CLOSE); setSize(1200,780); setLocationRelativeTo(null); setLayout(new BorderLayout()); getContentPane().setBackground(UiTheme.BG);
    JPanel top=new JPanel(new BorderLayout()); top.setBorder(BorderFactory.createEmptyBorder(12,12,12,12)); top.setBackground(UiTheme.PANEL); JLabel lbl=new JLabel("Xin chào, "+current.displayName+" ("+current.role+")"); lbl.setFont(UiTheme.H2);
    JButton btnLogout=new JButton("Đăng xuất"); UiTheme.stylePrimary(btnLogout); btnLogout.addActionListener(e->{ dispose(); new LoginFrame().setVisible(true);} );
    top.add(lbl,BorderLayout.WEST); top.add(btnLogout,BorderLayout.EAST); add(top,BorderLayout.NORTH);
    JPanel side=new JPanel(); side.setLayout(new BoxLayout(side,BoxLayout.Y_AXIS)); side.setBackground(UiTheme.PANEL); side.setBorder(BorderFactory.createEmptyBorder(12,12,12,12)); add(side,BorderLayout.WEST);
    addNav(side,"Bảng điều khiển","dash"); addNav(side,"Người dùng","users"); addNav(side,"Giảng viên (Users)","lecturers"); addNav(side,"Môn học","courses"); addNav(side,"Phòng","rooms"); addNav(side,"Khung giờ","timeslots"); addNav(side,"Học kỳ","semesters"); addNav(side,"Lớp học phần","sections"); addNav(side,"Xếp lịch","schedule"); addNav(side,"Đăng ký (SV)","enroll"); addNav(side,"Lịch cá nhân","calendar");
    content.add(new DashboardPanel(),"dash"); content.add(new UsersPanel(),"users"); content.add(new LecturersUsersPanel(),"lecturers"); content.add(new CoursesPanel(),"courses"); content.add(new RoomsPanel(),"rooms"); content.add(new TimeslotsPanel(),"timeslots"); content.add(new SemestersPanel(),"semesters"); content.add(new SectionsPanel(),"sections"); content.add(new SchedulePanel(),"schedule"); content.add(new EnrollmentsPanel(current),"enroll"); content.add(new CalendarPanel(current),"calendar");
    add(new JScrollPane(content),BorderLayout.CENTER); card.show(content,"dash"); }
  private void addNav(JPanel side,String text,String key){ JButton b=new JButton(text); b.setHorizontalAlignment(SwingConstants.LEFT); b.setMaximumSize(new Dimension(240,40)); UiTheme.styleToolbar(b); b.addActionListener(e->card.show(content,key)); side.add(b); side.add(Box.createVerticalStrut(6)); }
  static class DashboardPanel extends JPanel{ DashboardPanel(){ setLayout(new GridBagLayout()); setBackground(UiTheme.BG); GridBagConstraints g=new GridBagConstraints(); g.insets=new Insets(10,10,10,10);
      add(card("Tổng lớp học phần","-"),g); g.gridx=1; add(card("Học kỳ đang công bố","-"),g); g.gridx=2; add(card("Phòng đang dùng","-"),g); }
    private JPanel card(String title,String value){ JPanel p=new JPanel(new BorderLayout()); p.setBackground(Color.white); p.setBorder(BorderFactory.createEmptyBorder(12,12,12,12)); p.setPreferredSize(new Dimension(260,120));
      JLabel t=new JLabel(title); t.setFont(UiTheme.TEXT); JLabel v=new JLabel(value); v.setFont(new Font("Segoe UI",Font.BOLD,24)); v.setForeground(UiTheme.PRIMARY); p.add(t,BorderLayout.NORTH); p.add(v,BorderLayout.CENTER); return p; } } }