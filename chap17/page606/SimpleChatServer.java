package advanced_java.chap17.page606;


import java.io.*;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.*;
import java.util.concurrent.*;
import static java.nio.charset.StandardCharsets.UTF_8;


public class SimpleChatServer {
    // List to store all connected client output streams (to send messages to everyone)
    private final List<PrintWriter> clientWriters = new ArrayList<>();

    public static void main(String[] args) {
        // Start the chat server
        new SimpleChatServer().go();
    }

    public void go() {
        // Use a thread pool to handle multiple clients
        ExecutorService threadPool = Executors.newCachedThreadPool();

        try {
            // Create a non-blocking server socket channel on port 5000
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(5000));

            // Keep accepting new clients
            while (serverSocketChannel.isOpen()) {
                // Wait for a client to connect
                SocketChannel clientSocket = serverSocketChannel.accept();

                // Create a PrintWriter to send messages to the client
                PrintWriter writer = new PrintWriter(Channels.newWriter(clientSocket, UTF_8));
                clientWriters.add(writer); // Add to list so we can broadcast later

                // Start a new thread to handle incoming messages from this client
                threadPool.submit(new ClientHandler(clientSocket));

                System.out.println("Got a connection");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Send a message to all connected clients
    private void tellEveryone(String message) {
        for (PrintWriter writer : clientWriters) {
            writer.println(message);
            writer.flush();
        }
    }

    // Inner class that handles one connected client
    public class ClientHandler implements Runnable {
        BufferedReader reader;
        SocketChannel socket;

        public ClientHandler(SocketChannel clientSocket) {
            socket = clientSocket;
            reader = new BufferedReader(Channels.newReader(socket, UTF_8));
        }

        public void run() {
            String message;
            try {
                // Keep reading messages from this client
                while ((message = reader.readLine()) != null) {
                    System.out.println("Read: " + message);
                    tellEveryone(message); // Send message to all clients
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
