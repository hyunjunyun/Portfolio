package frame;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

public class UserCertifiForm extends BaseFrame {

	HashMap<Integer, Object> hash = new HashMap<Integer, Object>();

	public UserCertifiForm() {
		super(350, 600, "�ı�");
		JScrollPane scp = new JScrollPane();
		int j = 0;
		try (var pst = con.prepareStatement("select * from `member`")) {
			ArrayList<Integer> list = new ArrayList<Integer>();
			var rs = pst.executeQuery();
			while (rs.next()) {
				hash.put(rs.getInt(1), rs.getObject(3));
				list.add(rs.getInt(1));
			}
			
			Object[] listObject = new Object[list.size()];

			for (int i = 0; i < list.size(); i++) {
				listObject[i] = list.get(i);
			}
			String[] selections = {"ù��°��", "�ι�°��", "����°��"};
			JOptionPane.showInputDialog(null, "selectionValues�� �̿��ؼ�\n����â���� �����ϴ�.", "����ǥ����", JOptionPane.QUESTION_MESSAGE, null, selections, "�ι�°��");

			String input = (String) JOptionPane.showInputDialog(null, "�����ȣ", "������ ����", JOptionPane.QUESTION_MESSAGE, null, listObject,"d");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
