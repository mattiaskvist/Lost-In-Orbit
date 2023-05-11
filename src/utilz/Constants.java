package utilz;
/**
 * The constants class is responsible for all the player constants that are used to create animations or movement
 */
public class Constants {
    /**
     * The PlayerDirections class assigns a fixed value to different directions to keep them as constants
     */
    public static class PlayerDirections {
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

    public static class PlayerSize{
        public static final int WIDTH = 41;
        public static final int HEIGHT = 51;
        public static final int XOFFSET = 43;
        public static final int YOFFSET = 12;
    }
    
    
    /**
     * The PlayerAnimation inner class returns an integer value depending on the current player action.
     * This is necessary in order to return the correct amount of sprite animations with respect to the action
     * 
     * Through this class, the animation ticker will always have the correct maximum index that it is allowed
     * to iterate through.
     */
    public static class PlayerAnimations {
        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int JUMP = 2;
        public static final int FALLING = 3;
        public static final int GROUND = 4;
        public static final int HIT = 5;
        public static final int ATTACK_1 = 6;
        public static final int ATTACK_JUMP_1 = 7;
        public static final int ATTACK_JUMP_2 = 8;

        /**
         * Returns the size of the sub arrays depending on the current player action
         * in order to display the correct animation inside of the animation methods
         * 
         * @param playerAction The current player action in the form of an integer
         * @return the amount of indexes for that specific animation
         */
        public static int getSpriteAmount(int playerAction) {
            switch(playerAction) {
                case RUNNING:
                    return 6;
                case IDLE:
                    return 5;
                case HIT:
                    return 4;
                case JUMP: 
                case ATTACK_1:
                case ATTACK_JUMP_1:
                case ATTACK_JUMP_2:
                    return 3;
                case GROUND:
                    return 2;
                case FALLING:
                default:
                    return 1;
            }
        }
    }

    /**
     * The EnemyAnimation inner class returns an integer value depending on the current enemy action.
     * This is necessary in order to return the correct amount of sprite animations with respect to the action
     * 
     * Through this class, the animation ticker will always have the correct maximum index that it is allowed
     * to iterate through.
     */
    public static class EnemyAnimations {
        public static final int IDLE = 0;
        public static final int WALKING = 1;
        public static final int ATTACKING = 2;
        public static final int DAMAGED = 3;
        public static final int KILLED = 4;

        /**
         * Returns the size of the sub arrays depending on the current enemy action
         * in order to display the correct animation inside of the animation methods
         * 
         * @param enemyAction The current enemy action in the form of an integer
         * @return the amount of indexes for that specific animation
         */
        public static int getSpriteAmount(int enemyAction) {
            switch(enemyAction) {
                case IDLE:
                    return 9;
                case WALKING: 
                    return 6;
                case ATTACKING:
                    return 7;
                case DAMAGED:
                    return 4;
                case KILLED:
                    return 5;
                default:
                    return 1;
            }
        }
    }

    public static class CrabbySize {
        public static final int WIDTH = 45;
        public static final int HEIGHT = 60;
        public static final int XOFFSET = 43;
        public static final int YOFFSET = 12;
    }

    public static class SharkSize {
        public static final int WIDTH = 34;
        public static final int HEIGHT = 30;
        public static final int XOFFSET = 43;
        public static final int YOFFSET = 12;
    }

    public static class PinkStarSize {
        public static final int WIDTH = 34;
        public static final int HEIGHT = 30;
        public static final int XOFFSET = 43; 
        public static final int YOFFSET = 12; 
    }

    public static class TextSize {
        public static final int WIDTH = 9;
        public static final int HEIGHT = 11;
    }
}
