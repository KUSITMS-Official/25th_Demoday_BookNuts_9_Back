package team.nine.booknutsbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.nine.booknutsbackend.domain.Series.Series;
import team.nine.booknutsbackend.domain.User;

import java.util.List;
import java.util.Optional;

public interface SeriesRepository extends JpaRepository<Series, Long> {
    List<Series> findAllByOwner(User user);
    Optional<Series> findBySeriesIdAndOwner(Long seriesId, User user);
}
