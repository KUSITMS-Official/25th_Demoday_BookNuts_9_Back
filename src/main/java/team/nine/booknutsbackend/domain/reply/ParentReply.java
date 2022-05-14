package team.nine.booknutsbackend.domain.reply;

import lombok.Getter;
import lombok.Setter;
import team.nine.booknutsbackend.domain.Board;
import team.nine.booknutsbackend.domain.User;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class ParentReply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long parentReplyId;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int depth = 1; //부모 댓글은 무조건 depth = 1

    @Column(nullable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(nullable = false)
    private Boolean deleteCheck = false; //true : 삭제됨

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name ="writer")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name ="board")
    private Board board;
}
