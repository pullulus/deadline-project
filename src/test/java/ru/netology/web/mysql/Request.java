package ru.netology.web.mysql;

import lombok.Value;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Request {
    public Request() {

    }

    @Value
    public static class CodeInfo {
        private String code;
    }

    public static CodeInfo getCode() throws SQLException {
        val getCode = "SELECT code FROM auth_codes";
        val runner = new QueryRunner();

        try (
                val conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/DB_URL", "DB_USER", "DB_PASS"
                );
        ) {
            val code = runner.query(conn, getCode, new ScalarHandler<>());

        }
        return new CodeInfo ("code");
            }

    public void shouldDelete() throws SQLException {
        val clearUsers = "DELETE FROM users";
        val clearCodes = "DELETE FROM auth_codes";
        val clearCards = "DELETE FROM cards";
        val clearCardTransactions = "DELETE FROM card_transactions";
        val runner = new QueryRunner();

        try (
                val conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/DB_URL", "DB_USER", "DB_PASS"
                );
        ) {
            runner.update(conn, clearCardTransactions);
            runner.update(conn, clearCards);
            runner.update(conn, clearCodes);
            runner.update(conn, clearUsers);
        }
    }
}
