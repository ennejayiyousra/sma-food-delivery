package fooddelivery.Agents;

import fooddelivery.behaviors.OrderRequestBehavior;
import fooddelivery.Views.OrderGUI;
import jade.core.Agent;

public class CustomerAgent extends Agent {
    private OrderGUI gui;

    protected void setup() {
        try {
            gui = new OrderGUI(this);
            gui.setVisible(true);
            System.out.println("CustomerAgent " + getAID().getLocalName() + " is ready.");
        } catch (Exception e) {
            System.err.println("Failed to initialize OrderGUI: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void placeOrder(String dishName) {
        if (gui != null) {
            addBehaviour(new OrderRequestBehavior(this, dishName));
        } else {
            System.err.println("Cannot place order: GUI is not initialized.");
        }
    }

    public void updateOrderStatus(String status) {
        if (gui != null) {
            gui.updateOrderStatus(status);
        }
    }

    protected void takeDown() {
        if (gui != null) {
            gui.dispose();
            System.out.println("CustomerAgent " + getAID().getLocalName() + " is terminating.");
        } else {
            System.out.println("CustomerAgent " + getAID().getLocalName() + " is terminating without GUI.");
        }
    }
}