package udp.counter;

import java.net.DatagramSocket;

/**
 * Created by eschs on 03.07.2017.
 */
public class Server
{
    private static int counter = 0;

    public static void main(String[] args)
    {
        if (args.length != 1)
        {
            System.out.println("port");
            return;
        }
        int port = Integer.parseInt(args[0]);
        try (DatagramSocket socket = new DatagramSocket(port))
        {
            runServer(socket);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        System.out.println("socket closed");
    }

    public static void runServer(DatagramSocket socket)
    {
        try (UDPSocket udpSocket = new UDPSocket(socket))
        {
            System.out.println("waiting for client requests");
            while (true)
            {
                String request = udpSocket.receive(20);
                switch (request)
                {
                    case "increment":
                        counter++;
                        System.out.format("Counter incremented by %s: %d", udpSocket.getSenderAddress(), udpSocket.getSenderPort());
                        break;
                    case "decrement":
                        counter--;
                        System.out.format("Counter decremented by %s: %d", udpSocket.getSenderAddress(), udpSocket.getSenderPort());
                        break;
                    case "reset":
                        counter = 0;
                        System.out.format("Counter resetted by %s: %d", udpSocket.getSenderAddress(), udpSocket.getSenderPort());
                        break;
                    default:
                        if (request.matches("set -?[0-9]*"))
                        {
                            counter = Integer.parseInt(request.substring(4));
                            System.out.format("Counter set by %s: %d", udpSocket.getSenderAddress(), udpSocket.getSenderPort());
                        }
                        break;
                }
                System.out.println();
                String answer = String.valueOf(counter);
                udpSocket.reply(answer);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
