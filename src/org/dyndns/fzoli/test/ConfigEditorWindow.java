package org.dyndns.fzoli.test;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.dyndns.fzoli.swt.ButtonComposite;
import org.dyndns.fzoli.swt.SWTUtils;
import org.dyndns.fzoli.test.res.R;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

import com.richclientgui.toolbox.validation.ValidatingField;
import com.richclientgui.toolbox.validation.ValidationToolkit;
import com.richclientgui.toolbox.validation.converter.IntegerStringConverter;
import com.richclientgui.toolbox.validation.string.StringValidationToolkit;
import com.richclientgui.toolbox.validation.validator.IFieldValidator;

public class ConfigEditorWindow {

	private static final Pattern PT_ADDRESS_1 = Pattern.compile("^[a-z\\d]{0,1}$", Pattern.CASE_INSENSITIVE);
	private static final Pattern PT_ADDRESS_2 = Pattern.compile("^[a-z\\d]{1}[\\w\\.\\d]{0,18}[a-z\\d]{1}$", Pattern.CASE_INSENSITIVE);
	private static final Pattern PT_PORT = Pattern.compile("^[1-9]{1}[\\d]{0,5}$", Pattern.CASE_INSENSITIVE);
	
	private final Map<Text, String> VALUES = new HashMap<Text, String>();
	
	protected Shell shell;
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ConfigEditorWindow window = new ConfigEditorWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	private Text txtAddress, txtPort;
	
	private IFieldValidator<String> fvAddress = new IFieldValidator<String>() {
		
		@Override
		public boolean warningExist(String s) {
			return s.isEmpty();
		}
		
		@Override
		public boolean isValid(String s) {
			return s.isEmpty() || PT_ADDRESS_2.matcher(s).matches();
		}
		
		@Override
		public String getWarningMessage() {
			return "A mező kitöltése kötelező.\nA cím minimum 2 karakter.";
		}
		
		@Override
		public String getErrorMessage() {
			return txtAddress != null && txtAddress.getText().length() < 2 ? "A cím minimum 2 karakter." : "A cím IP-cím vagy domain lehet.";
		}
		
	};
	
	private IFieldValidator<Integer> fvPort = new IFieldValidator<Integer>() {
		
		@Override
		public boolean warningExist(Integer i) {
			return i == null;
		}
		
		@Override
		public boolean isValid(Integer i) {
			return (txtPort != null && txtPort.getText().isEmpty()) || i != null && i > 0 && i < 65536;
		}
		
		@Override
		public String getWarningMessage() {
			return "A mező kitöltése kötelező.\nA port 1 és 65535 között lehet.";
		}
		
		@Override
		public String getErrorMessage() {
			return "A port 1 és 65535 között lehet.";
		}
		
	};
	
	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setText("Kapcsolatbeállítás");
		shell.setLayout(new GridLayout(1, false));
		
		GridData gdTab = new GridData();
		gdTab.horizontalAlignment = SWT.FILL;
		gdTab.verticalAlignment = SWT.FILL;
		gdTab.grabExcessHorizontalSpace = true;
		gdTab.grabExcessVerticalSpace = true;
		
		TabFolder tabFolder = new TabFolder(shell, SWT.NONE);
		tabFolder.setLayoutData(gdTab);
		
		Composite composite1 = new Composite(tabFolder, SWT.NONE);
		composite1.setLayout(new GridLayout(2, false));
		
		TabItem tbtm1 = new TabItem(tabFolder, SWT.NO_FOCUS);
		tbtm1.setControl(composite1);
		tbtm1.setText("Útvonal");
		
		GridData gdMessage1 = new GridData();
		gdMessage1.horizontalSpan = 2;
		gdMessage1.horizontalAlignment = SWT.FILL;
		gdMessage1.grabExcessHorizontalSpace = true;
		gdMessage1.grabExcessVerticalSpace = true;
		gdMessage1.widthHint = 250;
		
		Label lblMessage1 = new Label(composite1, SWT.WRAP);
		lblMessage1.setLayoutData(gdMessage1);
		lblMessage1.setText("Ezen a lapfülen állíthatja be a Híd szervernek az elérési útvonalát.");
		Label lblAddress = new Label(composite1, SWT.NONE);
		lblAddress.setText("Szerver cím:");
		
		GridData gdAddress = new GridData();
		gdAddress.horizontalIndent = 4;
		gdAddress.horizontalAlignment = SWT.FILL;
		gdAddress.grabExcessVerticalSpace = true;
		
		ValidationToolkit<String> vtAddress = new StringValidationToolkit(SWT.TOP | SWT.LEFT, 1, true);
		ValidatingField<String> vfAddress = vtAddress.createTextField(composite1, fvAddress, false, "localhost");
		
		txtAddress = (Text) vfAddress.getControl();
		txtAddress.setLayoutData(gdAddress);
		
		Label lblPort = new Label(composite1, SWT.NONE);
		lblPort.setText("Szerver port:");
		
		GridData gdPort = new GridData();
		gdPort.horizontalIndent = 4;
		gdPort.horizontalAlignment = SWT.FILL;
		gdPort.grabExcessVerticalSpace = true;
		
		ValidationToolkit<Integer> vtPort = new ValidationToolkit<Integer>(new IntegerStringConverter(), SWT.TOP | SWT.LEFT, 1, true);
		ValidatingField<Integer> vfPort = vtPort.createTextField(composite1, fvPort, false, 8443);
		
		txtPort = (Text) vfPort.getControl();
		txtPort.setLayoutData(gdPort);
		
		Composite cmpFiller = new Composite(tabFolder, SWT.NONE);
		cmpFiller.setLayout(new GridLayout(1, false));
		
		GridData gdFiller = new GridData();
		gdFiller.minimumHeight = 185;
		gdFiller.horizontalAlignment = SWT.FILL;
		gdFiller.verticalAlignment = SWT.FILL;
		gdFiller.grabExcessHorizontalSpace = true;
		gdFiller.grabExcessVerticalSpace = true;
		
		new Text(cmpFiller, SWT.MULTI | SWT.V_SCROLL | SWT.WRAP | SWT.BORDER).setLayoutData(gdFiller);
		
		TabItem tbtm2 = new TabItem(tabFolder, SWT.NO_FOCUS);
		tbtm2.setControl(cmpFiller);
		tbtm2.setText("Bevitel");
		
		GridData gdButtons = new GridData();
		gdButtons.horizontalAlignment = SWT.FILL;
		
		ButtonComposite cmpButtons = new ButtonComposite(shell, SWT.NONE, "Súgó", "Mégse", "OK");
		cmpButtons.setLayoutData(gdButtons);
		
		cmpButtons.getButtonList().get(1).addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				shell.dispose();
			}
			
		});
		
		shell.pack();
		SWTUtils.setLocationToCenter(shell);
		shell.setImage(R.getIconImage());
		
		txtAddress.addListener(SWT.Verify, new Listener() {
			
			boolean skip = false;
			
			@Override
			public void handleEvent(Event event) {
				if (skip) return;
				String text = SWTUtils.getText(event);
				if (text.length() < 2) {
					if (!PT_ADDRESS_1.matcher(text).matches()) event.doit = false;
				}
				else {
					String subBefText = SWTUtils.getText(event, true, false);
					String subAftText = SWTUtils.getText(event, false, true);
					if (subBefText.endsWith("..") || subAftText.startsWith("..")) {
						event.doit = false;
						return;
					}
					boolean remove = SWTUtils.isRemove(event);
					boolean befDotEnd = subBefText.endsWith(".");
					boolean aftDotEnd = subAftText.endsWith(".");
					if (!subAftText.equals(".") && aftDotEnd) {
						skip = true;
						text = text.substring(0, text.length() - 1);
						txtAddress.setText(text);
						txtAddress.setSelection(event.start + (remove ? 0 : 1));
						skip = false;
						event.doit = false;
						return;
					}
					if (remove && (befDotEnd || subBefText.isEmpty()) && subAftText.startsWith(".")) {
						event.doit = false;
						skip = true;
						txtAddress.setText(subBefText + subAftText.substring(1));
						txtAddress.setSelection(event.start - 1);
						skip = false;
						return;
					}
					if (befDotEnd && (aftDotEnd || subAftText.isEmpty())) return;
					if (!PT_ADDRESS_2.matcher(text).matches()) event.doit = false;
				}
			}
			
		});
		
		final Listener lFocusIn = new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				Text t = (Text) event.widget;
				VALUES.put(t, t.getText());
			}
			
		};
		
		final Listener lAddressOut = new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				String text = txtAddress.getText().trim();
				if (text.endsWith(".")) txtAddress.setText(text = text.substring(0, text.length() - 1));
				if (PT_ADDRESS_2.matcher(text).matches()) txtAddress.setText(text.toLowerCase());
				else txtAddress.setText(VALUES.get(event.widget));
				txtAddress.setSelection(text.length());
			}
			
		};
		
		txtAddress.addListener(SWT.FocusIn, lFocusIn);
		
		txtAddress.addListener(SWT.FocusOut, lAddressOut);
		
		txtAddress.addListener(SWT.KeyDown, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				if (event.character == SWT.CR) {
					lAddressOut.handleEvent(event);
				}
			}
			
		});
		
		txtPort.addListener(SWT.Verify, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				try {
					String text = SWTUtils.getText(event);
					if (text.isEmpty()) return;
					int number = Integer.parseInt(text);
					if (number < 1 || number > 65535 || !PT_PORT.matcher(text).matches()) throw new Exception();
				}
				catch (Exception ex) {
					event.doit = false;
				}
			}
			
		});
		
		final Listener lPortOut = new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				if (txtPort.getText().isEmpty()) txtPort.setText(VALUES.get(txtPort));
				txtPort.setSelection(txtPort.getText().length());
			}
			
		};
		
		txtPort.addListener(SWT.KeyDown, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				if (event.character == SWT.CR) {
					lPortOut.handleEvent(event);
				}
			}
			
		});
		
		txtPort.addListener(SWT.FocusIn, lFocusIn);
		
		txtPort.addListener(SWT.FocusOut, lPortOut);
	}

}
