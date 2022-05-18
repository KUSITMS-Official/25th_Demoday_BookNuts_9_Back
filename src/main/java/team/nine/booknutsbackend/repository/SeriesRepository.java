package team.nine.booknutsbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.nine.booknutsbackend.domain.User;
import team.nine.booknutsbackend.domain.myStory.Series;

import java.util.List;

public interface SeriesRepository extends JpaRepository<Series, Long> {

    List<Series> findAllByUser(User user);
}
