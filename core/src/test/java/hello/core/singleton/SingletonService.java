package hello.core.singleton;

public class SingletonService {

    // static 영역에 자기 자신 객체를 1개만 생성
    private static final SingletonService instance = new SingletonService();

    // 조회할 때 사용. 위 인스턴스의 참조를 꺼낼 수 있는 방법은 이 메서드밖에 없다. 그리고 이제 SingletonService를 생성할 수 있는 곳은 없다.
    public static SingletonService getInstance() {
        return instance;
    }

    // 생성자를 private으로 선언해서 외부에서 new 키워드를 이용한 객체 생성 방지
    private SingletonService() {
    }

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }

}
