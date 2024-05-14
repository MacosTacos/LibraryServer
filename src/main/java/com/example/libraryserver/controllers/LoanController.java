package com.example.libraryserver.controllers;

import com.example.libraryserver.requests.loans.CreateLoanRequest;
import com.example.libraryserver.requests.loans.UpdateLoanStatusRequest;
import com.example.libraryserver.responses.general.InfoResponse;
import com.example.libraryserver.responses.loans.GetLoanResponse;
import com.example.libraryserver.responses.loans.GetLoansResponse;
import com.example.libraryserver.services.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/loan")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @Transactional
    @PostMapping("/create")
    public ResponseEntity<InfoResponse> createLoan(@RequestBody CreateLoanRequest createLoanRequest) {
        return loanService.createLoan(createLoanRequest);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getLoanById(@PathVariable("id") Long id) {
        return loanService.getLoanById(id);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getLoans() {
        return loanService.getAllLoans();
    }
    @PatchMapping("/update")
    public ResponseEntity<InfoResponse> updateLoanStatus(@RequestBody UpdateLoanStatusRequest request) {
        return loanService.updateLoanStatus(request);
    }

    //TODO: сделать поиск по юзеру

}
