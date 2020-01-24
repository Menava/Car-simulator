import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Simulator implements ActionListener, Runnable, MouseListener {
    private int x, y;
    private boolean running = false;
    private JFrame frame = new JFrame("Car Traffic Simulation");
    private TrafficLight tf = new TrafficLight();
    Road startRoad = new Road(6, "horizontal",0, 270, "east", tf); // fixed starting road on map
    private int getX(){
        return x;
    }
    private int getY(){
        return y;
    }
    //north container
    private JLabel info = new JLabel("Click on screen to select x,y position");
    private JLabel labelXPosField = new JLabel("Road x position");
    private JTextField xPosField = new JTextField("0");
    private JLabel labelYPosField = new JLabel("Road y position");
    private JTextField yPosField = new JTextField("0");
    private Container north = new Container();

    //south container
    private JButton startSim = new JButton("Start");
    private JButton exitSim = new JButton("Exit");
    private JButton removeRoad = new JButton("Remove Last Road");
    private Container south = new Container();

    //west container
    private Container west = new Container();
    private JButton addSedan = new JButton("Add car");
    private JButton addBus = new JButton("Add bus");
    private JButton addRoad = new JButton("Add road");
    //road orientation selection
    private ButtonGroup selections = new ButtonGroup();
    private JRadioButton horizontal = new JRadioButton("horizontal");
    private JRadioButton vertical = new JRadioButton("vertical");
    //has traffic light selection
    private ButtonGroup selections2 = new ButtonGroup();
    private JRadioButton hasLight = new JRadioButton("Traffic light(True)");
    private JRadioButton noLight = new JRadioButton("Traffic light(false)");
    //road length
    private JLabel label = new JLabel("Enter road length");
    private JTextField length = new JTextField("5");
    //traffic direction
    private ButtonGroup selections3 = new ButtonGroup();
    private JRadioButton northDirection = new JRadioButton("north");
    private JRadioButton southDirection = new JRadioButton("south");
    private JRadioButton westDirection = new JRadioButton("west");
    private JRadioButton eastDirection = new JRadioButton("east");

    private Simulator(){

        Map.roadList.add(startRoad);
        frame.setSize(1200,700);
        frame.setLayout(new BorderLayout());
        frame.add(startRoad, BorderLayout.CENTER);
        startRoad.addMouseListener(this);

        //north side info
        north.setLayout(new GridLayout(1, 5));
        north.add(info);
        north.add(labelXPosField);
        north.add(xPosField);
        north.add(labelYPosField);
        north.add(yPosField);
        frame.add(north, BorderLayout.NORTH);

        //buttons on the south side
        south.setLayout(new GridLayout(1, 3));
        south.add(startSim);
        startSim.addActionListener(this);
        south.add(exitSim);
        exitSim.addActionListener(this);
        south.add(removeRoad);
        removeRoad.addActionListener(this);
        frame.add(south, BorderLayout.SOUTH);

        //buttons on west side
        west.setLayout(new GridLayout(13,1));
        west.add(addSedan);
        addSedan.addActionListener(this);
        west.add(addBus);
        addBus.addActionListener(this);
        west.add(addRoad);
        addRoad.addActionListener(this);
        west.add(label);
        west.add(length);
        length.addActionListener(this);

        //radio buttons on west side
        selections.add(vertical);
        selections.add(horizontal);
        west.add(vertical);
        vertical.addActionListener(this);
        horizontal.setSelected(true);
        west.add(horizontal);
        horizontal.addActionListener(this);

        selections2.add(hasLight);
        selections2.add(noLight);
        west.add(hasLight);
        hasLight.addActionListener(this);
        west.add(noLight);
        noLight.addActionListener(this);
        noLight.setSelected(true);

        selections3.add(northDirection);
        selections3.add(southDirection);
        selections3.add(eastDirection);
        selections3.add(westDirection);
        west.add(northDirection);
        northDirection.addActionListener(this);
        northDirection.setEnabled(false);
        west.add(southDirection);
        southDirection.addActionListener(this);
        southDirection.setEnabled(false);
        west.add(eastDirection);
        eastDirection.addActionListener(this);
        eastDirection.setSelected(true);
        west.add(westDirection);
        westDirection.addActionListener(this);

        frame.add(west, BorderLayout.WEST);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Map.trafficList.add(tf);
        frame.repaint();

    }

    public static void main(String[] args){
        new Simulator();
        Map map = new Map();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(horizontal.isSelected()){
            northDirection.setEnabled(false);
            southDirection.setEnabled(false);
            eastDirection.setEnabled(true);
            westDirection.setEnabled(true);
        }
        else if(vertical.isSelected()){
            eastDirection.setEnabled(false);
            westDirection.setEnabled(false);
            northDirection.setEnabled(true);
            southDirection.setEnabled(true);
        }
        if(source == startSim){
            if(!running) {
                running = true;
                Thread t = new Thread(this);
                t.start();
            }
        }
        if(source == removeRoad){
            if(Map.roadList.size()>1) {
                Map.roadList.remove(Map.roadList.size() - 1);
                frame.repaint();
            }
        }
        if(source == addBus){
            Bus bus = new Bus(startRoad);
            Map.carList.add(bus);
            for (int x = startRoad.roadPosX; x < bus.getRoadOfCar().getRoadLength()*50; x = x + 30) {
                bus.setCarPositionX(x);
                bus.setCarPositionY(bus.getRoadOfCar().getRoadPosY()+5);
                if(!bus.ifCollision(x, bus)){
                    frame.repaint();
                    return;
                }
            }
        }
        if(source == addSedan){
            Car sedan = new Car(startRoad);
            Map.carList.add(sedan);
            sedan.setCarPositionY(sedan.getRoadOfCar().getRoadPosY()+5);
            for (int x = startRoad.roadPosX; x < sedan.getRoadOfCar().getRoadLength()*50; x = x + 30) {
                sedan.setCarPositionX(x);
                if(!sedan.ifCollision(x, sedan)){
                    frame.repaint();
                    return;
                }
            }
        }
        if(source == addRoad){
            int roadLength = 5;
            String orientation = "horizontal";
            String direction = "east";
            int xPos = 0;
            int yPos = 0;
            Boolean lightOnRoad = false;
            if(vertical.isSelected()){
                orientation = "vertical";
            }
            else if(horizontal.isSelected()){
                orientation = "horizontal";
            }
            if(hasLight.isSelected()){
                lightOnRoad = true;
            }
            else if(noLight.isSelected()){
                lightOnRoad = false;
            }
            if(eastDirection.isSelected()){ direction = "east";}
            else if(westDirection.isSelected()) { direction = "west";}
            else if(northDirection.isSelected()) { direction = "north";}
            else if(southDirection.isSelected()){direction = "south";}

            if (orientation.equals("horizontal")){
                yPos = Integer.parseInt(yPosField.getText());
                xPos = Integer.parseInt(xPosField.getText());
            }
            else if(orientation.equals("vertical")){
                xPos = Integer.parseInt(yPosField.getText());
                yPos = Integer.parseInt(xPosField.getText());
            }
            try {
                roadLength = Integer.parseInt(length.getText());
            }
            catch (Exception error) {
                JOptionPane.showMessageDialog(null, "road length needs an integer");
                length.setText("5");
            }
            if(lightOnRoad) {
                Road road = new Road(roadLength, orientation, xPos, yPos, direction, new TrafficLight());
                Map.roadList.add(road);
            }
            else{
                Road road = new Road(roadLength, orientation, xPos, yPos, direction);
                Map.roadList.add(road);
            }
            frame.repaint();

        }
        if(source == exitSim){
            System.exit(0);
        }
    }
    @Override
    public void mouseClicked(MouseEvent e){
        x = e.getX();
        y = e.getY();
        xPosField.setText(Integer.toString(getX()));
        yPosField.setText(Integer.toString(getY()));
    }
    @Override
    public void mousePressed(MouseEvent e){}

    @Override
    public void mouseReleased(MouseEvent e){}

    @Override
    public void mouseEntered(MouseEvent e){}

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void run() {
        boolean carCollision = false;
        ArrayList<Boolean> trueCases = new ArrayList<Boolean>();
        while (running) {
            try {
                Thread.sleep(300);
            }
            catch (Exception ignored) {
            }
            for (int j = 0; j < Map.roadList.size(); j++) {
                Road r = Map.roadList.get(j);
                TrafficLight l = r.getTrafficLight();
                if(l != null) {
                    l.operate();
                    if (l.getTrafColor().equals("red")) {
                        r.setTrafColor(Color.red);
                    }
                    else{
                        r.setTrafColor(Color.GREEN);
                    }
                }

            }
            for (int i = 0; i < Map.carList.size(); i++) {
                Vehicle currentCar = Map.carList.get(i);
                String direction = currentCar.getRoadOfCar().getTrafficDirection();
                if(!currentCar.ifCollision(currentCar.getCarPositionX() + 30, currentCar) && (direction.equals("east") || direction.equals("south"))
                        || !currentCar.ifCollision(currentCar.getCarPositionX(), currentCar) && (direction.equals("west") || direction.equals("north"))){
                    currentCar.move();
                }
                else{
                    for(int z = 0; z< Map.carList.size(); z++) {
                        Vehicle otherCar = Map.carList.get(z);
                        if (otherCar.getCarPositionY() != currentCar.getCarPositionY()) {
                            if (currentCar.getCarPositionX() + currentCar.getCarWidth() < otherCar.getCarPositionX()) {
                                trueCases.add(true); // safe to switch lane
                            }
                            else {
                                trueCases.add(false); // not safe to switch lane
                            }
                        }
                    }
                    for (int l = 0; l < trueCases.size(); l++) {
                        if (!trueCases.get(l)) {
                            carCollision = true;
                            break;
                        }
                    }
                    if(!carCollision){
                        currentCar.setCarPositionY(currentCar.getRoadOfCar().getRoadPosY() + 30);
                    }
                    for(int m =0; m<trueCases.size(); m++){
                        trueCases.remove(m);
                    }
                    carCollision = false;
                }

            }
            frame.repaint();
        }
    }
}
