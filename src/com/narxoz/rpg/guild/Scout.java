package com.narxoz.rpg.guild;

/** Responsible for route reports and reconnaissance. */
public class Scout extends GuildMember {

    public Scout(String name, GuildMediator mediator) { super(name, mediator); }

    @Override
    public void subscribeToTopics(GuildHall hall) {
        hall.addSubscriber("SCOUT_REPORT",  this);
        hall.addSubscriber("MISSION_ORDER", this);
    }

    public void reportRoute(String payload) {
        System.out.println("[" + getName() + "] Reporting route → " + payload);
        getMediator().dispatch("SCOUT_REPORT", this, payload);
    }

    @Override
    public void receive(String topic, GuildMember from, String payload) {
        switch (topic) {
            case "SCOUT_REPORT" ->
                    System.out.println("  [" + getName() + "] <- SCOUT_REPORT from " + "Council" + ": cross-checking map — " + payload);
            case "MISSION_ORDER" ->
                    System.out.println("  [" + getName() + "] <- MISSION_ORDER from " + "Council" + ": plotting route for — " + payload);
        }
    }
}
