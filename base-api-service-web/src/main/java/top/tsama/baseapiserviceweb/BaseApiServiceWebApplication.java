package top.tsama.baseapiserviceweb;

import com.github.pagehelper.PageHelper;
import com.github.tobato.fastdfs.FdfsClientConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.util.Properties;

@Import(FdfsClientConfig.class)
@SpringBootApplication(scanBasePackages = {"top.tsama.*"})
@MapperScan({"top.tsama.baseapiservicedao.mapper"})
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class BaseApiServiceWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(BaseApiServiceWebApplication.class, args);
	}
	//配置mybatis的分页插件pageHelper
	@Bean
	public PageHelper pageHelper(){
		PageHelper pageHelper = new PageHelper();
		Properties properties = new Properties();
		properties.setProperty("offsetAsPageNum","false");
		properties.setProperty("rowBoundsWithCount","true");
		properties.setProperty("reasonable","true");
		properties.setProperty("pageSizeZero","true");//如果<0，显示第一页数据
		properties.setProperty("dialect","mysql");    //配置mysql数据库的方言
		pageHelper.setProperties(properties);
		return pageHelper;
	}
}
