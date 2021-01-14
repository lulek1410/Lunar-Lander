package company;

import javax.swing.*;
import java.awt.geom.Rectangle2D;
import java.util.Properties;

/**
 * Klasa odpowiedzialna za utworzenie statku
 */
public class Lander extends Sprite {

    /** Zminna przechowująca poziom na którym znajduje sie obiekt*/
    private Level level;
    /**
     * Konstruktr klasy ustawiający podstawowe parametry potrzebne do działania kalsy
     * @param level poziom na którym znajduje sie obiekt
     */
    public Lander(Level level) {
        this.level = level;
        initialize();
    }

    /**
     * Funkcja inicjująca zmienne odziedziczone od klasy Sprite
     */
    private void initialize() {
        int start_x = PropertiesLoad.xSize / 2 - PropertiesLoad.LanderWidth / 2;
        int start_y = PropertiesLoad.ySize - 450;
        setX(start_x);
        setY(start_y);
        Rectangle2D rect = new Rectangle2D.Float(this.getX(), this.getY(), 40, 40);
        setRect(rect);
    }

    /**
     * Zmienia obrazek reprezentujący statek
     * @param image = na jaki obraze zamieniamy
     */
    public void landerImageChange(Image image){
        ImageIcon imageIcon = ImageFactory.createImage(image);
        setImage(imageIcon.getImage());
    }

    /**
     * Nadpisuje metodę klasy sprite. Odpowiada za zmianę połozenia statku
     */
    @Override
    public void move() {
        //System.out.println("w lander.move " + x);

        x += velx;
        if (vely<1 && vely>0) {
            y += 1;
        }
        else {
            y += vely;
        }
        if(x<0){
            x = 0;
            velx=0;
        }
        if(x+PropertiesLoad.LanderWidth>=PropertiesLoad.xSize && level.getWidth()!=0){
            x = PropertiesLoad.xSize - PropertiesLoad.LanderWidth;
            velx=0;
        }
        if(y<0){
            y = 0;
            vely = 0;
        }

    }

    /**
     * Aktualizuje hitbox statku
     */
    private void updateRect() {
        Rectangle2D rect = new Rectangle2D.Float((int)(x*((float)(level.getWidth())/PropertiesLoad.xSize)), (int)(y*((float)level.getHeight()/PropertiesLoad.ySize)), (int)(40*((float)level.getWidth()/PropertiesLoad.xSize)), (int)(40*((float)level.getHeight()/PropertiesLoad.ySize)));
        setRect(rect);
    }

    /**
     * Zwiększa wartośc prędkości poziomej(dodaje stałą grawitacyjną)
     * @param accY przyśpieszenie pionowe
     */
    public void acceleration(float accY) {
        this.vely += accY;
        level.labelUpdate("vy");
        level.labelUpdate("vx");
    }

    /**
     * Aktualizuje położenie statku i jego hitbox oraz dodaje stałą grawitacyjną do prędkości pionowej
     */
    public void update() {
        move();
        updateRect();
        acceleration(PropertiesLoad.mapGravity);
    }

    /**
     * Ustawia obecny poziom na którym jest obiekt
     * @param lev = poziom
     */
    public void setLevel(Level lev)
    {
        this.level = lev;
    }

    /**
     * Metoda wykorzystywana przy obsłudze przycisku strzałki do góry (zmniejsza ilość paliwa oraz powoduje że statek zmniejsza swoją prędkośc spadania)
     */
    public void moveUp() {
        vely -= PropertiesLoad.enginePowerVy;
        vely += PropertiesLoad.mapGravity;
        level.fuelLevel -= 5;
        level.BarUpdate();
        level.noFuel();
    }

    /**
     * Metoda wykorzystywana przy obsłudze przycisku strzałki do dołu (zmniejsza ilość paliwa oraz powoduje że statek szybciej porusza sie w dół)
     */
    public void moveDown() {
        vely += PropertiesLoad.enginePowerVy;
        vely += PropertiesLoad.mapGravity;
    }

    /**
     * Metoda wykorzystywana przy obsłudze przycisku strzałki w lewo (zmniejsza ilość paliwa oraz zwieksza prędkośc statku w lewo)
     */
    public void moveLeft() {
        velx -= PropertiesLoad.enginePowerVx;
        level.fuelLevel -= 2.5;
        level.BarUpdate();
        level.noFuel();
    }

    /**
     * Metoda wykorzystywana przy obsłudze przycisku strzałki w prawo (zmniejsza ilość paliwa oraz zwiększa prędkośc statku w prawo)
     */
    public void moveRight() {
        velx += PropertiesLoad.enginePowerVx;
        level.fuelLevel -= 2.5;
        level.BarUpdate();
        level.noFuel();
    }
}