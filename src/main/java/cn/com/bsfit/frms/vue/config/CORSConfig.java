package cn.com.bsfit.frms.vue.config;

import java.util.Arrays;

import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

@Configuration
public class CORSConfig {

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins(CorsConfiguration.ALL).allowedMethods(CorsConfiguration.ALL)
						.allowedHeaders(CorsConfiguration.ALL).allowCredentials(true);
			}
		};
	}

//	private CorsConfiguration corsConfiguration() {
//		CorsConfiguration corsConfiguration = new CorsConfiguration();
//		corsConfiguration.addAllowedOrigin(CorsConfiguration.ALL);
//		corsConfiguration.addAllowedHeader(CorsConfiguration.ALL);
//		corsConfiguration.addAllowedMethod(CorsConfiguration.ALL);
//		return corsConfiguration;
//	}

	@Bean
	public HttpMessageConverters httpMessageConverters() {
		final FastJsonHttpMessageConverter fastjson = new FastJsonHttpMessageConverter();
		fastjson.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON_UTF8, MediaType.APPLICATION_JSON));
		return new HttpMessageConverters(fastjson);
	}

//	@Bean
//	public FilterRegistrationBean filterRegistrationBean() {
//		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		source.registerCorsConfiguration("/**", corsConfiguration()); // 4
//		CorsFilter filter = new CorsFilter(source);
//
//		registrationBean.setFilter(filter);
//		List<String> urlPatterns = new ArrayList<String>();
//		urlPatterns.add("/api/*");
//		registrationBean.setUrlPatterns(urlPatterns);
//
//		return registrationBean;
//	}
}