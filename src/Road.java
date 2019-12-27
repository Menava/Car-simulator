public class Road {
    Object[]object; // this array will keep all the objects on road like cars and traffic light
    String name; //name of the road
    int length; // length of the object
    Vehicle vehicle=new Vehicle(); // vehicle object to store it in object
    TrafficLight trafficLight=new TrafficLight(); // traffic light object to store it in object
    Road connectedRoad; // the connected roads of this current road
    boolean reached=false; // if the vehicle reach the end of the road

    public Road(String name,int length,Vehicle vehicle, Road connectedRoad,TrafficLight trafficLight) {
        this.object=new Object[length];
        this.name=name;
        this.length=length;
        this.vehicle=vehicle;
        this.trafficLight=trafficLight;
        this.connectedRoad = connectedRoad;
        object[0]=vehicle;
        object[this.length-1]=trafficLight;
    }
    public void update(Vehicle vehicle) // this method will update vehicles positions in object array
    {
        if(vehicle.position<this.length) {
            object[vehicle.position-1]=null;
            this.object[vehicle.position] = this.vehicle;
        }
        else if(vehicle.position==this.length){
            if(trafficLight.color.equalsIgnoreCase("Green")) {
                vehicle.position = 0;
                vehicle.currentRoad = connectedRoad;
                reached = true;
            }
            else{
                vehicle.position-=1;
            }
        }
    }
}
