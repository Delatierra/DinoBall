package name.panitz.game2d.dino;

import name.panitz.game2d.FallingImage;
import name.panitz.game2d.Vertex;

public class Dino extends FallingImage {
  public Dino(Vertex corner) {
    super( corner, new Vertex(0, 0),"dino.png");
  }
}
