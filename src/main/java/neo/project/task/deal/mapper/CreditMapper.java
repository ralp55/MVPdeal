package neo.project.task.deal.mapper;

import neo.project.task.deal.dto.CreditDto;
import neo.project.task.deal.entity.Credit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CreditMapper {

    @Mapping(target = "creditStatus", constant = "CALCULATED")
    @Mapping(target = "amount", source = "amount")
    @Mapping(target = "term", source = "creditDto.term")
    @Mapping(target = "monthlyPayment", source = "creditDto.monthlyPayment")
    @Mapping(target = "rate", source = "creditDto.rate")
    @Mapping(target = "psk", source = "creditDto.psk")
    @Mapping(target = "paymentSchedule", source = "creditDto.paymentSchedule")
    @Mapping(target = "insuranceEnabled", source = "creditDto.isInsuranceEnabled")
    @Mapping(target = "salaryClient", source = "creditDto.isSalaryClient")
    Credit toCredit(CreditDto creditDto);
}
