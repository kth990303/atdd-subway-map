package wooteco.subway.domain;

public class Station {
    private Long id;
    private String name;

    public Station() {
    }

    public Station(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Station(String name) {
        this.name = name;
    }

    public boolean isSameName(Station station) {
        return name.equals(station.getName());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

