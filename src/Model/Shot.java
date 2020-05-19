package Model;

import java.util.ArrayList;

public class Shot {

    private int x;
    private int y;
    private boolean accurate; //hit hoặc miss
    private static ArrayList<String> stringHistory = new ArrayList<>();

    public Shot(int x, int y, boolean accurate) {
        super();
        this.x = x;
        this.y = y;
        this.accurate = accurate;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isAccurate() {
        return accurate;
    }

    public void setAccurate(boolean accurate) {
        this.accurate = accurate;
    }

    public void setHistoryShot(ArrayList<Shot> historyList) {
        stringHistory.clear();
        int numLoops = historyList.size();
        for (int i = 1; i <= numLoops; i++) {
            stringHistory.add(String.format("%3d. %s", i, historyList.get(i - 1).shotToString()));
        }
    }

    public ArrayList<String> getHistoryShot() {
        return stringHistory;
    }

    //Hiển thị các nước đi dưới dạng string

    public String shotToString() {
        String accurateStr;
        if (isAccurate()) {
            accurateStr = "hit";
        } else {
            accurateStr = "miss";
        }
        return (String.format("(x, y) : (%d, %d) : %s", this.getX(), this.getY(), accurateStr));
    }
}
