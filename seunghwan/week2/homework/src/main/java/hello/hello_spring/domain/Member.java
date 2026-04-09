package hello.hello_spring.domain;

public class Member {
    private long id; // 고객이 정하는 값이 아니라 시스템이 정하는 값
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
