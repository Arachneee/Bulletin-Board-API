package bulletin.board.config;

import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.cloud.StorageClient;

@Configuration
public class FirebaseConfig {

	@Bean
	public FirebaseApp firebaseApp() throws IOException {
		FileInputStream serviceAccount = new FileInputStream(
			"C:/Users/hogeon/Desktop/project/bulletin-board/board/src/main/resources/serviceAccountKey.json");

		FirebaseOptions options = FirebaseOptions.builder()
			.setCredentials(GoogleCredentials.fromStream(serviceAccount))
			.setStorageBucket("board-d6466.appspot.com")
			.build();

		return FirebaseApp.initializeApp(options);
	}

	@Bean
	public FirebaseAuth firebaseAuth() throws IOException {
		return FirebaseAuth.getInstance(firebaseApp());
	}

	@Bean
	public Bucket bucket() throws IOException {
		return StorageClient.getInstance(firebaseApp()).bucket();
	}
}
