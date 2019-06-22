import java.awt.*;
import java.util.ArrayList;

public class Ellipse extends MyShape {
    int rx;
    int ry;
    private ArrayList<Dot> EllipseSem(int x, int y, int currentX, int currentY) {
        ArrayList<Dot> res = new ArrayList<>();
        Dot p1 = new Dot(x + currentX, y + currentY);
        Dot p2 = new Dot(x + currentX, y - currentY);
        Dot p3 = new Dot(x - currentX, y + currentY);
        Dot p4 = new Dot(x - currentX, y - currentY);
        res.add(p1);
        res.add(p2);
        res.add(p3);
        res.add(p4);
        return res;
    }
    Ellipse(int ID, Color color, Dot center, int a, int b) {
        super(ID, color, 1);
        this.Center = center;
        this.rx = a;
        this.ry = b;
        double sqa = a * a;
        double sqb = b * b;
        double d = sqb + sqa * (-b + 0.25);
        int x = 0;
        int y = b;
        this.dots.addAll(EllipseSem(Center.x, Center.y, x, y));
        while (sqb * (x + 1) < sqa * (y - 0.5)) {
            if (d < 0) {
                d += sqb * (2 * x + 3);
            } else {
                d += (sqb * (2 * x + 3) + sqa * (-2 * y + 2));
                y--;
            }
            x++;
            this.dots.addAll(EllipseSem(Center.x, Center.y, x, y));
        }
        d = (b * (x + 0.5)) * 2 + (a * (y - 1)) * 2 - (a * b) * 2;
        while (y > 0) {
            if (d < 0) {
                d += sqb * (2 * x + 2) + sqa * (-2 * y + 3);
                x++;
            } else {
                d += sqa * (-2 * y + 3);
            }
            y--;
            this.dots.addAll(EllipseSem(Center.x, Center.y, x, y));
        }
    }
    public Ellipse scale(Dot scaleCenter, float s){
        int nx = (int)(s * rx);
        int ny = (int)(s * ry);
        return new Ellipse(this.picID, this.shapeColor,this.Center, nx, ny);
    }
}
