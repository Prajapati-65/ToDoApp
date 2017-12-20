package com.bridgeit.springToDoApp.Label.Service;


import java.util.List;

import com.bridgeit.springToDoApp.Label.Model.Label;
import com.bridgeit.springToDoApp.User.Model.User;

/**
 * @author Om Prajapati
 *
 */
public interface LabelService {

	/**
	 * @param Label object
	 */
	void saveLabel(Label label);

	/**
	 * @param integer id
	 * @return boolean
	 */
	boolean deleteLabelById(int id);

	/**
	 * @param User object
	 * @return List of labels
	 */
	List<Label> getLabels(User user);

	/**
	 * @param integer labelId
	 * @return Label object
	 */
	Label getLabelById(int labelId);

	/**
	 * @param String labelName
	 * @return Label Object
	 */
	Label getLabelByName(String labelName);

	/**
	 * @param Label object
	 * @return boolean
	 */
	boolean editLabel(Label label);


}
