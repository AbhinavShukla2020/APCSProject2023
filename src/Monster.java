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
//        while(moved==false){
//            int val=(int) (Math.random()*4);
//
//            if(val==0){
//                if((x+speed)<=800) {
//                    this.x += speed;
//                    moved = true;
//                }
//            }
//            if(val == 1 ) {
//                if((x-speed)>=0) {
//                    this.x -= speed;
//                    moved=true;
//                }
//            }
//            if(val == 2 ) {
//                if((y+speed)<=800) {
//                    this.y += speed;
//                    moved=true;
//                }
//            }
//            if(val == 3 ) {
//                if((y-speed)>=0) {
//                    this.y -= speed;
//                    moved=true;
//                }
//            }
//        }



    }


}