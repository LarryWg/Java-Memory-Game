package memorygame;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository for persisting high scores to SQLite.
 * Database file is stored in user's home directory as .memory-game/scores.db
 */
public class HighScoreRepository {
    private static final String DB_NAME = "scores.db";
    private static final String TABLE_NAME = "high_scores";

    private final String dbPath;

    public HighScoreRepository() {
        Path homeDir = Paths.get(System.getProperty("user.home"));
        Path appDir = homeDir.resolve(".memory-game");
        appDir.toFile().mkdirs();
        this.dbPath = appDir.resolve(DB_NAME).toAbsolutePath().toString();
        initDatabase();
    }

    public HighScoreRepository(String dbPath) {
        this.dbPath = dbPath;
        initDatabase();
    }

    private void initDatabase() {
        String sql = """
            CREATE TABLE IF NOT EXISTS %s (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                game_mode TEXT NOT NULL,
                player_name TEXT DEFAULT 'Anonymous',
                score INTEGER NOT NULL,
                level INTEGER NOT NULL,
                created_at DATETIME DEFAULT CURRENT_TIMESTAMP
            )
            """.formatted(TABLE_NAME);

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            // Migration: add player_name to existing tables
            try {
                stmt.execute("ALTER TABLE " + TABLE_NAME + " ADD COLUMN player_name TEXT DEFAULT 'Anonymous'");
            } catch (SQLException ignored) {
                // Column already exists
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database", e);
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:" + dbPath);
    }

    /**
     * Saves a score only if it is the player's new high for this game mode.
     * Keeps at most one record per player per mode (their highest score).
     */
    public void saveScore(String gameMode, String playerName, int score) {
        String name = (playerName == null || playerName.isBlank()) ? "Anonymous" : playerName.trim();
        int currentBest = getBestScoreForPlayer(gameMode, name);
        if (score <= currentBest) {
            return;
        }
        try (Connection conn = getConnection()) {
            if (currentBest >= 0) {
                String deleteSql = "DELETE FROM " + TABLE_NAME + " WHERE game_mode = ? AND player_name = ?";
                try (PreparedStatement del = conn.prepareStatement(deleteSql)) {
                    del.setString(1, gameMode);
                    del.setString(2, name);
                    del.executeUpdate();
                }
            }
            String insertSql = "INSERT INTO " + TABLE_NAME + " (game_mode, player_name, score, level) VALUES (?, ?, ?, ?)";
            try (PreparedStatement ins = conn.prepareStatement(insertSql)) {
                ins.setString(1, gameMode);
                ins.setString(2, name);
                ins.setInt(3, score);
                ins.setInt(4, score);
                ins.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save score", e);
        }
    }

    private int getBestScoreForPlayer(String gameMode, String playerName) {
        String sql = "SELECT MAX(score) AS best FROM " + TABLE_NAME + " WHERE game_mode = ? AND player_name = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, gameMode);
            pstmt.setString(2, playerName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next() && rs.getObject("best") != null) {
                    return rs.getInt("best");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get best score for player", e);
        }
        return -1;
    }

    /**
     * Returns the top scores per player: each player appears at most once,
     * with their highest score for the given game mode.
     */
    public List<HighScore> getTopScores(String gameMode, int limit) {
        String sql = """
            SELECT game_mode, player_name, MAX(score) AS score, MAX(level) AS level, MAX(created_at) AS created_at
            FROM %s
            WHERE game_mode = ?
            GROUP BY game_mode, player_name
            ORDER BY score DESC
            LIMIT ?
            """.formatted(TABLE_NAME);

        List<HighScore> scores = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, gameMode);
            pstmt.setInt(2, limit);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String playerName = rs.getString("player_name");
                    if (playerName == null || playerName.isBlank()) playerName = "Anonymous";
                    scores.add(new HighScore(
                            rs.getString("game_mode"),
                            playerName,
                            rs.getInt("score"),
                            rs.getInt("level"),
                            rs.getString("created_at")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load scores", e);
        }
        return scores;
    }

    public int getBestScore(String gameMode) {
        String sql = "SELECT MAX(score) as best FROM %s WHERE game_mode = ?".formatted(TABLE_NAME);
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, gameMode);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next() && rs.getObject("best") != null) {
                    return rs.getInt("best");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get best score", e);
        }
        return -1;
    }

    public record HighScore(String gameMode, String playerName, int score, int level, String createdAt) {}
}
