package name.panitz.game2d.dino;

import java.io.IOException;
import java.io.InputStream;

public class testResource {
    public static void main(String[] args) {
        String fileName = "dino.png"; // Der Name der Bilddatei
        var url = testResource.class.getResource("/" + fileName); // Versuche, den Ressourcenpfad zu ermitteln
        if (url == null) {
            System.err.println("Resource not found: " + fileName);
        } else {
            System.out.println("Resource found: " + url.toExternalForm());
        }

        try (InputStream is = testResource.class.getResourceAsStream("/" + fileName)) {
            if (is == null) {
                System.err.println("Resource not found when using InputStream: " + fileName);
            } else {
                System.out.println("Resource successfully accessed as InputStream.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}