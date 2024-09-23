package uz.akbar;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.json.JSONObject;

/**
 * MetadataFetcher
 */
public class MetadataFetcher {

	public static JSONObject fetchMetadata(String url) {
		try {
			ProcessBuilder builder = new ProcessBuilder("yt-dlp", "--dump-json", url);
			Process process = builder.start();

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			StringBuilder jsonOutput = new StringBuilder();
			String line;

			while ((line = reader.readLine()) != null) {
				jsonOutput.append(line).append("\n");
			}

			process.waitFor();

			String content = jsonOutput.toString().trim();
			if (content.startsWith("{")) {
				return new JSONObject(content);
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
