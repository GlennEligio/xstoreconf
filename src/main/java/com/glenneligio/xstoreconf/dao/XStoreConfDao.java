package com.glenneligio.xstoreconf.dao;

import com.glenneligio.xstoreconf.model.XStoreConf;

import java.util.List;
import java.util.Optional;

public class XStoreConfDao implements Dao<XStoreConf> {

    @Override
    public Optional<XStoreConf> get(long id) {
        return Optional.empty();
    }

    @Override
    public List<XStoreConf> getAll() {
        return null;
    }

    @Override
    public boolean save(XStoreConf entity) {
        return false;
    }

    @Override
    public boolean saveAll(List<XStoreConf> entities) {
        return false;
    }

    @Override
    public boolean deleteById(long id) {
        return false;
    }

    @Override
    public XStoreConf update(long id, XStoreConf newEntity) {
        return null;
    }
}
