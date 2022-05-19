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

    //스토리 그룹핑
    public void saveSeries(SeriesRequest seriesRequest, User user) {
        List<Long> boardIdlist = seriesRequest.getBoardIdlist();
        Series series = SeriesRequest.newSeries(seriesRequest, user);
        seriesRepository.save(series);
//        Series series=seriesRepository.save(Series.builder()
//                            .title(seriesRequest.getTitle())
//                            .content(seriesRequest.getContent())
//                            .imgUrl(seriesRequest.getImgUrl())
//                            .user(user).build());

        for (Long boardId : boardIdlist) {
            SeriesBoard seriesBoard = new SeriesBoard();
            seriesBoard.setSeries(series);
            seriesBoard.setBoard(boardService.findBoard(boardId));
            seriesBoardRepository.save(seriesBoard);
//            seriesBoardRepository.save(SeriesBoard.builder()
//                    .series(series)
//                    .board(boardService.findBoard(boardId)).build());
        }
    }

    //특정 스토리 조회
//    @Transactional(readOnly = true)
//    public List<SeriesBoard> find(Long mystoryId) throws SeriesNotFoundException {
//        return seriesBoardRepository.findAllByMyStoryId(mystoryId);
//    }

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
        //List<Board> boards = boardRepository.findAll();
        //        List<BoardResponse> boardDtoList = new ArrayList<>();
        //
        //        for (Board board : boards) {
        //            boardDtoList.add(BoardResponse.boardResponse(board, user));
        //        }
        //
        //        Collections.reverse(boardDtoList); //최신순
        //        return boardDtoList;
    }
}
