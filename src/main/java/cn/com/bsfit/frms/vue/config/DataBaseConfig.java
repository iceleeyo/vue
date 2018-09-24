//package cn.com.bsfit.frms.vue.config;
//
//import javax.sql.DataSource;
//
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.jdbc.core.JdbcTemplate;
//
//import com.zaxxer.hikari.HikariDataSource;
//
//@Configuration
//public class DataBaseConfig {
//
//	@Bean
//	@Primary
//	@ConfigurationProperties(prefix = "frms.etl.from")
//	public DataSource dataSource() {
//		return DataSourceBuilder.create().type(HikariDataSource.class).build();
//	}
//
//	@Autowired
//	@Qualifier("dataSource")
//	private DataSource dataSource;
//
//	@Bean
//	public SqlSessionFactory fromSqlSessionFactory(@Value("${frms.etl.from.log4jdbc.enable:false}") boolean useSpy)
//			throws Exception {
//		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//		bean.setDataSource(this.spyDriver(this.dataSource, useSpy));
//		return bean.getObject();
//	}
//
//	private DataSource spyDriver(DataSource dataSource, boolean useSpy) {
//		if (useSpy && dataSource instanceof HikariDataSource) {
//			HikariDataSource hikariDataSource = (HikariDataSource) dataSource;
//			hikariDataSource.setDriverClassName("net.sf.log4jdbc.DriverSpy");
//			hikariDataSource.setJdbcUrl(hikariDataSource.getJdbcUrl().replaceFirst(":", ":log4jdbc:"));
//		}
//
//		return dataSource;
//	}
//
//	@Bean
//	public JdbcTemplate jdbcTemplate(final DataSource dataSource) {
//		return new JdbcTemplate(dataSource);
//	}
//}
