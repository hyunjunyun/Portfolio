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
      JButton button = new JButton("Show message");
      //need to do absolute positioning because by default LayeredPane has null layout,
      button.setBounds(100, 50, 150, 30);
      layeredPane.add(button, JLayeredPane.DEFAULT_LAYER);//depth 0
      
      JButton button1 = new JButton("Show message");
      //need to do absolute positioning because by default LayeredPane has null layout,
      button1.setBounds(100, 90, 150, 30);
      layeredPane.add(button1, JLayeredPane.DEFAULT_LAYER);//depth 0

      //adding an initially invisible JLabel in an upper layer
      JPanel jp = new JPanel();
      jp.setOpaque(true);
      //setting background with transparency value to see though the label
      jp.setBackground(new Color(50, 210, 250, 200));
      //just set the size for now
      jp.setSize(200, 50);
      jp.setBorder(new LineBorder(Color.black));
      jp.setVisible(false);
      layeredPane.add(jp, 1);//depth 300
      
      //to make label visible
      button.addActionListener(e -> {
          JComponent source = (JComponent) e.getSource();
          //set the  popup label location at center of the source component
          jp.setLocation(new Point(source.getX(),
                  source.getHeight()+source.getY()));
          jp.setVisible(true);
      });

      //to hide the label
      frame.addMouseListener(new MouseAdapter() {
          @Override
          public void mousePressed(MouseEvent e) {
              jp.setVisible(false);
          }
      });

      frame.add(layeredPane);
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
  }

  private static JFrame createFrame() {
      JFrame frame = new JFrame("JLayeredPane Basic Example");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(new Dimension(500, 400));
      return frame;
  }
}