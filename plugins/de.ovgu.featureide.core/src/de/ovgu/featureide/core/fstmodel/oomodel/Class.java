/* FeatureIDE - An IDE to support feature-oriented software development
 * Copyright (C) 2005-2011  FeatureIDE Team, University of Magdeburg
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see http://www.gnu.org/licenses/.
 *
 * See http://www.fosd.de/featureide/ for further information.
 */
package de.ovgu.featureide.core.fstmodel.oomodel;

import java.util.LinkedList;
import java.util.TreeMap;

import org.eclipse.core.resources.IFile;

import de.ovgu.featureide.core.fstmodel.IClass;
import de.ovgu.featureide.core.fstmodel.IFSTModelElement;
import de.ovgu.featureide.core.fstmodel.IField;
import de.ovgu.featureide.core.fstmodel.IMethod;


/**
 * Describes a class of a featureproject according to a selected configuration
 * 
 * @author Tom Brosch
 * @author Thomas Thuem
 * 
 */
public class Class extends OOModelElement implements IClass {

	// Only the own AST methods are implemented

	private IFile currentFile;

	public TreeMap<String, Method> methods;

	public TreeMap<String, IField> fields;

	private String className;

	private LinkedList<IFile> sources;

	public Class() {
		this("");
	}

	/**
	 * Creates a new instance of a class
	 * 
	 * @param className
	 *            The name of the class
	 */
	public Class(String className) {
		this.className = className;
		methods = new TreeMap<String, Method>();
		fields = new TreeMap<String, IField>();
	}

	public int getFieldCount() {
		return fields.size();
	}

	public IField[] getFields() {
		return fields.values().toArray(new Field[fields.values().size()]);
	}

	public int getMethodCount() {
		return methods.size();
	}

	public IMethod[] getMethods() {
		IMethod[] methodArray = new IMethod[methods.size()];
		int i = 0;
		for (IMethod meth : methods.values())
			methodArray[i++] = meth;
		return methodArray;
	}

	
	public int getPublicFieldCount() {
		int i = 0;
		for (IField field : fields.values())
			if (field.isPublic())
				i++;
		return i;
	}
	
	public IField[] getPublicFields() {
		IField[] fieldArray = new Field[getPublicFieldCount()];
		int i = 0;
		for (IField field : fields.values())
			if (field.isPublic())
				fieldArray[i++] = field;
		return fieldArray;
	}
	
	public int getPublicMethodCount() {
		int i = 0;
		for (IMethod meth : methods.values())
			if (meth.isPublic())
				i++;
		return i;
	}
	
	public IMethod[] getPublicMethods() {
		IMethod[] methodArray = new Method[getPublicMethodCount()];
		int i = 0;
		for (IMethod meth : methods.values()) {
			if (meth.isPublic())
				methodArray[i++] = meth;
		}
		return methodArray;
	}
	
	public int getProtectedFieldCount() {
		int i = 0;
		for (IField field : fields.values())
			if (field.isProtected())
				i++;
		return i;
	}
	
	public IField[] getProtectedFields() {
		IField[] fieldArray = new Field[getProtectedFieldCount()];
		int i = 0;
		for (IField field : fields.values())
			if (field.isProtected())
				fieldArray[i++] = field;
		return fieldArray;
	}
	
	public int getProtectedMethodCount() {
		int i = 0;
		for (IMethod meth : methods.values())
			if (meth.isProtected())
				i++;
		return i;
	}
	
	public IMethod[] getProtectedMethods() {
		IMethod[] methodArray = new Method[getProtectedMethodCount()];
		int i = 0;
		for (IMethod meth : methods.values()) {
			if (meth.isProtected())
				methodArray[i++] = meth;
		}
		return methodArray;
	}
	
	public int getPrivateFieldCount() {
		int i = 0;
		for (IField field : fields.values())
			if (field.isPrivate())
				i++;
		return i;
	}
	
	public IField[] getPrivateFields() {
		IField[] fieldArray = new Field[getPrivateFieldCount()];
		int i = 0;
		for (IField field : fields.values())
			if (field.isPrivate())
				fieldArray[i++] = field;
		return fieldArray;
	}
	
	public int getPrivateMethodCount() {
		int i = 0;
		for (IMethod meth : methods.values())
			if (meth.isPrivate())
				i++;
		return i;
	}
	
	public IMethod[] getPrivateMethods() {
		IMethod[] methodArray = new Method[getPrivateMethodCount()];
		int i = 0;
		for (IMethod meth : methods.values()) {
			if (meth.isPrivate())
				methodArray[i++] = meth;
		}
		return methodArray;
	}
	
	public int getPackagePrivateFieldCount() {
		int i = 0;
		for (IField field : fields.values())
			if (!field.isPrivate() && !field.isProtected() && !field.isPublic())
				i++;
		return i;
	}
	
	public IField[] getPackagePrivateFields() {
		IField[] fieldArray = new Field[getPackagePrivateFieldCount()];
		int i = 0;
		for (IField field : fields.values())
			if (!field.isPrivate() && !field.isProtected() && !field.isPublic())
				fieldArray[i++] = field;
		return fieldArray;
	}
	
	public int getPackagePrivateMethodCount() {
		int i = 0;
		for (IMethod meth : methods.values())
			if (!meth.isPrivate() && !meth.isProtected() && !meth.isPublic())
				i++;
		return i;
	}
	
	public IMethod[] getPackagePrivateMethods() {
		IMethod[] methodArray = new Method[getPrivateMethodCount()];
		int i = 0;
		for (IMethod meth : methods.values()) {
			if (!meth.isPrivate() && !meth.isProtected() && !meth.isPublic())
				methodArray[i++] = meth;
		}
		return methodArray;
	}
	
	public int getAvailableFieldCount() {
		int i = 0;
		for (IField field : fields.values())
			if (field.isAvailable(currentFile))
				i++;
		return i;
	}

	public IField[] getAvailableFields() {
		IField[] fieldArray = new Field[getAvailableFieldCount()];
		int i = 0;
		for (IField field : fields.values())
			if (field.isAvailable(currentFile))
				fieldArray[i++] = field;
		return fieldArray;
	}

	public int getAvailableMethodCount() {
		int i = 0;
		for (IMethod meth : methods.values())
			if (meth.isAvailable(currentFile))
				i++;
		return i;
	}

	public IMethod[] getAvailableMethods() {
		IMethod[] methodArray = new Method[getAvailableMethodCount()];
		int i = 0;
		for (IMethod meth : methods.values()) {
			if (meth.isAvailable(currentFile))
				methodArray[i++] = meth;
		}
		return methodArray;
	}

	public int getOwnFieldCount() {
		int i = 0;
		for (IField field : fields.values())
			if (field.isOwn(currentFile))
				i++;
		return i;
	}

	public IField[] getOwnFields() {
		IField[] fieldArray = new Field[getOwnFieldCount()];
		int i = 0;
		for (IField field : fields.values())
			if (field.isOwn(currentFile))
				fieldArray[i++] = field;
		return fieldArray;
	}

	public int getOwnMethodCount() {
		int i = 0;
		for (IMethod meth : methods.values())
			if (meth.isOwn(currentFile))
				i++;
		return i;
	}

	public IMethod[] getOwnMethods() {
		IMethod[] methodArray = new Method[getOwnMethodCount()];
		int i = 0;
		for (IMethod meth : methods.values()) {
			if (meth.isOwn(currentFile))
				methodArray[i++] = meth;
		}
		return methodArray;
	}

	public void setFile(IFile file) {
		currentFile = file;
	}

	public IFile getFile() {
		return currentFile;
	}

	public String getName() {
		return className;
	}

	public IFSTModelElement[] getChildren() {
		IFSTModelElement[] elements = new IFSTModelElement[getOwnFieldCount()
				+ getOwnMethodCount()];
		IField[] fieldArray = getOwnFields();
		IMethod[] methodArray = getOwnMethods();
		int pos = 0;
		for (int i = 0; i < fieldArray.length; i++, pos++)
			elements[pos] = fieldArray[i];
		for (int i = 0; i < methodArray.length; i++, pos++)
			elements[pos] = methodArray[i];
		return elements;
	}

	public boolean hasChildren() {
		return getOwnMethodCount() + getOwnFieldCount() > 0;
	}

	public LinkedList<IFile> getSources() {
		return sources;
	}

	/**
	 * @param file
	 */
	public void setName(String name) {
		className = name;
	}
}