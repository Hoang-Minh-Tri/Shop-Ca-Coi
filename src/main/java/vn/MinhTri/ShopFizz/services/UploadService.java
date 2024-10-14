package vn.MinhTri.ShopFizz.services;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletContext;

@Service
public class UploadService {
    private final ServletContext servletContext;

    public UploadService(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public String handleSaveUpLoadFile(MultipartFile file, String diaChiFile) {
        if (file.isEmpty())
            return "";
        String rootPath = this.servletContext.getRealPath("/resources/images");// this.servletContext.getRealPath trả ra
                                                                               // thư mục web app
        String finalNameFile = "";
        try {
            byte[] bytes = file.getBytes();

            File dir = new File(rootPath + File.separator + diaChiFile);// File.separator có nghĩa à dấu '/'
            if (!dir.exists())// Kiểm tra linh dir đã tồn tại hay chưa nếu chưa thì tạo mới nink
                dir.mkdirs();
            finalNameFile = System.currentTimeMillis() + "-" + file.getOriginalFilename();
            File serverFile = new File(dir.getAbsolutePath() + File.separator + finalNameFile);// tạo kink mới lưu file
            // System.currentTimeMillis() + "-" + file.getOriginalFilename() đổi tên anh mới

            BufferedOutputStream stream = new BufferedOutputStream(
                    new FileOutputStream(serverFile));
            stream.write(bytes);
            stream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return finalNameFile;
    }
}
