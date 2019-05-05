package com.company.UDP;

import com.company.Board;
import com.company.Move;
import com.company.Ship;
import java.io.*;
import java.net.*;

public class Server {

    DatagramSocket socket;
    int port;
    int turn = -1;

    Board board, board2;
    Ship ship;

    public Server(int port) {
        try {
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        this.port = port;
        ship = new Ship();
        board = new Board(5, '-', 'O');
        board2 = new Board(5, '-', 'O');
    }

    public void runServer() throws IOException{
        byte [] receivingData = new byte[1024];
        byte [] sendingData;
        InetAddress clientIP;
        int clientPort;

        ship.printShip(board, board2);

        while(ship.defeat){

            DatagramPacket packet = new DatagramPacket(receivingData, 1024); // Paquete creado para recibir la información
            socket.receive(packet);
            sendingData = processData(packet.getData(), packet.getLength()); // Información recibida i obtención de la respuesta
            clientIP = packet.getAddress(); // Obtenemos dirección del cliente
            clientPort = packet.getPort(); // Obtenemos el puerto del cliente
            packet = new DatagramPacket(sendingData, sendingData.length,
                    clientIP, clientPort); // Paquete creado para enviar la respuesta
            System.out.println("Sending...");
            socket.send(packet);
        }
        socket.close();
    }

    private byte[] processData(byte[] data, int length) {
        Move move = null;
        ByteArrayInputStream in = new ByteArrayInputStream(data);

        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(in);
            move = (Move) objectInputStream.readObject();

            if(turn == -1 || turn == move.numPlayer) {
                if (move.numPlayer == 1) {
                    ship.shoot(board2, move.x, move.y);
                    turn = 2;
                    board2.availableTurn = true;
                } else {
                    ship.shoot(board, move.x, move.y);
                    turn = 1;
                    board.availableTurn = true;
                }
            } else {
                if(move.numPlayer == 1) {
                    board2.availableTurn = false;
                } else {
                    board.availableTurn = false;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(outputStream);
            if(move.numPlayer == 1) {
                objectOutputStream.writeObject(board2);
            } else {
                objectOutputStream.writeObject(board);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] reply = outputStream.toByteArray();
        return reply;
    }

    public static void main(String[] args) {
        Server server = new Server(5556);

        try {
            server.runServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
