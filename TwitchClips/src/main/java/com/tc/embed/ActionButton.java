package com.tc.embed;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.util.function.Consumer;

public class ActionButton extends EmbedButton {
    private final String id;
    private final String label;
    private final Consumer<ButtonInteractionEvent> action;

    public ActionButton(String id, String label, Consumer<ButtonInteractionEvent> action) {
        super(id, label);
        this.id = id;
        this.label = label;
        this.action = action;
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public Button createButton() {
        return Button.primary(id, label); // Utilisation du type de bouton primaire par défaut
    }

    public void execute(ButtonInteractionEvent event) {
        if (action != null) {
            action.accept(event);
        }
    }

}