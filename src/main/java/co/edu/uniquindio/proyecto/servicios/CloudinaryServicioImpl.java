package co.edu.uniquindio.proyecto.servicios;

import co.edu.uniquindio.proyecto.servicios.interfaces.ICloudinaryServicio;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CloudinaryServicioImpl implements ICloudinaryServicio {
    private Cloudinary cloudinary;
    private Map<String, String> config = new HashMap<>();

    public CloudinaryServicioImpl() {
        config.put("cloud_name", "dqktdxbzd");
        config.put("api_key", "295866122955819");
        config.put("api_secret", "ZyI0RrhZsvUmlwbtaamCH7a4cRU");
        cloudinary = new Cloudinary(config);
    }

    @Override
    public Map subirImagen(MultipartFile imagen) throws Exception {

        File file = convertir(imagen);
        return cloudinary.uploader().upload(file, ObjectUtils.asMap("folder", "unilocal"));
    }

    @Override
    public Map eliminarImagen(String idImagen) throws Exception {

        return cloudinary.uploader().destroy(idImagen, ObjectUtils.emptyMap());
    }

    private File convertir(MultipartFile imagen) throws IOException {
        File file = File.createTempFile(imagen.getOriginalFilename(), null);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(imagen.getBytes());
        fos.close();
        return file;
    }
}
