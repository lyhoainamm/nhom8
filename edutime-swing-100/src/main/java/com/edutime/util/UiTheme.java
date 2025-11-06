package com.edutime.util;
import com.formdev.flatlaf.FlatClientProperties; import com.formdev.flatlaf.FlatLightLaf; import javax.swing.*; import java.awt.*;
public final class UiTheme{
  public static final Color PRIMARY=new Color(0x2D7BE5), BG=new Color(0xF5F7FB), PANEL=Color.white;
  public static final Font H1=new Font("Segoe UI",Font.BOLD,18), H2=new Font("Segoe UI",Font.BOLD,16), TEXT=new Font("Segoe UI",Font.PLAIN,13);
  private UiTheme(){}
  public static void setupLookAndFeel(){ FlatLightLaf.setup(); UIManager.put("Component.arc",14); UIManager.put("Button.arc",14); UIManager.put("TextComponent.arc",12); }
  public static void stylePrimary(AbstractButton b){ b.putClientProperty(FlatClientProperties.STYLE,"background:#2D7BE5; foreground:#FFFFFF; arc:14; focusWidth:1;"); }
  public static void styleToolbar(JComponent c){ c.putClientProperty(FlatClientProperties.STYLE,"background:#FFFFFF; borderColor:#E6EAF2; borderWidth:1; arc:12;"); }
}