package ru.mts.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mts.dto.RequestDto;
import ru.mts.service.RequestService;

import java.util.List;

@RestController
@RequestMapping("/api/request")
@Slf4j
public class RequestController {

    private final RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<RequestDto>> getAllRequests(@AuthenticationPrincipal UserDetails userDetails) {

        log.info(">> Пользователь {} посмотрел все заявки", userDetails.getUsername());
        return ResponseEntity.ok(requestService.getRequests(userDetails.getUsername()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RequestDto> getRequestById(@PathVariable Integer id, @AuthenticationPrincipal UserDetails userDetails) {

        log.info(">> Пользователь {} посмотрел заявку с ID {}", userDetails.getUsername(), id);
        return ResponseEntity.ok(requestService.getRequestById(id, userDetails.getUsername()));
    }
}
