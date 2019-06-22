import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class MyShape {
    ArrayList<Dot> dots = new ArrayList<>();
    int picID;
    Color shapeColor;
    Dot Center;
    int type;
    MyShape(int ID, Color color, int type){
        this.picID = ID;
        this.shapeColor = color;
        this.type = type;
    }
    public void rotate(Dot rDot, float angle){
        double cosine = Math.cos(angle);
        double sine = Math.sin(angle);
        Iterator<Dot> it = this.dots.iterator();
        ArrayList<Dot> newdots = new ArrayList<>();
        while(it.hasNext()) {
            Dot tbm = it.next();
            int nx = (int) ((tbm.x - rDot.x) * cosine - (tbm.y - rDot.y) * sine + rDot.x);
            int ny = (int) ((tbm.x - rDot.x) * sine + (tbm.y - rDot.y) * cosine + rDot.y);
            Dot newDot = new Dot(nx, ny);
            it.remove();
            newdots.add(newDot);
        }
        this.dots.addAll(newdots);
    }
    public void translate(float dx, float dy){
        for(Dot d:dots){
            d.x += (int)dx;
            d.y += (int)dy;
        }
    }
}