package team.nine.booknutsbackend.domain.reaction;

import lombok.Getter;
import lombok.Setter;
import team.nine.booknutsbackend.domain.Board;
import team.nine.booknutsbackend.domain.User;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class Heart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long heartId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name ="user")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name ="board")
    private Board board;
}
