package com.example.foodservice.common.fileservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {
    // Путь к корневой папке хранения. По дефолту создаст папку "uploads" в корне проекта
    @Value("${app.upload.dir:${user.dir}/uploads}")
    private String baseUploadDir;

    /**
     * Универсальный метод для сохранения любого файла на диск.
     * @param file бинарный файл из контроллера
     * @param subFolder подпапка (например, "brands" или "products")
     * @return Относительный URL-путь для сохранения в базу данных
     */
    public String storeFile(MultipartFile file, String subFolder) {
        if (file == null || file.isEmpty()) {
            return null;
        }

        try {
            // Формируем физический путь к папке: uploads/brands/ или uploads/products/
            String finalDir = baseUploadDir + File.separator + subFolder + File.separator;
            File directory = new File(finalDir);
            if (!directory.exists()) {
                directory.mkdirs(); // Создаем папки, если их нет
            }

            // Генерируем уникальное имя файла для защиты от дубликатов
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path targetLocation = Paths.get(finalDir + fileName);

            // Физически копируем байты файла на жесткий диск
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            // Возвращаем виртуальный путь, по которому Spring будет раздавать статику
            return "/static/" + subFolder + "/" + fileName;

        } catch (IOException ex) {
            throw new RuntimeException("Could not store file. Please try again!", ex);
            // TODO: Заменить на кастомный FileStorageException в будущем
        }
    }
}
