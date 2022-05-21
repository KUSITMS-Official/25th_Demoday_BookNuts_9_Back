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

    @Column(length = 100, nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name ="owner")
    private User owner;

    @Column(length = 300, nullable = false)
    private String imgUrl;

}
