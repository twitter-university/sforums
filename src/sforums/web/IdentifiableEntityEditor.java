package sforums.web;

import java.beans.PropertyEditorSupport;

import sforums.dao.IdentifiableEntityDao;
import sforums.domain.IdentifiableEntity;

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