package wooteco.subway.line;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.springframework.util.ReflectionUtils;
import wooteco.subway.exception.DuplicatedLineNameException;

public class LineDao {

    private static Long seq = 0L;
    private static List<Line> lines = new ArrayList<>();

    public static Line save(Line line) {
        validateDuplicate(line);
        Line persistLine = createNewObject(line);
        lines.add(persistLine);
        return persistLine;
    }

    private static void validateDuplicate(Line line) {
        if (lines.stream()
            .map(Line::getName)
            .anyMatch(name -> name.equals(line.getName()))) {
            throw new DuplicatedLineNameException("[ERROR] 노선의 이름이 중복됩니다.");
        }
        if (lines.stream()
            .map(Line::getColor)
            .anyMatch(color -> color.equals(line.getColor()))) {
            throw new DuplicatedLineNameException("[ERROR] 노선의 색이 중복됩니다.");
        }
    }

    public static List<Line> findAll() {
        return lines;
    }

    private static Line createNewObject(Line line) {
        Field field = ReflectionUtils.findField(Line.class, "id");
        field.setAccessible(true);
        ReflectionUtils.setField(field, line, ++seq);
        return line;
    }
}
