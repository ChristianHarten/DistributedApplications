package tcp.counter;

public class TCPCounterClient
{
    /* Wird der Server abgeschossen, während Anfragen laufen, bekommt der Client
    * eine SocketException (socket write error). Wird der Server aber abgeschossen
    * wenn alle Anfragen durch sind, gibt es keine Probleme auf Clientseite. Dann
    * wird einfach der Code weiter ausgeführt.*/
    public static void main(String[] args)
    {
        if (args.length != 3)
        {
            System.out.println("server-name server-port count");
            return;
        }
        int port = Integer.parseInt(args[1]);
        // create Socket connection
        try (TCPSocket tcpSocket = new TCPSocket(args[0], port))
        {
            System.out.println("setting counter to zero");
            tcpSocket.sendLine("reset");
            String reply = tcpSocket.receiveLine();
            System.out.printf("counter = %s \n", reply);

            int count = Integer.parseInt(args[2]);
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
            System.out.println("Cannot establish TCP Connection.");
            e.printStackTrace();
        }
        System.out.println("TCP Connection closed.");
    }
}
