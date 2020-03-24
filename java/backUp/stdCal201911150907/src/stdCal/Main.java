// package stdCal;

// import java.awt.BorderLayout;
// import java.awt.FlowLayout;
// import java.awt.GridLayout;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;
// import java.util.ArrayList;

// import javax.swing.JButton;
// import javax.swing.JFrame;
// import javax.swing.JLabel;
// import javax.swing.JPanel;
// import javax.swing.JTextField;

// import stdResult.Student;

// public class Main implements ActionListener {
// 	ArrayList<Student> arr = new ArrayList<Student>();
// 	// Frame
// 	JFrame f = new JFrame("���� ��� ���α׷�");
// 	JFrame f2 = new JFrame("����");
// 	//Panel
// 	JPanel namepanel = new JPanel();
// 	JPanel hakpanel = new JPanel();
// 	JPanel kookpanel = new JPanel();
// 	JPanel mathpanel = new JPanel();
// 	JPanel engpanel = new JPanel();
// 	JPanel cenpanel = new JPanel(new GridLayout(5, 2));
// 	JPanel buttonpanel = new JPanel();
// 	JPanel sumpanel = new JPanel();
// 	JPanel avgpanel = new JPanel();
// //	TextField
// 	JTextField tfname = new JTextField(10);
// 	JTextField tfhak = new JTextField(10);
// 	JTextField tfkook = new JTextField(10);
// 	JTextField tfmath = new JTextField(10);
// 	JTextField tfeng = new JTextField(10);
// 	//Label
// 	JLabel namelabel = new JLabel("�̸�        ");
// 	JLabel haklabel = new JLabel("�й�        ");
// 	JLabel kooklabel = new JLabel("�����");
// 	JLabel mathlabel = new JLabel("���м���");
// 	JLabel englabel = new JLabel("�����");
// 	JLabel sumlabel = new JLabel("���� ����");
// 	JLabel sum2label = new JLabel("");
// 	JLabel avglabel = new JLabel("���� ���");
// 	JLabel avg2label = new JLabel("");

// 	// ��1 ���� ��ư
// 	JButton rsBtn = new JButton("������");
// 	JButton endBtn = new JButton("����");
// 	JButton cfBtn = new JButton("�Է�");

// 	// ��2 ���� �г�
// 	JPanel namepanel2 = new JPanel();
// 	JPanel hakpanel2 = new JPanel();
// 	JPanel kookpanel2 = new JPanel();
// 	JPanel mathpanel2 = new JPanel();
// 	JPanel engpanel2 = new JPanel();

// 	// ��2 ���� ��
// 	JLabel labelname2 = new JLabel("");
// 	JLabel labelhak2 = new JLabel("");
// 	JLabel labelkook2 = new JLabel("");
// 	JLabel labelmath2 = new JLabel("");
// 	JLabel labeleng2 = new JLabel("");
// 	JLabel resultlabel = null;

// 	public void go() {
// 		f.getContentPane().setLayout(new BorderLayout());
// 		initGUIf1();
// 		f.setBounds(300, 300, 300, 300);
// 		f.setVisible(true);

// 		// f2.setLayout(new FlowLayout());
// 		// initGUIf2();
// 		// f2.setBounds(300, 300, 300, 300);
// 	}

// 	// ��1 ������
// 	public void initGUIf1() {

// 		namepanel.add(namelabel);
// 		namepanel.add(tfname);
// 		cenpanel.add(namepanel);

// 		hakpanel.add(haklabel);
// 		hakpanel.add(tfhak);
// 		cenpanel.add(hakpanel);

// 		kookpanel.add(kooklabel);
// 		kookpanel.add(tfkook);
// 		cenpanel.add(kookpanel);

// 		mathpanel.add(mathlabel);
// 		mathpanel.add(tfmath);
// 		cenpanel.add(mathpanel);

// 		engpanel.add(englabel);
// 		engpanel.add(tfeng);
// 		cenpanel.add(engpanel);

// 		f.getContentPane().add(cenpanel, BorderLayout.CENTER);

// 		buttonpanel.add(cfBtn);
// 		buttonpanel.add(rsBtn);
// 		buttonpanel.add(endBtn);
// 		f.getContentPane().add(buttonpanel, BorderLayout.SOUTH);

// 		rsBtn.addActionListener(this);
// 		endBtn.addActionListener(this);
// 		cfBtn.addActionListener(this);
// 	}

// 	public void actionPerformed(ActionEvent e) {
// 		String cmd = e.getActionCommand();

// //����ϱ�
		
// 		if (cmd.equals("������")) {
// 			for (int i = 0; i < arr.size(); i++) {
// 				System.out.println(arr.get(i));
// 			}

// 		}

// //�Է¹�ư ��������
// 		else if (cmd.equals("�Է�")) {
// 			String name2 = tfname.getText();
// 			String hak2 = tfhak.getText();
// 			int kook2 = Integer.parseInt(tfkook.getText());
// 			int math2 = Integer.parseInt(tfmath.getText());
// 			int eng2 = Integer.parseInt(tfeng.getText());

// 			arr.add(new Student(name2, hak2, kook2, math2, eng2));
// 			System.out.println(arr.size());
// 			// �ؽ�Ʈ�ʵ� �ʱ�ȭ
// 			tfname.setText("");
// 			tfhak.setText("");
// 			tfkook.setText("");
// 			tfmath.setText("");
// 			tfeng.setText("");
// 		}

// 		else {
// 			f.dispose();
// 			System.exit(0);
// 		}
// 	}

// 	public static void main(String args[]) {
// 		Main ss = new Main();
// 		ss.go();
// 	}
// }