 package uz.backend.zero_one_intern_task1

 import org.springframework.context.annotation.Bean
 import org.springframework.context.annotation.Configuration
 import org.springframework.context.support.ResourceBundleMessageSource
 import org.springframework.web.servlet.config.annotation.CorsRegistry
 import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
 import org.springframework.web.servlet.i18n.SessionLocaleResolver
 import java.util.*

 @Configuration
 class WebMvcConfig : WebMvcConfigurer {
     @Bean
     fun localeResolver() = SessionLocaleResolver().apply { setDefaultLocale(Locale("ru")) }

     @Bean
     fun errorMessageSource() = ResourceBundleMessageSource().apply {
         setDefaultEncoding(Charsets.UTF_8.name())
         setBasename("error")
     }


     @Bean
     fun messageSource() = ResourceBundleMessageSource().apply {
         setDefaultEncoding(Charsets.UTF_8.name())
         setBasename("message")
     }
 }

 @Configuration
 class CorsConfig : WebMvcConfigurer {
     override fun addCorsMappings(registry: CorsRegistry) {
         registry.addMapping("/**")
             .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
     }
 }