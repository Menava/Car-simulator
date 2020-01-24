import java.util.ArrayList;

public class Map{
    static ArrayList<Road> roadList = new ArrayList<>();
    static ArrayList<Vehicle> carList = new ArrayList<>();
    static ArrayList<TrafficLight> trafficList = new ArrayList<>();
    public Map(){
    }

    public void addRoad(Road road){
        roadList.add(road);
    }
    public void addCar(Vehicle car){
        carList.add(car);
    }
    public void addTrafficLight(TrafficLight light) {
        trafficList.add(light);
    }


}
