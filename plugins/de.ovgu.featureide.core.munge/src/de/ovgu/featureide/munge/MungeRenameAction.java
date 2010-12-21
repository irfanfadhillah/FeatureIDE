package de.ovgu.featureide.munge;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;

import de.ovgu.featureide.core.CorePlugin;
import de.ovgu.featureide.core.IFeatureProject;
import de.ovgu.featureide.fm.core.FMCorePlugin;
import de.ovgu.featureide.fm.core.IRenameAction;

/**
 * Updates the preprocessor features of any java file after renaming at the
 * feature model.
 *  
 * @author Jens Meinicke
*/
public class MungeRenameAction implements IRenameAction {

	@Override
	public void performRenaming(String oldName, String newName, IProject project) {
		IFeatureProject featureProject = CorePlugin.getFeatureProject(project);
		IFolder sourceFolder = featureProject.getSourceFolder();
		if (!sourceFolder.exists())
			return;
		
		try {
			performRenamings(oldName, newName, sourceFolder);
			sourceFolder.refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			FMCorePlugin.getDefault().logError(e);
		}
		return;
	}

	private void performRenamings(String oldName, String newName, IFolder folder) throws CoreException {
		for (IResource res : folder.members()) {
			if (res instanceof IFolder) {
				performRenamings(oldName, newName, (IFolder)res);
			} else if (res instanceof IFile) {
				IFile file = (IFile)res;
				if (file.getName().endsWith(".java")) {
					performRenamings(oldName, newName, file);
				}
			}
			
		}
	}
	
	private void performRenamings(String oldName, String newName, IFile iFile) {
		try {
			File file = iFile.getRawLocation().toFile();
			String fileText = "";
			Scanner scanner = new Scanner(file);
			while (scanner.hasNext()) {
				fileText += scanner.nextLine() + "\r\n";
			}
			scanner.close();
			if (!fileText.contains(oldName)) {
				return;
			}
			//XXX regular expression for "[oldName]*/" ?
			fileText = fileText.replaceAll(oldName,newName);
			FileWriter fw = new FileWriter(file);
			fw.write(fileText);
			fw.close();	
		} catch (FileNotFoundException e) {
			MungeCorePlugin.getDefault().logError(e);
		} catch (IOException e) {
			MungeCorePlugin.getDefault().logError(e);
		}
	}
}