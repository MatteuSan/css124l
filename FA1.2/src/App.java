package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

/**
 * @author Matthew Hernandez
 */
public class App extends JFrame {
    JFrame frame = new JFrame("Song Lyrics Font Editor");
    JPanel controlPanel = new JPanel();
    JPanel lyricsView = new JPanel(new BorderLayout());
    String[] songOptions = {
        "Select a Song",
        "CaptainSparklez - Fallen Kingdom",
        "CaptainSparklez - Revenge",
        "CaptainSparklez - Take Back the Night",
        "Cory Wong - Golden",
        "Laufey - Christmas Waltz",
    };
    JComboBox<String> comboBox = new JComboBox<>(songOptions);
    JTextArea lyricsArea = new JTextArea(30, 30);
    JScrollPane scrollPane = new JScrollPane(lyricsArea);
    JCheckBox checkBoxNormal = new JCheckBox("Normal");
    JCheckBox checkBoxBold = new JCheckBox("Bold");
    JCheckBox checkBoxItalic = new JCheckBox("Italic");
    JRadioButton radioButtonFontSmall = new JRadioButton("Small");
    JRadioButton radioButtonFontMedium = new JRadioButton("Medium");
    JRadioButton radioButtonFontLarge = new JRadioButton("Large");

    private int currentFontStyle = Font.PLAIN;

    public App() {
        lyricsArea.setFont(new Font("Arial", Font.PLAIN, 14));
        lyricsArea.setLineWrap(true);
        lyricsArea.setWrapStyleWord(true);

        ButtonGroup fontSizeGroup = new ButtonGroup();
        fontSizeGroup.add(radioButtonFontSmall);
        fontSizeGroup.add(radioButtonFontMedium);
        fontSizeGroup.add(radioButtonFontLarge);

        controlPanel.setBorder(BorderFactory.createTitledBorder("Control Panel"));
        controlPanel.add(comboBox);
        controlPanel.add(checkBoxNormal);
        controlPanel.add(checkBoxItalic);
        controlPanel.add(checkBoxBold);
        controlPanel.add(radioButtonFontSmall);
        controlPanel.add(radioButtonFontMedium);
        controlPanel.add(radioButtonFontLarge);

        lyricsView.setBorder(BorderFactory.createTitledBorder("Lyrics View"));
        lyricsView.add(scrollPane, BorderLayout.CENTER);

        frame.add(controlPanel, BorderLayout.NORTH);
        frame.add(lyricsView, BorderLayout.CENTER);
        frame.setSize(960, 540);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        checkBoxNormal.setSelected(true);

        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedSong = Objects.requireNonNull(comboBox.getSelectedItem()).toString();
                if (!selectedSong.equals("Select a Song")) {
                    String fileName = "/lyrics/" + selectedSong + ".txt";

                    try (InputStream is = getClass().getResourceAsStream(fileName);
                         InputStreamReader isr = new InputStreamReader(is);
                         BufferedReader reader = new BufferedReader(isr)) {
                        String line;
                        StringBuilder content = new StringBuilder();
                        while ((line = reader.readLine()) != null) {
                            content.append(line).append("\n");
                        }
                        lyricsArea.setText(content.toString());

                        // Set the caret position to the beginning of the text
                        lyricsArea.setCaretPosition(0);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    lyricsArea.setText(""); // Clear text if "Select a Song" is chosen
                }
            }
        });

        // Action listener for font style checkboxes
        ActionListener checkBoxListener = e -> {
            int style = Font.PLAIN;

            if (checkBoxItalic.isSelected()) style |= Font.ITALIC;
            if (checkBoxBold.isSelected()) style |= Font.BOLD;
            if (checkBoxNormal.isSelected()) style |= Font.PLAIN;

            lyricsArea.setFont(new Font("Arial", style, getSelectedFontSize()));
            currentFontStyle = style;
        };

        // Configure actions for font style checkboxes
        checkBoxNormal.addActionListener(e -> {
            if (checkBoxNormal.isSelected()) {
                checkBoxItalic.setSelected(false);
                checkBoxBold.setSelected(false);
            }
            checkBoxListener.actionPerformed(e);
        });

        checkBoxItalic.addActionListener(e -> {
            if (checkBoxItalic.isSelected()) checkBoxNormal.setSelected(false);
            checkBoxListener.actionPerformed(e);
        });

        checkBoxBold.addActionListener(e -> {
            if (checkBoxBold.isSelected()) checkBoxNormal.setSelected(false);
            checkBoxListener.actionPerformed(e);
        });

        // Action listeners for font size radio buttons
        radioButtonFontSmall.addActionListener(e -> lyricsArea.setFont(new Font("Arial", currentFontStyle, getSelectedFontSize())));

        radioButtonFontMedium.addActionListener(e -> lyricsArea.setFont(new Font("Arial", currentFontStyle, getSelectedFontSize())));

        radioButtonFontLarge.addActionListener(e -> lyricsArea.setFont(new Font("Arial", currentFontStyle, getSelectedFontSize())));

        // Set the default font size to "Medium"
        radioButtonFontMedium.setSelected(true);
    }

    // Helper method to get the selected font size
    private int getSelectedFontSize() {
        if (radioButtonFontSmall.isSelected()) return 12;
        if (radioButtonFontLarge.isSelected())  return 18;
        return 14;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App());
    }
}
