package uz.akbar;

import java.io.ByteArrayInputStream;

import org.json.JSONObject;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendAudio;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

/**
 * Bot
 */
public class Bot implements LongPollingSingleThreadUpdateConsumer {

	String botToken = ConfigLoader.getBotToken();

	TelegramClient telegramClient = new OkHttpTelegramClient(botToken);

	@Override
	public void consume(Update update) {

		if (update.hasMessage() && update.getMessage().hasText()) {
			Long chatId = update.getMessage().getChatId();
			String link = update.getMessage().getText();

			if (YoutubeValidator.isValidYoutubeLink(link)) {

				try {
					byte[] downloadedAudio = YoutubeDownloader.downloadAudio(link);

					JSONObject metadata = MetadataFetcher.fetchMetadata(link);
					if (metadata != null) {
					}

					ByteArrayInputStream inputStream = new ByteArrayInputStream(downloadedAudio);
					InputFile inputFile = new InputFile(inputStream, "audio.mp3");

					executeAudio(SendAudio.builder()
							.chatId(chatId)
							.audio(inputFile)
							.caption("Here is your audio file! 🎶")
							.build());
				} catch (Exception e) {
					e.printStackTrace();
					executeMessage(SendMessage.builder()
							.chatId(chatId)
							.text("Something went wrong! 🤷")
							.build());
				}

			} else {
				executeMessage(SendMessage.builder()
						.chatId(chatId)
						.text("Not a valid youtube link 😝")
						.build());
			}

		}

	}

	private void executeMessage(SendMessage sendMessage) {
		try {
			telegramClient.execute(sendMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void executeAudio(SendAudio sendAudio) {
		try {
			telegramClient.execute(sendAudio);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
