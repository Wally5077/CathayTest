package tw.wally.cub;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import tw.wally.cub.models.Currency;
import tw.wally.cub.repositories.CurrencyRepository;
import tw.wally.cub.repositories.entities.CurrencyData;
import tw.wally.cub.services.CurrencyService;

import java.util.Date;
import java.util.List;
import java.util.Random;

import static java.lang.System.currentTimeMillis;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tw.wally.cub.utils.StreamUtils.mapToList;

@Slf4j
@ContextConfiguration(classes = {CubApplication.class})
public class CubApplicationTest extends AbstractSpringBootTest {

    private static final String COINDESK_CURRENCY_API_PREFIX = "/api/coindesk/currency";
    private static final String CURRENCY_API_PREFIX = "/api/currency";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CurrencyRepository currencyRepository;

    private final Random random = new Random();
    private final Currency USD = new Currency("USD", "美金", "$", "United States Dollar", random.nextFloat(), new Date(currentTimeMillis()));
    private final Currency GBP = new Currency("GBP", "英鎊", "£", "British Pound Sterling", random.nextFloat(), new Date(currentTimeMillis()));
    private final Currency EUR = new Currency("EUR", "歐元", "€", "Euro", random.nextFloat(), new Date(currentTimeMillis()));

    @BeforeEach
    public void cleanUp() {
        currencyRepository.deleteAll();
    }

    /**
     * 測試呼叫查詢幣別對應表資料API，並顯示其內容。
     */
    @Test
    public void givenUSDHasSaved_WhenGetUSD_ThenShouldSucceed() throws Exception {
        Currency currency = givenCurrencyHasSavedAndGet(USD);

        String currencyUSDCode = currency.getEngCurrency();
        String usdCurrency = getContentAsString(
                mockMvc.perform(get(CURRENCY_API_PREFIX + "/{code}", currencyUSDCode))
                        .andExpect(status().isOk()));

        assertTrue(currencyRepository.existsById(currencyUSDCode));
        log.info("[Query Currency] currency={}", usdCurrency);
    }

    /**
     * 測試呼叫新增幣別對應表資料API。
     */
    @Test
    public void whenCreateGPB_ThenShouldSucceed() throws Exception {
        String currencyGBPCode = "GBP";
        String gpbCurrency = getContentAsString(
                mockMvc.perform(post(CURRENCY_API_PREFIX + "/{code}", currencyGBPCode)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(new CurrencyService.Request(currencyGBPCode, "英鎊", "gbp-symbol", "British Pound Sterling", 33397.0751f))))
                        .andExpect(status().isOk()));

        assertTrue(currencyRepository.existsById(currencyGBPCode));
        log.info("[Create Currency] currency={}", gpbCurrency);
    }

    /**
     * 測試呼叫更新幣別對應表資料API，並顯示其內容。
     */
    @Test
    public void givenEURHasSaved_WhenUpdateEUR_ThenShouldSucceed() throws Exception {
        Currency currency = givenCurrencyHasSavedAndGet(EUR);

        String currencyEURCode = currency.getEngCurrency();
        String eurCurrency = getContentAsString(
                mockMvc.perform(put(CURRENCY_API_PREFIX + "/{code}", currencyEURCode)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(new CurrencyService.Request(currencyEURCode, "歐元", "eur-symbol", "Euro", 40005.8548f))))
                        .andExpect(status().isOk()));

        assertTrue(currencyRepository.existsById(currencyEURCode));
        log.info("[Update Currency] currency={}", eurCurrency);
    }

    /**
     * 測試呼叫刪除幣別對應表資料API。
     */
    @Test
    public void givenUSDAndGBPAndEURHaveSaved_WhenDeleteGBP_ThenShouldSucceed() throws Exception {
        givenCurrenciesHaveSaved(USD, GBP, EUR);

        mockMvc.perform(delete(CURRENCY_API_PREFIX + "/{code}", GBP.getEngCurrency()))
                .andExpect(status().isOk());

        List<Currency> actualCurrencies = mapToList(currencyRepository.findAll(), CurrencyData::toEntity);
        assertEquals(2, actualCurrencies.size());
        assertTrue(actualCurrencies.containsAll(asList(USD, EUR)));
        assertFalse(actualCurrencies.contains(GBP));
        log.info("[Delete Currency] currencies={}", toJson(actualCurrencies));
    }

    /**
     * 測試呼叫 coindesk API，並顯示其內容。
     */
    @Test
    public void whenGetDetailCoindeskCurrency_ThenShouldSucceed() throws Exception {
        String detailCoindeskCurrency = getContentAsString(
                mockMvc.perform(get(COINDESK_CURRENCY_API_PREFIX + "/detail"))
                        .andExpect(status().isOk()));

        log.info("[Query Detail Coindesk Currency] currency={}", detailCoindeskCurrency);
    }

    /**
     * 測試呼叫資料轉換的API，並顯示其內容。
     */
    @Test
    public void givenUSDAndGBPAndEURHaveSaved_WhenGetBasicCoindeskCurrencies_ThenShouldSucceed() throws Exception {
        givenCurrenciesHaveSaved(USD, GBP, EUR);

        String basicCoindeskCurrencies = getContentAsString(
                mockMvc.perform(get(COINDESK_CURRENCY_API_PREFIX + "/basic"))
                        .andExpect(status().isOk()));

        log.info("[Query Basic Coindesk Currency] currencies={}", basicCoindeskCurrencies);
    }

    private Currency givenCurrencyHasSavedAndGet(Currency currency) {
        return currencyRepository.save(CurrencyData.toData(currency))
                .toEntity();
    }

    private void givenCurrenciesHaveSaved(Currency... currencies) {
        currencyRepository.saveAll(mapToList(currencies, CurrencyData::toData));
    }

}