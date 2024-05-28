import javax.swing.*;


public class Test extends JFrame {

    JMenu jm1,jm2,jm3;

    JMenuItem jmi1, jmi2, jmi3;

    JMenuBar mBar;
    JPanel main = new JPanel();

    Test() {

        super("JMenu Test");

        jm1 = new JMenu("A");

        jm2 = new JMenu("B");

        jm3 = new JMenu("C");

        jmi1 = new JMenuItem("D");

        jmi2 = new JMenuItem("E");

        jmi3 = new JMenuItem("F");

        jm1.add(jmi2);

        jm2.add(jmi1);

        jm3.add(jmi3);

        mBar = new JMenuBar();

        mBar.add(jm1);

        mBar.add(jm2);

        mBar.add(jm3);

        setJMenuBar(mBar);

        setSize(200, 200);

        setVisible(true);
    }

    public static void main(String[] args) {
        new Test();
    }

}