package ru.oop.task3;

/**
 * Транспорт
 */
public interface Vehicle extends Positioned {
    /**
     * Добраться до места назначения из текущей точки.
     * Если добраться до точки нельзя,
     * то останавливается в ближаейшем доступном месте от точки назначения.
     */
    void moveTo(Position destination);
}
