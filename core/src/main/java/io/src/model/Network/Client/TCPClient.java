package io.src.model.Network.Client;

import java.io.*;
import java.net.Socket;

public class TCPClient {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public void connect(String host, int port) throws IOException {
        socket = new Socket(host, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    public void send(String json) {
        out.println(json);
    }

    public String receive() throws IOException {
        return in.readLine();
    }

    public void close() throws IOException {
        socket.close();
    }
}
