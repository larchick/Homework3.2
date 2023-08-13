package ru.hogwarts.school.service;

import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.dto.AvatarDTO;
import ru.hogwarts.school.model.Avatar;

import java.io.IOException;
import java.util.List;

public interface AvatarService{
    void uploadAvatar(Long studentID, MultipartFile avatarFile) throws IOException;

    Avatar findAvatar(Long studentId);

    List<AvatarDTO> getPaginatedAvatars(int pageNumber, int pageSize);
}
