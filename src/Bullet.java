import processing.core.PApplet;

public class Bullet {
    public int x,y,xSpeed,ySpeed;
    boolean alive;
    int xChangeLength=20;
    int yChangeLength=20;
    public Bullet(int x,int y,int xSpeed, int ySpeed){
        this.x=x;
        this.y=y;
        this.alive=true;
        this.xSpeed=xSpeed;
        this.ySpeed=ySpeed;
        xChangeLength=(int) (xSpeed*60/Math.sqrt((xSpeed)*(xSpeed)+(ySpeed)*(ySpeed)));
        yChangeLength=(int) (ySpeed*60/Math.sqrt((xSpeed)*(xSpeed)+(ySpeed)*(ySpeed)));
    }
    public void display(PApplet circ){
        circ.line(x,y,x+xChangeLength,y+yChangeLength);
    }
    public double distance(int x1,int y1,int x2,int y2){
        return Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
    }
    public boolean collides(Monster m){
        if(distance(m.x,m.y,x+xChangeLength,y+yChangeLength)<30){
            return true;
        }
        return false;
    }
    public void move(){
        this.x+=xSpeed;
        this.y+=ySpeed;
    }
}