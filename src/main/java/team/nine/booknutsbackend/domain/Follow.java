package team.nine.booknutsbackend.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long followId;

    @ManyToOne
    @JoinColumn(name ="following")
    private User following;

    @ManyToOne
    @JoinColumn(name ="follower")
    private User follower;
}
