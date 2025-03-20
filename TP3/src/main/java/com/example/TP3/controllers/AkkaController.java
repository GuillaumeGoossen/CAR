package com.example.TP3.controllers;

import com.example.TP3.services.AkkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/akka")
public class AkkaController {

    @Autowired
    private AkkaService akkaService;

    // Page d'accueil
    @GetMapping
    public String index() {
        return "index";
    }

    // Endpoint pour uploader un fichier
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) {
        try {
            List<String> lines = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
            }
            akkaService.distributeLines(lines);
            model.addAttribute("message", "Fichier traité avec succès !");
        } catch (Exception e) {
            model.addAttribute("message", "Erreur lors du traitement du fichier : " + e.getMessage());
        }
        return "index";
    }

    // Endpoint pour rechercher un mot-clé
    @GetMapping("/count")
    public String getWordCount(@RequestParam("word") String word, Model model) {
        try {
            int count = akkaService.getWordCount(word);
            model.addAttribute("word", word);
            model.addAttribute("count", count);
        } catch (Exception e) {
            model.addAttribute("message", "Erreur lors de la recherche du mot : " + e.getMessage());
        }
        return "index";
    }

    // Endpoint pour arrêter le système
    @PostMapping("/shutdown")
    public String shutdown(Model model) {
        akkaService.shutdown();
        model.addAttribute("message", "Système arrêté avec succès.");
        return "index";
    }
}