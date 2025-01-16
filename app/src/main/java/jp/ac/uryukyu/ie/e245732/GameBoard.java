package jp.ac.uryukyu.ie.e245732;
import java.util.ArrayList;
import java.util.Random;

/**
 * 迷路生成を行うクラス。
 * プリム法 (Prim's Algorithm) を用いて迷路を生成します。
 */
public class GameBoard {
    private final int width; // 迷路の幅（セル単位）
    private final int height; // 迷路の高さ（セル単位）
    private final int[][] maze; // 迷路データを格納する2次元配列
    private final Random random = new Random();

    /**
     * コンストラクタ。指定された幅と高さの迷路を生成します。
     *
     * @param width  迷路の幅（セル単位）
     * @param height 迷路の高さ（セル単位）
     */
    public GameBoard(int width, int height) {
        this.width = width;
        this.height = height;
        this.maze = new int[height][width];
    }

    /**
     * 迷路を生成するメソッド。
     */
    public void generateMaze() {
        // 初期化：全てのセルを壁（値=1）で埋める
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                maze[y][x] = 1;
            }
        }

        // 開始地点を設定し、壁リストに追加
        int startX = 1; // 左上の開始地点
        int startY = 1;
        maze[startY][startX] = 0; // 開始地点を通路（値=0）に設定
        ArrayList<int[]> wallList = new ArrayList<>();
        addWalls(startX, startY, wallList);

        // プリム法で迷路を生成
        while (!wallList.isEmpty()) {
            // 壁リストからランダムに壁を選ぶ
            int[] wall = wallList.remove(random.nextInt(wallList.size()));
            int x = wall[0];
            int y = wall[1];

            // 壁が2つの領域を分けているか確認
            if (canBeConvertedToPath(x, y)) {
                maze[y][x] = 0; // 壁を通路に変更
                addWalls(x, y, wallList); // 新たな壁をリストに追加
            }
        }

        // スタートとゴールを設定
        maze[1][1] = 2; // スタート地点
        maze[height - 2][width - 2] = 3; // ゴール地点
    }

    /**
     * 指定されたセルの周囲の壁を壁リストに追加します。
     *
     * @param x        現在のセルのX座標
     * @param y        現在のセルのY座標
     * @param wallList 壁リスト
     */
    private void addWalls(int x, int y, ArrayList<int[]> wallList) {
        int[][] directions = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}}; // 上下左右
        for (int[] dir : directions) {
            int nx = x + dir[0];
            int ny = y + dir[1];
            if (isInBounds(nx, ny) && maze[ny][nx] == 1) {
                wallList.add(new int[]{nx, ny});
            }
        }
    }

    /**
     * 壁を通路に変える条件を満たしているか確認します。
     *
     * @param x 壁のX座標
     * @param y 壁のY座標
     * @return 条件を満たしている場合はtrue、それ以外はfalse。
     */
    private boolean canBeConvertedToPath(int x, int y) {
        int[][] directions = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}}; // 上下左右
        int pathCount = 0;
        for (int[] dir : directions) {
            int nx = x + dir[0];
            int ny = y + dir[1];
            if (isInBounds(nx, ny) && maze[ny][nx] == 0) {
                pathCount++;
            }
        }
        return pathCount == 1; // 周囲の通路が1つだけの場合に通路に変更可能
    }

    /**
     * 指定された座標が迷路の範囲内か確認します。
     *
     * @param x チェックするX座標
     * @param y チェックするY座標
     * @return 範囲内であればtrue、それ以外はfalse。
     */
    private boolean isInBounds(int x, int y) {
        return x > 0 && x < width - 1 && y > 0 && y < height - 1;
    }

    /**
     * 現在の迷路をコンソールに表示します。
     */
    public void printMaze() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                switch (maze[y][x]) {
                    case 0 -> System.out.print(" "); // 通路
                    case 1 -> System.out.print("█"); // 壁
                    case 2 -> System.out.print("S"); // スタート
                    case 3 -> System.out.print("G"); // ゴール
                }
            }
            System.out.println();
        }
    }
}
