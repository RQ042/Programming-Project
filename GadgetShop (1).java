import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GadgetShop extends JFrame {

    private ArrayList<Gadget> gadgetList = new ArrayList<>();

    private JTextField modelTextField = new JTextField(12);
    private JTextField priceTextField = new JTextField(12);
    private JTextField weightTextField = new JTextField(12);
    private JTextField sizeTextField = new JTextField(12);
    private JTextField creditTextField = new JTextField(12);
    private JTextField memoryTextField = new JTextField(12);
    private JTextField phoneTextField = new JTextField(12);
    private JTextField durationTextField = new JTextField(12);
    private JTextField downloadTextField = new JTextField(12);
    private JTextField displayNumberTextField = new JTextField(12);

    private JButton addMobileButton = new JButton("Add Mobile");
    private JButton addMP3Button = new JButton("Add MP3");
    private JButton makeCallButton = new JButton("Make a Call");
    private JButton downloadMusicButton = new JButton("Download Music");
    private JButton displayAllButton = new JButton("Display All");
    private JButton clearButton = new JButton("Clear");

    public GadgetShop() {
        super("Gadget Shop");

        JPanel main = new JPanel(new BorderLayout(15, 15));
        main.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel header = new JLabel("Gadget Shop System", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 20));
        main.add(header, BorderLayout.NORTH);

        JPanel left = new JPanel(new GridBagLayout());
        left.setBorder(BorderFactory.createTitledBorder("Gadget Details"));
        JPanel right = new JPanel(new GridBagLayout());
        right.setBorder(BorderFactory.createTitledBorder("Mobile / MP3 Options"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int r = 0;
        addField(left, gbc, "Model:", modelTextField, r++);
        addField(left, gbc, "Price (£):", priceTextField, r++);
        addField(left, gbc, "Weight (g):", weightTextField, r++);
        addField(left, gbc, "Size:", sizeTextField, r++);

        int r2 = 0;
        addField(right, gbc, "Initial Credit:", creditTextField, r2++);
        addField(right, gbc, "Initial Memory:", memoryTextField, r2++);
        addField(right, gbc, "Phone Number:", phoneTextField, r2++);
        addField(right, gbc, "Duration:", durationTextField, r2++);
        addField(right, gbc, "Download Size:", downloadTextField, r2++);
        addField(right, gbc, "Display Number:", displayNumberTextField, r2++);

        JPanel center = new JPanel(new GridLayout(1, 2, 20, 0));
        center.add(left);
        center.add(right);

        JPanel buttons = new JPanel(new GridLayout(2, 3, 15, 15));
        buttons.setBorder(BorderFactory.createTitledBorder("Actions"));
        buttons.add(addMobileButton);
        buttons.add(addMP3Button);
        buttons.add(makeCallButton);
        buttons.add(downloadMusicButton);
        buttons.add(displayAllButton);
        buttons.add(clearButton);

        main.add(center, BorderLayout.CENTER);
        main.add(buttons, BorderLayout.SOUTH);

        setContentPane(main);

        addListeners();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addField(JPanel panel, GridBagConstraints gbc, String label, JTextField field, int row) {
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private void highlightFields(JTextField... fields) {
        JTextField[] all = {
            modelTextField, priceTextField, weightTextField, sizeTextField,
            creditTextField, memoryTextField, phoneTextField, durationTextField,
            downloadTextField, displayNumberTextField
        };

        for (JTextField f : all) {
            f.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            f.setFont(f.getFont().deriveFont(Font.PLAIN));
        }
        for (JTextField f : fields) {
            f.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            f.setFont(f.getFont().deriveFont(Font.BOLD));
        }
    }

    private void addListeners() {
        addMobileButton.addActionListener(e -> {
            highlightFields(modelTextField, priceTextField, weightTextField, sizeTextField, creditTextField);
            addMobile();
        });

        addMP3Button.addActionListener(e -> {
            highlightFields(modelTextField, priceTextField, weightTextField, sizeTextField, memoryTextField);
            addMP3();
        });

        makeCallButton.addActionListener(e -> {
            highlightFields(phoneTextField, durationTextField, displayNumberTextField);
            makeCall();
        });

        downloadMusicButton.addActionListener(e -> {
            highlightFields(downloadTextField, displayNumberTextField);
            downloadMusic();
        });

        displayAllButton.addActionListener(e -> {
            highlightFields();
            displayAll();
        });

        clearButton.addActionListener(e -> {
            highlightFields();
            clearFields();
        });
    }

    // ====== LOGIC METHODS (same as before) ======

    private boolean existsInList(String model) {
        for (Gadget g : gadgetList) {
            if (g.getModel().equalsIgnoreCase(model)) {
                return true;
            }
        }
        return false;
    }

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GadgetShop::new);
    }
}
