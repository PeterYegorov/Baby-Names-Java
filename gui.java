
//Usually you will require both swing and awt packages
// even if you are working with just swings.
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class gui {
    public static void main(String args[]) {

        //Creating the Frame
        JFrame frame = new JFrame("Name Guru");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        //Creating the MenuBar and adding components
        /*JMenuBar mb = new JMenuBar();
        JMenu m1 = new JMenu("FILE");
        JMenu m2 = new JMenu("Help");
        mb.add(m1);
        mb.add(m2);
        JMenuItem m11 = new JMenuItem("Open");
        JMenuItem m22 = new JMenuItem("Save as");
        m1.add(m11);
        m1.add(m22);*/

        //Creating the panel and adding components
        JPanel panel = new JPanel(); // the panel is not visible in output
        JLabel nameLabel = new JLabel("Name");
        JLabel genderLabel = new JLabel("Gender");
        JLabel yearLabel = new JLabel("Year");
        JLabel yearOfInterestLabel = new JLabel("Year of interest");
        JTextField nameText = new JTextField(20); // accepts upto 20 characters
        JTextField genderText = new JTextField(1); // accepts upto 1 characters
        JTextField yearText = new JTextField(4); // accepts upto 4 characters
        JTextField yearOfInterestText = new JTextField(4); // accepts upto 4 characters
        JButton getNameInYear = new JButton("Name if born in a specified year");
        getNameInYear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BabyNames bn = new BabyNames();
                String name = nameText.getText().toString();
                int year = Integer.parseInt(yearText.getText().toString());
                int yearOfInterest = Integer.parseInt(yearOfInterestText.getText().toString());
                String gender = genderText.getText().toString();
                
                //System.out.println(bn.whatIsNameInYear(name, year, yearOfInterest, gender));
                JLabel result = new JLabel(bn.whatIsNameInYear(name, year, yearOfInterest, gender));
                panel.add(result);
                panel.revalidate();
                panel.repaint();
            }
        });
        
        JButton getInfo = new JButton("Info about this name");
        getInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BabyNames bn = new BabyNames();
                String name = nameText.getText().toString();
                String gender = genderText.getText().toString();
                
                System.out.println(bn.yearOfHighestRank(name, gender));
                System.out.println(bn.getAverageRank(name, gender));
                JLabel result1 = new JLabel("Highest rank of name " + name + " was in " + bn.yearOfHighestRank(name, gender));
                JLabel result2 = new JLabel("Avarage rank of name " + name + " was " + bn.getAverageRank(name, gender));
                panel.add(result1);
                panel.add(result2);
                panel.revalidate();
                panel.repaint();
            }
        });
        
        panel.add(nameLabel); // Components Added using Flow Layout
        panel.add(nameText);
        panel.add(genderLabel);
        panel.add(genderText);
        panel.add(yearLabel);
        panel.add(yearText);
        panel.add(yearOfInterestLabel);
        panel.add(yearOfInterestText);
        panel.add(getNameInYear);
        panel.add(getInfo);

        // Text Area at the Center
        //JTextArea ta = new JTextArea();

        //Adding Components to the frame.
        frame.getContentPane().add(BorderLayout.CENTER, panel);
        /*frame.getContentPane().add(BorderLayout.NORTH, mb);
        frame.getContentPane().add(BorderLayout.CENTER, ta);*/
        frame.setVisible(true);
        
    }
}
