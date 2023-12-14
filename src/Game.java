import ddf.minim.AudioPlayer;
import processing.core.PApplet;
import processing.core.PImage;
import ddf.minim.Minim;
import gifAnimation.Gif;


import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;


public class Game extends PApplet {
    // TODO: declare game variables
    Minim loader;
    Player player;
//    Minim loader;
    Hospital hospital;
    boolean startgame = false;
    Shop shop;
    Gif bg1;
    PImage backgroundImage, mario, mushroom,startbg, start, hospitalImg, shopImg;
    AudioPlayer song;

    double MONSTER_SPAWN_PROBABILITY=0.03;
    ArrayList<Bullet> bullets;
    ArrayList<Monster> enemies;

    public void settings() {
        size(800, 800);   // set the window size
    }



    public static void writeDataToFile(String filePath, String data) throws IOException {
        try (FileWriter f = new FileWriter(filePath);
             BufferedWriter b = new BufferedWriter(f);
             PrintWriter writer = new PrintWriter(b);) {


            writer.println(data);


        } catch (IOException error) {
            System.err.println("There was a problem writing to the file: " + filePath);
            error.printStackTrace();
        }
    }

    public static String readFile(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }


    public void updateFile() throws IOException {
        String filePath="src/SavedGameFile";
        File file = new File(filePath);
        file.delete();
        file.createNewFile();
        String write="";
        write+=player.x+"\n";
        write+=player.y+"\n";
        write+=player.health+"\n";
        write+=player.ammo+"\n";

        for(Bullet i : bullets){
            write+=i.x+"\n";
            write+=i.y+"\n";
            write+=i.xSpeed+"\n";
            write+=i.ySpeed+"\n";
        }
        write+="MONSTERS\n";
        for(Monster i : enemies){
            if(i.alive) {
                write+=i.x+"\n";
                write+=i.y+"\n";
            }
        }
        writeDataToFile(filePath,write);
    }

    public void updateGameFromFile() throws IOException {
        String filePath="src/SavedGameFile";
        String data=readFile(filePath);
        try {
            enemies.clear();
            bullets.clear();
            BufferedReader in = new BufferedReader(new FileReader(filePath));

            String line;
            int cnt=0;

            int curBullX=0,curBullY=0,curBullXSpeed=0,curBullYSpeed=0;
            int curMonsterX=0,curMonsterY=0;
            boolean curBullet=true;
            boolean curMonsX=true;
            while((line = in.readLine())!=null){
                if(line.equals("")){
                    continue;
                }
                if(cnt==0){
                    player.x=Integer.parseInt(line);
                    cnt++;
                    continue;
                }
                if(cnt==1){
                    player.y=Integer.parseInt(line);
                    cnt++;
                    continue;
                }
                if(cnt==2){
                    player.health=100;
                    cnt++;
                    continue;
                }
                if(cnt==3){
                    player.ammo=Integer.parseInt(line);
                    cnt++;
                    continue;
                }
                if(curBullet){
                    if(line.equals("MONSTERS")){
                        curBullet=false;
                        cnt++;
                        continue;
                    }
                    if((cnt%4)==0){
                        curBullX=Integer.parseInt(line);
                    }
                    if((cnt%4)==1){
                        curBullY=Integer.parseInt(line);
                    }
                    if((cnt%4)==2){
                        curBullXSpeed=Integer.parseInt(line);
                    }
                    if((cnt%4)==3){
                        curBullYSpeed=Integer.parseInt(line);
                        Bullet newBullet = new Bullet(curBullX,curBullY,curBullXSpeed,curBullYSpeed);
                        bullets.add(newBullet);
                    }
                    cnt++;
                }
                if(!curBullet){
                    if(curMonsX){
                        curMonsterX=Integer.parseInt(line);
                        curMonsX=false;
                    }else{
                        curMonsterY=Integer.parseInt(line);
                        Monster curMonster=new Monster(curMonsterX,curMonsterY,mushroom);
                        enemies.add(curMonster);
                        curMonsX=true;
                    }
                    cnt++;
                }
            }
            in.close();
        }catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public void setup() {

        backgroundImage = loadImage("BGImage.png");
        startbg = loadImage("StartBG.png");
        start = loadImage("Start.png");
        mario = loadImage("SuperMario.png");
        mushroom = loadImage("Mushroom.png");
        hospitalImg = loadImage("HospitalBackgroundRemoved.png");
        shopImg = loadImage("ammoShop.png");
        hospitalImg.resize(200,150);
        shopImg.resize(200,150);
        mushroom.resize(20,20);
        startbg.resize(800,800);
        start.resize(200,100);
        bg1 = new Gif(this, "ezgif.com-resize.gif");

        loader = new Minim(this);
        song = loader.loadFile("01. Ground Theme.mp3");
        song.play();

        File file = new File("src/SavedGameFile");
        backgroundImage.resize(800, 800);
        hospital = new Hospital(180, 560, 300, 750);
        shop = new Shop(550, 560, 700, 750);
        player = new Player(50, width / 2, mario);
        enemies = new ArrayList<>();
        bullets = new ArrayList<>();
        if(file.length()==0) {

        }else {

            try {
                updateGameFromFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }



    }

    /***
     * Draws each frame to the screen.  Runs automatically in a loop at frameRate frames a second.
     * tick each object (have it update itself), and draw each object
     */
    public void draw() {
        if(startgame) {

            MONSTER_SPAWN_PROBABILITY+=0.00005;
            fill(255, 0, 0); // Set text color to red

            background(200);
            image(backgroundImage, 0, 0);
            hospital.display(this,hospitalImg);
            shop.display(this,shopImg);
            if(player.alive) {
                player.display(this);
                mousePressed();
            }else{
                image(bg1,0,0);
                bg1.play();

                fill(173,216,230);
                rect(250,690,300,50);
                fill(0);
                text("Restart", 360,720);
                textSize(25);
                if(mousePressed && (mouseX > 250 && mouseX < 460 && mouseY > 690 && mouseY < 740)) {
                    startgame = true;
                    player.alive = true;
                    player.health= 100;
                    player.ammo = 100;

                }
                return;


            }


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
            try {
                updateFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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
        try {
            updateFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void move() {
        for(Monster m : enemies){
            if(m.alive) {
                m.move(player);
            }
        }
        for(Bullet b : bullets){
            if(b.alive) {
                b.move();
            }
        }
        try {
            updateFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
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
        try {
            updateFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void keyPressed() {
        int kC = keyCode;
        if (kC != 0) {
            player.move(kC);
        }
        try {
            updateFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) {
        PApplet.main("Game");
    }
}