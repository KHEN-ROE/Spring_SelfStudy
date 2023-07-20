package study.datajpa.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "username", "age"}) // 연관관계 필드는 ToString 안 하는게 좋다. 무한루프 빠질 수 있다
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String username;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY) // 회원은 하나의 팀만 가진다.
    @JoinColumn(name = "team_id")
    private Team team;

    // 아무 곳에서 호출하지 못하도록 protected로 설정
    // jpa 표준 스펙에 명시돼있음
    // private으로 하면 프록시 기술을 사용할 수 없어서 등등의 이유로...
//    protected Member() {
//    }

    public Member(String username) {
        this.username = username;
    }

    public Member(String username, int age, Team team) {
        this.username = username;
        this.age = age;
        if (team != null) {
            changeTeam(team);
        }
    }

    // 세터 대신 수정 메서드 사용하는게 좋다
    public void changeUsername(String username) {
        this.username = username;
    }

    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this); // Team 엔티티에도 설정해야함.
    }
}
