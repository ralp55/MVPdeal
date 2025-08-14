package neo.project.task.deal.repository;

import neo.project.task.deal.entity.StatementStatusHistory;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatementStatusHistoryRepository {
    List<StatementStatusHistory> findStatusChangesBetweenDates(LocalDateTime from, LocalDateTime to);

    void save(StatementStatusHistory history);
}
