package team.nine.booknutsbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.nine.booknutsbackend.domain.User;
import team.nine.booknutsbackend.domain.Series.Series;
import team.nine.booknutsbackend.domain.archive.Archive;

import java.util.List;
import java.util.Optional;

public interface SeriesRepository extends JpaRepository<Series, Long> {
    List<Series> findAllByUser(User user);
    Optional<Series> findBySeriesIdAndUser(Long seriesId, User user);
}
