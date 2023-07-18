package jpabook.jpashop;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JpashopApplication {


	public static void main(String[] args) {
		SpringApplication.run(JpashopApplication.class, args);
	}

	@Bean // 지양해야할 방법이다. 엔티티 노출하면 안 된다.
	Hibernate5Module hibernate5Module() {
		return new Hibernate5Module();
	}

}
