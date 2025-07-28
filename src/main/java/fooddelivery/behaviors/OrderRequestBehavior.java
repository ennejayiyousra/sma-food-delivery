package fooddelivery.behaviors;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class OrderRequestBehavior extends OneShotBehaviour {
    private String dishName;

    public OrderRequestBehavior(jade.core.Agent agent, String dishName) {
        super(agent);
        this.dishName = dishName;
    }

    @Override
    public void action() {
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        msg.addReceiver(new AID("Delivery", AID.ISLOCALNAME));
        msg.setContent(dishName);
        msg.setLanguage("English");
        msg.setOntology("Food-Order");
        myAgent.send(msg);
        System.out.println("CustomerAgent sent REQUEST to " + msg.getAllReceiver() + " for: " + dishName);
    }
}