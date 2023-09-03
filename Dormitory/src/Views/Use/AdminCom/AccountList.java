package Views.Use.AdminCom;

import Views.ActionAlert;
import ViewsDataHandling.Admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class AccountList extends JPanel{
    JTable tb;
    Admin admin;
    public AccountList() {
        admin=new Admin();
        setSize(900,700);
        setLayout(null);
        setVisible(true);
        JLabel Title=new JLabel("Danh sách tài khoản");
        Title.setBounds(200,20,700,70);
        Title.setFont(new Font("VEDANA", Font.BOLD, 25));
        add(Title);

        JLabel LeftLabel = new JLabel("DANH SÁCH TÀI KHOẢN");
        LeftLabel.setBounds(110,110,300,30);
        LeftLabel.setFont(new Font("VEDANA", Font.BOLD, 16));
        add(LeftLabel);
        JScrollPane LeftPanel=new JScrollPane();
        LeftPanel.setBounds(15,160,575,500);
        add(LeftPanel);
        tb=new JTable(admin.StudentTable(""));
        LeftPanel.setViewportView(tb);
        JPanel LineCenter=new JPanel();
        LineCenter.setVisible(true);
        add(LineCenter);
        LineCenter.setBounds(600,100,2,550);
        LineCenter.setBackground(Color.DARK_GRAY);

        JPanel RightPanel=new JPanel();
        RightPanel.setBounds(600,100,300,600);
        RightPanel.setLayout(null);
        add(RightPanel);

        JLabel FilterLabel = new JLabel("LỌC DANH SÁCH");
        FilterLabel.setBounds(50,10,200,30);
        FilterLabel.setFont(new Font("VEDANA", Font.BOLD, 16));
        RightPanel.add(FilterLabel);

        JLabel EmailLabel=new JLabel("Nhập email hoặc tên");
        EmailLabel.setBounds(20,100,250,30);
        RightPanel.add(EmailLabel);
        EmailLabel.setForeground(Color.GRAY);
        JTextField EmailTF=new JTextField();
        RightPanel.add(EmailTF);
        EmailTF.setBounds(17,130,250,40);
        EmailTF.setVisible(true);
        JButton Submit1=new JButton("Tìm Kiếm");
        Submit1.setBounds(17,180,120,37);
        RightPanel.add(Submit1);
        Submit1.setBackground(new Color(76,140,245,255));
        Submit1.setForeground(Color.WHITE);
        Submit1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tb=new JTable(admin.StudentTable(EmailTF.getText()));
                LeftPanel.setViewportView(tb);
            }
        });



//        JButton ComfirmAll=new JButton("Nhập file");
//        ComfirmAll.setBounds(17,250,250,60);
//        RightPanel.add(ComfirmAll);
//        ComfirmAll.setBackground(Color.BLUE);
//        ComfirmAll.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                ActionAlert alert=new ActionAlert("Hướng dẫn","Chọn file *.csv với cấu trúc: Email,Mật khẩu,tên,SDT,giới tính,mã trường");
//                alert.Submit.addActionListener(new ActionListener() {
//                    @Override
//                    public void actionPerformed(ActionEvent e) {
//                        tb=new JTable(new Admin().StudentTable(EmailTF.getText()));
//                        LeftPanel.setViewportView(tb);
//                        JFileChooser fileChooser = new JFileChooser();
//
//                        int result = fileChooser.showOpenDialog(null);
//
//                        if (result == JFileChooser.APPROVE_OPTION) {
//                            File selectedFile = fileChooser.getSelectedFile();
//                            BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
//                            String line;
//                            while ((line = reader.readLine()) != null) {
//                                String[] s = line.split(",");
//                                for (int i = 0; i < s.length; i++) {
//                                    if (i != 4) {
//                                        s[i] = "'" + s[i] + "'";
//                                    }
//                                }
//                                // Do something with the modified string array
//                                System.out.println(String.join(",", s));
//                            }
//                            reader.close();
//                        }
//
//                    }
//                });
//            }
//        });





    }
}
