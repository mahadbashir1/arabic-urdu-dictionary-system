package com.aud.dal;

import java.util.List;
import com.aud.dtos.RootDTO;

public interface IRootDAO {
    void addRoot(RootDTO root) throws Exception;
    RootDTO getRootById(int id) throws Exception;
    void updateRoot(RootDTO root) throws Exception;
    void deleteRoot(int id) throws Exception;
    List<RootDTO> getAllRoots() throws Exception;
}
