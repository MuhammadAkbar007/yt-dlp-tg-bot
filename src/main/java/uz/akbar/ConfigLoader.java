package uz.akbar;

import java.io.InputStream;
import java.util.Properties;

/**
 * ConfigLoader
 */
public class ConfigLoader {

	public static String getBotToken() {

		try (InputStream input = Main.class.getClassLoader().getResourceAsStream("application.properties")) {

			if (input == null) {
				System.out.println("Sorry, unable to find application.properties");
				return null;
			}

			Properties prop = new Properties();
			prop.load(input);

			return prop.getProperty("bot.token");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
}
