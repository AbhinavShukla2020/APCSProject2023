import processing.core.PApplet;
public class Building {
    public int x,y;
    public int width,length;
    public int x1,x2,y1,y2;

    public Building(int x1,int y1,int x2,int y2){
        this.x1=x1;
        this.y1=y1;
        this.x2=x2;
        this.y2=y2;
    }

    public boolean collides(Player player){
        if(player.x>=Math.min(x1,x2) && player.x<=Math.max(x1,x2) && player.y>=Math.min(y1,y2) && player.y<=Math.max(y1,y2)){
            return true;
        }
        return false;
    }

    public void interact(Player player){

    }

    public void display(PApplet circ) {
        circ.rect(Math.min(x1,x2),Math.min(y1,y2),Math.max(x1,x2)-Math.min(x1,x2),Math.max(y1,y2)-Math.min(y1,y2));
    }
}