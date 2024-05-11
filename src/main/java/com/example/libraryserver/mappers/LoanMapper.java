package com.example.libraryserver.mappers;

import com.example.libraryserver.entities.LoanEntity;
import com.example.libraryserver.dtos.LoanDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface LoanMapper {
    LoanDTO loanEntityToLoanDTO(LoanEntity loanEntity);

    LoanEntity loanDTOToLoanEntity(LoanDTO loanDTO);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "book", ignore = true)
    @Named("loanEntityToLoanDTOWithoutUserAndBook")
    LoanDTO loanEntityToLoanDTOWithoutUserAndBook(LoanEntity loanEntity);
}
