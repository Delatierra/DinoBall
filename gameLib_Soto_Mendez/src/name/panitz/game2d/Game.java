package name.panitz.game2d;
import java.util.List;
import java.awt.event.*;
import java.awt.*;

public interface Game{

  // Dimensiones del área de juego
  int width();
  int height();
  // El objeto principal del jugador
  GameObj player();
  // Listas de otros objetos del juego organizados en capa
  List<List<? extends GameObj>> goss();

  // Médtodo para inicializar o reiniciar el estado del juego
  void init();

  // Médtodo para realizar comprobaciones y actualizar el estado del juego
  void doChecks();
  // Médtodo para manejar la entrada del teclado
  void keyPressedReaction(KeyEvent keyEvent);

  // Médtodo por defecto para mover todos los objetos del juego
  default void move(){
  	if (ended()) return;
    for (var gos:goss()) gos.forEach(go -> go.move());
    player().move();
  }    

  boolean won();
  boolean lost();

  default boolean ended() {
	return won()||lost();
  }

  // Médtodo por defecto para dibujar todos los objetos del juego

  default void paintTo(Graphics g){
    for (var gos:goss()) gos.forEach( go -> go.paintTo(g));
    player().paintTo(g); //implemento painTo

  }

  // Médtodo por defecto para iniciar el juego

  default void play(){
    init();
    var f = new javax.swing.JFrame();
    f.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
    f.add(new SwingScreen(this));
    f.pack();
    f.setVisible(true);
  }
}

