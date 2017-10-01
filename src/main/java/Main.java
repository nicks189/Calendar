import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class Main {
    static JLabel lblMonth, lblYear;
    static JButton btnPrev, btnNext;
    static JTable tblCalendar;
    static JComboBox<String> cmbYear;
    static JFrame frmMain;
    static Container pane;
    static DefaultTableModel mdlCalendar; //table model
    static JScrollPane stblCalendar; //scroll pane
    static JPanel pnlCalendar; //panel
    static int realDay, realMonth, realYear, currentYear, currentMonth;
    static String val, filepath;
    static final String DIRECTORY = "/.calendarAppData/";

    /**
     * @param args
     */
    public static void main(String args[]) throws IOException {
        try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
        catch (ClassNotFoundException e) {}
        catch (InstantiationException e) {}
        catch (IllegalAccessException e) {}
        catch (UnsupportedLookAndFeelException e) {}

        filepath = System.getProperty("user.home");
        filepath += DIRECTORY;
        File file = new File(filepath);
        if(!file.exists()) {
            file.mkdir();
        }

        frmMain = new JFrame("Calendar Application");
        frmMain.setSize(628, 640);
        pane = frmMain.getContentPane();
        pane.setLayout(null);
        frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //instantiate variables
        lblMonth = new JLabel("January");
        lblYear = new JLabel("Change Year: ");
        cmbYear = new JComboBox<String>();
        btnPrev = new JButton("<<");
        btnNext = new JButton(">>");
        mdlCalendar = new DefaultTableModel() {public boolean isCellEditable(int rowIndex, int mColIndex) {return false;}};
        tblCalendar = new JTable(mdlCalendar); //table using the above model
        stblCalendar = new JScrollPane(tblCalendar); //the scrollpane of the above table
        pnlCalendar = new JPanel(null); //create the "panel" to place components

        //add controls to panel
        pnlCalendar.setBorder(BorderFactory.createTitledBorder("Calendar"));
        pane.add(pnlCalendar);
        pnlCalendar.add(lblMonth);
        pnlCalendar.add(lblYear);
        pnlCalendar.add(cmbYear);
        pnlCalendar.add(btnNext);
        pnlCalendar.add(btnPrev);
        pnlCalendar.add(stblCalendar);

        //set fonts
        lblMonth.setFont(new Font("Serif", Font.PLAIN, 20));
        lblYear.setFont(new Font("Serif", Font.PLAIN, 16));

        //set bounds
        pnlCalendar.setBounds(5, 5, 620, 620);
        lblMonth.setBounds(303-lblMonth.getPreferredSize().width/2, 25, 180, 25);
        lblYear.setBounds(380, 570, 125, 30);
        cmbYear.setBounds(510, 570, 100, 30);
        btnPrev.setBounds(10, 20, 60, 35);
        btnNext.setBounds(551, 20, 60, 35);
        stblCalendar.setBounds(10, 60, 600, 500);

        //get real month/year
        frmMain.setResizable(false);
        frmMain.setVisible(true);
        GregorianCalendar cal = new GregorianCalendar();
        realDay = cal.get(GregorianCalendar.DAY_OF_MONTH);
        realMonth = cal.get(GregorianCalendar.MONTH);
        realYear = cal.get(GregorianCalendar.YEAR);
        currentMonth = realMonth;
        currentYear = realYear;
        for(int i = realYear - 100; i < realYear + 101; i++) { //fills combo box
            cmbYear.addItem(String.valueOf(i));
        }
        //add headers
        String[] headers = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for(int i = 0; i < 7; i++) {
            mdlCalendar.addColumn(headers[i]);
        }

        //set background color
        tblCalendar.getParent().setBackground(tblCalendar.getBackground());
        //no resizing/reordering
        tblCalendar.getTableHeader().setResizingAllowed(false);
        tblCalendar.getTableHeader().setReorderingAllowed(false);
        //single cell selection
        tblCalendar.setColumnSelectionAllowed(true);
        tblCalendar.setRowSelectionAllowed(true);
        tblCalendar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //set row/column count
        tblCalendar.setRowHeight(79);
        mdlCalendar.setColumnCount(7);
        mdlCalendar.setRowCount(6);

        //register action listeners
        btnNext.addActionListener(new btnNext_Action());
        btnPrev.addActionListener(new btnPrev_Action());
        cmbYear.addActionListener(new cmbYear_Action());

        refreshCalendar(realMonth, realYear);
        tblCalendar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tblCalendar.rowAtPoint(e.getPoint());
                int col = tblCalendar.columnAtPoint(e.getPoint());
                val = (String)mdlCalendar.getValueAt(row, col);
                if(val == null){}
                else if(val.contains((CharSequence)"*")) {
                    try {
                        Note tmp = new Note(val, currentMonth + 1, currentYear);
                        if(tmp.isEmpty()) {
                            tmp.deleteNote();
                            val = val.replace("*", "");
                        }
                    }
                    catch(IOException error) {
                        System.err.println("ERROR");
                    }
                    mdlCalendar.setValueAt(val, row, col);
                }
                else {
                    val += "*";
                    try {
                        Note tmp = new Note(val, currentMonth + 1, currentYear);
                        if(tmp.isEmpty()) {
                            tmp.deleteNote();
                            val = val.replace("*", "");
                        }
                    }
                    catch(IOException error) {
                        System.err.println("ERROR");
                    }
                    mdlCalendar.setValueAt(val, row, col);
                }
            }
        });
    }

    public static void refreshCalendar(int month, int year) {
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        int nod,som; //number of days, start of month
        btnPrev.setEnabled(true);
        btnNext.setEnabled(true);

        if (month == 0 && year <= realYear - 10) {btnPrev.setEnabled(false);} //too early
        if (month == 11 && year >= realYear + 100) {btnNext.setEnabled(false);} //too late
        lblMonth.setText(months[month]); //refresh the month label
        lblMonth.setBounds(303 - lblMonth.getPreferredSize().width/2, 25, 180, 25);
        cmbYear.setSelectedItem(String.valueOf(year));

        //get first day of month and number of days
        GregorianCalendar cal = new GregorianCalendar(year, month, 1);
        nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        som = cal.get(GregorianCalendar.DAY_OF_WEEK);

        //clear table
        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 7; j++) {
                mdlCalendar.setValueAt(null, i, j);
            }
        }

        //draw calendar
        for(int i = 1; i <= nod; i++) {
            int row = new Integer((i + som - 2) / 7);
            int column = (i + som - 2) % 7;
            String filepathtmp = filepath;
            filepathtmp += "note_" + (month + 1) + "_" + i + "_" + year;
            File ftmp = new File(filepathtmp);
            if(ftmp.exists()) {
                mdlCalendar.setValueAt(Integer.toString(i) + "*", row, column);
            }
            else {
                mdlCalendar.setValueAt(Integer.toString(i), row, column);
            }
        }
        tblCalendar.setDefaultRenderer(tblCalendar.getColumnClass(0), new tblCalendarRenderer());
    }

    static class tblCalendarRenderer extends DefaultTableCellRenderer {
        public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column) {
            super.getTableCellRendererComponent(table, value, selected, focused, row, column);
            if(column == 0 || column == 6) //weekend
                setBackground(new Color(250, 220, 220));
            else //weekday
                setBackground(new Color(255, 255, 255));
            if(value != null) {
                String temp = value.toString();
                if(value.toString().contains((CharSequence)"*")) {
                    StringBuilder sb = new StringBuilder(value.toString());
                    sb.deleteCharAt(value.toString().length()-1);
                    temp = sb.toString();
                }
                if(Integer.parseInt(temp) == realDay && currentMonth == realMonth && currentYear == realYear)
                    setBackground(new Color(202,202,246));
            }
            if(value == null)
                setBackground(new Color(230,230,233));
            setBorder(null);
            setForeground(Color.black);

            return this;
        }
    }

    static class btnNext_Action implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            if(currentMonth == 11) {
                currentMonth = 0;
                currentYear += 1;
            }
            else
                currentMonth += 1;
            refreshCalendar(currentMonth, currentYear);
        }
    }

    static class btnPrev_Action implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(currentMonth == 0) {
                currentMonth = 11;
                currentYear -= 1;
            }
            else
                currentMonth -= 1;
            refreshCalendar(currentMonth, currentYear);
        }
    }

    static class cmbYear_Action implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            if(cmbYear.getSelectedItem() != null) {
                String b = cmbYear.getSelectedItem().toString();
                currentYear = Integer.parseInt(b);
                refreshCalendar(currentMonth, currentYear);
            }
        }
    }


}