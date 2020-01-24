public class TrafficLight {
    private double changeRate = 0.7;
    private String trafficColor = "green";
    private int redPeriod = 0;
    private int greenPeriod = 0;


    public String getTrafColor(){
        return trafficColor;
    }

    public void operate(){

        if (trafficColor.equals("red")){ //forces the light to stay red for a set number of cycles
            changeRate = 1;
            redPeriod += 1;
        }
        else{
            changeRate = 0;
            greenPeriod +=1;
        }

        if(redPeriod == 10 || greenPeriod == 6){  // gets the light changing colors after a certain amount of time elapses
            redPeriod = 0;
            greenPeriod = 0;
            changeRate = 0.3;
        }
        double num = Math.random(); // gets random number between 0 and 1
        if(num < changeRate) {  // checks whether light should be green or red
            trafficColor = "red";
        }
        else
            trafficColor = "green";
    }
}
