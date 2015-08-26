package nl.oce.ownhealth;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPSender {

    private String address = "0.0.0.0";
    private int port = 5000;
    private DatagramSocket client_socket = null;
    private InetAddress ipAddress = null;
    private byte[] sendData = new byte[1024];


    public UDPSender(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public void sendData(String sendObject) {
        try {
            client_socket = new DatagramSocket(5000);
            ipAddress = InetAddress.getByName(address);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        sendData = sendObject.getBytes();

        DatagramPacket sendPacket = new DatagramPacket(sendData, sendObject.length(), ipAddress, 5000);
        try {
            client_socket.send(sendPacket);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        client_socket.close();
    }
}