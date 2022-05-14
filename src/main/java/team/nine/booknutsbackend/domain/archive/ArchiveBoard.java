package team.nine.booknutsbackend.domain.archive;

import lombok.Getter;
import lombok.Setter;
import team.nine.booknutsbackend.domain.Board;
import team.nine.booknutsbackend.domain.User;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class ArchiveBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long archiveBoardId;

    @ManyToOne
    @JoinColumn(name ="user")
    private User user;

    @ManyToOne
    @JoinColumn(name ="archive")
    private Archive archive;

    @ManyToOne
    @JoinColumn(name ="board")
    private Board board;
}
