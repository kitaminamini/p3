/**
 * Created by Thanapat on 12/2/2016 AD.
 */

/*
Java Swing, 2nd Edition
By Marc Loy, Robert Eckstein, Dave Wood, James Elliott, Brian Cole
ISBN: 0-596-00408-7
Publisher: O'Reilly
*/
// SwingProgressBarExample.java
// A demonstration of the JProgressBar component. The component tracks the
// progress of a for loop.
//

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

public class progressBar extends JPanel {

    JProgressBar pbar;

    static final int MY_MINIMUM = 0;

    static final int MY_MAXIMUM = 100;

    public progressBar() {
        pbar = new JProgressBar();
        pbar.setMinimum(MY_MINIMUM);
        pbar.setMaximum(MY_MAXIMUM);
        pbar.setSize(500,1000);
        add(pbar);
    }

    public void updateBar(int newValue) {
        pbar.setValue(newValue);
    }



}
