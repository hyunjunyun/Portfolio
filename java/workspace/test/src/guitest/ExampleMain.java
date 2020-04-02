package guitest;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ExampleMain {
  public static void main(String[] args) {
      JFrame frame = createFrame();
      
      JLayeredPane layeredPane = new JLayeredPane();

      //adding a button to the JLayeredPane
      JPanel jp = new JPanel();
      var jsc = new JScrollPane(jp);
      JButton button = new JButton("Show message");
      //need to do absolute positioning because by default LayeredPane has null layout,
      button.setSize(200,200);
      layeredPane.add(button, JLayeredPane.DEFAULT_LAYER);//depth 0
      layeredPane.add(jsc, 1);//depth 300
      
      JButton button1 = new JButton("Show message");
      //need to do absolute positioning because by default LayeredPane has null layout,
      button1.setBounds(100, 90, 150, 30);
      layeredPane.add(button1, JLayeredPane.DEFAULT_LAYER);//depth 0

      //adding an initially invisible JLabel in an upper layer
      jsc.setSize(200,50);
      //setting background with transparency value to see though the label
      //just set the size for now
      jp.setSize(200, 50);
      jp.setBorder(new LineBorder(Color.black));
      jsc.setVisible(false);
      
      //to make label visible
      button.addActionListener(e -> {
          JComponent source = (JComponent) e.getSource();
          //set the  popup label location at center of the source component
          jsc.setLocation(new Point(source.getX(),
                  source.getHeight()+source.getY()));
          jsc.setVisible(true);
      });

      //to hide the label
      frame.addMouseListener(new MouseAdapter() {
          @Override
          public void mousePressed(MouseEvent e) {
              jsc.setVisible(false);
          }
      });
      frame.add(button);
      frame.add(layeredPane);
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
  }

  private static JFrame createFrame() {
      JFrame frame = new JFrame("JLayeredPane Basic Example");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setLayout(new FlowLayout(FlowLayout.CENTER));
      frame.setSize(new Dimension(500, 400));
      return frame;
  }
}