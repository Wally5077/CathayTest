package tw.wally.cub.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tw.wally.cub.excetions.NotFoundException;
import tw.wally.cub.models.Currency;
import tw.wally.cub.repositories.CurrencyRepository;
import tw.wally.cub.repositories.entities.CurrencyData;
import tw.wally.cub.services.CurrencyService;

import java.util.Date;

/**
 * @author - wally55077@gmail.com
 */
@Service
@AllArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;

    @Override
    public Currency getCurrencyByCode(String code) {
        return currencyRepository.findById(code)
                .map(CurrencyData::toEntity)
                .orElseThrow(() -> new NotFoundException(code));
    }

    @Override
    public Currency createCurrency(Request request) {
        return currencyRepository.save(new CurrencyData(request.engCurrency, request.chiCurrency, request.symbol, request.description, request.rate, new Date()))
                .toEntity();
    }

    @Override
    public Currency updateCurrency(Request request) {
        String code = request.engCurrency;
        if (!currencyRepository.existsById(code)) {
            throw new NotFoundException(code);
        }
        return currencyRepository.save(new CurrencyData(code, request.chiCurrency, request.symbol, request.description, request.rate, new Date()))
                .toEntity();
    }

    @Override
    public void deleteCurrency(String code) {
        currencyRepository.deleteById(code);
    }
}
