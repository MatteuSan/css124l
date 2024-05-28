import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class App extends JFrame {
    DefaultListModel<Task> taskListModel;
    JList<Task> taskList;
    JButton startButton = new JButton("Start"),
            pauseButton = new JButton("Pause"),
            stopButton = new JButton("Stop"),
            addTaskButton = new JButton("Add Task"),
            editTaskButton = new JButton("Edit Task"),
            deleteTaskButton = new JButton("Delete Task");
    Timer pomodoroTimer;
    JPanel main = new JPanel(),
           topPanel = new JPanel(),
           taskListPanel = new JPanel(),
           taskInputPanel = new JPanel(),
           timerPanel = new JPanel();
    JMenuBar menuBar = new JMenuBar();
    JMenu menuFile = new JMenu("File"),
          menuEdit = new JMenu("Edit"),
          menuHelp = new JMenu("Help");
    JMenuItem fileClearTaskList = new JMenuItem("Clear Task List"),
              fileExit = new JMenuItem("Exit App");
    JMenuItem editEditTask = new JMenuItem("Edit"),
              editDeleteTask = new JMenuItem("Delete");
    JMenuItem helpAbout = new JMenuItem("About");
    JTextField taskNameField = new JTextField(20);
    JLabel timerLabel = new JLabel("25:00");


    private static final int POMODORO_DURATION = 25 * 60; // 25 minutes in seconds

    public App() {
        super("Pomodoro Timer");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initMenu();
        initComponents();
        initActions();

        setVisible(true);
    }

    private void initMenu() {
        menuFile.add(fileClearTaskList);
        menuFile.add(fileExit);

        menuEdit.add(editEditTask);
        menuEdit.add(editDeleteTask);

        menuHelp.add(helpAbout);

        menuBar.add(menuFile);
        menuBar.add(menuEdit);
        menuBar.add(menuHelp);

        setJMenuBar(menuBar);
    }

    private void initComponents() {
        taskListModel = new DefaultListModel<>();
        taskList = new JList<>(taskListModel);
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taskList.addMouseListener(new TaskMouseListener());
        JScrollPane taskListScrollPane = new JScrollPane(taskList);

        // Create timer for Pomodoro
        pomodoroTimer = new Timer(1000, new ActionListener() {
            int timeLeft = POMODORO_DURATION;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (timeLeft > 0) {
                    timeLeft--;
                    updateTimerLabel(timeLeft);
                } else {
                    pomodoroTimer.stop();
                    JOptionPane.showMessageDialog(null, "Pomodoro completed!");
                    updateTimerLabel(POMODORO_DURATION);
                }
            }
        });

        topPanel.setLayout(new GridLayout(1, 2));
        topPanel.add(timerPanel);
        topPanel.add(taskListPanel);

        taskListPanel.setLayout(new BorderLayout());
        taskListPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        taskListPanel.add(taskListScrollPane, BorderLayout.CENTER);
        JPanel div = new JPanel();
        div.add(editTaskButton);
        div.add(deleteTaskButton);
        taskListPanel.add(div, BorderLayout.SOUTH);

        timerPanel.setLayout(new BorderLayout());
        timerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        timerLabel.setFont(new Font("Arial", Font.BOLD, 50));
        timerLabel.setHorizontalAlignment(JLabel.CENTER);
        startButton.setPreferredSize(new Dimension(200, 50));
        JPanel div2 = new JPanel();
        div2.setLayout(new GridLayout(1, 3, 10, 10));
        div2.add(startButton);
        div2.add(pauseButton);
        div2.add(stopButton);
        pauseButton.setEnabled(false);
        stopButton.setEnabled(false);
        timerPanel.add(div2, BorderLayout.SOUTH);
        timerPanel.add(timerLabel, BorderLayout.CENTER);

        taskInputPanel.setLayout(new FlowLayout());
        taskInputPanel.add(new JLabel("Task Name:"));
        taskInputPanel.add(taskNameField);
        taskInputPanel.add(addTaskButton);

        main.setLayout(new BorderLayout());
        main.add(topPanel, BorderLayout.CENTER);
        main.add(taskInputPanel, BorderLayout.SOUTH);
        add(main);
    }

    private void initActions() {
        fileClearTaskList.addActionListener(e -> taskListModel.clear());
        fileExit.addActionListener(e -> System.exit(0));

        editEditTask.addActionListener(e -> editTask());
        editDeleteTask.addActionListener(e -> deleteTask());

        helpAbout.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Pomodoro Timer with To-Do List\n\n" +
                    "Created by: Matthew Hernandez\n" +
                    "Version: 1.0.0\n" +
                    "FOPI01 CSS124L");
        });

        addTaskButton.addActionListener(e -> {
            String taskName = taskNameField.getText();
            if (!taskName.isEmpty()) {
                taskListModel.addElement(new Task(taskName));
                taskNameField.setText("");
            }
        });

        editTaskButton.addActionListener(e -> editTask());
        deleteTaskButton.addActionListener(e -> deleteTask());

        startButton.addActionListener(e -> startPomodoro());
        pauseButton.addActionListener(e -> pomodoroTimer.stop());
        stopButton.addActionListener(e -> {
            pomodoroTimer.stop();
            updateTimerLabel(POMODORO_DURATION);

            startButton.setText("Start");
            pauseButton.setEnabled(false);
            stopButton.setEnabled(false);
            taskList.setEnabled(true);

            int selectedIndex = taskList.getSelectedIndex();
            if (selectedIndex != -1) taskListModel.remove(selectedIndex);
        });
    }

    private void startPomodoro() {
        if (!pomodoroTimer.isRunning()) {
            int selectedIndex = taskList.getSelectedIndex();
            if (selectedIndex != -1) {
                startButton.setText("Resume");
                pauseButton.setEnabled(true);
                stopButton.setEnabled(true);
                taskList.setEnabled(false);
                pomodoroTimer.start();
            } else {
                JOptionPane.showMessageDialog(this, "Please select a task to start Pomodoro.");
            }
        } else if (pomodoroTimer.isRunning() && pauseButton.isEnabled()) {
            startButton.setText("Resume");
            pauseButton.setEnabled(true);
            stopButton.setEnabled(true);
            pomodoroTimer.stop();
        }
    }

    private void updateTimerLabel(int timeLeft) {
        timerLabel.setText(String.format("%02d:%02d", timeLeft / 60, timeLeft % 60));
    }

    private void editTask() {
        int selectedIndex = taskList.getSelectedIndex();

        if (selectedIndex != -1) {
            Task selectedTask = taskListModel.getElementAt(selectedIndex);
            String newTaskName = JOptionPane.showInputDialog(App.this, "Edit Task:", selectedTask.getName());
            if (newTaskName != null) {
                selectedTask.setName(newTaskName);
                taskListModel.setElementAt(selectedTask, selectedIndex);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task to edit.");
        }
    }

    private void deleteTask() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            taskListModel.remove(selectedIndex);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task to delete.");
        }
    }

    private class TaskMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                int index = taskList.locationToIndex(e.getPoint());
                if (index != -1) {
                    Task task = taskListModel.getElementAt(index);
                    String newTaskName = JOptionPane.showInputDialog(App.this, "Edit Task:", task.getName());
                    if (newTaskName != null) {
                        task.setName(newTaskName);
                        taskListModel.setElementAt(task, index);
                    }
                }
            }
        }
    }

    private static class Task {
        private String name;

        public Task(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public static void main(String[] args) {
        new App();
    }
}
