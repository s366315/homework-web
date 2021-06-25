package org.hillel.web.controller;

//import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class DownloadController {
    @GetMapping("/download/{file_name:.+}")
    public void downloadFile(@PathVariable("file_name") String fileName, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String filesDir = request.getServletContext().getRealPath("/WEB-INF/storage/");
        Path path = Paths.get(filesDir, fileName);
        File file = path.toFile();

        InputStream inputStream = new FileInputStream(file);
        response.setContentType("application/force-download");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
//        IOUtils.copy(inputStream, response.getOutputStream());
        response.flushBuffer();
        inputStream.close();
    }
}
