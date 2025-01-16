package jp.ac.uryukyu.ie.e245732;

/**
 * メインクラス。
 */
public class Main {
    public static void main(String[] args) {
        int width = 20; // 迷路の幅
        int height = 10; // 迷路の高さ
        GameBoard gameBoard = new GameBoard(width, height);
        gameBoard.generateMaze();
        gameBoard.printMaze();
    }
}
