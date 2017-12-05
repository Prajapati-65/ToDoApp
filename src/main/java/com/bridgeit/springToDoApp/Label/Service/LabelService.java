package com.bridgeit.springToDoApp.Label.Service;

import java.util.Set;

import com.bridgeit.springToDoApp.Label.Model.Label;

public interface LabelService {

	public int addLabel(Label label);

	public boolean deleteLable(Label label);

	public boolean updateLable(Label label);

	public Set<Label> getAllLabels(int userId);

}
