package uz.akbar;

import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;

/**
 * Main
 */
public class Main {

	public static void main(String[] args) {
		try {
			String botToken = ConfigLoader.getBotToken();

			if (botToken == null || botToken.isEmpty()) {
				throw new IllegalArgumentException("Telegram bot token is missing! 🤖");
			}

			TelegramBotsLongPollingApplication botsLongPollingApplication = new TelegramBotsLongPollingApplication();
			botsLongPollingApplication.registerBot(botToken, new Bot());

			System.out.println();
			System.out.println("yt-dlp bot has started 👾");
			System.out.println();

			Runtime.getRuntime().addShutdownHook(new Thread(() -> {
				System.out.println("\n\nShutting down the bot... 👋");

				try {
					botsLongPollingApplication.close();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
