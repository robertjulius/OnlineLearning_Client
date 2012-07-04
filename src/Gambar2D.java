import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

/**
 *
 * @author Pandinito
 */
public class Gambar2D 
{
     public void Gambar2D(JPanel PanelKanvas,JToggleButton tombolGaris,JToggleButton 
            tombolKurva, JToggleButton tombolKotak )
    {
        
       tombolGaris.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) 
            {
               BufferedImage bg = new BufferedImage (10,20,BufferedImage.TYPE_INT_RGB);
               Graphics2D g2 = bg.createGraphics();
               
               g2.setPaint(Color.WHITE);
               int x1 = 0; int y1 = 0; 
               int x2 = 0; int y2 = 0; 
               g2.drawLine(x1, y1, x2, y2);
            }
        }
               
               );
       
       
       tombolKurva.addActionListener(new ActionListener(){
           @Override
           public void actionPerformed(ActionEvent e)
           {
               
           }
           
           
       });
       
    }

   
    
        
        
        
            
        }
    

