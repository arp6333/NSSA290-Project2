import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A socket server in TCP protocol
 */
public class TcpServer implements Runnable {
    private ServerSocket listener;
    private List<ClientThread> clients;

    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * Initializes the TCP Server
     * @param port the port to listen on
     * @throws IOException if the server cannot listen to given port
     */
    public TcpServer(int port) throws IOException {
        listener = new ServerSocket(port);
        clients = new ArrayList<>();
    }

    @Override
    public void run() {
        System.out.println("TCP Server started on port: " + listener.getLocalPort());
        System.out.println("External IP: " + Main.getExternalIp());
        while(true) {
            try {
                // Get connected socket and start handling it in a new thread
                Socket socket = listener.accept();
                System.out.printf("[%s] Connected from %s\n", formatter.format(new Date()), socket.getInetAddress());
                ClientThread client = new ClientThread(socket);
                client.start();
                clients.add(client);
            } catch (IOException e) {
                // Unable to handle client
                System.out.println("Unable to start client: " + e.toString());
            }
        }
    }

    /**
     * A class to handle client connections in a new thread
     */
    class ClientThread  extends Thread implements Runnable {
        private Socket socket;
        private BufferedReader reader;
        private PrintWriter writer;

        /**
         * Constructs a new ClientThread with a socket
         * @param socket the client socket
         */
        public ClientThread(Socket socket) throws IOException {
            this.socket = socket;
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new PrintWriter(socket.getOutputStream());
        }

        /**
         * Writes the message to the client
         * @param message the message to send
         */
        public void write(String message) {
            writer.println(message.trim());
            writer.flush();
        }

        public void run() {
            try {
                String message;
                // Continuously receive the message and broadcast to connected clients
                while((message = this.reader.readLine()) != null) {
                    System.out.printf("[%s] %s: %s\n", formatter.format(new Date()), socket.getInetAddress(), message);
                    write(message);
                }
            } catch (IOException e) {
                clients.remove(this);
            }
        }
    }
}
