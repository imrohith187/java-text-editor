import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.plaf.metal.*;
import java.util.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
class editorr extends JFrame implements ActionListener {
    JTextArea t;
    JFrame f;
    JLabel fontLabel;
    JComboBox fontBox;
    JButton fontColorButton;
    JScrollPane scrollPane;
    JMenu format,font,font1,font2;
    JMenuItem fstyle1,fstyle2,fstyle3,fstyle4;
    int i=0;
    Font fo;
    String months[]={
        "Jan","Feb","Mar","Apr",
        "May","Jun","Jul","Aug",
        "Sep","Oct","Nov","Dec"};
    GregorianCalendar gcalendar;
    JSpinner fontSizeSpinner;
    editorr()
    {
        f = new JFrame("editor");
        f.getContentPane().setBackground(Color.gray);
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            MetalLookAndFeel.setCurrentTheme(new OceanTheme());
        }
        catch (Exception e) {
        }
        t = new JTextArea();
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontBox = new JComboBox(fonts);
        fontBox.addActionListener(this);
        fontBox.setSelectedItem("Arial");
        fontBox.setBounds(250,10,150,40);
        f.add(fontBox);
        JMenuBar mb = new JMenuBar();
        JMenu m1 = new JMenu("File");
        JMenuItem mi1 = new JMenuItem("New");
        JMenuItem mi2 = new JMenuItem("Open");
        JMenuItem mi3 = new JMenuItem("Save");
        JMenuItem mi9 = new JMenuItem("Print");
        mi1.addActionListener(this);
        mi2.addActionListener(this);
        mi3.addActionListener(this);
        mi9.addActionListener(this);
        m1.add(mi1);
        m1.add(mi2);
        m1.add(mi3);
        m1.add(mi9);
        JMenu m2 = new JMenu("Edit");
        JMenuItem mi4 = new JMenuItem("cut");
        JMenuItem mi5 = new JMenuItem("copy");
        JMenuItem mi6 = new JMenuItem("paste");
        JMenuItem mi7 = new JMenuItem("Delete");
        JMenuItem mi8 = new JMenuItem("Select All");
        JMenuItem mi0 = new JMenuItem("Time/Date");
        mi4.addActionListener(this);
        mi5.addActionListener(this);
        mi6.addActionListener(this);
        mi7.addActionListener(this);
        mi8.addActionListener(this);
        mi0.addActionListener(this);
        m2.add(mi4);
        m2.add(mi5);
        m2.add(mi6);
        m2.add(mi7);
        m2.add(mi8);
        m2.add(mi0);
        format=new JMenu("Format");
        font1=new JMenu("Font Style");
        format.add(font1);
        font1.add(fstyle1=new JMenuItem("Regular"));
        font1.add(fstyle2=new JMenuItem("Bold"));
        font1.add(fstyle3=new JMenuItem("Italic"));
        font1.add(fstyle4=new JMenuItem("Bold Italic"));
        fstyle1.addActionListener(this);
        fstyle2.addActionListener(this);
        fstyle3.addActionListener(this);
        fstyle4.addActionListener(this);
        JMenuItem mc = new JMenuItem("close");
        mc.addActionListener(this);
        mb.add(m1);
        mb.add(m2);
        mb.add(format);
        mb.add(mc);
        fontSizeSpinner = new JSpinner();
        fontSizeSpinner.setValue(20);
        fontSizeSpinner.setBounds(50,10,40,40);
        fontSizeSpinner.addChangeListener(new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent e) {
		fo = new Font(t.getFont().getFamily(),Font.PLAIN,(int) fontSizeSpinner.getValue());
        t.setFont(fo); 
        
        }
        });
        t.setEditable(true);
        fo=new Font("Arial",Font.PLAIN,20);
        t.setFont(fo); 
        scrollPane = new JScrollPane(t);
		scrollPane.setBounds(25, 100, 500, 500);
        f.add(scrollPane);
        fontColorButton = new JButton("Color");
        fontColorButton.setBounds(100,10,100,40);
        fontColorButton.addActionListener(this);
        f.setJMenuBar(mb);
        f.add(fontColorButton);
        f.add(fontSizeSpinner);
        f.setLayout(null);
        f.setSize(750, 750);
        f.show();
    }
    public void actionPerformed(ActionEvent e)
    {
        String s = e.getActionCommand();
        if(e.getSource()==fontColorButton) 
        {
               JColorChooser colorChooser = new JColorChooser();
               Color color = colorChooser.showDialog(null, "Choose a color", Color.black);
               t.setForeground(color);
        }
        if(e.getSource()==fontBox) {
			fo = new Font((String)fontBox.getSelectedItem(),Font.PLAIN,t.getFont().getSize());
            t.setFont(fo);
            
           }
        if (s.equals("cut")) {
            t.cut();
        }
        else if (s.equals("copy")) {
            t.copy();
        }
        else if (s.equals("paste")) {
            t.paste();
        }
        else if (s.equals("Save")) {
            JFileChooser j = new JFileChooser("f:");
            int r = j.showSaveDialog(null);
            if (r == JFileChooser.APPROVE_OPTION) {
                File fi = new File(j.getSelectedFile().getAbsolutePath());
                try {
                    FileWriter wr = new FileWriter(fi, false);
                    BufferedWriter w = new BufferedWriter(wr);
                    w.write(t.getText());
                    w.flush();
                    w.close();
                }
                catch (Exception evt) {
                    JOptionPane.showMessageDialog(f, evt.getMessage());
                }
            }
            else
                JOptionPane.showMessageDialog(f, "the user cancelled the operation");
        }
        else if(s.equals("Select All"))
            {
            String strText=t.getText();
            int strLen=strText.length();
            t.select(0,strLen);
            }
        else if(s.equals("Time/Date"))
        {
        gcalendar=new GregorianCalendar();
        String h=String.valueOf(gcalendar.get(Calendar.HOUR));
        String m=String.valueOf(gcalendar.get(Calendar.MINUTE));
        String se=String.valueOf(gcalendar.get(Calendar.SECOND));
        String date=String.valueOf(gcalendar.get(Calendar.DATE));
        String mon=months[gcalendar.get(Calendar.MONTH)];
        String year=String.valueOf(gcalendar.get(Calendar.YEAR));
        String hms="Time"+" - "+h+":"+m+":"+se+" Date"+" - "+date+" "+mon+" "+year;
        int loc=t.getCaretPosition();
        t.insert(hms,loc);
        }
        else if(s.equals("Delete"))
        {
        String msg=t.getSelectedText();
        i=t.getText().indexOf(msg);
        t.replaceRange(" ",i,i+msg.length());
        }
        else if (s.equals("Print")) {
            try {
                t.print();
            }
            catch (Exception evt) {
                JOptionPane.showMessageDialog(f, evt.getMessage());
            }
        }
        else if (s.equals("Open")) {
            JFileChooser j = new JFileChooser("f:");
            int r = j.showOpenDialog(null);
            if (r == JFileChooser.APPROVE_OPTION) {
                File fi = new File(j.getSelectedFile().getAbsolutePath());
                try {
                    String s1 = "", sl = "";
                    FileReader fr = new FileReader(fi);
                    BufferedReader br = new BufferedReader(fr);
                    sl = br.readLine();
                    while ((s1 = br.readLine()) != null) {
                        sl = sl + "\n" + s1;
                    }
                    t.setText(sl);
                }
                catch (Exception evt) {
                    JOptionPane.showMessageDialog(f, evt.getMessage());
                }
            }
            else
                JOptionPane.showMessageDialog(f, "the user cancelled the operation");
        }
        else if (s.equals("New")) {
            t.setText("");
        }
        else if(s.equals("Regular"))
        {
        String fontName=fo.getName();
        int fontSize=fo.getSize();
        fo=new Font(fontName,Font.PLAIN,fontSize);
        t.setFont(fo);
        }
        else if(s.equals("Bold"))
        {
        String fontName=fo.getName();
        int fontSize=fo.getSize();
        fo=new Font(fontName,Font.BOLD,fontSize);
        t.setFont(fo);
        }
        else if(s.equals("Italic"))
        {
        String fontName=fo.getName();
        int fontSize=fo.getSize();
        fo=new Font(fontName,Font.ITALIC,fontSize);
        t.setFont(fo);
        }
        else if(s.equals("Bold Italic"))
        {
        String fontName=fo.getName();
        int fontSize=fo.getSize();
        fo=new Font(fontName,Font.BOLD|Font.ITALIC,fontSize);
        t.setFont(fo);
        }
        else if (s.equals("close")) {
            f.setVisible(false);
        }

    }
}
public class rohith
{   public static void main(String args[])
    {
        editorr e = new editorr();
    }
}