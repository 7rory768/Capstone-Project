package tester;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class MyFrame extends javax.swing.JFrame{

    // these are the components we need.
    private final JSplitPane leftSplit;  // split the window in top and bottom
    private final JSplitPane rightSplit;  // split the window in top and bottom
    private final JPanel buttonPanel;       // container panel for the top
    private final JPanel paintPanel;    // container panel for the bottom
    private final JPanel pitchPanel;
    private final JPanel headingPanel;
    private final JSlider pitchSlider, headingSlider;
    
    private final JButton button;         // and a "send" button

    public MyFrame(){

        // first, lets create the containers:
        // the leftSplit devides the window in two components (here: top and bottom)
        // users can then move the devider and decide how much of the top component
        // and how much of the bottom component they want to see.
        leftSplit = new JSplitPane();
        rightSplit = new JSplitPane();
        pitchPanel = new JPanel();
        headingPanel = new JPanel();
        buttonPanel = new JPanel();
        paintPanel = new JPanel();
		this.pitchSlider = new JSlider(0, 360, 180);
		this.headingSlider = new JSlider(SwingConstants.VERTICAL, -90, 90, 0);
        

        // in our bottom panel we want the text area and the input components

        // the input components will be put in a separate panel
        button = new JButton("send");    // and a button at the right, to send the text

        // now lets define the default size of our window and its layout:
        setPreferredSize(new Dimension(400, 400));     // let's open the window with a default size of 400x400 pixels
        // the contentPane is the container that holds all our components
        getContentPane().setLayout(new GridLayout());  // the default GridLayout is like a grid with 1 column and 1 row,
        // we only add one element to the window itself
        getContentPane().add(leftSplit);   
        getContentPane().add(rightSplit); // due to the GridLayout, our leftSplit will now fill the whole window

        // let's configure our leftSplit:
        leftSplit.setOrientation(JSplitPane.VERTICAL_SPLIT);  // we want it to split the window verticaly
        leftSplit.setDividerLocation(330);
        leftSplit.setTopComponent(paintPanel);
        leftSplit.setRightComponent(pitchPanel);// and at the bottom we want our "bottomPanel"
        
        rightSplit.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        rightSplit.setDividerLocation(30);
        rightSplit.setLeftComponent(headingPanel);
        rightSplit.setRightComponent(buttonPanel);

        // our topPanel doesn't need anymore for this example. Whatever you want it to contain, you can add it here
        paintPanel.setLayout(new BoxLayout(paintPanel, BoxLayout.Y_AXIS)); // BoxLayout.Y_AXIS will arrange the content vertically
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
      
        // let's set the maximum size of the inputPanel, so it doesn't get too big when the user resizes the window
        pitchPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));     // we set the max height to 75 and the max width to (almost) unlimited
        //pitchPanel.setLayout(new BoxLayout(pitchSlider, BoxLayout.X_AXIS));   // X_Axis will arrange the content horizontally

        pitchPanel.add(pitchSlider);
        
        headingPanel.setMaximumSize(new Dimension(20, Integer.MAX_VALUE));
        headingPanel.add(headingSlider);
        
        buttonPanel.add(button);
        //inputPanel.add(textField);        // left will be the textField
        //inputPanel.add(button);           // and right the "send" button

        pack();   // calling pack() at the end, will ensure that every layout and size we just defined gets applied before the stuff becomes visible
    }

    public static void main(String args[]){
        EventQueue.invokeLater(new Runnable(){
            public void run(){
                new MyFrame().setVisible(true);
            }
        });
    }
}