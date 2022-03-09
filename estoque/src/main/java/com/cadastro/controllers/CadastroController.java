package com.cadastro.controllers;

import com.cadastro.models.CadastroModel;
import com.cadastro.repositories.CadastroRepository;
import java.util.Optional;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@Controller
public class CadastroController {

    @Autowired
    CadastroRepository cadastroRepository;

    @GetMapping("/")
    public String index() {

        return "cadastro/index";
    }

    @GetMapping("/produtos")
    public ModelAndView verProdutos() {

        List<CadastroModel> listaCadastros = new ArrayList<>();

        listaCadastros = cadastroRepository.findAll();

        ModelAndView mv = new ModelAndView("cadastro/produtos");

        mv.addObject("listaCadastros", listaCadastros);

        return mv;
    }

    @PostMapping("/produtos/cadastrar")
    public String cadastraProduto(CadastroModel cadastro) {

        cadastroRepository.save(cadastro);

        return "redirect:/produtos";
    }

    @GetMapping("/produtos/{id}")
    public ModelAndView detalhes(@PathVariable Long id) {

        Optional<CadastroModel> cadastro = cadastroRepository.findById(id);

        if (cadastro.isPresent()) {

            ModelAndView mv = new ModelAndView("cadastro/detalhes");

            mv.addObject("cadastro", cadastro.get());

            return mv;
        } else {

            return new ModelAndView("redirect:/produtos");
        }

    }

    @GetMapping("/produtos/deletar/{id}")
    public String deletar(@PathVariable Long id) {

        cadastroRepository.deleteById(id);

        return "redirect:/produtos";
    }

    @GetMapping("/produtos/{id}/editar")
    public ModelAndView produtoEditar(@PathVariable Long id) {

        Optional<CadastroModel> cadastro = cadastroRepository.findById(id);

        if (cadastro.isPresent()) {

            ModelAndView mv = new ModelAndView("/cadastro/edicao");

            mv.addObject("cadastro", cadastro.get());

            return mv;
        } else {

            return new ModelAndView("redirect:/produtos");
        }

    }

    @PostMapping("/produtos/editar")
    public String editarProduto(CadastroModel cadastro) {

        cadastroRepository.save(cadastro);

        return "redirect:/produtos";
    }

}
