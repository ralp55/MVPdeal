package neo.project.task.deal.repository;

import neo.project.task.deal.dto.ClientDTO;
import neo.project.task.deal.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {
    @Query("SELECT new com.example.send.dto.ClientDTO(" +
            "c.clientId, c.lastName, c.firstName, c.middleName, " +
            "c.birthDate, c.email, c.gender, c.maritalStatus, " +
            "c.dependentAmount, c.passport, c.employment, c.accountNumber) " +
            "FROM Client c WHERE c.clientId = :clientId")
    Optional<ClientDTO> findClientDtoById(@Param("clientId") UUID clientId);
}

