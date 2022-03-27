package tw.wally.cub.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tw.wally.cub.controllers.views.CurrencyView;
import tw.wally.cub.services.CurrencyService;

/**
 * @author - wally55077@gmail.com
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/currency/{code}")
public class CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping
    public CurrencyView getCurrencyByCode(@PathVariable String code) {
        return CurrencyView.toView(currencyService.getCurrencyByCode(code));
    }

    @PostMapping
    public CurrencyView createCurrency(@PathVariable String code,
                                       @RequestBody CurrencyService.Request request) {
        request.engCurrency = code;
        return CurrencyView.toView(currencyService.createCurrency(request));
    }

    @PutMapping
    public CurrencyView updateCurrency(@PathVariable String code,
                                       @RequestBody CurrencyService.Request request) {
        request.engCurrency = code;
        return CurrencyView.toView(currencyService.updateCurrency(request));
    }

    @DeleteMapping
    public void deleteCurrency(@PathVariable String code) {
        currencyService.deleteCurrency(code);
    }

}
