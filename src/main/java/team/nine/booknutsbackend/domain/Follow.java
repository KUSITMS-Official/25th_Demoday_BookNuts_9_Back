package team.nine.booknutsbackend.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long followId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name ="following")
    private User following;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name ="follower")
    private User follower;
}
