package tw.wally.cub.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import tw.wally.cub.controllers.views.DetailCoindeskCurrencyView;
import tw.wally.cub.excetions.NotFoundException;
import tw.wally.cub.models.Currency;
import tw.wally.cub.repositories.CurrencyRepository;
import tw.wally.cub.repositories.entities.CurrencyData;
import tw.wally.cub.services.CoindeskCurrencyService;

import java.util.List;
import java.util.Map;

import static tw.wally.cub.utils.StreamUtils.mapToList;
import static tw.wally.cub.utils.StreamUtils.toMap;

/**
 * @author - wally55077@gmail.com
 */
@AllArgsConstructor
public class HttpCoindeskCurrencyService implements CoindeskCurrencyService {

    private final ObjectMapper objectMapper;
    private final RestTemplate api;
    private final String serviceHost;
    private final CurrencyRepository currencyRepository;

    @Override
    public DetailCoindeskCurrencyView getDetailCoindeskCurrency() {
        try {
            String coindeskCurrencyJson = api.getForObject(serviceHost + "/v1/bpi/currentprice.json", String.class);
            return objectMapper.readValue(coindeskCurrencyJson, DetailCoindeskCurrencyView.class);
        } catch (RestClientException e) {
            throw new NotFoundException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Currency> getBasicCoindeskCurrencies() {
        List<Currency> detailCoindeskCurrencies = getDetailCoindeskCurrency()
                .totCurrencies();
        Map<String, String> currencies = getCurrencies(detailCoindeskCurrencies);
        detailCoindeskCurrencies.forEach(currency -> currency.setChiCurrency(currencies.getOrDefault(currency.getEngCurrency(), "N/A")));
        return detailCoindeskCurrencies;
    }

    private Map<String, String> getCurrencies(List<Currency> detailCoindeskCurrencies) {
        List<String> engCurrencies = mapToList(detailCoindeskCurrencies, Currency::getEngCurrency);
        return toMap(currencyRepository.findAllById(engCurrencies), CurrencyData::getEngCurrency, CurrencyData::getChiCurrency);
    }

}
