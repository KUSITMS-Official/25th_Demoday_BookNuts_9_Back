package team.nine.booknutsbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.nine.booknutsbackend.domain.User;
import team.nine.booknutsbackend.domain.myStory.MyStory;

import java.util.List;

public interface MyStoryRepository extends JpaRepository<MyStory, Long> {

    List<MyStory> findAllByUser(User user);
}
