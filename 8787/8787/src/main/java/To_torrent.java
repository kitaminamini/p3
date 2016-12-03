
import com.turn.ttorrent.common.Torrent;
import com.turn.ttorrent.tracker.TrackedTorrent;
import com.turn.ttorrent.tracker.Tracker;

import java.io.File;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URI;

/**
 * Created by Thanapat on 11/23/2016 AD.
 */

public class To_torrent {


    public static void makeTorrent(String filepath,String outfiledir,String file_name,String master,InetAddress mas) {
        // File parent = new File("d:/echo-insurance.backup");
        //String sharedFile = "d:/echo-insurance.backup";
        String sharedFile = filepath;

        try {

            String urll = "http://"+master+":3434"+"/"+"announce";
            URI lol = new URI(urll);
            Tracker tracker = new Tracker(new InetSocketAddress(mas,3434));
            tracker.start();

            System.out.println( "create new .torrent metainfo from "+filepath );

            //Torrent torrent = Torrent.create(new File(sharedFile), tracker.getAnnounceUrl().toURI(), "createdByAuthor");
            Torrent torrent = Torrent.create(new File(sharedFile),lol , "createdByAuthor");
            System.out.println("save .torrent to "+outfiledir+file_name);

            FileOutputStream fos = new FileOutputStream(outfiledir+file_name);
            torrent.save( fos );
            fos.close();
            System.out.println("done creating .torrent");
            tracker.announce((TrackedTorrent.load(new File(outfiledir+file_name))));
            System.out.println("annouce to tracker to file : "+outfiledir+file_name);
            //tracker.stop();


        } catch ( Exception e ) {
            e.printStackTrace();
        }

    }

}
