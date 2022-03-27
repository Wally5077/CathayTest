package tw.wally.cub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.wally.cub.repositories.entities.CurrencyData;

/**
 * @author - wally55077@gmail.com
 */
@Repository
public interface CurrencyRepository extends JpaRepository<CurrencyData, String> {
}
