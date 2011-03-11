/* FeatureIDE - An IDE to support feature-oriented software development
 * Copyright (C) 2005-2010  FeatureIDE Team, University of Magdeburg
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
package de.ovgu.featureide.fm.ui.editors.featuremodel.actions;

import org.eclipse.gef.ui.parts.GraphicalViewerImpl;
import org.eclipse.jface.action.Action;

import de.ovgu.featureide.fm.core.Feature;
import de.ovgu.featureide.fm.core.FeatureModel;
import de.ovgu.featureide.fm.ui.editors.FeatureUIHelper;

/**
 * TODO shows/hides the legend when executed
 * 
 * @author Fabian Benduhn
 */
public class LegendAction extends Action {

	private final FeatureModel featureModel;

	public LegendAction(GraphicalViewerImpl viewer, FeatureModel featureModel) {
		super("Show Legend");
		this.featureModel = featureModel;
	}

	@Override
	public void run() {

		if (this.getText().equals("Show Legend")) {
			featureModel.setLegend(true);
			setText("Hide Legend");
			layoutLegend();

		} else {
			featureModel.setLegend(false);
			setText("Show Legend");
		}
		featureModel.handleModelDataChanged();
	}

	/**
	 * sets the position of the legend to the right-bottom of the features
	 */
	private void layoutLegend() {
		int maxX = 0;
		int maxY = 0;
		int maxWidth = 0;
		for (Feature c : featureModel.getFeatures()) {
			System.out.println(c);
			int nextX = FeatureUIHelper.getLocation(c).x;
			if (nextX > maxX)
				maxX = nextX;
			int nextY = FeatureUIHelper.getLocation(c).y;
			if (nextY > maxY)
				maxY = nextY;
			int nextWidth = FeatureUIHelper.getSize(c).width;
			if (nextWidth > maxWidth)
				maxWidth = nextWidth;
		}

		featureModel.setLegendPos(maxX + maxWidth / 2, maxY);
	}

}
