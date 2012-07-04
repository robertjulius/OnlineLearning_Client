
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import org.apache.poi.hslf.model.Slide;
import org.apache.poi.hslf.usermodel.SlideShow;
import org.apache.poi.xslf.usermodel.*;


/**
 *
 * @author Zorro & ultima51
 */

public class PopUpMenu {
    
    public PopUpMenu(JFrame frame, JPanel Panel, 
                JPanel MainSlidePane, JPanel ScrollPane, JScrollPane ScrollPaneContainer) throws IOException{
        FileInputStream is = null;
        try {
            //      JFrame frame = new JFrame("Creating a Popup Menu");
            String filename = File.separator+"tmp";
            JFileChooser fc = new JFileChooser(new File(filename));
            // Show open dialog; this method does not return until the dialog is closed
            fc.showOpenDialog(frame);
            File selFile = fc.getSelectedFile();
            // Show save dialog; this method does not return until the dialog is closed
            //      fc.showSaveDialog(frame);
            //      selFile = fc.getSelectedFile();
            
            
            SlideShow slideShow = new SlideShow();
            Slide slide1 = slideShow.createSlide();
            is = new FileInputStream(selFile);
            SlideShow ppt = new SlideShow(is);
            
            is.close();
            Dimension pgsize = ppt.getPageSize();
            Slide[] slide = ppt.getSlides();
            for (int i = 0; i < slide.length; i++) {

                BufferedImage img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.TYPE_INT_RGB);
                Graphics2D graphics = img.createGraphics();
                //clear the drawing area
                graphics.setPaint(Color.white);
                //graphics.setBackground(Color.red);
                graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));

                //render
                slide[i].draw(graphics);

                //save the output
                File file = new File("C:\\project\\Research\\PPT Image");
                file.mkdirs();
                FileOutputStream out = new FileOutputStream("C:\\project\\Research\\PPT Image\\"+selFile.getName()+"-" + (i+1) + ".png");
                javax.imageio.ImageIO.write(img, "png", out);
                out.close();
            }
            PPTSlider slider = new PPTSlider(frame, 
                    Panel, MainSlidePane, ScrollPane, slide.length,
                    ScrollPaneContainer, "C:\\project\\Research\\PPT Image\\"+selFile.getName());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PopUpMenu.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
                Logger.getLogger(PopUpMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
