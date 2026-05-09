package com.narxoz.rpg.guild;

/** Responsible for gear, supplies, and rewards. */
public class Quartermaster extends GuildMember {

    public Quartermaster(String name, GuildMediator mediator) { super(name, mediator); }

    @Override
    public void subscribeToTopics(GuildHall hall) {
        hall.addSubscriber("SUPPLY_REQUEST", this);
        hall.addSubscriber("MISSION_ORDER",  this);
        hall.addSubscriber("LORE",           this);
    }

    public void requestSupplies(String payload) {
        System.out.println("[" + getName() + "] Requesting supplies → " + payload);
        getMediator().dispatch("SUPPLY_REQUEST", this, payload);
    }

    @Override
    public void receive(String topic, GuildMember from, String payload) {
        switch (topic) {
            case "SUPPLY_REQUEST" ->
                    System.out.println("  [" + getName() + "] <- SUPPLY_REQUEST from " + "Council" + ": allocating — " + payload);
            case "MISSION_ORDER" ->
                    System.out.println("  [" + getName() + "] <- MISSION_ORDER from " + "Council" + ": preparing gear for — " + payload);
            case "LORE" ->
                    System.out.println("  [" + getName() + "] <- LORE from " + "Council" + ": noted in ledger — " + payload);
        }
    }
}
