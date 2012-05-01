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
package de.ovgu.featureide.core.typecheck.parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Path;

import AST.ClassDecl;
import AST.CompilationUnit;
import AST.Program;
import AST.TypeDecl;
import de.ovgu.featureide.core.IFeatureProject;
import de.ovgu.featureide.core.typecheck.helper.Timer;
import de.ovgu.featureide.fm.core.Feature;

/**
 * TODO description
 * 
 * @author S�nke Holthusen
 */
public class Parser
{
	public Timer timer = new Timer();

	private IFeatureProject _project;
	private ClassTable _class_table;

	public Parser(IFeatureProject project)
	{
		_project = project;

		_class_table = new ClassTable();
	}

	public void parse(String feature_path, List<Feature> feature_list)
	{
		for (int i = 0; i < feature_list.size(); i++)
		{
			parseFeature(feature_path, feature_list.get(i));
		}
	}

	public void parseFeatures(String feature_path, List<Feature> feature_list)
	{		
		Timer timer = new Timer();
		//System.out.print("Parsing Feature " + feature.getName() + " ... ");
		timer.start();
		this.timer.resume();

		try
		{
			List<String> list = new ArrayList<String>();
			for(Feature feature : feature_list)
			{
				list.add(feature.getName());
			}

			Iterator<Program> iter = FujiWrapper.getFujiCompositionIterator(list, feature_path);

			while (iter.hasNext())
			{
				// XXX: takes a very long time
				Program ast = iter.next();

				@SuppressWarnings("unchecked")
				Iterator<CompilationUnit> it = ast.compilationUnitIterator();
				while (it.hasNext())
				{
					CompilationUnit cu = it.next();
					if (cu.fromSource())
					{
						parseCU(cu, feature_path, list);
					}
				}

			}

		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.timer.stop();
		timer.stop();
		System.out.println(" done (" + timer.getTime() + " ms)");
	}
	
	private void parseFeature(String feature_path, Feature feature)
	{
		Timer timer = new Timer();
		System.out.print("Parsing Feature " + feature.getName() + " ... ");
		timer.start();
		this.timer.resume();

		try
		{
			List<String> list = new ArrayList<String>();
			list.add(feature.getName());

			Iterator<Program> iter = FujiWrapper.getFujiCompositionIterator(list, feature_path);

			while (iter.hasNext())
			{
				// XXX: takes a very long time
				Program ast = iter.next();

				@SuppressWarnings("unchecked")
				Iterator<CompilationUnit> it = ast.compilationUnitIterator();
				while (it.hasNext())
				{
					CompilationUnit cu = it.next();
					if (cu.fromSource())
					{
						parseCU(feature, cu);
					}
				}

			}

		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.timer.stop();
		timer.stop();
		System.out.println(" done (" + timer.getTime() + " ms)");
	}

	private void parseCU(CompilationUnit cu, String feature_path, List<String> feature_list)
	{
		System.out.println(cu.classQName());
		List<String> feature_paths = new ArrayList<String>();
		for(String feature_name : feature_list)
		{
			System.out.println(feature_name);
			
			feature_paths.add(feature_path + feature_name);
			//FujiWrapper.getIntros(cu, feature_paths);
			
		}
		cu.printIntros(feature_paths);
		cu.printRefs(feature_paths);
	}
	
	private void parseCU(Feature feature, CompilationUnit cu)
	{
		// TODO: handle imports

		final IProject project = _project.getProject();
		
		IFile class_path = project.getFile(new Path(cu.pathName()).makeRelativeTo(project.getLocation()));

		for (TypeDecl type : cu.getTypeDeclList())
		{
			if (type instanceof ClassDecl)
			{
				parseClass(feature, (ClassDecl) type, cu, class_path);
			}
		}
	}

	private void parseClass(Feature feature, ClassDecl class_ast, CompilationUnit cu, IFile class_path)
	{
		_class_table.add(feature, class_ast, cu, class_path);
	}

	public ClassTable getClassTable()
	{
		return _class_table;
	}
}