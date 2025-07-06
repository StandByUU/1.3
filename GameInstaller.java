import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GameInstaller {
    public static void main(String[] args) {
        String baseDirPath = "D:/GAMES";
        File baseDir = new File(baseDirPath);

        StringBuilder log = new StringBuilder();

        if (baseDir.mkdir()) {
            log.append("Директория ").append(baseDir.getAbsolutePath()).append("\n");
        } else {
            log.append("Не удалось создать директорию ").append(baseDir.getAbsolutePath()).append("\n");
        }

        String[][] dirsToCreate = {
                {"src", null},
                {"src/main", null},
                {"src/test", null},
                {"res", null},
                {"res/drawables", null},
                {"res/vectors", null},
                {"res/icons", null},
                {"savegames", null},
                {"temp", null}
        };

        for (String[] dirInfo : dirsToCreate) {
            String path = dirInfo[0];
            File dir = new File(baseDir, path);
            if (dir.mkdirs()) {
                log.append("Создана директория: ").append(dir.getAbsolutePath()).append("\n");
            } else {
                log.append("Не удалось создать директорию: ").append(dir.getAbsolutePath()).append("\n");
            }
        }

        File mainDir = new File(baseDir, "src/main");
        String[] filesInMain = {"Main.java", "Utils.java"};
        for (String fileName : filesInMain) {
            File file = new File(mainDir, fileName);
            try {
                if (file.createNewFile()) {
                    log.append("Создан файл: ").append(file.getAbsolutePath()).append("\n");
                } else {
                    log.append("Файл уже существует или не может быть создан: ").append(file.getAbsolutePath()).append("\n");
                }
            } catch (IOException e) {
                log.append("Ошибка при создании файла: ").append(file.getAbsolutePath()).append(" - ").append(e.getMessage()).append("\n");
            }
        }

        File tempFile = new File(baseDir, "temp/temp.txt");
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write(log.toString());
            System.out.println("Лог записан в файл: " + tempFile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Не удалось записать лог в файл: " + tempFile.getAbsolutePath());
            e.printStackTrace();
        }
    }
}
