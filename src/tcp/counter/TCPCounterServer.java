package tcp.counter;

import java.io.IOException;
import java.net.ServerSocket;

public class TCPCounterServer
{
    @SuppressWarnings("InfiniteLoopStatement") public static void main(String[] args)
    {
        int counter = 0;

        //create Socket
        try (ServerSocket serverSocket = new ServerSocket(1250))
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
                        String request = tcpSocket.receiveLine();
                        if (request != null)
                        {
                            if (request.equals("increment"))
                            {
                                counter++;
                                System.out.printf("counter has been incremented by %s, %d \n", tcpSocket.getAddress(), tcpSocket.getPort());
                            }
                            else if (request.equals("reset"))
                            {
                                counter = 0;
                                System.out.printf("counter has been resetted by %s, %d \n", tcpSocket.getAddress(), tcpSocket.getPort());
                            }
                            tcpSocket.sendLine(String.valueOf(counter));
                        }
                        else
                        {
                            System.out.printf("closing connection to %s, %d \n", tcpSocket.getAddress(), tcpSocket.getPort());
                            break;
                        }
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        catch (IOException e)
        {
            System.out.println("Unable to create TCPCounterServer Socket");
            e.printStackTrace();
        }
    }
}
