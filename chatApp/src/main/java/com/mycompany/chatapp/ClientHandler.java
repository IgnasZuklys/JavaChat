package com.mycompany.chatapp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket socket;
    private DataInputStream din;
    private DataOutputStream dout;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        try {
            din = new DataInputStream(socket.getInputStream());
            dout = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String msgin = "";
        try {
            while (!msgin.equals("exit")) {
                msgin = din.readUTF();
                server.broadcastMessage("User: " + msgin);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                din.close();
                dout.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String message) {
        try {
            dout.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
