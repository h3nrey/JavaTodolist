package br.com.h3nrey.todolist.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/testroute")
public class firstController {
    @GetMapping("/")
    public String GetJojos() {
        return "Jotaro";
    }
}
