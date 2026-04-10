package com.narxoz.rpg.council;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.guild.GuildMediator;
import com.narxoz.rpg.quest.*;

import java.util.List;

/**
 * Orchestrates a planning session using Iterator + Mediator.
 */
public class CouncilEngine {

    public CouncilRunResult runCouncil(List<Hero> party, QuestLog questLog, GuildMediator hall) {
        int questsTraversed = 0;
        int messagesRouted  = 0;
        int membersNotified = 0;

        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.println("║      WAR COUNCIL PLANNING SESSION        ║");
        System.out.println("╚══════════════════════════════════════════╝");
        System.out.println("Party: " + party.stream().map(Hero::getName).toList());

        // ── Iterator 1: Ordered traversal ────────────────────────────────
        System.out.println("\n=== [ITERATOR 1] Ordered Quest Review ===");
        QuestIterator ordered = questLog.ordered();
        while (ordered.hasNext()) {
            Quest q = ordered.next();
            questsTraversed++;
            System.out.printf("  %-35s [%-6s] %4dg%n",
                    q.getTitle(), q.getPriority(), q.getRewardGold());
        }

        // ── Iterator 2: Priority filtered (HIGH+) ─────────────────────────
        System.out.println("\n=== [ITERATOR 2] High-Priority Quest Planning (via Mediator) ===");
        QuestIterator highPrio = questLog.priorityAtLeast(QuestPriority.HIGH);
        while (highPrio.hasNext()) {
            Quest q = highPrio.next();
            System.out.println("\n  Planning: " + q.getTitle() + " [" + q.getPriority() + "]");
            hall.dispatch("MISSION_ORDER", null, "Deploy for: " + q.getTitle());
            messagesRouted++;
            membersNotified += 3;

            if (q.isUrgent()) {
                hall.dispatch("HEAL_REQUEST", null, "Emergency aid: " + q.getTitle());
                messagesRouted++;
                membersNotified += 1;
            }
        }

        return new CouncilRunResult(questsTraversed, messagesRouted, membersNotified);
    }
}
