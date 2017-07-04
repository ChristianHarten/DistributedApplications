package udp.counter;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPSocket implements AutoCloseable
{
    private DatagramSocket socket;
    private InetAddress address;
    private int port;

    UDPSocket() throws SocketException
    {
        this(new DatagramSocket());
    }

    public UDPSocket(int port) throws SocketException
    {
        this(new DatagramSocket(port));
    }

    UDPSocket(DatagramSocket socket)
    {
        this.socket = socket;
    }

    void send(String s, InetAddress address, int port) throws IOException
    {
        byte[] outBuffer = s.getBytes();
        DatagramPacket packet = new DatagramPacket(outBuffer, outBuffer.length, address, port);
        socket.send(packet);
    }

    String receive(int maxBytes) throws IOException
    {
        byte[] inBuffer = new byte[maxBytes];
        DatagramPacket packet = new DatagramPacket(inBuffer, inBuffer.length);
        socket.receive(packet);
        address = packet.getAddress();
        port = packet.getPort();

        return new String(inBuffer, 0, packet.getLength());
    }

    void reply(String s) throws IOException
    {
        if (address == null)
        {
            throw new IOException("no on to reply");
        }
        send(s, address, port);
    }

    void setTimeOut(int timeOut) throws SocketException
    {
        socket.setSoTimeout(timeOut);
    }

    InetAddress getSenderAddress()
    {
        return this.address;
    }

    int getSenderPort()
    {
        return this.port;
    }

    @Override public void close() throws Exception
    {
        socket.close();
    }
}
