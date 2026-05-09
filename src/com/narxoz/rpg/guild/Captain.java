package com.narxoz.rpg.guild;

/** Responsible for orders and mission coordination. */
public class Captain extends GuildMember {

    public Captain(String name, GuildMediator mediator) { super(name, mediator); }

    @Override
    public void subscribeToTopics(GuildHall hall) {
        hall.addSubscriber("SCOUT_REPORT",   this);
        hall.addSubscriber("HEAL_REQUEST",   this);
        hall.addSubscriber("MISSION_ORDER",  this);
        hall.addSubscriber("SUPPLY_REQUEST", this);
    }

    public void issueOrder(String payload) {
        System.out.println("[" + getName() + "] Issuing order → " + payload);
        getMediator().dispatch("MISSION_ORDER", this, payload);
    }

    @Override
    public void receive(String topic, GuildMember from, String payload) {
        switch (topic) {
            case "SCOUT_REPORT" ->
                    System.out.println("  [" + getName() + "] <- SCOUT_REPORT from " + "Council" + ": updating battle plan — " + payload);
            case "HEAL_REQUEST" ->
                    System.out.println("  [" + getName() + "] <- HEAL_REQUEST from " + "Council" + ": approving medical resources — " + payload);
            case "SUPPLY_REQUEST" ->
                    System.out.println("  [" + getName() + "] <- SUPPLY_REQUEST from " + "Council" + ": authorizing — " + payload);
            case "MISSION_ORDER" ->
                    System.out.println("  [" + getName() + "] <- MISSION_ORDER from " + "Council" + ": acknowledged — " + payload);
        }
    }
}
