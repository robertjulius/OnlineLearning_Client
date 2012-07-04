package ppt;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;

public class Util {	
	
	public static String getPathFromUrl(String url) throws UnsupportedEncodingException {
		return new File(URLDecoder.decode(url, Charset.defaultCharset().name())).getAbsolutePath();
	}

	public static String getActiveDirectory() throws UnsupportedEncodingException {
		return new File(Util.getPathFromUrl(Util.class.getProtectionDomain().getCodeSource().getLocation().getFile())).getParentFile().getAbsolutePath();
	}
}
