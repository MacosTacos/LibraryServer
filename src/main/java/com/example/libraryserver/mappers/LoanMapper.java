package com.example.libraryserver.mappers;

import com.example.libraryserver.entities.LoanEntity;
import com.example.libraryserver.dtos.LoanDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {UserMapper.class, BookMapper.class})
public interface LoanMapper {
    @Mapping(target = "user", qualifiedByName = "userEntityToUserDTOWithoutPswrdAndRoleAndLoans")
    @Mapping(target = "book", qualifiedByName = "bookEntityToBookDTOWithoutAuthorsLoansGenres")
    LoanDTO loanEntityToLoanDTO(LoanEntity loanEntity);

    LoanEntity loanDTOToLoanEntity(LoanDTO loanDTO);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "book", ignore = true)
    @Named("loanEntityToLoanDTOWithoutUserAndBook")
    LoanDTO loanEntityToLoanDTOWithoutUserAndBook(LoanEntity loanEntity);
}
