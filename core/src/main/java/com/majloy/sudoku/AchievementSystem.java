package com.majloy.sudoku;

import java.util.Map;

public class AchievementSystem {
    public static final Map<String, Achievement> ALL_ACHIEVEMENTS = Map.of(
        "first_win", new Achievement("First Win", "Win your first game", 10),
        "daily_streak_5", new Achievement("5-day Streak", "Play 5 days in a row", 20)
    );

    public void checkAchievements(User user) {
    }

    public static class Achievement {
        public final String name;
        public final String description;
        public final int rewardPoints;

        public Achievement(String name, String description, int rewardPoints) {
            this.name = name;
            this.description = description;
            this.rewardPoints = rewardPoints;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public int getRewardPoints() {
            return rewardPoints;
        }
    }
}
