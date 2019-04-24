package com.zc;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableTransactionManagement(proxyTargetClass = true)
@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackages = { "com.zc", "com.zc.api", "com.zc.configuration" })
public class EsRegister implements CommandLineRunner {

	@Override
	public void run(String... arg0) throws Exception {
		if (arg0.length > 0 && arg0[0].equals("exitcode")) {
			throw new ExitException();
		}
	}

	class ExitException extends RuntimeException implements ExitCodeGenerator {
		private static final long serialVersionUID = 1L;

		@Override
		public int getExitCode() {
			return 0;
		}

	}

	public static void main(String[] args) throws Exception {
		new SpringApplication(EsRegister.class).run(args);
	}
}
