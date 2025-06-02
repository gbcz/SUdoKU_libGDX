package com.majloy.sudoku;

import java.util.*;

public class SocialManager {
    private DatabaseHelper dbHelper;

    public SocialManager(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public void sendChallenge(int fromUserId, int toUserId, int gridSize, int difficulty) {
        Challenge challenge = new Challenge();
        challenge.setFromUserId(fromUserId);
        challenge.setGridSize(gridSize);
        challenge.setDifficulty(difficulty);
        challenge.setCreated(new Date());
        // Сохраняем вызов в базе данных
        dbHelper.saveChallenge(challenge);
    }

    public List<Challenge> getChallengesForUser(int userId) {
        return dbHelper.getChallengesForUser(userId);
    }

    public void shareResult(int userId, String socialNetwork, int score) {
        // Логика публикации результата в соцсети
    }

    public static class Challenge {
        private int id;
        private int fromUserId;
        private int toUserId;
        private int gridSize;
        private int difficulty;
        private Date created;

        public void setId(int id) {
            this.id = id;
        }

        public void setFromUserId(int fromUserId) {
            this.fromUserId = fromUserId;
        }

        public void setToUserId(int toUserId) {
            this.toUserId = toUserId;
        }

        public void setGridSize(int gridSize) {
            this.gridSize = gridSize;
        }

        public void setDifficulty(int difficulty) {
            this.difficulty = difficulty;
        }

        public void setCreated(Date created) {
            this.created = created;
        }

        public int getId() {
            return id;
        }

        public int getFromUserId() {
            return fromUserId;
        }

        public int getToUserId() {
            return toUserId;
        }

        public int getGridSize() {
            return gridSize;
        }

        public int getDifficulty() {
            return difficulty;
        }

        public Date getCreated() {
            return created;
        }
    }
}
