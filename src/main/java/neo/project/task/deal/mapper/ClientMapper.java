package neo.project.task.deal.mapper;

import neo.project.task.deal.dto.FinishRegistrationRequestDto;
import neo.project.task.deal.dto.LoanStatementRequestDto;
import neo.project.task.deal.entity.Client;
import neo.project.task.deal.entity.Passport;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(target = "passport", source = "passport.passport")
    Client toClient(LoanStatementRequestDto request, Passport passport);
}
