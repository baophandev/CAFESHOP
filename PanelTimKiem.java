/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package caitiemcafe;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author GIA BAO
 */
public class PanelTimKiem extends javax.swing.JPanel {
    private final List<String> suggestions;
    private final DefaultComboBoxModel<String> comboBoxModel;

    /**
     * Creates new form PanelTimKiem
     */
    public PanelTimKiem() {
        initComponents();
        // Khởi tạo danh sách và model cho JComboBox
        suggestions = new ArrayList<>();
        comboBoxModel = new DefaultComboBoxModel<>();
        initSuggestions();
        // Khởi tạo sự kiện lắng nghe cho JTextField
        txtMa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMaKeyReleased(evt);
            }
        });

        // Thiết lập model mặc định cho JComboBox và ẩn nó

        comboBoxModel.addElement("");
        jComboBox.setModel(comboBoxModel);
        jComboBox.setVisible(false);
        
            // Sự kiện lắng nghe cho khi chọn một mục từ JComboBox
        jComboBox.addActionListener((java.awt.event.ActionEvent e) -> {
            Object selectedItem = jComboBox.getSelectedItem();
            if (selectedItem != null) {
                txtMa.setText(selectedItem.toString());
            }
            jComboBox.setVisible(false);
        });
        
        txtMa.setPreferredSize(new Dimension(200, 30));
        
        tbDetailSP.setModel(modelDetail);
        tbDetailDT.setModel(model);
        String iconPath = "D:\\Java Project\\CaiTiemCafe\\src\\caitiemcafe\\Image\\White_and_Brown_Elegant_Simple_Boba_Drink_Logo-removebg-preview.png";
        ImageIcon icon = new ImageIcon(iconPath);
        Detail.setIconImage(icon.getImage());
    }

    
    private void initSuggestions() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String dbUrl = "jdbc:sqlserver://LAPTOP-1QVPM352\\MSSQLEXPRESS:1433;databaseName=CAFEACCOUNT;user=sa;password=sa2023";
            Connection con = DriverManager.getConnection(dbUrl);
            String sql = "SELECT so FROM HoaDon";
            try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        suggestions.add(rs.getString("so"));
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    private void loadDataToHD(String so){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String dbUrl = "jdbc:sqlserver://LAPTOP-1QVPM352\\MSSQLEXPRESS:1433;databaseName=CAFEACCOUNT;user=sa;password=sa2023";
            Connection con = DriverManager.getConnection(dbUrl);
            Statement s = con.createStatement();
            String query = "SELECT * FROM HoaDon WHERE So = '"+so+"'";
            ResultSet rs = s.executeQuery(query);
            model.setRowCount(0);
            while(rs.next()){
                Object[] rowData = {
                    rs.getString("So"),
                    rs.getString("Ngay"),
                    rs.getString("NhanVienThuNgan"),
                    rs.getInt("Tong"),
                    rs.getInt("TienGiam"),
                    rs.getInt("ThanhToan"),
                    rs.getInt("TienKhachDua"),
                    rs.getInt("TienTraLai")
                };
                
                model.addRow(rowData);
            }
            
            
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void loadDatatoSanPham(String so){
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String dbUrl = "jdbc:sqlserver://LAPTOP-1QVPM352\\MSSQLEXPRESS:1433;databaseName=CAFEACCOUNT;user=sa;password=sa2023";
            Connection con = DriverManager.getConnection(dbUrl);
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM HoaDonSanPham where So ='" + so + "'");
            
            modelDetail.setRowCount(0);
            while(rs.next()){
                Object[] rowData = {
                    rs.getString("Ten"),
                    rs.getString("SoLuong"),
                    rs.getString("DonGia"),
                    rs.getString("Tong")
                };
                
                modelDetail.addRow(rowData);
            }
            
            Detail.setTitle("Hóa đơn số : " + so);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    private void txtMaKeyReleased(java.awt.event.KeyEvent evt) {
        String text = txtMa.getText();
        comboBoxModel.removeAllElements();

        if (text.isEmpty()) {
            jComboBox.setVisible(false);
        } else {
            suggestions.stream()
                    .filter(s -> s.startsWith(text))
                    .forEach(comboBoxModel::addElement);

            if (comboBoxModel.getSize() > 0) {
                jComboBox.setVisible(true);
            } else {
                jComboBox.setVisible(false);
            }
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    DefaultTableModel model = new DefaultTableModel(
        new Object [][] {
        },
        new String [] {
            "Số", "Ngày", "Nhân viên thu ngân", "Tổng", "Tiền giảm", "Thanh toán", "Tiên khách đưa", "Tiền trả lại"
        }
    );
    
    DefaultTableModel modelDetail = new DefaultTableModel(
        new Object [][] {
        },
        new String [] {
            "Tên", "Số lượng", "Đơn giá", "Thành tiền"
        }
    );
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Detail = new javax.swing.JDialog();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbDetailDT = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbDetailSP = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtMa = new javax.swing.JTextField();
        jComboBox = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        Detail.setBackground(new java.awt.Color(255, 255, 255));

        tbDetailDT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbDetailDT.setEnabled(false);
        jScrollPane1.setViewportView(tbDetailDT);

        tbDetailSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbDetailSP.setEnabled(false);
        jScrollPane2.setViewportView(tbDetailSP);

        jLabel1.setText("Chi tiết :");

        javax.swing.GroupLayout DetailLayout = new javax.swing.GroupLayout(Detail.getContentPane());
        Detail.getContentPane().setLayout(DetailLayout);
        DetailLayout.setHorizontalGroup(
            DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 983, Short.MAX_VALUE)
            .addComponent(jScrollPane2)
            .addGroup(DetailLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        DetailLayout.setVerticalGroup(
            DetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DetailLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        setBackground(new java.awt.Color(255, 255, 255));

        txtMa.setPreferredSize(new java.awt.Dimension(300, 30));

        jComboBox.setBackground(new java.awt.Color(255, 255, 255));
        jComboBox.setForeground(new java.awt.Color(0, 102, 102));
        jComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox.setPreferredSize(new java.awt.Dimension(300, 30));

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 102, 102));
        jButton1.setText("Xem");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 153, 153));
        jLabel2.setText("Nhập số hóa đơn : ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(108, 108, 108)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMa, javax.swing.GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE)
                            .addComponent(jComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(101, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMa, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(64, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        Detail.setVisible(true);
        Detail.pack();
        Detail.setLocationRelativeTo(null);
        
        String so = txtMa.getText().toString();
        loadDataToHD(so);
        loadDatatoSanPham(so);
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog Detail;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tbDetailDT;
    private javax.swing.JTable tbDetailSP;
    private javax.swing.JTextField txtMa;
    // End of variables declaration//GEN-END:variables
}
