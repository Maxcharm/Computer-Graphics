import javafx.util.Pair;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class MyGraphics {
    JFrame canvas = new JFrame();
    ArrayList<MyShape> shapes = new ArrayList<>();
    Color brushColor = null;
    int drawType;
    int currentID;
    class myPanel extends JPanel {
        public void paintComponent(Graphics g) {
            Color white = new Color(255, 255, 255);
            g.setColor(white);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            for (MyShape ms : shapes) {
                g.setColor(ms.shapeColor);
                for (Dot d : ms.dots) {
                    g.fillOval(d.x, d.y, 2, 2);
                }
            }
        }
    }
    public void setDrawType(int type){
        this.drawType = type;
    }
    myPanel cmdPanel;
    myPanel drawPanel;
    JPanel ButtonPanel;
    JPanel TopPanel;
    JPanel ColorPanel;
    JTextField Text_ID;
    Dot polyStart = null;
    ArrayList<Dot> polydots = new ArrayList<>();
    Dot plast;
    ArrayList<Dot> curdots = new ArrayList<>();
    int curCount = 0;
    int polyCount = 0;

    class LButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            setDrawType(0);
            //System.out.println("drawing a " + drawType);
        }
    }
    class EButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            setDrawType(1);
        }
    }
    class CButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            setDrawType(2);
        }
    }
    class PButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            setDrawType(3);
        }
    }
    class TButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            setDrawType(5);
        }
    }
    class RButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            setDrawType(4);
        }
    }
    class ClButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            setDrawType(6);
        }
    }
    class SButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            setDrawType(7);
        }
    }
    class ReButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            shapes = new ArrayList<>();
            drawPanel = new myPanel();
            canvas.repaint();
        }
    }
    class IDButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            currentID = Integer.parseInt(Text_ID.getText());
            //System.out.println("ID is " + currentID);
        }
    }
    class RedButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            brushColor = Color.RED;
        }
    }
    class BlueButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            brushColor = Color.BLUE;
        }
    }
    class YellowButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            brushColor = Color.YELLOW;
        }
    }
    class GreenButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            brushColor = Color.GREEN;
        }
    }
    class GrayButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            brushColor = Color.GRAY;
        }
    }
    class DrawPanelListener implements MouseListener{
        Dot start;
        Dot end;
        @Override
        public void mousePressed(MouseEvent e) {
            if(drawType != 2 && drawType != 3) {
                start = new Dot(e.getX(), e.getY());
            }
        }
        @Override
        public void mouseClicked(MouseEvent e) {
            // TODO Auto-generated method stub
            if(drawType == 3){
                if(polyCount == 0){
                    polyStart = new Dot(e.getX(), e.getY());
                    plast = polyStart;
                    polydots.add(polyStart);
                    polyCount++;
                }
                else if(polyCount == 1 || polyCount == 2){
                    polydots.add(new Dot(e.getX(), e.getY()));
                    Line p = new Line(-polyCount, brushColor, plast, new Dot(e.getX(), e.getY()), 0);
                    shapes.add(p);
                    canvas.repaint();
                    plast = new Dot(e.getX(), e.getY());
                    polyCount++;
                }
                else{
                    if(Math.abs(e.getY() - polyStart.y) < 5 && Math.abs(e.getX() - polyStart.x) < 5){
                        Polygon newpl = new Polygon(currentID, brushColor, polyCount,0, polydots);
                        for(int i = 1; i <= polyCount; i++){
                            for(MyShape sss: shapes){
                                if(sss.picID == -i){
                                    shapes.remove(sss);
                                    break;
                                }
                            }
                        }
                        shapes.add(newpl);
                        canvas.repaint();
                        polydots = new ArrayList<>();
                        polyCount = 0;
                    }
                    else{
                        Line p = new Line(-polyCount, brushColor, plast, new Dot(e.getX(), e.getY()), 0);
                        shapes.add(p);
                        canvas.repaint();
                        plast = new Dot(e.getX(), e.getY());
                        polyCount ++;
                        polydots.add(new Dot(e.getX(), e.getY()));
                    }
                }

            }
            else if(drawType == 2){
                curCount++;
                curdots.add(new Dot(e.getX(), e.getY()));
                if(curCount == 4){
                    curCount = 0;
                    Curve newcv = new Curve(currentID, brushColor, curdots, 0);
                    shapes.add(newcv);
                    canvas.repaint();
                    curdots = new ArrayList<>();
                }
            }
        }
        @Override
        public void mouseReleased(MouseEvent e) {
            end = new Dot(e.getX(), e.getY());
            if(drawType == 0){
                Line l = new Line(currentID, brushColor, start, end, 0);
                shapes.add(l);
                canvas.repaint();
            }
            else if(drawType == 1){
                Dot center = new Dot((start.x + end.x)/2, (start.y + end.y)/2);
                int a = Math.abs(start.x - end.x)/2;
                int b = Math.abs(end.y - start.y)/2;
                Ellipse el = new Ellipse(currentID, brushColor, center, a, b);
                shapes.add(el);
                canvas.repaint();
            }
            else if(drawType > 3 && drawType < 8){
                if(drawType == 5){
                    float dx = (float)(end.x - start.x);
                    float dy = (float)(end.y - start.y);
                    for(MyShape sss:shapes){
                        if(sss.picID == currentID){
                            sss.translate(dx, dy);
                            break;
                        }
                    }
                    canvas.repaint();
                }
                if(drawType == 4){
                    for(MyShape sss:shapes){
                        if(sss.picID == currentID){
                            float angle1, angle2;
                            if(start.x == sss.Center.x) angle1 = 90;
                            else angle1 = (float)Math.atan((start.y - sss.Center.y) / (start.x - sss.Center.x));
                            if(end.x == sss.Center.x) angle2 = 90;
                            else angle2 = (float)Math.atan((end.y - sss.Center.y) / (end.x - sss.Center.x));
                            sss.rotate(sss.Center, angle2 - angle1);
                            break;
                        }
                    }
                    canvas.repaint();
                }
                if(drawType == 6){
                    for(MyShape sss:shapes){
                        if(sss.picID == currentID){
                            if(sss.type != 0) System.out.println("Clip is only permitted with lines.");
                            else{
                                Line nl = (Line)sss;
                                nl.clip(start, end, 0);
                                shapes.add(nl);
                                shapes.remove(sss);
                                canvas.repaint();
                            }
                            break;
                        }
                    }
                }
                if(drawType == 7){
                    MyShape ss = null;
                    for(MyShape sss:shapes){
                        if(sss.picID == currentID){
                            switch (sss.type){
                                case 0: ss = ((Line)sss).scale(sss.Center, (end.y > start.y)?1.5f:0.8f); break;
                                case 1: ss = ((Ellipse)sss).scale(sss.Center, (end.y > start.y)?1.5f:0.8f); break;
                                case 2: ss = ((Curve)sss).scale(sss.Center, (end.y > start.y)?1.5f:0.8f); break;
                                case 3: ss = ((Polygon)sss).scale(sss.Center, (end.y > start.y)?1.5f:0.8f); break;
                                default: break;
                            }
                            shapes.remove(sss);
                            shapes.add(ss);
                            break;
                        }
                    }
                    canvas.repaint();
                }
            }
        }
        @Override
        public void mouseEntered(MouseEvent e) {
            // TODO Auto-generated method stub
        }
        @Override
        public void mouseExited(MouseEvent e) {
            // TODO Auto-generated method stub
        }
    }

    public void savePic(String filename){
        Container content = this.canvas;
        BufferedImage img = new BufferedImage(canvas.getWidth(), canvas.getWidth(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = img.createGraphics();
        content.paintAll(g2d);
        File f = new File(filename);
        try{
            ImageIO.write(img, "bmp", f);
        }catch(IOException e){
            e.printStackTrace();
        }
        g2d.dispose();
    }

    public void cmdProcessor(ArrayList<String> cmd){
        while(!cmd.isEmpty()){
            String line = cmd.remove(0);
            String [] spString = line.split("\\s+");
            switch(spString[0]){
                case "resetCanvas":{
                    shapes = new ArrayList<>();
                    canvas.setSize(Integer.parseInt(spString[1]), Integer.parseInt(spString[2]));
                    cmdPanel = new myPanel();
                    System.out.println("h is "+ canvas.getHeight());
                    System.out.println("w is " + canvas.getWidth());
                    canvas.getContentPane().add(cmdPanel);
                    canvas.repaint();
                    System.out.println("reset OK.");
                    break;
                }
                case "setColor":{
                    brushColor = new Color(Integer.parseInt(spString[1]), Integer.parseInt(spString[2]), Integer.parseInt(spString[3]));
                    System.out.println("setColor OK.");
                    break;
                }
                case "drawLine":{
                    Dot start = new Dot(Integer.parseInt(spString[2]), Integer.parseInt(spString[3]));
                    Dot end = new Dot(Integer.parseInt(spString[4]), Integer.parseInt(spString[5]));
                    int Algorithm = (spString[6].equals("DDA"))?0:1;
                    //System.out.println(spString[1]);
                    Line newLine = new Line(Integer.parseInt(spString[1]), brushColor, start, end, Algorithm);
                    shapes.add(newLine);
                    System.out.println("DrawLine OK.");
                    break;
                }
                case "saveCanvas":{
                    String fileName = spString[1] + ".bmp";
                    canvas.repaint();
                    savePic(fileName);
                    System.out.println("saveOK.");
                    break;
                }
                case "drawPolygon":{
                    int edgeNum = Integer.parseInt(spString[2]);
                    int id = Integer.parseInt(spString[1]);
                    int Algorithm = (spString[3].equals("DDA"))?0:1;
                    line = cmd.remove(0);
                    spString = line.split("\\s+");
                    ArrayList<Dot> Vertices = new ArrayList<>();
                    for(int i = 0; i < edgeNum; i++){
                        int x = Integer.parseInt(spString[i * 2]);
                        int y = Integer.parseInt(spString[i * 2 + 1]);
                        Vertices.add(new Dot(x, y));
                    }
                    Polygon newplg = new Polygon(id, brushColor, edgeNum,Algorithm, Vertices);
                    shapes.add(newplg);
                    break;
                }
                case "drawCurve":{
                    int id = Integer.parseInt(spString[1]);
                    String paras = cmd.remove(0);
                    String[] para = paras.split("\\s+");
                    ArrayList<Dot> ds = new ArrayList<>();
                    for(int i = 0; i < 4; i++){
                        Dot d = new Dot(Integer.parseInt(para[i * 2]), Integer.parseInt(para[i * 2 + 1]));
                        ds.add(d);
                    }
                    Curve newcv = new Curve(id, brushColor, ds, 0);
                    shapes.add(newcv);
                    break;
                }
                case "drawEllipse":{
                    Dot center = new Dot(Integer.parseInt(spString[2]), Integer.parseInt(spString[3]));
                    Ellipse ellipse = new Ellipse(Integer.parseInt(spString[1]), brushColor, center, Integer.parseInt(spString[4]), Integer.parseInt(spString[5]));
                    shapes.add(ellipse);
                    break;
                }
                case "translate":{
                    int id = Integer.parseInt(spString[1]);
                    for(MyShape ms:shapes){
                        if(ms.picID == id){
                            ms.translate(Float.parseFloat(spString[2]), Float.parseFloat(spString[3]));
                            break;
                        }
                    }
                    break;
                }
                case "rotate":{
                    int id = Integer.parseInt(spString[1]);
                    Dot rDot = new Dot(Integer.parseInt(spString[2]), Integer.parseInt(spString[3]));
                    for(MyShape ms:shapes){
                        if(ms.picID == id){
                            ms.rotate(rDot, Float.parseFloat(spString[4]));
                            break;
                        }
                    }
                    break;
                }
                case "clip":{
                    int id = Integer.parseInt(spString[1]);
                    Dot sd = new Dot(Integer.parseInt(spString[2]), Integer.parseInt(spString[3]));
                    Dot ed = new Dot(Integer.parseInt(spString[4]), Integer.parseInt(spString[5]));
                    for(MyShape ms:shapes){
                        if(ms.picID == id){
                            Line l = (Line)ms;
                            l.clip(sd, ed, 0);
                            shapes.remove(ms);
                            shapes.add(l);
                            break;
                        }
                    }
                    break;
                }
                case "scale":{
                    int id = Integer.parseInt(spString[1]);
                    for(MyShape ms:shapes){
                        if(ms.picID == id){
                            switch(ms.type){
                                case 0: {
                                    Line l = (Line) ms;
                                    l = l.scale(new Dot(Integer.parseInt(spString[2]), Integer.parseInt(spString[3])), Float.parseFloat(spString[4]));
                                    shapes.remove(ms);
                                    shapes.add(l);
                                    break;
                                }
                                case 1: {
                                    Ellipse e = (Ellipse) ms;
                                    e = e.scale(new Dot(Integer.parseInt(spString[2]), Integer.parseInt(spString[3])), Float.parseFloat(spString[4]));
                                    shapes.remove(ms);
                                    shapes.add(e);
                                    break;
                                }
                                case 2:{
                                    Curve c = (Curve) ms;
                                    c = c.scale(new Dot(Integer.parseInt(spString[2]), Integer.parseInt(spString[3])), Float.parseFloat(spString[4]));
                                    shapes.remove(ms);
                                    shapes.add(c);
                                    break;
                                }
                                case 3:{
                                    Polygon p = (Polygon)ms;
                                    p = p.scale(new Dot(Integer.parseInt(spString[2]), Integer.parseInt(spString[3])), Float.parseFloat(spString[4]));
                                    shapes.remove(ms);
                                    shapes.add(p);
                                    break;
                                }
                                default:break;
                            }
                            break;
                        }
                    }
                    break;
                }
                default:{
                    System.out.println("The command is not found.");
                    break;
                }
            }
        }
    }

    public void guiInit(){
        canvas = new JFrame();
        shapes = new ArrayList<>();
        canvas.setSize(1280, 720);

        drawPanel = new myPanel();
        ButtonPanel = new JPanel();
        TopPanel = new JPanel();

        JButton label_line = new JButton("Line");
        label_line.setPreferredSize(new Dimension(110, 55));
        label_line.setHorizontalAlignment(JButton.CENTER);
        label_line.addActionListener(new LButtonListener());

        JButton label_curve = new JButton("Curve");
        label_curve.setPreferredSize(new Dimension(110, 55));
        label_curve.setHorizontalAlignment(JButton.CENTER);
        label_curve.addActionListener(new CButtonListener());

        JButton label_polygon = new JButton("Polygon");
        label_polygon.setPreferredSize(new Dimension(110, 55));
        label_polygon.setHorizontalAlignment(JButton.CENTER);
        label_polygon.addActionListener(new PButtonListener());

        JButton label_ellipse = new JButton("Ellipse");
        label_ellipse.setPreferredSize(new Dimension(110, 55));
        label_ellipse.setHorizontalAlignment(JButton.CENTER);
        label_ellipse.addActionListener(new EButtonListener());

        JButton label_translate = new JButton("Translate");
        label_translate.setPreferredSize(new Dimension(110, 55));
        label_translate.setHorizontalAlignment(JButton.CENTER);
        label_translate.addActionListener(new TButtonListener());

        JButton label_rotate = new JButton("Rotate");
        label_rotate.setPreferredSize(new Dimension(110, 55));
        label_rotate.setHorizontalAlignment(JButton.CENTER);
        label_rotate.addActionListener(new RButtonListener());

        JButton label_clip = new JButton("Clip");
        label_clip.setPreferredSize(new Dimension(110, 55));
        label_clip.setHorizontalAlignment(JButton.CENTER);
        label_clip.addActionListener(new ClButtonListener());

        JButton label_scale = new JButton("Scale");
        label_scale.setPreferredSize(new Dimension(110, 55));
        label_scale.setHorizontalAlignment(JButton.CENTER);
        label_scale.addActionListener(new SButtonListener());

        JButton label_reset = new JButton("reset");
        label_reset.setBackground(new Color(225,225,225));
        label_reset.setPreferredSize(new Dimension(67,28));
        label_reset.setHorizontalAlignment(JButton.CENTER);
        label_reset.addActionListener(new ReButtonListener());

        JButton label_ID = new JButton("ID:");
        label_ID.setBackground(new Color(225,225,225));
        label_ID.setPreferredSize(new Dimension(67,28));
        label_ID.setHorizontalAlignment(JButton.CENTER);

        Text_ID = new JTextField();
        Text_ID.setPreferredSize(new Dimension(90, 28));
        Text_ID.setHorizontalAlignment(JTextField.CENTER);
        Text_ID.addActionListener(new IDButtonListener());

        JButton red_Button = new JButton();
        red_Button.setPreferredSize(new Dimension(60, 60));
        red_Button.setHorizontalAlignment(JButton.CENTER);
        red_Button.setBackground(Color.RED);
        red_Button.addActionListener(new RedButtonListener());

        JButton blue_Button = new JButton();
        blue_Button.setPreferredSize(new Dimension(60, 60));
        blue_Button.setHorizontalAlignment(JButton.CENTER);
        blue_Button.setBackground(Color.blue);
        blue_Button.addActionListener(new BlueButtonListener());

        JButton green_Button = new JButton();
        green_Button.setPreferredSize(new Dimension(60, 60));
        green_Button.setHorizontalAlignment(JButton.CENTER);
        green_Button.setBackground(Color.green);
        green_Button.addActionListener(new GreenButtonListener());

        JButton gray_Button = new JButton();
        gray_Button.setPreferredSize(new Dimension(60, 60));
        gray_Button.setHorizontalAlignment(JButton.CENTER);
        gray_Button.setBackground(Color.gray);
        gray_Button.addActionListener(new GrayButtonListener());

        JButton yellow_Button = new JButton();
        yellow_Button.setPreferredSize(new Dimension(60, 60));
        yellow_Button.setHorizontalAlignment(JButton.CENTER);
        yellow_Button.setBackground(Color.yellow);
        yellow_Button.addActionListener(new YellowButtonListener());

        ButtonPanel.setBorder(new LineBorder(new Color(0,0,0)));
        ButtonPanel.setBounds(10, 70, 130, 490);
        ButtonPanel.setBackground(new Color(255, 255, 255));
        ButtonPanel.add(label_line);
        ButtonPanel.add(label_curve);
        ButtonPanel.add(label_ellipse);
        ButtonPanel.add(label_polygon);
        ButtonPanel.add(label_rotate);
        ButtonPanel.add(label_translate);
        ButtonPanel.add(label_scale);
        ButtonPanel.add(label_clip);

        TopPanel.setBackground(new Color(255, 255, 255));
        TopPanel.setBorder(new LineBorder(new Color(0,0,0)));
        TopPanel.setBounds(0, 0, 1280, 40);
        TopPanel.add(label_reset);
        TopPanel.add(label_ID);
        TopPanel.add(Text_ID);

        drawPanel.addMouseListener(new DrawPanelListener());

        ColorPanel = new JPanel();
        ColorPanel.setBounds(880, 570, 350, 80);
        // ColorPanel.setBorder(new LineBorder(Color.BLACK));
        ColorPanel.setBackground(Color.WHITE);
        ColorPanel.add(red_Button);
        ColorPanel.add(blue_Button);
        ColorPanel.add(yellow_Button);
        ColorPanel.add(gray_Button);
        ColorPanel.add(green_Button);

        canvas.getContentPane().add(TopPanel);
        canvas.getContentPane().add(ButtonPanel);
        canvas.getContentPane().add(ColorPanel);
        canvas.getContentPane().add(drawPanel);


        canvas.setVisible(true);
        canvas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        MyGraphics gp = new MyGraphics();
        gp.canvas.setVisible(true);
        gp.canvas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        if (args.length == 1) {
            //cmd mode
            TextReader textReader = new TextReader();
            ArrayList<String> cmd = textReader.getText(args[0]);
            gp.cmdProcessor(cmd);
        } else {
            gp.brushColor = Color.GRAY;
            gp.guiInit();
        }
    }
}