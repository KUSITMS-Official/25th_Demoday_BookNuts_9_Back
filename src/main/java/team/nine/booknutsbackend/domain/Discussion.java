package team.nine.booknutsbackend.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class Discussion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long discussionId;

    @Column(length = 100, nullable = false)
    private String bookTitle;

    @Column(length = 100, nullable = false)
    private String bookImgUrl;

    @Column(length = 100, nullable = false)
    private int bookGenre; //0 : 소설, ..

    @Column(length = 100, nullable = false)
    private String topic;

    @Column(length = 100, nullable = false)
    private String coverImgUrl;

    @Column(length = 100, nullable = false)
    private int type; //0 : 채팅, 1 : 음성

    @Column(length = 100, nullable = false)
    private Long yesUser; //찬성 유저 수

    @Column(length = 100, nullable = false)
    private Long noUser; //반대 유저 수

    @Column(length = 100, nullable = false)
    private int status; //0 : 토론 대기 중, 1 : 토론 진행 중, 2 : 토론 종료

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name ="owner")
    private User user;
}
