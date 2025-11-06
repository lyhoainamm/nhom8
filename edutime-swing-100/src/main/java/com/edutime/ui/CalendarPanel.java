package com.edutime.ui;
import com.edutime.model.User; import com.edutime.util.UiTheme; import javax.swing.*; import java.awt.*; import java.io.*; import java.sql.*; import java.time.*; import java.time.format.DateTimeFormatter;
public class CalendarPanel extends JPanel{
  private final User current;
  private final JTextArea txt=new JTextArea(20,80);
  public CalendarPanel(User current){ this.current=current; setLayout(new BorderLayout(10,10)); setBackground(UiTheme.BG);
    JToolBar tb=new JToolBar(); tb.setFloatable(false); UiTheme.styleToolbar(tb);
    JButton btnLoad=new JButton("Xem tuần"), btnCsv=new JButton("Xuất CSV"), btnIcs=new JButton("Xuất ICS");
    tb.add(btnLoad); tb.add(btnCsv); tb.add(btnIcs); add(tb,BorderLayout.NORTH);
    txt.setEditable(false); add(new JScrollPane(txt),BorderLayout.CENTER);
    btnLoad.addActionListener(e->loadWeek(LocalDate.now()));
    btnCsv.addActionListener(e->exportCsv(LocalDate.now()));
    btnIcs.addActionListener(e->exportIcs(LocalDate.now()));
  }
  private void loadWeek(LocalDate anyDay){ LocalDate monday=anyDay.with(java.time.DayOfWeek.MONDAY); LocalDate sunday=monday.plusDays(6);
    try(java.sql.Connection c=com.edutime.dao.Db.get()){
      String sql = "SELECT se.start_date,se.end_date, cs.section_code, r.code as room, ts.day_of_week, ts.start_time, ts.end_time "
                 + "FROM enrollments e JOIN class_sections cs ON e.section_id=cs.id "
                 + "JOIN schedule_entries se ON se.class_section_id=cs.id "
                 + "JOIN rooms r ON r.id=se.room_id "
                 + "JOIN timeslots ts ON ts.id=se.timeslot_id "
                 + "JOIN semesters sm ON sm.id=cs.semester_id "
                 + "WHERE e.student_user_id=? AND sm.is_published=1 "
                 + "AND NOT (se.end_date < ? OR se.start_date > ?) "
                 + "ORDER BY ts.day_of_week, ts.start_time";
      try(java.sql.PreparedStatement ps=c.prepareStatement(sql)){
        ps.setInt(1, current.id); ps.setDate(2, java.sql.Date.valueOf(monday)); ps.setDate(3, java.sql.Date.valueOf(sunday));
        try(java.sql.ResultSet rs=ps.executeQuery()){
          StringBuilder sb=new StringBuilder(); sb.append("Tuần: ").append(monday).append(" - ").append(sunday).append("\n");
          while(rs.next()){
            int dow=rs.getInt("day_of_week");
            String start=rs.getString("start_time"); String end=rs.getString("end_time");
            String sec=rs.getString("section_code"); String room=rs.getString("room");
            LocalDate d = monday.plusDays(dow-2);
            sb.append(String.format("%s  %s-%s  %s  @%s\n", d, start, end, sec, room));
          }
          txt.setText(sb.toString());
        }
      }
    }catch(Exception ex){ txt.setText("Lỗi: "+ex.getMessage()); }
  }
  private void exportCsv(LocalDate anyDay){
    JFileChooser fc=new JFileChooser(); fc.setSelectedFile(new java.io.File("calendar_week.csv"));
    if(fc.showSaveDialog(this)==JFileChooser.APPROVE_OPTION){
      try(java.io.PrintWriter out=new java.io.PrintWriter(fc.getSelectedFile(), java.nio.charset.StandardCharsets.UTF_8)){
        out.println("date,start_time,end_time,section_code,room");
        LocalDate monday=anyDay.with(java.time.DayOfWeek.MONDAY); LocalDate sunday=monday.plusDays(6);
        try(java.sql.Connection c=com.edutime.dao.Db.get()){
          String sql = "SELECT se.start_date,se.end_date, cs.section_code, r.code as room, ts.day_of_week, ts.start_time, ts.end_time "
                     + "FROM enrollments e JOIN class_sections cs ON e.section_id=cs.id "
                     + "JOIN schedule_entries se ON se.class_section_id=cs.id "
                     + "JOIN rooms r ON r.id=se.room_id "
                     + "JOIN timeslots ts ON ts.id=se.timeslot_id "
                     + "JOIN semesters sm ON sm.id=cs.semester_id "
                     + "WHERE e.student_user_id=? AND sm.is_published=1 "
                     + "AND NOT (se.end_date < ? OR se.start_date > ?) "
                     + "ORDER BY ts.day_of_week, ts.start_time";
          try(java.sql.PreparedStatement ps=c.prepareStatement(sql)){
            ps.setInt(1, current.id); ps.setDate(2, java.sql.Date.valueOf(monday)); ps.setDate(3, java.sql.Date.valueOf(sunday));
            try(java.sql.ResultSet rs=ps.executeQuery()){
              while(rs.next()){
                int dow=rs.getInt("day_of_week"); String start=rs.getString("start_time"); String end=rs.getString("end_time");
                String sec=rs.getString("section_code"); String room=rs.getString("room");
                LocalDate d = monday.plusDays(dow-2);
                out.printf("%s,%s,%s,%s,%s%n", d, start, end, sec, room);
              }
            }
          }
        }
      }catch(Exception ex){ JOptionPane.showMessageDialog(this,"Lỗi export CSV: "+ex.getMessage()); }
    }
  }
  private void exportIcs(LocalDate anyDay){
    JFileChooser fc=new JFileChooser(); fc.setSelectedFile(new java.io.File("calendar_week.ics"));
    if(fc.showSaveDialog(this)==JFileChooser.APPROVE_OPTION){
      try(java.io.PrintWriter out=new java.io.PrintWriter(fc.getSelectedFile(), java.nio.charset.StandardCharsets.UTF_8)){
        out.println("BEGIN:VCALENDAR"); out.println("VERSION:2.0"); out.println("PRODID:-//EduTime//Calendar//VN");
        LocalDate monday=anyDay.with(java.time.DayOfWeek.MONDAY);
        try(java.sql.Connection c=com.edutime.dao.Db.get()){
          String sql = "SELECT se.start_date,se.end_date, cs.section_code, r.code as room, ts.day_of_week, ts.start_time, ts.end_time "
                     + "FROM enrollments e JOIN class_sections cs ON e.section_id=cs.id "
                     + "JOIN schedule_entries se ON se.class_section_id=cs.id "
                     + "JOIN rooms r ON r.id=se.room_id "
                     + "JOIN timeslots ts ON ts.id=se.timeslot_id "
                     + "JOIN semesters sm ON sm.id=cs.semester_id "
                     + "WHERE e.student_user_id=? AND sm.is_published=1 "
                     + "ORDER BY ts.day_of_week, ts.start_time";
          try(java.sql.PreparedStatement ps=c.prepareStatement(sql)){
            ps.setInt(1, current.id);
            try(java.sql.ResultSet rs=ps.executeQuery()){
              java.time.format.DateTimeFormatter dt = java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss");
              while(rs.next()){
                int dow=rs.getInt("day_of_week"); String start=rs.getString("start_time"); String end=rs.getString("end_time");
                String sec=rs.getString("section_code"); String room=rs.getString("room");
                java.time.LocalDate rangeS = rs.getDate("start_date").toLocalDate();
                java.time.LocalDate rangeE = rs.getDate("end_date").toLocalDate();
                java.time.LocalDate first = rangeS.with(java.time.temporal.TemporalAdjusters.nextOrSame(java.time.DayOfWeek.of(dow==8?7:dow-1)));
                if(first.isBefore(rangeS)) first=first.plusWeeks(1);
                java.time.LocalDateTime st = java.time.LocalDateTime.of(first, java.time.LocalTime.parse(start));
                java.time.LocalDateTime en = java.time.LocalDateTime.of(first, java.time.LocalTime.parse(end));
                out.println("BEGIN:VEVENT");
                out.println("SUMMARY:"+sec+" @"+room);
                out.println("DTSTART:"+dt.format(st));
                out.println("DTEND:"+dt.format(en));
                out.println("RRULE:FREQ=WEEKLY;UNTIL="+rangeE.format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd"))+"T235959;WKST=MO");
                out.println("END:VEVENT");
              }
            }
          }
        }
        out.println("END:VCALENDAR");
      }catch(Exception ex){ JOptionPane.showMessageDialog(this,"Lỗi export ICS: "+ex.getMessage()); }
    }
  }
}