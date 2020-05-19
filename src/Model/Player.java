package Model;

public class Player {

    private String name;
    private int totalShots, hit, miss, score;
    private String result;

    public Player() {
        
    }
    public Player(String name, int score) {
        this.name = name;
        this.score = score;
    }

    //Thay cho transferStat
    public void setPlayerData(String result, int totalShots, int hit, int score) {
        this.result = result;
        this.totalShots = totalShots;
        this.hit = hit;
        this.miss = totalShots - hit;
        this.score = score;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setTotalShots(int totalShots) {
        this.totalShots = totalShots;
    }

    public int getTotalShots() {
        return totalShots;
    }

    public void setHit(int hit) {
        this.hit = hit;
    }

    public int getHit() {
        return hit;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }    
}
