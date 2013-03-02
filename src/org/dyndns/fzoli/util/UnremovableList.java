package org.dyndns.fzoli.util;

import java.util.ArrayList;
import java.util.Collection;

public class UnremovableList<T> extends ArrayList<T> {
	
	private static final long serialVersionUID = -7664014226962999193L;
	
	private boolean shutdown = false;
	
	public UnremovableList() {
		super();
		initGC();
	}

	public UnremovableList(Collection<? extends T> c) {
		super(c);
		initGC();
	}
	
	public UnremovableList(int initialCapacity) {
		super(initialCapacity);
		initGC();
	}
	
	private void initGC() {
		Runtime.getRuntime().addShutdownHook(new Thread() {

			@Override
			public void run() {
				shutdown = true;
				clear();
			}

		});
	}
	
	@Override
	public T remove(int index) {
		if (shutdown) return super.remove(index);
		else return get(index);
	}
	
	@Override
	public boolean removeAll(Collection<?> c) {
		if (shutdown) return super.removeAll(c);
		else return false;
	}
	
	@Override
	protected void removeRange(int fromIndex, int toIndex) {
		if (shutdown) super.removeRange(fromIndex, toIndex);
	}
	
	@Override
	public void clear() {
		if (shutdown) super.clear();
	}
	
}
