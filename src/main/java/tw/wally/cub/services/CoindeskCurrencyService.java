package tw.wally.cub.services;

import tw.wally.cub.controllers.views.DetailCoindeskCurrencyView;
import tw.wally.cub.models.Currency;

import java.util.List;

/**
 * @author - wally55077@gmail.com
 */
public interface CoindeskCurrencyService {
    DetailCoindeskCurrencyView getDetailCoindeskCurrency();

    List<Currency> getBasicCoindeskCurrencies();
}
