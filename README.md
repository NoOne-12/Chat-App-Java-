
Java Group Chat Application (GUI + Sockets + Collections + Multithreading)

A simple yet powerful group chat system built in Java, where a central server manages all communication and multiple clients can join the same shared chatroom. The application uses:

- Swing GUI for the user interface  
- Sockets for real‑time network communication  
- Multithreading to handle multiple clients simultaneously  
- Collections Framework to manage connected users and broadcast messages  

This project demonstrates core concepts of network programming, concurrency, and GUI development in Java.

---

🚀 Features

Client
- Clean and responsive Swing-based GUI
- Connects to a central chat server using TCP sockets
- Supports real-time group chatting
- Displays chat history
- Notifies when users join or leave the chatroom
- Sends and receives messages instantly

Server
- Acts as the central communication hub
- Handles multiple clients using multithreading
- Uses Collections (e.g., HashMap, ArrayList) to store:
  - Active client sockets  
  - Usernames  
  - Message logs  
- Broadcasts every message to all connected clients
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

1. Central Server
- Starts a ServerSocket on a specified port  
- Waits for incoming client connections  
- For each new client:
  - Creates a dedicated ClientHandler thread
  - Registers the client in a Collection
  - Listens for messages and broadcasts them to all clients

The server never sends private messages — it maintains a shared group chat environment.

2. Client
- Connects to the central server using a TCP Socket
- GUI runs on the Event Dispatch Thread (EDT)
- A background thread listens for incoming messages
- Messages typed in the GUI are sent to the server and broadcast to everyone

---

🖥️ Running the Application

1. Start the Server
`bash
javac Server.java
java Server 4800
`

2. Start a Client
`bash
javac Client.java
java Client
`

Open multiple clients to join the same group chatroom.

---

🧵 Multithreading Overview

The project uses threads for:

- Server main thread → Accepts new clients  
- ClientHandler threads → One per connected client  
- Client listener thread → Receives messages without freezing the GUI  
- Swing EDT → Handles all GUI updates  

This ensures smooth, responsive, and non-blocking communication.

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

Pull requests are welcome.  
If you’d like to improve the GUI, optimize threading, or add new features, feel free to contribute.

---

📄 License

This project is open-source and available under the MIT License.
