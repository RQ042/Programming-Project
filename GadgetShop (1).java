import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GadgetShop extends JFrame {

    private ArrayList<Gadget> gadgetList = new ArrayList<>();

    // Text fields
    private JTextField modelTextField = new JTextField(10);
    private JTextField priceTextField = new JTextField(10);
    private JTextField weightTextField = new JTextField(10);
    private JTextField sizeTextField = new JTextField(10);
    private JTextField creditTextField = new JTextField(10);
    private JTextField memoryTextField = new JTextField(10);
    private JTextField phoneTextField = new JTextField(10);
    private JTextField durationTextField = new JTextField(10);
    private JTextField downloadTextField = new JTextField(10);
    private JTextField displayNumberTextField = new JTextField(10);

    // Buttons
    private JButton addMobileButton = new JButton("Add Mobile");
    private JButton addMP3Button = new JButton("Add MP3");
    private JButton makeCallButton = new JButton("Make a Call");
    private JButton downloadMusicButton = new JButton("Download Music");
    private JButton displayAllButton = new JButton("Display All");
    private JButton clearButton = new JButton("Clear");

    public GadgetShop() {
        super("Gadget Shop");

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        int row = 0;

        // Add labels + text fields
        addLabelAndField("Model:", modelTextField, gbc, row++);
        addLabelAndField("Price (£):", priceTextField, gbc, row++);
        addLabelAndField("Weight (g):", weightTextField, gbc, row++);
        addLabelAndField("Size:", sizeTextField, gbc, row++);
        addLabelAndField("Initial Credit:", creditTextField, gbc, row++);
        addLabelAndField("Initial Memory:", memoryTextField, gbc, row++);
        addLabelAndField("Phone Number:", phoneTextField, gbc, row++);
        addLabelAndField("Duration:", durationTextField, gbc, row++);
        addLabelAndField("Download Size:", downloadTextField, gbc, row++);
        addLabelAndField("Display Number:", displayNumberTextField, gbc, row++);

        // Add buttons
        gbc.gridx = 0; gbc.gridy = row;
        add(addMobileButton, gbc);
        gbc.gridx = 1;
        add(addMP3Button, gbc);

        gbc.gridx = 0; gbc.gridy = row + 1;
        add(makeCallButton, gbc);
        gbc.gridx = 1;
        add(downloadMusicButton, gbc);

        gbc.gridx = 0; gbc.gridy = row + 2;
        add(displayAllButton, gbc);
        gbc.gridx = 1;
        add(clearButton, gbc);

        // Button actions
        addMobileButton.addActionListener(e -> addMobile());
        addMP3Button.addActionListener(e -> addMP3());
        makeCallButton.addActionListener(e -> makeCall());
        downloadMusicButton.addActionListener(e -> downloadMusic());
        displayAllButton.addActionListener(e -> displayAll());
        clearButton.addActionListener(e -> clearFields());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addLabelAndField(String labelText, JTextField field, GridBagConstraints gbc, int row) {
        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel(labelText), gbc);
        gbc.gridx = 1;
        add(field, gbc);
    }

    private boolean existsInList(String model) {
        for (Gadget g : gadgetList) {
            if (g.getModel().equalsIgnoreCase(model)) {
                return true;
            }
        }
        return false;
    }

    // ===== Button methods =====

    private void addMobile() {
        try {
            String model = modelTextField.getText();
            if (existsInList(model)) {
                JOptionPane.showMessageDialog(this, "This Mobile already exists!");
                return;
            }
            double price = Double.parseDouble(priceTextField.getText());
            int weight = Integer.parseInt(weightTextField.getText());
            String size = sizeTextField.getText();
            int credit = Integer.parseInt(creditTextField.getText());

            Mobile m = new Mobile(model, price, weight, size, credit);
            gadgetList.add(m);
            JOptionPane.showMessageDialog(this, "Mobile added successfully!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter valid numbers for price, weight, and credit.");
        }
    }

    private void addMP3() {
        try {
            String model = modelTextField.getText();
            if (existsInList(model)) {
                JOptionPane.showMessageDialog(this, "This MP3 already exists!");
                return;
            }
            double price = Double.parseDouble(priceTextField.getText());
            int weight = Integer.parseInt(weightTextField.getText());
            String size = sizeTextField.getText();
            int memory = Integer.parseInt(memoryTextField.getText());

            MP3 mp3 = new MP3(model, price, weight, size, memory);
            gadgetList.add(mp3);
            JOptionPane.showMessageDialog(this, "MP3 added successfully!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter valid numbers for price, weight, and memory.");
        }
    }

    private void makeCall() {
        int index = getDisplayNumber();
        if (index == -1) return;

        Gadget g = gadgetList.get(index);
        if (g instanceof Mobile) {
            try {
                String phone = phoneTextField.getText();
                int duration = Integer.parseInt(durationTextField.getText());
                ((Mobile) g).makeCall(phone, duration);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Enter a valid duration.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selected gadget is not a Mobile.");
        }
    }

    private void downloadMusic() {
        int index = getDisplayNumber();
        if (index == -1) return;

        Gadget g = gadgetList.get(index);
        if (g instanceof MP3) {
            try {
                int size = Integer.parseInt(downloadTextField.getText());
                ((MP3) g).downloadMusic(size);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Enter a valid download size.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selected gadget is not an MP3.");
        }
    }

    private void displayAll() {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < gadgetList.size(); i++) {
            output.append("Gadget #").append(i).append("\n");
            output.append(gadgetList.get(i).toString()).append("\n\n");
        }
        JOptionPane.showMessageDialog(this, output.toString());
    }

    private void clearFields() {
        modelTextField.setText("");
        priceTextField.setText("");
        weightTextField.setText("");
        sizeTextField.setText("");
        creditTextField.setText("");
        memoryTextField.setText("");
        phoneTextField.setText("");
        durationTextField.setText("");
        downloadTextField.setText("");
        displayNumberTextField.setText("");
    }

    private int getDisplayNumber() {
        try {
            int num = Integer.parseInt(displayNumberTextField.getText());
            if (num < 0 || num >= gadgetList.size()) {
                JOptionPane.showMessageDialog(this, "Invalid gadget number!");
                return -1;
            }
            return num;
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter a valid integer for display number.");
            return -1;
        }
    }

    // ===== Main method (fixed to open first time) =====
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GadgetShop());
    }
}