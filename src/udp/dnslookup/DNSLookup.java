package udp.dnslookup;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class DNSLookup
{
    public static void main(String[] args)
    {
        if (args.length == 0)
        {
            System.out.println("Mindestens ein Argument angeben!");
            return;
        }
        for (String host : args)
        {
            InetAddress[] ipAdresses;
            try
            {
                ipAdresses = InetAddress.getAllByName(host);
            }
            catch (UnknownHostException e)
            {
                System.out.format("Konnte Host %s nicht auflösen", host);
                System.out.println("-----------------------");
                continue;
            }
            for (InetAddress ip : ipAdresses)
            {
                System.out.println(host + ": " + ip.toString());
            }
            System.out.println("-----------------------");
        }
    }
}
