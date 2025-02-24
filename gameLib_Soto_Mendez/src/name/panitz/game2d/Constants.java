package name.panitz.game2d;
public class Constants {


    public static class PlayerConstants {
        public static final int IDLE = 0;
        public static final int WALK = 1;
        public static final int JUMP = 2;
        public static final int STOP = 3;

        public static int GetSpriteAmount(int player_action) {
            switch (player_action) {
                case WALK:
                    return 6;
                case IDLE:
                case JUMP:
                case STOP:
                    return 2;
                default:
                    return 1;
            }
        }
    }
}
