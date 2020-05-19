package Game;

import Model.Player;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class SaveData {

    public static void GenerateDataFile() {
        try {
            File file = new File("Result.txt");
            if (file.createNewFile()) {
                System.out.println("New File is created!");
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList Player_Data() {
        ArrayList<Player> list = new ArrayList<>();
        GenerateDataFile();
        File file = new File("Result.txt");
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNext()) {
                Player pd = new Player();
                pd.setName(sc.nextLine());
                pd.setScore(Integer.parseInt(sc.nextLine()));
                list.add(pd);
            }
        } catch (Exception e) {
            System.out.println("got an exception!");
        }
        return list;
    }

    public static ArrayList GenerateArray() {
        ArrayList<Player> list = Player_Data();
        return list;
    }

    public static ArrayList InputPlayer(String name, int score, ArrayList<Player> list) {
        Player pd = new Player(name, score);
        list.add(pd);
        Collections.sort(list, new ScoreComparator());
        return list;

    }

    public static void InputDataToFile(ArrayList<Player> tempList) {
        try (PrintWriter pw = new PrintWriter("Result.txt")) {
            for (int i = 0; i < 5; i++) {
                pw.println(tempList.get(i).getName());
                pw.println(tempList.get(i).getScore());
            }
        } catch (Exception e) {
            System.out.println("got an exception!");
        }
    }

    public static void ClearResultFile() {
        try {
            PrintWriter pw = new PrintWriter("Result.txt");
            pw.close();
        } catch (Exception e) {
            System.out.println("got an exception!");
        }
    }
}

class ScoreComparator implements Comparator<Player> {
    @Override
    public int compare(Player o1, Player o2) {
        return o2.getScore() - o1.getScore();
    }
}
