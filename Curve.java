import com.sun.scenario.animation.SplineInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;

import java.awt.*;
import java.util.ArrayList;

public class Curve extends MyShape {
    ArrayList<Dot> ControlDots;
    int Algorithm;
    Curve(int ID, Color color, ArrayList<Dot> ControlDots, int Algorithm){
        super(ID, color, 2);
        this.ControlDots = ControlDots;
        Center = new Dot((this.ControlDots.get(0).x + this.ControlDots.get(ControlDots.size()-1).x) / 2, (this.ControlDots.get(0).y + this.ControlDots.get(ControlDots.size()-1).y) / 2);
        this.Algorithm = Algorithm;
        if(Algorithm == 0){
            //bezier
             int num = ControlDots.size() - 1;
             float u;
             for (u = 0; u <= 1; u += 0.01) {
                 Dot p[] = new Dot[num + 1];
                 for (int i = 0; i <= num; i++) {
                     p[i] = new Dot(ControlDots.get(i).x, ControlDots.get(i).y);
                 }
                 for (int r = 1; r <= num; r++) {
                     for (int i = 0; i <= num - r; i++) {
                         p[i].x = (int)((1 - u) * p[i].x + u * p[i + 1].x);
                         p[i].y = (int)((1 - u) * p[i].y + u * p[i + 1].y);
                     }
                 }
                 dots.add(p[0]);
             }
        }
    }
    public Curve scale(Dot scaleCenter, float size){
        for(Dot cd: ControlDots){
            int nx = (int)((cd.x - scaleCenter.x) * size + scaleCenter.x);
            int ny = (int)((cd.y - scaleCenter.y) * size + scaleCenter.y);
            cd.x = nx;
            cd.y = ny;
        }
        Curve c = new Curve(this.picID, this.shapeColor, ControlDots, Algorithm);
        return c;
    }
}
