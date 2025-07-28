package fooddelivery.Views;

import fooddelivery.Agents.CustomerAgent;
import jade.lang.acl.ACLMessage;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class OrderGUI extends JFrame {
    private CustomerAgent agent;
    private JTextField dishField;
    private JTextArea statusArea;
    private JButton orderButton;
    private JLabel titleLabel;
    private JPanel headerPanel;

    // Modern color scheme
    private static final Color PRIMARY_COLOR = new Color(255, 99, 71); // Tomato red
    private static final Color SECONDARY_COLOR = new Color(255, 165, 0); // Orange
    private static final Color BUTTON_COLOR = new Color(173, 216, 230); // Light blue
    private static final Color BUTTON_HOVER_COLOR = new Color(135, 206, 250); // Sky blue (darker for hover)
    private static final Color BACKGROUND_COLOR = new Color(248, 249, 250); // Light gray
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Color TEXT_COLOR = new Color(33, 37, 41); // Dark gray
    private static final Color SUCCESS_COLOR = new Color(40, 167, 69); // Green
    private static final Color WARNING_COLOR = new Color(255, 193, 7); // Yellow

    public OrderGUI(CustomerAgent agent) {
        this.agent = agent;
        initializeComponents();
        setupLayout();
        setupStyling();
        setupEventHandlers();
        setupJadeBehavior();

        setTitle("🍕 DeliciousEats - Food Delivery");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void initializeComponents() {
        // Header
        headerPanel = new JPanel();
        titleLabel = new JLabel("🍕 DeliciousEats");

        // Input components
        dishField = new JTextField(20);
        orderButton = new JButton("Place Order");

        // Status area
        statusArea = new JTextArea(15, 30);
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        // Header Panel
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBorder(new EmptyBorder(20, 30, 20, 30));

        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel subtitleLabel = new JLabel("Order your favorite food with just a click!");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(subtitleLabel, BorderLayout.SOUTH);

        // Input Panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.setBorder(new EmptyBorder(20, 30, 20, 30));

        JLabel dishLabel = new JLabel("What would you like to order?");
        dishLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        dishLabel.setBorder(new EmptyBorder(0, 0, 10, 0));

        // Dish input container
        JPanel dishInputPanel = new JPanel(new BorderLayout(10, 0));
        dishField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dishField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(206, 212, 218), 1),
                new EmptyBorder(12, 15, 12, 15)
        ));

        orderButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        orderButton.setPreferredSize(new Dimension(140, 45));
        orderButton.setFocusPainted(false);
        orderButton.setBorderPainted(false);

        dishInputPanel.add(dishField, BorderLayout.CENTER);
        dishInputPanel.add(orderButton, BorderLayout.EAST);

        inputPanel.add(dishLabel, BorderLayout.NORTH);
        inputPanel.add(dishInputPanel, BorderLayout.CENTER);

        // Status Panel
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBorder(new EmptyBorder(0, 30, 30, 30));

        JLabel statusLabel = new JLabel("Order Status & Updates");
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        statusLabel.setBorder(new EmptyBorder(0, 0, 10, 0));

        statusArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        statusArea.setEditable(false);
        statusArea.setLineWrap(true);
        statusArea.setWrapStyleWord(true);
        statusArea.setBorder(new EmptyBorder(15, 15, 15, 15));

        JScrollPane scrollPane = new JScrollPane(statusArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(206, 212, 218), 1));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        statusPanel.add(statusLabel, BorderLayout.NORTH);
        statusPanel.add(scrollPane, BorderLayout.CENTER);

        // Add panels to main frame
        add(headerPanel, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.SOUTH);
    }

    private void setupStyling() {
        // Set background colors
        getContentPane().setBackground(BACKGROUND_COLOR);
        headerPanel.setBackground(CARD_COLOR);

        // Style title
        titleLabel.setForeground(PRIMARY_COLOR);

        // Style input field
        dishField.setBackground(Color.WHITE);
        dishField.setForeground(TEXT_COLOR);

        // Style button with light blue color - force override any look and feel
        orderButton.setBackground(BUTTON_COLOR);
        orderButton.setForeground(Color.BLACK); // Changed to black for better contrast with light blue
        orderButton.setOpaque(true); // Ensure background color is visible
        orderButton.setContentAreaFilled(true); // Make sure content area is filled
        orderButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Style status area
        statusArea.setBackground(Color.WHITE);
        statusArea.setForeground(TEXT_COLOR);

        // Add placeholder text
        addPlaceholderText();

        // Add welcome message
        statusArea.append("🎉 Welcome to DeliciousEats!\n");
        statusArea.append("💡 Enter a dish name above and click 'Place Order' to get started.\n");
        statusArea.append("─────────────────────────────────────\n\n");
    }

    private void addPlaceholderText() {
        dishField.setText("e.g., Margherita Pizza, Chicken Burger...");
        dishField.setForeground(Color.GRAY);

        dishField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (dishField.getText().equals("e.g., Margherita Pizza, Chicken Burger...")) {
                    dishField.setText("");
                    dishField.setForeground(TEXT_COLOR);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (dishField.getText().isEmpty()) {
                    dishField.setText("e.g., Margherita Pizza, Chicken Burger...");
                    dishField.setForeground(Color.GRAY);
                }
            }
        });
    }

    private void setupEventHandlers() {
        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dishName = dishField.getText().trim();
                if (!dishName.isEmpty() && !dishName.equals("e.g., Margherita Pizza, Chicken Burger...")) {
                    // Change button state
                    orderButton.setEnabled(false);
                    orderButton.setText("Processing... ⏳");
                    orderButton.setBackground(WARNING_COLOR);

                    // Place order
                    agent.placeOrder(dishName);
                    appendStatusMessage("🛒 Order placed for: " + dishName, "order");

                    // Clear field
                    dishField.setText("");
                    dishField.setForeground(TEXT_COLOR);
                } else {
                    appendStatusMessage("⚠️ Please enter a valid dish name.", "warning");
                    dishField.requestFocus();
                }
            }
        });

        // Add hover effects with light blue colors
        orderButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (orderButton.isEnabled()) {
                    orderButton.setBackground(BUTTON_HOVER_COLOR);
                }
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (orderButton.isEnabled()) {
                    orderButton.setBackground(BUTTON_COLOR);
                }
            }
        });

        // Enter key support
        dishField.addActionListener(e -> orderButton.doClick());
    }

    private void setupJadeBehavior() {
        agent.addBehaviour(new jade.core.behaviours.CyclicBehaviour() {
            @Override
            public void action() {
                jade.lang.acl.ACLMessage msg = myAgent.receive(jade.lang.acl.MessageTemplate.MatchOntology("Food-Order"));
                if (msg != null) {
                    System.out.println("GUI received: " + msg.getContent());

                    SwingUtilities.invokeLater(() -> {
                        appendStatusMessage(msg.getContent(), "info");

                        if (msg.getPerformative() == ACLMessage.INFORM) {
                            // Reset button state to light blue
                            orderButton.setEnabled(true);
                            orderButton.setText("Place Order");
                            orderButton.setBackground(BUTTON_COLOR);

                            appendStatusMessage("✅ Ready for your next order!", "success");
                        }
                    });
                } else {
                    block();
                }
            }
        });
    }

    private void appendStatusMessage(String message, String type) {
        String timestamp = java.time.LocalTime.now().format(
                java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss")
        );

        String icon = "";
        switch (type) {
            case "order": icon = "🛒"; break;
            case "success": icon = "✅"; break;
            case "warning": icon = "⚠️"; break;
            case "info": icon = "ℹ️"; break;
            default: icon = "•"; break;
        }

        statusArea.append(String.format("[%s] %s %s\n", timestamp, icon, message));
        statusArea.setCaretPosition(statusArea.getDocument().getLength());
    }

    public void updateOrderStatus(String status) {
        SwingUtilities.invokeLater(() -> {
            appendStatusMessage(status, "info");
        });
    }
}