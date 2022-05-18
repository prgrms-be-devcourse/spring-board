package testutil;

import static org.assertj.core.api.Assertions.assertThat;

import com.programmers.epicblues.jpa_board.entity.LongIdHolder;
import com.programmers.epicblues.jpa_board.entity.User;
import java.lang.reflect.Field;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

// 테스트 전용 id 변경 인터페이스
public class FieldModifier {

  private FieldModifier() throws NoSuchMethodException {
    throw new NoSuchMethodException();
  }

  public static void assignId(LongIdHolder idHolder, Long newId)
      throws NoSuchFieldException, IllegalAccessException {
    Field idField = idHolder.getClass().getDeclaredField("id");
    idField.setAccessible(true);
    idField.set(idHolder, newId);
    idField.setAccessible(false);
  }

  @Test
  @DisplayName("Long 타입의 private id 필드가 있는 객체의 id를 수정할 수 있어야 한다.")
  void assign_id_test() throws NoSuchFieldException, IllegalAccessException {

    // Given
    User user = EntityFixture.getUser();
    Long newId = 1L;

    // When
    assertThat(user.getId()).isNull();
    FieldModifier.assignId(user, newId);

    // Then
    assertThat(user.getId()).isEqualTo(newId);
  }

}
