import processing.core.PApplet;

import java.util.ArrayList;

public class Game extends PApplet {
    // TODO: declare game variables
    Player player;
    Shop shop;
    Restaurant restaurant;

    double MONSTER_SPAWN_PROBABILITY=0.03;
     ArrayList<Bullet> bullets;
    ArrayList<Monster> enemies;

    public void settings() {
        size(800, 800);   // set the window size
    }

    public void setup() {
        shop= new Shop(100,600,300,750);
        restaurant=new Restaurant(500,600,700,750);
        player= new Player(50,width/2);
        enemies = new ArrayList<>();
        bullets = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int x=(int) (Math.random()*800);
            int y=(int) (Math.random()*800);
            Monster a = new Monster(x,y);
            enemies.add(a);
        }

    }

    /***
     * Draws each frame to the screen.  Runs automatically in a loop at frameRate frames a second.
     * tick each object (have it update itself), and draw each object
     */
    public void draw() {
        background(200);
        shop.display(this);
        restaurant.display(this);
        player.display(this);
        for(Monster m : enemies){
            if(m.alive) {
                m.display(this);
            }
        }
        for(Bullet b : bullets){
            if(b.alive) {
                b.display(this);
            }
        }
        double p=Math.random();
        if(p<MONSTER_SPAWN_PROBABILITY){
            int x=(int) (Math.random()*800);
            int y=(int) (Math.random()*800);
            Monster a = new Monster(x,y);
            enemies.add(a);
        }
        move();
        collision();
        mousePressed();
    }

    public void mousePressed(){
        if(mousePressed) {
            Bullet a = new Bullet(player.x, player.y, (int) (10*(mouseX - player.x)/Math.sqrt(((mouseX - player.x))*(mouseX - player.x)+(mouseY - player.y)*(mouseY - player.y))), (int) (10*(mouseY - player.y)/Math.sqrt(((mouseX - player.x))*(mouseX - player.x)+(mouseY - player.y)*(mouseY - player.y))));
            bullets.add(a);
        }
    }

    public void move() {
        for(Monster m : enemies){
            if(m.alive) {
                m.move();
            }
        }
        for(Bullet b : bullets){
            if(b.alive) {
                b.move();
            }
        }
        if (keyPressed) {
            player.move(key);
        }
    }

    public void collision(){
        if(shop.collides(player)){
            shop.interact(player);
        }
        if(restaurant.collides(player)){
            restaurant.interact(player);
        }

        for(Bullet b : bullets){
            for(Monster m : enemies){
                if(b.alive && m.alive) {
                    if (b.collides(m)) {
                        b.alive = false;
                        m.alive = false;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        PApplet.main("Game");
    }
}
