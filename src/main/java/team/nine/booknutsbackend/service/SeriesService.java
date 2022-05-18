package team.nine.booknutsbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.nine.booknutsbackend.domain.User;
import team.nine.booknutsbackend.domain.myStory.Series;
import team.nine.booknutsbackend.domain.myStory.SeriesBoard;
import team.nine.booknutsbackend.dto.Response.SeriesResponse;
import team.nine.booknutsbackend.exception.mystory.SeriesNotFoundException;
import team.nine.booknutsbackend.repository.SeriesBoardRepository;
import team.nine.booknutsbackend.repository.SeriesRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SeriesService {

    private final SeriesRepository seriesRepository;
    private final SeriesBoardRepository seriesBoardRepository;

    //내 시리즈 조회
    @Transactional(readOnly = true)
    public List<SeriesResponse> allMySeries(User user) {
        List<Series> stories = seriesRepository.findAllByUser(user);
        List<SeriesResponse> seriesResponseList =new ArrayList<>();

        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        for(Series series : stories){
            seriesResponseList.add(SeriesResponse.myStoryResponse(series,user));
        }
        return seriesResponseList;
    }

    //스토리 그룹핑
//    @Transactional(readOnly = true)
//    public void grouping(long[] boardlist) {
//        Series series;
//        SeriesBoard seriesBoard;
//
//        for(Long boardId: boardlist){
//
//        }
//
//        return;
//    }

    //특정 스토리 조회
//    @Transactional(readOnly = true)
//    public List<SeriesBoard> find(Long mystoryId) throws SeriesNotFoundException {
//        return seriesBoardRepository.findAllByMyStoryId(mystoryId);
//    }
}
