package snippettoolbar.views;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.ui.part.ViewPart;

import snippettoolbar.Activator;
import snippettoolbar.Snippet;
import snippettoolbar.SnippetSet;
import snippettoolbar.preferences.PreferenceConstants;

public class ToolbarView extends ViewPart {

	private ExpandBar expand;
	private List<SnippetSet> sets;
	private Composite root;
	
	public ExpandBar getExpand() {
		return expand;
	}

	public void setExpand(ExpandBar expand) {
		this.expand = expand;
	}

	@Override
	public void createPartControl(Composite parent) {
		expand = new ExpandBar(parent, SWT.FLAT | SWT.WRAP | SWT.RIGHT);
		sets = new ArrayList<SnippetSet>();
		root = parent;
		create();
	}

	public void create() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		
		// create snippet directory if it doesnt exist
		String dirpath = store.getString(PreferenceConstants.SNIPPET_DIRECTORY);
		File dir = new File(dirpath);
		if (!dir.exists()) {
			dir.mkdir();
		}
	
		// read the snippets from the folder
		try {
			SnippetSet[] sets = SnippetSet.readSets(dirpath);
			for (SnippetSet set : sets) {
				newSet(set);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void saveSnippet(Snippet snippet, String setName) {
		
		// check if setName exisists in the current sets
		SnippetSet set = null;
		for (SnippetSet currentSet : sets) {
			if (currentSet.getName().equals(setName)) {
				set = currentSet;
			}
		}
		
		if (set == null) {
			// if we didnt find any set with setName, create a new one
			// and add the snippet
			
			set = new SnippetSet();
			set.setName(setName);
			newSet(set);
			snippet.setSet(set);
			set.addSnippetToToolbar(snippet,this);
			snippet.updateFilePath();
			snippet.save();
			
		} else if (set.hasSnippetWithName(snippet.getName())) {
			Snippet s = set.getSnippetByName(snippet.getName());
			s.setName(snippet.getName());
			s.setText(snippet.getText());
			s.save();
		} else {
			// we have a set but no snippet in it
			set.addSnippetToToolbar(snippet, this);
			snippet.updateFilePath();
			snippet.save();
		}
	}
	
	public void newSet(SnippetSet set) {
		sets.add(set);
		set.createFolder();
		set.createExpandItem(this);
	}
	
	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		
	}
}
