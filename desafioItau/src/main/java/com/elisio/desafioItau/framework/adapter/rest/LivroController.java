package com.elisio.desafioItau.framework.adapter.rest;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/livros")
@Tag(name = "Livros REST")
public class LivroController {
}
