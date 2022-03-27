package tw.wally.cub.controllers.views;

import lombok.AllArgsConstructor;
import lombok.Data;
import tw.wally.cub.models.Currency;

import static tw.wally.cub.utils.DateUtils.parseDateToString;

/**
 * @author - wally55077@gmail.com
 */
@Data
@AllArgsConstructor
public class CurrencyView {

    private String engCurrency;

    private String chiCurrency;

    private String symbol;

    private String description;

    private Float rate;

    private String updateTime;


    public static CurrencyView toView(Currency currency) {
        return new CurrencyView(currency.getEngCurrency(), currency.getChiCurrency(), currency.getSymbol(), currency.getDescription(), currency.getRate(), parseDateToString(currency.getUpdateTime()));
    }
}
