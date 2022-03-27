package tw.wally.cub.controllers.views;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.wally.cub.models.Currency;

import static tw.wally.cub.utils.DateUtils.parseDateToString;

/**
 * @author - wally55077@gmail.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasicCoindeskCurrencyView {
    private String engCurrency;

    private String chiCurrency;

    private Float rate;

    private String updateTime;


    public static BasicCoindeskCurrencyView toView(Currency currency) {
        return new BasicCoindeskCurrencyView(currency.getEngCurrency(), currency.getChiCurrency(), currency.getRate(), parseDateToString(currency.getUpdateTime()));
    }
}
