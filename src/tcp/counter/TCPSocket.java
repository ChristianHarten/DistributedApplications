package tcp.counter;

import java.io.*;
import java.net.Socket;

public class TCPSocket implements AutoCloseable
{
    private Socket socket;
    private DataInputStream inStream;
    private DataOutputStream outStream;

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
        /*inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outStream = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));*/

        inStream = new DataInputStream(socket.getInputStream());
        outStream = new DataOutputStream(socket.getOutputStream());
    }

    void sendLine(int msg) throws IOException
    {
        /*outStream.write(s);
        outStream.newLine();
        outStream.flush();*/

        outStream.writeInt(msg);
        outStream.flush();
    }

    int receiveLine() throws IOException
    {
        //return inStream.readLine();
        return inStream.readInt();
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
