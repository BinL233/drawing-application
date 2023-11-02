/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java2ddrawingapplication;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Objects;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author acv
 */
public class DrawingApplicationFrame extends JFrame {

    // Create the panels for the top of the application. One panel for each
    // line and one to contain both of those panels.
    static ArrayList<MyShapes> set;
    private final JPanel main;

    // create the widgets for the firstLine Panel.
    private final JPanel firstPanel;
    private final JLabel label1;
    private final JButton button1;
    private final JButton button2;
    private final JButton button3;
    private final JButton button4;
    private final JComboBox comboBox;

    //create the widgets for the secondLine Panel.
    private final JPanel secondPanel;
    private final JLabel label2;
    private final JLabel label3;
    private final JLabel label4;
    private final JSpinner spinner1;
    private final JSpinner spinner2;
    private final JCheckBox checkbox1;
    private final JCheckBox checkbox2;
    private final JCheckBox checkbox3;
    // Variables for drawPanel.

    private final JPanel bottomPanel;
    private final JLabel label5;
    private String shape;
    MyShapes myShapes;
    MyBoundedShapes myBoundedShapes;

    final DrawPanel drawPanel;
    Color color1 = new Color(120, 150, 255);
    Color color2 = new Color(108, 182, 196);
    Paint color;
    Paint oriColor;

    Paint paintG = new GradientPaint(0, 0, color1, 50, 50, color2, true);
    Point pointA;
    Point pointB;
    Point pointC;

    int lineWidth = 4;
    Stroke stroke = new BasicStroke(lineWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    private float[] dashLength;
    boolean fill = false;



    // add status label

    // Constructor for DrawingApplicationFrame
    public DrawingApplicationFrame() {
        // add widgets to panels
        this.setTitle("Java 2D Drawings");
        main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));

        // firstLine widgets
        firstPanel = new JPanel();
        main.add(firstPanel);

        label1 = new JLabel();

        label1.setText("Shape:");
        label1.setHorizontalAlignment(label1.CENTER);
        label1.setFont(new Font("Serif", Font.BOLD, 15));
        firstPanel.add(label1);

        String[] shapes = {"Line", "Oval", "Rectangle"};
        comboBox = new JComboBox(shapes);
        shape = "Line";
        comboBox.addActionListener(new ComboBoxHandler());
        firstPanel.add(comboBox);

        button1 = new JButton();
        button1.setBounds(0, 0, 0, 0);
        button1.setText("1st Color...");
        firstPanel.add(button1);
        button1.addActionListener(new Button1Handler());


        button2 = new JButton();
        button2.setBounds(0, 0, 0, 0);
        button2.setText("2st Color...");
        firstPanel.add(button2);
        button2.addActionListener(new Button2Handler());


        button3 = new JButton();
        button3.setBounds(0, 0, 0, 0);
        button3.setText("Undo");
        firstPanel.add(button3);
        button3.addActionListener(new Button3Handler());


        button4 = new JButton();
        button4.setBounds(0, 0, 0, 0);
        button4.setText("Clear");
        firstPanel.add(button4);
        button4.addActionListener(new Button4Handler());


        // secondLine widgets
        secondPanel = new JPanel();
        main.add(secondPanel);

        label2 = new JLabel();

        label2.setText("Options:");
        label2.setHorizontalAlignment(label2.CENTER);
        label2.setFont(new Font("Serif", Font.BOLD, 15));
        secondPanel.add(label2);

        checkbox1 = new JCheckBox();
        checkbox1.setText("filled");
        checkbox1.setFocusable(false);
        checkbox1.addActionListener(new Checkbox1Handler());

        secondPanel.add(checkbox1);

        checkbox2 = new JCheckBox();
        checkbox2.setText("Use Gradient");
        checkbox1.setFocusable(false);
        secondPanel.add(checkbox2);
        checkbox2.addActionListener(new Checkbox2Handler());

        checkbox3 = new JCheckBox();
        checkbox3.setText("Dashed");
        checkbox1.setFocusable(false);
        secondPanel.add(checkbox3);

        label3 = new JLabel();
        label3.setText("Line Width:");
        label3.setHorizontalAlignment(label3.CENTER);
        label3.setFont(new Font("Serif", Font.BOLD, 15));
        secondPanel.add(label3);

        SpinnerModel width = new SpinnerNumberModel(4, 0, 50, 1);
        spinner1 = new JSpinner(width);
        secondPanel.add(spinner1);
        spinner1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSpinner spinner = (JSpinner) e.getSource();
                Object value = spinner.getValue();
                lineWidth = (int)value;
            }
        });

        label4 = new JLabel();
        label4.setText("Dash Length:");
        label4.setHorizontalAlignment(label4.CENTER);
        label4.setFont(new Font("Serif", Font.BOLD, 15));
        secondPanel.add(label4);

        SpinnerModel length = new SpinnerNumberModel(15, 0, 100, 1);
        spinner2 = new JSpinner(length);
        secondPanel.add(spinner2);
        spinner2.addChangeListener(new ChangeListener() {
                                       @Override
                                       public void stateChanged(ChangeEvent e) {
                                           JSpinner spinner = (JSpinner) e.getSource();
                                           Object value = spinner.getValue();
                                           dashLength[0] = Float.parseFloat(value.toString());
                                       }
                                   });

        bottomPanel = new JPanel();
        drawPanel = new DrawPanel();

        label5 = new JLabel(drawPanel.pos);
        label5.setFont(new Font("Serif", Font.BOLD, 15));
        bottomPanel.add(label5);


        // add top panel of two panels
        firstPanel.setPreferredSize(new Dimension(100, 100));
        secondPanel.setPreferredSize(new Dimension(100, 100));
        main.setPreferredSize(new Dimension(100, 100));
        bottomPanel.setPreferredSize(new Dimension(100, 25));
        Color color1 = new Color(127, 205, 196);
        Color color2 = new Color(192, 192, 192);
        firstPanel.setBackground(color1);
        secondPanel.setBackground(color1);
        main.setBackground(color1);
        bottomPanel.setBackground((color2));

        // add topPanel to North, drawPanel to Center, and statusLabel to South
        this.add(main, BorderLayout.NORTH);
        this.add(bottomPanel, BorderLayout.SOUTH);
        this.add(drawPanel, BorderLayout.CENTER);
        //this.add(secondPanel, BorderLayout.NORTH);
        //add listeners and event handlers
    }


    private class ComboBoxHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            shape = (String) ((JComboBox) e.getSource()).getSelectedItem();
            System.out.println(shape);
        }
    }

    private class Button1Handler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            color = color1;
            oriColor = color1;
        }

    }

    private class Button2Handler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            color = color2;
            oriColor = color2;
        }

    }

    private class Button3Handler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            set.remove(set.size()-1);
            repaint();
        }

    }

    private class Button4Handler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            set.clear();
            repaint();
        }

    }

    private class Checkbox1Handler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (checkbox1.isSelected()) {

                fill = true;
            }
            else{
                fill = false;
            }
        }

    }

    private class Checkbox2Handler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (checkbox2.isSelected()) {
                color = paintG;
            }

            else {
                color = oriColor;
            }

        }

    }




        // Create event handlers, if needed

        // Create a private inner class for the DrawPanel.
        private class DrawPanel extends JPanel {
            private final JPanel drawPanel;
            private String pos;

            public void StartDraw() {
                dashLength[0] = Float.parseFloat(spinner2.getValue().toString());
                lineWidth = Integer.parseInt(spinner1.getValue().toString());

                if (checkbox3.isSelected()) {
                    stroke = new BasicStroke(lineWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10
                            , dashLength, 0);

                } else {
                    stroke = new BasicStroke(lineWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
                }

                if (Objects.equals(shape, "Line")) {
                    myShapes = new MyLine(pointA, pointB, color, stroke);

                }

                else if (Objects.equals(shape, "Rectangle")) {
                    myShapes = new MyRectangle(pointA, pointB, color, stroke, fill);

                }

                else if (Objects.equals(shape, "Oval")) {
                    myShapes = new MyOval(pointA, pointB, color, stroke, fill);

                }
            }


            public DrawPanel() {
                drawPanel = new JPanel();
                drawPanel.setPreferredSize(new Dimension(650, 500));
                MouseHandler handler = new MouseHandler();
                dashLength = new float[1];
                this.addMouseListener(handler);
                this.addMouseMotionListener(handler);
                set = new ArrayList<>();
            }

            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                //loop through and draw each shape in the shapes arraylist
                for (MyShapes d : set) {
                    d.draw(g2d);

                }
            }


            private class MouseHandler extends MouseAdapter implements MouseMotionListener {

                public void mousePressed(MouseEvent event) {
                    pointA = new Point(event.getX(), event.getY());
                    pointB = pointA;
                    StartDraw();
                    set.add(myShapes);


                }

                public void mouseReleased(MouseEvent event) {
                    pointB = new Point(event.getX(), event.getY());
                    set.get(set.size() - 1).setEndPoint(pointB);
                    repaint();
                }

                @Override
                public void mouseDragged(MouseEvent event) {
                    pointC = event.getPoint();
                    set.get(set.size() - 1).setEndPoint(pointC);
                    repaint();
                }

                @Override
                public void mouseMoved(MouseEvent event) {
                    pos = "(" + event.getPoint().x + "," + event.getPoint().y + ")";
                    label5.setText(pos);
                }
            }

        }
    }

