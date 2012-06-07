package sforums.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

public abstract class IdentifiableEntityList<E extends IdentifiableEntity> {
	private List<E> list;

	public IdentifiableEntityList() {
	}

	public IdentifiableEntityList(List<E> list) {
		this.list = list;
	}

	@XmlTransient
	public List<E> getList() {
		return this.list;
	}

	public void setList(List<E> list) {
		this.list = list;
	}
}
