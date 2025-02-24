package name.panitz.game2d;
import java.awt.*;
import javax.swing.ImageIcon;
public record ImageObject( Vertex pos, Vertex velocity
                  , double width, double height
                  , String fileName, Image image)
    implements GameObj{

  public ImageObject{
    var url = getClass().getClassLoader().getResource(fileName);
    if (url == null) {
      System.err.println("Resource not found: " + fileName);
    } else {
      var iIcon = new ImageIcon(url);
      width = iIcon.getIconWidth();
      height = iIcon.getIconHeight();
      image = iIcon.getImage();
      System.out.println("Loaded image: " + fileName);
    }
  }

  public ImageObject(Vertex pos,Vertex velocity, String fileName){
    this(pos,velocity,0,0,fileName,null);
  }

  public ImageObject(String fileName){
    this(new Vertex(0,0),new Vertex(0,0),fileName);
  }

  public void paintTo(Graphics g){
    if (image != null) {
      g.drawImage(image, (int) pos.x, (int) pos.y, null);
    } else {
      System.err.println("No image to draw for: " + fileName);
    }
  }
}

