/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;
import javafx.geometry.Point2D;

public class Controller {
    
    private GameController gc = new GameController();
    
    public void CreateShipForAI(Board enemyBoard, int[] shipList) {
        gc.CreateShipForAI(enemyBoard, shipList);
    }
    public void isValidPoint(double x, double y) {
        gc.isValidPoint(x, y);
    }
    public void addHistoryShot(Board.Cell cell, boolean accurate, ArrayList<Shot> history) {
        gc.addHistoryShot(cell, accurate, history);
    }
    public void getAdjacentPoint(Stack<Point2D> adjacentPoints, int x, int y) {
        gc.getAdjacentPoint(adjacentPoints, x, y);
    }
    public void getAdjacentByDirection(Stack<Point2D> adjacentPoints, int x, int y, boolean vertical) {
        gc.getAdjacentByDirection(adjacentPoints, x, y, vertical);
    }
    public void Result(Board myBoard, Board enemyBoard, String strResult, ArrayList<Shot> history) throws IOException {
        gc.Result(myBoard, enemyBoard, strResult, history);
    }
    
    
    
    
}
