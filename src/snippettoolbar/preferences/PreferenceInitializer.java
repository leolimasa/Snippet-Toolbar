package snippettoolbar.preferences;

import java.io.File;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import snippettoolbar.Activator;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
//		store.setDefault(PreferenceConstants.P_BOOLEAN, true);
//		store.setDefault(PreferenceConstants.P_CHOICE, "choice2");
//		store.setDefault(PreferenceConstants.P_STRING,
//				"Default value");
		//String path = new File(Platform.getInstallLocation().getURL().getFile().toString(), "snippets").getPath();
		String path = new File(System.getProperty("user.home"), "eclipse_snippets").getPath();
		store.setDefault(PreferenceConstants.SNIPPET_DIRECTORY, path);
		store.setDefault(PreferenceConstants.SNIPPET_EXTENSION, ".txt");
	}

}
