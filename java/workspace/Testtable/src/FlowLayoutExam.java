import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel; 

public class FlowLayoutExam extends JFrame implements ActionListener{ 

    /** 
     * 필요한 필드 선언 
     */ 
    JButton btn1 = new JButton("버튼1"); 
    JButton btn2 = new JButton("버튼2"); 
    JButton btn3 = new JButton("버튼3"); 
    JPanel j1;
    JPanel j2;
    /** 
     * 화면구성 생성자 
     */ 
    public FlowLayoutExam() { 
        super("First Swing"); // 타이틀지정 
        j1 = new JPanel();
        j1.add(btn1,new BorderLayout());
        add(j1);
        
        j2 = new JPanel();
        j2.add(btn2,new BorderLayout());
        
        add(j1);
        setLocation(620, 300);
		setSize(700, 500);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) { 
        new FlowLayoutExam(); 
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object obj = e.getSource();
		if(obj== btn1) {
			setSize(200,100);
//			setPreferredSize(new Dimension(200,200));
//			j2.setVisible(true);
		}
	} 
} 
