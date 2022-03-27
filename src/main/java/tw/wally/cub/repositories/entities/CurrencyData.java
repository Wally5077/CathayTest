package tw.wally.cub.repositories.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tw.wally.cub.models.Currency;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author - wally55077@gmail.com
 */
@Setter
@Getter
@Entity
@Table(name = "CURRENCY")
@NoArgsConstructor
public class CurrencyData {

    @Id
    @Column(name = "ENG_CURRENCY")
    private String engCurrency;

    @Column(name = "CHI_CURRENCY")
    private String chiCurrency;

    @Column(name = "SYMBOL")
    private String symbol;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "RATE")
    private Float rate;

    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    public CurrencyData(String engCurrency, String chiCurrency, String symbol, String description, Float rate, Date updateTime) {
        this.engCurrency = engCurrency;
        this.chiCurrency = chiCurrency;
        this.symbol = symbol;
        this.description = description;
        this.rate = rate;
        this.updateTime = updateTime;
    }

    public Currency toEntity() {
        return new Currency(engCurrency, chiCurrency, symbol, description, rate, updateTime);
    }

    public static CurrencyData toData(Currency currency) {
        return new CurrencyData(currency.getEngCurrency(), currency.getChiCurrency(), currency.getSymbol(), currency.getDescription(), currency.getRate(), currency.getUpdateTime());
    }
}
