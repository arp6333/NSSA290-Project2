import java.io.*;
import java.net.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Scanner;

/**
 * NSSA290 - Homework 2
 * Ellie Parobek, Winston Chang, Isabella Sturm
 * Client side to a TCP and UDP messaging system.
 */
public class Client{
    private DatagramSocket udpSocket;
    private InetAddress serverAddress;
    private int port;
    private Scanner scanner;
    private static String conn;
    private Socket socket;
    private SimpleDateFormat dateFormat;
   
    /**
    * Main method, start up client then connect either TCP or UDP.
    */
    public static void main(String[] args) throws NumberFormatException, IOException{      
        Client sender = new Client();
        sender.Connection();
        // Get connection type
        if(conn.equals("UDP")){
            sender.udp();
        }
        else{
            sender.tcp();
        }
    }

    /**
     * Construct client.
     */
    private Client() throws IOException{
        udpSocket = null;
        serverAddress = null;
        port = 0;
        scanner = new Scanner(System.in);
        conn = "";
        socket = null;
        dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    }
    
    /**
     * Set and get variables from user input.
     */
    public void Connection() throws IOException{
        // Get IP / host name
        System.out.println("Enter server IP or host name: ");
        String destinationAddress = scanner.nextLine();
        // Get TCP or UDP
        System.out.println("TCP or UDP?: ");
        conn = scanner.nextLine();
        boolean bad = true;
        while(bad){
            if(conn.equals("")){
                System.out.println("Please enter only TCP or UDP: ");
                conn = scanner.nextLine();
            }
            else if(conn.charAt(0) == 'U' || conn.charAt(0) == 'u'){
                conn = "UDP";
                bad = false;
            }
            else if(conn.charAt(0) == 'T' || conn.charAt(0) == 't'){
                conn = "TCP";
                bad = false;
            }
            else{
                System.out.println("Please enter only TCP or UDP: ");
                conn = scanner.nextLine();
            }
        }
        // Get port
        System.out.println("Enter port number: ");
        port = scanner.nextInt();
        scanner.nextLine();
        // Set server address
        serverAddress = InetAddress.getByName(destinationAddress);
    }
    
    /**
     * Send and recieve messages over UDP.
     */
    private void udp() throws IOException{
        // Connect over UDP
	     Date date = new Date();
        System.out.println("Connecting to " + serverAddress + " with IP address " + InetAddress.getLocalHost() + " using " + conn + " on port " + port + " at " + dateFormat.format(date));
        udpSocket = new DatagramSocket();
        String in = "";
        while(true){
            // New scanner and get date
	         date = new Date();
            scanner = new Scanner(System.in);
            in = scanner.nextLine();
            if(!in.equals("")){
               if(in.equals("end")){
                  return;
               }
            }
            // Send message
            DatagramPacket p = new DatagramPacket(in.getBytes(), in.getBytes().length, serverAddress, port);
            udpSocket.send(p);
            // Recieve response
            p = new DatagramPacket(in.getBytes(), in.getBytes().length);
            udpSocket.receive(p);
            String received = new String(p.getData(), 0, p.getLength());
            System.out.println("[" + dateFormat.format(date) + "] " + received);        
        }
    }
    
    /**
     * Send and recieve messages over TCP.
     */
    private void tcp() throws IOException{
        // Connect over TCP
	     Date date = new Date();
        System.out.println("Connecting to " + serverAddress + " with IP address " + InetAddress.getLocalHost() + " using " + conn + " on port " + port + " at " + dateFormat.format(date));
        String in = "";
        socket = new Socket(serverAddress, port);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        while(true){
            // New scanner and get date
	         date = new Date();
            scanner = new Scanner(System.in);
            in = scanner.nextLine();
            if(!in.equals("")){
               if(in.equals("end")){
                  return;
               }
            }
            out.println(in);
            out.flush();
            String resp = br.readLine();
            System.out.println("[" + dateFormat.format(date) + "] " + resp);
        }
    }
}