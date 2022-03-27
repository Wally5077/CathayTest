package tw.wally.cub.services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.wally.cub.models.Currency;

/**
 * @author - wally55077@gmail.com
 */
public interface CurrencyService {

    Currency getCurrencyByCode(String code);

    Currency createCurrency(Request request);

    Currency updateCurrency(Request request);

    void deleteCurrency(String code);

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class Request {
        public String engCurrency;
        public String chiCurrency;
        public String symbol;
        public String description;
        public Float rate;
    }
}
