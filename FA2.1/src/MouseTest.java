import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Matthew Hernandez
 */
public class MouseTest extends JFrame {
    int shape = 0;

    JButton jButton1 = new JButton("Line"),
            jButton2 = new JButton("Circle"),
            jButton3 = new JButton("Rectangle");
    JPanel main = new JPanel(),
           controlPanel = new JPanel(),
           drawingPanel = new JPanel();

    public MouseTest() {
        super("Mouse Test");
        setSize(500, 300);
        initComponents();
        initActions();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    void initComponents() {
        main.setLayout(new BorderLayout());
        controlPanel.setLayout(new FlowLayout());
        controlPanel.add(jButton1);
        controlPanel.add(jButton2);
        controlPanel.add(jButton3);
        main.add(controlPanel, BorderLayout.NORTH);
        main.add(drawingPanel, BorderLayout.CENTER);
        add(main);
    }

    void initActions() {
        jButton1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                super.mouseClicked(me);
                clear();
                shape = 1;
                paint();
            }
        });

        jButton2.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                super.mouseClicked(me);
                clear();
                shape = 2;
                paint();
            }
        });

        jButton3.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                super.mouseClicked(me);
                clear();
                shape = 3;
                paint();
            }
        });
    }

    void paint() {
        Graphics g = drawingPanel.getGraphics();
        switch (shape) {
            case 1:
                g.drawLine((250 - 100), 10, 100, 100);
                break;
            case 2:
                g.drawOval((250 - 100), 10, 100, 100);
                break;
            case 3:
                g.drawRect((250 - 100), 10, 100, 100);
                break;
        }
    }

    void clear() {
        Graphics g = drawingPanel.getGraphics();
        g.clearRect(0, 0, 500, 500);
    }

    public static void main(String[] args) {
        MouseTest app = new MouseTest();
    }
}