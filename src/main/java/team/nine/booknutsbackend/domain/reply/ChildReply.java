package team.nine.booknutsbackend.domain.reply;

import lombok.Getter;
import lombok.Setter;
import team.nine.booknutsbackend.domain.Board;
import team.nine.booknutsbackend.domain.User;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(nullable = false)
    private Boolean deleteCheck = false; //true : 삭제됨

    @ManyToOne
    @JoinColumn(name ="writer")
    private User user;

    @ManyToOne
    @JoinColumn(name="parentReply")
    private ParentReply parentReply;

    @ManyToOne
    @JoinColumn(name ="board")
    private Board board;
}
