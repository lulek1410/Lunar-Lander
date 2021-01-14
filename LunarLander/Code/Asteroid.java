package company;

import javax.swing.*;
import java.awt.geom.Rectangle2D;

/** Klasa odpowiedzialna za tworzenie asteroid*/
public class Asteroid extends Sprite {

    /** Obiekt klasy Level**/
    private Level level;
    /** Prędkość asteroidy w poziomie**/
    private int velx;
    /** Prędkość asteroidy w pionie**/
    private int vely;
    /** Zmienna określająca kierunek asteroidy**/
    private int direction;

    /**
     *  Konstruktr klasy ustawiający podstawowe parametry potrzebne do działania kalsy
     * @param start_x początkowe położenie asteroidy na osi OX
     * @param start_y poczatkowe położenie asteroidy na osi OY
     * @param velx prędkość pozioma asteroidy
     * @param vely prędkośc pionowa asteroidy
     * @param direction określa w którą stronę będize poruszać się asteroida
     * @param level poziom na którym znajduje się obiekt
     */
    public Asteroid(int start_x, int start_y, int velx, int vely, int direction, Level level) {
        initialize(start_x, start_y, velx, vely, direction, level);
    }

    /**
     * Inicjuje zmienne klasy Asteroid
     * @param start_x = położenie początkowe na osi OX
     * @param start_y = położenie początkowe na osi OX
     * @param velx = prędkość pozioma
     * @param vely = prędkość pionowa
     * @param direction = decyduje w którą stronę będize poruszać sie asteroida
     * @param level = odwołanie do poziomu w którym są asteroidy
     */
    private void initialize(int start_x, int start_y, int velx, int vely ,int direction ,Level level){
        ImageIcon imageIcon = ImageFactory.createImage(Image.Asteroid);
        setImage(imageIcon.getImage());
        setX(start_x);
        setY(start_y);
        this.velx = velx;
        this.vely = vely;
        this.level = level;
        this.direction = direction;
        Rectangle2D rect = new Rectangle2D.Float(this.getX(), this.getY(), 20, 20);
        setRect(rect);
    }

    /**
     * Aktualizuje hitbox asteroidy
     */
    private void updateRect() {
        Rectangle2D rect = new Rectangle2D.Float((int)(getX()*((float)(level.getWidth())/PropertiesLoad.xSize)), (int)(getY()*((float)level.getHeight()/PropertiesLoad.ySize)),
                (int)(20*((float)level.getWidth()/PropertiesLoad.xSize)), (int)(20*((float)level.getHeight()/PropertiesLoad.ySize)));
        setRect(rect);
    }

    /**
     * Aktualizuje położenie asteroidy oraz jej hitbox
     */
    public void update() {
        move();
        updateRect();
    }

    /**
     * Ustawia odniesienie do poziomu
     * @param lev = poziom
     */
    public void setLevel(Level lev)
    {
        this.level = lev;
    }

    /**
     * Nadpisuje metodę klasy Sprite. Odpowiada za poruszanie sie Asteroidy
     */
    @Override
    public void move(){
        if (this.direction == 0){
            x-=velx;
        }
        else{
            x+=velx;
        }
        y+=vely;
    }
}
