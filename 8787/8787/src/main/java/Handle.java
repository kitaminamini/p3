
import org.apache.commons.codec.binary.StringUtils;

import javax.swing.text.JTextComponent;
import java.io.File;

import java.net.InetAddress;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Created by Don on 11/23/2016 AD.
 */
//source:
public class Handle {

    static String torrent_name = "default.torrent";
    static String m_check="M";
    static String tcp_port="1212";
    static String path;
    static String file_name;  //test_1.txt
    static String windowMachineName = System.getProperty("user.name").toUpperCase();
    static String macMachineName = System.getProperty("user.name");

    static ConcurrentSkipListSet<String> get_list() {
        return UDPserv.getrekt();
    }

    static String get_master_ip(String substringg) {
        ConcurrentSkipListSet<String> temp = UDPserv.getrekt();
        for (String x : temp) {
            if (x.startsWith(substringg)) {
                String masterIP = x.substring(substringg.length());
                return masterIP;
            }

        }
        return "";
    }

    public static void main(String[] args) throws Exception {

        Thread udpRegist = new Thread(new UDPregister());
        Thread udpRegistM = new Thread(new UDPregisterM(m_check));
        Thread serv = new Thread(new UDPserv(m_check));
        udpRegist.start();
        serv.start();

        System.out.println("mac or window?");
        Scanner a = new Scanner(System.in);
        String os_type = a.nextLine();

        System.out.println("<For master:enter file name>,<For client:type 'N' and press enter>");
        Scanner ujm = new Scanner(System.in);
        file_name = ujm.nextLine();

        if (os_type.equals("mac")) {
            path = "/Users/"+macMachineName+"/Desktop/";
        } else if (os_type.equals("window")) {
            path = "C:/Users/" + windowMachineName + "/Desktop/";
        }
        String type = ""; //either "client or master"
        boolean found_status = false;
        while (found_status == false) {
            File f = new File(path + file_name);
            if (f.exists()) {
                System.out.println("found file!!");
                System.out.println("Im a master!");
                found_status = true;
                type = "master";
            }

            if (get_master_ip(m_check).equals("")) {

                continue;
            } else {
                System.out.println("Im a client!");
                type = "client";
                break;
            }

        }

        if (type.equals("master")) {
            udpRegist.stop();
            udpRegistM.start();

            String IPP = getIP.getMyIP();

            Thread give_torrent = new Thread(new SimpleFileServer(tcp_port,path+torrent_name));
            To_torrent.makeTorrent(path+file_name,path,torrent_name,IPP,InetAddress.getByName(IPP));

            give_torrent.start();

            Seed.loadIt(path+torrent_name,path);


        } else if (type.equals("client")) {
            String torrentGetter_url = get_master_ip(m_check);
            Thread.sleep(5000);
            SimpleFileClient s = new SimpleFileClient(torrentGetter_url,tcp_port,path+torrent_name);
            s.rq();
            //new SimpleFileClient(torrentGetter_url,tcp_port,path+torrent_name);
            Seed.loadIt(path+torrent_name,path);

        }








    }
}


