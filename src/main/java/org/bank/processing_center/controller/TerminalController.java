package org.bank.processing_center.controller;

import org.bank.processing_center.model.Terminal;
import org.bank.processing_center.service.TerminalService;
import org.bank.processing_center.view.ConsoleView;

import java.util.List;
import java.util.Optional;

public class TerminalController implements Controller<Terminal, Long> {

    private final TerminalService terminalService;
    private final ConsoleView view;

    public TerminalController(TerminalService terminalService, ConsoleView view) {
        this.terminalService = terminalService;
        this.view = view;
    }

    @Override
    public void createTable() {
        view.showMessage("Creating Terminal table...");
        terminalService.createTable();
        view.showMessage("Terminal table created.");
    }

    @Override
    public void dropTable() {
        view.showMessage("Dropping Terminal table...");
        terminalService.dropTable();
        view.showMessage("Terminal table dropped.");
    }

    @Override
    public void clearTable() {
        view.showMessage("Clearing Terminal table...");
        terminalService.clearTable();
        view.showMessage("Terminal table cleared.");
    }

    @Override
    public void addEntity(Terminal terminal) {
        view.showMessage("Adding Terminal: " + terminal);
        terminalService.save(terminal);
        view.showMessage("Terminal added successfully.");
    }

    @Override
    public void deleteEntity(Long id) {
        view.showMessage("Deleting Terminal with ID: " + id);
        terminalService.delete(id);
        view.showMessage("Terminal deleted successfully.");
    }

    @Override
    public List<Terminal> getAllEntities() {
        try {
            List<Terminal> terminals = terminalService.findAll();
            view.showList(terminals, "Terminals List:");
            return terminals;
        } catch (Exception e) {
            view.showError("Ошибка при получении списка terminals: " + e.getMessage());
            return List.of();
        }
    }

    @Override
    public Optional<Terminal> findById(Long id) {
        view.showMessage("Finding Terminal with ID: " + id);
        Optional<Terminal> terminal = terminalService.findById(id);
        if (terminal.isPresent()) {
            view.showMessage("Found Terminal: " + terminal.get().toString());
        } else {
            view.showMessage("Terminal with ID " + id + " not found.");
        }
        return terminal;
    }

    @Override
    public void updateEntity(Terminal terminal) {
        try {
            terminalService.update(terminal);
            view.showMessage("Terminal updated: " + terminal);
        } catch (Exception e) {
            view.showError("Error updating terminal: " + e.getMessage());
        }
    }

}