package uz.akbar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * YoutubeValidator
 */
public class YoutubeValidator {

	public static final String REGEX = "^(https?://)?(www\\.)?"
			+ "(youtube\\.com/(watch\\?v=|embed/|v/|.+\\?v=)|youtu\\.be/|youtube-nocookie\\.com/(embed/|watch\\?v=))"
			+ "([a-zA-Z0-9_-]{11})"
			+ "(\\?[^\\s]*)?$";

	public static boolean isValidYoutubeLink(String url) {
		if (url == null || url.isEmpty()) {
			return false;
		}

		Pattern pattern = Pattern.compile(REGEX);
		Matcher matcher = pattern.matcher(url);

		return matcher.matches();
	}

}
