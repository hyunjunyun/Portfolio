package udptests;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {

	public void start() {
		try (DatagramSocket socket = new DatagramSocket();) {
			InetAddress addr = InetAddress.getByName("127.0.0.1");// 서버 주소

			byte[] msg = new byte[100];

			DatagramPacket outpac = new DatagramPacket(msg, 1, addr, 7777);//보낼 패킷

			DatagramPacket inpac = new DatagramPacket(msg, msg.length);// 받을 패킷

			socket.send(outpac);//보내기
			socket.receive(inpac);//받기

			System.out.println("current server time : " + new String(inpac.getData()));//받은 데이터 출력

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			new Client().start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
