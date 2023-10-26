import processing.core.PApplet;
import processing.core.PImage;


class Player {
    public int x, y;
    public int health, money;
    public PImage mario;
    // private boolean canMove;
    private int speed = 5;

    public Player(int x, int y, PImage marioImage) {
        this.x = x;
        this.y = y;
        this.health = 100;
        this.money = 0;
        mario = marioImage;  // Store the image
    }



    public void display(PApplet circ) {
        circ.fill(255,255,0);
        //circ.ellipse(x, y, 100, 100);
        circ.image(mario, x-190, y-90);
    }



    public void move(int keyCode) {
        if (keyCode == 65 || keyCode == 37) {
            this.x -= speed;
        } else if (keyCode == 68 || keyCode == 39) {
            this.x += speed;
        } else if (keyCode == 88 || keyCode == 40) {
            this.y += speed;
        } else if (keyCode == 83 || keyCode == 38) {
            this.y -= speed;
        }
    }




    // Check for collisions with walls

}