import java.awt.*;
import java.util.Iterator;

public class Line extends MyShape {
    Dot start;
    Dot end;
    int Algorithm;
    Line(int ID, Color color, Dot start, Dot end, int Algorithm){
        super(ID, color, 0);
        this.start = start;
        this.end = end;
        this.Algorithm = Algorithm;
        Center = new Dot((start.x + end.x) / 2, (start.y + start.y) / 2);
        if(Algorithm == 0){
            //DDA
            System.out.println("DDA");
            int dx = end.x - start.x;
            int dy = end.y - start.y;
            System.out.println("dx is "+ dx + ", dy is "+ dy);
            int steps;
            float xIncrement, yIncrement, x = start.x, y = start.y;
            steps = Math.max(Math.abs(dx), Math.abs(dy));
            xIncrement = ((float)dx) / ((float)steps);
            yIncrement = ((float)dy) / ((float)steps);
            //System.out.println("xi is "+ xIncrement + ", ys is " + yIncrement);
            while(x != end.x && y != end.y){
                Dot currentDot = new Dot((int)x, (int)y);
                //System.out.println(currentDot.x + ", " + currentDot.y);
                this.dots.add(currentDot);
                x += xIncrement;
                y += yIncrement;
                //try{Thread.sleep(200);}catch(Exception e){}
            }
            this.dots.add(end);
        }
        else{
            //Bresenham
            int x = start.x;
            int y = start.y;
            int dx = Math.abs(end.x - start.x);
            int dy = Math.abs(end.y - start.y);
            int s1 = (end.x>start.x)?1:-1;
            int s2 = (end.y>start.y)?1:-1;
            int swap = 0;
            if(dy>dx) {
                int temp = dx;
                dx = dy;
                dy = temp;
                swap = 1;
            }
            int p = 2 * dy - dx;
            for(int i = 0; i <= dx; i++) {
                Dot currentDot = new Dot(x, y);
                this.dots.add(currentDot);
                if (p >= 0) {
                    if (swap == 1) x = x + s1;
                    else y = y + s2;
                    p = p - 2 * dx;
                }
                if (swap == 1) y = y + s2;
                else x = x + s1;
                p = p + 2 * dy;
            }
        }
    }
    public Line scale(Dot scaleCenter, float s){
        int startX = (int)((start.x - scaleCenter.x) * s + scaleCenter.x);
        int startY = (int)((start.y - scaleCenter.y) * s + scaleCenter.y);

        int endX = (int)((end.x - scaleCenter.x) * s + scaleCenter.x);
        int endY = (int)((end.y - scaleCenter.y) * s + scaleCenter.y);
        Line newLine = new Line(this.picID, this.shapeColor, new Dot(startX, startY), new Dot(endX, endY), this.Algorithm);
        return newLine;
    }
    public int[] set_ind(Dot d, int M, int m, int T, int t){
        int[] result = new int[4];
        result[0] = (d.x < m)?1:0;
        result[1] = (d.x > M)?1:0;
        result[2] = (d.y < t)?1:0;
        result[3] = (d.y > T)?1:0;
        return result;
    }
    public void clip(Dot cstart, Dot cend, int Algorithm){
            int l = Math.min(cstart.x, cend.x);
            int r = Math.max(cstart.x, cend.x);
            int u = Math.max(cstart.y, cend.y);
            int b = Math.min(cstart.y, cend.y);
            Iterator<Dot> it = this.dots.iterator();
            while(it.hasNext()){
                Dot d = it.next();
                if(l <= d.x && d.x <= r && b <= d.y && d.y <= u){
                }
                else it.remove();
            }
    }
}
