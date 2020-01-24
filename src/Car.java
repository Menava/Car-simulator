import java.awt.*;

public class Car extends Vehicle {
    Car(Road road){
        super(road);
        width = 30;
        height = 12;
    }
    public void horizontalPaint(Graphics g){
        g.setColor(Color.CYAN);
        g.fillRect(posX, posY, width, height);
    }
    public void verticalPaint(Graphics g){
        g.setColor(Color.CYAN);
        g.fillRect(posY, posX, height, width);
    }
}
