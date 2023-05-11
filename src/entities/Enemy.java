package entities;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import main.SoundPlayer;
import utilz.Resources;

import static utilz.Constants.EnemyAnimations.*;
import static utilz.Constants.CrabbySize.*;

/**
 * The enemy class is the parent class for all enemies in the game. It contains
 * the basic functionality of an enemy, such as movement, animations, and
 * interaction with the player. It also contains the method
 * loadAnimations(), which is implemented by each enemy class to load their
 * animations.
 */
public class Enemy extends Entity {
    protected BufferedImage[][] animations;
    protected int animationTick, animationIndex, animationSpeed = 8;
    protected int enemyAction = WALKING;
    private boolean moving = false, attacking = false;
    protected float enemySpeed = -3.0f;
    private float gravity = 0.14f;
    private float velocityY = 0;
    protected Hitbox hitbox;
    private boolean isGrounded = false;
    protected Player player;
    protected String spriteFilePath;
    private boolean facingRight = false;

    private boolean canTakeDamage = true;
    private boolean isAlive = true;
    protected String attackFile = Resources.SHARK_ATTACK;
    protected String deathFile = Resources.ENEMY_DEATH;
    protected float volume = 0.3f;

    public Enemy(float x, float y, Player player) {
        super(x, y);
        loadAnimations();
        hitbox = new Hitbox(x, y, WIDTH, HEIGHT, null);
        this.player = player;
    }

    /**
     * Checks for enemy-player interaction and updates the enemy's state accordingly
     */
    public void updatePlayerInteraction() {
        if (player.getKnifeHitbox().intersects(hitbox) && player.isAttacking() && canTakeDamage) {
            System.out.println("Player hit enemy");
            enemySpeed = 0;
            canTakeDamage = false;

            // Enemy dies
            enemyAction = KILLED;
            SoundPlayer.playSoundEffect(deathFile, 0.3f);

            // After some time, enemy is removed
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            isAlive = false;
                        }
                    },
                    600);

        } else if (hitbox.intersects(player.getPlayerHitbox()) && isAlive && player.getDamageCooldown() == 0
                && canTakeDamage) {
            System.out.println("Enemy hit player");
            // Send player back and change direction
            // set enemy action to attacking
            enemyAction = ATTACKING;
            SoundPlayer.playSoundEffect(attackFile, volume);
            if (player.getDamageCooldown() == 0) {
                if(player.getPlayerHitbox().getX() < hitbox.getX()) {
                    player.updateX(-25);
                } else {
                    player.updateX(25);
                }
                player.takeDamage();
            }
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            enemyAction = WALKING;
                        }
                    },
                    700);

            enemySpeed *= -1;

        }
    }

    /**
     * Updates the enemy state and position based on events in the game
     */
    public void update() {

        // movement
        updatePos();

        // Update hitbox position
        hitbox.updatePos(x + XOFFSET, y + YOFFSET);

        // update hitbox collision with player
        updatePlayerInteraction();

        // Updates the animation that is currently being iterated through
        updateAnimationTick();

        // Sets the current animation
        // setAnimation();

        if(enemyAction == KILLED){
            return;
        }

        if (player.getGame().getFPS()==120) {
            animationSpeed = 15;
            if(facingRight) {
                enemySpeed = 1.5f;
            } else {
                enemySpeed = -1.5f;
            }
        } else if (player.getGame().getFPS()==60){
            animationSpeed = 8;
            if(facingRight) {
                enemySpeed = 3.0f;
            } else {
                enemySpeed = -3.0f;
            }
        }
    }

    /**
     * Renders enemy elements
     * 
     * @param g Graphics object
     */
    public void render(Graphics g) {

        // Character drawn on the screen (if alive)
        if (isAlive) {
            BufferedImage image = animations[enemyAction][animationIndex];

            // Flip the image if the enemy is moving to the left (Ai generated)
            if (facingRight) {
                AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
                tx.translate(-image.getWidth(null), 0);
                AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
                image = op.filter(image, null);
            }
            g.drawImage(image, (int) x, (int) y, 128, 80, null);
        }

        // Draw hitbox for debugging
        // g.setColor(Color.RED);
        // g.drawRect((int) hitbox.getX(), (int) hitbox.getY(), (int) hitbox.getWidth(), (int) hitbox.getHeight());

    }

    /**
     * Loads the animations for the enemy into a 2D array
     */

    protected void loadAnimations() {
        BufferedImage img = Resources.getResource(spriteFilePath);

        animations = new BufferedImage[5][9];
        for (int i = 0; i < animations.length; i++) {
            for (int j = 0; j < animations[i].length; j++) {
                animations[i][j] = img.getSubimage(j * 72, i * 32, 72, 32);
            }
        }
    }

    /**
     * Updates the animation tick and index to iterate through the animation
     * Default set to Crabby's sprite amount
     */
    protected void updateAnimationTick() {

        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= getSpriteAmount(enemyAction)) {
                animationIndex = 0;
                attacking = false;
            }
        }
    }

    /**
     * Can be used to set the correct animation depending on the enemy's state
     * Needs work, not used TODO
     */
    private void setAnimation() {
        if (moving) {
        enemyAction = WALKING;
        } else {
        enemyAction = IDLE;
        }
    }

    /**
     * Handles the enemy's movement based on its current state and position
     */
    private void updatePos() {

        moving = false;

        // Gravity
        for (int i = 0; i < Math.abs(velocityY); i++) {
            y += Math.signum(velocityY);
            if (hitbox.checkCollision(x, y + YOFFSET + HEIGHT) ||
                    hitbox.checkCollision(x + WIDTH, y + YOFFSET + HEIGHT)) {
                // Landed on the ground
                velocityY = 0;
                y = (int) y; // Snap to the ground
                isGrounded = true;
                falling = false;
                break;
            }
        }

        // Handle moving left/right, Change direction if hit a wall or edge
        if (isGrounded) {
            if (!hitbox.checkCollision(x + XOFFSET, y + YOFFSET + HEIGHT + 1) ||
                    !hitbox.checkCollision(x + XOFFSET + WIDTH, y + YOFFSET + HEIGHT + 1) ||
                    hitbox.checkCollision(x + XOFFSET + WIDTH, y + YOFFSET) ||
                    hitbox.checkCollision(x + XOFFSET, y + YOFFSET)) {
                enemySpeed *= -1;
            }
        }

        if (!isGrounded) {
            velocityY += gravity;
            y += velocityY;
        }

        if (isGrounded) {
            x += enemySpeed;
        }

        if(enemySpeed < 0) {
            facingRight = false;
        } else {
            facingRight = true;
        }
    }

    public Hitbox getHitbox() {
        return hitbox;
    }

}
