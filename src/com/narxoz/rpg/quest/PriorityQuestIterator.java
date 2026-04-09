package com.narxoz.rpg.quest;

import java.util.ArrayList;
import java.util.List;

/** Traverses only quests whose priority meets or exceeds the threshold. */
public class PriorityQuestIterator implements QuestIterator {
    private final List<Quest> snapshot;
    private int cursor;

    public PriorityQuestIterator(QuestLog questLog, QuestPriority threshold) {
        QuestPriority min = threshold == null ? QuestPriority.LOW : threshold;
        this.snapshot = new ArrayList<>();
        for (Quest q : questLog.snapshot()) {
            if (q.getPriority().ordinal() >= min.ordinal()) snapshot.add(q);
        }
        this.cursor = 0;
    }

    @Override public boolean hasNext() { return cursor < snapshot.size(); }
    @Override public Quest next()      { return snapshot.get(cursor++); }
}
