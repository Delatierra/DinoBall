package name.panitz.game2d.dino;

import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_RIGHT;
import static java.awt.event.KeyEvent.VK_UP;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import name.panitz.game2d.FallingImage;
import name.panitz.game2d.Game;
import name.panitz.game2d.GameObj;
import name.panitz.game2d.ImageObject;
import name.panitz.game2d.TextObject;
import name.panitz.game2d.Vertex;

public record DinoBall(FallingImage player, int width, int height, List<List<? extends GameObj>> goss,
					   List<ImageObject> walls, List<ImageObject> eggs, List<FallingImage> barrels,
					   List<TextObject> texts, int[] energyAndEggs, List<GameObj> hintergrund)
		implements Game {

	//static TextObject instructions;
	//static boolean gameStarted = false;

	static ImageObject newEgg(Vertex corner) {
		return new ImageObject(corner, new Vertex(0, 0), "egg.gif");
	}

	static ImageObject newWall(Vertex corner, String fileName) {
		return new ImageObject(corner, new Vertex(0, 0), fileName);
	}


	static final int GRID_WIDTH = 34;

	static String level1 = """
			1                 f      2
			1                        2
			1e                    w  2
			1w                       2
			1                ww      2
			1                        2
			1    wwwwwwww          e 2
			1                      ww2
			1                 f      2
			1w            wwww       2
			1      e                 2
			1      w          e      2
			1wwww           wwww     2
			1                        2
			1       www         2    2
			1                   2    2
			1              w    www  2
			1  p                     2
			1       wwww             2
			1             www        2
			1                     e g2
			1wwwwwww  wwwwww   wwwwwww""";

	public DinoBall() {
		this(new Dino(new Vertex(0, 0)), 26 * GRID_WIDTH, 22 * GRID_WIDTH
				,new ArrayList<>(),new ArrayList<>(),new ArrayList<>()
				,new ArrayList<>(),new ArrayList<>(),new int[] {0,0}, new ArrayList<>());
		init();

	}

	private void decreaseEnergy() {
		energyAndEggs[0]--;
		initTexts();
	}
	private void playerBarrelCollision() {
		for (var b : barrels()) {
			if (b.touches(player)) {
				decreaseEnergy();
				b.pos().moveTo(new Vertex(Math.random()*(width() - 2*40) + 40, -40));

			}
			if (b.pos().y > height()) {
				b.pos().moveTo(
						new Vertex(Math.random()*(width() - 2*40) + 40, -40));
			}
		}
	}

	private void fallingBarrel() {
		for (var b : barrels) {
			for (var wall : walls) {
				if (!b.isAbove(wall)&&!b.isUnderneath(wall)
						&& (
						b.isLeftOf(wall)&&b.pos().x+b.width()+b.velocity().x+1>wall.pos().x
								||
								b.isRightOf(wall)&&b.pos().x+b.velocity().x-1<wall.pos().x+wall.width()
				)) {
					b.velocity().x*=-1;

				}
			}
			boolean isStandingOnTop = false;
			for (var wall : walls) {

				if (b.isStandingOnTopOf(wall)) {
					isStandingOnTop = true;
					break;
				}
			}
			if (!isStandingOnTop && !b.isJumping) {
				b.startJump(0.1);
			}else if (isStandingOnTop){
				b.restart();
			}
		}

	}

	private void checkPlayerWallCollsions() {
		boolean isStandingOnTop = false;
		for (var wall : walls) {
			if (player.touches(wall)) {
				player.stop();
				return;
			}
			if (player.isStandingOnTopOf(wall)) {
				isStandingOnTop = true;
			}
		}

		if (!isStandingOnTop && !player.isJumping)
			player.startJump(0.05);
	}

	private void collectEggs() {
		ImageObject removeMe = null;
		for (var egg : eggs) {
			if (player().touches(egg)) {
				removeMe = egg;
				energyAndEggs[1]++;
				initTexts();
				break;
			}
		}
		if (removeMe != null)
			eggs.remove(removeMe);
	}

	@Override
	public void doChecks() {

		collectEggs();
		checkPlayerWallCollsions();
		fallingBarrel();
		playerBarrelCollision();
		if (player().pos().y > height()) {
			player().pos().moveTo(new Vertex(2*GRID_WIDTH, height() - 140)); //spawn
		}
		player.update(); //agrego Update de la clase FallingImage
	}


	public boolean lost() {
		return energyAndEggs[0] <= 0;
	}

	public boolean won() {
		return eggs.isEmpty();
	}

	@Override
	public void init() {
		//instructions = new TextObject(new Vertex(100, 100), "Press ENTER to start the game. Use arrow keys to move.");
		barrels.clear();
		eggs.clear();
		walls.clear();

		readLevel();
		initTexts();

		hintergrund().clear();
		hintergrund().add(new ImageObject("BG.png"));
		goss().add(hintergrund());  // Hintergrund wird zuerst hinzugefügt

		goss().add(barrels);
		goss().add(walls);
		goss().add(eggs);
		goss().add(texts);

		energyAndEggs[0] = 10;
		energyAndEggs[1] = 0;

	}

	private void readLevel() {
		int l = 0;
		var lines = level1.split("\\n");
		for (String line : lines) {
			int col = 0;
			for (char c : line.toCharArray()) {
				switch (c) {
					case 'w'->walls.add(newWall(new Vertex(col * GRID_WIDTH, l * GRID_WIDTH), "wall.png"));
					case '1'->walls.add(newWall(new Vertex(col * GRID_WIDTH, l * GRID_WIDTH), "wall1.png"));
					case '2'->walls.add(newWall(new Vertex(col * GRID_WIDTH, l * GRID_WIDTH), "wall2.png"));
					case 'e'->eggs.add(newEgg(new Vertex(col * GRID_WIDTH, l * GRID_WIDTH)));
					case 'f'->barrels.add(new Barrel(new Vertex(col * GRID_WIDTH, l * GRID_WIDTH-1)));
					case 'p'->player().pos().moveTo(new Vertex(col * GRID_WIDTH, l * GRID_WIDTH - 2));
				}
				col++;
			}
			l++;
		}
	}

	private void initTexts() {
		texts.clear();
		texts.add(new TextObject(new Vertex(50, 20),"Life: " + energyAndEggs[0] ));
		texts.add(new TextObject(new Vertex(50, 50),"Eggs: " + energyAndEggs[1] ));
	}

	@Override
	public void keyPressedReaction(KeyEvent keyEvent) {
		switch (keyEvent.getKeyCode()) {
			case VK_RIGHT -> player().right();
			case VK_LEFT -> player().left();
			case VK_DOWN -> player().stop();
			case VK_UP -> player().jump();
		}

	}

	public static void main(String... args) {
		new DinoBall().play();
	}



}
