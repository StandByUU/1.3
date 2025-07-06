import java.io.*;
import java.util.*;
import java.util.zip.*;

public class SaveManager {

    public static void main(String[] args) {
        String saveDir = "D:/GAMES/savegames";
        String zipPath = saveDir + "/zip.zip";

        List<String> saveFiles = new ArrayList<>();

        GameProgress progress1 = new GameProgress(100, 5, 1, 123.45);
        GameProgress progress2 = new GameProgress(80, 4, 2, 234.56);
        GameProgress progress3 = new GameProgress(60, 3, 3, 345.67);

        saveFiles.add(saveGame(saveDir + "/save1.dat", progress1));
        saveFiles.add(saveGame(saveDir + "/save2.dat", progress2));
        saveFiles.add(saveGame(saveDir + "/save3.dat", progress3));

        zipFiles(zipPath, saveFiles);

        for (String filePath : saveFiles) {
            File file = new File(filePath);
            if (file.delete()) {
                System.out.println("Файл удален: " + filePath);
            }
        }
    }

    public static String saveGame(String path, GameProgress gameProgress) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(gameProgress);
            System.out.println("Сохранено: " + path);
            return path;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void zipFiles(String zipPath, List<String> filePaths) {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipPath))) {
            byte[] buffer = new byte[1024];
            for (String filePath : filePaths) {
                File file = new File(filePath);
                try (FileInputStream fis = new FileInputStream(file)) {
                    zos.putNextEntry(new ZipEntry(file.getName()));
                    int length;
                    while ((length = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, length);
                    }
                    zos.closeEntry();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
