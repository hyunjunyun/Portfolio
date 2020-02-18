package frame;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

public class UserCertifiForm extends BaseFrame {

	HashMap<Integer, Object> hash = new HashMap<Integer, Object>();

	public UserCertifiForm() {
		super(350, 600, "식권");
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
			String[] selections = {"첫번째값", "두번째값", "세번째값"};
			JOptionPane.showInputDialog(null, "selectionValues를 이용해서\n선택창으로 만들어봅니다.", "제목표시줄", JOptionPane.QUESTION_MESSAGE, null, selections, "두번째값");

			String input = (String) JOptionPane.showInputDialog(null, "사원번호", "결제자 인증", JOptionPane.QUESTION_MESSAGE, null, listObject,"d");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
