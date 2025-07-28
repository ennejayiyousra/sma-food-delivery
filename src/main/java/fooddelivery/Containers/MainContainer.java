package fooddelivery.Containers;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;

public class MainContainer {
    public static void main(String[] args) throws ControllerException {
        Runtime runtime = Runtime.instance();
        ProfileImpl profileImpl = new ProfileImpl();
        profileImpl.setParameter(ProfileImpl.GUI, "true");
        AgentContainer mainContainer = runtime.createMainContainer(profileImpl);

        // Start agents
        AgentController customer = mainContainer.createNewAgent("Customer", "fooddelivery.Agents.CustomerAgent", null);
        AgentController delivery = mainContainer.createNewAgent("Delivery", "fooddelivery.Agents.DeliveryAgent", null);
        AgentController restaurant1 = mainContainer.createNewAgent("Restaurant1", "fooddelivery.Agents.RestaurantAgent", new Object[]{"Restaurant1"});
        AgentController restaurant2 = mainContainer.createNewAgent("Restaurant2", "fooddelivery.Agents.RestaurantAgent", new Object[]{"Restaurant2"});
        AgentController restaurant3 = mainContainer.createNewAgent("Restaurant3", "fooddelivery.Agents.RestaurantAgent", new Object[]{"Restaurant3"});

        customer.start();
        delivery.start();
        restaurant1.start();
        restaurant2.start();
        restaurant3.start();

        mainContainer.start();
    }
}