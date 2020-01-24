import java.awt.*;

public class Bus extends Vehicle {
    Bus(Road road){
        super(road);
        width = 45;
        height = 15;
    }
    public void horizontalPaint(Graphics g){
        g.setColor(Color.GREEN);
        g.fillRect(posX, posY, width, height);
    }
    public void verticalPaint(Graphics g){
        g.setColor(Color.GREEN);
        g.fillRect(posY, posX, height, width);
    }
}
