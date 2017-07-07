package tcp.counter;

import java.io.*;
import java.net.Socket;

public class TCPSocket implements AutoCloseable
{
    private Socket socket;
    private BufferedReader inStream;
    private BufferedWriter outStream;

    TCPSocket(String serverAddress, int serverPort) throws IOException
    {
        this.socket = new Socket(serverAddress, serverPort);
        initStreams();
    }

    TCPSocket(Socket socket) throws IOException
    {
        this.socket = socket;
        initStreams();
    }

    private void initStreams() throws IOException
    {
        inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outStream = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    void sendLine(String s) throws IOException
    {
        outStream.write(s);
        outStream.newLine();
        outStream.flush();
    }

    String receiveLine() throws IOException
    {
        return inStream.readLine();
    }

    String getAddress()
    {
        return String.valueOf(socket.getInetAddress());
    }

    int getPort()
    {
        return socket.getPort();
    }

    @Override public void close() throws Exception
    {
        socket.close();
    }
}
