package tcp.counter;

import java.io.IOException;
import java.net.ServerSocket;

public class TCPCounterServer
{
    private static int counter;

    public static void main(String[] args)
    {
        if (args.length != 1)
        {
            System.out.println("server-port");
            return;
        }
        int port = Integer.parseInt(args[0]);
        //create Socket
        try (ServerSocket serverSocket = new ServerSocket(port))
        {
            runServer(serverSocket);
        }
        catch (IOException e)
        {
            System.out.println("Unable to create TCPCounterServer Socket");
            e.printStackTrace();
        }
    }

    @SuppressWarnings("InfiniteLoopStatement") private static void runServer(ServerSocket serverSocket)
    {
        System.out.println("waiting for clients");
        while (true)
        {
            // create new TCP Socket for each client connection
            try (TCPSocket tcpSocket = new TCPSocket(serverSocket.accept()))
            {
                // execute client requests
                while (true)
                {
                    int request = tcpSocket.receiveLine();
                    if (request != -1)
                    {
                        switch (request)
                        {
                            case 0:
                                counter = 0;
                                System.out.printf("counter has been resetted by %s, %d \n", tcpSocket.getAddress(), tcpSocket.getPort());
                                break;
                            case 1:
                                counter++;
                                System.out.printf("counter has been incremented by %s, %d \n", tcpSocket.getAddress(), tcpSocket.getPort());
                                break;
                            case 2:
                                counter--;
                                System.out.printf("counter has been decremented by %s, %d \n", tcpSocket.getAddress(), tcpSocket.getPort());
                                break;
                            case 3:
                                System.out.printf("counter has been set to %d by %s, %d \n", counter, tcpSocket.getAddress(), tcpSocket.getPort());
                                break;
                            default:
                                System.out.println("use appropriate method");
                                break;
                        }
                        tcpSocket.sendLine(counter);
                    }
                    else
                    {
                        System.out.printf("closing connection to %s, %d \n", tcpSocket.getAddress(), tcpSocket.getPort());
                        break;
                    }
                    /*String request = tcpSocket.receiveLine();
                    if (request != null)
                    {
                        if (request.equals("increment"))
                        {
                            counter++;
                            System.out.printf("counter has been incremented by %s, %d \n", tcpSocket.getAddress(), tcpSocket.getPort());
                        }
                        else if (request.equals("decrement"))
                        {
                            counter--;
                            System.out.printf("counter has been decremented by %s, %d \n", tcpSocket.getAddress(), tcpSocket.getPort());
                        }
                        else if (request.matches("set -?[0-9]*"))
                        {
                            counter = Integer.parseInt(request.substring(4));
                            //System.out.printf("counter has been set to %d by %s, %d \n", counter, tcpSocket.getAddress(), tcpSocket.getPort());
                        }
                        else if (request.equals("reset"))
                        {
                            counter = 0;
                            System.out.printf("counter has been resetted by %s, %d \n", tcpSocket.getAddress(), tcpSocket.getPort());
                        }
                        else
                        {
                            System.out.println("unable to handle request. use appropriate method");
                        }
                        tcpSocket.sendLine(String.valueOf(counter));
                    }
                    else
                    {
                        System.out.printf("closing connection to %s, %d \n", tcpSocket.getAddress(), tcpSocket.getPort());
                        break;
                    }*/
                }
            }
            catch (Exception e)
            {
                System.out.println("connection closed because of error");
                e.printStackTrace();
            }
        }
    }
}
