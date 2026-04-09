package com.narxoz.rpg.quest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Open/closed proof — 4th iterator.
 * Traverses quests sorted by rewardGold descending (highest reward first).
 * Added WITHOUT modifying any existing class.
 */
public class RewardSortedQuestIterator implements QuestIterator {
    private final List<Quest> snapshot;
    private int cursor;

    public RewardSortedQuestIterator(QuestLog questLog) {
        this.snapshot = new ArrayList<>(questLog.snapshot());
        this.snapshot.sort(Comparator.comparingInt(Quest::getRewardGold).reversed());
        this.cursor = 0;
    }

    @Override public boolean hasNext() { return cursor < snapshot.size(); }
    @Override public Quest next()      { return snapshot.get(cursor++); }
}
