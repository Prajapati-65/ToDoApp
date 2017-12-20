package com.bridgeit.springToDoApp.Label.DAO;


import java.util.List;

import com.bridgeit.springToDoApp.Label.Model.Label;
import com.bridgeit.springToDoApp.User.Model.User;

/**
 * @author Om Prajapati
 *
 */
public interface LabelDao {

	
	/**
	 * @param Label object
	 */
	void saveLabel(Label labels);

	/**
	 * @param integer id
	 */
	void deleteById(int id);

	/**
	 * @param User object
 	 * @return
	 */
	List<Label> getLabels(User user);

	/**
	 * @param integer labelId
	 * @return Label object
	 */
	Label getLabelById(int labelId);

	
	/**
	 * @param Label label
	 * @return boolean
	 */
	boolean editLabel(Label label);

	/**
	 * @param String labelName
	 * @return Label object
	 */
	Label getLabelByName(String labelName);
	
	
}
