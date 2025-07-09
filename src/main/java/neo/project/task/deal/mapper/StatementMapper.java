package neo.project.task.deal.mapper;

import neo.project.task.deal.dto.ApplicationStatus;
import neo.project.task.deal.entity.Client;
import neo.project.task.deal.entity.Statement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface StatementMapper {

    @Mapping(target = "client", source = "client")
    @Mapping(target = "creationDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "status", expression = "ApplicationStatus.PREAPPROVAL")
    Statement toStatement(Client client);

    @Mapping(target = "creditId", source = "creditId")
    @Mapping(target = "status", source = "status")
    void updateStatement(@MappingTarget Statement statement, UUID creditId, ApplicationStatus status);
}

