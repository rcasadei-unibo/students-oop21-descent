package com.unibo.view;

import com.unibo.key_bindings.InputHandler;
import com.unibo.key_bindings.KeyBindings;
import com.unibo.model.Hero;
import com.unibo.util.Direction;

/**
 * Class for the view of the hero.
 */
public class HeroView extends CharacterView {

    private final InputHandler input;

    /**
     * Constructor for the view.
     * 
     * @param hero the hero model
     * @param path of the hero movement animation
     * @param input handler for keyboard inputs
     */
    public HeroView(final Hero hero, final String path, final InputHandler input) {
        super(hero, path, "audio/sounds/Hadouken.mp3");
        this.input = input;
    }

    /**
     * Moves the hero depending on the pressed key.
     */
    public void move() {
        setDir(Direction.STILL);

        this.input.handleInput(KeyBindings.MOVE_LEFT).ifPresent(t -> t.executeCommand(this));

        this.input.handleInput(KeyBindings.MOVE_RIGHT).ifPresent(t -> t.executeCommand(this));

        this.input.handleInput(KeyBindings.MOVE_UP).ifPresent(t -> t.executeCommand(this));

        this.input.handleInput(KeyBindings.MOVE_DOWN).ifPresent(t -> t.executeCommand(this));
    }
}
