/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package caitiemcafe;

import caitiemcafe.Login.SessionManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author GIA BAO
 */
public class Home_ver2 extends javax.swing.JFrame {

    /**
     * Creates new form Home_ver2
     */
    
    public Home_ver2() {
        initComponents();
        //SET FULL SCREEN
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        loadData();
        getContentPane().setBackground(Color.WHITE);

        tableDetail.setModel(model);
        tableBillDetail.setModel(modelBill);
        tableBillDetail.setEnabled(false);
        
        String iconPath = "D:\\Java Project\\CaiTiemCafe\\src\\caitiemcafe\\Image\\White_and_Brown_Elegant_Simple_Boba_Drink_Logo-removebg-preview.png";
        ImageIcon icon = new ImageIcon(iconPath);
        setIconImage(icon.getImage());
        SanPham.setIconImage(icon.getImage());
        Timkiem.setIconImage(icon.getImage());

        //Nút xóa được xử lý
        tableDetail.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = tableDetail.rowAtPoint(evt.getPoint());
                int col = tableDetail.columnAtPoint(evt.getPoint());
                if (row >= 0 && col == 7) {
                    // Xóa dòng khi nút được nhấp
                    model.removeRow(row);
                    updateSTT(); // Cập nhật lại STT sau khi xóa dòng
                }
            }
        });

        //Thêm sự kiện cho nút "Tăng"
        tableDetail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = tableDetail.rowAtPoint(evt.getPoint());
                int col = tableDetail.columnAtPoint(evt.getPoint());
                if (row >= 0 && col == 5) {
                    int soLuongHienTai = Integer.parseInt((String) model.getValueAt(row, 2));
                    model.setValueAt(String.valueOf(soLuongHienTai + 1), row, 2);
                }
            }
        });

        //Thêm sự kiện cho nút "Giảm"
        tableDetail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = tableDetail.rowAtPoint(evt.getPoint());
                int col = tableDetail.columnAtPoint(evt.getPoint());
                if (row >= 0 && col == 6) {
                    int soLuongHienTai = Integer.parseInt((String) model.getValueAt(row, 2));

                    if (soLuongHienTai == 1) {
                        model.removeRow(row);
                        updateSTT(); // Cập nhật lại STT sau khi xóa dòng
                    } else {
                        model.setValueAt(String.valueOf(soLuongHienTai - 1), row, 2);
                    }
                }
            }
        });

        model.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int row = e.getFirstRow();
                int col = e.getColumn();

                // Kiểm tra xem sự kiện có phải là sự thay đổi trong cột số lượng không
                if (col == 2) {
                    updateTotalForRow(row);
                }
            }
        });

        //Thêm sự kiện lắng nghe cho txtTienKhachDua
        txtTienKhachDua.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateTienTraLai(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateTienTraLai(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateTienTraLai(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

        });

        //Tăng độ rộng của 1 cột
        TableColumn column = tableDetail.getColumnModel().getColumn(1);
        column.setPreferredWidth(300);
        TableColumn column1 = tableBillDetail.getColumnModel().getColumn(1);
        column1.setPreferredWidth(200);

        //Thêm button vào bảng
        TableColumn deleteColumn = tableDetail.getColumnModel().getColumn(7);
        TableColumn tangColumn = tableDetail.getColumnModel().getColumn(6);
        TableColumn giamColumn = tableDetail.getColumnModel().getColumn(5);
        deleteColumn.setCellRenderer(new Home_ver2.ButtonRenderer());
        deleteColumn.setCellEditor(new Home_ver2.ButtonEditor(new JCheckBox()));
        tangColumn.setCellRenderer(new Home_ver2.ButtonRenderer());
        tangColumn.setCellEditor(new Home_ver2.ButtonEditor(new JCheckBox()));
        giamColumn.setCellRenderer(new Home_ver2.ButtonRenderer());
        giamColumn.setCellEditor(new Home_ver2.ButtonEditor(new JCheckBox()));
        
        pnMain.setLayout(new BorderLayout());
        pnMainTK.setLayout(new BorderLayout());
        
        SanPham.setPreferredSize(new Dimension(1000, 700));
        Timkiem.setPreferredSize(new Dimension(720, 400));
        
    }

    private void loadDataToBill(){
        modelBill.setRowCount(0);
        for(int i=0; i<model.getRowCount(); i++){
            modelBill.addRow(new Object[]{
                model.getValueAt(i, 0).toString(),
                model.getValueAt(i, 1).toString(),
                model.getValueAt(i, 2).toString(),
                model.getValueAt(i, 3).toString(),
                model.getValueAt(i, 4)
            });
        }
    }
    
    private void updateTienTraLai() {
        try {
            //Lấy giá trị từ txtTienKhachDua và lblTong
            int tienKhachDua = Integer.parseInt(txtTienKhachDua.getText());
            int tong = Integer.parseInt(lblThanhToan.getText().replaceAll("[^0-9]", ""));

            //Tính toán và cập nhật giá trị tiền trả lại
            int tienTraLai = tienKhachDua - tong;
            lblTienTraLai.setText(tienTraLai + " VNĐ");
        } catch (NumberFormatException ex) {
            lblTienTraLai.setText("0.0 VNĐ");
        }
    }

    private void updateSTT() {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.setValueAt(i + 1, i, 0); // Cột 0 là cột chứa STT
        }
    }

    private void updateTotalForRow(int row) {
        try {
            // Lấy giá trị số lượng và đơn giá từ bảng
            int soLuong = Integer.parseInt(model.getValueAt(row, 2).toString());
            int donGia = Integer.parseInt(model.getValueAt(row, 3).toString());

            // Tính toán tổng
            int tong = soLuong * donGia;

            // Cập nhật giá trị tổng trong bảng
            model.setValueAt(tong, row, 4);
        } catch (NumberFormatException e) {
            // Xử lý nếu giá trị không phải là số
            System.out.println("Giá trị không hợp lệ");
        }
    }

    private void loadData() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String dbUrl = "jdbc:sqlserver://LAPTOP-1QVPM352\\MSSQLEXPRESS:1433;databaseName=CAFEACCOUNT;user=sa;password=sa2023";
            Connection con = DriverManager.getConnection(dbUrl);
            Statement s = con.createStatement();

            String query = "select * from SanPham";
            ResultSet rs = s.executeQuery(query);

            while (rs.next()) {
                String ten = rs.getString("TEN");
                String gia = String.valueOf(rs.getInt("GIA"));
                
                pnShow.setLayout(new FlowLayout());

                JPanel panel = new JPanel();
                panel.setLayout(new BorderLayout());
                panel.setBackground(Color.white);
                
                JLabel lblTen = new JLabel(ten);
                JLabel lblGia = new JLabel(gia + " VNĐ");
                JLabel lblTextGia = new JLabel("Giá: ");
                JLabel lblPhuVu = new JLabel("HẾT HÀNG");
                lblTen.setForeground(new java.awt.Color(26, 172, 172));

                // Đặt chữ in đậm cho JLabel
                Font boldFont = new Font(lblTen.getFont().getName(), Font.BOLD, lblTen.getFont().getSize());
                lblTen.setFont(boldFont);

                panel.add(lblTen, BorderLayout.NORTH);
                panel.add(lblGia, BorderLayout.EAST);
                panel.add(lblTextGia, BorderLayout.WEST);
                panel.setPreferredSize(new Dimension(150, 150));

                
                //Set maù nền và màu chữ cho Panel

                panel.setForeground(new java.awt.Color(0, 0, 0));

                //SET MÀU ĐƯỜNG VIỀN CHO panel
                LineBorder border = new LineBorder(new java.awt.Color(255, 217, 183), 3); // Đường viền màu đen có độ rộng 1 pixel
                panel.setBorder(border);

                //Tạo padding
                // Tạo khoảng trắng 10 pixels cho panel (có thể điều chỉnh theo ý muốn)
                EmptyBorder padding = new EmptyBorder(5, 5, 5, 5);

                // Kết hợp đường viền và khoảng trắng cho panel
                Border compoundBorder = BorderFactory.createCompoundBorder(border, padding);
                panel.setBorder(compoundBorder);

                //Thêm sự kiện vào button
                panel.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        addTable(evt, ten, gia);

                    }
                });
                
                if (rs.getString("PHUC_VU").equals("1")) {
                    pnShow.add(panel);
                    
                }
            }

            rs.close();
            con.close();

            pnShow.repaint();
            pnShow.revalidate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //Hàm xử lý xự kiện click chuộ
    private void addTable(java.awt.event.MouseEvent evt, String ten, String gia) {

        boolean daCoDongGiongTen = false;
        //Kiểm tra sản phẩm đã tồn tại trong bảng chưa nếu tồn tại thì tăng thêm
        for (int i = 0; i < model.getRowCount(); i++) {
            String tenDongHienTai = (String) model.getValueAt(i, 1);
            if (tenDongHienTai.equals(ten)) {
                int soLuongHienTai = Integer.parseInt((String) model.getValueAt(i, 2));
                model.setValueAt(String.valueOf(soLuongHienTai + 1), i, 2);
                daCoDongGiongTen = true;
                break;
            }
        }

        //Nếu chưa tồn tại thì add dòng mới
        if (!daCoDongGiongTen) {
            model.addRow(new Object[]{
                model.getRowCount() + 1,
                ten,
                "1",
                gia,
                gia,
                "Tăng",
                "Giảm",
                "Xóa"
            });
        }

        model.fireTableDataChanged();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        billDetail = new javax.swing.JDialog();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableBillDetail = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        btnInHoaDon = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lblNhanVienThuNgan = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        lblDayNow = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        btnLuuHoaDon = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        lblBillTong = new javax.swing.JLabel();
        lblBillTienGiam = new javax.swing.JLabel();
        lblBillThanhToan = new javax.swing.JLabel();
        lblBillTienKhachDua = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        lblBillTienTraLai = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        lblSoHoaDon = new javax.swing.JLabel();
        SanPham = new javax.swing.JDialog();
        pnMain = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        Timkiem = new javax.swing.JDialog();
        pnMainTK = new javax.swing.JPanel();
        pnShow = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableDetail = new javax.swing.JTable();
        btnNhap = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtVoucher = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        lblTong = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtTienKhachDua = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        lblTienTraLai = new javax.swing.JLabel();
        btnAddVoucher = new javax.swing.JButton();
        jLabel29 = new javax.swing.JLabel();
        lblThanhToan = new javax.swing.JLabel();
        btnBillDetail = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();

        billDetail.setTitle("CHI TIẾT HÓA ĐƠN");
        billDetail.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane3.setBackground(new java.awt.Color(255, 255, 255));

        tableBillDetail.setBackground(new java.awt.Color(255, 255, 255));
        tableBillDetail.setForeground(new java.awt.Color(0, 153, 153));
        tableBillDetail.setModel(new javax.swing.table.DefaultTableModel(
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
        tableBillDetail.setGridColor(new java.awt.Color(0, 204, 204));
        tableBillDetail.setRowHeight(25);
        tableBillDetail.setSelectionBackground(new java.awt.Color(0, 204, 204));
        tableBillDetail.setSelectionForeground(new java.awt.Color(255, 255, 255));
        jScrollPane3.setViewportView(tableBillDetail);

        jLabel5.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 204, 204));
        jLabel5.setText("CHI TIẾT :");

        btnInHoaDon.setBackground(new java.awt.Color(255, 255, 255));
        btnInHoaDon.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        btnInHoaDon.setForeground(new java.awt.Color(0, 204, 204));
        btnInHoaDon.setText("IN");

        jButton5.setBackground(new java.awt.Color(255, 0, 51));
        jButton5.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("ĐÓNG");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 204, 204));
        jLabel7.setText("HÓA ĐƠN BÁN HÀNG");

        jLabel10.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(153, 0, 51));
        jLabel10.setText("CÁI TIỆM CÀ PHÊ");

        jLabel11.setText("3/2, Xuân khánh, Ninh Kiều, TP.Cần Thơ");

        jLabel12.setText("Nhân viên thu ngân : ");

        lblNhanVienThuNgan.setText("jLabel13");

        jLabel14.setText("Ngày:");

        lblDayNow.setText("20-8-2023");

        jLabel16.setText("Số:");

        jLabel18.setText("-------------------o0o------------------");

        btnLuuHoaDon.setBackground(new java.awt.Color(255, 255, 255));
        btnLuuHoaDon.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        btnLuuHoaDon.setForeground(new java.awt.Color(51, 204, 0));
        btnLuuHoaDon.setText("LƯU");
        btnLuuHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuHoaDonActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setText("Tiền khách đưa");

        jLabel19.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 0, 0));
        jLabel19.setText("Tiền giảm: ");

        jLabel20.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 0, 0));
        jLabel20.setText("Thanh toán");

        jLabel21.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(0, 0, 0));
        jLabel21.setText("Tổng: ");

        jLabel22.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(0, 0, 0));
        jLabel22.setText("Tiền trả lại: ");

        lblBillTong.setText("0.0 VNĐ");

        lblBillTienGiam.setText("0.0 VNĐ");

        lblBillThanhToan.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        lblBillThanhToan.setForeground(new java.awt.Color(0, 204, 204));
        lblBillThanhToan.setText("0.0 VNĐ");

        lblBillTienKhachDua.setText("0.0 VNĐ");

        jLabel27.setText("jLabel27");

        lblBillTienTraLai.setText("0.0 VNĐ");

        jLabel31.setForeground(new java.awt.Color(51, 51, 0));
        jLabel31.setText("_______________________________________________________________");

        jLabel32.setText("SĐT : 0789668216 - caitiemcafe@gmail.com");

        lblSoHoaDon.setText("00");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(88, 88, 88)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(jLabel16))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblDayNow, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                            .addComponent(lblSoHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(26, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel7))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(193, 193, 193))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblNhanVienThuNgan, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(jLabel20)
                            .addComponent(jLabel19)
                            .addComponent(jLabel22)
                            .addComponent(jLabel21))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(lblBillTienKhachDua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblBillThanhToan, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnInHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnLuuHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblBillTong, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblBillTienGiam, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lblBillTienTraLai, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGap(99, 99, 99)
                        .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGap(191, 191, 191)
                        .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel16)
                                    .addComponent(lblSoHoaDon)))
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel14)
                                    .addComponent(lblDayNow))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 102, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel7)
                                .addGap(31, 31, 31))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(74, 74, 74)
                                .addComponent(jLabel17)))
                        .addGap(18, 18, Short.MAX_VALUE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(lblNhanVienThuNgan))
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(lblBillTong))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(lblBillTienGiam))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(btnInHoaDon)
                            .addComponent(lblBillThanhToan))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(btnLuuHoaDon)
                            .addComponent(lblBillTienKhachDua))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(jButton5)
                            .addComponent(lblBillTienTraLai)))
                    .addComponent(jLabel27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel32)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout billDetailLayout = new javax.swing.GroupLayout(billDetail.getContentPane());
        billDetail.getContentPane().setLayout(billDetailLayout);
        billDetailLayout.setHorizontalGroup(
            billDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(billDetailLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        billDetailLayout.setVerticalGroup(
            billDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        SanPham.setTitle("Cập nhật món");
        SanPham.setBackground(new java.awt.Color(255, 255, 255));
        SanPham.setIconImage(null);
        SanPham.setIconImages(null);

        pnMain.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout pnMainLayout = new javax.swing.GroupLayout(pnMain);
        pnMain.setLayout(pnMainLayout);
        pnMainLayout.setHorizontalGroup(
            pnMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnMainLayout.setVerticalGroup(
            pnMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 406, Short.MAX_VALUE)
        );

        jButton2.setBackground(new java.awt.Color(0, 153, 153));
        jButton2.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jButton2.setText("Save");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout SanPhamLayout = new javax.swing.GroupLayout(SanPham.getContentPane());
        SanPham.getContentPane().setLayout(SanPhamLayout);
        SanPhamLayout.setHorizontalGroup(
            SanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(SanPhamLayout.createSequentialGroup()
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 720, Short.MAX_VALUE))
        );
        SanPhamLayout.setVerticalGroup(
            SanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SanPhamLayout.createSequentialGroup()
                .addComponent(pnMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 161, Short.MAX_VALUE)
                .addComponent(jButton2))
        );

        Timkiem.setTitle("Tìm kiếm hóa đơn");

        javax.swing.GroupLayout pnMainTKLayout = new javax.swing.GroupLayout(pnMainTK);
        pnMainTK.setLayout(pnMainTKLayout);
        pnMainTKLayout.setHorizontalGroup(
            pnMainTKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 918, Short.MAX_VALUE)
        );
        pnMainTKLayout.setVerticalGroup(
            pnMainTKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 582, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout TimkiemLayout = new javax.swing.GroupLayout(Timkiem.getContentPane());
        Timkiem.getContentPane().setLayout(TimkiemLayout);
        TimkiemLayout.setHorizontalGroup(
            TimkiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnMainTK, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        TimkiemLayout.setVerticalGroup(
            TimkiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnMainTK, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CÁI TIỆM CÀ PHÊ ");
        setBackground(new java.awt.Color(255, 255, 255));

        pnShow.setBackground(new java.awt.Color(158, 210, 190));
        pnShow.setPreferredSize(new java.awt.Dimension(500, 674));

        javax.swing.GroupLayout pnShowLayout = new javax.swing.GroupLayout(pnShow);
        pnShow.setLayout(pnShowLayout);
        pnShowLayout.setHorizontalGroup(
            pnShowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );
        pnShowLayout.setVerticalGroup(
            pnShowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        tableDetail.setBackground(new java.awt.Color(255, 255, 255));
        tableDetail.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        tableDetail.setForeground(new java.awt.Color(0, 153, 153));
        tableDetail.setModel(new javax.swing.table.DefaultTableModel(
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
        tableDetail.setEnabled(false);
        tableDetail.setRowHeight(24);
        jScrollPane1.setViewportView(tableDetail);

        btnNhap.setBackground(new java.awt.Color(65, 145, 151));
        btnNhap.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        btnNhap.setForeground(new java.awt.Color(255, 255, 255));
        btnNhap.setText("NHẬP");
        btnNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhapActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "THANH TOÁN", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("sansserif", 1, 12), new java.awt.Color(0, 153, 153))); // NOI18N
        jPanel1.setForeground(new java.awt.Color(0, 204, 204));

        jLabel2.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel2.setText("NHẬP MÃ GIẢM GIÁ :");

        jLabel6.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel6.setText("TỔNG : ");

        lblTong.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        lblTong.setForeground(new java.awt.Color(0, 204, 204));
        lblTong.setText("0.0 VNĐ");

        jLabel8.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel8.setText("TIỀN KHÁCH ĐƯA: ");

        txtTienKhachDua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTienKhachDuaActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel4.setText("TIỀN TRẢ LẠI: ");

        lblTienTraLai.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        lblTienTraLai.setForeground(new java.awt.Color(204, 204, 0));
        lblTienTraLai.setText("0.0 VNĐ");

        btnAddVoucher.setBackground(new java.awt.Color(255, 255, 255));
        btnAddVoucher.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        btnAddVoucher.setForeground(new java.awt.Color(0, 204, 204));
        btnAddVoucher.setText("ADD");
        btnAddVoucher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddVoucherActionPerformed(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel29.setText("THANH TOÁN :");

        lblThanhToan.setBackground(new java.awt.Color(255, 255, 255));
        lblThanhToan.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        lblThanhToan.setForeground(new java.awt.Color(51, 204, 0));
        lblThanhToan.setText("0.0 VNĐ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtVoucher))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblTong, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(82, 82, 82)
                        .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAddVoucher)
                    .addComponent(lblThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(38, 38, 38)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTienKhachDua, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTienTraLai, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtVoucher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txtTienKhachDua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddVoucher))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(lblTong)
                    .addComponent(jLabel4)
                    .addComponent(lblTienTraLai)
                    .addComponent(jLabel29)
                    .addComponent(lblThanhToan))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        btnBillDetail.setBackground(new java.awt.Color(255, 255, 255));
        btnBillDetail.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        btnBillDetail.setForeground(new java.awt.Color(51, 204, 0));
        btnBillDetail.setText("CHI TIẾT HÓA ĐƠN");
        btnBillDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBillDetailActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(0, 204, 153));
        jButton1.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("ADD NEW");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jMenuBar1.setBackground(new java.awt.Color(255, 255, 255));
        jMenuBar1.setForeground(new java.awt.Color(255, 255, 255));
        jMenuBar1.setPreferredSize(new java.awt.Dimension(70, 35));

        jMenu1.setText("File");
        jMenu1.setPreferredSize(new java.awt.Dimension(40, 19));

        jMenuItem1.setText("Add New");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem5.setText("Tìm kiếm hóa đơn");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem5);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Account");

        jMenuItem2.setText("Chuyển đổi tài khoản");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Edit");
        jMenu3.setPreferredSize(new java.awt.Dimension(40, 19));

        jMenuItem3.setText("Cập nhật món");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem3);

        jMenuItem4.setText("Refresh");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem4);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnShow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnBillDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNhap, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnShow, javax.swing.GroupLayout.DEFAULT_SIZE, 693, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 472, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNhap)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBillDetail)
                    .addComponent(jButton1))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhapActionPerformed
        // TODO add your handling code here:
        String voucherCode = txtVoucher.getText().trim();

        calculateTotalAndSetLabel();
        if (voucherCode.isEmpty()) {
            lblThanhToan.setText(lblTong.getText());
        }
    }//GEN-LAST:event_btnNhapActionPerformed

    private void calculateTotalAndSetLabel() {
        int rowCount = model.getRowCount();
        int total = 0;

        //Lặp các dòng trong bảng để tính tổng
        for (int i = 0; i < rowCount; i++) {
            try {
                //Lấy giá trị từ cột tổng của mỗi dòng
                int rowTotal = Integer.parseInt(model.getValueAt(i, 4).toString());
                total += rowTotal;
            } catch (NumberFormatException ex) {
                System.out.println("Gía trị không hợp lệ");
            }
        }

        lblTong.setText(total + " VNĐ");
    }

    private void txtTienKhachDuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTienKhachDuaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTienKhachDuaActionPerformed

    private void btnAddVoucherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddVoucherActionPerformed
        // TODO add your handling code here:
        handleAddVoucher();
    }//GEN-LAST:event_btnAddVoucherActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        billDetail.setVisible(false);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void btnLuuHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuHoaDonActionPerformed
        // TODO add your handling code here:
        String soHoaDon = lblSoHoaDon.getText();
        String dayNow = lblDayNow.getText();
        String nhanVienThuNgan = lblNhanVienThuNgan.getText();
        int tong = Integer.parseInt(lblBillTong.getText().replaceAll("[^0-9]", ""));
        int giam = Integer.parseInt(lblBillTienGiam.getText().replaceAll("[^0-9]", ""));
        int thanhToan = Integer.parseInt(lblBillThanhToan.getText().replaceAll("[^0-9]", ""));
        int tienKhachDua = Integer.parseInt(lblBillTienKhachDua.getText().replaceAll("[^0-9]", ""));
        int tienTraLai = Integer.parseInt(lblBillTienTraLai.getText().replaceAll("[^0-9]", ""));
        insertHoaDon(soHoaDon, dayNow, nhanVienThuNgan, tong, giam, thanhToan, tienKhachDua, tienTraLai);

        for (int i = 0; i < model.getRowCount(); i++) {
            String Ten = (String) model.getValueAt(i, 1);
            String SoLuong = (String) model.getValueAt(i, 2);
            String DonGia = (String) model.getValueAt(i, 3);
            String Tong = model.getValueAt(i, 4).toString();
            insertHoaDonSanPham(soHoaDon, Ten, SoLuong, DonGia, Tong);
        }
    }//GEN-LAST:event_btnLuuHoaDonActionPerformed

    private void insertHoaDonSanPham(String So, String Ten, String SoLuong, String DonGia, String Tong) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String dbUrl = "jdbc:sqlserver://LAPTOP-1QVPM352\\MSSQLEXPRESS:1433;databaseName=CAFEACCOUNT;user=sa;password=sa2023";
            Connection con = DriverManager.getConnection(dbUrl);
            Statement s = con.createStatement();
            String query = "INSERT INTO HoaDonSanPham VALUES ('" + So + "', '" + Ten + "', '" + SoLuong + "', '" + DonGia + "', '" + Tong + "');";
            s.executeUpdate(query);

            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void btnBillDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBillDetailActionPerformed
        // TODO add your handling code here:
        billDetail.setVisible(true);
        billDetail.pack();
        billDetail.setLocationRelativeTo(null);
        String voucherCode = txtVoucher.getText().trim();

        //Cập nhật lblDayNow với ngày hiện tại
        //        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd/MM/yyyy", new Locale("vi", "VN")); thứ tiếng Việt
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd/MM/yyyy");
        Date currentDate = new Date();
        String formattedDate = dateFormat.format(currentDate);

        lblDayNow.setText(formattedDate);
        String hoVaTen = SessionManager.getHoVaTen();
        lblNhanVienThuNgan.setText(hoVaTen);
        lblBillTong.setText(lblTong.getText());
        lblBillThanhToan.setText(lblThanhToan.getText());
        if (txtTienKhachDua.getText().isEmpty()) {
            lblBillTienKhachDua.setText("0.0 VNĐ");
        } else {
            lblBillTienKhachDua.setText(txtTienKhachDua.getText() + " VNĐ");
        }
        lblBillTienTraLai.setText(lblTienTraLai.getText());

        // Tạo số ngẫu nhiên có 8 chữ số
        Random random = new Random();
        int randomValue = 10000000 + random.nextInt(90000000); // Số từ 10000000 đến 99999999

        lblSoHoaDon.setText(String.valueOf(randomValue));
        loadDataToBill();
    }//GEN-LAST:event_btnBillDetailActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if (model.getRowCount() > 0) {
            int choice = JOptionPane.showConfirmDialog(null, "Bảng đã có dữ liệu. Bạn có chắc chắn muốn thêm mới?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                // Xóa hết dữ liệu trong bảng
                model.setRowCount(0);
                lblTong.setText("0.0 VNĐ");
                txtTienKhachDua.setText("");
                lblTienTraLai.setText("0.0 VNĐ");
                txtVoucher.setText("");
                lblThanhToan.setText("0.0 VNĐ");

            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        if (model.getRowCount() > 0) {
            int choice = JOptionPane.showConfirmDialog(null, "Bảng đã có dữ liệu. Bạn có chắc chắn muốn thêm mới?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                // Xóa hết dữ liệu trong bảng
                model.setRowCount(0);
                lblTong.setText("0.0 VNĐ");
                txtTienKhachDua.setText("");
                lblTienTraLai.setText("0.0 VNĐ");
                txtVoucher.setText("");
                lblThanhToan.setText("0.0 VNĐ");

            }
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        Login login = new Login();
        login.setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        SanPham.setVisible(true);
        SanPham.pack();
        
        pnMain.removeAll();
        pnMain.setPreferredSize(new Dimension(1000, 620));
        PanelSanPham panelSanPham = new PanelSanPham();
        pnMain.add(panelSanPham, BorderLayout.CENTER);
        pnMain.revalidate();
        pnMain.repaint();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        pnShow.removeAll();
        loadData();
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        pnShow.removeAll();
        loadData();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO add your handling code here:
        Timkiem.setVisible(true);
        Timkiem.pack();
        pnMainTK.removeAll();
        pnMainTK.setPreferredSize(new Dimension(1000, 620));
        PanelTimKiem panelTimKiem = new PanelTimKiem();
        pnMainTK.add(panelTimKiem, BorderLayout.CENTER);
        
        pnMainTK.revalidate();
        pnMainTK.repaint();
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void insertHoaDon(String soHoaDon, String dayNow, String nhanVienThuNgan, int tong, int giam, int thanhToan, int tienKhachDua, int tienTraLai) {

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String dbUrl = "jdbc:sqlserver://LAPTOP-1QVPM352\\MSSQLEXPRESS:1433;databaseName=CAFEACCOUNT;user=sa;password=sa2023";
            Connection con = DriverManager.getConnection(dbUrl);
            String query = "INSERT INTO HoaDon VALUES (?, ? , ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setString(1, soHoaDon);
                ps.setString(2, dayNow);
                ps.setString(3, nhanVienThuNgan);
                ps.setInt(4, tong);
                ps.setInt(5, giam);
                ps.setInt(6, thanhToan);
                ps.setInt(7, tienKhachDua);
                ps.setInt(8, tienTraLai);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Đã lưu !");
                billDetail.setVisible(false);
            }
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi cập nhật dữ liệu! Mã lỗi: " + " \" " + ex.getMessage() + " \"");
        }
    }

    private void handleAddVoucher() {
        String voucherCode = txtVoucher.getText().trim();

        //Kiểm tra nếu mã Voucher không rỗng
        if (!voucherCode.isEmpty()) {
            //Kết nối CSDL và thực hiện kiểm tra
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                String dbUrl = "jdbc:sqlserver://LAPTOP-1QVPM352\\MSSQLEXPRESS:1433;databaseName=CAFEACCOUNT;user=sa;password=sa2023";
                Connection con = DriverManager.getConnection(dbUrl);

                //truy vấn kiểm tra mã Voucher có trong CSDL hay không
                String query = "SELECT GIAM FROM VOUCHER WHERE MA = ?";
                try (PreparedStatement ps = con.prepareStatement(query)) {
                    ps.setString(1, voucherCode);
                    ResultSet rs = ps.executeQuery();

                    if (rs.next()) {
                        int voucherValue = rs.getInt("GIAM");
                        int currentTotal = Integer.parseInt(lblTong.getText().replaceAll("[^0-9]", ""));
                        int newTotal = currentTotal - voucherValue;

                        lblBillTienGiam.setText(rs.getInt("GIAM") + " VNĐ");
                        if (newTotal < 0) {
                            lblThanhToan.setText("0.0 VNĐ");
                        } else {
                            lblThanhToan.setText(newTotal + " VNĐ");
                        }
                        JOptionPane.showMessageDialog(this, "Áp dụng mã Voucher thành công! Giảm : " + rs.getString("GIAM") + " VNĐ");

                        con.close();
                    } else {
                        JOptionPane.showMessageDialog(this, "Mã voucher không hợp lệ. Vui lòng kiểm tra lại!");
                    }
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã Voucher!");
        }
    }

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
            java.util.logging.Logger.getLogger(Home_ver2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Home_ver2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Home_ver2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Home_ver2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Home_ver2().setVisible(true);
            }
        });
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {

        public ButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {

        protected JButton button;

        private String label;

        private boolean isPushed;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            if (isSelected) {
                button.setForeground(table.getSelectionForeground());
                button.setBackground(table.getSelectionBackground());
            } else {
                button.setForeground(table.getForeground());
                button.setBackground(table.getBackground());
            }

            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        public Object getCellEditorValue() {
            return label;
        }

        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }

    public class CustomTableModel extends AbstractTableModel {

        private final int columnCount;

        public CustomTableModel(int columnCount) {
            this.columnCount = columnCount;
        }

        @Override
        public int getRowCount() {
            // Thêm logic để trả về số hàng tương ứng
            return 0;
        }

        @Override
        public int getColumnCount() {
            // Chỉ trả về số cột bạn muốn hiển thị
            return columnCount;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            // Thêm logic để trả về giá trị tại mỗi ô tương ứng
            return null;
        }

        // Các phương thức khác của AbstractTableModel cũng cần được override tùy thuộc vào yêu cầu cụ thể của bạn
    }

    DefaultTableModel model = new DefaultTableModel(
            new Object[][]{},
            new String[]{
                "STT", "Tên", "Số lượng", "Đơn giá", "Tổng", "Tăng", "Giảm", "Xóa"
            }
    );
    
    DefaultTableModel modelBill = new DefaultTableModel(
            new Object[][]{},
            new String[]{
                "STT", "Tên", "Số lượng", "Đơn giá", "Tổng"
            }
    );


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog SanPham;
    private javax.swing.JDialog Timkiem;
    private javax.swing.JDialog billDetail;
    private javax.swing.JButton btnAddVoucher;
    private javax.swing.JButton btnBillDetail;
    private javax.swing.JButton btnInHoaDon;
    private javax.swing.JButton btnLuuHoaDon;
    private javax.swing.JButton btnNhap;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblBillThanhToan;
    private javax.swing.JLabel lblBillTienGiam;
    private javax.swing.JLabel lblBillTienKhachDua;
    private javax.swing.JLabel lblBillTienTraLai;
    private javax.swing.JLabel lblBillTong;
    private javax.swing.JLabel lblDayNow;
    private javax.swing.JLabel lblNhanVienThuNgan;
    private javax.swing.JLabel lblSoHoaDon;
    private javax.swing.JLabel lblThanhToan;
    private javax.swing.JLabel lblTienTraLai;
    private javax.swing.JLabel lblTong;
    private javax.swing.JPanel pnMain;
    private javax.swing.JPanel pnMainTK;
    private javax.swing.JPanel pnShow;
    private javax.swing.JTable tableBillDetail;
    private javax.swing.JTable tableDetail;
    private javax.swing.JTextField txtTienKhachDua;
    private javax.swing.JTextField txtVoucher;
    // End of variables declaration//GEN-END:variables
}
