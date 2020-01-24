import javax.swing.*;
import java.awt.*;

public class Road extends JPanel {
    private TrafficLight light;
    private int roadLength; //length of road
    private final int roadWidth = 100;
    final int roadPosY;
    final int roadEndPosY;
    final int roadPosX;
    final int roadEndPosX;
    private Color trafColor = Color.green;
    private String orientation;
    String trafficDirection;

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, 1200, 1200);

        for(int z = 0; z < Map.roadList.size(); z++){
            Road r = Map.roadList.get(z);
            r.paintRoad(g);
            if(r.getOrientation().equals("vertical")){
                for (int c = 0; c < Map.carList.size(); c++) {
                    Vehicle car = Map.carList.get(c);
                    if(car.getRoadOfCar().equals(r)) {
                        car.verticalPaint(g);
                    }
                }
                if (r.getTrafficLight() != null) {
                    r.paintLight(g);
                }
            }
            else{
                for (int c = 0; c < Map.carList.size(); c++) {
                    Vehicle car = Map.carList.get(c);
                    if(car.getRoadOfCar().equals(r)) {
                        car.horizontalPaint(g);
                    }
                }
                if (r.getTrafficLight() != null) {
                    r.paintLight(g);
                }
            }
        }
    }

    // paints traffic light
    public void paintLight(Graphics g){
        g.setColor(trafColor);
        if(getOrientation().equals("horizontal")) {
            if (getTrafficDirection().equals("east")) {
                g.fillRect(roadPosX + roadLength * 25 - 10, roadPosY - 20, 10, 20);
                g.setColor(Color.black);
                g.drawRect(roadPosX + roadLength * 25 - 10, roadPosY - 20, 10, 20);
            } else {
                g.fillRect(roadPosX, roadPosY - 20, 10, 20);
                g.setColor(Color.black);
                g.drawRect(roadPosX, roadPosY - 20, 10, 20);
            }
        }
        else{
            if (getTrafficDirection().equals("south")) {
                g.fillRect(roadPosY - 20, roadPosX + roadLength * 25 - 10, 20, 10);
                g.setColor(Color.black);
                g.drawRect(roadPosY - 20, roadPosX + roadLength * 25 - 10, 20, 10);
            }
            else{
                g.fillRect(roadPosY - 20, roadPosX, 20, 10);
                g.setColor(Color.black);
                g.drawRect(roadPosY - 20, roadPosX, 20, 10);
            }
        }
    }

    public void paintRoad(Graphics g){
        if(orientation.equals("horizontal")) {
            g.setColor(Color.black);
            g.fillRect(roadPosX, roadPosY, roadLength * 25, roadWidth);
            g.setColor(Color.WHITE);
            for (int j = 0; j < roadLength * 25; j = j + 50) { // line being drawn
                g.fillRect(roadPosX + j, roadPosY + roadWidth / 2, 30, 2);
            }
        }
        else{
            g.setColor(Color.black);
            g.fillRect(roadPosY, roadPosX, roadWidth, roadLength * 25);
            g.setColor(Color.WHITE);
            for (int j = 0; j < roadLength * 25; j = j + 50) { // line being drawn
                g.fillRect( roadPosY + roadWidth / 2, roadPosX + j, 2,30);
            }
        }
    }

    Road(int roadLength, String orientation, int xPos, int yPos, String direction){
        super();
        this.roadLength = roadLength *2;
        this.orientation = orientation;
        this.roadPosX = xPos;
        this.roadPosY = yPos;
        this.roadEndPosX = xPos + roadLength * 50;
        this.roadEndPosY = yPos + roadLength * 50;
        this.trafficDirection = direction;
    }
    Road(int roadLength, String orientation, int xPos, int yPos, String direction, TrafficLight light){
        super();
        this.roadLength = roadLength *2;
        this.orientation = orientation;
        this.light = light;
        this.roadPosX = xPos;
        this.roadPosY = yPos;
        this.roadEndPosX = xPos + roadLength * 50;
        this.roadEndPosY = yPos + roadLength * 50;
        this.trafficDirection = direction;

    }
    public String getOrientation(){ return orientation;}
    public TrafficLight getTrafficLight(){
        return light;
    }
    public int getRoadLength(){
        return roadLength;
    }
    public int getRoadPosY(){
        return roadPosY;
    }
    public int getRoadPosX(){
        return roadPosX;
    }
    public int getRoadEndPosY(){
        return roadEndPosY;
    }
    public int getRoadEndPosX(){
        return roadEndPosX;
    }
    public String getTrafficDirection(){ return trafficDirection; }
    public void setTrafColor(Color c){
        trafColor = c;
    }

}
