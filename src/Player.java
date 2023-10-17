import processing.core.PApplet;

class Player {
    public int x, y;
    public int health, hunger, money;
    // private boolean canMove;
    private int speed = 2;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        // canMove = true;
    }

    public void display(PApplet circ) {
        circ.fill(255,255,0);
        circ.ellipse(x, y, 100, 100);
    }



    public void move(char direction) {
        if(direction=='a'){
            this.x-=speed;
        }else if(direction=='d'){
            this.x+=speed;
        }else if(direction=='s'){
            this.y+=speed;
        }else if(direction=='w') {
            this.y -= speed;
        }
    }


    // Check for collisions with walls

}