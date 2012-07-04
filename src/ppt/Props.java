package ppt;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;

public class Props {
	
	private static Properties properties;
	
	static {
		try {
			properties = new Properties();
			FileInputStream inputStream = new FileInputStream(Util.getActiveDirectory() + "/setting.properties");
			properties.clear();
	        properties.load(inputStream);
		} catch (FileNotFoundException e) {
			Logger.getLogger(Props.class.getName()).log(Level.WARNING, e.getMessage(), e);
                        JOptionPane.showMessageDialog(null, e.getMessage());
			System.exit(1);
		} catch (IOException e) {
			Logger.getLogger(Props.class.getName()).log(Level.WARNING, e.getMessage(), e);
                        JOptionPane.showMessageDialog(null, e.getMessage());
			System.exit(1);
		} catch (Exception e) {
			Logger.getLogger(Props.class.getName()).log(Level.WARNING, e.getMessage(), e);
                        JOptionPane.showMessageDialog(null, e.getMessage());
			System.exit(1);
		} finally {
			
		}
	}
	
	public static String getValue(String key) {
		return properties.getProperty(key);
	}
	
	public static Properties getProperties() {
		return properties;
	}
}
