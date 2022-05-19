package team.nine.booknutsbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.nine.booknutsbackend.domain.Series.Series;
import team.nine.booknutsbackend.domain.Series.SeriesBoard;

import java.util.List;

public interface SeriesBoardRepository extends JpaRepository<SeriesBoard, Long> {

    //시리즈 번호로 시리즈 조회
    List<SeriesBoard> findBySeries(Series series);
}
