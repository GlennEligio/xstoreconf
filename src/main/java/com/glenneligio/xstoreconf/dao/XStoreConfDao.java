package com.glenneligio.xstoreconf.dao;

import com.glenneligio.xstoreconf.model.XStoreConf;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface XStoreConfDao {
    List<XStoreConf> getAll();
}
