package fooddelivery.Agents;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import fooddelivery.behaviors.RestaurantProposalBehavior;

public class RestaurantAgent extends Agent {
    private String restaurantName;

    protected void setup() {
        Object[] args = getArguments();
        if (args != null && args.length > 0) {
            restaurantName = (String) args[0];
        } else {
            restaurantName = getAID().getLocalName();
        }

        // Register service
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setName("Food-Delivery");
        sd.setType("Food-Delivery");
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
            System.out.println("RestaurantAgent " + restaurantName + " registered service.");
        } catch (FIPAException e) {
            e.printStackTrace();
        }

        // Add behavior
        addBehaviour(new RestaurantProposalBehavior(this));
        System.out.println("RestaurantAgent " + restaurantName + " is ready.");
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    protected void takeDown() {
        try {
            DFService.deregister(this);
            System.out.println("RestaurantAgent " + restaurantName + " is terminating.");
        } catch (FIPAException e) {
            e.printStackTrace();
        }
    }
}