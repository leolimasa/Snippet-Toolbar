package snippettoolbar.wizards;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import snippettoolbar.Snippet;

public class EditSnippetWizardPage extends WizardPage {

	private Label lblFolder;
	private Text txtFolder;
	private Label lblName;
	private Text txtName;
	private Label lblCode;
	private Text txtCode;
	private Snippet snippet;
	
	protected EditSnippetWizardPage(String pageName, Snippet snippet) {
		super(pageName);
		this.snippet = snippet;
	}
	
	public String getFolder() {
		return txtFolder.getText();
	}
	
	public String getName() {
		return txtCode.getText();
	}
	
	public String getCode() {
		return txtCode.getText();
	}

	public Snippet getSnippet() {
		if (snippet == null) {
			snippet = new Snippet();
		}
		String code = txtCode.getText();
		snippet.setName(txtName.getText());
		snippet.setText(txtCode.getText());
		return snippet;
	}
	
	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 2;
		
		GridData txtLayout = new GridData(GridData.FILL_HORIZONTAL);
		
		GridData txtCodeLayout = new GridData(GridData.FILL_HORIZONTAL);
		txtCodeLayout.grabExcessHorizontalSpace = true;
		txtCodeLayout.verticalAlignment = GridData.FILL_VERTICAL;
		txtCodeLayout.heightHint = 300;
		
		lblFolder = new Label(container, SWT.NONE);
		lblFolder.setText("Folder");
		txtFolder = new Text(container, SWT.BORDER);
		txtFolder.setLayoutData(txtLayout);
		
		lblName = new Label(container, SWT.NONE);
		lblName.setText("Name");
		txtName = new Text(container, SWT.BORDER);
		txtName.setLayoutData(txtLayout);
		
		lblCode = new Label(container, SWT.NONE);
		lblCode.setText("Code");
		txtCode = new Text(container, SWT.BORDER | SWT.MULTI);
		txtCode.setSize(100, 500);
		txtCode.setLayoutData(txtCodeLayout);
		setControl(container);
		
		if (snippet != null) {
			txtFolder.setText(snippet.getSet().getName());
			txtName.setText(snippet.getName());
			txtCode.setText(snippet.getText());
		}
	}
}
