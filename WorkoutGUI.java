import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WorkoutGUI{

    public WorkoutGUI() {}

    public static void main(String[] args) {
        //Frame
        JFrame frame = new JFrame("A Simple GUI");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(300, 300);
        frame.setLocationRelativeTo(null);

        //CAN MAKE A DIFFERENT FRAME POPUP IF NEEDED
        JPanel mainPanel = new JPanel();
        mainPanel.setVisible(false);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        frame.add(mainPanel, BorderLayout.CENTER);

        JLabel exLabel = new JLabel("Exercise: ");
        exLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(exLabel);

        //Drop Menu
        String[] choices = { "Bicep Curl", "Bench Press" };
        final JComboBox<String> menu = new JComboBox<String>(choices);
        menu.setMaximumSize(menu.getPreferredSize());
        menu.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(menu);

        //Text Fields
        JPanel rowPanel = new JPanel();
        JLabel weight = new JLabel("Weight: ");
        JTextField t1 = new JTextField(10);
        rowPanel.add(weight);
        rowPanel.add(t1);
        mainPanel.add(rowPanel);

        //Confimation Button
        JButton confirm = new JButton("OK");
        confirm.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirm.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                mainPanel.setVisible(false);
                //PLACEHOLDER 
                String ex = String.valueOf(menu.getSelectedItem());
                String w = t1.getText();
                System.out.println(ex + " " + w);
            } 
        });
        mainPanel.add(confirm);

        //button to show exercise panel
        JButton addEx = new JButton("ADD EXERCISES");
        addEx.addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) { 
                mainPanel.setVisible(true);
            } 
        }); 
        frame.add(addEx, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}