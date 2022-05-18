package team.nine.booknutsbackend.dto.Response;

import lombok.Builder;
import lombok.Getter;
import team.nine.booknutsbackend.domain.User;
import team.nine.booknutsbackend.domain.myStory.Series;

@Getter
@Builder
public class SeriesResponse {
    Long seriesId;
    String title;
    String content;
    String imgUrl;

    public static SeriesResponse myStoryResponse(Series series, User user){
        return SeriesResponse.builder()
                .seriesId(series.getSeriesId())
                .title(series.getTitle())
                .content(series.getContent())
                .imgUrl(series.getImgUrl())
                .build();
    }
}
