package team.nine.booknutsbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.nine.booknutsbackend.domain.Board;
import team.nine.booknutsbackend.domain.Series.Series;
import team.nine.booknutsbackend.domain.Series.SeriesBoard;
import team.nine.booknutsbackend.domain.User;
import team.nine.booknutsbackend.domain.archive.ArchiveBoard;

import java.util.List;
import java.util.Optional;

public interface SeriesBoardRepository extends JpaRepository<SeriesBoard, Long> {

    //시리즈 번호로 시리즈 조회
    List<SeriesBoard> findBySeries(Series series);
    Optional<SeriesBoard> findByBoardAndSeries(Board board, Series series);
}
