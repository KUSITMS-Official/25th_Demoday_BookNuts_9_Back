package team.nine.booknutsbackend.dto;

import lombok.Builder;
import lombok.Getter;
import team.nine.booknutsbackend.domain.User;
import team.nine.booknutsbackend.domain.myStory.MyStory;

@Getter
@Builder
public class MyStoryDto {
    Long storyId;
    String title;
    String content;
    String imgUrl;

    public static MyStoryDto myStoryResponse(MyStory myStory, User user){
        return MyStoryDto.builder()
                .storyId(myStory.getMyStoryId())
                .title(myStory.getTitle())
                .content(myStory.getContent())
                .imgUrl(myStory.getImgUrl())
                .build();
    }
}
