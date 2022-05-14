package team.nine.booknutsbackend.domain.myStory;

import lombok.Getter;
import lombok.Setter;
import team.nine.booknutsbackend.domain.Board;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class MyStoryBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long myStoryBoardId;

    @ManyToOne
    @JoinColumn(name ="myStory")
    private MyStory myStory;

    @ManyToOne
    @JoinColumn(name ="board")
    private Board board;
}
