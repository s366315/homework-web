package org.hillel.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Controller
public class WelcomeController {

    @GetMapping("/welcome")
    public String welcomePage() {
        return "jsp/welcome";
    }

    @GetMapping("/")
    public String welcomePageMore() {
        return "jsp/welcome";
    }

    @PostMapping(value = "/welcome")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {
        String path = System.getProperty("java.io.tmpdir") + "\\" + file.getOriginalFilename();

        File newFile = new File(path);
        newFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(newFile);
        byte[] bytes = file.getBytes();
        fos.write(bytes);
        fos.close();

        redirectAttributes.addAttribute("path", path);

        return "redirect:/download";
    }
}
