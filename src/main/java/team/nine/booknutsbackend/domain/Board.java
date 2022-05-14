package team.nine.booknutsbackend.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(nullable = false)
    private Boolean deleteCheck = false; //true : 삭제됨

    @Column(length = 100, nullable = false)
    private String bookTitle;

    @Column(length = 100, nullable = false)
    private String bookImgUrl;

    @Column(length = 100, nullable = false)
    private int bookGenre; //0 : 소설, ..

    @ManyToOne
    @JoinColumn(name ="writer")
    private User user;
}
