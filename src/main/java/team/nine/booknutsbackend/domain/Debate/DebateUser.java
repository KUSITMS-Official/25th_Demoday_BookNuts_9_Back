package team.nine.booknutsbackend.domain.Debate;

import lombok.Getter;
import lombok.Setter;
import team.nine.booknutsbackend.domain.User;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class DebateUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long debateUserId;

    @Column(nullable = false)
    private Boolean opinion;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "debateRoom")
    private DebateRoom debateRoom;

}
