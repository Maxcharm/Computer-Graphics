import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class Polygon extends MyShape {
    int Algorithm;
    int edgeNum;
    ArrayList<Dot> Vertices;
    Polygon(int ID, Color color, int edgeNum, int Algorithm, ArrayList<Dot> Vertices){
        super(ID, color, 3);
        this.edgeNum = edgeNum;
        this.Algorithm = Algorithm;
        this.Vertices = Vertices;
        Dot eDot = Vertices.get(edgeNum - 1);
        Dot sDot = Vertices.get(0);
        Line l1 = new Line(-1, color, sDot, eDot, this.Algorithm);
        this.dots.addAll(l1.dots);
        for(int i = 0; i < edgeNum - 1; i++){
            sDot = Vertices.get(i);
            eDot = Vertices.get(i + 1);
            l1 = new Line(-1, color, sDot, eDot, this.Algorithm);
            this.dots.addAll(l1.dots);
        }
        this.Center = getCenter(Vertices);

    }
    public Dot getCenter(ArrayList<Dot> Vertices){
        ArrayList<Integer> xset = new ArrayList<>();
        ArrayList<Integer> yset = new ArrayList<>();
        for(int i = 0; i < edgeNum; i++){
            xset.add(Vertices.get(i).x);
            yset.add(Vertices.get(i).y);
        }
        Dot center = new Dot((Collections.max(xset) + Collections.min(xset)) / 2, (Collections.max(yset) + Collections.min(yset)) / 2);
        return center;
    }
    public Polygon scale(Dot scaleCenter, float s){
        for(Dot d:Vertices){
            d.x = (int)((d.x - scaleCenter.x) * s + scaleCenter.x);
            d.y = (int)((d.y - scaleCenter.y) * s + scaleCenter.y);
        }
        return new Polygon(this.picID, this.shapeColor, this.edgeNum, this.Algorithm, this.Vertices);
    }
}
