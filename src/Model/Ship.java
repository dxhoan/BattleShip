package Model;

public class Ship {

    public int type;
    public boolean vertical = true; //true thì đặt tàu xếp dọc
    private int health;

    public Ship(int type, boolean vertical) {
        this.type = type;
        this.vertical = vertical;
        health = type;
    }

    public void hit() {
        //playHitMusic(explosion_mp3);
        health--;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public int getHealth() {
        return health;
    }
}
