package team.nine.booknutsbackend.domain.myStory;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import team.nine.booknutsbackend.domain.Board;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@Builder
public class SeriesBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long SeriesBoardId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name ="series")
    private Series series;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name ="board")
    private Board board;
}
