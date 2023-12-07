/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package caitiemcafe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author GIA BAO
 */
public class PanelVoucher extends javax.swing.JPanel {

    /**
     * Creates new form PanelVoucher
     */
    public PanelVoucher() {
        initComponents();
        table.setModel(model);
        loadDatatoTable();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        btnNew = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        table.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(table);

        btnNew.setText("+New");
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnXoa.setText("Delete");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 918, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSave)
                .addGap(18, 18, 18)
                .addComponent(btnXoa)
                .addGap(18, 18, 18)
                .addComponent(btnUpdate)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 549, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNew)
                    .addComponent(btnSave)
                    .addComponent(btnXoa)
                    .addComponent(btnUpdate))
                .addGap(0, 24, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        // TODO add your handling code here:
        Object[] rowData = {
            "",
            ""
        };

        model.addRow(rowData);
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) { // Kiểm tra xem có hàng nào được chọn không
            String ma = model.getValueAt(selectedRow, 0).toString();
            int so;

            // Kiểm tra số có phải là số nguyên không
            try {
                so = Integer.parseInt(model.getValueAt(selectedRow, 1).toString());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số nguyên cho trường số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return; // Kết thúc hàm nếu có lỗi
            }

            // Kiểm tra điều kiện trước khi lưu dữ liệu
            if (ma.isEmpty() || so < 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin và số phải lớn hơn hoặc bằng 0!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            } else {
                saveData(ma, so);
                loadDatatoTable();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một hàng để lưu dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        int selectedRow = table.getSelectedRow();
        String ma = model.getValueAt(selectedRow, 0).toString();
        deleteData(ma);
        loadDatatoTable();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void updateData(String ma, int giam) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String dbUrl = "jdbc:sqlserver://LAPTOP-1QVPM352\\MSSQLEXPRESS:1433;databaseName=CAFEACCOUNT;user=sa;password=sa2023";
            Connection con = DriverManager.getConnection(dbUrl);
            Statement s = con.createStatement();
            String query = "UPDATE VOUCHER SET GIAM = '" + giam + "' WHERE MA = '" + ma + "';";
            s.executeUpdate(query);

            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Bạn không được thay đổi Mã", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        int selectedRow = table.getSelectedRow();
        String ma = model.getValueAt(selectedRow, 0).toString();
        int so = Integer.parseInt(model.getValueAt(selectedRow, 1).toString());
        updateData(ma, so);
        loadDatatoTable();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void deleteData(String ma) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String dbUrl = "jdbc:sqlserver://LAPTOP-1QVPM352\\MSSQLEXPRESS:1433;databaseName=CAFEACCOUNT;user=sa;password=sa2023";
            Connection con = DriverManager.getConnection(dbUrl);
            Statement s = con.createStatement();
            String query = "DELETE FROM VOUCHER WHERE MA = '" + ma + "'";
            s.executeUpdate(query);

            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void saveData(String ma, int so) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String dbUrl = "jdbc:sqlserver://LAPTOP-1QVPM352\\MSSQLEXPRESS:1433;databaseName=CAFEACCOUNT;user=sa;password=sa2023";
            Connection con = DriverManager.getConnection(dbUrl);
            Statement s = con.createStatement();
            String query = "INSERT INTO VOUCHER VALUES ('" + ma + "', '" + so + "');";
            s.executeUpdate(query);

            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Mã không được trùng nhau", "Lỗi", JOptionPane.ERROR_MESSAGE);

        }
    }

    private void loadDatatoTable() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String dbUrl = "jdbc:sqlserver://LAPTOP-1QVPM352\\MSSQLEXPRESS:1433;databaseName=CAFEACCOUNT;user=sa;password=sa2023";
            Connection con = DriverManager.getConnection(dbUrl);
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM VOUCHER");

            model.setRowCount(0);
            while (rs.next()) {
                Object[] rowData = {
                    rs.getString("MA"),
                    rs.getInt("GIAM")
                };

                model.addRow(rowData);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Tạo một lớp mô hình bảng tùy chỉnh
    public class NonEditableTableModel extends DefaultTableModel {

        private final boolean[] editableColumns;  // Mảng boolean để lưu trạng thái chỉnh sửa của từng cột

        // Hàm tạo của lớp với tham số truyền vào dữ liệu, tên cột và trạng thái chỉnh sửa của cột
        public NonEditableTableModel(Object[][] data, Object[] columnNames, boolean[] editableColumns) {
            // Gọi hàm tạo của lớp cha (DefaultTableModel) để khởi tạo mô hình bảng
            super(data, columnNames);
            // Sao chép mảng editableColumns để tránh ảnh hưởng của thay đổi từ bên ngoài
            this.editableColumns = editableColumns.clone();
        }

        // Ghi đè phương thức isCellEditable để kiểm soát khả năng chỉnh sửa của từng ô
        @Override
        public boolean isCellEditable(int row, int column) {
            // Trả về giá trị từ mảng editableColumns tương ứng với cột hiện tại
            return editableColumns[column];
        }
    }

    // Mảng boolean để xác định trạng thái chỉnh sửa của từng cột
    boolean[] editableColumns = {true, true}; // Đặt true cho các cột có thể chỉnh sửa
    // Sử dụng lớp NonEditableTableModel thay vì DefaultTableModel
    NonEditableTableModel model = new NonEditableTableModel(new Object[][]{}, new String[]{"MÃ", "GIẢM"}, editableColumns);

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnXoa;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
