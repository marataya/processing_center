package org.bank.processing_center.dao;

import java.util.List;

public interface Dao<T, ID> {
    // Создание таблицы для сущности.  Не должно приводить к исключению, если такая таблица уже существует.
    void createTable();

    // Удаление таблицы. Не должно приводить к исключению, если таблицы не существует.
    void dropTable();

    // Очистка содержимого таблицы
    void clearTable();

    // Добавление объекта соответствующей модели в таблицу
    void save(T t);

    // Удаление объекта из таблицы (по id)
    void delete(ID id);

    // Получение всех объектов из таблицы
    List<T> findAll();

    // Получение объекта по id.
    T findById(ID id);

    // Обновление существующей записи
    void update(T t);
}
