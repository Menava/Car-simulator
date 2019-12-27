public class Vehicle {
    String name; // name of vehicle
    int position; // position of vehicle on current road
    Road currentRoad; // vehicle current road
    public Vehicle() { //default constructor
    }

    public Vehicle(String name, int position, Road currentRoad) {
        this.name = name;
        this.position = position;
        this.currentRoad = currentRoad;
    }

    public void move(){
        this.position+=1;
    } // this method will make vehicle move on road
}
