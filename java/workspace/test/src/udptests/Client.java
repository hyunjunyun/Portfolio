package udptests;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {

	public void start() {
		try (DatagramSocket socket = new DatagramSocket();) {
			InetAddress addr = InetAddress.getByName("127.0.0.1");// ���� �ּ�

			byte[] msg = new byte[100];

			DatagramPacket outpac = new DatagramPacket(msg, 1, addr, 7777);//���� ��Ŷ

			DatagramPacket inpac = new DatagramPacket(msg, msg.length);// ���� ��Ŷ

			socket.send(outpac);//������
			socket.receive(inpac);//�ޱ�

			System.out.println("current server time : " + new String(inpac.getData()));//���� ������ ���

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
