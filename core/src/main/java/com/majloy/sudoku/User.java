package com.majloy.sudoku;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class User {
    private final int id;
    private final String username;
    private int level;
    private int gamesPlayed;
    private int gamesWon;
    private long totalScore;
    private final Set<String> unlockedAchievements;
    private String currentTheme;
    private boolean isPremiumUser;
    private int dailyStreak;
    private int hintsAvailable;
    private String theme;
    private LocalDate lastPlayDate;

    public User(int id, String username, int level, int gamesPlayed) {
        validateUsername(username);
        validateLevel(level);
        validateGamesPlayed(gamesPlayed);

        this.id = id;
        this.username = Objects.requireNonNull(username);
        this.level = Math.max(1, level);
        this.gamesPlayed = Math.max(0, gamesPlayed);
        this.gamesWon = 0;
        this.totalScore = 0;
        this.unlockedAchievements = new HashSet<>();
        this.currentTheme = "classic";
        this.isPremiumUser = false;
        this.dailyStreak = 0;
        this.hintsAvailable = 3;
    }

    public boolean checkDailyBonus() {
        LocalDate today = LocalDate.now();
        if (!today.equals(lastPlayDate)) {
            hintsAvailable += 2;
            lastPlayDate = today;
            return true;
        }
        return false;
    }

    private Set<Integer> friends = new HashSet<>();

    public void addFriend(int friendId) {
        friends.add(friendId);
    }

    public void removeFriend(int friendId) {
        friends.remove(friendId);
    }

    public Set<Integer> getFriends() {
        return Collections.unmodifiableSet(friends);
    }

    private void validateUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя пользователя не может быть пустым");
        }
    }

    private void validateLevel(int level) {
        if (level < 1) {
            throw new IllegalArgumentException("Уровень должен быть положительным числом");
        }
    }

    private void validateGamesPlayed(int gamesPlayed) {
        if (gamesPlayed < 0) {
            throw new IllegalArgumentException("Количество игр не может быть отрицательным");
        }
    }

    private void validateGamesWon(int gamesWon) {
        if (gamesWon < 0 || gamesWon > gamesPlayed) {
            throw new IllegalArgumentException("Некорректное количество выигранных игр");
        }
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public int getLevel() { return level; }
    public int getGamesPlayed() { return gamesPlayed; }
    public int getGamesWon() { return gamesWon; }
    public long getTotalScore() { return totalScore; }
    public Set<String> getUnlockedAchievements() { return Collections.unmodifiableSet(unlockedAchievements); }
    public String getCurrentTheme() { return currentTheme; }
    public boolean isPremiumUser() { return isPremiumUser; }
    public int getDailyStreak() { return dailyStreak; }

    public void setLevel(int level) {
        validateLevel(level);
        this.level = level;
    }

    public void setGamesPlayed(int gamesPlayed) {
        validateGamesPlayed(gamesPlayed);
        this.gamesPlayed = gamesPlayed;
    }

    public void setGamesWon(int gamesWon) {
        validateGamesWon(gamesWon);
        this.gamesWon = gamesWon;
    }

    public void setTotalScore(long totalScore) {
        if (totalScore < 0) {
            throw new IllegalArgumentException("Общий счет не может быть отрицательным");
        }
        this.totalScore = totalScore;
    }

    public void setCurrentTheme(String theme) {
        if (theme == null || theme.trim().isEmpty()) {
            throw new IllegalArgumentException("Тема не может быть пустой");
        }
        this.currentTheme = theme;
    }

    public void setPremiumStatus(boolean isPremium) {
        this.isPremiumUser = isPremium;
    }

    public void setDailyStreak(int streak) {
        if (streak < 0) {
            throw new IllegalArgumentException("Серия дней не может быть отрицательной");
        }
        this.dailyStreak = streak;
    }

    public void incrementGamesPlayed() {
        this.gamesPlayed++;
    }

    public void incrementGamesWon() {
        if (gamesWon >= gamesPlayed) {
            throw new IllegalStateException("Количество выигранных игр не может превышать общее количество игр");
        }
        this.gamesWon++;
    }

    public void addToTotalScore(long points) {
        if (points < 0) {
            throw new IllegalArgumentException("Добавляемые очки должны быть положительными");
        }
        this.totalScore += points;
    }

    public void levelUp() {
        this.level++;
    }

    public void unlockAchievement(String achievementId) {
        if (achievementId == null || achievementId.trim().isEmpty()) {
            throw new IllegalArgumentException("ID достижения не может быть пустым");
        }
        unlockedAchievements.add(achievementId);
    }

    public boolean hasAchievement(String achievementId) {
        return unlockedAchievements.contains(achievementId);
    }

    public void incrementDailyStreak() {
        this.dailyStreak++;
    }

    public void resetDailyStreak() {
        this.dailyStreak = 0;
    }

    @SuppressWarnings("DefaultLocale")
    @Override
    public String toString() {
        return String.format(
            "User[id=%d, username='%s', level=%d, gamesPlayed=%d, gamesWon=%d, score=%d, achievements=%d, theme='%s', premium=%b, streak=%d]",
            id, username, level, gamesPlayed, gamesWon, totalScore, unlockedAchievements.size(), currentTheme, isPremiumUser, dailyStreak
        );
    }
    private String colorScheme = "classic";

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public void setColorScheme(String colorScheme) {
        this.colorScheme = colorScheme;
    }

    public String getTheme() {
        return theme;
    }

    public String getColorScheme() {
        return colorScheme;
    }

    private Map<String, AchievementProgress> achievements = new HashMap<>();

    public void updateAchievement(String achievementId, int progress) {
        AchievementProgress ap = achievements.getOrDefault(achievementId,
            new AchievementProgress(achievementId, 0));
        ap.setProgress(progress);
        achievements.put(achievementId, ap);
    }

    public static class AchievementProgress {
        private String achievementId;
        private int progress;

        public AchievementProgress(String achievementId, int progress) {
            this.achievementId = achievementId;
            this.progress = progress;
        }

        public String getAchievementId() {
            return achievementId;
        }

        public void setAchievementId(String achievementId) {
            this.achievementId = achievementId;
        }

        public int getProgress() {
            return progress;
        }

        public void setProgress(int progress) {
            this.progress = progress;
        }
    }
}
