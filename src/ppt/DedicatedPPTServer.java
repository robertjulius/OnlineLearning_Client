package ppt;

import java.awt.Component;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class DedicatedPPTServer {
	
	private static ArrayList<Socket> clientSockets;
	private static ServerSocket serverSocket;
	private static boolean listening;
	
	public static void startListening() throws NumberFormatException, IOException {
		
            JOptionPane.showMessageDialog(null, Integer.parseInt(Props.getValue("ppt.socket.server.port")));
            
		serverSocket = new ServerSocket(Integer.parseInt(Props.getValue("ppt.socket.server.port")));
                
                JOptionPane.showMessageDialog(null, Integer.parseInt(Props.getValue("ppt.socket.server.port")));
                
		if (clientSockets != null) {
			clientSockets.clear();
		} else {
			clientSockets = new ArrayList<Socket>();
		}


		Thread thread = new Thread () {

			public void run() {
				listening = true;
				while (listening) {
					try {
						Socket clientSocket = serverSocket.accept();
						clientSockets.add(clientSocket);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		};
		
		thread.start();
	}
	
	public static void stopListening() {
		listening = false;
		for (Socket clientSocket : clientSockets) {
			try {
				clientSocket.close();
			} catch (IOException e) {}
		}
	}
	
	public static void broadcastPPTImage(Component parentComponent, ImageIcon icon) throws IOException {		
		for (Socket clientSocket : clientSockets) {
			if (clientSocket != null && !clientSocket.isClosed()) {
				OutputStream outputStream = null;
				ObjectOutputStream objectOutputStream = null;
				try {
					outputStream = clientSocket.getOutputStream();
					objectOutputStream = new ObjectOutputStream(outputStream);
					objectOutputStream.writeObject(icon);
					
					objectOutputStream.flush();
				} catch (IOException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(parentComponent, "Failed sending slide show to " + clientSocket.getInetAddress() + ": " + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
				} finally {
					if (objectOutputStream != null) {
						try {
							objectOutputStream.close();
						} catch (IOException e) {}
					}
					if (outputStream != null) {
						try {
							outputStream.close();
						} catch (IOException e) {}
					}
				}
			}
		}
	}
}
