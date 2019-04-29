package cn.sxt.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Date;

public class MygameFrame extends JFrame {


    Image bgImg = GameUtil.getImage("images/bg.jpg");
    Image planeImg = GameUtil.getImage("images/plane.png");

    static int count = 0;
    Plane plane = new Plane(planeImg,250,250,3);
    ArrayList<Shell> shelllist = new ArrayList<Shell>();

    Explode bao;
    Date startTime = new Date();
    Date endTime;

    @Override
    public void paint(Graphics g) {   //自动调用，画出窗口

        g.drawImage(bgImg, 0, 0, null);
        plane.drawMySelf(g);    //画出飞机本身

        for (int i=0;i<shelllist.size();i++){
            Shell b = shelllist.get(i);
            b.draw(g);

            boolean peng = b.getRect().intersects(plane.getRect());

            if(peng){
                plane.live = false;
                endTime = new Date();
                if (bao == null){
                    bao = new Explode(plane.x,plane.y);

                }
                bao.draw(g);
            }
        }
        if(!plane.live){
            if(endTime==null){
                endTime = new Date();
            }
            int period = (int)((endTime.getTime()-startTime.getTime())/1000);
            printInfo(g, "时间："+period+"秒", 50, 120, 260, Color.white);
        }
        /*    super.paint(g);
        Color c = g.getColor();
        g.setColor(Color.BLUE);
        g.drawLine(100,100,300,300);
        g.drawRect(100,100,300,300);
        g.drawOval(100,100,300,300);
        g.fillRect(100,100,300,300);
        g.drawString("上官婉儿",200,200);

        g.setColor(c);
    */
    }

    ///重复画窗口
    class PaintThread extends Thread{
        @Override
        public void run() {
            while (true){
               repaint();//重画

                try {
                    Thread.sleep(60);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //定义键盘监听的内部类
    class KeyMonitor extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            plane.addDirection(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            plane.addDirection(e);
        }
    }


    //初始化窗口
    public void lauchFrame(){
        this.setTitle("liuxuyang");//游戏标题
        this.setVisible(true);//默认不可见，改为可见
        this.setSize(Constant.GAME_WIDTH,Constant.GAME_HEIGHT);//窗口大小500*500
        this.setLocation(300,300);//窗口左上角顶点的坐标位置


        //增加关闭窗口监听
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        new PaintThread().start();//启动重画窗口的线程
        addKeyListener(new KeyMonitor());

        //初始化子弹
        for (int i = 0;i<50;i++){
            Shell b = new Shell();
            shelllist.add(b);
        }

    }
    public static void main(String[]args){
        MygameFrame f = new MygameFrame();
        f.lauchFrame();
    }


    private Image offScreenImage = null;

    public void update(Graphics g) {
        if(offScreenImage == null)
            offScreenImage = this.createImage(500,500);//这是游戏窗口的宽度和高度

        Graphics gOff = offScreenImage.getGraphics();
        paint(gOff);
        g.drawImage(offScreenImage, 0, 0, null);
    }
    public void printInfo(Graphics g,String str,int size,int x,int y,Color color){
        Color c = g.getColor();
        g.setColor(color);
        Font f = new Font("微软雅黑",Font.BOLD,size);
        g.setFont(f);
        g.drawString(str,x,y);
        g.setColor(c);
    }
}
