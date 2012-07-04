/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author ultima51
 */
public class JavaApplication6 extends JApplet{

    @Override
    public void init() {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                   public void run() {
                       AudioTab audioTab = new AudioTab();
                       audioTab.setVisible(true);
                       setContentPane(audioTab);
                   }
               });
        } catch (InterruptedException ex) {
            Logger.getLogger(JavaApplication6.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(JavaApplication6.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
