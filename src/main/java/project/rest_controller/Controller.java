package project.rest_controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.repository.impl.UserRcSQLRepositoryImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/control")
public class Controller {

    private final UserRcSQLRepositoryImpl userRcSQLRepository;

    @GetMapping(value = "/{kek}")
    public Object getUser(@PathVariable("kek") String kek) {
        return userRcSQLRepository.object(kek);
    }
}
