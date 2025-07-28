package fooddelivery.Views;

import fooddelivery.Agents.CustomerAgent;
import jade.lang.acl.ACLMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrderGUI extends JFrame {
    private CustomerAgent agent;
    private JTextField dishField = new JTextField(20);
    private JTextArea statusArea = new JTextArea(10, 30);
    private JButton orderButton = new JButton("Place Order");

    public OrderGUI(CustomerAgent agent) {
        this.agent = agent;
        setTitle("Food Delivery Platform");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Changed to avoid full exit
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        JLabel dishLabel = new JLabel("Dish Name:");
        inputPanel.add(dishLabel);
        inputPanel.add(dishField);
        inputPanel.add(orderButton);

        statusArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(statusArea);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);

        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dishName = dishField.getText().trim();
                if (!dishName.isEmpty()) {
                    orderButton.setEnabled(false);
                    agent.placeOrder(dishName);
                    statusArea.append("Order placed for: " + dishName + "\n");
                    dishField.setText("");
                } else {
                    statusArea.append("Please enter a dish name.\n");
                }
            }
        });

        agent.addBehaviour(new jade.core.behaviours.CyclicBehaviour() {
            @Override
            public void action() {
                jade.lang.acl.ACLMessage msg = myAgent.receive(jade.lang.acl.MessageTemplate.MatchOntology("Food-Order"));
                if (msg != null) {
                    System.out.println("GUI received: " + msg.getContent());
                    statusArea.append(msg.getContent() + "\n");
                    if (msg.getPerformative() == ACLMessage.INFORM) {
                        orderButton.setEnabled(true);
                    }
                } else {
                    block();
                }
            }
        });
    }

    public void updateOrderStatus(String status) {
        statusArea.append(status + "\n");
    }
}