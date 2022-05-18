package team.nine.booknutsbackend.dto.Request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import team.nine.booknutsbackend.domain.User;
import team.nine.booknutsbackend.domain.myStory.Series;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Setter
@ToString
@Getter
public class SeriesRequest {
    @NotBlank String title;
    @NotBlank String content;
    @NotBlank String imgUrl;
    List<Long> boardIdlist;

//    public static Series newSeries(SeriesRequest seriesRequest, User user) {
//        Series series = new Series();
//        series.setTitle(seriesRequest.getTitle());
//        series.setContent(seriesRequest.getContent());
//        series.setImgUrl(seriesRequest.getImgUrl());
//        series.setUser(user);
//        return series;
//    }

}
