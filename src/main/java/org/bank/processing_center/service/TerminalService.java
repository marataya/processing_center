package org.bank.processing_center.service;

import org.bank.processing_center.dao.Dao;
import org.bank.processing_center.model.Terminal;

import java.util.List;

public class TerminalService implements Service<Terminal, Long> {

    private final Dao<Terminal, Long> terminalDao;

    public TerminalService(Dao<Terminal, Long> dao) {
        this.terminalDao = dao;
    }

    @Override
    public void createTable() {
        terminalDao.createTable();
    }

    @Override
    public void dropTable() {
        terminalDao.dropTable();
    }

    @Override
    public void clearTable() {
        terminalDao.clearTable();
    }

    @Override
    public Terminal save(Terminal entity) {
        return terminalDao.save(entity);
    }

    @Override
    public void delete(Long id) {
        terminalDao.delete(id);
    }

    @Override
    public List<Terminal> findAll() {
        return terminalDao.findAll();
    }

    @Override
    public Terminal findById(Long id) {
        return terminalDao.findById(id);
    }

    @Override
    public Terminal update(Terminal terminal) {
        return terminalDao.update(terminal);
    }
}