import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class CalorieTracker {
    private double dailyCalorieRequirement = 0;
    private double caloriesEatenToday = 0;
    private LocalDate lastResetDate = LocalDate.now();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CalorieTracker().createMainMenu());
    }

    public void createMainMenu() {
        JFrame frame = new JFrame("FitTrack - Main Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel welcomeLabel = new JLabel("Welcome to FitTrack!", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JButton calorieRequirementButton = new JButton("Daily Calorie Requirement");
        JButton foodTrackerButton = new JButton("Food Calorie Tracker");
        JButton eraseDataButton = new JButton("Erase Data");

        panel.add(welcomeLabel);
        panel.add(calorieRequirementButton);
        panel.add(foodTrackerButton);
        panel.add(eraseDataButton);

        frame.add(panel);
        frame.setVisible(true);

        calorieRequirementButton.addActionListener(e -> {
            frame.dispose();
            createUserProfileScreen();
        });

        foodTrackerButton.addActionListener(e -> {
            frame.dispose();
            createFoodTrackerScreen();
        });

        eraseDataButton.addActionListener(e -> {
            dailyCalorieRequirement = 0;
            caloriesEatenToday = 0;
            lastResetDate = LocalDate.now();
            JOptionPane.showMessageDialog(frame, "All data has been erased.");
        });
    }

    public void createUserProfileScreen() {
        JFrame frame = new JFrame("FitTrack - Calorie Requirement");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9, 2, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();

        JLabel genderLabel = new JLabel("Gender:");
        String[] genders = {"Male", "Female", "Others"};
        JComboBox<String> genderBox = new JComboBox<>(genders);

        JLabel ageLabel = new JLabel("Age:");
        JTextField ageField = new JTextField();

        JLabel weightLabel = new JLabel("Weight (kg):");
        JTextField weightField = new JTextField();

        JLabel heightLabel = new JLabel("Height (cm):");
        JTextField heightField = new JTextField();

        JLabel activityLabel = new JLabel("Activity Level:");
        String[] activityLevels = {
            "Little or no exercise",
            "Light: exercise 1-3 times/week",
            "Moderate: exercise 4-5 times/week",
            "Active: daily exercise"
        };
        JComboBox<String> activityBox = new JComboBox<>(activityLevels);

        JLabel goalLabel = new JLabel("Goal:");
        String[] goals = {"Lose", "Maintain", "Gain"};
        JComboBox<String> goalBox = new JComboBox<>(goals);

        JButton backButton = new JButton("Back to Main Menu");
        JButton calculateButton = new JButton("Calculate");
        

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(genderLabel);
        panel.add(genderBox);
        panel.add(ageLabel);
        panel.add(ageField);
        panel.add(weightLabel);
        panel.add(weightField);
        panel.add(heightLabel);
        panel.add(heightField);
        panel.add(activityLabel);
        panel.add(activityBox);
        panel.add(goalLabel);
        panel.add(goalBox);
        panel.add(backButton);
        panel.add(calculateButton);
        

        frame.add(panel);
        frame.setVisible(true);

        calculateButton.addActionListener(e -> {
            try {
                String gender = (String) genderBox.getSelectedItem();
                int age = Integer.parseInt(ageField.getText());
                double weight = Double.parseDouble(weightField.getText());
                double height = Double.parseDouble(heightField.getText());
                String activity = (String) activityBox.getSelectedItem();
                String goal = (String) goalBox.getSelectedItem();

                double bmr = (gender.equals("Male"))
                        ? 10 * weight + 6.25 * height - 5 * age + 5
                        : 10 * weight + 6.25 * height - 5 * age - 161;

                double activityFactor = switch (activity) {
                    case "Little or no exercise" -> 1.2;
                    case "Light: exercise 1-3 times/week" -> 1.375;
                    case "Moderate: exercise 4-5 times/week" -> 1.55;
                    default -> 1.725; // Active
                };

                dailyCalorieRequirement = bmr * activityFactor;

                if (goal.equals("Lose")) dailyCalorieRequirement -= 300;
                if (goal.equals("Gain")) dailyCalorieRequirement += 300;

                JOptionPane.showMessageDialog(frame, "Your daily calorie target is: " + (int) dailyCalorieRequirement + " kcal");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter valid numerical values for age, weight, and height.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> {
            frame.dispose();
            createMainMenu();
        });
    }

    public void createFoodTrackerScreen() {
        JFrame frame = new JFrame("Food Calorie Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel foodLabel = new JLabel("Food Item:");
        JTextField foodField = new JTextField();

        JLabel calorieLabel = new JLabel("Calories (kcal):");
        JTextField calorieField = new JTextField();

        JLabel totalCaloriesLabel = new JLabel("Calories Eaten Today:");
        JLabel totalCaloriesValueLabel = new JLabel((int) caloriesEatenToday + " / " + (int) dailyCalorieRequirement + " kcal");

        JButton addButton = new JButton("Add to Tracker");
        JButton doneButton = new JButton("Back to Main Menu");

        panel.add(foodLabel);
        panel.add(foodField);
        panel.add(calorieLabel);
        panel.add(calorieField);
        panel.add(totalCaloriesLabel);
        panel.add(totalCaloriesValueLabel);
        panel.add(new JLabel());
        panel.add(addButton);
        panel.add(new JLabel());
        panel.add(doneButton);

        frame.add(panel);
        frame.setVisible(true);

        addButton.addActionListener(event -> {
            String foodItem = foodField.getText().trim();
            if (!foodItem.matches("[a-zA-Z ]+")) {
                JOptionPane.showMessageDialog(frame, "Food item must contain only letters.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                foodField.setText("");
                return;
            }

            try {
                double foodCalories = Double.parseDouble(calorieField.getText());
                caloriesEatenToday += foodCalories;
                totalCaloriesValueLabel.setText((int) caloriesEatenToday + " / " + (int) dailyCalorieRequirement + " kcal");
                foodField.setText("");
                calorieField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid calorie amount.", "Error", JOptionPane.ERROR_MESSAGE);
                calorieField.setText("");
            }
        });

        doneButton.addActionListener(e -> {
            frame.dispose();
            createMainMenu();
        });
    }
}
