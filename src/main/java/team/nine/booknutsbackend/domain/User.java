package team.nine.booknutsbackend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import team.nine.booknutsbackend.domain.archive.ArchiveBoard;
import team.nine.booknutsbackend.domain.reaction.Heart;
import team.nine.booknutsbackend.domain.reaction.Nuts;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@JsonIgnoreProperties(value = {"password", "enabled", "authorities", "accountNonLocked", "accountNonExpired", "credentialsNonExpired"})
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(length = 100, nullable = false, unique = true)
    private String loginId;

    @Column(length = 300, nullable = false)
    private String password;

    @Column(length = 100, nullable = false)
    private String username;

    @Column(length = 100, nullable = false, unique = true)
    private String nickname;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(length = 300)
    private String accessToken = "";

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @OneToMany(mappedBy = "owner")
    @JsonIgnore
    private List<ArchiveBoard> archiveBoards = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Heart> hearts = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Nuts> nutsList = new ArrayList<>();

    @OneToMany(mappedBy = "follower")
    @JsonIgnore
    private List<Follow> followers = new ArrayList<>();

    @OneToMany(mappedBy = "following")
    @JsonIgnore
    private List<Follow> followings = new ArrayList<>();

    @Override
    public String getUsername() {
        return email;
    } // userPk -> email

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}