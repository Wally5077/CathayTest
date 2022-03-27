package tw.wally.cub.controllers.views;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.wally.cub.models.Currency;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static tw.wally.cub.utils.DateUtils.parseStringToDate;

/**
 * @author - wally55077@gmail.com
 */
@Data
@NoArgsConstructor
public class DetailCoindeskCurrencyView {
    private Time time;
    private String disclaimer;
    private String chartName;
    private Bpi bpi;

    public Optional<Bpi> mayHaveBpi() {
        return ofNullable(bpi);
    }

    public List<Currency> totCurrencies() {
        return mayHaveBpi()
                .map(Bpi::getCoindeskCurrencies)
                .orElseGet(Collections::emptyList)
                .stream()
                .map(coindeskCurrency -> coindeskCurrency.toCurrency(time))
                .collect(toList());
    }

    @Data
    @NoArgsConstructor
    public static class Time {
        private String updated;
        private String updatedISO;
        private String updateduk;
    }

    @Data
    @NoArgsConstructor
    public static class Bpi {
        @JsonProperty("USD")
        private CoindeskCurrency USD;
        @JsonProperty("GBP")
        private CoindeskCurrency GBP;
        @JsonProperty("EUR")
        private CoindeskCurrency EUR;

        @JsonIgnore
        public List<CoindeskCurrency> getCoindeskCurrencies() {
            return asList(USD, GBP, EUR);
        }
    }

    @Data
    @NoArgsConstructor
    public static class CoindeskCurrency {
        private String code;
        private String symbol;
        private String rate;
        private String description;
        private float rate_float;

        public Currency toCurrency(Time time) {
            Date date = parseStringToDate(time.updatedISO);
            return new Currency(code, "N/A", symbol, description, rate_float, date);
        }
    }

}
