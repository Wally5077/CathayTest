package tw.wally.cub.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.wally.cub.controllers.views.BasicCoindeskCurrencyView;
import tw.wally.cub.controllers.views.DetailCoindeskCurrencyView;
import tw.wally.cub.services.CoindeskCurrencyService;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author - wally55077@gmail.com
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/coindesk/currency")
public class CoindeskCurrencyController {

    private final CoindeskCurrencyService coindeskCurrencyService;

    @GetMapping("/detail")
    public DetailCoindeskCurrencyView detailCoindeskCurrency() {
        return coindeskCurrencyService.getDetailCoindeskCurrency();
    }

    @GetMapping("/basic")
    public List<BasicCoindeskCurrencyView> basicCoindeskCurrencies() {
        return coindeskCurrencyService.getBasicCoindeskCurrencies()
                .stream()
                .map(BasicCoindeskCurrencyView::toView)
                .collect(toList());
    }
}
