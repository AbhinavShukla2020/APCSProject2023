import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;




public class Game extends PApplet {
    // TODO: declare game variables
    Player player;
    Hospital hospital;
    boolean startgame = false;
    Shop shop;
    PImage backgroundImage, mario, mushroom,startbg, start;

    double MONSTER_SPAWN_PROBABILITY=0.03;
    ArrayList<Bullet> bullets;
    ArrayList<Monster> enemies;

    public void settings() {
        size(800, 800);   // set the window size
    }

    public void setup() {

        backgroundImage = loadImage("BGImage.png");
        startbg = loadImage("StartBG.png");
        start = loadImage("Start.png");
        mario = loadImage("SuperMario.png");
        mushroom = loadImage("Mushroom.png");
        mushroom.resize(20,20);
        startbg.resize(800,800);
        start.resize(200,100);



        backgroundImage.resize(800,800);
        hospital= new Hospital(100,600,300,750);
        shop=new Shop(500,600,700,750);
        player= new Player(50,width/2, mario );
        enemies = new ArrayList<>();
        bullets = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int x=(int) (Math.random()*800);
            int y=(int) (Math.random()*800);
            Monster a = new Monster(x,y,mushroom);
            enemies.add(a);
        }

    }

    /***
     * Draws each frame to the screen.  Runs automatically in a loop at frameRate frames a second.
     * tick each object (have it update itself), and draw each object
     */
    public void draw() {
        if(startgame) {

            MONSTER_SPAWN_PROBABILITY+=0.0001;
            fill(255, 0, 0); // Set text color to red

            background(200);
            image(backgroundImage, 0, 0);
            hospital.display(this);
            if(player.alive) {
                player.display(this);
                mousePressed();
            }else{
                textSize(50);
                text("Game Over!", 250, 50);
            }
            shop.display(this);

            for (Monster m : enemies) {
                if (m.alive) {
                    m.display(this);
                }
            }
            for (Bullet b : bullets) {
                if (b.alive) {
                    b.display(this);
                }
            }
            double p = Math.random();
            if (p < MONSTER_SPAWN_PROBABILITY) {
                int x = (int) (Math.random() * 800);
                int y = (int) (Math.random() * 800);
                Monster a = new Monster(x, y, mushroom);
                enemies.add(a);
            }

            if(player.health <= 0){
                MONSTER_SPAWN_PROBABILITY=0;
                enemies.clear();
                player.alive=false;
            }

            move();
            collision();
            if(player.alive) {
                textSize(32);
                text("Health: " + player.health + "    Ammo: " + player.ammo, 350, 50);
            }
            textSize(30);
            fill(0, 408, 612);
            text("Hospital", 142, 690);
            text("Shop", 560, 690);
        }
        else
        {
            image(startbg, 0, 0);
            image(start, 300, 560);

        }

    }


    public void mousePressed(){
        if(mousePressed && player.ammo>0 && player.alive) {
            player.ammo--;
            Bullet a = new Bullet(player.x, player.y, (int) (10*(mouseX - player.x)/Math.sqrt(((mouseX - player.x))*(mouseX - player.x)+(mouseY - player.y)*(mouseY - player.y))), (int) (10*(mouseY - player.y)/Math.sqrt(((mouseX - player.x))*(mouseX - player.x)+(mouseY - player.y)*(mouseY - player.y))));
            bullets.add(a);
        }
        if(mouseX > 250 && mouseX < 550 && mouseY > 500 && mouseY < 700)
        {
            startgame = true;
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
    }

    public void collision(){
        if(hospital.collides(player)){
            hospital.interact(player);
        }
        if(shop.collides(player)){
            shop.interact(player);
        }

        //bullet - monster collision detection
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

        //Player - monster collision detection
        for (Monster m : enemies) {
            if (m.alive && player.x >= m.x - 40 && player.x <= m.x + 40 && player.y >= m.y - 40 && player.y <= m.y + 40 && player.health>0) {
                player.health--;
            }
        }
    }

    public void keyPressed() {
        int kC = keyCode;
        if (kC != 0) {
            player.move(kC);
        }
    }


    public static void main(String[] args) {
        PApplet.main("Game");
    }
}