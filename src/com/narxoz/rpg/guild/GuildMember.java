package com.narxoz.rpg.guild;

/**
 * Base class for all guild officers that communicate through a mediator.
 */
public abstract class GuildMember {

    private final String name;
    private final GuildMediator mediator;

    protected GuildMember(String name, GuildMediator mediator) {
        this.name = name;
        this.mediator = mediator;
        mediator.register(this);
    }

    public String getName()                 { return name; }
    protected GuildMediator getMediator()   { return mediator; }

    /** Called by GuildHall.register() — member declares which topics it listens to. */
    public abstract void subscribeToTopics(GuildHall hall);

    public abstract void receive(String topic, GuildMember from, String payload);
}
