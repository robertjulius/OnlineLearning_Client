import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.*;

/**
 *
 * @author Zorro
 */

public class FileServer 
{
    private PPTSlider presentasi;
    private Server servis;
    private String namaFile;
    
    public FileServer (final String namaFile) throws IOException
    {
               
        while (true)
        {           
            
            Socket sock = servis.MyService.accept();
            
            //kirim file
            File nmFile = new File(namaFile);
            byte [] mybytearray  = new byte [(int)namaFile.length()];
            FileInputStream fis = new FileInputStream(namaFile);
            BufferedInputStream bis = new BufferedInputStream(fis);
            bis.read(mybytearray,0,mybytearray.length);
            OutputStream os = sock.getOutputStream();
            System.out.println("Sending...");
            os.write(mybytearray,0,mybytearray.length);
            os.flush();
            sock.close();
            
        }
    }
}
