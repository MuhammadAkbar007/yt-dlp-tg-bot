package uz.akbar;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * YoutubeDownloader
 */
public class YoutubeDownloader {

	public static byte[] downloadAudio(String videoUrl) {

		try {
			ProcessBuilder builder = new ProcessBuilder(
					"yt-dlp",
					"-x",
					"--audio-format",
					"mp3",
					"-o",
					"-",
					videoUrl);
			Process process = builder.start();

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

			try (InputStream inputStream = process.getInputStream()) {
				byte[] buffer = new byte[1024];
				int bytesRead;

				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, bytesRead);
				}

			}

			byte[] audioData = outputStream.toByteArray();
			System.out.println("Downloaded audio size: " + audioData.length + " bytes. 💾");

			try (InputStream errorStream = process.getErrorStream()) {
				byte[] buffer = new byte[1024];
				int errorBytesRead;

				while ((errorBytesRead = errorStream.read(buffer)) != -1) {
					System.err.write(buffer, 0, errorBytesRead);
				}
			}

			process.waitFor();

			return audioData;

		} catch (Exception e) {
			e.printStackTrace();
			return new byte[0];
		}

	}

}
