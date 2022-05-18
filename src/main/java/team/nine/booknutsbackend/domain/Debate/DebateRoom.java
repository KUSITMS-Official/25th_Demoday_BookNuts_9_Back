package team.nine.booknutsbackend.domain.Debate;

import lombok.Getter;
import lombok.Setter;
import team.nine.booknutsbackend.domain.User;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class DebateRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long debateRoomId;

    @Column(length = 100, nullable = false)
    private String bookTitle;

    @Column(length = 100, nullable = false)
    private String bookAuthor;

    @Column(length = 300, nullable = false)
    private String bookImgUrl;

    @Column(length = 100, nullable = false)
    private String bookGenre;

    @Column(length = 100, nullable = false)
    private String topic;

    @Column(length = 300, nullable = false)
    private String coverImgUrl;

    @Column(length = 100, nullable = false)
    private int type; //0 : 채팅, 1 : 음성

    @Column(length = 100, nullable = false)
    private int maxUser; //총 토론 참여자 수 (2,4,6,8)

    @Column(length = 100, nullable = false)
    private int curYesUser; //현재 참여한 찬성 유저 수

    @Column(length = 100, nullable = false)
    private int curNoUser; //현재 참여한 반대 유저 수

    @Column(length = 100, nullable = false)
    private int status = 0; //0 : 토론 대기 중, 1 : 토론 진행 중, 2 : 토론 종료

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name ="owner")
    private User owner;
}
