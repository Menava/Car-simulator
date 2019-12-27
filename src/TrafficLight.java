public class TrafficLight {
    String color; // the color of the traffic light
    double rateOfChange; // variable to determine the traffic light change

    public TrafficLight() { // default constructor
    }

    public TrafficLight(String color, double rateOfChange){
        this.color=color;
        this.rateOfChange=rateOfChange;
    }
    public void operate(){ // this method will change the color of traffic light
        if(rateOfChange<(Math.random()*((1.0-10.0)+1))+10.0) // generate random numbers to determine colors
            this.color="Red";
        else
            this.color="Green";
    }
}
