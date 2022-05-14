package team.nine.booknutsbackend.domain.reaction;

import lombok.Getter;
import lombok.Setter;
import team.nine.booknutsbackend.domain.Board;
import team.nine.booknutsbackend.domain.User;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Nuts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long nutsId;

    @ManyToOne
    @JoinColumn(name ="user")
    private User user;

    @ManyToOne
    @JoinColumn(name ="board")
    private Board board;
}
