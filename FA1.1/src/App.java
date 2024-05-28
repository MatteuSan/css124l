import javax.swing.*;
import java.awt.*;

/**
 * @author Matthew Hernandez
 */
public class App extends JFrame {
    public App() {
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1077, 800);
        setTitle("SpaceX Starlink Project");

        rootPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane results = new JScrollPane();
        results.add(new JTable());
        JPanel bottomActions = new JPanel();
        bottomActions.setLayout(new FlowLayout());
        bottomActions.add(button("Clear All"));

        add(form(), BorderLayout.NORTH);
        add(results, BorderLayout.CENTER);
        add(bottomActions, BorderLayout.SOUTH);

        setVisible(true);
    }

    private GridLayout flexRow(int columns, int gap) {
        GridLayout gridLayout = new GridLayout(1, columns);
        gridLayout.setVgap(gap);
        gridLayout.setHgap(gap);
        return gridLayout;
    }

    private JPanel form() {
        JPanel form = new JPanel();
        GridLayout formLayout = new GridLayout(5, 1);

        formLayout.setVgap(3);
        formLayout.setHgap(3);

        form.setLayout(formLayout);
        form.add(div(flexRow(2, 10), formField("Satellite ID"), button("Find Satellite")));
        form.add(formField("Satellite Name"));
        form.add(div(flexRow(2, 10), formField("Longitude"), formField("Latitude")));
        form.add(div(flexRow(2, 10), formField("Elevation"), selectField("Health Status")));
        form.add(div(flexRow(3, 0), button("Save"), button("Update"), button("Delete")));
        form.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        return form;
    }

    private JPanel div(LayoutManager layout, Component ...children) {
        JPanel div = new JPanel();
        div.setLayout(layout);
        for (Component child : children) div.add(child);
        return div;
    }

    private JButton button(String text) {
        JButton button = new JButton(text);
        // TODO: Future interactivity code here
        return button;
    }

    private JPanel formField(String labelText) {
        JPanel formField = new JPanel();
        JLabel formFieldLabel = new JLabel(labelText);
        JTextField formFieldInput = new JTextField();
        LayoutManager formFieldLayout = new BorderLayout();

        formFieldLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

        formField.setLayout(formFieldLayout);
        formField.setFocusable(true);
        formField.add(formFieldLabel, BorderLayout.WEST);
        formField.add(formFieldInput, BorderLayout.CENTER);

        return formField;
    }

    private JPanel selectField(String labelText) {
        JPanel selectField = new JPanel();
        JLabel selectFieldLabel = new JLabel(labelText);
        JComboBox<ComboBoxModel> selectFieldInput = new JComboBox<>();
        LayoutManager selectFieldLayout = new BorderLayout();

        selectFieldLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

        selectField.setLayout(selectFieldLayout);
        selectField.setFocusable(true);
        selectField.add(selectFieldLabel, BorderLayout.WEST);
        selectField.add(selectFieldInput, BorderLayout.CENTER);

        return selectField;
    }

    public static void main(String[] args) {
        new App();
    }
}