import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @author Matthew Hernandez
 */
public class App extends JFrame {
    int count = 0;
    int multiplierValue = 1;

    JLabel countText = new JLabel(String.valueOf(count));
    JButton increaseButton = new JButton("+");
    JButton decreaseButton = new JButton("-");
    JButton resetButton = new JButton("Reset All");

    JPanel adjustmentPanel = new JPanel();

    JComboBox<String> multiplier = new JComboBox<>(new String[]{"1", "2", "3", "5", "10", String.valueOf(count)});

    public App() {
        super("Counter App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
        setLayout(new BorderLayout());

        initLayout();
        initActions();
    }

    void initLayout() {
        adjustmentPanel.setLayout(new FlowLayout());
        rootPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        countText.setFont(new Font("Arial", Font.BOLD, 100));
        countText.setHorizontalAlignment(JLabel.CENTER);

        increaseButton.setFont(new Font("Arial", Font.BOLD, 50));
        increaseButton.setFocusable(true);
        decreaseButton.setFont(new Font("Arial", Font.BOLD, 50));
        decreaseButton.setFocusable(true);

        multiplier.setToolTipText("Multiplier: multiply the increment/decrement value.");
        adjustmentPanel.add(new JLabel("Multiplier: "));
        adjustmentPanel.add(multiplier);
        adjustmentPanel.add(resetButton);

        add(countText, BorderLayout.CENTER);
        add(increaseButton, BorderLayout.EAST);
        add(decreaseButton, BorderLayout.WEST);
        add(adjustmentPanel, BorderLayout.SOUTH);
    }

    void initActions() {
        increaseButton.addActionListener(e -> {
            count += multiplierValue;
            countText.setText(String.valueOf(count));
        });

        increaseButton.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    count -= multiplierValue;
                    countText.setText(String.valueOf(count));
                    decreaseButton.grabFocus();
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    count += multiplierValue;
                    countText.setText(String.valueOf(count));
                }
            }
        });

        decreaseButton.addActionListener(e -> {
            count -= multiplierValue;
            countText.setText(String.valueOf(count));
        });

        decreaseButton.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    count += multiplierValue;
                    countText.setText(String.valueOf(count));
                    increaseButton.grabFocus();
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    count -= multiplierValue;
                    countText.setText(String.valueOf(count));
                }
            }
        });

        resetButton.addActionListener(e -> {
            count = 0;
            multiplierValue = 1;
            multiplier.setSelectedIndex(0);
            countText.setText(String.valueOf(count));
        });

        multiplier.addItemListener(e -> multiplierValue = Integer.parseInt((String) e.getItem()));
    }

    public static void main(String[] args) {
        new App();
    }
}