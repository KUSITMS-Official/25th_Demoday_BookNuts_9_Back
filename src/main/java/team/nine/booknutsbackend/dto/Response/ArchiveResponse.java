package team.nine.booknutsbackend.dto.Response;

import lombok.Builder;
import lombok.Getter;
import team.nine.booknutsbackend.domain.User;
import team.nine.booknutsbackend.domain.archive.Archive;

@Getter
@Builder
public class ArchiveResponse {
    Long archiveId;
    String title;
    String content;
    String imgUrl;

    public static ArchiveResponse archiveResponse(Archive archive){
        return ArchiveResponse.builder()
                .archiveId(archive.getArchiveId())
                .title(archive.getTitle())
                .content(archive.getContent())
                .imgUrl(archive.getImgUrl())
                .build();
    }
}
