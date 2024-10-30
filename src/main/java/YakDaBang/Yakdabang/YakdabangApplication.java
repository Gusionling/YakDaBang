package YakDaBang.Yakdabang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class YakdabangApplication {

	public static void main(String[] args) {
		SpringApplication.run(YakdabangApplication.class, args);
	}

}
