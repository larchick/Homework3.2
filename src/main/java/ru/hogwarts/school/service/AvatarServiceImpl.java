package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.dto.AvatarDTO;
import ru.hogwarts.school.mapper.AvatarMapper;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class AvatarServiceImpl implements AvatarService {
    private final StudentRepository studentRepository;
    private final AvatarRepository avatarRepository;
    private final AvatarMapper avatarMapper;
    public final Logger logger = LoggerFactory.getLogger(AvatarServiceImpl.class);
    @Value("${path.to.avatars.folder}")
    private String avatarsDir;

    public AvatarServiceImpl(StudentRepository studentRepository, AvatarRepository avatarRepository, AvatarMapper avatarMapper) {
        this.studentRepository = studentRepository;
        this.avatarRepository = avatarRepository;
        this.avatarMapper = avatarMapper;
    }

    @Override
    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        logger.info("Method uploadAvatar was invoked!");
        Student student = studentRepository.findById(studentId).orElseThrow();
        Path path = saveToDisk(student, avatarFile);
        saveToDb(student, avatarFile, path);
    }

    private void saveToDb(Student student, MultipartFile avatarFile, Path filePath) throws IOException {
        logger.info("Method saveToDb was invoked!");
        Avatar avatar = findAvatar(student.getId());
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());
        avatarRepository.save(avatar);

    }

    private Path saveToDisk(Student student, MultipartFile avatarFile) throws IOException {

        logger.info("Method saveToDisk was invoked!");
        Path filePath = Path.of(avatarsDir, student.getId() + "." + getExtensions(avatarFile.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = avatarFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }

        return filePath;
    }

    private String getExtensions(String fileName) {
        logger.info("Method getExtensions was invoked!");
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public Avatar findAvatar(Long studentId){
        logger.info("Method findAvatar was invoked!");
        return avatarRepository.findByStudent_Id(studentId).orElse(new Avatar());

    }

    @Override
    public List<AvatarDTO> getPaginatedAvatars(int pageNumber, int pageSize) {
        logger.info("Method getPaginatedAvatars was invoked!");
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        return avatarRepository.
                findAll(pageable)
                .getContent()
                .stream()
                .map(avatarMapper::mapToDTO)
                .collect(Collectors.toList());
    }

}
