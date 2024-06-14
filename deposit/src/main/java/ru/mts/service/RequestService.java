package ru.mts.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.mts.common.entity.Customer;
import ru.mts.common.repository.CustomerRepository;
import ru.mts.dto.RequestDto;
import ru.mts.entity.Deposit;
import ru.mts.entity.DepositStatus;
import ru.mts.entity.Request;
import ru.mts.exception.NotFoundException;
import ru.mts.repository.DepositRepository;
import ru.mts.repository.RequestRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class RequestService {

    private final DepositRepository depositRepository;
    private final RequestRepository requestRepository;
    private final CustomerRepository customerRepository;

    public List<RequestDto> getRequests(String username) {
        Customer customer = customerRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("Пользователь " + username + " не найден"));
        List<Request> requests = requestRepository.findAllByCustomer(customer);
        return requests.stream().map(RequestDto::new).collect(Collectors.toList());
    }

    public RequestDto getRequestById(Integer id, String username) {
        return new RequestDto(getRequestByIdAndUser(id, username));
    }

    private Request getRequestByIdAndUser(Integer id, String username) {
        Customer customer = customerRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("Пользователь " + username + " не найден"));
        Optional<Request> optionalRequest = requestRepository.findById(id);
        if (optionalRequest.isEmpty() || !optionalRequest.get().getCustomer().getId().equals(customer.getId())) {
            throw new NotFoundException("Заявка с ID " + id + " не принадлежит пользователю " + username);
        }
        return optionalRequest.get();
    }

    Deposit proceedDeposit(Deposit deposit) {
        DepositStatus status = new Random().nextInt(5) == 0 ? DepositStatus.REJECTED : DepositStatus.ACCEPTED;
        Request request = new Request()
                .setCustomer(deposit.getCustomer())
                .setDeposit(deposit)
                .setStatus(status.name())
                .setRequestDate(deposit.getStartDate())
                .setUpdateDate(LocalDateTime.now());
        requestRepository.save(request);

        deposit.setStatus(status).setStartDate(LocalDateTime.now());

        log.info(">> Заявка на депозит {} обработана. Статус заявки {}", deposit.getId(), status);
        return depositRepository.save(deposit);
    }
}
