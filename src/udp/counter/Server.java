package udp.counter;

import java.net.DatagramSocket;

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

    @SuppressWarnings("InfiniteLoopStatement") private static void runServer(DatagramSocket socket)
    {
        try (UDPSocket udpSocket = new UDPSocket(socket))
        {
            System.out.println("waiting for client requests");
            boolean nextIsSetValue = true;
            while (true)
            {
                int request = udpSocket.receive(Integer.BYTES);
                switch (request)
                {
                    // reset
                    case 0:
                        counter = 0;
                        System.out.format("Counter resetted by %s: %d", udpSocket.getSenderAddress(), udpSocket.getSenderPort());
                        break;
                    // increment
                    case 1:
                        counter++;
                        System.out.format("Counter incremented by %s: %d", udpSocket.getSenderAddress(), udpSocket.getSenderPort());
                        break;
                    // decrement
                    case 2:
                        counter--;
                        System.out.format("Counter decremented by %s: %d", udpSocket.getSenderAddress(), udpSocket.getSenderPort());
                        break;
                    // set
                    case 3:
                        nextIsSetValue = false;
                        int newValue = udpSocket.receive(Integer.BYTES);
                        counter = newValue;
                        System.out.format("Counter set by %s: %d", udpSocket.getSenderAddress(), udpSocket.getSenderPort());
                        break;
                    default:
                        System.out.println("WTF??" + request);
                        break;
                }
                System.out.println();
                int answer = counter;
                udpSocket.reply(answer);
            }

            /*while (true)
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
                        } // TODO else: tell client to use one of the previous methods
                        break;
                }
                System.out.println();
                String answer = String.valueOf(counter);
                udpSocket.reply(answer);
            }*/
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
