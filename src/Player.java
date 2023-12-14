import processing.core.PApplet;
import processing.core.PImage;


class Player {
    public int x, y;
    public int ammo;
    public int health;
    public boolean alive;

    public PImage mario;
    private int speed =20;

    public Player(int x, int y, PImage marioImage) {
        this.x = x;
        this.y = y;
        this.ammo = 100;
        this.health=100;
        this.alive=true;
        mario = marioImage;  // Store the image
    }


    public void display(PApplet circ) {
        circ.fill(255,255,0);
        circ.image(mario, x-190, y-90);
    }



    public void move(int keyCode) {
        if ((keyCode == 65 || keyCode == 37) && (this.x-speed)>=0) {
            this.x -= speed;
        } else if ((keyCode == 68 || keyCode == 39) && (this.x+speed)<=800) {
            this.x += speed;
        } else if ((keyCode == 83 || keyCode == 40) && (this.y+speed<=800)) {
            this.y += speed;
        } else if ((keyCode == 87 || keyCode == 38) && (this.y-speed)>=0) {
            this.y -= speed;
        }
    }


}