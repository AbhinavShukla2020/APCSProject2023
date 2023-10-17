import processing.core.PApplet;
public class Building {
    public int x,y;
    public int width=200, length=200;
    public int leftX,rightX,upY,downY;

    public Building(int x,int y){
        leftX=x-width/2;
        rightX=x+width/2;
        upY=y+length/2;
        downY=y-length/2;
        this.x=x;
        this.y=y;
    }

    public void collides(Player player){
        if(player.x>=leftX && player.x<=rightX && player.y>=downY && player.y<=upY){
            interact(player);
        }
    }

    public void interact(Player player){

    }

    public void display(PApplet circ) {
        circ.rect(leftX,upY,rightX,downY);
    }
}
