package de.javagil.samples.springwebfluxcontrollertest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * The whole purpose of this repository is, to show how this Spring WebFlux @Controller can be tested.
 */
@Controller
public class RecordsController {

    private final RecordsService recordsService;

    public RecordsController(RecordsService recordsService) {
        this.recordsService = recordsService;
    }

    @GetMapping("/records")
    public String getRecordsPage(Model model) {

        model.addAttribute("records", recordsService.getRecords());

        return "records";
    }
}
