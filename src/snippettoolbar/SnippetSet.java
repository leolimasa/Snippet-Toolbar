package snippettoolbar;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;

import snippettoolbar.preferences.PreferenceConstants;
import snippettoolbar.views.ToolbarView;

public class SnippetSet {
	private List<Snippet> snippets = new ArrayList<Snippet>();
	private String name;
	private ExpandItem expandItem;
	private File folder;
	
	public List<Snippet> getSnippets() {
		return snippets;
	}

	public void setSnippets(List<Snippet> snippets) {
		this.snippets = snippets;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SnippetSet() {
		
	}
	
	public GridData getLabelLayout() {
		GridData labelLayout = new GridData(GridData.FILL_HORIZONTAL);
		return labelLayout;
	}
	
	public void readDir(File file) throws IOException {
		snippets = new ArrayList<Snippet>();
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		setName(file.getName());
		folder = file;
		
		for (File f : file.listFiles()) {
			if (f.getName().endsWith(store.getString(PreferenceConstants.SNIPPET_EXTENSION))) {
				Snippet snippet = new Snippet();
				snippet.readFile(f);
				snippet.setSet(this);
				snippets.add(snippet);
			}
		}
	}
	
	public static SnippetSet[] readSets(String path) throws IOException {
		File dir = new File(path);
		List<SnippetSet> sets = new ArrayList<SnippetSet>();
		for (File f : dir.listFiles()) {
			if (f.isDirectory()) {
				SnippetSet set = new SnippetSet();
				set.readDir(f);
				sets.add(set);
			}
		}
		return sets.toArray(new SnippetSet[0]);
	}
	
	public ExpandItem createExpandItem(ToolbarView toolbarView) {
		// setup the container
		Composite composite = new Composite(toolbarView.getExpand(), SWT.NONE);
		GridLayout grid = new GridLayout();
		composite.setLayout(grid);
		ExpandItem item = new ExpandItem(toolbarView.getExpand(),SWT.NONE, 0);
		item.setControl(composite);
		item.setText(name);
		
		// generates the buttons
		for (Snippet s : snippets) {
			s.createLabel(item, toolbarView, getLabelLayout());
		}
		
		expandItem = item;
		
		return item;
	}
	
	public void addSnippetToToolbar(Snippet snippet, ToolbarView toolbar) {
		snippet.createLabel(expandItem, toolbar, getLabelLayout());
		addSnippet(snippet);
	}
	
	public void addSnippet(Snippet snippet) {
		snippets.add(snippet);
		snippet.setSet(this);
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		String extension = store.getString(PreferenceConstants.SNIPPET_EXTENSION);
		
		if (snippet.getFile() == null) {
			// creates the snippet file if it doesnt exist
			try {
				snippet.setFile(new File(folder,snippet.getName() + extension));
				snippet.save();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void createFolder() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		String repo = store.getDefaultString(PreferenceConstants.SNIPPET_DIRECTORY);
		File folder = new File(repo,this.getName());
		if (!folder.exists()) {
			folder.mkdir();
		}
	}
	
	public boolean hasSnippet(Snippet snippet) {
		return snippets.contains(snippet);
	}
	
	public Snippet getSnippetByName(String name) {
		for (Snippet s : snippets) {
			if (s.getName().equals(name)) {
				return s;
			}
		}
		return null;
	}
	
	public void deleteSnippet(Snippet snippet) {
		
	}
	
	public boolean hasSnippetWithName(String name) {
		for (Snippet s : snippets) {
			if (s.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}
}
