package udp.counter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;

public class InteractiveClient
{
    private static int TIMEOUT = 2000;

    public static void main (String[] args)
    {
        if (args.length != 2)
        {
            System.out.println("server-name server-port");
            return;
        }

        int port = Integer.parseInt(args[1]);
        try (UDPSocket socket = new UDPSocket())
        {
            socket.setTimeOut(TIMEOUT);

            InetAddress server = InetAddress.getByName(args[0]);

            System.out.println("Methode eingeben");
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            String command;
            while (!(command = input.readLine()).equals("exit"))
            {
                socket.send(command, server, port);
                try
                {
                    String reply = socket.receive(20);
                    System.out.format("counter = %s", reply);
                }
                catch (Exception e)
                {
                    System.out.println("Fehler beim empfangen der Antwort");
                    e.printStackTrace();
                }
                System.out.println();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
