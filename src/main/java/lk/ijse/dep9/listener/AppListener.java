package lk.ijse.dep9.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class AppListener implements ServletContextListener {

    public void ContextListener(ServletContextEvent sce){
        String realPath=sce.getServletContext().getRealPath("/");
        try {
            Path picturePath = Path.of(realPath, "pictures");
            if (!Files.exists(picturePath)) Files.createDirectory(picturePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
