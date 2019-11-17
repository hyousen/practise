package tetris;

import java.io.File;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Tetris extends Application {

	private static final int WINDOW_WIDTH = 500;
	private static final int WINDOW_HEIGHT = 500;
	private static final int CANVAS_WIDTH = 250;
	private static final int CANVAS_HEIGHT = 500;

	private static final int BLOCK_SIZE = 25;
	private static final int GAME_WIDTH = 10;
	private static final int GAME_HEIGHT = 20;
	private static final int MINO_WIDTH = 4;
	private static final int MINO_HEIGHT = 4;
	private static final int ANGLE_MAX = 4;
	private static final int TYPE_MAX = 7;
	int field[][] = new int[GAME_HEIGHT][GAME_WIDTH];
	int displayBuffer[][] = new int[GAME_HEIGHT][GAME_WIDTH];
	int minoX = 4;
	int minoY = 0;
	int minoAngle = 0;
	int minoType = 0;
	boolean isGameOver = false;

	GraphicsContext gc;
	File file;
	StreamingLineSound player;

	/*Color.black       黒を表します
	Color.blue        青を表します
	Color.cyan        シアンを表します
	Color.darkGray    ダークグレイを表します
	Color.gray        グレイを表します
	Color.green       緑を表します
	Color.lightGray   ライトグレイを表します
	Color.magenta     マゼンタを表します
	Color.orange      オレンジを表します
	Color.pink        ピンクを表します
	Color.red         赤を表します
	Color.white       白を表します
	Color.yellow      黄を表します*/

	static final int BLACK = 0;
	static final int BLUE = 1;
	static final int ORANGE = 2;
	static final int MAGENTA = 3;
	static final int GREEN = 4;
	static final int YELLOW = 5;
	static final int CYAN = 6;
	static final int RED = 7;


	int minoShapes[][][][] = {
			{
				{
					{0, 1, 0, 0},
					{0, 1, 0, 0},
					{0, 1, 0, 0},
					{0, 1, 0, 0}
				},

				{
					{0, 0, 0, 0},
					{0, 0, 0, 0},
					{1, 1, 1, 1},
					{0, 0, 0, 0}
				},

				{
					{0, 0, 1, 0},
					{0, 0, 1, 0},
					{0, 0, 1, 0},
					{0, 0, 1, 0}
				},

				{
					{0, 0, 0, 0},
					{1, 1, 1, 1},
					{0, 0, 0, 0},
					{0, 0, 0, 0}
				}
			},

			{
				{
					{0, 0, 0, 0},
					{0, 2, 2, 0},
					{0, 2, 2, 0},
					{0, 0, 0, 0}
				},

				{
					{0, 0, 0, 0},
					{0, 2, 2, 0},
					{0, 2, 2, 0},
					{0, 0, 0, 0}
				},

				{
					{0, 0, 0, 0},
					{0, 2, 2, 0},
					{0, 2, 2, 0},
					{0, 0, 0, 0}
				},

				{
					{0, 0, 0, 0},
					{0, 2, 2, 0},
					{0, 2, 2, 0},
					{0, 0, 0, 0}
				}
			},

			{
				{
					{0, 0, 0, 0},
					{0, 3, 0, 0},
					{0, 3, 0, 0},
					{0, 3, 3, 0}
				},

				{
					{0, 0, 0, 0},
					{0, 0, 3, 0},
					{3, 3, 3, 0},
					{0, 0, 0, 0}
				},

				{
					{0, 0, 0, 0},
					{0, 3, 3, 0},
					{0, 0, 3, 0},
					{0, 0, 3, 0}
				},

				{
					{0, 0, 0, 0},
					{0, 3, 3, 3},
					{0, 3, 0, 0},
					{0, 0, 0, 0}
				}
			},

			{
				{
					{0, 0, 0, 0},
					{0, 0, 4, 0},
					{0, 0, 4, 0},
					{0, 4, 4, 0}
				},

				{
					{0, 0, 0, 0},
					{4, 4, 4, 0},
					{0, 0, 4, 0},
					{0, 0, 0, 0}
				},

				{
					{0, 0, 0, 0},
					{0, 4, 4, 0},
					{0, 4, 0, 0},
					{0, 4, 0, 0}
				},

				{
					{0, 0, 0, 0},
					{0, 4, 0, 0},
					{0, 4, 4, 4},
					{0, 0, 0, 0}
				}
			},

			{
				{
					{0, 0, 0, 0},
					{0, 5, 5, 0},
					{5, 5, 0, 0},
					{0, 0, 0, 0}
				},

				{
					{0, 0, 0, 0},
					{0, 5, 0, 0},
					{0, 5, 5, 0},
					{0, 0, 5, 0}
				},

				{
					{0, 0, 0, 0},
					{0, 0, 5, 5},
					{0, 5, 5, 0},
					{0, 0, 0, 0}
				},

				{
					{0, 5, 0, 0},
					{0, 5, 5, 0},
					{0, 0, 5, 0},
					{0, 0, 0, 0}
				}
			},

			{
				{
					{0, 0, 0, 0},
					{0, 6, 6, 0},
					{0, 0, 6, 6},
					{0, 0, 0, 0}
				},

				{
					{0, 0, 6, 0},
					{0, 6, 6, 0},
					{0, 6, 0, 0},
					{0, 0, 0, 0}
				},

				{
					{0, 0, 0, 0},
					{6, 6, 0, 0},
					{0, 6, 6, 0},
					{0, 0, 0, 0}
				},

				{
					{0, 0, 0, 0},
					{0, 0, 6, 0},
					{0, 6, 6, 0},
					{0, 6, 0, 0}
				}
			},

			{
				{
					{0, 7, 0, 0},
					{0, 7, 7, 0},
					{0, 7, 0, 0},
					{0, 0, 0, 0}
				},

				{
					{0, 0, 0, 0},
					{0, 7, 0, 0},
					{7, 7, 7, 0},
					{0, 0, 0, 0}
				},

				{
					{0, 0, 0, 0},
					{0, 0, 7, 0},
					{0, 7, 7, 0},
					{0, 0, 7, 0}
				},

				{
					{0, 0, 0, 0},
					{0, 7, 7, 7},
					{0, 0, 7, 0},
					{0, 0, 0, 0}
				}
			}
	};


	@Override
	public void start(Stage stage) throws Exception{

		BorderPane pane = new BorderPane();
		Scene scene = new Scene(pane, WINDOW_WIDTH, WINDOW_HEIGHT, Color.SKYBLUE);
		Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
		pane.setCenter(canvas);;

		this.gc = canvas.getGraphicsContext2D();

		stage.setTitle("TETRIS");
		stage.setScene(scene);
		stage.show();

		initialize();

		/*file = new File("./src/tetrisBGM.wav");
		try {
			TetrisBGM bgm = new TetrisBGM(file);
		}catch (Exception e) {
			e.printStackTrace();
		}*/

		minoReset();

		Timeline timer = new Timeline(new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {

				if(!isGameOver) {
					scene.setOnKeyPressed(e -> {
						try {
							keyPressed(e);
						} catch (InterruptedException e1) {
							// TODO 自動生成された catch ブロック
							e1.printStackTrace();
						}
					});
					draw();

					if(isUpdate(minoX, minoY + 1, minoAngle, minoType)) {
						minoY++;
					}else {
						for(int i=0;i<MINO_HEIGHT;i++ ) {
							for(int j=0;j<MINO_WIDTH;j++) {
								field[(minoY + i) % GAME_HEIGHT][(minoX + j + GAME_WIDTH) % GAME_WIDTH] |= minoShapes[minoType][minoAngle][i][j];
							}
						}
						for(int i=0;i<GAME_HEIGHT;i++ ) {
							boolean isFillline = true;
							for(int j=0;j<GAME_WIDTH;j++) {
								if(field[i][j] == 0) {
									isFillline = false;
								}
							}
							if(isFillline) {
								for(int j=i;0<j;j--) {
									field[j] = field[j-1];
								}
							}
						}
						minoReset();

					}
				}else {

				}
			}


		}));
		timer.setCycleCount(Timeline.INDEFINITE);
		timer.play();


	}

	private void keyPressed(KeyEvent e) throws InterruptedException {

		switch(e.getCode()) {
		case UP:
			if(isUpdate(minoX, minoY, (minoAngle + 1) % minoShapes[minoType].length, minoType)){
				minoAngle = (minoAngle + 1) % minoShapes[minoType].length;
			}
			break;
		case RIGHT:
			if(isUpdate(minoX + 1, minoY, minoAngle, minoType)) {
				minoX++;
			}
			break;
		case LEFT:
			if(isUpdate(minoX - 1, minoY, minoAngle, minoType)) {
				minoX--;
			}
			break;
		case DOWN:
			if(isUpdate(minoX, minoY + 1, minoAngle, minoType)) {
				if(minoY == 0) {
					Thread.sleep(10);
					minoY++;
				}else {
					minoY++;
				}
			}
			break;
		default:
			break;
		}
		draw();
	}

	public void draw() {

		for(int i=0;i<GAME_HEIGHT;i++) {
			for(int j=0;j<GAME_WIDTH;j++) {
				displayBuffer[i][j] = field[i][j];
			}
		}

		for(int i=0;i<MINO_HEIGHT;i++) {
			for(int j=0;j<MINO_WIDTH;j++) {
				displayBuffer[(minoY + i) % GAME_HEIGHT][(minoX + j + GAME_WIDTH) % GAME_WIDTH] |= minoShapes[minoType][minoAngle][i][j];
			}
		}

		for(int i=0;i<GAME_HEIGHT;i++) {
			for(int j=0;j<GAME_WIDTH;j++) {

				switch (displayBuffer[i][j]) {
				case BLUE:
					gc.setFill(Color.BLUE);
					gc.setStroke(Color.BLACK);
					gc.fillRect(j * BLOCK_SIZE, i * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
					gc.strokeRect(j * BLOCK_SIZE, i * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
					break;
				case ORANGE:
					gc.setFill(Color.ORANGE);
					gc.setStroke(Color.BLACK);
					gc.fillRect(j * BLOCK_SIZE, i * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
					gc.strokeRect(j * BLOCK_SIZE, i * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
					break;
				case MAGENTA:
					gc.setFill(Color.MAGENTA);
					gc.setStroke(Color.BLACK);
					gc.fillRect(j * BLOCK_SIZE, i * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
					gc.strokeRect(j * BLOCK_SIZE, i * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
					break;
				case  GREEN:
					gc.setFill(Color.LIGHTGREEN);
					gc.setStroke(Color.BLACK);
					gc.fillRect(j * BLOCK_SIZE, i * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
					gc.strokeRect(j * BLOCK_SIZE, i * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
					break;
				case YELLOW:
					gc.setFill(Color.YELLOW);
					gc.setStroke(Color.BLACK);
					gc.fillRect(j * BLOCK_SIZE, i * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
					gc.strokeRect(j * BLOCK_SIZE, i * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
					break;
				case CYAN:
					gc.setFill(Color.CYAN);
					gc.setStroke(Color.BLACK);
					gc.fillRect(j * BLOCK_SIZE, i * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
					gc.strokeRect(j * BLOCK_SIZE, i * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
					break;
				case RED:
					gc.setFill(Color.RED);
					gc.setStroke(Color.BLACK);
					gc.fillRect(j * BLOCK_SIZE, i * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
					gc.strokeRect(j * BLOCK_SIZE, i * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
					break;
				case BLACK:
					gc.setFill(Color.BLACK);
					gc.setStroke(Color.BLACK);
					gc.fillRect(j * BLOCK_SIZE, i * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
					gc.strokeRect(j * BLOCK_SIZE, i * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
					break;
				default:
					break;
				}

			}
		}

	}

	boolean isUpdate(int X, int Y, int Angle, int Type) {

		for(int i=0;i<MINO_HEIGHT;i++ ) {
			for(int j=0;j<MINO_WIDTH;j++) {
				if(minoShapes[Type][Angle][i][j] != 0) {
					int tempX = X + j;
					int tempY = Y + i;
					if(tempX < 0 || tempX >= GAME_WIDTH || tempY >= GAME_HEIGHT || field[tempY][tempX] != 0) {
						return false;
					}
				}
			}
		}
		return true;

	}

	public void initialize() {
		for(int i=0;i<MINO_HEIGHT;i++) {
			for(int j=0;j<MINO_WIDTH;j++) {
				field[i][j] = 0;
			}
		}
	}

	public void minoReset() {
		minoX = 5;
		minoY = 0;
		minoAngle = rand(ANGLE_MAX);
		minoType = rand(TYPE_MAX);
		if(!isUpdate(minoX, minoY, minoAngle, minoType)) {
			isGameOver = true;
		}

	}

	int rand(int type) {
		int num = (int)(Math.random() * type);
		return num;
	}

	public static void main(String args[]) {
		launch(args);
	}

}