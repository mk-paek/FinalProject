package com.example.demo.controller;

import com.example.demo.dto.SampleDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@Log4j2
@RequestMapping("/sample")     //     localhost:8080/sample/aaa
public class SampleController {
    @GetMapping("/hello")
    public void hello() {             //localhost:8081/sample/hello
        log.info("hello .....");
    }

    @GetMapping({"/hello2"})
    public void helloModel(Model model) {
        List<SampleDTO> list = IntStream.rangeClosed(1, 20).asLongStream().
                mapToObj(i -> {
                    SampleDTO dto = SampleDTO.builder()
                            .sno(i)
                            .first("First ...." + i)
                            .last("Last ... " + i)
                            .regTime(LocalDateTime.now())
                            .build();
                    return dto;
                }).collect(Collectors.toList());

        model.addAttribute("list", list);
    }
}
