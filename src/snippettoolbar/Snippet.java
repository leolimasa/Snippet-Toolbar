package snippettoolbar;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import snippettoolbar.preferences.PreferenceConstants;
import snippettoolbar.shells.EditSnippetShell;
import snippettoolbar.views.ToolbarView;
import snippettoolbar.wizards.EditSnippetWizard;

public class Snippet {
	private SnippetSet set;
	private String name;
	private String text;
	private File file;
	private Label label;
	private Menu popupMenu;
	
	public SnippetSet getSet() {
		return set;
	}

	public void setSet(SnippetSet set) {
		this.set = set;
		
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		
	}
	
	public void save() {
		FileWriter fstream;
		try {
			fstream = new FileWriter(file);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(text);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		
		if (label != null) {
			label.setText(name);
		}
	}
	
	public void updateFilePath() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		String folder = store.getString(PreferenceConstants.SNIPPET_DIRECTORY);
		String extension = store.getString(PreferenceConstants.SNIPPET_EXTENSION);
		file = new File(folder, set.getName() + File.separator + getName() + extension);
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) throws IOException {
		this.file = file;
		if (!file.exists()) {
			file.createNewFile();
			setText(text);
		}
	}
	
	public void delete() {
		file.delete();
	}

	public void readFile(File file) throws IOException {
		setFile(file);
		readFile();
	}
	
	public void readFile() throws IOException {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		String extension = store.getString(PreferenceConstants.SNIPPET_EXTENSION);
		name = file.getName().substring(0, file.getName().length() - extension.length());
		 FileInputStream stream = new FileInputStream(file);
		  try {
		    FileChannel fc = stream.getChannel();
		    MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
		    /* Instead of using default, pass in a decoder. */
		    text = Charset.defaultCharset().decode(bb).toString();
		  }
		  finally {
		    stream.close();
		  }
	}
	
	public void createLabel(ExpandItem expand, final ToolbarView toolbar) {		
		// setup drag and drop
		int operations = DND.DROP_MOVE | DND.DROP_COPY;
		Transfer[] types = new Transfer[] {TextTransfer.getInstance()};
		
		// creates the label
		Label lbl = new Label((Composite) expand.getControl(), SWT.NONE);
		lbl.setText(name);
		
		DragSource ds = new DragSource(lbl, operations);
		ds.setTransfer(types);
		ds.addDragListener(new DragSourceListener() {

			@Override
			public void dragStart(DragSourceEvent event) {
				
			}

			@Override
			public void dragSetData(DragSourceEvent event) {
				event.data = text;
			}

			@Override
			public void dragFinished(DragSourceEvent event) {
				
			}
			
		});
		
		expand.setHeight(expand.getControl().computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		label = lbl;
		
		// creates the menu for the label
		popupMenu = new Menu(label);
		MenuItem mnuNew = new MenuItem(popupMenu, SWT.CASCADE);
		mnuNew.setText("New");
		mnuNew.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				EditSnippetWizard wizard = new EditSnippetWizard(toolbar);
				WizardDialog dialog = new WizardDialog(toolbar.getSite().getShell(), wizard);
				dialog.create();
				dialog.open();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}});
		
		MenuItem mnuEdit = new MenuItem(popupMenu, SWT.CASCADE);
		mnuEdit.setText("Edit");
		mnuEdit.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
//				EditSnippetShell editWindow = new EditSnippetShell(toolbar);
//				editWindow.displayEdit(Snippet.this);
				EditSnippetWizard wizard = new EditSnippetWizard(toolbar);
				wizard.setSnippet(Snippet.this);
				WizardDialog dialog = new WizardDialog(toolbar.getSite().getShell(), wizard);
				dialog.create();
				dialog.open();
				
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}});
		label.setMenu(popupMenu);
		
	}
}
