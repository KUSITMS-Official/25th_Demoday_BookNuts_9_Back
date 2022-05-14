package team.nine.booknutsbackend.domain.archive;

import lombok.Getter;
import lombok.Setter;
import team.nine.booknutsbackend.domain.User;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Archive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long archiveId;

    @Column(length = 100, nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name ="user")
    private User user;

    @Column(length = 100, nullable = false)
    private String imgUrl;

}
