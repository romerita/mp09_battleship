package com.company.UDP;

import com.company.Board;
import com.company.Move;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {

    private int destPort;
    private String name, ipServer;
    private InetAddress destAddr;
    boolean matchState = true;
    int numPlayer;

    Board board;
    Move move = new Move();
    Scanner scanner = new Scanner(System.in);

    public Client(String ip, int port) {
        this.destPort = port;
        ipServer = ip;

        try {
            destAddr = InetAddress.getByName(ipServer);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void setName(String n) {
        name =n;
    }

    public void runClient() throws IOException {
        byte [] receivedData = new byte[1024];

        System.out.println("Número jugador (1 o 2):");
        numPlayer = scanner.nextInt();

        System.out.println("Hola " + name + "! Comenzamos! ");
        do {
            System.out.println("Indica la posición X y la posición Y: ");
            int posX = scanner.nextInt();
            int posY = scanner.nextInt();

            move.setNumPlayer(numPlayer);
            move.setX(posX);
            move.setY(posY);

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = null;
            try {
                objectOutputStream = new ObjectOutputStream(os);
                objectOutputStream.writeObject(move);
            } catch (IOException e) {
                e.printStackTrace();
            }

            byte[] message = os.toByteArray();

            DatagramPacket packet = new DatagramPacket(message,message.length, destAddr, destPort); // Se crea el paquete a enviar
            DatagramSocket socket = new DatagramSocket(); // Se crea un socket temporal con el que realiza el envío
            System.out.println("Sending...");
            System.out.println();
            socket.send(packet);

            packet = new DatagramPacket(receivedData, 1024); // Se crea un paquete para recibir la información
            socket.setSoTimeout(5000); // Tiempo de espera

            ByteArrayInputStream in = new ByteArrayInputStream(packet.getData());
            try {
                socket.receive(packet);

                ObjectInputStream objectInputStream = new ObjectInputStream(in);
                board = (Board) objectInputStream.readObject();
                if (!board.availableTurn) {
                    System.out.println("Esperando a tu oponente...");
                } else {
                    matchState = board.gameState();
                    board.printBoard();
                }
            } catch (SocketTimeoutException e) {
                System.out.println("No hay respuesta del servidor: " + e.getMessage());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            socket.close();
        } while (board.gameState());
    }

    public static void main(String[] args) {
        String player, ipServer;

        System.out.println("IP del servidor");
        Scanner sc = new Scanner(System.in);
        ipServer = sc.next();
        System.out.println("Nombre del jugador:");
        player = sc.next();

        Client client = new Client(ipServer, 5556);

        client.setName(player);
        try {
            client.runClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("VICTORIA !!");
    }
}
