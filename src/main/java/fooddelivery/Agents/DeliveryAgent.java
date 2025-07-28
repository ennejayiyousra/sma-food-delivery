package fooddelivery.Agents;

import fooddelivery.behaviors.ProposalHandlingBehavior;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class DeliveryAgent extends Agent {
    private boolean isProcessing = false;

    protected void setup() {
        System.out.println("DeliveryAgent " + getAID().getLocalName() + " is ready.");

        addBehaviour(new CyclicBehaviour(this) {
            @Override
            public void action() {
                MessageTemplate mt = MessageTemplate.and(
                        MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
                        MessageTemplate.MatchOntology("Food-Order")
                );
                ACLMessage msg = receive(mt);
                if (msg != null && !isProcessing) {
                    System.out.println("DeliveryAgent received REQUEST from " + msg.getSender().getLocalName() + " for: " + msg.getContent());
                    isProcessing = true;
                    String dishName = msg.getContent();
                    addBehaviour(new ProposalHandlingBehavior(myAgent, dishName));
                } else {
                    block();
                }
            }
        });
    }

    protected void takeDown() {
        System.out.println("DeliveryAgent " + getAID().getLocalName() + " is terminating.");
        try {
            DFService.deregister(this);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
    }

    // Method to reset processing flag (called by ProposalHandlingBehavior when done)
    public void setProcessingDone() {
        isProcessing = false;
    }
}