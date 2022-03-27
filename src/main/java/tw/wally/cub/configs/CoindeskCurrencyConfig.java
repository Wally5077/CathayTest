package tw.wally.cub.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import tw.wally.cub.repositories.CurrencyRepository;
import tw.wally.cub.services.CoindeskCurrencyService;
import tw.wally.cub.services.impl.HttpCoindeskCurrencyService;

/**
 * @author - wally55077@gmail.com
 */
@Configuration
public class CoindeskCurrencyConfig {

    @Bean
    public CoindeskCurrencyService coindeskCurrencyService(ObjectMapper objectMapper,
                                                           RestTemplate restTemplate,
                                                           @Value("${cub.currency.service.host}") String coindeskCurrencyServiceHost,
                                                           CurrencyRepository currencyRepository) {
        return new HttpCoindeskCurrencyService(objectMapper, restTemplate, coindeskCurrencyServiceHost, currencyRepository);
    }
}
