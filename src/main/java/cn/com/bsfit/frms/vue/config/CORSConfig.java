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

/**
 * CORS configuration
 */
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
	
	@Bean
	public HttpMessageConverters httpMessageConverters() {
		final FastJsonHttpMessageConverter fastjson = new FastJsonHttpMessageConverter();
		fastjson.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON_UTF8, MediaType.APPLICATION_JSON));
		return new HttpMessageConverters(fastjson);
	} 
}