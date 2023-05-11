package entities;

/**
 * The Entity class is the parent class of entities in the game.
 */
public abstract class Entity {
    protected float x, y;
    protected float velocityY = 0;
    protected float gravity = 0.03f;

    protected boolean isGrounded = false;
    protected boolean falling = false;
    protected Hitbox hitbox;
  
    public Entity(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
