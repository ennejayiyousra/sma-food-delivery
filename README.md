# 🍔 Food Delivery Platform – Multi-Agent System with JADE

The **Food Delivery Platform** is a **multi-agent system** application that simulates and manages a food ordering and delivery process using the **JADE (Java Agent DEvelopment Framework)**. This innovative platform leverages **agent-based technology** to coordinate interactions between **customers**, **delivery services**, and **restaurants**—all in a distributed environment designed to reflect real-world logistics and decision-making.

---

## 🚀 Key Features

- **🔗 Multi-Agent System** – Built with JADE, the system consists of several intelligent agents:
  - **Customer Agent**: Initiates food orders and receives delivery confirmations.
  - **Delivery Agent**: Gathers offers from restaurants, selects the best based on price, and coordinates delivery.
  - **Restaurant Agents**: Simulate restaurants by responding with randomized price and delivery time offers.

- **⚙️ Dynamic Order Processing** – Automatically collects restaurant proposals and picks the best one within a **15-second decision window** to ensure timely processing.

- **📡 Real-Time Updates** – A user-friendly GUI displays live updates of the order flow, from dish request to delivery confirmation.

- **📈 Scalable Design** – Easily add more restaurant agents to simulate a larger, more competitive market.

---

## 🧠 How It Works

1. **User Input** – The user starts the app and enters a dish name (e.g., *Pizza Margherita*) via the GUI.
2. **Customer Request** – The **Customer Agent** sends the dish request to the **Delivery Agent**.
3. **Restaurant Bidding** – The **Delivery Agent** contacts all available **Restaurant Agents**, which respond with their own price and delivery estimates.
4. **Offer Selection** – The **best offer** is selected based on the lowest price (within 15 seconds).
5. **Confirmation** – The selected restaurant is notified, and the **Customer Agent** is informed.
6. **GUI Update** – The graphical interface shows the selected restaurant, price, and order confirmation.

---

## 🛠 Technical Details

- **Language**: Java (JDK 24)
- **Framework**: [JADE 4.6.0](https://jade.tilab.com/)
- **IDE**: Developed using **IntelliJ IDEA**
- **Structure**: Organized under the `fooddelivery` package
- **Execution**: 
  - Can be run via the **command line** using JADE boot commands
  - Can be packaged into a **JAR file** for distribution
  - Future potential for integration into **web** or **mobile platforms**

---

## 📂 Project Structure (Overview)

```
fooddelivery/
├── Containers/
│   └── MainContainer.java
├── Agents/
│   ├── CustomerAgent.java
│   ├── DeliveryAgent.java
│   └── RestaurantAgent.java
├── Behaviors/
│   ├── OrderRequestBehavior.java
│   ├── ProposalHandlingBehavior.java
│   └── RestaurantProposalBehavior.java
├── Views/
│   └── OrderGUI.java
```

---

## ▶️ How to Run

1. **Clone the Repository**
   ```bash
   git clone https://github.com/your-username/food-delivery-app.git
   cd food-delivery-app
   ```

2. **Add JADE to Classpath**  
   Ensure JADE `.jar` files are in the `/lib` folder and added to your IDE or command-line classpath.

3. **Compile the Project**  
   Use IntelliJ or `javac` to compile the source files.

4. **Launch the Application**  
   Run the main container with:
   ```bash
   java -cp lib/jade.jar:./out/production/food-delivery-app jade.Boot -gui
   ```

5. **Add Agents**  
   Launch agents via GUI or JADE console:
   ```
   CustomerAgent:fooddelivery.agents.CustomerAgent
   DeliveryAgent:fooddelivery.agents.DeliveryAgent
   RestaurantAgent1:fooddelivery.agents.RestaurantAgent
   RestaurantAgent2:fooddelivery.agents.RestaurantAgent
   ...
   ```

---

## 📌 Future Improvements

- Integration with **real-time restaurant APIs**
- Expansion into **mobile applications**
- Incorporation of **user profiles and authentication**
- Enhanced decision-making using **machine learning** techniques

---

## 📄 License

This project is released under the **MIT License**.  
Feel free to use, modify, and contribute!
