package com.majloy.sudoku;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class GamePreferences {
    private static final String PREFS_NAME = "sudoku_game_data";
    private final Preferences prefs;

    private static GamePreferences instance;

    private GamePreferences() {
        prefs = Gdx.app.getPreferences(PREFS_NAME);
    }

    public static synchronized GamePreferences getInstance() {
        if (instance == null) {
            instance = new GamePreferences();
        }
        return instance;
    }

    // User related methods
    public void saveUserData(User user) {
        prefs.putInteger("user_" + user.getId() + "_level", user.getLevel());
        prefs.putInteger("user_" + user.getId() + "_games_played", user.getGamesPlayed());
        prefs.putInteger("user_" + user.getId() + "_games_won", user.getGamesWon());
        prefs.putLong("user_" + user.getId() + "_total_score", user.getTotalScore());
        prefs.putString("user_" + user.getId() + "_theme", user.getTheme());
        prefs.putBoolean("user_" + user.getId() + "_premium", user.isPremiumUser());
        prefs.putInteger("user_" + user.getId() + "_daily_streak", user.getDailyStreak());

        // Save achievements
        Set<String> achievements = user.getUnlockedAchievements();
        prefs.putInteger("user_" + user.getId() + "_achievements_count", achievements.size());
        int i = 0;
        for (String achievement : achievements) {
            prefs.putString("user_" + user.getId() + "_achievement_" + i++, achievement);
        }

        prefs.flush();
    }

    public User loadUserData(int userId, String username) {
        User user = new User(userId, username,
            prefs.getInteger("user_" + userId + "_level", 1),
            prefs.getInteger("user_" + userId + "_games_played", 0));

        user.setGamesWon(prefs.getInteger("user_" + userId + "_games_won", 0));
        user.setTotalScore(prefs.getLong("user_" + userId + "_total_score", 0));
        user.setTheme(prefs.getString("user_" + userId + "_theme", "classic"));
        user.setPremiumStatus(prefs.getBoolean("user_" + userId + "_premium", false));
        user.setDailyStreak(prefs.getInteger("user_" + userId + "_daily_streak", 0));

        // Load achievements
        int achievementsCount = prefs.getInteger("user_" + userId + "_achievements_count", 0);
        Set<String> achievements = new HashSet<>();
        for (int i = 0; i < achievementsCount; i++) {
            String achievement = prefs.getString("user_" + userId + "_achievement_" + i);
            if (achievement != null) {
                achievements.add(achievement);
            }
        }
        user.getUnlockedAchievements().addAll(achievements);

        return user;
    }

    // Game state methods
    public void saveGameState(SavedGameState gameState, User user) {
        prefs.putBoolean("has_save", true);
        prefs.putInteger("save_grid_size", gameState.gridSize);
        prefs.putInteger("save_cells_to_remove", gameState.cellsToRemove);

        // Save grid
        for (int row = 0; row < gameState.gridSize; row++) {
            for (int col = 0; col < gameState.gridSize; col++) {
                prefs.putInteger("save_grid_" + row + "_" + col, gameState.grid[row][col]);
            }
        }

        // Save metadata
        prefs.putLong("save_timestamp", new Date().getTime());
        prefs.putInteger("save_user_id", user.getId());

        prefs.flush();
    }

    public SavedGameState loadGameState() {
        if (!prefs.getBoolean("has_save", false)) {
            return null;
        }

        int gridSize = prefs.getInteger("save_grid_size");
        int cellsToRemove = prefs.getInteger("save_cells_to_remove");
        int[][] grid = new int[gridSize][gridSize];

        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                grid[row][col] = prefs.getInteger("save_grid_" + row + "_" + col);
            }
        }

        return new SavedGameState(gridSize, cellsToRemove, grid);
    }

    public void clearSavedGame() {
        if (prefs.getBoolean("has_save", false)) {
            int gridSize = prefs.getInteger("save_grid_size");

            // Remove grid data
            for (int row = 0; row < gridSize; row++) {
                for (int col = 0; col < gridSize; col++) {
                    prefs.remove("save_grid_" + row + "_" + col);
                }
            }

            // Remove other data
            prefs.remove("has_save");
            prefs.remove("save_grid_size");
            prefs.remove("save_cells_to_remove");
            prefs.remove("save_timestamp");
            prefs.remove("save_user_id");

            prefs.flush();
        }
    }

    // Settings methods
    public void saveSettings(boolean soundEnabled, String theme, String colorScheme) {
        prefs.putBoolean("sound_enabled", soundEnabled);
        prefs.putString("theme", theme);
        prefs.putString("color_scheme", colorScheme);
        prefs.flush();
    }

    public boolean isSoundEnabled() {
        return prefs.getBoolean("sound_enabled", true);
    }

    public String getTheme() {
        return prefs.getString("theme", "classic");
    }

    public String getColorScheme() {
        return prefs.getString("color_scheme", "default");
    }

    // Challenge methods
    public void saveChallenge(Challenge challenge) {
        int nextId = prefs.getInteger("challenges_next_id", 1);

        prefs.putInteger("challenge_" + nextId + "_from", challenge.getFromUserId());
        prefs.putInteger("challenge_" + nextId + "_to", challenge.getToUserId());
        prefs.putInteger("challenge_" + nextId + "_size", challenge.getGridSize());
        prefs.putInteger("challenge_" + nextId + "_diff", challenge.getDifficulty());
        prefs.putLong("challenge_" + nextId + "_created", challenge.getCreated().getTime());

        prefs.putInteger("challenges_next_id", nextId + 1);
        prefs.flush();
    }

    // Other utility methods
    public void clearAllData() {
        prefs.clear();
        prefs.flush();
    }

    public static class Challenge {
        private int fromUserId;
        private int toUserId;
        private int gridSize;
        private int difficulty;
        private Date created;

        // Getters and setters
        public int getFromUserId() { return fromUserId; }
        public void setFromUserId(int fromUserId) { this.fromUserId = fromUserId; }
        public int getToUserId() { return toUserId; }
        public void setToUserId(int toUserId) { this.toUserId = toUserId; }
        public int getGridSize() { return gridSize; }
        public void setGridSize(int gridSize) { this.gridSize = gridSize; }
        public int getDifficulty() { return difficulty; }
        public void setDifficulty(int difficulty) { this.difficulty = difficulty; }
        public Date getCreated() { return created; }
        public void setCreated(Date created) { this.created = created; }
    }
}
