package com.aud.bll;

import java.util.List;
import com.aud.dal.IRootDAO;
import com.aud.dal.RootDAO;
import com.aud.dtos.RootDTO;


public class RootBO implements IRootBO {
    private IRootDAO rootDAO;

    public RootBO(IRootDAO rootDAO) {
        this.rootDAO = rootDAO;
    }

    @Override
    public void addRoot(RootDTO root) throws Exception {
        if (root == null || root.getRootLetters() == null || root.getRootLetters().trim().isEmpty()) {
            throw new IllegalArgumentException("Root letters cannot be null or empty");
        }
        rootDAO.addRoot(root);
    }

    @Override
    public RootDTO getRootById(int id) throws Exception {
        return rootDAO.getRootById(id);
    }

    @Override
    public void updateRoot(RootDTO root) throws Exception {
        if (root == null || root.getId() <= 0) {
            throw new IllegalArgumentException("Invalid root data");
        }
        rootDAO.updateRoot(root);
    }

    @Override
    public void deleteRoot(int id) throws Exception {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid ID");
        }
        rootDAO.deleteRoot(id);
    }

    @Override
    public List<RootDTO> getAllRoots() throws Exception {
        return rootDAO.getAllRoots();
    }
}
