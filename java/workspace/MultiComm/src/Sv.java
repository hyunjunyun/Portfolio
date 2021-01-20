
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class Sv {

	private ServerSocket ss;

	private Socket cs;

	private Sgui sg;

	private String msg;

	private String nick;

	private String check;

	private String answer;

	Random r = new Random();

	int cnt = 0;

	boolean c = true;

	private Map<String, DataOutputStream> clientMap = Collections
			.synchronizedMap(new HashMap<String, DataOutputStream>());

	public final void setSg(Sgui sg) {

		this.sg = sg;

	}

	// 포트세팅
	public void setting() throws IOException {

		try {

			ss = new ServerSocket(7777);

			while (true) {

				cs = ss.accept();

				Thread receiver = new Receiver(cs);

				receiver.start();
			}

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	public void addClient(String nick, DataOutputStream out) throws IOException {
		sendMessage(nick + " in this channel!\n");
		clientMap.put(nick, out);
		cnt++;
	}

	// 메세지를 보내는 부분
	public void sendMessage(String msg) {
		Iterator<String> it = clientMap.keySet().iterator();

		String key = "";
		while (it.hasNext()) {
			key = it.next();
			try {
				clientMap.get(key).writeUTF(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} // while
	} // sendMessage()

	// 연결하는 부분
	class Receiver extends Thread {
		DataInputStream in;
		DataOutputStream out;
		//서버가 클라이언트와 연결을 하는 부분
		public Receiver(Socket cs) throws IOException {

			in = new DataInputStream(cs.getInputStream());

			out = new DataOutputStream(cs.getOutputStream());

			nick = in.readUTF();
			addClient(nick, out);

			if (cnt > 1) {
				sg.jta.setEditable(false);
				try {
					for (int i = 1; i <= 3; i++) {
						sg.appendMsg(i + " second\n");
						sendMessage("Starts in " + i + " second\n");
						Thread.sleep(1000);
					}
					sendMessage("Start!\n");
				} catch (Exception e) {
					e.printStackTrace();
				}
				sg.jta.setEditable(true);
			}
		}

		@Override
		//메시지를 보내주고 문제가 맞았는지 틀렸는지 체크하는 메서드
		public void run() {
			String[] msgStr;
			try {
				while (in != null) {
					msg = in.readUTF();

					sg.appendMsg(msg + "\n");
					sendMessage(msg + "\n");
					c = true;
					msgStr = msg.split(" ");
					if (msgStr[1].equals(check)) {
						c = false;
						sg.appendMsg("answer!\n");
						startAction(r.nextInt(10)+1);
						sendMessage("the correct answer!!\n");
						break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	//문제를 무작위로 내주는 메서드
	public void startAction(int r) {
		try (ResultSet rs = CM.getResult("select * from q_data where q_no = "+ r)){
			if (rs.next()) {
				answer = rs.getString(2);
				check = rs.getString(3);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		sg.appendMsg(answer + "," + check+"\n"); //서버에 문제를 보내는 명령어
		sendMessage(answer+"\n");
	}

	
}