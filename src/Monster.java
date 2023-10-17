import processing.core.PApplet;

public class Monster {
    private int x, y;
    private int speed = 5;


    public Monster(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void display(PApplet circ) {
        circ.fill(255,0,0);
        circ.ellipse(x, y, 20, 20);

    }

    public void move() {
        int max = 5; int min = 2;



        boolean moved=false;
        while(moved==false){
            int val=(int) (Math.random()*4);

            if(val==0){
                if((x+speed)<=800) {
                    this.x += speed;
                    moved=true;
                }

            }
            if(val == 1 ) {
                if((x-speed)>=0) {
                    this.x -= speed;
                    moved=true;
                }
            }
            if(val == 2 ) {
                if((y+speed)<=800) {
                    this.y += speed;
                    moved=true;
                }
            }
            if(val == 3 ) {
                if((y-speed)>=0) {
                    this.y -= speed;
                    moved=true;
                }
            }
        }



    }


}
