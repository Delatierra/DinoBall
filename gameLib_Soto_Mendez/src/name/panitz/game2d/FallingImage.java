package name.panitz.game2d;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import static name.panitz.game2d.Constants.PlayerConstants.*;



public class FallingImage extends AbstractGameObj {
	static double G = 9.81;
	double v0;
	int t = 0;
	Image image;

	private BufferedImage[] [] animations;
	private int aniTick, aniIndex, aniSpeed = 35;
	private int playerAction = IDLE;
	private boolean moving = false;

	public boolean isJumping = false;

	public FallingImage(String imageFileName, Vertex corner, Vertex movement) {
		super(corner,  movement, 0 ,0);
		var iIcon
				= new ImageIcon(getClass().getClassLoader().getResource(imageFileName));
		width = iIcon.getIconWidth();
		height=iIcon.getIconHeight();
		image = iIcon.getImage();
	}

	public FallingImage(Vertex corner, Vertex movement, String imageFileName) {
		super(corner,  movement, 0 ,0);
		System.out.println("Initializing FallingImage with animations: " + imageFileName);
		loadAnimations(imageFileName); // Sicherstellen, dass dies aufgerufen wird
		System.out.println("Animations array initialized: " + (animations != null));
	}

	public void update() {
		updateAnimationTick();
	}

	public void stop() {
		pos().add(velocity().mult(-1.1));
		velocity().x = 0;
		velocity().y = 0;
		isJumping = false;
		playerAction = IDLE;
	}

	public void restart() {
		double oldX = velocity().x;
		pos().add(velocity().mult(-1.1));
		velocity().x = oldX;
		velocity().y = 0;
		isJumping = false;
	}


	public void left() {
		if (!isJumping) {
			velocity().x = -1;
			playerAction = WALK;
			moving = true;
		}
	}

	public void right() {
		if (!isJumping) {
			velocity().x = +1;
			playerAction = WALK;
			moving = true;
		}
	}

	public void jump() {
		if (!isJumping) {
			startJump(-3.7);
			playerAction = JUMP;
			moving = false;
		}
	}

	public void startJump(double v0) {
		isJumping = true;
		this.v0 = v0;
		t = 0;
	}

	@Override
	public void move() {
		if (isJumping) {
			t++;
			double v = v0 + G * t / 200;
			velocity().y = v;
			moving = false;
		} else if (!moving) {
			playerAction = IDLE;
		}
		super.move();
	}

	@Override
	public void paintTo(Graphics g) {
		if (animations == null) {
			g.drawImage(image, (int)pos.x, (int)pos.y, null);
		} else {
			//System.out.println("Drawing animation frame [" + playerAction + "][" + aniIndex + "]");
			g.drawImage(animations[playerAction][aniIndex], (int) pos.x, (int) pos.y, 64, 64, null);
		}
	}

	private void updateAnimationTick() {
		aniTick++;
		if(aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			}
		if(aniIndex >= GetSpriteAmount(playerAction)) {
				aniIndex = 0;
		}
	}

	public static BufferedImage GetSpriteAtlas(String imageFileName){
		BufferedImage img = null;
		InputStream is = FallingImage.class.getResourceAsStream( "/" +imageFileName);
		try {
			if (is == null) {
				System.err.println("Resource not found: " + imageFileName);
				return null;
			}
			img = ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return img;
	}
	private void loadAnimations(String imageFileName) {
		this.width =60;
		this.height = 60;
		BufferedImage img = GetSpriteAtlas(imageFileName);
		if (img == null) {
			System.err.println("Failed to load sprite atlas for: " + imageFileName);
			return; // Exit the method if the image is null
		}
		animations = new BufferedImage[4][6];
		for (int j = 0; j < animations.length; j++) {
			for (int i = 0; i < animations[j].length; i++) {
				try {
					animations[j][i] = img.getSubimage(i * 16, j * 16, 16, 16);
					System.out.println("Loaded subimage [" + j + "][" + i + "]");
				} catch (RasterFormatException e) {
					System.err.println("Error creating subimage at index [" + j + "][" + i + "]");
					e.printStackTrace();
				}
			}
		}
		System.out.println("Animations successfully loaded for: " + imageFileName);
	}
}