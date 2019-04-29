package cn.sxt.game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

public class Plane extends GameObject {

    boolean left, up, right, down;
    boolean live = true;

    // 按下上下左右键，则改变方向值。
    // 比如：按下上键，则e.getKeyCode()的值就是VK_UP，那么置：up=true;
    public void addDirection(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                left = true;
                break;
            case KeyEvent.VK_UP:
                up = true;
                break;
            case KeyEvent.VK_RIGHT:
                right = true;
                break;
            case KeyEvent.VK_DOWN:
                down = true;
                break;
            default:
                break;
        }
    }

    // 松开上下左右键，则改变方向值。
    // 比如：松开上键，则e.getKeyCode()的值就是VK_UP，那么置：up=false;
    public void minusDirection(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                left = false;
                break;
            case KeyEvent.VK_UP:
                up = false;
                break;
            case KeyEvent.VK_RIGHT:
                right = false;
                break;
            case KeyEvent.VK_DOWN:
                down = false;
                break;
            default:
                break;
        }
    }

    @Override
    public void drawMySelf(Graphics g) {
        if (live) {
            super.drawMySelf(g);
            //根据方向，计算飞机新的坐标
            if (left) {
                x -= speed;
            }
            if (right) {
                x += speed;
            }
            if (up) {
                y -= speed;
            }
            if (down) {
                y += speed;
            }
        }

    }

    public Plane(Image img, double x, double y, int speed) {
        super(img, x, y);
        this.width = img.getWidth(null);
        this.height = img.getHeight(null);

        this.speed = 3;
    }
}