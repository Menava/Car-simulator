public class Simulator {

    public static void main(String[] args) throws InterruptedException,NullPointerException
    {

        Boolean end=false; //boolean to end the simulator or not
        //Declaring Vehicle variable
        Vehicle vehicle;
        vehicle=new Vehicle("Car",0,null);

        //Declaring traffic variable
        TrafficLight t1=new TrafficLight("Red",5.0);

        //Declaring Road variable
        Road road;
        Road r3=new Road("r3",6,vehicle,null,t1);
        Road r2=new Road("r2",5,vehicle,r3,t1);
        Road r1=new Road("r1",4,vehicle,r2,t1);


        vehicle.currentRoad=r1;
        road=vehicle.currentRoad;

        while(end==false){ //the simulator will keep running until user choose to end
            t1.operate(); // this will change traffic light color
            System.out.println("Vehicle:"+vehicle.name+" Road:"+vehicle.currentRoad.name+" Position:"+vehicle.position+" Traffic Light: "+t1.color);
            vehicle.move(); // vehicle will constantly moving until the loop end
            road.update(vehicle); // the road will update vehicle locations
            if(road.reached==true) // the vehicle will move to next road if it reached the end of current road
            {
                if(road.connectedRoad==null) // the simulator will end if there is no more connected road
                {
                    end=true;
                }
                road=vehicle.currentRoad;
            }
            Thread.sleep(2000);
        }
        System.out.println("Simulation finished");
    }
}
