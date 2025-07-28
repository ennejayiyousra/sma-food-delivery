package fooddelivery.behaviors;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import fooddelivery.Agents.RestaurantAgent;

import java.util.Random;

public class RestaurantProposalBehavior extends CyclicBehaviour {
    private Random random = new Random();

    public RestaurantProposalBehavior(RestaurantAgent agent) {
        super(agent);
    }

    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.and(
                MessageTemplate.MatchPerformative(ACLMessage.CFP),
                MessageTemplate.MatchOntology("Food-Order")
        );
        ACLMessage msg = myAgent.receive(mt);
        if (msg != null) {
            String dishName = msg.getContent();
            double price = 5.0 + random.nextDouble() * 15.0; // Random price between $5 and $20
            int deliveryTime = 10 + random.nextInt(51); // Random delivery time between 10 and 60 minutes
            ACLMessage reply = msg.createReply();
            reply.setPerformative(ACLMessage.PROPOSE);
            reply.setContent(dishName + ":" + price + ":" + deliveryTime);
            reply.setOntology("Food-Order");
            myAgent.send(reply);
            System.out.println(((RestaurantAgent) myAgent).getRestaurantName() + " sent proposal for " + dishName + ": $" + price + ", " + deliveryTime + " min");
        } else {
            block();
        }

        // Handle acceptance
        MessageTemplate acceptMt = MessageTemplate.and(
                MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL),
                MessageTemplate.MatchOntology("Food-Order")
        );
        ACLMessage acceptMsg = myAgent.receive(acceptMt);
        if (acceptMsg != null) {
            System.out.println(((RestaurantAgent) myAgent).getRestaurantName() + " received order acceptance for " + acceptMsg.getContent());
        }
    }
}