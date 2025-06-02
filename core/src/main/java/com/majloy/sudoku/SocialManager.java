package com.majloy.sudoku;

import java.util.*;

public class SocialManager {
    private DatabaseHelper dbHelper;

    public SocialManager(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public void sendChallenge(int fromUserId, int toUserId, int gridSize, int difficulty) {
        //Сохраняем вызов в базе данных
    }

    public List<Challenge> getChallengesForUser(int userId) {
        //Получаем список вызовов для пользователя
        return new ArrayList<>();
    }

    public void shareResult(int userId, String socialNetwork, int score) {
        //Логика публикации результата в соцсети
    }

    public static class Challenge {
        private int id;
        private int fromUserId;
        private int gridSize;
        private int difficulty;
        private Date created;

        public void setId(int id) {
            this.id = id;
        }

        public void setFromUserId(int fromUserId) {
            this.fromUserId = fromUserId;
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
