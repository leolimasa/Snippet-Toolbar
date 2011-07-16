package snippettoolbar.shells;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import snippettoolbar.Snippet;
import snippettoolbar.views.ToolbarView;

public class EditSnippetShell {
	private Shell shell;
	private Display display;
	private Label lblFolder;
	private Text txtFolder;
	private Label lblName;
	private Text txtName;
	private Label lblCode;
	private Text txtCode;
	private Button btnSave;
	private Button btnCancel;
	private Snippet snippet;

	private ToolbarView toolbar;
	
	public EditSnippetShell(ToolbarView toolbar) {
		this.toolbar = toolbar;
	}
	
	public void displayNew() {
		this.snippet = new Snippet();
		createForm();
	}
	
	public void displayEdit(Snippet snippet) {
		this.snippet = snippet;
		createForm();
		txtFolder.setText(snippet.getSet().getName());
		txtName.setText(snippet.getName());
		txtCode.setText(snippet.getText());
		openShell();
	}
	
	private void openShell() {
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}
	
	private void updateSnippet() {
		snippet.setName(txtName.getText());
		snippet.setText(txtCode.getText());
	}
	
	private void createForm() {
		display = new Display();
		shell = new Shell(display);
		FillLayout layout = new FillLayout();
		
		lblFolder = new Label(shell, SWT.NONE);
		lblFolder.setText("Folder");
		txtFolder = new Text(shell, SWT.BORDER);
		
		lblName = new Label(shell, SWT.NONE);
		lblName.setText("Name");
		txtName = new Text(shell, SWT.BORDER);
		
		lblCode = new Label(shell, SWT.NONE);
		lblName.setText("Code");
		txtName = new Text(shell, SWT.BORDER | SWT.MULTI);
		
		btnSave = new Button(shell, SWT.PUSH);
		btnSave.setText("Save");
		btnSave.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				updateSnippet();
				toolbar.saveSnippet(snippet, txtFolder.getText());
				shell.dispose();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}});
		
		btnCancel = new Button(shell, SWT.PUSH);
		btnCancel.setText("Cancel");
		btnCancel.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
				
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
}
