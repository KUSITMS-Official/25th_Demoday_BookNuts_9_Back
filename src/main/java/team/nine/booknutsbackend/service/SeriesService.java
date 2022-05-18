package team.nine.booknutsbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.nine.booknutsbackend.domain.User;
import team.nine.booknutsbackend.domain.myStory.Series;
import team.nine.booknutsbackend.domain.myStory.SeriesBoard;
import team.nine.booknutsbackend.dto.Request.SeriesRequest;
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
    private final BoardService boardService;

    //내 시리즈 조회
    @Transactional(readOnly = true)
    public List<SeriesResponse> allMySeries(User user) {
        List<Series> stories = seriesRepository.findAllByUser(user);
        List<SeriesResponse> seriesResponseList =new ArrayList<>();

        for(Series series : stories){
            seriesResponseList.add(SeriesResponse.myStoryResponse(series,user));
        }
        return seriesResponseList;
    }

    //스토리 그룹핑
    public void saveSeries(SeriesRequest seriesRequest, User user) {
        List<Long> boardIdlist=seriesRequest.getBoardIdlist();

        Series series=seriesRepository.save(Series.builder()
                            .title(seriesRequest.getTitle())
                            .content(seriesRequest.getContent())
                            .imgUrl(seriesRequest.getImgUrl())
                            .user(user).build());

        for(Long boardid : boardIdlist){
            seriesBoardRepository.save(SeriesBoard.builder()
                    .series(series)
                    .board(boardService.findBoard(boardid)).build());
        }
    }

    //특정 스토리 조회
//    @Transactional(readOnly = true)
//    public List<SeriesBoard> find(Long mystoryId) throws SeriesNotFoundException {
//        return seriesBoardRepository.findAllByMyStoryId(mystoryId);
//    }
}
