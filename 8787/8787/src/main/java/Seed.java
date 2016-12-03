import com.turn.ttorrent.client.Client;
import com.turn.ttorrent.client.SharedTorrent;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.Observable;
import java.util.Observer;


/**
 * Created by Thanapat on 11/24/2016 AD.
 */
public class Seed {
    public static void loadIt(String torrent_file_path,String original_file_dir) throws InterruptedException {
        try {

            final progressBar it = new progressBar();

            JFrame frame = new JFrame("Progress Bar Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(it);
            //frame.setSize(300,200);
            frame.pack();
            frame.setVisible(true);


            System.out.println("Client: input .torrent file = "+torrent_file_path);
            System.out.println("Client: to seed file path = "+original_file_dir);
            String MyIP =getIP.getMyIP();
            Client client = new Client(InetAddress.getByName(MyIP), SharedTorrent.fromFile(new File(torrent_file_path), new File(original_file_dir)));
            client.setMaxDownloadRate(0);
            client.setMaxUploadRate(0);

            client.addObserver(new Observer() {
                @Override
                public void update(Observable observable, Object data) {
                    Client client = (Client) observable;

                    int progress = Math.round(client.getTorrent().getCompletion());
                    long downloaded = client.getTorrent().getDownloaded();
                    long uploaded = client.getTorrent().getUploaded();
//                    System.out.println("progress : "+String.valueOf(progress));
//                    System.out.println("downloaded : "+String.valueOf(downloaded));
//                    System.out.println("uploaded : "+String.valueOf(uploaded));
                    try {
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                it.updateBar(progress);
                            }
                        });
                        java.lang.Thread.sleep(100);
                    } catch (InterruptedException e) {
                        ;
                    }

                }
            });

            client.download();



            client.share(3600);
            client.waitForCompletion();

        } catch (UnknownHostException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
