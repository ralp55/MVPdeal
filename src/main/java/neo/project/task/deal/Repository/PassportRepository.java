package neo.project.task.deal.Repository;

import neo.project.task.deal.DTO.Passport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PassportRepository extends JpaRepository<Passport, UUID> {
    Optional<Passport> findBySeriesAndNumberPassport(String series, String numberPassport);
}
