package tw.wally.cub.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author - wally55077@gmail.com
 */
@Data
@NoArgsConstructor
public class Currency {

    private String engCurrency;

    private String chiCurrency;

    private String symbol;

    private String description;

    private Float rate;

    private Date updateTime;

    public Currency(String engCurrency, String chiCurrency, String symbol, String description, Float rate, Date updateTime) {
        this.engCurrency = engCurrency;
        this.chiCurrency = chiCurrency;
        this.symbol = symbol;
        this.description = description;
        this.rate = rate;
        this.updateTime = updateTime;
    }
}
