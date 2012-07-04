
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ppt.DedicatedPPTServer;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Zorro
 */
public class PPTSlider{
    
    private static int currentPage = 1;
    
    //this is for scale properties
    private static int width = 720;
    private static int height = 540;
    private JLabel slidePic;
    private String fileName;
    
    public PPTSlider(JFrame Frame, JPanel Panel, 
                JPanel MainSlidePane, JPanel ScrollPane, int FileAmount,
                JScrollPane ScrollPaneContainer, final String FileName){
        try {
            MainSlidePane.removeAll();
            ScrollPane.removeAll();
//          Panel.removeAll();
            fileName = FileName;
            //size method"D:\\project\\Research\\PPT Image\\slide-1.png"));
            BufferedImage src = ImageIO.read(new File(fileName+"-1.png"));
            BufferedImage dest = new BufferedImage(width,height,
            BufferedImage.TYPE_INT_RGB);
            Graphics2D g = dest.createGraphics();
            AffineTransform at = AffineTransform.getScaleInstance(
            (double)width/src.getWidth(),
            (double)height/src.getHeight());
            g.drawRenderedImage(src,at);
            ImageIO.write(dest,"JPG",new File(fileName+"-1sized.png"));
             
            slidePic = new JLabel(new ImageIcon(dest));
            
            JButton next = new JButton("►");
            next.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        //you can insert size method above to this,if u find any inapprorite size
                        if(new File(fileName+"-"+ (currentPage+1) +".png").exists()){
                            ++currentPage;
                            BufferedImage nextSlide = ImageIO.read(new File(fileName+"-"+currentPage+".png"));
                            BufferedImage dest = new BufferedImage(width,height,
                            BufferedImage.TYPE_INT_RGB);
                            Graphics2D g = dest.createGraphics();
                            AffineTransform at = AffineTransform.getScaleInstance(
                            (double)width/nextSlide.getWidth(),
                            (double)height/nextSlide.getHeight());
                            g.drawRenderedImage(nextSlide,at);
                            ImageIO.write(dest,"JPG",new File(fileName+"-"+currentPage+"sized.png"));
                     
                            slidePic.setIcon(new ImageIcon(dest));
                            DedicatedPPTServer.broadcastPPTImage(null, new ImageIcon(dest));
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(PPTSlider.class.getName()).log(Level.SEVERE, null, ex);
                    }
             
                }
            });
            
            JButton prev = new JButton("◄");
            prev.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        //you can insert size method above to this,if u find any inapprorite size
                       if(new File(fileName+"-"+ (currentPage-1) +".png").exists()){
                            --currentPage;
                            BufferedImage nextSlide = ImageIO.read(new File(fileName+"-"+currentPage+".png"));
                            BufferedImage dest = new BufferedImage(width,height,
                            BufferedImage.TYPE_INT_RGB);
                            Graphics2D g = dest.createGraphics();
                            AffineTransform at = AffineTransform.getScaleInstance(
                            (double)width/nextSlide.getWidth(),
                            (double)height/nextSlide.getHeight());
                            g.drawRenderedImage(nextSlide,at);
                            ImageIO.write(dest,"JPG",new File(fileName+"-"+currentPage+"sized.png"));
                            
                            slidePic.setIcon(new ImageIcon(dest));
                            
                            DedicatedPPTServer.broadcastPPTImage(null, new ImageIcon(dest));
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(PPTSlider.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            JButton broadCast = new JButton("Broadcast");
            
            BoxLayout hlayout = new BoxLayout(MainSlidePane, BoxLayout.X_AXIS);
            MainSlidePane.setLayout(hlayout);
            
            sidePanelSlide(ScrollPane, FileAmount);
            MainSlidePane.add(slidePic);
            
            JPanel botPanel = new JPanel();
            BoxLayout hlayoutBot = new BoxLayout(botPanel, BoxLayout.X_AXIS);
            botPanel.setLayout(hlayoutBot);
            
            botPanel.add(prev);
            botPanel.add(broadCast);
            botPanel.add(next);
            botPanel.setBounds(530, 520, 300, 100);
            
            Panel.add(botPanel);
            MainSlidePane.revalidate();
            ScrollPane.revalidate();
            Panel.revalidate();
            
        } catch (IOException ex) {
            Logger.getLogger(PPTSlider.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void sidePanelSlide(JPanel ScrollPane, int FileAmount){
        try {
            for(int i = 0; i < FileAmount; i++){
                BoxLayout layoutScroll = new BoxLayout(ScrollPane, BoxLayout.Y_AXIS);
                ScrollPane.setLayout(layoutScroll);
                JPanel subPanel = new JPanel();
                final JLabel number = new JLabel(String.valueOf(i+1));
                final JButton SubslidePic;
                int subWidth = 160;
                int subHeight = 120;

                BoxLayout hlayout = new BoxLayout(subPanel, BoxLayout.X_AXIS);
                subPanel.setLayout(hlayout);

                BufferedImage nextSlide = ImageIO.read(new File(fileName+"-"+ (i+1) +".png"));
                BufferedImage dest = new BufferedImage(subWidth,subHeight, BufferedImage.TYPE_INT_RGB);
                Graphics2D g = dest.createGraphics();
                AffineTransform at = AffineTransform.getScaleInstance(
                (double)subWidth/nextSlide.getWidth(),
                (double)subHeight/nextSlide.getHeight());
                g.drawRenderedImage(nextSlide,at);
                ImageIO.write(dest,"JPG",new File(fileName+"-"+(i+1)+"sizedSub.png"));

                SubslidePic = new JButton();
                SubslidePic.setIcon(new ImageIcon(dest));

                SubslidePic.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
                SubslidePic.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                                if(!new File(fileName+"-"+number.getText()+"sized.png").exists()){
                                    try {
                                        BufferedImage nextSlide = ImageIO.read(new File(fileName+"-"+number.getText()+".png"));
                                        BufferedImage dest = new BufferedImage(width,height,
                                        BufferedImage.TYPE_INT_RGB);
                                        Graphics2D g = dest.createGraphics();
                                        AffineTransform at = AffineTransform.getScaleInstance(
                                        (double)width/nextSlide.getWidth(),
                                        (double)height/nextSlide.getHeight());
                                        g.drawRenderedImage(nextSlide,at);
                                        ImageIO.write(dest,"JPG",new File(fileName+"-"+number.getText()+"sized.png"));
                                    } catch (IOException ex) {
                                        Logger.getLogger(PPTSlider.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                                    
                                slidePic.setIcon(new ImageIcon(fileName+"-"+number.getText()+"sized.png"));
                                    
                        }
                    });

                subPanel.add(number);
                subPanel.add(SubslidePic);
                ScrollPane.add(subPanel);
            }
        } catch (IOException ex) {
            Logger.getLogger(PPTSlider.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    }

    

