package es.uji.ei1027.sape;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
@Configuration
public class SAPEConfiguration
{
	@Bean
	@ConfigurationProperties(prefix="spring.datasource")
	public DataSource dataSource()
	{
		return DataSourceBuilder.create().build();
	}
}
