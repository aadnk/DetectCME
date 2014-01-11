package com.comphenix.detectcme.injector;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.comphenix.detectcme.reflect.VolatileField;
import com.google.common.collect.Lists;

public class AbstractInjector {
	protected List<VolatileField> injectedFields;

	public AbstractInjector() {
		super();
		injectedFields = Lists.newArrayList();
	}

	public void uninject() {
		for (VolatileField field : injectedFields) {
			field.revertValue();
		}
		injectedFields.clear();
	}

	@SuppressWarnings("unchecked")
	protected void injectField(Object container, Field field, Thread expectedThread) {
		VolatileField injected = new VolatileField(field, container, true);
		Object current = injected.getValue();
		
		if (current instanceof List)
			injected.setValue(new SingleThreadedList<Object>((List<Object>) current, expectedThread));
		else if (current instanceof Set)
			injected.setValue(new SingleThreadedSet<Object>((Set<Object>) current, expectedThread));
		else if (current instanceof Collection)
			injected.setValue(new SingleThreadedCollection<Object>((Collection<Object>) current, expectedThread));
		else
			throw new IllegalArgumentException("Cannot inject  " + current);
		
		// Add it to the list of injected fields
		injectedFields.add(injected);
	}

}