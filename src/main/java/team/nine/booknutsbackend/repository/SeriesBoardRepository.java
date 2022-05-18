package team.nine.booknutsbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.nine.booknutsbackend.domain.myStory.SeriesBoard;

import java.util.List;

public interface SeriesBoardRepository extends JpaRepository<SeriesBoard, Long> {

    //List<SeriesBoard> findAllBySeriesId(Long mystoryId);
}
