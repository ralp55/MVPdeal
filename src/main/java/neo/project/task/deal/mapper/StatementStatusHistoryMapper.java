package neo.project.task.deal.mapper;

import neo.project.task.deal.dto.ApplicationStatus;
import neo.project.task.deal.dto.StatusChangeType;
import neo.project.task.deal.entity.Statement;
import neo.project.task.deal.entity.StatementStatusHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface StatementStatusHistoryMapper {

    @Mapping(target = "time", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "changeType", expression = "java(StatusChangeType.AUTO)")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "statement", source = "statement")
    StatementStatusHistory toStatementStatusHistory(Statement statement, ApplicationStatus status);
}

