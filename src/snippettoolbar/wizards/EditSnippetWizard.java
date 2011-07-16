package snippettoolbar.wizards;

import org.eclipse.jface.wizard.Wizard;

import snippettoolbar.Snippet;
import snippettoolbar.views.ToolbarView;

public class EditSnippetWizard extends Wizard {

	EditSnippetWizardPage page;
	Snippet snippet = null;
	ToolbarView toolbar;
	
	public EditSnippetWizard(ToolbarView toolbar) {
		this.toolbar = toolbar;
	}
	
	public void addPages() {
		page = new EditSnippetWizardPage("SnippetPage", snippet);
		addPage(page);
		if (snippet == null) {
			page.setTitle("Create new Snippet");
		} else {
			page.setTitle("Edit Snippet");
		}
	}
	
	public void setSnippet(Snippet snippet) {
		this.snippet = snippet;
	}
	
	@Override
	public boolean performFinish() {
		this.toolbar.saveSnippet(page.getSnippet(), page.getFolder());
		return true;
	}
}
