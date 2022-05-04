package wooteco.subway.dao;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import wooteco.subway.domain.Line;

@JdbcTest
public class JdbcLineDaoTest {
    private JdbcLineDao lineDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        lineDao = new JdbcLineDao(jdbcTemplate);

        jdbcTemplate.execute("create table if not exists LINE(\n"
                + "    id bigint auto_increment not null,\n"
                + "    name varchar(255) not null unique,\n"
                + "    color varchar(20) not null,\n"
                + "    primary key(id)\n"
                + ");");

        final Line line1 = new Line("분당선", "bg-red-600");
        final Line line2 = new Line("신분당선", "bg-orange-600");
        lineDao.save(line1);
        lineDao.save(line2);
    }

    @Test
    @DisplayName("지하철 노선을 저장한다.")
    void save() {
        final String sql = "select count(*) from LINE";
        final int expected = 2;

        final int actual = jdbcTemplate.queryForObject(sql, Integer.class);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("지하철 노선 목록을 조회한다.")
    void findAllLines() {
        final int expected = 2;

        final int actual = lineDao.findAll().size();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("지하철 단일 노선을 조회한다.")
    void findLine() {
        final Line expected = new Line("다른분당선", "bg-blue-600");
        final long lineId = lineDao.save(expected);

        final Line actual = lineDao.findById(lineId);

        assertThat(actual.getName()).isEqualTo(expected.getName());
    }

    @Test
    @DisplayName("지하철 노선을 수정한다.")
    void update() {
        final Line line3 = new Line("다른분당선", "bg-blue-600");
        final long lineId = lineDao.save(line3);
        final String expected = "또다른분당선";
        lineDao.updateById(lineId, expected, line3.getColor());

        final Line updatedLine = lineDao.findById(lineId);
        final String actual = updatedLine.getName();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("지하철 노선을 삭제한다.")
    void delete() {
        final Line line3 = new Line("다른분당선", "bg-blue-600");
        final long lineId = lineDao.save(line3);
        lineDao.deleteById(lineId);
        final int expected = 2;

        final int actual = lineDao.findAll().size();

        assertThat(actual).isEqualTo(expected);
    }
}