package com.narxoz.rpg;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.council.CouncilEngine;
import com.narxoz.rpg.council.CouncilRunResult;
import com.narxoz.rpg.guild.*;
import com.narxoz.rpg.quest.*;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== Homework 10 Demo: Iterator + Mediator ===\n");

        // ── 1. Heroes ─────────────────────────────────────────────────────
        Hero aragorn = new Hero("Aragorn", 200, 80, 40, 15, 300);
        Hero legolas  = new Hero("Legolas", 150, 60, 35,  8, 200);
        List<Hero> party = List.of(aragorn, legolas);

        System.out.println("=== PARTY ===");
        party.forEach(System.out::println);

        // ── 2. QuestLog (≥ 5 quests, mixed priority) ──────────────────────
        QuestLog questLog = new QuestLog();
        questLog.add(new Quest("Escort the Merchant",       QuestPriority.LOW,    150, false));
        questLog.add(new Quest("Clear Goblin Cave",         QuestPriority.NORMAL, 300, false));
        questLog.add(new Quest("Retrieve the Lost Relic",   QuestPriority.HIGH,   500, false));
        questLog.add(new Quest("Defend the Northern Gate",  QuestPriority.URGENT, 800, true));
        questLog.add(new Quest("Slay the Forest Troll",     QuestPriority.NORMAL, 250, false));
        questLog.add(new Quest("Hunt the Shadow Dragon",    QuestPriority.URGENT, 1000,true));

        // ── 3. GuildHall + 5 colleagues (4 required + Loremaster) ─────────
        GuildHall hall = new GuildHall();
        Quartermaster quartermaster = new Quartermaster("Gimli",    hall);
        Scout         scout         = new Scout        ("Legolas",  hall);
        Healer        healer        = new Healer       ("Elrond",   hall);
        Captain       captain       = new Captain      ("Aragorn",  hall);
        Loremaster    loremaster    = new Loremaster   ("Gandalf",  hall);

        System.out.println("\n=== GUILD HALL REGISTERED ===");
        System.out.println("Members: Quartermaster, Scout, Healer, Captain, Loremaster");

        // ── 4. Iterator demo: 4 different traversal styles ────────────────
        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.println("║       ITERATOR PATTERN DEMO              ║");
        System.out.println("╚══════════════════════════════════════════╝");

        System.out.println("\n--- [Iterator 1] Ordered (arrival order) ---");
        QuestIterator ordered = questLog.ordered();
        while (ordered.hasNext()) {
            Quest q = ordered.next();
            System.out.printf("  %-35s [%-6s] %4dg%n",
                    q.getTitle(), q.getPriority(), q.getRewardGold());
        }

        System.out.println("\n--- [Iterator 2] Reverse (newest first) ---");
        QuestIterator reverse = questLog.reverse();
        while (reverse.hasNext()) {
            Quest q = reverse.next();
            System.out.printf("  %-35s [%-6s] %4dg%n",
                    q.getTitle(), q.getPriority(), q.getRewardGold());
        }

        System.out.println("\n--- [Iterator 3] Priority >= HIGH ---");
        QuestIterator highPrio = questLog.priorityAtLeast(QuestPriority.HIGH);
        while (highPrio.hasNext()) {
            Quest q = highPrio.next();
            System.out.printf("  %-35s [%-6s] %4dg  %s%n",
                    q.getTitle(), q.getPriority(), q.getRewardGold(),
                    q.isUrgent() ? "URGENT!" : "");
        }

        System.out.println("\n--- [Iterator 4] Reward-Sorted DESC (open/closed proof) ---");
        QuestIterator rewardSorted = new RewardSortedQuestIterator(questLog);
        while (rewardSorted.hasNext()) {
            Quest q = rewardSorted.next();
            System.out.printf("  %-35s [%-6s] %4dg%n",
                    q.getTitle(), q.getPriority(), q.getRewardGold());
        }

        // ── 5. Mediator demo: coordinated messages by topic ───────────────
        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.println("║       MEDIATOR PATTERN DEMO              ║");
        System.out.println("╚══════════════════════════════════════════╝");

        System.out.println("\n--- Captain issues MISSION_ORDER ---");
        captain.issueOrder("All units prepare for Defend the Northern Gate");

        System.out.println("\n--- Scout reports route ---");
        scout.reportRoute("Northern pass is clear, enemy spotted 2 leagues east");

        System.out.println("\n--- Healer prepares aid ---");
        healer.prepareAid("Field medics ready, 20 healing potions allocated");

        System.out.println("\n--- Quartermaster requests supplies ---");
        quartermaster.requestSupplies("Need 50 rations and 10 torches for cave mission");

        System.out.println("\n--- Loremaster shares lore (open/closed proof) ---");
        loremaster.shareLore("Shadow Dragon's lair has an ancient ward — bring dispel scrolls");

        // ── 6. CouncilEngine ─────────────────────────────────────────────
        CouncilEngine engine = new CouncilEngine();
        CouncilRunResult result = engine.runCouncil(party, questLog, hall);

        // ── 7. Final CouncilRunResult ────────────────────────────────────
        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.println("║         COUNCIL RUN RESULT               ║");
        System.out.println("╚══════════════════════════════════════════╝");
        System.out.println("  Quests traversed:   " + result.getQuestsTraversed());
        System.out.println("  Messages routed:    " + result.getMessagesRouted());
        System.out.println("  Members notified:   " + result.getMembersNotified());
        System.out.println("  " + result);
    }
}
