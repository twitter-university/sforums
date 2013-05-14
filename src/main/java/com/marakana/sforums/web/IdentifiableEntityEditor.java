
package com.marakana.sforums.web;

import java.beans.PropertyEditorSupport;

import com.marakana.sforums.dao.IdentifiableEntityDao;
import com.marakana.sforums.domain.IdentifiableEntity;

public class IdentifiableEntityEditor extends PropertyEditorSupport {
    private final IdentifiableEntityDao<?> dao;

    public IdentifiableEntityEditor(IdentifiableEntityDao<?> dao) {
        this.dao = dao;
    }

    @Override
    public String getAsText() {
        Object value = super.getValue();
        if (value != null) {
            IdentifiableEntity e = (IdentifiableEntity) value;
            Long id = e.getId();
            if (id != null) {
                return id.toString();
            }
        }
        return null;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (text == null || text.isEmpty()) {
            super.setValue(null);
        } else {
            super.setValue(this.dao.getById(new Long(text)));
        }
    }
}
