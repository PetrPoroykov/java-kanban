package manager;

import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    CustomLinkedList customLinkedList = new CustomLinkedList();

    HashMap<Integer, Node> listViewTasks = new HashMap<>();

    @Override
    public List<Task> getHistory() {
        return customLinkedList.getTasks();
    }

    @Override
    public void add(Task task) {
        if (listViewTasks.containsKey(task.getId())) {
            customLinkedList.removeNode(listViewTasks.get(task.getId()));
            Node node = customLinkedList.linkLast(task);
            listViewTasks.put(task.getId(), node);
        } else {
            Node node = customLinkedList.linkLast(task);
            listViewTasks.put(task.getId(), node);
        }
    }

    @Override
    public void removeView(int id) {
        if (listViewTasks.containsKey(id)) {
            customLinkedList.removeNode(listViewTasks.get(id));
        }
        listViewTasks.remove(id);
    }

    class CustomLinkedList<T> {

        Node<T> head;
        Node<T> tail;
        Node<T> curNode;
        int size = 0;

        public Node linkLast(T element) {
            final Node<T> oldTail = tail;
            final Node<T> newNode = new Node<>(oldTail, element, null);
            tail = newNode;
            if (oldTail == null)
                head = newNode;
            else
                oldTail.setNext(newNode);
            size++;
            return tail;
        }

        public List<T> getTasks() {
            List<T> nodeList = new ArrayList<>();
            curNode = head;
            while (curNode != null) {
                nodeList.add(curNode.getData());
                curNode = ((Node<T>) curNode).getNext();
            }
            return nodeList;
        }

        public void removeNode(Node<T> node) {
            Node<T> nextCur = node.getNext();
            Node<T> prevCur = node.getPrev();
            if ((nextCur != null) && (prevCur != null)) {
                nextCur.setPrev(prevCur);
                prevCur.setNext(nextCur);
                return;
            }
            if ((nextCur == null) && (prevCur == null)) {
                head = null;
                tail = null;
                return;
            }
            if (prevCur == null) {
                nextCur.setPrev(null);
                head = nextCur;
                return;
            }
            if (nextCur == null) {
                prevCur.setNext(null);
                tail = prevCur;
                return;
            }
            size--;
        }
    }
}
