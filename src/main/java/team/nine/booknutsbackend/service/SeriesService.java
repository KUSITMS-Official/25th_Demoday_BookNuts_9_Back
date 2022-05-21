package team.nine.booknutsbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.nine.booknutsbackend.domain.Board;
import team.nine.booknutsbackend.domain.Series.Series;
import team.nine.booknutsbackend.domain.Series.SeriesBoard;
import team.nine.booknutsbackend.domain.User;
import team.nine.booknutsbackend.dto.Request.SeriesRequest;
import team.nine.booknutsbackend.dto.Response.BoardResponse;
import team.nine.booknutsbackend.dto.Response.SeriesResponse;
import team.nine.booknutsbackend.exception.archive.ArchiveDuplicateException;
import team.nine.booknutsbackend.exception.board.BoardNotFoundException;
import team.nine.booknutsbackend.exception.board.NoAccessException;
import team.nine.booknutsbackend.exception.series.CheckMyboard;
import team.nine.booknutsbackend.exception.series.SeriesDuplicateException;
import team.nine.booknutsbackend.exception.series.SeriesNotFoundException;
import team.nine.booknutsbackend.repository.BoardRepository;
import team.nine.booknutsbackend.repository.SeriesBoardRepository;
import team.nine.booknutsbackend.repository.SeriesRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SeriesService {

    private final SeriesRepository seriesRepository;
    private final SeriesBoardRepository seriesBoardRepository;
    private final BoardRepository boardRepository;
    private final BoardService boardService;

    //내 시리즈 조회
    @Transactional(readOnly = true)
    public List<SeriesResponse> allMySeries(User user) {
        List<Series> stories = seriesRepository.findAllByOwner(user);
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

    //시리즈 삭제
    @Transactional
    public void delete(Long seriesId, User user) throws NoAccessException {
        Series series = seriesRepository.findBySeriesIdAndOwner(seriesId, user)
                .orElseThrow(() -> new NoAccessException("해당 유저는 삭제 권한이 없습니다."));
        List<SeriesBoard> seriesBoards = seriesBoardRepository.findBySeries(series);

        seriesBoardRepository.deleteAll(seriesBoards);
        seriesRepository.delete(series);
    }

    //시리즈에 게시글 추가
    public void addToSeries(Long seriesId, Long boardId) {
        Series series = seriesRepository.findById(seriesId)
                .orElseThrow(() -> new SeriesNotFoundException("존재하지 않는 아카이브 아이디입니다."));
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("존재하지 않는 게시글 번호입니다."));

        //시리즈 중복체크
        if(seriesBoardRepository.findByBoardAndSeries(board, series).isPresent()) throw new SeriesDuplicateException("이미 시리즈에 게시글이 존재합니다.");

        SeriesBoard seriesBoard = new SeriesBoard();
        seriesBoard.setSeries(series);
        seriesBoard.setBoard(board);
        series.setOwner(series.getOwner());
        seriesBoardRepository.save(seriesBoard);
    }
}
