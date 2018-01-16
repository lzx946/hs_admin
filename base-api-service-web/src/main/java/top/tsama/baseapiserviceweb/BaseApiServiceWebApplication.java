package top.tsama.baseapiserviceweb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(scanBasePackages = {"top.tsama.*"})
@MapperScan({"top.tsama.baseapiservicedao.mapper"})
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class BaseApiServiceWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(BaseApiServiceWebApplication.class, args);
	}
}
