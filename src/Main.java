public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");


//        TaskManager inMemoryTaskManager = Managers.getDefault();

//        TaskManager inMemoryTaskManager = new InMemoryTaskManager(new InMemoryHistoryManager());


//        Task task = new Task("ЗАДАЧА 1", "Просто задача один", Status.NEW);
//        inMemoryTaskManager.createTask(task);
//        inMemoryTaskManager.getTask(1).setStartTime(LocalDateTime.of(2025, 1, 1, 10, 0));
//        inMemoryTaskManager.getTask(1).setDuration(Duration.ofMinutes(30));
//
//        Task task2 = new Task("ЗАДАЧА 2", "Просто задача два", Status.NEW);
//        inMemoryTaskManager.createTask(task2);
//        inMemoryTaskManager.getTask(2).setStartTime(LocalDateTime.of(2025, 1, 1, 3, 0));
//        inMemoryTaskManager.getTask(2).setDuration(Duration.ofMinutes(30));
//
//        Task task3 = new Task("ЗАДАЧА 3", "Просто задача три", Status.NEW);
//        inMemoryTaskManager.createTask(task3);
//        Task task4 = new Task("ЗАДАЧА 4-2", "Просто задача четыре-два", 2, Status.NEW);
//        inMemoryTaskManager.updateTask(task4);

//        Epic epic = new Epic("СБОРНАЯ ЗАДАЧА 1", "Сборная задача 1", 0);
//        inMemoryTaskManager.createEpic(epic);
//
//        Epic epic2 = new Epic("СБОРНАЯ ЗАДАЧА 2 ", "Сборная задача 2", 0);
//        inMemoryTaskManager.createEpic(epic2);
//        Epic epic3 = new Epic("СБОРНАЯ ЗАДАЧА 3", "Сборная задача 3", 0);
//        inMemoryTaskManager.createEpic(epic3);
//        Epic epic4 = new Epic("СБОРНАЯ ЗАДАЧА 4", "Сборная задача 4", 0);
//        inMemoryTaskManager.createEpic(epic4);
//
//        SubTask subTask = new SubTask("ПОДЗАДАЧА 1", "один",
//                0, Status.NEW, 6);
//        inMemoryTaskManager.createSubTask(subTask);
//        SubTask subTask2 = new SubTask("ПОДЗАДАЧА 2", "два",
//                0, Status.NEW, 6);
//        inMemoryTaskManager.createSubTask(subTask2);
//        SubTask subTask3 = new SubTask("ПОДЗАДАЧА 3", "три",
//                0, Status.NEW, 6);
//        inMemoryTaskManager.createSubTask(subTask3);
//        SubTask subTask4 = new SubTask("ПОДЗАДАЧА 4", "четыре",
//                0, Status.NEW, 7);
//        inMemoryTaskManager.createSubTask(subTask4);
//        SubTask subTask5 = new SubTask("ПОДЗАДАЧА 5", "пять",
//                0, Status.NEW, 7);
//        inMemoryTaskManager.createSubTask(subTask5);
//        SubTask subTask6 = new SubTask("ПОДЗАДАЧА 6", "шесть",
//                0, Status.NEW, 7);
//        inMemoryTaskManager.createSubTask(subTask6);
//        SubTask subTask7 = new SubTask("ПОДЗАДАЧА 7", "7",
//                0, Status.DONE, 7);
//        inMemoryTaskManager.createSubTask(subTask7);
//
//        System.out.println("Задачи");
//        System.out.println(inMemoryTaskManager.getAllTask());
//        System.out.println("Эпики");
//        System.out.println(inMemoryTaskManager.getAllEpic());
//        System.out.println("Подзадачи");
//        System.out.println(inMemoryTaskManager.getAllSubtask());
//
//        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>> Проверка сортировки <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
//        for (Task prioritizedTask : inMemoryTaskManager.getPrioritizedTasks()) {
//            System.out.print(prioritizedTask);
//            if (!(prioritizedTask.getStartTime() == null)) {
//                System.out.println(prioritizedTask.getStartTime());
//            } else {
//                System.out.println("  Время начала не установлено");
//            }
//        }
////        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>> Окончание проверки сортировки  сортировки <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
////
////
//        inMemoryTaskManager.deleteTaskById(1);
//        System.out.println("");
//        System.out.println(">>>>>     inMemoryTaskManager.deleteTaskById(1)     <<<<<");
//        System.out.println("Задачи");
//        System.out.println(inMemoryTaskManager.getAllTask());
//        System.out.println("Эпики");
//        System.out.println(inMemoryTaskManager.getAllEpic());
//        System.out.println("Подзадачи");
//        System.out.println(inMemoryTaskManager.getAllSubtask());
//
//        inMemoryTaskManager.deleteSubTaskById(13);
//        System.out.println("");
//        System.out.println(">>>>>      inMemoryTaskManager.deleteSubTaskById(13)     <<<<<");
//        System.out.println("Задачи");
//        System.out.println(inMemoryTaskManager.getAllTask());
//        System.out.println("Эпики");
//        System.out.println(inMemoryTaskManager.getAllEpic());
//        System.out.println("Подзадачи");
//        System.out.println(inMemoryTaskManager.getAllSubtask());
//
//        inMemoryTaskManager.deleteEpicById(6);
//        System.out.println("");
//        System.out.println(">>>>>      inMemoryTaskManager.deleteEpicById(6)     <<<<<");
//        System.out.println("Задачи");
//        System.out.println(inMemoryTaskManager.getAllTask());
//        System.out.println("Эпики");
//        System.out.println(inMemoryTaskManager.getAllEpic());
//        System.out.println("Подзадачи");
//        System.out.println(inMemoryTaskManager.getAllSubtask());
//
//
//        System.out.println("История просмотров.");
//        System.out.println("Размер списка просмотренных задач= " + inMemoryTaskManager.getHistory().size());
//        inMemoryTaskManager.getTask(2);
//        inMemoryTaskManager.getTask(3);
//        inMemoryTaskManager.getEpic(4);
//        inMemoryTaskManager.getEpic(5);
//        inMemoryTaskManager.getSubTask(11);
//        inMemoryTaskManager.getSubTask(12);
//        inMemoryTaskManager.getSubTask(14);
//        inMemoryTaskManager.getEpic(5);
//        inMemoryTaskManager.getSubTask(11);
//        inMemoryTaskManager.getSubTask(12);
//        inMemoryTaskManager.getSubTask(14);
//        inMemoryTaskManager.getTask(2);
////        inMemoryTaskManager.getTask(2);
////        inMemoryTaskManager.getEpic(4);
////        inMemoryTaskManager.getEpic(7);
//        System.out.println(inMemoryTaskManager.getHistory());
//
//        System.out.println("Проверка изменения время эпика");
//        System.out.println(inMemoryTaskManager.getEpic(7).getTitle());
//        inMemoryTaskManager.getSubTask(11).setStartTime(LocalDateTime.of(2024, 1, 1, 10, 0));
//        inMemoryTaskManager.getSubTask(11).setDuration(Duration.ofMinutes(315));
//        inMemoryTaskManager.getSubTask(12).setStartTime(LocalDateTime.of(2024, 1, 1, 12, 0));
//        inMemoryTaskManager.getSubTask(12).setDuration(Duration.ofMinutes(45));
//        inMemoryTaskManager.getSubTask(14).setStartTime(LocalDateTime.of(2024, 1, 1, 14, 0));
//        inMemoryTaskManager.getSubTask(14).setDuration(Duration.ofMinutes(10));
//        inMemoryTaskManager.getSubTask(11).setEndTime();
//        inMemoryTaskManager.getSubTask(12).setEndTime();
//        inMemoryTaskManager.getSubTask(14).setEndTime();
//        for (SubTask st : inMemoryTaskManager.getAllSubtask()) {
//            System.out.println(st.getId());
//            System.out.println(st.getStartTime());
//            System.out.println(st.getDuration());
//            System.out.println(st.getEndTime());
//            System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - -");
//        }
//        System.out.println(inMemoryTaskManager.getEpic(7));
//        inMemoryTaskManager.updateEpicTime(inMemoryTaskManager.getEpic(7));
//        System.out.println("Первая задача начнется в  " + inMemoryTaskManager.getEpic(7).getStartTime());
//        System.out.println("Все подзадачи будут завершены к " + inMemoryTaskManager.getEpic(7).getEndTime());
//        System.out.println("На выполнение подзадач будет потрачено суммарно " + inMemoryTaskManager.getEpic(7).getDuration());
    }
}
