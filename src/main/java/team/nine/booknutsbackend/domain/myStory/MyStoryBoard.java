package team.nine.booknutsbackend.domain.myStory;

import lombok.Getter;
import lombok.Setter;
import team.nine.booknutsbackend.domain.Board;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class MyStoryBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long myStoryBoardId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name ="myStory")
    private MyStory myStory;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name ="board")
    private Board board;
}
