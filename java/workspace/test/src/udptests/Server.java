package udptests;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Server {
	public void start() {
		try (DatagramSocket socket = new DatagramSocket(7777)) {
			DatagramPacket inpac, outpac;

			byte[] inMsg = new byte[100];
			byte[] outMsg;
			inpac = new DatagramPacket(inMsg, inMsg.length);
			socket.receive(inpac);

			InetAddress addr = inpac.getAddress();
			int port = inpac.getPort();

			SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
			String time = format.format(new Date());
			outMsg = time.getBytes();

			outpac = new DatagramPacket(outMsg, outMsg.length, addr, port);
			socket.send(outpac);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			new Server().start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
