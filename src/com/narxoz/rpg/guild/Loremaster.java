package com.narxoz.rpg.guild;

/**
 * Open/closed proof — 5th colleague.
 * Subscribes to LORE and SCOUT_REPORT topics.
 * Added WITHOUT modifying any existing colleague class.
 */
public class Loremaster extends GuildMember {

    public Loremaster(String name, GuildMediator mediator) { super(name, mediator); }

    @Override
    public void subscribeToTopics(GuildHall hall) {
        hall.addSubscriber("LORE",        this);
        hall.addSubscriber("SCOUT_REPORT",this);
    }

    public void shareLore(String payload) {
        System.out.println("[" + getName() + "] Sharing lore → " + payload);
        getMediator().dispatch("LORE", this, payload);
    }

    @Override
    public void receive(String topic, GuildMember from, String payload) {
        switch (topic) {
            case "LORE" ->
                    System.out.println("  [" + getName() + "] <- LORE from " + "Council" + ": recording in archives — " + payload);
            case "SCOUT_REPORT" ->
                    System.out.println("  [" + getName() + "] <- SCOUT_REPORT from " + "Council" + ": cross-referencing ancient maps — " + payload);
        }
    }
}
