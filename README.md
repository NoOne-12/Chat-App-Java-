

Java Chat Application (GUI + Sockets + Collections + Multithreading)

A simple yet powerful client–server chat application built in Java using:

- Swing GUI for the user interface  
- Sockets for network communication  
- Multithreading for handling multiple clients  
- Collections Framework for managing active users and message broadcasting  

This project demonstrates core concepts of network programming, concurrent processing, and GUI development in Java.

---

🚀 Features

Client
- Clean and responsive Swing-based GUI
- Connects to server using TCP sockets
- Sends and receives messages in real time
- Displays chat history
- Notifies when users join or leave

Server
- Handles multiple clients using multithreading
- Uses Collections (e.g., HashMap, ArrayList) to store:
  - Active client sockets  
  - Usernames  
  - Message logs  
- Broadcasts messages to all connected clients
- Gracefully handles client disconnections

---

🧰 Technologies Used

| Component | Technology |
|----------|------------|
| Programming Language | Java (JDK 8+) |
| GUI Framework | Swing |
| Networking | Java Sockets (TCP) |
| Concurrency | Threads / Runnable |
| Data Structures | Collections Framework |

---

⚙️ How It Works

1. Server
- Starts a ServerSocket on a specified port  
- Waits for incoming client connections  
- For each client:
  - Creates a new ClientHandler thread
  - Stores the client in a Collection
  - Listens for messages and broadcasts them

2. Client
- Connects to server using Socket
- GUI runs on the Event Dispatch Thread (EDT)
- A background thread listens for incoming messages
- Messages typed in the GUI are sent to the server

---

🖥️ Running the Application

1. Start the Server
`bash
javac Server.java
java Server
`

2. Start a Client
`bash
javac Client.java
java Client
`

You can open multiple clients to simulate a group chat.

---

🧵 Multithreading Overview

The project uses threads for:

- Server main thread → Accepts new clients  
- ClientHandler threads → One per connected client  
- Client listener thread → Receives messages without freezing GUI  
- Swing EDT → Handles all GUI updates  

This ensures smooth, non-blocking communication.

---

📚 Collections Used

- HashMap<String, Socket> → Track connected users  
- ArrayList<ClientHandler> → Manage active client threads  
- LinkedList<String> → Store chat history  
- HashSet<String> → Prevent duplicate usernames  

---

🧪 Future Improvements

- Private messaging (DMs)  
- File sharing  
- User authentication  
- Better GUI styling (JavaFX or custom themes)  
- Database integration for message history  

---

🤝 Contributing

Pull requests are welcome!  
If you’d like to improve the GUI, optimize threading, or add new features, feel free to contribute.

---

📄 License

This project is open-source and available under the MIT License.

