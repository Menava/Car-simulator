public class Vehicle {
    String name;
    int position;
    Road currentRoad;
    public Vehicle() {
    }

    public Vehicle(String name, int position, Road currentRoad) {
        this.name = name;
        this.position = position;
        this.currentRoad = currentRoad;
    }

    public void move(){
        this.position+=1;
    }
}
