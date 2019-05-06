import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UdpServer implements Runnable {
    private DatagramSocket listener;

    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * Initializes the UDP server
     * @param port the port to listen on
     * @throws SocketException if server cannot bind the given port
     */
    public UdpServer(int port) throws SocketException {
        listener = new DatagramSocket(port);
    }

    @Override
    public void run() {
        System.out.println("UDP Server started on port: " + listener.getLocalPort());
        System.out.println("External IP: " + Main.getExternalIp());
        while (true) {
            try {
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, 0, buffer.length);
                // Receive the packet
                listener.receive(packet);
                byte[] messageBuffer = packet.getData();
                System.out.printf("[%s] %s:%s - %s\n", formatter.format(new Date()), packet.getAddress(), packet.getPort(), new String(messageBuffer, packet.getOffset(), packet.getLength()));
                // Construct packet for echo
                DatagramPacket output = new DatagramPacket(messageBuffer, messageBuffer.length, packet.getAddress(), packet.getPort());
                // echo back to client
                listener.send(output);
            } catch (IOException e) {
                System.out.println(e.toString());
            }
        }
    }
}
