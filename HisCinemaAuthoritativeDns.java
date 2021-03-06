import java.io.*;
import java.net.*;
import java.util.Scanner;

public class HisCinemaAuthoritativeDns {
private static final int PORT = 40293;

private static DatagramPacket receivePacket;
private static DatagramSocket serverSocket;

public static void main(String[] args) throws Exception {
        new HisCinemaAuthoritativeDns().runUDPServer();
}

public void runUDPServer() throws Exception {
        serverSocket = new DatagramSocket(PORT);

        //serverSocket.setSoTimeout(60000); // Set timeout for server of 1 minute
        System.out.println("HisCinema Authoritative Server up and ready...");

        while(true) {
                String filePath = "src/HisCinemaFiles/records.txt";
                File file = new File(filePath);
                Scanner scan = new Scanner(file);

                String message = receiveData().trim();

                while(scan.hasNext()) {
                        System.out.println("Request: " + message);
                        String line = scan.nextLine().toString();
                        if(line.contains(message)) {
                                sendData(line);
                                System.out.print("Reply: " + line + "\n");
                                //System.out.println("File sent successfully to: " + serverSocket.getInetAddress());
                        }
                }
                scan.close();
        }
}

public String receiveData() throws Exception {
        String query;
        byte[] receiveData = new byte[1024];

        receivePacket =
                new DatagramPacket(receiveData, receiveData.length);
        serverSocket.receive(receivePacket);

        query = new String(receivePacket.getData());
        return query;
}

public void sendData(String message) throws Exception {
        byte[] sendData = new byte[1024];
        InetAddress IPAddress = receivePacket.getAddress();
        int port = receivePacket.getPort();

        sendData = message.getBytes();

        DatagramPacket sendPacket =
                new DatagramPacket(sendData, sendData.length, IPAddress, port);
        serverSocket.send(sendPacket);
}
}
