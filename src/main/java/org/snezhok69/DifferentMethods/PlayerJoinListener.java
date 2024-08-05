package org.snezhok69.DifferentMethods;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.snezhok69.Main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerJoinListener implements Listener {

    private final Main plugin;
    private static final long PROTECTION_TIME = 600L; // Время защиты в тиках (10 минут)

    public PlayerJoinListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Connection connection = Main.getConnection();

        try {
            // Проверка, заходил ли игрок ранее
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM player_protection WHERE uuid = ?");
            statement.setString(1, player.getUniqueId().toString());
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                // Новый игрок, добавляем запись и предоставляем защиту
                PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO player_protection (uuid, protection_end) VALUES (?, ?)");
                long protectionEndTime = System.currentTimeMillis() + (PROTECTION_TIME * 50); // Преобразование тиков в миллисекунды
                insertStatement.setString(1, player.getUniqueId().toString());
                insertStatement.setLong(2, protectionEndTime);
                insertStatement.executeUpdate();

                // Отправка приветственного сообщения
                player.sendMessage("Добро пожаловать на сервер! Вам предоставлена защита на 10 минут.");

                // Предоставляем защиту
                new ProtectionTask(player).runTaskTimer(plugin, 0L, 20L); // Проверка каждые 20 тиков (1 секунда)
            } else {
                // Игрок уже существует, проверяем, истекла ли защита
                long protectionEnd = resultSet.getLong("protection_end");
                if (System.currentTimeMillis() < protectionEnd) {
                    // Защита всё ещё активна
                    new ProtectionTask(player, protectionEnd).runTaskTimer(plugin, 0L, 20L);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static class ProtectionTask extends BukkitRunnable {
        private final Player player;
        private long protectionEndTime;

        public ProtectionTask(Player player) {
            this.player = player;
            this.protectionEndTime = System.currentTimeMillis() + (PROTECTION_TIME * 50);
        }

        public ProtectionTask(Player player, long protectionEndTime) {
            this.player = player;
            this.protectionEndTime = protectionEndTime;
        }

        @Override
        public void run() {
            if (System.currentTimeMillis() >= protectionEndTime) {
                cancel(); // Защита окончена
            }
        }
    }
}