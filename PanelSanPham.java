/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package caitiemcafe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 *
 * @author GIA BAO
 */
public class PanelSanPham extends javax.swing.JPanel {

    /**
     * Creates new form PanelSanPham
     */
    public PanelSanPham() {
        initComponents();
        loadSanPham();
        this.setLayout(new FlowLayout());

        JPopupMenu jPopupMenu = new JPopupMenu();

        JMenuItem btnAddNew = new JMenuItem("Tạo sản phẩm mới");
        jPopupMenu.add(btnAddNew);

        //Kích hoạt Popup
        this.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showPopupMenu(e);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showPopupMenu(e);
                }
            }

            private void showPopupMenu(MouseEvent e) {
                jPopupMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        });

        btnAddNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createSanPham();

            }

        });
    }

    private void loadSanPham() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String dbUrl = "jdbc:sqlserver://LAPTOP-1QVPM352\\MSSQLEXPRESS:1433;databaseName=CAFEACCOUNT;user=sa;password=sa2023";
            Connection con = DriverManager.getConnection(dbUrl);
            Statement s = con.createStatement();

            String query = "select * from SanPham";
            ResultSet rs = s.executeQuery(query);

            this.removeAll();

            while (rs.next()) {
                String so = rs.getString("ID");
                String ten = rs.getString("Ten");
                String gia = rs.getString("GIA");
                String phucvu = rs.getString("PHUC_VU");

                createSanPham(so, ten, gia, phucvu);
            }

            this.repaint();
            this.revalidate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void createSanPham(String so, String ten, String gia, String phucvu) {
        //Tạo các panel
        JPanel panelContainer = new JPanel();
        panelContainer.setLayout(new BorderLayout());
        panelContainer.setPreferredSize(new Dimension(200, 75));
        panelContainer.setBackground(new Color(126, 215, 193));
        LineBorder border = new LineBorder(new java.awt.Color(255, 255, 255), 3); // Đường viền màu đen có độ rộng 1 pixel
        panelContainer.setBorder(border);
        //Tạo padding
        // Tạo khoảng trắng 10 pixels cho panel (có thể điều chỉnh theo ý muốn)
        EmptyBorder padding = new EmptyBorder(5, 5, 5, 5);

        // Kết hợp đường viền và khoảng trắng cho panel
        Border compoundBorder = BorderFactory.createCompoundBorder(border, padding);
        panelContainer.setBorder(compoundBorder);

        //Thêm các thông tin vào panel
        JLabel txtSo = new JLabel(so);
        JTextField txtTen = new JTextField(ten);
        JTextField txtGia = new JTextField(gia);

        //Tạo một JPopupMenu mới
        JPopupMenu popupMenu = new JPopupMenu();

        //Thêm các item vào Popup
        JMenuItem menuItemSave = new JMenuItem("Lưu");
        JMenuItem menuItemPV = new JMenuItem("Phục vụ");
        JMenuItem menuItemKhongPV = new JMenuItem("Không phục vụ");
        JMenuItem menuItemXoa = new JMenuItem("Xóa sản phẩm");

        popupMenu.add(menuItemPV);
        popupMenu.add(menuItemKhongPV);
        popupMenu.add(menuItemXoa);
        popupMenu.add(menuItemSave);

        //Kích hoạt Popup Menu
        panelContainer.addMouseListener(new MouseAdapter() {
            @Override
            //Kiểm tra sự kiện click chuột có phải là chuột phải không
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showPopupMenu(e);
                }
            }

            //Sự kiện nhả chuột
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showPopupMenu(e);
                }
            }

            //Show popup menu tại vị trí con trỏ chuột
            private void showPopupMenu(MouseEvent e) {
                popupMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        });

        //sự kiện được kích hoạt khi người dùng nhấp vào menuItemSave. 
        menuItemSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lấy giá trị của txtSo ở panelContainer được chọn
                // Lấy ra thành phần thứ 3 được add vào panelContainer
                JLabel txtSo = (JLabel) panelContainer.getComponent(3);
                String so = txtSo.getText();
                String ten = txtTen.getText();
                String gia = txtGia.getText();

                updateSanPham(so, ten, gia);
            }
        });

        menuItemPV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String so = txtSo.getText();

                updatePV(so);
            }
        });

        menuItemKhongPV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String so = txtSo.getText();

                updateKhongPV(so);
            }
        });

        menuItemXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String so = txtSo.getText();

                int choice = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa?", "Warning!!!", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    xoaSanPham(so);
                }
            }

        });

        //Add các thành phần vào Panel
        if ("1".equals(phucvu)) {
            JLabel txtPhucVu = new JLabel("Đang phục vụ");
            panelContainer.add(txtPhucVu, BorderLayout.SOUTH);
        } else {
            JLabel txtKhongPhucVu = new JLabel("Không phục vụ");
            panelContainer.add(txtKhongPhucVu, BorderLayout.SOUTH);
            panelContainer.setBackground(new Color(240, 219, 175));
        }

        panelContainer.add(txtTen, BorderLayout.WEST);//1
        panelContainer.add(txtGia, BorderLayout.EAST);//2
        panelContainer.add(txtSo, BorderLayout.NORTH);//3

        this.add(panelContainer);
        this.repaint();
        this.revalidate();
    }

    private void createSanPham() {
        //Tạo các panel
        JPanel panelContainer = new JPanel();
        panelContainer.setLayout(new BorderLayout());
        panelContainer.setPreferredSize(new Dimension(200, 75));
        panelContainer.setBackground(new Color(241, 212, 229));

        Random random = new Random();
        int randomValue = 100 + random.nextInt(900);

        //lblSoHoaDon.setText(String.valueOf(randomValue));
        //Thêm các thông tin vào panel
        JLabel txtSo = new JLabel(String.valueOf(randomValue));
        JTextField txtTen = new JTextField("Nhập tên");
        JTextField txtGia = new JTextField("10000");

        //Tạo một JPopupMenu mới
        JPopupMenu popupMenu = new JPopupMenu();

        //Thêm các item vào Popup
        JMenuItem menuItemSave = new JMenuItem("Lưu");

        popupMenu.add(menuItemSave);

        //Kích hoạt Popup Menu
        panelContainer.addMouseListener(new MouseAdapter() {
            @Override
            //Kiểm tra sự kiện click chuột có phải là chuột phải không
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showPopupMenu(e);
                }
            }

            //Sự kiện nhả chuột
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    showPopupMenu(e);
                }
            }

            //Show popup menu tại vị trí con trỏ chuột
            private void showPopupMenu(MouseEvent e) {
                popupMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        });

        //sự kiện được kích hoạt khi người dùng nhấp vào menuItemSave. 
        menuItemSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lấy giá trị của txtSo ở panelContainer được chọn
                // Lấy ra thành phần thứ 3 được add vào panelContainer
                String so = txtSo.getText();
                String ten = txtTen.getText();
                String gia = txtGia.getText();

                // Kiểm tra xem giá là số nguyên hay không
                try {
                    int giaInt = Integer.parseInt(gia);

                    // Nếu không có lỗi, tiếp tục với các bước khác
                    addSPtoData(so, ten, gia);
                } catch (NumberFormatException ex) {
                    // Nếu có lỗi, hiển thị thông báo lỗi
                    JOptionPane.showMessageDialog(getRootPane(), "Vui lòng nhập một số nguyên cho giá!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panelContainer.add(txtTen, BorderLayout.WEST);//1
        panelContainer.add(txtGia, BorderLayout.EAST);//2
        panelContainer.add(txtSo, BorderLayout.NORTH);//3

        this.add(panelContainer);
        this.repaint();
        this.revalidate();
    }

    private void addSPtoData(String so, String ten, String gia) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String dbUrl = "jdbc:sqlserver://LAPTOP-1QVPM352\\MSSQLEXPRESS:1433;databaseName=CAFEACCOUNT;user=sa;password=sa2023";
            Connection con = DriverManager.getConnection(dbUrl);
            Statement s = con.createStatement();

            String query = "INSERT INTO SanPham VALUES ('" + ten + "', '" + gia + "', '1','" + so + "');";
            s.executeUpdate(query);

            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            loadSanPham();

            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void updateSanPham(String so, String ten, String gia) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String dbUrl = "jdbc:sqlserver://LAPTOP-1QVPM352\\MSSQLEXPRESS:1433;databaseName=CAFEACCOUNT;user=sa;password=sa2023";
            Connection con = DriverManager.getConnection(dbUrl);
            Statement s = con.createStatement();

            String query = "UPDATE SanPham SET TEN = '" + ten + "', GIA ='" + gia + "' WHERE ID =" + so;
            s.executeUpdate(query);

            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            loadSanPham();

            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void updatePV(String so) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String dbUrl = "jdbc:sqlserver://LAPTOP-1QVPM352\\MSSQLEXPRESS:1433;databaseName=CAFEACCOUNT;user=sa;password=sa2023";
            Connection con = DriverManager.getConnection(dbUrl);
            Statement s = con.createStatement();

            String query = "UPDATE SanPham SET PHUC_VU = '1' WHERE ID =" + so;
            s.executeUpdate(query);

            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            loadSanPham();

            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void updateKhongPV(String so) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String dbUrl = "jdbc:sqlserver://LAPTOP-1QVPM352\\MSSQLEXPRESS:1433;databaseName=CAFEACCOUNT;user=sa;password=sa2023";
            Connection con = DriverManager.getConnection(dbUrl);
            Statement s = con.createStatement();

            String query = "UPDATE SanPham SET PHUC_VU = '0' WHERE ID =" + so;
            s.executeUpdate(query);

            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            loadSanPham();

            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void xoaSanPham(String so) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String dbUrl = "jdbc:sqlserver://LAPTOP-1QVPM352\\MSSQLEXPRESS:1433;databaseName=CAFEACCOUNT;user=sa;password=sa2023";
            Connection con = DriverManager.getConnection(dbUrl);
            Statement s = con.createStatement();

            String query = "DELETE FROM SanPham WHERE ID =" + so;
            s.executeUpdate(query);

            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            loadSanPham();

            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(255, 245, 225));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1029, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 821, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void setIconImage(Image image) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
