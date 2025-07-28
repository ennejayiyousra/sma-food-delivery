package fooddelivery.behaviors;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ProposalHandlingBehavior extends ParallelBehaviour {
    private String dishName;
    private AID[] restaurantAIDs;
    private AID bestRestaurant;
    private double bestPrice = Double.MAX_VALUE;
    private int bestDeliveryTime = Integer.MAX_VALUE;
    private int responsesReceived = 0;

    public ProposalHandlingBehavior(Agent agent, String dishName) {
        super(agent, ParallelBehaviour.WHEN_ALL);
        this.dishName = dishName;

        DFAgentDescription dfd = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType("Food-Delivery");
        dfd.addServices(sd);
        try {
            DFAgentDescription[] result = DFService.search(agent, dfd);
            restaurantAIDs = new AID[result.length];
            System.out.println("DeliveryAgent found " + result.length + " restaurants:");
            for (int i = 0; i < result.length; i++) {
                restaurantAIDs[i] = result[i].getName();
                System.out.println(" - " + restaurantAIDs[i].getLocalName());
            }
        } catch (FIPAException e) {
            e.printStackTrace();
        }

        addSubBehaviour(new TickerBehaviour(agent, 60000) {
            @Override
            protected void onTick() {
                if (bestRestaurant != null) {
                    ACLMessage accept = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
                    accept.addReceiver(bestRestaurant);
                    accept.setContent(dishName + ":" + bestPrice + ":" + bestDeliveryTime);
                    accept.setOntology("Food-Order");
                    myAgent.send(accept);
                    System.out.println("DeliveryAgent selected " + bestRestaurant.getLocalName() + " with price $" + bestPrice + " and delivery time " + bestDeliveryTime + " minutes.");

                    ACLMessage inform = new ACLMessage(ACLMessage.INFORM);
                    inform.addReceiver(new AID("Customer", AID.ISLOCALNAME));
                    inform.setContent("Order confirmed with " + bestRestaurant.getLocalName() + " for " + dishName + " at $" + bestPrice + " (Delivery: " + bestDeliveryTime + " min)");
                    inform.setOntology("Food-Order");
                    myAgent.send(inform);
                } else {
                    System.out.println("DeliveryAgent received no valid proposals.");
                    ACLMessage inform = new ACLMessage(ACLMessage.INFORM);
                    inform.addReceiver(new AID("Customer", AID.ISLOCALNAME));
                    inform.setContent("No restaurants available for " + dishName);
                    inform.setOntology("Food-Order");
                    myAgent.send(inform);
                }
                myAgent.removeBehaviour(this.getParent()); // Remove the entire behavior
            }
        });

        addSubBehaviour(new jade.core.behaviours.CyclicBehaviour() {
            @Override
            public void action() {
                MessageTemplate mt = MessageTemplate.and(
                        MessageTemplate.MatchPerformative(ACLMessage.PROPOSE),
                        MessageTemplate.MatchOntology("Food-Order")
                );
                ACLMessage msg = myAgent.receive(mt);
                if (msg != null) {
                    System.out.println("DeliveryAgent received PROPOSE from " + msg.getSender().getLocalName());
                    String[] content = msg.getContent().split(":");
                    double price = Double.parseDouble(content[1]);
                    int deliveryTime = Integer.parseInt(content[2]);
                    if (price < bestPrice || (price == bestPrice && deliveryTime < bestDeliveryTime)) {
                        bestPrice = price;
                        bestDeliveryTime = deliveryTime;
                        bestRestaurant = msg.getSender();
                    }
                    responsesReceived++;
                    if (responsesReceived >= restaurantAIDs.length) {
                        getParent().root(); // Stop the behavior when all responses are received
                    }
                } else {
                    block();
                }
            }
        });
    }

    @Override
    public void onStart() {
        if (restaurantAIDs != null && restaurantAIDs.length > 0) {
            ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
            for (AID restaurant : restaurantAIDs) {
                cfp.addReceiver(restaurant);
            }
            cfp.setContent(dishName);
            cfp.setLanguage("English");
            cfp.setOntology("Food-Order");
            myAgent.send(cfp);
            System.out.println("DeliveryAgent sent CFP for " + dishName + " to " + restaurantAIDs.length + " restaurants.");
        } else {
            System.out.println("DeliveryAgent found no restaurants for " + dishName);
            ACLMessage inform = new ACLMessage(ACLMessage.INFORM);
            inform.addReceiver(new AID("Customer", AID.ISLOCALNAME));
            inform.setContent("No restaurants available for " + dishName);
            inform.setOntology("Food-Order");
            myAgent.send(inform);
            myAgent.removeBehaviour(this); // Remove behavior if no restaurants
        }
    }
}