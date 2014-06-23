package lv.iljakorneckis.webloans.repository;

import lv.iljakorneckis.webloans.domain.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Admin_2 on 2014-06-22.
 */
public interface LoanRepository extends JpaRepository<Loan, Long>{
    List<Loan> findByUserId(String userId);
}
