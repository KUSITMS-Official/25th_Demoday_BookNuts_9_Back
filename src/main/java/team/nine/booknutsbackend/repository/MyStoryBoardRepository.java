package team.nine.booknutsbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.nine.booknutsbackend.domain.myStory.MyStoryBoard;

import java.util.List;

public interface MyStoryBoardRepository extends JpaRepository<MyStoryBoard, Long> {

    List<MyStoryBoard> findAllByMyStoryId(Long mystoryId);
}
