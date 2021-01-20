
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Ct {

	private Socket cs;

	private DataInputStream in;

	private DataOutputStream out;

	private Cgui cgui;

	private String msg;

	private String nickName;

	public final void setCgui(Cgui cgui) {

		this.cgui = cgui;

	}
	
	public void connect() {

		try {

			cs = new Socket("127.0.0.1", 7777);

			in = new DataInputStream(cs.getInputStream());

			out = new DataOutputStream(cs.getOutputStream());

			out.writeUTF(nickName);

			while (in != null) {
				msg = in.readUTF();
				cgui.appendMsg(msg);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int a = 0;
	}
	
	
	public void sendMessage(String msg2) {
		try {
			out.writeUTF(msg2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

}