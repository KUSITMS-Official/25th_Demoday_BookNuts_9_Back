package team.nine.booknutsbackend.domain.reply;

import lombok.Getter;
import lombok.Setter;
import team.nine.booknutsbackend.domain.Board;
import team.nine.booknutsbackend.domain.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class ChildReply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long childReplyId;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int depth = 2; //자식 댓글은 무조건 depth = 2

    @Column(nullable = false)
    private String createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    @Column(nullable = false)
    private Boolean deleteCheck = false; //true : 삭제됨

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "writer")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parentReply")
    private ParentReply parentReply;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board")
    private Board board;

}
