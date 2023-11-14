package project.rest_controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.repository.UserRcSQLRepository;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/control")
public class Controller {

    private final UserRcSQLRepository userRcSQLRepository;

    @GetMapping(value = "/{kek}")
    public Object getUser(@PathVariable("kek") String kek) {
        return userRcSQLRepository.object(kek);
    }
}
