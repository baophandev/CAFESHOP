/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package caitiemcafe;

import java.awt.Color;
import java.awt.Image;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.ImageIcon;

/**
 *
 * @author GIA BAO
 */
public class Login extends javax.swing.JFrame {
    private static String HoVaTen;

    /**
     * Creates new form Login
     */
    public Login() {
        initComponents();
        setLocationRelativeTo(null);
        
//        printAll();
        
        
        //Tạo ảnh cho cửa sổ
        String iconPath = "D:\\Java Project\\CaiTiemCafe\\src\\caitiemcafe\\Image\\White_and_Brown_Elegant_Simple_Boba_Drink_Logo-removebg-preview.png";
        ImageIcon icon = new ImageIcon(iconPath);
        setIconImage(icon.getImage());
        
        //Load ảnh từ file học resource
        ImageIcon originalIcon = new ImageIcon("D:\\Java Project\\CaiTiemCafe\\src\\caitiemcafe\\Image\\White_and_Brown_Elegant_Simple_Boba_Drink_Logo-removebg-preview.png");
        
        // Lấy hình ảnh từ ImageIcon
        Image originalImage = originalIcon.getImage();
        
        //set kích thước
        int desiredWidth = 541;
        int desiredHeight = 500;
        
        //Thay đổi kích thước của hình ảnh
        Image scaledImage = originalImage.getScaledInstance(desiredWidth, desiredHeight, Image.SCALE_SMOOTH);
         
         //Tạo ImageIcon mới từ hình ảnh đã thay đổi kích thước
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
         
        //set cửa sỗ màu trắng
        getContentPane().setBackground(Color.WHITE);
        
        jLabel5.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtusername = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnLogin = new javax.swing.JButton();
        txtpassword = new javax.swing.JPasswordField();
        checkbox = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CÁI TIỆM CÀ PHÊ - ĐĂNG NHẬP");

        jLabel3.setIcon(new javax.swing.ImageIcon("D:\\Java Project\\CaiTiemCafe\\src\\caitiemcafe\\Image\\White and Brown Elegant Simple Boba Drink Logo (1).png")); // NOI18N
        jLabel3.setText("jLabel3");

        jLabel1.setFont(new java.awt.Font("SVN-Franko", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 0, 51));
        jLabel1.setText("LOGIN");

        txtusername.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        txtusername.setName("USERNAME"); // NOI18N

        jLabel2.setFont(new java.awt.Font("Sylfaen", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 0, 51));
        jLabel2.setText("Username : ");

        jLabel4.setFont(new java.awt.Font("Sylfaen", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 0, 51));
        jLabel4.setText("Password :");

        btnLogin.setBackground(new java.awt.Color(102, 0, 51));
        btnLogin.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        btnLogin.setForeground(new java.awt.Color(255, 255, 255));
        btnLogin.setText("LOGIN");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        txtpassword.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        txtpassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtpasswordKeyPressed(evt);
            }
        });

        checkbox.setBackground(new java.awt.Color(255, 255, 255));
        checkbox.setForeground(new java.awt.Color(102, 0, 51));
        checkbox.setText("Đăng nhập với quyền Admin");
        checkbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkboxActionPerformed(evt);
            }
        });

        jLabel5.setForeground(new java.awt.Color(255, 0, 51));
        jLabel5.setText("! Email hoặc mật khẩu sai");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(141, 141, 141)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(207, 207, 207))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtusername)
                                    .addComponent(txtpassword, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(124, 124, 124))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(205, 205, 205)
                        .addComponent(checkbox, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(187, 187, 187))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addComponent(jLabel1)
                .addGap(32, 32, 32)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtusername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtpassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(checkbox)
                .addGap(18, 18, 18)
                .addComponent(btnLogin)
                .addGap(35, 35, 35)
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //Lưu trữ tạm thời
    public class SessionManager {
        public static String getHoVaTen() {
            return HoVaTen;
        }

        public static void setHoVaTen(String hoVaTen) {
            HoVaTen = hoVaTen;
        }
    }
    
    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        // TODO add your handling code here:
        String enteredUsername = txtusername.getText().trim();
        String enteredPassword = txtpassword.getText().trim();
        
        if(checkbox.isSelected() && checkLogin(enteredUsername, enteredPassword, "1")){
            this.setVisible(false);
            Dashborad_ver2 dashborad_ver2 = new Dashborad_ver2();
            dashborad_ver2.setVisible(true);
            
        }else if(checkLogin(enteredUsername, enteredPassword)){
            this.setVisible(false);
            Home_ver2 home = new Home_ver2();
            home.setVisible(true);
        }else{
            jLabel5.setVisible(true);
        }
        
    }//GEN-LAST:event_btnLoginActionPerformed

    private void txtpasswordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpasswordKeyPressed

    }//GEN-LAST:event_txtpasswordKeyPressed

    private void checkboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkboxActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_checkboxActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }
    
    private boolean checkLogin(String username, String password){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String dbUrl = "jdbc:sqlserver://LAPTOP-1QVPM352\\MSSQLEXPRESS:1433;databaseName=CAFEACCOUNT;user=sa;password=sa2023";
            try (Connection con = DriverManager.getConnection(dbUrl)) {
                String query = "SELECT Ho_Va_Ten FROM DATA_ACCOUNT WHERE USERNAME = ? AND PASSWORD = ?";
                try (PreparedStatement ps = con.prepareStatement(query)) {
                    ps.setString(1, username);
                    ps.setString(2, password);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            HoVaTen = rs.getString("Ho_Va_Ten");
                            SessionManager.setHoVaTen(HoVaTen);
                            return true;
                        } else {
                            // Xử lý khi không có dòng kết quả
                            return false;
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            // Xử lý exception, có thể thông báo cho người dùng hoặc ghi vào bản nhật ký
            return false;
        }
    }
    
    private boolean checkLogin(String username, String password, String a){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String dbUrl = "jdbc:sqlserver://LAPTOP-1QVPM352\\MSSQLEXPRESS:1433;databaseName=CAFEACCOUNT;user=sa;password=sa2023";
            try (Connection con = DriverManager.getConnection(dbUrl)) {
                String query = "SELECT * FROM Admin WHERE USERNAME = ? AND PASSWORD = ?";
                try (PreparedStatement ps = con.prepareStatement(query)) {
                    ps.setString(1, username);
                    ps.setString(2, password);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            return true;
                        } else {
                            // Xử lý khi không có dòng kết quả
                            return false;
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            // Xử lý exception, có thể thông báo cho người dùng hoặc ghi vào bản nhật ký
            return false;
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogin;
    private javax.swing.JCheckBox checkbox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPasswordField txtpassword;
    private javax.swing.JTextField txtusername;
    // End of variables declaration//GEN-END:variables
}
