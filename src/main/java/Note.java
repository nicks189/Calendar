import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JButton;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Note extends JFrame {
    private int month, year;
    private String day;
    private JPanel contentPane;
    private JTextArea textArea;
    private String fpath;
    private final String DIRECTORY = "/.calendarAppData/";

    /**
     * Create the frame.
     * @throws IOException
     */
    public Note(String d, int m, int y) throws IOException {
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        day = d;
        month = m;
        year = y;

        d = d.replace("*", "");
        fpath = System.getProperty("user.home");
        fpath += DIRECTORY;
        fpath += "note_" + m + "_" + d + "_" + y;

        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        setName("Note");
        setVisible(true);

        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.CENTER);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
        gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0};
        gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
        gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        panel.setLayout(gbl_panel);

        textArea = new JTextArea(15,10);
        GridBagConstraints gbc_textField = new GridBagConstraints();
        gbc_textField.insets = new Insets(0, 0, 5, 0);
        gbc_textField.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField.gridx = 6;
        gbc_textField.gridy = 2;
        panel.add(textArea, gbc_textField);


        readFromFile();

        JButton btnDone = new JButton("Save");
        btnDone.addActionListener(new hideBtnAction());
        GridBagConstraints gbc_btnDone = new GridBagConstraints();
        gbc_btnDone.gridx = 6;
        gbc_btnDone.gridy = 3;
        panel.add(btnDone, gbc_btnDone);
    }

    private void writeToFile() throws IOException {
        FileWriter fwriter = null;
        try {
            fwriter = new FileWriter(fpath);
            String output = textArea.getText();
            fwriter.write(output);
        }
        finally {
            if(fwriter != null) {
                try {
                    fwriter.close();
                }
                catch(IOException e) {
                    System.err.println("Something went wrong in writeFromFile");
                    return;
                }
            }
        }
    }

    private void readFromFile() throws IOException {
        File curFile = new File(fpath);
        FileReader freader = null;
        int tmp = 0;
        String buffer = "";
        if(curFile.exists()) {
            try {
                freader = new FileReader(fpath);
                while((tmp = freader.read()) != -1) {
                    buffer += (char)tmp;
                }
                textArea.setText(buffer);
            }
            finally {
                if(freader != null) {
                    try {
                        freader.close();
                    }
                    catch(IOException e) {
                        System.err.println("Something went wrong in Note.readFromFile");
                        return;
                    }
                }
            }
        }
    }

    public void deleteNote() throws IOException {
        Path p = Paths.get(fpath);
        Files.delete(p);
    }

    public boolean isEmpty() {
        String tmp = textArea.getText().trim();
        if(tmp.length() < 1) {
            return true;
        }
        return false;
    }

    private class hideBtnAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
            try {
                writeToFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    protected String getDay() {
        return day;
    }

    protected int getMonth() {
        return month;
    }

    protected int getYear() {
        return year;
    }

}