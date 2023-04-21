/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package quanlythuvien;



import java.beans.Statement;
import java.sql.ResultSetMetaData;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author HuyMemee
 */
public class JFrameAdminKhoSach extends javax.swing.JFrame {
    
    DefaultTableModel model;
    DefaultTableModel model1;
    DefaultTableModel model2;
   
    /**
     * Creates new form JFrameAdminLogin
     */
    private static String username = "root";
    private static String password = "123456789";
    private static String data ="jdbc:mysql://localhost:3306/library";
    
    Connection sqlconn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    int q,i;
    public void search (String str){
        model = (DefaultTableModel) jTable1.getModel();
        TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(model);
        jTable1.setRowSorter(trs);
        trs.setRowFilter(RowFilter.regexFilter(str));
        GetDataTable();
    }
    public void search1(String str){
        model2 = (DefaultTableModel) jTable2.getModel();
        TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(model1);
        jTable2.setRowSorter(trs);
        trs.setRowFilter(RowFilter.regexFilter(str));
    }
    public void search2(String str){
        model2 = (DefaultTableModel) jTable3.getModel();
        TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(model2);
        jTable3.setRowSorter(trs);
        trs.setRowFilter(RowFilter.regexFilter(str));
    }
    public void Update_table_Book(){
        try{
            String sql="select * from books";
            pst = sqlconn.prepareStatement(sql);
            rs=pst.executeQuery();
            jTable1.setModel(DbUtils.resultSetToTableModel(rs));
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }
    public void Update_table_User(){
        try{
            String sql="select * from users";
            pst = sqlconn.prepareStatement(sql);
            rs=pst.executeQuery();
            jTable3.setModel(DbUtils.resultSetToTableModel(rs));
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }
    public void Update_table_Borrow(){
        try{
            String sql="select * from borrow";
            pst = sqlconn.prepareStatement(sql);
            rs=pst.executeQuery();
            jTable2.setModel(DbUtils.resultSetToTableModel(rs));
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }
    public void GetDataTable() {
        
        try
        {
            sqlconn =(Connection) DriverManager.getConnection(data,username,password);
            pst = sqlconn.prepareStatement("select * from books order by idBook");
            rs = pst.executeQuery();

            ResultSetMetaData stData = rs.getMetaData();
            q = stData.getColumnCount();

            DefaultTableModel RecordTable = (DefaultTableModel)jTable1.getModel();
            RecordTable.setRowCount(0);

            while(rs.next()){
                Vector columnData = new Vector();
                for (i = 1 ; i <=q ; i++)
                {
                    columnData.add(rs.getString("idBook"));
                    columnData.add(rs.getString("nameBook"));
                    columnData.add(rs.getString("author"));
                    columnData.add(rs.getString("typebook"));
                    columnData.add(rs.getString("shelf"));
                    columnData.add(rs.getString("quantity"));
                }
                  RecordTable.addRow(columnData);
            }   
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,"Lỗi không thể hiển thị");
            }
    }
    public void GetDataTable1() {
        
        try
        {
            sqlconn =(Connection) DriverManager.getConnection(data,username,password);
            pst = sqlconn.prepareStatement("select * from borrow ");
            rs = pst.executeQuery();

            ResultSetMetaData stData = rs.getMetaData();
            q = stData.getColumnCount();

            DefaultTableModel RecordTable = (DefaultTableModel)jTable2.getModel();
            RecordTable.setRowCount(0);

            while(rs.next()){
                Vector columnData = new Vector();
                for (i = 1 ; i <=q ; i++)
                {
                    columnData.add(rs.getString("idBorrow"));
                    columnData.add(rs.getString("username"));
                    columnData.add(rs.getString("idBook"));
                    columnData.add(rs.getString("dateBorrow"));
                    columnData.add(rs.getString("dateDue"));
                    columnData.add(rs.getString("dateReturn"));
                    columnData.add(rs.getString("trangthai"));
                }
                  RecordTable.addRow(columnData);
            }   
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,"Lỗi không thể hiển thị");
            }
    }
    public void GetDataTable2() {
        
        try
        {
            sqlconn =(Connection) DriverManager.getConnection(data,username,password);
            pst = sqlconn.prepareStatement("select * from users order by username");
            rs = pst.executeQuery();

            ResultSetMetaData stData = rs.getMetaData();
            q = stData.getColumnCount();

            DefaultTableModel RecordTable = (DefaultTableModel)jTable3.getModel();
            RecordTable.setRowCount(0);

            while(rs.next()){
                Vector columnData = new Vector();
                for (i = 1 ; i <=q ; i++)
                {
                    columnData.add(rs.getString("username"));
                    columnData.add(rs.getString("password"));
                    columnData.add(rs.getString("name"));
                    columnData.add(rs.getString("position"));
                    columnData.add(rs.getString("phone"));
                }
                  RecordTable.addRow(columnData);
            }   
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null,"Lỗi không thể hiển thị");
            }
    }
  
    public JFrameAdminKhoSach() throws SQLException {
        initComponents();
        GetDataTable();
        GetDataTable1();
        GetDataTable2();
        
        model = (DefaultTableModel) jTable1.getModel();
        model1 = (DefaultTableModel) jTable2.getModel();
        model2 = (DefaultTableModel) jTable3.getModel();
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        label1 = new java.awt.Label();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        label2 = new java.awt.Label();
        label3 = new java.awt.Label();
        label4 = new java.awt.Label();
        label5 = new java.awt.Label();
        Them = new javax.swing.JButton();
        Xoa = new javax.swing.JButton();
        label18 = new java.awt.Label();
        label19 = new java.awt.Label();
        CapNhat = new javax.swing.JButton();
        tensach = new javax.swing.JTextField();
        ID = new javax.swing.JTextField();
        tacgia = new javax.swing.JTextField();
        theloai = new javax.swing.JTextField();
        khuvuc = new javax.swing.JTextField();
        soluong = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        Timkiem = new javax.swing.JTextField();
        label28 = new java.awt.Label();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        label6 = new java.awt.Label();
        label7 = new java.awt.Label();
        label8 = new java.awt.Label();
        label9 = new java.awt.Label();
        label10 = new java.awt.Label();
        label11 = new java.awt.Label();
        Xoa1 = new javax.swing.JButton();
        Capnhat1 = new javax.swing.JButton();
        label21 = new java.awt.Label();
        Them1 = new javax.swing.JButton();
        label20 = new java.awt.Label();
        Timkiem1 = new javax.swing.JTextField();
        maMuon = new javax.swing.JTextField();
        ID1 = new javax.swing.JTextField();
        uname1 = new javax.swing.JTextField();
        muon1 = new javax.swing.JTextField();
        hantra1 = new javax.swing.JTextField();
        tra1 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        label26 = new java.awt.Label();
        trangthai1 = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        them2 = new javax.swing.JButton();
        xoa2 = new javax.swing.JButton();
        capnhat2 = new javax.swing.JButton();
        label12 = new java.awt.Label();
        label13 = new java.awt.Label();
        label14 = new java.awt.Label();
        label15 = new java.awt.Label();
        label16 = new java.awt.Label();
        label17 = new java.awt.Label();
        Timkiem2 = new javax.swing.JTextField();
        uname2 = new javax.swing.JTextField();
        password2 = new javax.swing.JTextField();
        hoten2 = new javax.swing.JTextField();
        vaitro2 = new javax.swing.JTextField();
        sdt2 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        label27 = new java.awt.Label();
        jPanel4 = new javax.swing.JPanel();
        tongSach = new java.awt.TextField();
        tongDoc = new java.awt.TextField();
        tongMuon = new java.awt.TextField();
        label22 = new java.awt.Label();
        label23 = new java.awt.Label();
        label24 = new java.awt.Label();
        label25 = new java.awt.Label();
        button1 = new java.awt.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setPreferredSize(new java.awt.Dimension(1018, 400));

        label1.setAlignment(java.awt.Label.CENTER);
        label1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        label1.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        label1.setName(""); // NOI18N
        label1.setText("KHO SÁCH");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Tên", "Tác giả", "Thể loại", "Khu vực", "Số lượng"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        label2.setText("ID :");

        label3.setText("Tên sách:");

        label4.setText("Tác giả :");

        label5.setText("Số lượng :");

        Them.setText("Thêm");
        Them.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ThemActionPerformed(evt);
            }
        });

        Xoa.setText("Xóa");
        Xoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                XoaActionPerformed(evt);
            }
        });

        label18.setText("Thể loại");

        label19.setText("Khu vực");

        CapNhat.setText("Cập nhật");
        CapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CapNhatActionPerformed(evt);
            }
        });

        jButton1.setText("Đăng xuất");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        Timkiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TimkiemActionPerformed(evt);
            }
        });

        label28.setText("Tìm kiếm:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(label1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(label19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(13, 13, 13)))
                            .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(Them, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(36, 36, 36)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tensach)
                            .addComponent(ID)
                            .addComponent(tacgia)
                            .addComponent(theloai)
                            .addComponent(khuvuc)
                            .addComponent(soluong)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(CapNhat)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 75, Short.MAX_VALUE)
                                .addComponent(Xoa, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(61, 61, 61)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 865, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(label28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Timkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 575, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(224, 224, 224)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Timkiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tensach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tacgia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(theloai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(khuvuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(soluong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 90, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Xoa)
                            .addComponent(CapNhat)
                            .addComponent(Them)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addGap(18, 18, 18))
        );

        jTabbedPane1.addTab("Kho sách", jPanel1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã Mượn", "Username", "ID sách", "Ngày mượn", "Hạn trả", "Ngày trả", "Trạng thái"
            }
        ));
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        label6.setAlignment(java.awt.Label.CENTER);
        label6.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        label6.setText("DANH SÁCH MƯỢN TRẢ");

        label7.setText("ID Sách");

        label8.setText("Username");

        label9.setText("Ngày mượn");

        label10.setText("Hạn trả");

        label11.setText("Ngày trả");

        Xoa1.setText("Xóa");
        Xoa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Xoa1ActionPerformed(evt);
            }
        });

        Capnhat1.setText("Cập nhật");
        Capnhat1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Capnhat1ActionPerformed(evt);
            }
        });

        label21.setText("Tìm kiếm:");

        Them1.setText("Thêm");
        Them1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Them1ActionPerformed(evt);
            }
        });

        label20.setText("Mã Mượn");

        Timkiem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Timkiem1ActionPerformed(evt);
            }
        });
        Timkiem1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                Timkiem1KeyReleased(evt);
            }
        });

        jButton2.setText("Đăng xuất");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        label26.setText("Trạng thái");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(label6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(76, 76, 76)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ID1)
                            .addComponent(uname1)
                            .addComponent(muon1)
                            .addComponent(hantra1)
                            .addComponent(tra1)
                            .addComponent(maMuon)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(Capnhat1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(Xoa1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(trangthai1))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 914, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Them1)
                            .addComponent(label20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(label21, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Timkiem1, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(318, 318, 318))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(18, 18, Short.MAX_VALUE)
                                .addComponent(jButton2)
                                .addGap(15, 15, 15))))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(label6, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(label21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Timkiem1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(maMuon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(ID1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(label20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(label8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(uname1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(muon1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(hantra1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tra1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(trangthai1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Capnhat1)
                            .addComponent(Xoa1)
                            .addComponent(Them1)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Danh sách mượn trả", jPanel2);

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Username", "Password", "Họ tên", "Vai trò", "SĐT"
            }
        ));
        jTable3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable3MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable3);

        them2.setText("Thêm");
        them2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                them2ActionPerformed(evt);
            }
        });

        xoa2.setText("Xóa");
        xoa2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xoa2ActionPerformed(evt);
            }
        });

        capnhat2.setText("Cập nhật");
        capnhat2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                capnhat2ActionPerformed(evt);
            }
        });

        label12.setAlignment(java.awt.Label.CENTER);
        label12.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        label12.setText("QUẢN LÝ NGƯỜI DÙNG");

        label13.setText("Username : ");

        label14.setText("Password : ");

        label15.setText("Họ tên :");

        label16.setText("Vai trò :");

        label17.setText("SĐT :");

        Timkiem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Timkiem2ActionPerformed(evt);
            }
        });

        jButton3.setText("Đăng xuất");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        label27.setText("Tìm kiếm:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(label12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(label13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(label14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(label16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(them2, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(47, 47, 47)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(vaitro2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                    .addComponent(hoten2, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(password2, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(uname2)
                                    .addComponent(sdt2)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addComponent(capnhat2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(xoa2, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(label27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(Timkiem2, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(41, 41, 41)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 915, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(19, 19, 19))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(label12, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(label27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Timkiem2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                                .addComponent(uname2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(48, 48, 48)
                                .addComponent(label13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(label14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(password2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(label15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(label16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(vaitro2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(30, 30, 30)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(label17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(sdt2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(hoten2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(xoa2)
                            .addComponent(capnhat2)
                            .addComponent(them2)))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jButton3)
                .addGap(16, 16, 16))
        );

        jTabbedPane1.addTab("Quản lý người dùng", jPanel3);

        tongSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tongSachActionPerformed(evt);
            }
        });

        tongDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tongDocActionPerformed(evt);
            }
        });

        label22.setText("Tổng số sách :");

        label23.setText("Tổng số lần mượn :");

        label24.setText("Tổng số người dùng :");

        label25.setAlignment(java.awt.Label.CENTER);
        label25.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        label25.setText("THỐNG KÊ");

        button1.setLabel("Kiểm tra");
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(427, 427, 427)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label25, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(label23, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(label22, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(219, 219, 219)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(tongDoc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(tongSach, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addComponent(label24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(tongMuon, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(615, 615, 615)
                        .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(515, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(label25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tongSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(70, 70, 70)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tongDoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(70, 70, 70)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tongMuon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 74, Short.MAX_VALUE)
                .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70))
        );

        jTabbedPane1.addTab("Thống Kê", jPanel4);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1350, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        int sosach = jTable1.getRowCount();
        int sodocgia = jTable2.getRowCount();
        int sopm = jTable3.getRowCount();
        tongSach.setText  (""+sosach);
        tongDoc.setText  (""+sodocgia);
        tongMuon.setText  (""+sopm);
    }//GEN-LAST:event_button1ActionPerformed

    private void tongDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tongDocActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tongDocActionPerformed

    private void tongSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tongSachActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tongSachActionPerformed

    private void Timkiem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Timkiem2ActionPerformed
        String searchString = Timkiem2.getText();
        search2(searchString);
    }//GEN-LAST:event_Timkiem2ActionPerformed

    private void capnhat2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_capnhat2ActionPerformed
        String uname = uname2.getText();
        String pass = password2.getText();
        String hten = hoten2.getText();
        String vtro = vaitro2.getText();
        String sdt = sdt2.getText();
        if(uname.equals("")| pass.equals("")||hten.equals("")||vtro.equals("")||sdt.equals("")){
            JOptionPane.showConfirmDialog(rootPane, "Vui lòng nhập đầy đủ thông tin!", "Error", 2);
        }
        else{
            try{
                String sql= "call updateUser(?,?,?,?,?)";
                sqlconn =(Connection) DriverManager.getConnection(data,username,password);
                pst = sqlconn.prepareStatement(sql);

                pst.setString(1,uname2.getText());
                pst.setString(2,password2.getText());
                pst.setString(3,hoten2.getText());
                pst.setString(4,vaitro2.getText());
                pst.setString(5,sdt2.getText());
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Cập nhật mới thành công");
                GetDataTable();
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null,"Lỗi");
            }
        }
        Update_table_User();
    }//GEN-LAST:event_capnhat2ActionPerformed

    private void xoa2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xoa2ActionPerformed
        String uname = uname2.getText();
        String pass = password2.getText();
        String hten = hoten2.getText();
        String vtro = vaitro2.getText();
        String sdt = sdt2.getText();
        if(uname.equals("")| pass.equals("")||hten.equals("")||vtro.equals("")||sdt.equals("")){
            JOptionPane.showConfirmDialog(rootPane, "Vui lòng nhập đầy đủ thông tin!", "Error", 2);
        }
        else{
            try{
                String sql= "CALL deleteUser(?)";
                sqlconn =(Connection) DriverManager.getConnection(data,username,password);
                pst = sqlconn.prepareStatement(sql);

                pst.setString(1,uname2.getText());

                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Xóa mới thành công");
                GetDataTable();
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null,"Lỗi");
            }
        }
        Update_table_User();
    }//GEN-LAST:event_xoa2ActionPerformed

    private void them2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_them2ActionPerformed
        String uname = uname2.getText();
        String pass = password2.getText();
        String hten = hoten2.getText();
        String vtro = vaitro2.getText();
        String sdt = sdt2.getText();
        if(uname.equals("")| pass.equals("")||hten.equals("")||vtro.equals("")||sdt.equals("")){
            JOptionPane.showConfirmDialog(rootPane, "Vui lòng nhập đầy đủ thông tin!", "Error", 2);
        }
        else{
            try{
                String sql= "CALL insertUser(?,?,?,?,?)";
                sqlconn =(Connection) DriverManager.getConnection(data,username,password);
                pst = sqlconn.prepareStatement(sql);

                pst.setString(1,uname2.getText());
                pst.setString(2,password2.getText());
                pst.setString(3,hoten2.getText());
                pst.setString(4,vaitro2.getText());
                pst.setString(5,sdt2.getText());
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Thêm mới thành công");
                GetDataTable();
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null,"Lỗi");
            }
        }
        Update_table_User();

    }//GEN-LAST:event_them2ActionPerformed

    private void jTable3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable3MouseClicked
        DefaultTableModel RecordTable = (DefaultTableModel)jTable3.getModel();
        int SelectedRows = jTable3.getSelectedRow();
        uname2.setText(RecordTable.getValueAt(SelectedRows, 0).toString());
        password2.setText(RecordTable.getValueAt(SelectedRows, 1).toString());
        hoten2.setText(RecordTable.getValueAt(SelectedRows, 2).toString());
        vaitro2.setText(RecordTable.getValueAt(SelectedRows, 3).toString());
        sdt2.setText(RecordTable.getValueAt(SelectedRows, 4).toString());
    }//GEN-LAST:event_jTable3MouseClicked

    private void CapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CapNhatActionPerformed
        String id = ID.getText();
        String tsach = tensach.getText();
        String tgia = tacgia.getText();
        String tloai = theloai.getText();
        String kvuc = khuvuc.getText();
        String sluong = soluong.getText();
        if(id.equals("")||tsach.equals("")||tgia.equals("")||tloai.equals("")||kvuc.equals("")||sluong.equals("")){
            JOptionPane.showConfirmDialog(rootPane, "Vui lòng nhập đầy đủ thông tin!", "Error", 2);
        }
        else{
            try{
                String sql="CALL updateBook(?, ?, ?, ?, ?, ?)";
                sqlconn =(Connection) DriverManager.getConnection(data,username,password);

                pst = sqlconn.prepareStatement(sql);

                pst.setString(1,ID.getText());
                pst.setString(2,tensach.getText());
                pst.setString(3,tacgia.getText());
                pst.setString(4,theloai.getText());
                pst.setString(5,khuvuc.getText());
                pst.setString(6,soluong.getText());

                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Cập nhật mới thành công");
                GetDataTable();

            }catch(Exception ex){
                JOptionPane.showMessageDialog(null,"Lỗi");
            }
            Update_table_Book();
        }

    }//GEN-LAST:event_CapNhatActionPerformed

    private void XoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_XoaActionPerformed
        String id = ID.getText();
        String tsach = tensach.getText();
        String tgia = tacgia.getText();
        String tloai = theloai.getText();
        String kvuc = khuvuc.getText();
        String sluong = soluong.getText();
        if(id.equals("")||tsach.equals("")||tgia.equals("")||tloai.equals("")||kvuc.equals("")||sluong.equals("")){
            JOptionPane.showConfirmDialog(rootPane, "Vui lòng nhập đầy đủ thông tin!", "Error", 2);
        }
        else{
            try{
                String sql= "call deleteBook(?)";
                sqlconn =(Connection) DriverManager.getConnection(data,username,password);
                pst = sqlconn.prepareStatement(sql);

                pst.setString(1,ID.getText());

                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Xóa thành công");
                GetDataTable();
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null,"Lỗi");
            }
        }
        Update_table_Book();
    }//GEN-LAST:event_XoaActionPerformed

    private void ThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ThemActionPerformed
        String id = ID.getText();
        String tsach = tensach.getText();
        String tgia = tacgia.getText();
        String tloai = theloai.getText();
        String kvuc = khuvuc.getText();
        String sluong = soluong.getText();
        if(id.equals("")||tsach.equals("")||tgia.equals("")||tloai.equals("")||kvuc.equals("")||sluong.equals("")){
            JOptionPane.showConfirmDialog(rootPane, "Vui lòng nhập đầy đủ thông tin!", "Error", 2);
        }
        else{
            try{
                String sql= "call insertBook(?,?,?,?,?,?)";
                sqlconn =(Connection) DriverManager.getConnection(data,username,password);
                pst = sqlconn.prepareStatement(sql);

                pst.setString(1,ID.getText());
                pst.setString(2,tensach.getText());
                pst.setString(3,tacgia.getText());
                pst.setString(4,theloai.getText());
                pst.setString(5,khuvuc.getText());
                pst.setString(6,soluong.getText());

                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Thêm mới thành công");
                GetDataTable();
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null,"Lỗi");
            }
        }

    }//GEN-LAST:event_ThemActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        DefaultTableModel RecordTable = (DefaultTableModel)jTable1.getModel();
        int SelectedRows = jTable1.getSelectedRow();
        ID.setText(RecordTable.getValueAt(SelectedRows, 0).toString());
        tensach.setText(RecordTable.getValueAt(SelectedRows, 1).toString());
        tacgia.setText(RecordTable.getValueAt(SelectedRows, 2).toString());
        theloai.setText(RecordTable.getValueAt(SelectedRows, 3).toString());
        khuvuc.setText(RecordTable.getValueAt(SelectedRows, 4).toString());
        soluong.setText(RecordTable.getValueAt(SelectedRows, 5).toString());
    }//GEN-LAST:event_jTable1MouseClicked

    private void Timkiem1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Timkiem1KeyReleased

    }//GEN-LAST:event_Timkiem1KeyReleased

    private void Timkiem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Timkiem1ActionPerformed
        String searchString = Timkiem1.getText();
        search1(searchString);
    }//GEN-LAST:event_Timkiem1ActionPerformed

    private void Them1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Them1ActionPerformed
        String mMuon = maMuon.getText();
        String name = uname1.getText();
        String id = ID1.getText();
        String muon = muon1.getText();
        String htra = hantra1.getText();
        String tra = tra1.getText();
        String tt = trangthai1.getText();
        if(mMuon.equals("")||id.equals("")||name.equals("")||muon.equals("")||htra.equals("")||tra.equals("")||tt.equals("")){
            JOptionPane.showConfirmDialog(rootPane, "Vui lòng nhập đầy đủ thông tin!", "Error", 2);
        }
        else{
            try{
                String sql= "call insertBorrow(?,?,?,?,?,?,?)";
                sqlconn =(Connection) DriverManager.getConnection(data,username,password);
                pst = sqlconn.prepareStatement(sql);

                pst.setString(1,maMuon.getText());
                pst.setString(2,ID1.getText());
                pst.setString(3,uname1.getText());
                pst.setString(4,muon1.getText());
                pst.setString(5,hantra1.getText());
                pst.setString(6,tra1.getText());
                pst.setString(7,trangthai1.getText());
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Thêm mới thành công");
                GetDataTable();
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null,"Lỗi");
            }
        }
        Update_table_Borrow();
    }//GEN-LAST:event_Them1ActionPerformed

    private void Capnhat1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Capnhat1ActionPerformed
        String mmBw = maMuon.getText();
        String unBw= uname1.getText();
        String idBw = ID1.getText();
        String nmBw = muon1.getText();
        String tBw = hantra1.getText();
        String ntBw = tra1.getText();
        String tt = trangthai1.getText();
        if(mmBw.equals("")||idBw.equals("")||unBw.equals("")||nmBw.equals("")||tBw.equals("")||ntBw.equals("")||tt.equals("")){
            JOptionPane.showConfirmDialog(rootPane, "Vui lòng nhập đầy đủ thông tin!", "Error", 2);
        }
        else{
            try{
                String sql= "call updateBorrow(?,?,?,?,?,?,?)";
                sqlconn =(Connection) DriverManager.getConnection(data,username,password);

                pst = sqlconn.prepareStatement(sql);

                pst.setString(1,maMuon.getText());
                pst.setString(2,ID1.getText());
                pst.setString(3,uname1.getText());
                pst.setString(4,muon1.getText());
                pst.setString(5,hantra1.getText());
                pst.setString(6,tra1.getText());
                pst.setString(7,trangthai1.getText());

                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Cập nhật mới thành công");
                GetDataTable();

            }catch(Exception ex){
                JOptionPane.showMessageDialog(null,"Lỗi");
            }
            Update_table_Borrow();
        }
    }//GEN-LAST:event_Capnhat1ActionPerformed

    private void Xoa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Xoa1ActionPerformed
        String mMuon = maMuon.getText();
        String id = ID1.getText();
        String name = uname1.getText();
        String muon = muon1.getText();
        String htra = hantra1.getText();
        String tra = tra1.getText();
        String tt = trangthai1.getText();
        if(mMuon.equals("")||id.equals("")||name.equals("")||muon.equals("")||htra.equals("")||tra.equals("")|| tt.equals("")){
            JOptionPane.showConfirmDialog(rootPane, "Vui lòng nhập đầy đủ thông tin!", "Error", 2);
        }
        else{
            try{
                String sql= "call deleteBorrow(?)";
                sqlconn =(Connection) DriverManager.getConnection(data,username,password);
                pst = sqlconn.prepareStatement(sql);

                pst.setString(1,maMuon.getText());

                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Xóa thành công");
                GetDataTable();
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null,"Lỗi");
            }
        }
        Update_table_Borrow();
    }//GEN-LAST:event_Xoa1ActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        DefaultTableModel RecordTable = (DefaultTableModel)jTable2.getModel();
        int SelectedRows = jTable2.getSelectedRow();
        maMuon.setText(RecordTable.getValueAt(SelectedRows, 0).toString());
        ID1.setText(RecordTable.getValueAt(SelectedRows, 1).toString());
        uname1.setText(RecordTable.getValueAt(SelectedRows, 2).toString());
        muon1.setText(RecordTable.getValueAt(SelectedRows, 3).toString());
        hantra1.setText(RecordTable.getValueAt(SelectedRows, 4).toString());
        tra1.setText(RecordTable.getValueAt(SelectedRows, 5).toString());
        trangthai1.setText(RecordTable.getValueAt(SelectedRows, 6).toString());
    }//GEN-LAST:event_jTable2MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
                    // TODO add your handling code here:
        new JFameLogin().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        new JFameLogin().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        new JFameLogin().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void TimkiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TimkiemActionPerformed
        String searchString = Timkiem.getText();
        search(searchString);
    }//GEN-LAST:event_TimkiemActionPerformed
                 
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
            java.util.logging.Logger.getLogger(JFrameAdminKhoSach.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFrameAdminKhoSach.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFrameAdminKhoSach.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrameAdminKhoSach.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new JFrameAdminKhoSach().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(JFrameAdminKhoSach.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CapNhat;
    private javax.swing.JButton Capnhat1;
    private javax.swing.JTextField ID;
    private javax.swing.JTextField ID1;
    private javax.swing.JButton Them;
    private javax.swing.JButton Them1;
    private javax.swing.JTextField Timkiem;
    private javax.swing.JTextField Timkiem1;
    private javax.swing.JTextField Timkiem2;
    private javax.swing.JButton Xoa;
    private javax.swing.JButton Xoa1;
    private java.awt.Button button1;
    private javax.swing.JButton capnhat2;
    private javax.swing.JTextField hantra1;
    private javax.swing.JTextField hoten2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextField khuvuc;
    private java.awt.Label label1;
    private java.awt.Label label10;
    private java.awt.Label label11;
    private java.awt.Label label12;
    private java.awt.Label label13;
    private java.awt.Label label14;
    private java.awt.Label label15;
    private java.awt.Label label16;
    private java.awt.Label label17;
    private java.awt.Label label18;
    private java.awt.Label label19;
    private java.awt.Label label2;
    private java.awt.Label label20;
    private java.awt.Label label21;
    private java.awt.Label label22;
    private java.awt.Label label23;
    private java.awt.Label label24;
    private java.awt.Label label25;
    private java.awt.Label label26;
    private java.awt.Label label27;
    private java.awt.Label label28;
    private java.awt.Label label3;
    private java.awt.Label label4;
    private java.awt.Label label5;
    private java.awt.Label label6;
    private java.awt.Label label7;
    private java.awt.Label label8;
    private java.awt.Label label9;
    private javax.swing.JTextField maMuon;
    private javax.swing.JTextField muon1;
    private javax.swing.JTextField password2;
    private javax.swing.JTextField sdt2;
    private javax.swing.JTextField soluong;
    private javax.swing.JTextField tacgia;
    private javax.swing.JTextField tensach;
    private javax.swing.JTextField theloai;
    private javax.swing.JButton them2;
    private java.awt.TextField tongDoc;
    private java.awt.TextField tongMuon;
    private java.awt.TextField tongSach;
    private javax.swing.JTextField tra1;
    private javax.swing.JTextField trangthai1;
    private javax.swing.JTextField uname1;
    private javax.swing.JTextField uname2;
    private javax.swing.JTextField vaitro2;
    private javax.swing.JButton xoa2;
    // End of variables declaration//GEN-END:variables
}
