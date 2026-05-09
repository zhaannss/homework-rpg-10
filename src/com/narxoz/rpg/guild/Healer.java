package com.narxoz.rpg.guild;

/** Responsible for wounds, potions, and recovery plans. */
public class Healer extends GuildMember {

    public Healer(String name, GuildMediator mediator) { super(name, mediator); }

    @Override
    public void subscribeToTopics(GuildHall hall) {
        hall.addSubscriber("HEAL_REQUEST",  this);
        hall.addSubscriber("MISSION_ORDER", this);
        hall.addSubscriber("SUPPLY_REQUEST",this);
    }

    public void prepareAid(String payload) {
        System.out.println("[" + getName() + "] Preparing aid → " + payload);
        getMediator().dispatch("HEAL_REQUEST", this, payload);
    }

    @Override
    public void receive(String topic, GuildMember from, String payload) {
        switch (topic) {
            case "HEAL_REQUEST" ->
                    System.out.println("  [" + getName() + "] <- HEAL_REQUEST from " + "Council" + ": preparing potions — " + payload);
            case "MISSION_ORDER" ->
                    System.out.println("  [" + getName() + "] <- MISSION_ORDER from " + "Council" + ": packing field kit for — " + payload);
            case "SUPPLY_REQUEST" ->
                    System.out.println("  [" + getName() + "] <- SUPPLY_REQUEST from " + "Council" + ": checking medical stock — " + payload);
        }
    }
}
