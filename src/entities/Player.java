package entities;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import levels.LevelHandler;

import main.Game;
import main.GameState;
import main.SoundPlayer;
import utilz.Resources;

import static utilz.Constants.PlayerAnimations.*;
import static utilz.Constants.PlayerSize.*;

//import enums for gamestates
import static main.GameState.*;

/**
 * The player class is responsible for handling the player's movement,
 * animations,
 * and interactions with the environment.
 */
public class Player extends Entity {
    private BufferedImage[][] animations;
    private int animationTick, animationIndex, animationSpeed = 8;
    private int playerAction = IDLE;
    private boolean moving = false, attacking = false, jumping = false, falling = false;
    private boolean left, right;
    private boolean facingLeft = false;
    private float playerSpeed = 5.0f;
    private float gravity = 0.17f;
    private float jumpSpeed = 4.0f;
    private float velocityY = 0;
    private Hitbox hitbox;
    private Hitbox spikeHitbox;
    private Hitbox arrowHitbox;
    private Game game;
    private LevelHandler levelHandler;
    private int levelCounter = 0;

    private float startX, startY;
    private Hitbox knifeHitbox;
    private boolean isGrounded = false;

    private int jumpCooldown = 0;
    private int attackCooldown = 0;

    private int collectibles = 0;
    private int collectiblesBeforeCheckpoint = 0;

    private int hp = 3;
    private int maxHp = 3;
    private BufferedImage[] hearts;
    private int damageCooldown = 0;

    private int runningSoundCooldown = 0;

    public Player(float x, float y, Game game, LevelHandler levelHandler) {
        super(x, y);
        startX = x;
        startY = y;
        this.game = game;
        this.levelHandler = levelHandler;
        loadAnimations();
        hitbox = new Hitbox(x + XOFFSET, y + YOFFSET, WIDTH, HEIGHT, null);
        spikeHitbox = new Hitbox(x + XOFFSET, y + YOFFSET, WIDTH, HEIGHT, null);

        arrowHitbox = new Hitbox(1150, 650, 35, 42, null);
        knifeHitbox = new Hitbox(x + XOFFSET + WIDTH, y + YOFFSET + 20, 42, 15, null);

        if (game.getFPS() == 60) {
            playerSpeed = 5.0f;
        } else if (game.getFPS() == 120) {
            playerSpeed = 2.50f;
        }
    }

    /**
     * Updates player elements
     */
    public void update() {

        // Movement
        updatePos();

        // Update hitbox position
        hitbox.updatePos(x + XOFFSET, y + YOFFSET);
        spikeHitbox.updatePos(x + XOFFSET, y + YOFFSET);

        // Update knife hitbox position
        if (facingLeft) {
            knifeHitbox.updatePos(x + XOFFSET - 42, y + YOFFSET + 30);
        } else {
            knifeHitbox.updatePos(x + XOFFSET + WIDTH, y + YOFFSET + 30);
        }

        // Updates the animation that is currently being iterated through
        updateAnimationTick();

        // Sets the current animation
        setAnimation();

        // Update cooldowns
        updateCooldowns();

        // Update variables after FPS
        updateFpsVariables();

    }

    /**
     * Updates cooldowns
     */
    public void updateCooldowns() {
        if (game.getFPS() == 120) {
            if (damageCooldown > 0) {
                damageCooldown--;
            }
            if (attackCooldown > 0) {
                attackCooldown--;
            }
            if (runningSoundCooldown > 0) {
                runningSoundCooldown--;
            }
        } else if (game.getFPS() == 60) {
            if (damageCooldown > 0) {
                damageCooldown -= 2;
            }
            if (attackCooldown > 0) {
                attackCooldown -= 2;
            }
            if (runningSoundCooldown > 0) {
                runningSoundCooldown -= 2;
            }
        }
        if (jumpCooldown > 0) {
            jumpCooldown--;
        }
    }

    /**
     * Renders character images on screen
     * 
     * @param g Graphics object
     */
    public void render(Graphics g) {

        // Character drawn on the screen
        BufferedImage image = animations[playerAction][animationIndex];

        // Flip the image if the player is moving to the left (Ai generated)
        if (facingLeft) {
            AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
            tx.translate(-image.getWidth(null), 0);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            image = op.filter(image, null);
        }

        g.drawImage(image, (int) x, (int) y, 128, 80, null);

        // Draw hitbox for debugging
        // g.setColor(Color.RED);
        // g.drawRect((int) hitbox.getX(), (int) hitbox.getY(), (int) hitbox.getWidth(),
        // (int) hitbox.getHeight());

        // // Draw knife hitbox for debugging
        // g.setColor(Color.BLUE);
        // g.drawRect((int) knifeHitbox.getX(), (int) knifeHitbox.getY(), (int)
        // knifeHitbox.getWidth(),
        // (int) knifeHitbox.getHeight());

        // Draw arrow hitbox for debugging
        // g.setColor(Color.GREEN);
        // g.drawRect((int) arrowHitbox.getX(), (int) arrowHitbox.getY(), (int)
        // arrowHitbox.getWidth(),
        // (int) arrowHitbox.getHeight());

        // Draw hearts
        drawHearts(g);

        // Draw collectible counter
        Collectible.renderCollectibleCounter(g, this, 100, -15);

    }

    /**
     * Draw hearts on the screen
     * 
     * @param g Graphics object
     */
    public void drawHearts(Graphics g) {

        // Draw filled hearts
        for (int i = 0; i < hp; i++) {
            g.drawImage(hearts[1], 10 + i * 32, 10, 32, 32, null);
        }

        // Draw empty hearts
        for (int i = hp; i < maxHp; i++) {
            g.drawImage(hearts[0], 10 + i * 32, 10, 32, 32, null);
        }
    }

    /**
     * Loads the animations for the player into arrays
     */
    private void loadAnimations() {
        BufferedImage img = Resources.getResource(Resources.PLAYER_SPRITE);

        animations = new BufferedImage[9][6];
        for (int i = 0; i < animations.length; i++) {
            for (int j = 0; j < animations[i].length; j++) {
                animations[i][j] = img.getSubimage(j * 64, i * 40, 64, 40);
            }
        }

        BufferedImage heartImg = Resources.getResource(Resources.heart_sprite);

        hearts = new BufferedImage[2];
        hearts[0] = heartImg.getSubimage(64, 0, 32, 32);
        hearts[1] = heartImg.getSubimage(0, 0, 32, 32);

    }

    /**
     * Updates the position of the player
     * Checks if the player is colliding with a wall
     */
    private void updatePos() {
        moving = false;

        // Handle jumping
        if (jumping && isGrounded && jumpCooldown <= 0) {
            isGrounded = false;
            jumpCooldown = 50; // 30 frames of cooldown
            velocityY -= jumpSpeed;
            SoundPlayer.playSoundEffect(Resources.JUMP, 1f);
        }
        // Check if player hits the ceiling when jumping
        if (velocityY < 0 && (hitbox.checkCollision(x + XOFFSET + 3, y + YOFFSET - 1) ||
                hitbox.checkCollision(x + XOFFSET + WIDTH - 3, y + YOFFSET - 1))) {
            velocityY = 0;
            jumping = false;
        }

        // Handle collision with ground
        for (int i = 0; i < Math.abs(velocityY); i++) {
            y += Math.signum(velocityY);
            if (hitbox.checkCollision(x + XOFFSET + 3, y + YOFFSET + HEIGHT + 1) ||
                    hitbox.checkCollision(x + XOFFSET + WIDTH - 3, y + YOFFSET + HEIGHT + 1) ||
                    hitbox.checkCollision(x + XOFFSET + WIDTH / 2, y + YOFFSET + HEIGHT + 1)) {
                // Player has landed on the ground
                velocityY = 0;
                y = (int) y; // Snap player to the ground
                isGrounded = true;
                falling = false;
                break;
            }
        }
        // If player is inside the ground, move player out of ground
        if (hitbox.checkCollision(x + XOFFSET + 3, y + YOFFSET + HEIGHT) ||
                hitbox.checkCollision(x + XOFFSET + WIDTH - 3, y + YOFFSET + HEIGHT) ||
                hitbox.checkCollision(x + XOFFSET + WIDTH / 2, y + YOFFSET + HEIGHT)) {
            y = (y - 1);
        }

        // Handle falling off the edge
        if (isGrounded) {
            if (!hitbox.checkCollision(x + XOFFSET, y + YOFFSET + HEIGHT + 1) &&
                    !hitbox.checkCollision(x + XOFFSET + WIDTH, y + YOFFSET + HEIGHT + 1) &&
                    !hitbox.checkCollision(x + XOFFSET + WIDTH / 2, y + YOFFSET + HEIGHT + 1)) {
                isGrounded = false;
                falling = true;
            }
        }

        // Handle gravity and vertical movement
        if (!isGrounded) {
            velocityY += gravity;
            y += velocityY;
        }

        // Handle jump cooldown
        if (jumpCooldown > 0) {
            jumpCooldown--;
        }

        if (jumpCooldown == 0) {
            jumping = false;
        }

        // Handle movement and collision left and right
        if (left && !right) {
            if (!hitbox.checkCollision(x + XOFFSET - 2, y + YOFFSET) &&
                    !hitbox.checkCollision(x + XOFFSET - 2, y + YOFFSET + HEIGHT) &&
                    !hitbox.checkCollision(x + XOFFSET - 2, y + YOFFSET + HEIGHT / 2) &&
                    !hitbox.checkCollision(x + XOFFSET - 2, y + YOFFSET + HEIGHT - 10)) {
                x -= playerSpeed;
                moving = true;
            }
        } else if (right && !left) {
            if (!hitbox.checkCollision(x + XOFFSET + WIDTH + 2, y + YOFFSET) &&
                    !hitbox.checkCollision(x + XOFFSET + WIDTH + 2, y + YOFFSET + HEIGHT) &&
                    !hitbox.checkCollision(x + XOFFSET + WIDTH + 2, y + YOFFSET + HEIGHT / 2)
                    && !hitbox.checkCollision(x + XOFFSET + WIDTH + 2, y + YOFFSET + HEIGHT - 10)) {
                x += playerSpeed;
                moving = true;
            }
        }

        if (moving && isGrounded && runningSoundCooldown <= 0) {
            SoundPlayer.playSoundEffect(Resources.PLAYER_RUNNING, 0.2f);
            if (game.getFPS() == 60) {
                runningSoundCooldown = 80;
            } else if (game.getFPS() == 120) {
                runningSoundCooldown = 40;
            }
        }

        // check if player is inside wall, move player out of wall
        if (hitbox.checkCollision(x + XOFFSET, y + YOFFSET + HEIGHT) ||
                hitbox.checkCollision(x + XOFFSET, y + YOFFSET) ||
                hitbox.checkCollision(x + XOFFSET, y + YOFFSET + HEIGHT / 2)) {
            x = (x + 2);
        } else if (hitbox.checkCollision(x + XOFFSET + WIDTH, y + YOFFSET + HEIGHT - 3) ||
                hitbox.checkCollision(x + XOFFSET + WIDTH, y + YOFFSET - 3) ||
                hitbox.checkCollision(x + XOFFSET + WIDTH, y + YOFFSET + HEIGHT / 2 - 3)) {
            x = (x - 2);
        }

        // Check if player is colliding with an arrow to switch to the next level
        if (hitbox.intersects(arrowHitbox) && levelCounter < levelHandler.getLevelSprite().size() - 1) {
            System.out.println("Next level!");
            game.getLevelHandler().nextLevel();
        }

        // Check if player is within bounds, if not, return
        if (x + XOFFSET < 0 || x + XOFFSET + WIDTH >= Game.GAME_WIDTH || y < 0 || y > Game.GAME_HEIGHT) {
            return;
        }

        // Check if player is colliding with spikes and restart the level
        if (spikeHitbox.checkCollision(x + XOFFSET, y + YOFFSET + HEIGHT - 10) ||
                spikeHitbox.checkCollision(x + XOFFSET + WIDTH, y + YOFFSET + HEIGHT - 10) ||
                spikeHitbox.checkCollision(x + XOFFSET + WIDTH / 2, y + YOFFSET + HEIGHT - 10)
                || spikeHitbox.checkCollision(x + XOFFSET + WIDTH, y + YOFFSET + HEIGHT - 10)
                || spikeHitbox.checkCollision(x + XOFFSET, y + YOFFSET + HEIGHT - 10)) {
            if (damageCooldown <= 0) {
                playerSpeed = 0;
                takeDamage();
                System.out.println("Player hit by spikes");
            }
            if (hp > 0) {
                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                respawn();
                            }
                        },
                        150);
            }
        }
    }

    /**
     * Updates the animation by increasing the index of the subimages in order to
     * create a moving image
     */
    private void updateAnimationTick() {
        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= getSpriteAmount(playerAction)) {
                animationIndex = 0;
                attacking = false;
            }
        }
    }

    /**
     * Update player position and spawnpoints after level change
     */
    public void nextLevel(LevelHandler levelHandler) {

        // switchcase depending on level
        // set x and y to the spawnpoint of the level
        // set arrowhitbox to the spawnpoint of the level
        switch (levelHandler.getLevelCounter()) {
            case 1:
                x = 100;
                y = 600;
                break;
            case 2:
                x = 1000;
                y = 600;
                break;
            case 3:
                collectiblesBeforeCheckpoint = collectibles;
                x = 1100;
                y = 100;
                break;
            case 4:
                x = 100;
                y = 400;
                break;
            case 5:
                x = 50;
                y = 30;
                break;
            case 6:
                collectiblesBeforeCheckpoint = collectibles;
                x = 50;
                y = 400;
                break;
            case 7:
                x = 1100;
                y = 500;
                break;
            case 8:
                x = 40;
                y = 30;
                break;
            case 9:
                x = 10;
                y = 500;

            default:
                break;
        }
        hitbox.setLevel(levelHandler.getLevelSprite().get(levelHandler.getLevelCounter()));
        arrowHitbox.setLevel(levelHandler.getLevelSprite().get(levelHandler.getLevelCounter()));
        spikeHitbox.setLevel(levelHandler.getSpikeSprite().get(levelHandler.getLevelCounter()));
        startX = x;
        startY = y;
    }

    /**
     * Sets the animation depending on the current action
     */
    private void setAnimation() {
        int startAni = playerAction;

        if (moving) {
            playerAction = RUNNING;
        } else {
            playerAction = IDLE;
        }

        if (attacking) {
            playerAction = ATTACK_1;
            attackCooldown = 40;
        }

        if (velocityY < 0 && !isGrounded) {
            playerAction = JUMP;
        }

        if (velocityY > 0 && !isGrounded) {
            playerAction = FALLING;
        }

        if (damageCooldown > 0) {
            playerAction = HIT;
        }

        if (startAni != playerAction) {
            resetAnimationTick();
        }
    }

    /**
     * Resets the animation tick and index
     */
    private void resetAnimationTick() {
        animationTick = 0;
        animationIndex = 0;
    }

    /**
     * Set all directions to false
     */
    public void resetDirBool() {
        left = false;
        right = false;
    }

    /**
     * Handles the player taking damage
     */
    public void takeDamage() {
        if (Game.getGameState() != PLAYING) {
            return;
        }

        // Set the velocity to jump upwards
        velocityY = -2.3f;

        // Decrease the player's HP
        if (hp > 0 && damageCooldown <= 0) {
            hp--;
            SoundPlayer.playSoundEffect(Resources.PLAYER_HURT, 0.5f);
        }
        System.out.println("HP: " + hp);

        // Set the cooldown for jumping to prevent the player from jumping
        // immediately
        jumpCooldown = 50;

        // Set the damage cooldown to prevent the player from taking damage again
        // immediately
        damageCooldown = 120;

        // Attack cooldown to prevent the player from attacking immediately
        attackCooldown = 120;

        // Set gamestate to GAMEOVER if player dies
        if (hp == 0) {
            playerSpeed = 0;
            SoundPlayer.playSoundEffect(Resources.PLAYER_DEATH, 0.3f);
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            game.setGameState(GameState.GAMEOVER);
                        }
                    },
                    700);
        }

    }

    public boolean isLeft() {
        return left;
    }

    /**
     * Resets the player to the last checkpoint and resets all variables, including
     * collectible counter
     */
    public void reset() {
        // Checkpoints are at the beginning of each planet
        if (levelHandler.getLevelCounter() >= 0 && levelHandler.getLevelCounter() < 3) {
            levelHandler.setLevelCounter(0);
            collectibles = collectiblesBeforeCheckpoint;
        } else if (levelHandler.getLevelCounter() >= 3 && levelHandler.getLevelCounter() < 6) {
            levelHandler.setLevelCounter(3);
            collectibles = collectiblesBeforeCheckpoint;
        } else if (levelHandler.getLevelCounter() >= 6 && levelHandler.getLevelCounter() < 9) {
            levelHandler.setLevelCounter(6);
            collectibles = collectiblesBeforeCheckpoint;
        }
        x = startX;
        y = startY;
        hp = 3;
        damageCooldown = 0;
        attackCooldown = 0;
        jumpCooldown = 0;
        velocityY = 0;
        isGrounded = false;
        falling = false;
        jumping = false;
        moving = false;
        attacking = false;
        playerAction = IDLE;
        animationTick = 0;
        animationIndex = 0;
        if (game.getFPS() == 60) {
            playerSpeed = 5.0f;
        } else if (game.getFPS() == 120) {
            playerSpeed = 2.50f;
        }
    }

    /**
     * Respawns the player at the starting position
     * Reset player speed
     */
    public void respawn() {
        x = startX;
        y = startY;
        if (game.getFPS() == 60) {
            playerSpeed = 5.0f;
        } else if (game.getFPS() == 120) {
            playerSpeed = 2.50f;
        }
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public Hitbox getPlayerHitbox() {
        return hitbox;
    }

    public Hitbox getArrowHitbox() {
        return arrowHitbox;
    }

    public Hitbox getKnifeHitbox() {
        return knifeHitbox;
    }

    public int getAttackCooldown() {
        return attackCooldown;
    }

    public void setAttackCooldown(int attackCooldown) {
        this.attackCooldown = attackCooldown;
    }

    public int getDamageCooldown() {
        return damageCooldown;
    }

    public void updateX(float x) {
        this.x += x;
    }

    public boolean isAttacking() {
        if (attacking) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Set the attacking variable to true and play the sound effect, add a cooldown
     * 
     * @param attacking
     */
    public void setAttacking(boolean attacking) {
        if (attackCooldown <= 0) {
            this.attacking = attacking;
            attackCooldown = 40;
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            SoundPlayer.playSoundEffect(Resources.SWORD_SWOOSH, 0.3f);
                        }
                    },
                    100);

        }
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;

    }

    public void setFacingLeft(boolean facingLeft) {
        this.facingLeft = facingLeft;
    }

    public void incrementNumCollectibles() {
        collectibles++;
    }

    public int getNumCollectibles() {
        return collectibles;
    }

    public void incrementHp() {
        if (hp < maxHp) {
            hp++;
        }
    }

    public void decrementNumCollectibles() {
        if (collectibles > 0) {
            collectibles--;
        }
    }

    public Game getGame() {
        return game;
    }

    /**
     * Set variables depending on the FPS to make the game run at the same speed
     */
    public void updateFpsVariables() {
        if (game.getFPS() == 60) {
            animationSpeed = 8;
            playerSpeed = 5.0f;
            gravity = 0.17f;
            jumpSpeed = 4.0f;
        } else if (game.getFPS() == 120) {
            animationSpeed = 15;
            playerSpeed = 2.5f;
            gravity = 0.1f;
            jumpSpeed = 3.1f;
        }
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
