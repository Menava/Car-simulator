import java.awt.*;
import java.util.ArrayList;

public class Vehicle {
    private Road road; // road that the car is on
    protected int posY; // current position on map
    protected int posX; // current position on map
    protected int  height;
    protected int width;
    public void horizontalPaint(Graphics g){
    }
    public void verticalPaint(Graphics g){
    }
    Vehicle(Road road){
        this.road = road;
        posY = getRoadOfCar().roadPosY;
        posX = getRoadOfCar().roadPosX;
    }

    public Road getRoadOfCar(){
        return road;
    }

    public int getCarPositionX(){ return posX; }
    public void setCarPositionX(int x){
        posX = x;
    }
    public int getCarPositionY(){ return posY; }
    public void setCarPositionY(int y){
        posY = y;
    }
    public int getCarWidth(){return width;}

    private void setRoad(Road road){
        this.road = road;
    }
    private boolean ifEndOfRoad(){
        if(getRoadOfCar().getTrafficDirection().equals("east") || getRoadOfCar().getTrafficDirection().equals("south")){
            return (posX +width >= getRoadOfCar().getRoadEndPosX());
        }
        else if(getRoadOfCar().getTrafficDirection().equals("west") || getRoadOfCar().getTrafficDirection().equals("north")){
            return (posX <= road.getRoadPosX());
        }
        else
            return true;
    }
    public boolean ifCollision(int x, Vehicle car){
        String direction = getRoadOfCar().getTrafficDirection();
        for (int i = 0; i < Map.carList.size(); i++) {
            Vehicle c = Map.carList.get(i);
            if (c.getRoadOfCar() == getRoadOfCar() && car.getCarPositionY() == c.getCarPositionY()) {
                int otherCarXPosition = c.getCarPositionX();
                int otherCarWidth = c.getCarWidth();
                if (!car.equals(c)) { // if not checking current car
                    if (x < otherCarXPosition + otherCarWidth && //left side is left  of cars right side
                            x + otherCarWidth > otherCarXPosition && (direction.equals("east") || direction.equals("south"))) { // right side right of his left side
                        return true;
                    }
                    else if (x < otherCarXPosition + otherCarWidth * 2 - 15 && x + car.getCarWidth() > otherCarXPosition &&
                            (direction.equals("west") || direction.equals("north"))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    private boolean canMoveForward(){
        String direction = getRoadOfCar().getTrafficDirection();
        if(posX +width >= getRoadOfCar().getRoadLength()*25-25+ getRoadOfCar().getRoadPosX() && (direction.equals("east") || direction.equals("south"))
                || posX <= getRoadOfCar().getRoadPosX()+25 && ( direction.equals("west") || direction.equals("north") )) {
            if (getRoadOfCar().getTrafficLight() == null) {
                return true;
            }
            else {
                TrafficLight light = getRoadOfCar().getTrafficLight();
                return light.getTrafColor().equals("green");
            }
        }
        return true;
    }
    private int getRoadIndex(){
        return Map.roadList.indexOf(road);
    }
    private Road nextRoad(){
        int otherRoadXPos;
        int otherRoadYPos;
        int otherRoadEndXPos;
        int otherRoadEndYPos;
        int currentRoadXPos;
        int currentRoadYPos;
        int currentRoadEndXPos;
        int currentRoadEndYPos;
        Road currentRoad = Map.roadList.get(getRoadIndex());
        Road nextRoad = Map.roadList.get(0);
        ArrayList<Integer> xPositions = new ArrayList<Integer>();
        ArrayList<Integer> yPositions = new ArrayList<Integer>();
        if(currentRoad.getOrientation().equals("vertical")){
            currentRoadXPos = currentRoad.getRoadPosY();
            currentRoadYPos = currentRoad.getRoadPosX();
            currentRoadEndXPos = currentRoad.getRoadEndPosY();
            currentRoadEndYPos = currentRoad.getRoadEndPosX();
        }
        else{
            currentRoadXPos = currentRoad.getRoadPosX();
            currentRoadYPos = currentRoad.getRoadPosY();
            currentRoadEndXPos = currentRoad.getRoadEndPosX();
            currentRoadEndYPos = currentRoad.getRoadEndPosY();
        }
        for (int i = 0; i < Map.roadList.size(); i++) {
            Road r = Map.roadList.get(i);
            if(r != currentRoad) {

                if(r.getOrientation().equals("horizontal")){
                    otherRoadXPos = r.getRoadPosX();
                    otherRoadYPos = r.getRoadPosY();
                    otherRoadEndXPos = r.getRoadEndPosX();
                    otherRoadEndYPos = r.getRoadEndPosY();
                }
                else{
                    otherRoadXPos = r.getRoadPosY();
                    otherRoadYPos = r.getRoadPosX();
                    otherRoadEndXPos = r.getRoadEndPosY();
                    otherRoadEndYPos = r.getRoadEndPosX();
                }
                if(currentRoad.getTrafficDirection().equals("east") && otherRoadXPos > currentRoadEndXPos) {
                    xPositions.add(otherRoadXPos);
                }
                else if(currentRoad.getTrafficDirection().equals("west") && otherRoadEndXPos < currentRoadXPos ){
                    xPositions.add(otherRoadEndXPos);
                }
                else if(currentRoad.getTrafficDirection().equals("north") && otherRoadEndYPos < currentRoadYPos) {
                    yPositions.add(otherRoadEndYPos);
                }
                else if(currentRoad.getTrafficDirection().equals("south") && otherRoadYPos > currentRoadEndYPos){
                    yPositions.add(otherRoadYPos);
                }
            }
        }
        int num;
        int num2;
        num = getCarPositionX(); //trying to find road with x position closest to this x position
        num2 = getCarPositionY(); // trying to find road with y position closest to this y position
        int index = 0;
        int index2 =0;
        int difference_1 = 10000;
        int difference_2 = 10000;
        if(currentRoad.getTrafficDirection().equals("east") || currentRoad.getTrafficDirection().equals("west")) {
            for (int j = 0; j < xPositions.size(); j++) { // loops through every position
                int Difference_x = Math.abs(xPositions.get(j) - num);
                if (Difference_x < difference_1) { // checks if difference is getting smaller
                    index = j;
                    difference_1 = Difference_x;
                }
            }
        }
        else if(currentRoad.getTrafficDirection().equals("south") || currentRoad.getTrafficDirection().equals("north")) {
            for (int j = 0; j < xPositions.size(); j++) { // loops through every position
                int Difference_y = Math.abs(yPositions.get(j) - num2);
                if (Difference_y < difference_2) { // checks if difference is getting smaller
                    index2 = j;
                    difference_2 = Difference_y;
                }
            }
        }
        int closestXPosition = 0;
        int closestYPosition = 0;
        if(currentRoad.getTrafficDirection().equals("east") || currentRoad.getTrafficDirection().equals("west")){
            closestXPosition = xPositions.get(index);
        }
        else {
            closestYPosition = yPositions.get(index2);
        }
        System.out.println(closestXPosition);

        for(int z = 0; z<Map.roadList.size(); z++){
            Road r = Map.roadList.get(z);
            if ((r.getRoadPosX() == closestXPosition || r.getRoadEndPosX() == closestXPosition) && r.getOrientation().equals("horizontal")) {
                nextRoad = r;
            }
            else if ((r.getRoadPosY() == closestXPosition || r.getRoadEndPosY() == closestXPosition) && r.getOrientation().equals("vertical")){
                nextRoad = r;
            }
            if ((r.getRoadPosY() == closestYPosition || r.getRoadEndPosX() == closestYPosition) && r.getOrientation().equals("horizontal")) {
                nextRoad = r;
            }
            else if ((r.getRoadPosX() == closestYPosition || r.getRoadEndPosX() == closestYPosition) && r.getOrientation().equals("vertical")){
                nextRoad = r;
            }
        }
        xPositions.clear();
        yPositions.clear();
        return nextRoad;
    }


    public void move() {
        if(canMoveForward()) {
            if(road.getTrafficDirection().equals("east") || road.getTrafficDirection().equals("south")) {
                posX += 25;
            }
            else if(road.getTrafficDirection().equals("west") || road.getTrafficDirection().equals("north")){
                posX -= 25;
            }
            if (ifEndOfRoad()) {
                try {
                    Road r = nextRoad();
                    setRoad(r);
                    if(r.getOrientation().equals("horizontal") && r.getTrafficDirection().equals("east") || r.getOrientation().equals("vertical") && r.getTrafficDirection().equals("south")) {
                        for (int x = r.getRoadPosX(); x + getCarWidth() < r.getRoadLength()*25+ r.getRoadEndPosX(); x = x + 30) {
                            setCarPositionX(x);
                            setCarPositionY(getRoadOfCar().getRoadPosY()+5);
                            if(!ifCollision(x, this)){
                                return;
                            }
                        }
                    }
                    else if(r.getOrientation().equals("horizontal") && r.getTrafficDirection().equals("west") || r.getOrientation().equals("vertical") && r.getTrafficDirection().equals("north")){
                        for (int x = r.getRoadPosX() + r.getRoadLength()*25 - getCarWidth(); x > r.getRoadPosX(); x = x - 30) {
                            setCarPositionX(x);
                            setCarPositionY(getRoadOfCar().getRoadPosY()+5);
                            if(!ifCollision(x, this)){
                                return;
                            }
                        }
                    }
                }
                catch (IndexOutOfBoundsException e){
                    setRoad(road);
                    posX = road.getRoadPosX();
                    posY = road.getRoadPosY() + 5;
                }
            }
        }

    }

}
