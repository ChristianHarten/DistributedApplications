package tcp.counter;

public class TCPCounterClient
{
    public static void main(String[] args)
    {
        if (args.length != 2)
        {
            System.out.println("server-name count");
            return;
        }
        // create Socket connection
        try (TCPSocket tcpSocket = new TCPSocket(args[0], 1250))
        {
            System.out.println("setting counter to zero");
            tcpSocket.sendLine("reset");
            String reply = tcpSocket.receiveLine();
            System.out.printf("counter = %s \n", reply);

            int count = Integer.parseInt(args[1]);
            long startTime = System.currentTimeMillis();

            for (int i = 0; i < count; i++)
            {
                tcpSocket.sendLine("increment");
                reply = tcpSocket.receiveLine();
            }

            long stopTime = System.currentTimeMillis();
            long duration = stopTime - startTime;
            System.out.printf("elapsed time = %d msecs \n", duration);

            if (count > 0)
            {
                System.out.printf("average ping = %f msecs \n", ((duration) / (float) count));
            }
            System.out.printf("counter = %s \n", reply);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        System.out.println("TCP Connection closed.");
    }
}
