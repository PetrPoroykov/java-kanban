import manager.InMemoryHistoryManager;
import manager.InMemoryTaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    @BeforeEach
    public void createManager() {
        taskManager = new InMemoryTaskManager();
        taskManager.inMemoryHistoryManager = new InMemoryHistoryManager();
    }


    @DisplayName("Создание новой задачи")
    @Test
    public void shouldCreateTask() {
        super.shouldCreateTask();
    }

    @DisplayName("Обновление существующей задачи по валидному ID")
    @Test
    public void shouldUpdateTask() {
        super.shouldUpdateTask();
    }

    @DisplayName("Создание новой  сборной задачи")
    @Test
    public void shouldCreateEpic() {
        super.shouldCreateEpic();
    }

    @DisplayName("Обновление существующей сборной задачи по валидному ID")
    @Test
    public void shouldUpdataEpic() {
        super.shouldUpdataEpic();
    }

    @DisplayName("Создание новой  подзадачи")
    @Test
    public void shouldCreateSubTask() {
        super.shouldCreateSubTask();
    }

    @DisplayName("Обновление существующей  подзадачи по валидному ID")
    @Test
    public void shouldUpdataSubTask() {
        super.shouldUpdataSubTask();
    }

    @DisplayName("Обновление cтатуса эпика")
    @Test
    public void shouldUpdateEpicStatus() {
        super.shouldUpdateEpicStatus();
    }

    @DisplayName("Обновление времени эпика")
    @Test
    public void shouldUpdateEpicTime() {
        super.shouldUpdateEpicTime();
    }

    @DisplayName("Должно выбрасываться ValidationException при пересечении по времени")
    @Test
    public void exceptionShouldBeThrownAtTimeIntersection() {
        super.exceptionShouldBeThrownAtTimeIntersection();
    }

    @DisplayName("Задачи и подзадачи должны быть отсортированы по времени")
    @Test
    public void shouldGetPrioritizedTasks() {
        super.shouldGetPrioritizedTasks();
    }

    @DisplayName("Просмотренные задачи должны помещаться в историю просмотров")
    @Test
    public void viewedStoriesShouldFormBrowsingHistory() {
        super.viewedStoriesShouldFormBrowsingHistory();
    }
}