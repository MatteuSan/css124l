import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @author Matthew Hernandez
 */
public class App extends JFrame {
    String food, drink, specialRequest;
    boolean hasWater;
    int foodPrice, drinkPrice, totalPrice;

    String[] foods = {"Chicken Salad", "Tuna Salad", "Vegetable Salad"};
    int cSaladPrice = 100,
        tSaladPrice = 150,
        vSaladPrice = 200,
        sodaPrice = 50,
        juicePrice = 150,
        milkteaPrice = 100;

    JPanel main = new JPanel(),
           foodPanel = new JPanel(),
           drinkPanel = new JPanel(),
           specialRequestPanel = new JPanel(),
           menuPanel = new JPanel(),
           orderPanel = new JPanel();
    JList<String> foodList = new JList<>();
    JRadioButton drink1 = new JRadioButton("Soda"),
                 drink2 = new JRadioButton("Juice"),
                 drink3 = new JRadioButton("Milktea");
    JCheckBox water = new JCheckBox("Request Water");
    JTextArea specialRequestField = new JTextArea();
    JMenuBar menuBar = new JMenuBar();
    JMenu file = new JMenu("File"),
          edit = new JMenu("Edit"),
          help = new JMenu("Help");

    JMenuItem placeOrder = new JMenuItem("Place Order"),
              clearOrder = new JMenuItem("Clear Order"),
              displayOrder = new JMenuItem("Display Order"),
              exit = new JMenuItem("Exit App");
    JMenuItem menuClear = new JMenuItem("Clear Menu Selection"),
              drinkClear = new JMenuItem("Clear Drink Selection"),
              specialRequestClear = new JMenuItem("Clear Special Request");
    JMenuItem about = new JMenuItem("About");

    public App() {
        super("70s Diner Order Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        initData();
        initComponents();
        initActions();
        initMenuActions();
        setVisible(true);
    }

    void initData() {
        foodList.setListData(foods);
        foodList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    void initComponents() {
        main.setLayout(new BorderLayout());
        orderPanel.setLayout(new GridLayout(2, 1));
        menuPanel.setLayout(new GridLayout(1, 2));

        foodPanel.setBorder(BorderFactory.createTitledBorder("Menu Selection"));
        foodPanel.setLayout(new GridLayout(1, 1));
        foodPanel.add(foodList);

        drinkPanel.setLayout(new GridLayout(3, 1));
        drinkPanel.setBorder(BorderFactory.createTitledBorder("Drink Selection"));
        drinkPanel.add(drink1);
        drinkPanel.add(drink2);
        drinkPanel.add(drink3);

        menuPanel.add(foodPanel);
        menuPanel.add(drinkPanel);

        Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        orderPanel.setBorder(padding);
        orderPanel.add(menuPanel);
        orderPanel.add(specialRequestPanel);

        specialRequestPanel.setLayout(new GridLayout(2, 1));
        specialRequestPanel.setBorder(BorderFactory.createTitledBorder("Special Request"));
        specialRequestPanel.add(specialRequestField);
        specialRequestPanel.add(water);

        file.add(placeOrder);
        file.add(clearOrder);
        file.add(displayOrder);
        file.add(exit);

        edit.add(menuClear);
        edit.add(drinkClear);
        edit.add(specialRequestClear);

        help.add(about);

        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(help);

        main.add(orderPanel, BorderLayout.CENTER);
        main.add(menuBar, BorderLayout.NORTH);
        add(main);
    }

    void initActions() {
        drink1.addActionListener(e -> {
            drink2.setSelected(false);
            drink3.setSelected(false);
            drink = "Soda";
            drinkPrice = sodaPrice;
        });

        drink2.addActionListener(e -> {
            drink1.setSelected(false);
            drink3.setSelected(false);
            drink = "Juice";
            drinkPrice = juicePrice;
        });

        drink3.addActionListener(e -> {
            drink1.setSelected(false);
            drink2.setSelected(false);
            drink = "Milktea";
            drinkPrice = milkteaPrice;
        });

        foodList.addListSelectionListener(e -> {
            int index = foodList.getSelectedIndex();
            food = foodList.getSelectedValue();
            foodPrice = index == 0 ? cSaladPrice : index == 1 ? tSaladPrice : vSaladPrice;
        });

        specialRequestField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                specialRequest = specialRequestField.getText();
            }
        });

        water.addActionListener(e -> hasWater = water.isSelected());
    }

    void initMenuActions() {
        placeOrder.addActionListener(e -> {
            if (food == null && drink == null) {
                JOptionPane.showMessageDialog(null, "Please select a food and drink!");
                return;
            }
            totalPrice = foodPrice + drinkPrice;
            JOptionPane.showMessageDialog(null, "Order Placed!" + "\n" +
                    "Food: " + food + "\n" +
                    "Drink: " + drink + "\n" +
                    "Special Request: " + specialRequest + "\n" +
                    (hasWater ? "Water: Yes" : "Water: No") + "\n" +
                    "Total Price: " + totalPrice);
        });

        clearOrder.addActionListener(e -> {
            food = null;
            foodPrice = 0;
            drinkPrice = 0;
            drink = null;
            specialRequest = null;
            hasWater = false;
            totalPrice = 0;

            drink1.setSelected(false);
            drink2.setSelected(false);
            drink3.setSelected(false);
            specialRequestField.setText("");
            water.setSelected(false);
            foodList.clearSelection();
        });

        displayOrder.addActionListener(e -> JOptionPane.showMessageDialog(null, "Food: " + food + "\n" +
                "Drink: " + drink + "\n" +
                "Special Request: " + specialRequest + "\n" +
                (hasWater ? "Water: Yes" : "Water: No") + "\n" +
                "Total Price: " + totalPrice));

        exit.addActionListener(e -> System.exit(0));

        menuClear.addActionListener(e -> {
            food = null;
            foodPrice = 0;
            foodList.clearSelection();
        });

        drinkClear.addActionListener(e -> {
            drink1.setSelected(false);
            drink2.setSelected(false);
            drink3.setSelected(false);
            drink = null;
            drinkPrice = 0;
        });

        specialRequestClear.addActionListener(e -> {
            specialRequestField.setText("");
            water.setSelected(false);
        });

        about.addActionListener(e -> JOptionPane.showMessageDialog(null, "70s Diner Order Form\n" +
                "Version 1.0\n" +
                "Created by Matthew Hernandez\nFOPI01 CSS124L"));
    }

    public static void main(String[] args) {
        new App();
    }
}