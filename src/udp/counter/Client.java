package udp.counter;

import java.net.InetAddress;
/**
 * Created by eschs on 03.07.2017.
 */
public class Client
{
    private static int TIMEOUT = 2000;

    public static void main(String[] args)
    {
        if (args.length != 3)
        {
            System.out.println("server-name server-port count");
            return;
        }
        int port = Integer.parseInt(args[1]);
        try (UDPSocket socket = new UDPSocket())
        {
            socket.setTimeOut(TIMEOUT);

            InetAddress server = InetAddress.getByName(args[0]);

            System.out.println("setting counter to zero");
            socket.send("reset", server, port);
            String reply = null;
            try
            {
                reply = socket.receive(20);
                System.out.format("counter = %s", reply);
                System.out.println();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            System.out.println("starting increment operation(s)");
            int count = Integer.parseInt(args[2]);
            long startTime = System.currentTimeMillis();

            for (int i = 0; i < count; i++)
            {
                socket.send("increment", server, port);
                try
                {
                    reply = socket.receive(20);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            System.out.format("counter = %s (after increment)", reply);
            System.out.println();

            System.out.println("setting counter to 23");
            socket.send("set 23", server, port);
            try
            {
                reply = socket.receive(20);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            long stopTime = System.currentTimeMillis();
            long duration = stopTime - startTime;
            System.out.format("elapsed time = %d msecs", duration);
            System.out.println();

            if (count > 0)
            {
                System.out.format("average ping = %f msecs", (duration / (float) count));
                System.out.println();
            }
            System.out.format("counter = %s", reply);
            System.out.println();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        System.out.println("socket closed");
    }
}
