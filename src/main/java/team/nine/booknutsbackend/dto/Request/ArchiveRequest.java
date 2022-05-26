package team.nine.booknutsbackend.dto.Request;

import lombok.Builder;
import lombok.Getter;
import team.nine.booknutsbackend.domain.User;
import team.nine.booknutsbackend.domain.archive.Archive;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
public class ArchiveRequest {

    @NotBlank String title;
    @NotBlank String content;
    @NotBlank String imgUrl;

    public static Archive newArchive(ArchiveRequest archiveRequest, User user) {
        Archive archive = new Archive();
        archive.setTitle(archiveRequest.getTitle());
        archive.setContent(archiveRequest.getContent());
        archive.setImgUrl(archiveRequest.getImgUrl());
        archive.setOwner(user);

        return archive;
    }

}
