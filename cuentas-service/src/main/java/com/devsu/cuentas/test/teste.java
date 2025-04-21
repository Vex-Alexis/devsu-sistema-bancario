package com.devsu.cuentas.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class teste {

    @GetMapping
    public String test(){
        return "testeando";
    }
}
