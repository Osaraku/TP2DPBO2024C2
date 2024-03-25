/*
Saya Muhamad Tio Ariyanto [2201718] mengerjakan soal Tugas Praktikum 2
dalam mata kuliah DPBO untuk keberkahanNya saya tidak melakukan kecurangan
seperti yang telah dispesifikasikan. Aamiin
*/

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Menu extends JFrame{
    public static void main(String[] args) {
        // buat object window
        Menu window = new Menu();

        // atur ukuran window
        window.setSize(600, 720);

        // letakkan window di tengah layar
        window.setLocationRelativeTo(null);

        // isi window
        window.setContentPane(window.mainPanel);

        // ubah warna background
        window.getContentPane().setBackground(Color.white);

        // tampilkan window
        window.setVisible(true);

        // agar program ikut berhenti saat window diclose
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    // index baris yang diklik
    private int selectedIndex = -1;
    // list untuk menampung semua mahasiswa
    private ArrayList<Mahasiswa> listMahasiswa;
    private Database database;

    private JPanel mainPanel;
    private JTextField nimField;
    private JTextField namaField;
    private JTable mahasiswaTable;
    private JButton addUpdateButton;
    private JButton cancelButton;
    private JComboBox jenisKelaminComboBox;
    private JButton deleteButton;
    private JLabel titleLabel;
    private JLabel nimLabel;
    private JLabel namaLabel;
    private JLabel jenisKelaminLabel;
    private JComboBox jalurMasukComboBox;
    private JRadioButton aktifRadioButton;
    private JRadioButton cutiRadioButton;

    // constructor
    public Menu() {
        // inisialisasi listMahasiswa
        listMahasiswa = new ArrayList<>();

        // buat objek database
        database = new Database();

        // isi tabel mahasiswa
        mahasiswaTable.setModel(setTable());

        // ubah styling title
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f));

        // atur isi combo box
        String[] JenisKelaminData = {"", "Laki-laki", "Perempuan"};
        jenisKelaminComboBox.setModel(new DefaultComboBoxModel(JenisKelaminData));

        String[] jalurMasukData = {"", "SNMPTN", "SBMPTN", "Mandiri"};
        jalurMasukComboBox.setModel(new DefaultComboBoxModel(jalurMasukData));

        // menambahkan grup radio button supaya hanya bisa pilih salah satu
        ButtonGroup statusGroup = new ButtonGroup();
        statusGroup.add(aktifRadioButton);
        statusGroup.add(cutiRadioButton);

        // sembunyikan button delete
        deleteButton.setVisible(false);


        // saat tombol add/update ditekan
        addUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex == -1) {
                    insertData();
                } else {
                    updateData();
                }
            }
        });
        // saat tombol delete ditekan
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex >= 0) {
                    deleteData();
                }
            }
        });
        // saat tombol cancel ditekan
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });
        // saat salah satu baris tabel ditekan
        mahasiswaTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // ubah selectedIndex menjadi baris tabel yang diklik
                selectedIndex = mahasiswaTable.getSelectedRow();

                // simpan value textfield dan combo box
                String selectedNim = mahasiswaTable.getModel().getValueAt(selectedIndex, 1).toString();
                String selectedNama = mahasiswaTable.getModel().getValueAt(selectedIndex, 2).toString();
                String selectedJenisKelamin = mahasiswaTable.getModel().getValueAt(selectedIndex, 3).toString();
                String selectedJalurMasuk = mahasiswaTable.getModel().getValueAt(selectedIndex, 4).toString();
                String selectedStatus = mahasiswaTable.getModel().getValueAt(selectedIndex, 5).toString();

                // ubah isi textfield dan combo box
                nimField.setText(selectedNim);
                namaField.setText(selectedNama);
                jenisKelaminComboBox.setSelectedItem(selectedJenisKelamin);
                jalurMasukComboBox.setSelectedItem(selectedJalurMasuk);

                // cek radio button sesuai status
                if (selectedStatus.equals("Aktif")) {
                    aktifRadioButton.setSelected(true);
                    cutiRadioButton.setSelected(false);
                } else if (selectedStatus.equals("Cuti")) {
                    aktifRadioButton.setSelected(false);
                    cutiRadioButton.setSelected(true);
                }

                // ubah button "Add" menjadi "Update"
                addUpdateButton.setText("Update");

                // tampilkan button delete
                deleteButton.setVisible(true);
            }
        });
    }

    public final DefaultTableModel setTable() {
        // tentukan kolom tabel
        Object[] column = {"No", "NIM", "Nama", "Jenis Kelamin", "Jalur Masuk", "Status"};

        // Buat objek tabel dengan kolom yang sudah dibuat
        DefaultTableModel temp = new DefaultTableModel(null, column);

        try{
            ResultSet resultSet = database.selectQuery("SELECT * FROM mahasiswa");

            int i = 0;
            while (resultSet.next()){
                Object[] row = new Object[6];
                row[0] = i + 1;
                row[1] = resultSet.getString("nim");
                row[2] = resultSet.getString("nama");
                row[3] = resultSet.getString("jenis_kelamin");
                row[4] = resultSet.getString("jalur_masuk");
                row[5] = resultSet.getString("status");

                temp.addRow(row);
                i++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return temp;
    }

    public void insertData() {
        // ambil value dari textfield dan combobox
        String nim = nimField.getText();
        String nama = namaField.getText();
        String jenisKelamin = jenisKelaminComboBox.getSelectedItem().toString();
        String jalurMasuk = jalurMasukComboBox.getSelectedItem().toString();

        // ambil value dari radio button
        String status = "";
        if(aktifRadioButton.isSelected()){
            status = aktifRadioButton.getText();
        } else if (cutiRadioButton.isSelected()) {
            status = cutiRadioButton.getText();
        }

        // pengecekan apakah form terisi semua
        if(nim.isEmpty())
        {
            JOptionPane.showMessageDialog(null,  "NIM belum diisi!", "Data tidak boleh kosong!", JOptionPane.ERROR_MESSAGE);
            return; // operasi dihentikan
        } else if(nama.isEmpty()){
            JOptionPane.showMessageDialog(null, "Nama belum diisi!", "Data tidak boleh kosong!", JOptionPane.ERROR_MESSAGE);
            return; // operasi dihentikan
        } else if(jenisKelamin.isEmpty()){
            JOptionPane.showMessageDialog(null, "Jenis kelamin belum dipilih!", "Data tidak boleh kosong!", JOptionPane.ERROR_MESSAGE);
            return; // operasi dihentikan
        } else if(jalurMasuk.isEmpty()){
            JOptionPane.showMessageDialog(null, "Jalur masuk belum dipilih!", "Data tidak boleh kosong!", JOptionPane.ERROR_MESSAGE);
            return; // operasi dihentikan
        } else if(status.isEmpty()){
            JOptionPane.showMessageDialog(null, "Status masuk belum dipilih!", "Data tidak boleh kosong!", JOptionPane.ERROR_MESSAGE);
            return; // operasi dihentikan
        } else {
            // pengecekan apakah NIM sudah ada
            try {
                ResultSet resultSet = database.selectQuery("SELECT nim FROM mahasiswa WHERE nim = '" + nim + "'");
                if (resultSet.next()) {
                    JOptionPane.showMessageDialog(null, "Sudah ada Mahasiswa yang menggunakan NIM tersebut!", "NIM tidak boleh sama!", JOptionPane.ERROR_MESSAGE);

                    // hapus nilai nim saat ini
                    nimField.setText("");

                    return; // operasi dihentikan
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        // tambahkan data ke dalam database
        String sql = "INSERT INTO mahasiswa VALUES (null, '" + nim + "', '" + nama + "', '" + jenisKelamin + "', '" + jalurMasuk + "', '" + status + "');";
        database.insertUpdateDeleteQuery(sql);

        // update tabel
        mahasiswaTable.setModel(setTable());

        // bersihkan form
        clearForm();

        // feedback
        System.out.println("Insert berhasil!");
        JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan");
    }

    public void updateData() {
        // ambil data dari form
        String nim = nimField.getText();
        String nama = namaField.getText();
        String jenisKelamin = jenisKelaminComboBox.getSelectedItem().toString();
        String jalurMasuk = jalurMasukComboBox.getSelectedItem().toString();
        String status = "";

        if(aktifRadioButton.isSelected()){
            status = aktifRadioButton.getText();
        } else if (cutiRadioButton.isSelected()) {
            status = cutiRadioButton.getText();
        }

        // pengecekan apakah form terisi semua
        if(nim.isEmpty())
        {
            JOptionPane.showMessageDialog(null,  "NIM belum diisi!", "Data tidak boleh kosong!", JOptionPane.ERROR_MESSAGE);
            return; // operasi dihentikan
        } else if(nama.isEmpty()){
            JOptionPane.showMessageDialog(null, "Nama belum diisi!", "Data tidak boleh kosong!", JOptionPane.ERROR_MESSAGE);
            return; // operasi dihentikan
        } else if(jenisKelamin.isEmpty()){
            JOptionPane.showMessageDialog(null, "Jenis kelamin belum dipilih!", "Data tidak boleh kosong!", JOptionPane.ERROR_MESSAGE);
            return; // operasi dihentikan
        } else if(jalurMasuk.isEmpty()){
            JOptionPane.showMessageDialog(null, "Jalur masuk belum dipilih!", "Data tidak boleh kosong!", JOptionPane.ERROR_MESSAGE);
            return; // operasi dihentikan
        }

        // tambahkan data ke dalam database
        String sql = "UPDATE mahasiswa SET nama='" + nama + "', jenis_kelamin='" + jenisKelamin + "', jalur_masuk='" + jalurMasuk + "', status='" + status + "' WHERE nim='" + nim + "'";
        database.insertUpdateDeleteQuery(sql);

        // update tabel
        mahasiswaTable.setModel(setTable());

        // bersihkan form
        clearForm();

        // feedback
        System.out.println("Update Berhasil!");
        JOptionPane.showMessageDialog(null, "Data berhasil diubah!");
    }

    public void deleteData() {
        // menampilkan prompt konfirmasi ketika data dihapus

        String nama = namaField.getText();

        ImageIcon icon = new ImageIcon("src/images/bin.png");

        JPanel panel = new JPanel();
        panel.setSize(new Dimension(200, 64));
        panel.setLayout(null);

        JLabel label1 = new JLabel("Data " + nama);
        label1.setVerticalAlignment(SwingConstants.BOTTOM);
        label1.setBounds(0, 0, 200, 32);
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label1);

        JLabel label2 = new JLabel("akan dihapus, yakin hapus?");
        label2.setVerticalAlignment(SwingConstants.TOP);
        label2.setHorizontalAlignment(SwingConstants.CENTER);
        label2.setBounds(0, 32, 200, 32);
        panel.add(label2);

        UIManager.put("OptionPane.minimumSize", new Dimension(300, 120));
        int response = JOptionPane.showConfirmDialog(null, panel, "Konfirmasi Penghapusan Data!",
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, icon);

        if (response == JOptionPane.YES_OPTION) {
            // hapus data dari database
            String nim = nimField.getText();
            String sql = "DELETE FROM mahasiswa WHERE nim = '"+ nim +"';";
            database.insertUpdateDeleteQuery(sql);

            // update tabel
            mahasiswaTable.setModel(setTable());

            // bersihkan form
            clearForm();

            // feedback
            System.out.println("Delete berhasil!");
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
        }
    }

    public void clearForm() {
        // kosongkan semua texfield dan combo box
        nimField.setText("");
        namaField.setText("");
        jenisKelaminComboBox.setSelectedItem("");
        jalurMasukComboBox.setSelectedItem("");
        aktifRadioButton.setSelected(false);
        cutiRadioButton.setSelected(false);

        // ubah button "Update" menjadi "Add"
        addUpdateButton.setText("Add");
        // sembunyikan button delete
        deleteButton.setVisible(false);
        // ubah selectedIndex menjadi -1 (tidak ada baris yang dipilih)
        selectedIndex = -1;
    }

    private void populateList() {
        listMahasiswa.add(new Mahasiswa("2203999", "Amelia Zalfa Julianti", "Perempuan", "SNMPTN", "Aktif"));
        listMahasiswa.add(new Mahasiswa("2202292", "Muhammad Iqbal Fadhilah", "Laki-laki", "SBMPTN", "Aktif"));
        listMahasiswa.add(new Mahasiswa("2202346", "Muhammad Rifky Afandi", "Laki-laki", "Mandiri", "Cuti"));
        listMahasiswa.add(new Mahasiswa("2210239", "Muhammad Hanif Abdillah", "Laki-laki", "SNMPTN", "Aktif"));
        listMahasiswa.add(new Mahasiswa("2202046", "Nurainun", "Perempuan", "SBMPTN", "Aktif"));
        listMahasiswa.add(new Mahasiswa("2205101", "Kelvin Julian Putra", "Laki-laki", "Mandiri", "Aktif"));
        listMahasiswa.add(new Mahasiswa("2200163", "Rifanny Lysara Annastasya", "Perempuan", "SNMPTN", "Aktif"));
        listMahasiswa.add(new Mahasiswa("2202869", "Revana Faliha Salma", "Perempuan", "SBMPTN", "Aktif"));
        listMahasiswa.add(new Mahasiswa("2209489", "Rakha Dhifiargo Hariadi", "Laki-laki", "Mandiri", "Aktif"));
        listMahasiswa.add(new Mahasiswa("2203142", "Roshan Syalwan Nurilham", "Laki-laki", "SNMPTN", "Cuti"));
        listMahasiswa.add(new Mahasiswa("2200311", "Raden Rahman Ismail", "Laki-laki", "SBMPTN", "Aktif"));
        listMahasiswa.add(new Mahasiswa("2200978", "Ratu Syahirah Khairunnisa", "Perempuan", "Mandiri", "Aktif"));
        listMahasiswa.add(new Mahasiswa("2204509", "Muhammad Fahreza Fauzan", "Laki-laki", "SNMPTN", "Aktif"));
        listMahasiswa.add(new Mahasiswa("2205027", "Muhammad Rizki Revandi", "Laki-laki", "SBMPTN", "Cuti"));
        listMahasiswa.add(new Mahasiswa("2203484", "Arya Aydin Margono", "Laki-laki", "Mandiri", "Aktif"));
        listMahasiswa.add(new Mahasiswa("2200481", "Marvel Ravindra Dioputra", "Laki-laki", "SNMPTN", "Aktif"));
        listMahasiswa.add(new Mahasiswa("2209889", "Muhammad Fadlul Hafiizh", "Laki-laki", "SBMPTN", "Aktif"));
        listMahasiswa.add(new Mahasiswa("2206697", "Rifa Sania", "Perempuan", "Mandiri", "Aktif"));
        listMahasiswa.add(new Mahasiswa("2207260", "Imam Chalish Rafidhul Haque", "Laki-laki", "SNMPTN", "Aktif"));
        listMahasiswa.add(new Mahasiswa("2204343", "Meiva Labibah Putri", "Perempuan", "SBMPTN", "Aktif"));
    }
}
