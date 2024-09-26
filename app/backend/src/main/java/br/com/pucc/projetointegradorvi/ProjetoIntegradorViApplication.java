package br.com.pucc.projetointegradorvi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ProjetoIntegradorViApplication {

	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "chtt24";
        String encodedPassword = encoder.encode(rawPassword);
        System.out.println("Senha encriptada: " + encodedPassword);
		SpringApplication.run(ProjetoIntegradorViApplication.class, args);
	}

}
