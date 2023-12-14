import processing.core.PApplet;
import processing.core.PImage;

public class Monster {
    public int x, y;
    public double speed = 1;
    public boolean alive;
    public PImage monster;
    public Monster(int x, int y, PImage mushImage) {
        this.x = x;
        this.y = y;
        this.alive=true;
        monster = mushImage;
    }

    public void display(PApplet circ) {
        circ.fill(255,0,0);
        circ.ellipse(x, y, 20, 20);
        circ.image(monster, x-10, y-10);
    }

    public void move(Player player) {
        boolean moved=false;
        int pX=player.x;
        int pY=player.y;
        this.x+=(pX-this.x)/Math.sqrt(10000*speed);
        this.y+=(pY-this.y)/Math.sqrt(10000*speed);
    }
}