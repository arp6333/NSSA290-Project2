import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while(true) {
            System.out.println("What server would you like to run? (TCP or UDP)");
            System.out.print("Input: ");
            String choice = scanner.next().toLowerCase();
            if (choice.equals("tcp")) {
                int port = requestPort();
                startTcpServer(port);
            } else if (choice.equals("udp")) {
                int port = requestPort();
                startUdpServer(port);
            } else {
                System.out.print("Invalid input");
            }
        }
    }

    private static int requestPort() {
        System.out.print("What port would you like to use? ");
        return scanner.nextInt();
    }

    private static void startTcpServer(int port) {
        try {
            TcpServer server = new TcpServer(port);
            server.run(); // run synchronously?
        } catch (IOException e) {
            System.out.println("Unable to run the TCP server");
        }
    }

    private static void startUdpServer(int port) {
        try {
            UdpServer server = new UdpServer(port);
            server.run();
        } catch (IOException e) {
            System.out.println("Unable to run the UDP server");
        }
    }

    public static String getExternalIp() {
        String awsUrl = "http://checkip.amazonaws.com";
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(awsUrl).openStream()));
            return reader.readLine();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "127.0.0.1";
    }
}
