public class Shop extends Building{
    public int x1,y1,x2,y2;

    public Shop(int x1, int y1, int x2, int y2){
        super(x1,y1,x2,y2);

    }

    public void interact(Player player) {
        if (player.ammo < 100) {
            player.ammo++;
        }
    }

}