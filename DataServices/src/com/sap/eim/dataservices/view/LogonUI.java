package com.sap.eim.dataservices.view;

import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.jface.databinding.swt.SWTObservables;

import com.crystaldecisions.sdk.exception.SDKException;
import com.crystaldecisions.sdk.framework.IEnterpriseSession;
import com.sap.businessobjects.beans.User;
import com.sap.businessobjects.util.Logon;

public class LogonUI extends ApplicationWindow {
	private DataBindingContext m_bindingContext;
	private Text text;
	private Composite container;
	private Text text_1;
	private Text text_2;
	private Combo combo;

	private IEnterpriseSession enterpriseSession;

	/**
	 * Create the application window.
	 */
	public LogonUI() {
		super(null);
		setShellStyle(SWT.BORDER | SWT.MIN | SWT.TITLE | SWT.APPLICATION_MODAL);
		createActions();
		addToolBar(SWT.FLAT | SWT.WRAP);
		addMenuBar();
		addStatusLine();
	}

	/**
	 * Create contents of the application window.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createContents(Composite parent) {
		parent.setFont(SWTResourceManager.getFont("Courier New", 12, SWT.BOLD));
		/*
		 * 设置Window窗口Icon，如出现问题请删�?
		 */
		getShell().setImage(SWTResourceManager.getImage(MainUI.class, "/lib/icons/icon.png"));
	
		container = new Composite(parent, SWT.BORDER);
		container.setForeground(SWTResourceManager.getColor(100, 149, 237));
		container.setFont(SWTResourceManager.getFont("Courier New", 12,
				SWT.BOLD));
		container.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

		Button btnNewButton = new Button(container, SWT.NONE);
		btnNewButton.setForeground(SWTResourceManager.getColor(100, 149, 237));
		btnNewButton.setFont(SWTResourceManager.getFont("Courier New", 12,
				SWT.BOLD));
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				getShell().dispose();
			}
		});
		btnNewButton.setBounds(370, 272, 145, 43);
		btnNewButton.setText("Cancel");

		Button button = new Button(container, SWT.NONE);
		button.setForeground(SWTResourceManager.getColor(100, 149, 237));
		button.setFont(SWTResourceManager.getFont("Courier New", 12, SWT.BOLD));
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (text.getText() == "" | text_1.getText() == "") // 仅作为测试，请打�?下一�?
				// if(text.getText()==""|text_1.getText()==""|text_2.getText()=="")
				{

					// 如果输入为空，则调用JFace的错误对话框显示出错信息
					MessageDialog
							.openError(getShell(), "ERROR",
									"System , User Name and Password can not be empty !");
				} else {
					/*
					 * 如果用户验证成功，则跳转到另外一个界�?
					 */
					User user = new User();
					user.setSystem(text.getText());
					user.setUsername(text_1.getText());
					user.setPassword(text_2.getText());
					user.setAuthentication(combo.getText());

					try {
						enterpriseSession = Logon.basicLogon(user);
						
						MainUI window = new MainUI(enterpriseSession);
						getShell().dispose();
						window.setBlockOnOpen(true);
						window.open();
					} catch (SDKException e1) {
						text.setText("");
						text_1.setText("");
						text_2.setText("");
						combo.setText("");
						/**
						 * 显示登录失败
						 */
						MessageDialog
								.openError(getShell(), "FAILED",
										"System , User Name or Password is not correct!");
						e1.printStackTrace();
					}

				}

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		button.setText("Logon");
		button.setBounds(126, 272, 145, 43);

		Label lblSystem = new Label(container, SWT.BORDER | SWT.CENTER);
		lblSystem.setForeground(SWTResourceManager.getColor(100, 149, 237));
		lblSystem.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_BACKGROUND));
		lblSystem.setFont(SWTResourceManager.getFont("Courier New", 12,
				SWT.BOLD));
		lblSystem.setBounds(126, 65, 145, 26);
		lblSystem.setText("System");

		text = new Text(container, SWT.BORDER);
		text.setForeground(SWTResourceManager.getColor(100, 149, 237));
		text.setFont(SWTResourceManager.getFont("Courier New", 12, SWT.BOLD));
		text.setBounds(304, 63, 211, 26);

		Label lblUserName = new Label(container, SWT.BORDER | SWT.CENTER);
		lblUserName.setForeground(SWTResourceManager.getColor(100, 149, 237));
		lblUserName.setText("User Name");
		lblUserName.setFont(SWTResourceManager.getFont("Courier New", 12,
				SWT.BOLD));
		lblUserName.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_BACKGROUND));
		lblUserName.setBounds(126, 107, 145, 26);

		Label lblPassword = new Label(container, SWT.BORDER | SWT.CENTER);
		lblPassword.setForeground(SWTResourceManager.getColor(100, 149, 237));
		lblPassword.setText("Password");
		lblPassword.setFont(SWTResourceManager.getFont("Courier New", 12,
				SWT.BOLD));
		lblPassword.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_BACKGROUND));
		lblPassword.setBounds(126, 150, 145, 26);

		Label lblAu = new Label(container, SWT.BORDER | SWT.CENTER);
		lblAu.setForeground(SWTResourceManager.getColor(100, 149, 237));
		lblAu.setText("Authentication");
		lblAu.setFont(SWTResourceManager.getFont("Courier New", 12, SWT.BOLD));
		lblAu.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_BACKGROUND));
		lblAu.setBounds(126, 195, 145, 26);

		text_1 = new Text(container, SWT.BORDER);
		text_1.setForeground(SWTResourceManager.getColor(100, 149, 237));
		text_1.setFont(SWTResourceManager.getFont("Courier New", 12, SWT.BOLD));
		text_1.setBounds(304, 107, 211, 26);

		text_2 = new Text(container, SWT.BORDER | SWT.PASSWORD);
		text_2.setForeground(SWTResourceManager.getColor(100, 149, 237));
		text_2.setFont(SWTResourceManager.getFont("Courier New", 12, SWT.BOLD));
		text_2.setBounds(304, 150, 211, 26);

		combo = new Combo(container, SWT.NONE);
		combo.setForeground(SWTResourceManager.getColor(100, 149, 237));
		combo.setFont(SWTResourceManager.getFont("Courier New", 12, SWT.BOLD));
		combo.setBounds(304, 195, 211, 26);
		combo.add("secEnterprise", 0);
		m_bindingContext = initDataBindings();

		return container;
	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
	}

	/**
	 * Create the status line manager.
	 * 
	 * @return the status line manager
	 */
	@Override
	protected StatusLineManager createStatusLineManager() {
		StatusLineManager statusLineManager = new StatusLineManager();
		statusLineManager.setCancelEnabled(true);
		return statusLineManager;
	}

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		Display display = Display.getDefault();
		Realm.runWithDefault(SWTObservables.getRealm(display), new Runnable() {
			public void run() {
				try {
					LogonUI window = new LogonUI();
					window.setBlockOnOpen(true);
					window.open();
					Display.getCurrent().dispose();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Configure the shell.
	 * 
	 * @param newShell
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("CMS Logon");
		/*
		 * 修改左上角的icon
		 */

		// Image image = new Image(display, "sap.png");
		// newShell.setImage(image);
	}

	/**
	 * Return the initial size of the window.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(653, 442);
	}

	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		return bindingContext;
	}
}
