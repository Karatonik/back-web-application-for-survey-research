package pl.mateusz.kalksztejn.survey.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mateusz.kalksztejn.survey.models.Payment;
@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {
}
