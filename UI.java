import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class UI {
  /** Code copied from the SwingContainerDemo file. */
  private JFrame mainFrame;

  private JLabel headerLabel;

  private JLabel statusLabel;

  private JLabel msgLabel;

  private JPanel controlPanel;

  /** End of code copied from SwingContainer. */

  public UI(){
    start();
  }

  public static void main(String[] args){
    UI demo = new UI();
    demo.showJFrameDemo();
  }

  private void start(){
    mainFrame = new JFrame("CS321 Workout App");
    mainFrame.setSize(300, 300);
    mainFrame.setLayout(new GridLayout(3, 1));
    mainFrame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent windowEvent){
        System.exit(0);
      }
    });
    headerLabel = new JLabel("", JLabel.CENTER);
    statusLabel = new JLabel("",JLabel.CENTER);
    statusLabel.setSize(350,100);
    msgLabel = new JLabel("Welcome to CS321 app.", JLabel.CENTER);

    controlPanel = new JPanel();
    controlPanel.setLayout(new FlowLayout());

    mainFrame.add(headerLabel);
    mainFrame.add(controlPanel);
    mainFrame.add(statusLabel);
    mainFrame.setVisible(true);
  }
  private void showJFrameDemo(){
    headerLabel.setText("Welcome to the workout app:");
    final JFrame frame = new JFrame();
    frame.setSize(300, 300);
    frame.setLayout(new FlowLayout());
    frame.add(msgLabel);

    frame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent windowEvent){
        frame.dispose();
      }
    });
    JButton okButton = new JButton("Add a new exercise.");
    okButton.addActionListener(e -> {
      statusLabel.setText("A Frame shown to the user.");
      frame.setVisible(true);
    });
    controlPanel.add(okButton);
    mainFrame.setVisible(true);
  }
}

