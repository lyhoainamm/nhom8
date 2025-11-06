package com.edutime.ui;
import com.edutime.dao.UserDao; import com.edutime.model.User; import com.edutime.util.UiTheme; import javax.swing.*; import java.awt.*; import java.sql.SQLException;
public class LoginFrame extends JFrame{
  private final JTextField txtUser=new JTextField("admin"); private final JPasswordField txtPass=new JPasswordField("admin123"); private final JButton btnLogin=new JButton("Đăng nhập"); private final JLabel lblStatus=new JLabel(" ");
  public LoginFrame(){ setTitle("EduTime – Đăng nhập"); setDefaultCloseOperation(EXIT_ON_CLOSE); setSize(720,420); setLocationRelativeTo(null); setLayout(new BorderLayout());
    JPanel root=new JPanel(new GridLayout(1,2)); root.setBackground(UiTheme.BG); JPanel left=new JPanel(new GridBagLayout()); left.setBackground(new Color(0xE9F0FF)); JLabel title=new JLabel("EduTime"); title.setFont(new Font("Segoe UI",Font.BOLD,36)); title.setForeground(UiTheme.PRIMARY); left.add(title);
    JPanel rightWrap=new JPanel(new GridBagLayout()); rightWrap.setBackground(UiTheme.BG); JPanel form=new JPanel(new GridBagLayout()); form.setBackground(UiTheme.PANEL); form.setBorder(BorderFactory.createEmptyBorder(24,24,24,24));
    GridBagConstraints g=new GridBagConstraints(); g.insets=new Insets(8,8,8,8); g.fill=GridBagConstraints.HORIZONTAL; g.gridx=0; g.gridy=0; g.gridwidth=2; JLabel heading=new JLabel("Đăng nhập hệ thống"); heading.setFont(UiTheme.H1); form.add(heading,g);
    g.gridy++; g.gridwidth=1; form.add(new JLabel("Tên đăng nhập"),g); g.gridx=1; form.add(txtUser,g); g.gridx=0; g.gridy++; form.add(new JLabel("Mật khẩu"),g); g.gridx=1; form.add(txtPass,g);
    g.gridx=1; g.gridy++; UiTheme.stylePrimary(btnLogin); form.add(btnLogin,g); g.gridx=0; g.gridy++; g.gridwidth=2; lblStatus.setForeground(new Color(200,0,0)); form.add(lblStatus,g);
    rightWrap.add(form,new GridBagConstraints()); root.add(left); root.add(rightWrap); add(root,BorderLayout.CENTER); getRootPane().setDefaultButton(btnLogin); btnLogin.addActionListener(e->doLogin()); }
  private void doLogin(){ String u=txtUser.getText().trim(); String p=new String(txtPass.getPassword()); if(u.isEmpty()||p.isEmpty()){ lblStatus.setText("Nhập đủ thông tin."); return; } btnLogin.setEnabled(false); lblStatus.setText("Đang kiểm tra...");
    SwingWorker<Void,Void> w=new SwingWorker<>(){ com.edutime.model.User user; @Override protected Void doInBackground(){ try{ user=new com.edutime.dao.UserDao().login(u,p);}catch(SQLException ex){ lblStatus.setText("Lỗi CSDL: "+ex.getMessage()); } return null; }
      @Override protected void done(){ btnLogin.setEnabled(true); if(user!=null){ new MainFrame(user).setVisible(true); dispose(); } else { lblStatus.setText("Sai thông tin hoặc tài khoản không hoạt động."); txtPass.setText(""); } } }; w.execute(); }
}