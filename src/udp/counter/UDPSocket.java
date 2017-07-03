package udp.counter;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Created by eschs on 03.07.2017.
 */
public class UDPSocket implements AutoCloseable
{
    protected DatagramSocket socket;
    private InetAddress address;
    private int port;

    public UDPSocket() throws SocketException
    {
        this(new DatagramSocket());
    }

    public UDPSocket(int port) throws SocketException
    {
        this(new DatagramSocket(port));
    }

    protected UDPSocket(DatagramSocket socket)
    {
        this.socket = socket;
    }

    public void send(String s, InetAddress address, int port) throws IOException
    {
        byte[] outBuffer = s.getBytes();
        DatagramPacket packet = new DatagramPacket(outBuffer, outBuffer.length, address, port);
        socket.send(packet);
    }

    public String receive(int maxBytes) throws IOException
    {
        byte[] inBuffer = new byte[maxBytes];
        DatagramPacket packet = new DatagramPacket(inBuffer, inBuffer.length);
        socket.receive(packet);
        address = packet.getAddress();
        port = packet.getPort();

        return new String(inBuffer, 0, packet.getLength());
    }

    public void reply(String s) throws IOException
    {
        if (address == null)
        {
            throw new IOException("no on to reply");
        }
        send(s, address, port);
    }

    public void setTimeOut (int timeOut) throws SocketException
    {
        socket.setSoTimeout(timeOut);
    }

    public InetAddress getSenderAddress ()
    {
        return this.address;
    }

    public int getSenderPort ()
    {
        return this.port;
    }

    @Override public void close() throws Exception
    {
        socket.close();
    }
}
