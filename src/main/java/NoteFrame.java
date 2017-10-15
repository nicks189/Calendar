import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;

public class NoteFrame extends JFrame {
    private int month, year;
    private int day;
    private JPanel contentPane;
    private JTextArea textArea;
    private Note note;

    /**
     * Create the frame.
     */
    public NoteFrame(String d, int m, int y) {
        day = Integer.parseInt(d.replace("*", ""));
        month = m;
        year = y;

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

        note = NoteManager.getNoteByDate(new Date(year - 1900, month, day));
        if (note != null) {
            textArea.setText(note.getInfo());
        }

        JButton btnDone = new JButton("Save");
        btnDone.addActionListener(new hideBtnAction());
        GridBagConstraints gbc_btnDone = new GridBagConstraints();
        gbc_btnDone.gridx = 6;
        gbc_btnDone.gridy = 3;
        panel.add(btnDone, gbc_btnDone);
    }

    public void deleteNote() {
        if (note != null) {
            NoteManager.delete(note.getId());
        }
    }

    public boolean isEmpty() {
        if (note != null) {
            return note.getInfo().isEmpty();
        } else {
            return true;
        }
    }

    private class hideBtnAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
            String info = textArea.getText();
            Date date = new Date(year - 1900, month, day);
            if (note != null) {
                note.setInfo(info);
                note.setDate(date);
                NoteManager.update(note);
            } else {
                try {
                    note = new Note();
                    note.setInfo(info);
                    note.setDate(date);
                    NoteManager.insert(note);
                } catch (SQLException ex) {
                    System.err.print("Couldn't insert note into database.");
                }
            }
            NoteManager.update(note);
        }
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

}