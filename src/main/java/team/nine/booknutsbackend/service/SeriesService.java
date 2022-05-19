package team.nine.booknutsbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.nine.booknutsbackend.domain.Series.Series;
import team.nine.booknutsbackend.domain.Series.SeriesBoard;
import team.nine.booknutsbackend.domain.User;
import team.nine.booknutsbackend.dto.Request.SeriesRequest;
import team.nine.booknutsbackend.dto.Response.BoardResponse;
import team.nine.booknutsbackend.dto.Response.SeriesResponse;
import team.nine.booknutsbackend.exception.series.SeriesNotFoundException;
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
        List<SeriesResponse> seriesResponseList = new ArrayList<>();

        for (Series series : stories) {
            seriesResponseList.add(SeriesResponse.myStoryResponse(series, user));
        }
        return seriesResponseList;
    }

    //새로운 시리즈 발행
    public void saveSeries(SeriesRequest seriesRequest, User user) {
        List<Long> boardIdlist = seriesRequest.getBoardIdlist();
        Series series = SeriesRequest.newSeries(seriesRequest, user);
        seriesRepository.save(series);

        for (Long boardId : boardIdlist) {
            SeriesBoard seriesBoard = new SeriesBoard();
            seriesBoard.setSeries(series);
            seriesBoard.setBoard(boardService.findBoard(boardId));
            seriesBoardRepository.save(seriesBoard);
        }
    }

    //특정 시리즈 조회
    @Transactional(readOnly = true)
    public List<BoardResponse> findSeries(Long seriesId, User user) throws SeriesNotFoundException {
        Series series = seriesRepository.findById(seriesId)
                .orElseThrow(() -> new SeriesNotFoundException("존재하지 않는 시리즈 아이디입니다."));
        List<SeriesBoard> seriesBoards = seriesBoardRepository.findBySeries(series);
        List<BoardResponse> boardList = new ArrayList<>();

        for (SeriesBoard seriesBoard : seriesBoards) {
            boardList.add(BoardResponse.boardResponse(seriesBoard.getBoard(), user));
        }
        return boardList;
    }
}
