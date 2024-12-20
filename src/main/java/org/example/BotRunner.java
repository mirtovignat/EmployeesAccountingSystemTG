package org.example;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.sql.SQLException;

public class BotRunner {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        try {

            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new Bot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        PostgresJavaConnection postgresJavaConnectionRunner = new PostgresJavaConnection();
        postgresJavaConnectionRunner.start();

    }
}
