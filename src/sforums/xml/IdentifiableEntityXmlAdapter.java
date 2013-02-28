package sforums.xml;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import sforums.dao.IdentifiableEntityDao;
import sforums.domain.IdentifiableEntity;

public class IdentifiableEntityXmlAdapter<E extends IdentifiableEntity> extends
		XmlAdapter<String, E> {

	private final IdentifiableEntityDao<E> dao;

	public IdentifiableEntityXmlAdapter(IdentifiableEntityDao<E> dao) {
		this.dao = dao;
	}

	@Override
	public String marshal(E e) throws Exception {
		return e == null || e.getId() == null ? null : e.getId().toString();
	}

	@Override
	public E unmarshal(String id) throws Exception {
		return id == null || this.dao == null ? null : this.dao
				.getById(new Long(id));
	}
}