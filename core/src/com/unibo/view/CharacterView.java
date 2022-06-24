package com.unibo.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.unibo.util.Direction;
import com.unibo.audio.AudioManager;
import com.unibo.model.Character;

/**
 * Class for the view of a generic character.
 */
public abstract class CharacterView {

    private final Character character;

    private TextureRegion still;
    private Animation<TextureRegion> animationLeft;
    private Animation<TextureRegion> animationRight;
    private Animation<TextureRegion> animationUp;
    private Animation<TextureRegion> animationDown;
    private Animation<TextureRegion> animationAttack;
    private Direction dir = Direction.STILL;
    private final Rectangle charRect;
    private String attackSoundPath;
    private static final Float ATTACK_VOLUME = (float) 1.0;
    private AudioManager audioManager;
    private Boolean isAttacking = false;
    private boolean isMoving = false;

    /**
     * Constructor for this class.
     * 
     * @param character
     * @param texturePath     path of the character movement animation
     * @param attackSoundPath path of the attack sound
     */
    public CharacterView(final Character character, final String texturePath, final String attackSoundPath, final AudioManager manager) {
        this.character = character;
        this.attackSoundPath = attackSoundPath;
        this.createTextures(texturePath);
        this.audioManager = manager;
        this.charRect = new Rectangle(this.character.getPos().getxCoord(), this.character.getPos().getyCoord(),
                still.getRegionWidth() * 0.66f, still.getRegionHeight() / 6);
    }

    /**
     * Changes the character animation according to the file path.
     * 
     * @param fileName
     */
    protected void createTextures(final String fileName) {
        final Texture characterTextures = new Texture(fileName);
        final TextureRegion[][] tmp = TextureRegion.split(characterTextures, characterTextures.getWidth() / 3,
                characterTextures.getHeight() / 4);

        // Texture when still
        still = tmp[2][1];

        // Texture when moving right
        TextureRegion[] characterTextureRight = new TextureRegion[3];
        characterTextureRight[0] = tmp[1][0];
        characterTextureRight[1] = tmp[1][1];
        characterTextureRight[2] = tmp[1][2];
        animationRight = new Animation<>(1f / 12f, characterTextureRight);

        // Texture when moving left
        TextureRegion[] characterTextureLeft = new TextureRegion[3];
        characterTextureLeft[0] = tmp[3][2];
        characterTextureLeft[1] = tmp[3][1];
        characterTextureLeft[2] = tmp[3][0];
        animationLeft = new Animation<>(1f / 12f, characterTextureLeft);

        // Texture when moving up
        TextureRegion[] characterTextureUp = new TextureRegion[3];
        characterTextureUp[0] = tmp[0][0];
        characterTextureUp[1] = tmp[0][1];
        characterTextureUp[2] = tmp[0][2];
        animationUp = new Animation<>(1f / 8f, characterTextureUp);

        // Texture when moving down
        TextureRegion[] characterTextureDown = new TextureRegion[3];
        characterTextureDown[0] = tmp[2][0];
        characterTextureDown[1] = tmp[2][1];
        characterTextureDown[2] = tmp[2][2];
        animationDown = new Animation<>(1f / 8f, characterTextureDown);

        // Texture when attacking
        // TODO: CHANGE WHEN SPRITES ARE READY
        TextureRegion[] characterTextureAttack = new TextureRegion[3];
        characterTextureAttack[0] = tmp[1][0];
        characterTextureAttack[1] = tmp[1][1];
        characterTextureAttack[2] = tmp[1][2];
        animationAttack = new Animation<>(1f / 6f, characterTextureAttack);
    }

    /**
     * Moves the character depending on the pressed key.
     */
    public abstract void move();

    /**
     * @return the still character texture
     */
    public TextureRegion getStillTexture() {
        return still;
    }

    /**
     * 
     * @param time
     * @return the attack animation
     */
    public TextureRegion getAttackText(final float time) {
        return animationAttack.getKeyFrame(time, false);
    }

    /**
     * 
     * @return the attack animation
     */
    public Animation<TextureRegion> getAttackAnim() {
        return animationAttack;
    }

    /**
     * Method to check whether the character is currently attacking.
     * 
     * @return true if the character is attacking
     */
    public Boolean getIsAttacking() {
        return isAttacking;
    }

    /**
     * Method to set the attacking status.
     * 
     * @param isAttacking
     */
    public void setIsAttacking(final Boolean isAttacking) {
        this.isAttacking = isAttacking;
        if (isAttacking.booleanValue()) {
        	soundNotifyAudiomanager(attackSoundPath, ATTACK_VOLUME);
        }
    }

    /**
     * @return the character model class
     */
    public Character getCharacter() {
        return character;
    }

    /**
     * @return the character direction
     */
    public Direction getDir() {
        return dir;
    }

    /**
     * Sets the character direction to the specified one.
     * 
     * @param dir the direction
     */
    public void setDir(final Direction dir) {
        this.dir = dir;
    }

    /**
     * Returns the animation corresponding to a direction.
     * 
     * @param dir  the direction
     * @param time for the animation duration
     * @return the animation
     */
    public TextureRegion getAnimFromDir(final Direction dir, final float time) {
        switch (dir) {
        case LEFT:
            return animationLeft.getKeyFrame(time, true);
        case RIGHT:
            return animationRight.getKeyFrame(time, true);
        case UP:
            return animationUp.getKeyFrame(time, true);
        case DOWN:
            return animationDown.getKeyFrame(time, true);
        default:
            return new TextureRegion(still);
        }
    }

    /**
     * @return the character rectangle for collisions.
     */
    public Rectangle getCharRect() {
        return charRect;
    }

    /**
     * @return the sprite height
     */
    public float getHeight() {
        return still.getRegionHeight();
    }

    /**
     * @return the sprite width
     */
    public float getWidth() {
        return still.getRegionWidth();
    }

    /**
     * @return true if the hero is moving
     */
    public boolean getIsMoving() {
        return isMoving;
    }

    /**
     * Sets whether the hero is moving or not.
     * @param isMoving
     */
    public void setIsMoving(final boolean isMoving) {
        this.isMoving = isMoving;
    }
    
    public void setAudioManager(AudioManager manager) {
    	this.audioManager = manager;
    }
    
    public void soundNotifyAudiomanager(String s, float volume) {
    	audioManager.playSoundEffect(s, volume);
    }
    
    public void musicNotifyAudiomanager(String s, Boolean looping, float volume) {
    	audioManager.playMusic(s, looping, volume);
    }
}
