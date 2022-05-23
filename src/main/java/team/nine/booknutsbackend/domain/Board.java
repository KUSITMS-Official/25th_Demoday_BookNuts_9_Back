package team.nine.booknutsbackend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import team.nine.booknutsbackend.domain.archive.ArchiveBoard;
import team.nine.booknutsbackend.domain.reaction.Heart;
import team.nine.booknutsbackend.domain.reaction.Nuts;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    private String createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    @Column(length = 100, nullable = false)
    private String bookTitle;

    @Column(length = 100, nullable = false)
    private String bookAuthor;

    @Column(length = 300, nullable = false)
    private String bookImgUrl;

    @Column(length = 100, nullable = false)
    private String bookGenre;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "writer")
    private User user;

    @OneToMany(mappedBy = "board")
    @JsonIgnore
    private List<Nuts> nutsList = new ArrayList<>();

    @OneToMany(mappedBy = "board")
    @JsonIgnore
    private List<Heart> hearts = new ArrayList<>();

    @OneToMany(mappedBy = "board")
    @JsonIgnore
    private List<ArchiveBoard> archiveBoards = new ArrayList<>();

}
