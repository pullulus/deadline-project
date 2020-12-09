package ru.netology.web.data;

import com.github.javafaker.Faker;
import lombok.Value;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.junit.jupiter.api.BeforeEach;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DataHelper {
    private DataHelper() {
    }

    @BeforeEach
    void setUp() throws SQLException {
        val faker = new Faker();
        val runner = new QueryRunner();
        val dataSQL = "INSERT INTO users(id, login, password, status) VALUES (?, ?, ?, ?);";

        try (
                val conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/DB_URL", "DB_USER", "DB_PASS"
                );
        ) {
            runner.update(conn, dataSQL, 1, faker.name().username(), "pass", "active");
            runner.update(conn, dataSQL, 2, faker.name().username(), "pass", "blocked");
        }
    }

    @Value
    public static class User {
        private int id;
        private String login;
        private String password;
        private String status;
    }

    public static User shouldFindFirstUser() throws SQLException {
        val userSQLFirst = "SELECT * FROM users WHERE id=1;";
        val runner = new QueryRunner();

        try (
                val conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/DB_URL", "DB_USER", "DB_PASS"
                );
        ) {
            val userFirst = runner.query(conn, userSQLFirst, new BeanHandler<>(User.class));
        }
        return new User;
    }
}
