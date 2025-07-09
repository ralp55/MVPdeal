package neo.project.task.deal.mapper;

import neo.project.task.deal.dto.FinishRegistrationRequestDto;
import neo.project.task.deal.dto.ScoringDataDto;
import neo.project.task.deal.entity.Client;
import neo.project.task.deal.entity.LoanOffer;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ScoringDataMapper {
    //@Mappings{
        @Mapping(source = "offer.requestedAmount", target = "amount")
        @Mapping(target = "term", source = "offer.term")
        @Mapping(target = "firstName", source = "client.firstName")
        @Mapping(target = "lastName", source = "client.lastName")
        @Mapping(target = "middleName", source = "client.middleName")
        @Mapping(target = "birthdate", source = "client.birthDate")
        @Mapping(target = "passportSeries", source = "client.passport.series")
        @Mapping(target = "passportNumber", source = "client.passport.numberPassport")
        @Mapping(target = "isInsuranceEnabled", source = "offer.isInsuranceEnabled")
        @Mapping(target = "isSalaryClient", source = "offer.isSalaryClient")
        @Mapping(target = "gender", source = "request.gender")
        @Mapping(target = "passportIssueDate", source = "request.passportIssueDate")
        @Mapping(target = "passportIssueBranch", source = "request.passportIssueBrach")
        @Mapping(target = "maritalStatus", source = "request.maritalStatus")
        @Mapping(target = "dependentAmount", source = "request.dependentAmount")
        @Mapping(target = "employment", source = "request.employment")
        @Mapping(target = "accountNumber", source = "request.accountNumber")
    //}
    ScoringDataDto toScoringDataDto(Client client, LoanOffer offer, FinishRegistrationRequestDto request);
}
