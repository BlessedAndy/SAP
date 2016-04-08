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
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Text;

import com.sap.util.ExportExcelUtil;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.eclipse.swt.widgets.ProgressBar;

public class MainUI extends ApplicationWindow {
	private Text text_ID;
	private Text text_Folder;
	private Table table_1;
	
	//****************** For duplicate page *******************
	
	public Button MD5CheckBox;
	public ProgressBar progressBar;
	public Button searchButton_Duplicate;
//	JProgressBar jProgressBar1 = new JProgressBar();
	
	
	//**********************************************************
	
	private Text text_Name;
	private Text text_Owner;
	private Text text_CUID;
	
	/**
	 * Create the application window.
	 */
	public MainUI() {
		super(null);
		
//		MainUI.enterpriseSession = enterpriseSession;
		createActions();
		addToolBar(SWT.FLAT | SWT.WRAP);
		addMenuBar();
		addStatusLine();
		
	}

	/**
	 * Create contents of the application window.
	 * @param parent
	 */
	/* (non-Javadoc)
	 * @see org.eclipse.jface.window.Window#createContents(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setTouchEnabled(true);
		/*
		 * ����Window����Icon�������������ɾ��
		 */
		
		container.setLayout(null);
		
		
		
		CTabFolder tabFolder = new CTabFolder(container, SWT.BORDER);
		tabFolder.setBounds(0, 0, 826, 585);
		tabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));

//**************************************************************************************
//                                 ��һ��Ҷǩ��ʼ
//**************************************************************************************
		
		CTabItem tbtmNewItem = new CTabItem(tabFolder, SWT.NONE);
		tbtmNewItem.setText("ObjectList");
		//tbtmNewItem.setControl(tabFolder);
		
		Group group = new Group(tabFolder, SWT.NONE);
		tbtmNewItem.setControl(group);
		
		Label lblObjectType = new Label(group, SWT.NONE);
		lblObjectType.setBounds(14, 23, 99, 28);
		lblObjectType.setText("Object Type");
		
		Button allTypeRadion = new Button(group, SWT.RADIO);
		allTypeRadion.setText("All");
		allTypeRadion.setBounds(175, 24, 55, 16);
		
		Button conncectionRadioButton = new Button(group, SWT.RADIO);
		conncectionRadioButton.setText("Connection");
		conncectionRadioButton.setBounds(285, 24, 90, 16);
		
		Button universeRadioButton = new Button(group, SWT.RADIO);
		universeRadioButton.setText("Universe");
		universeRadioButton.setBounds(415, 24, 90, 16);
		
		Button webiRadioButton = new Button(group, SWT.RADIO);
		webiRadioButton.setBounds(531, 24, 55, 16);
		webiRadioButton.setText("Webi");
		
		Combo combo_webi = new Combo(group, SWT.NONE);
		combo_webi.setBounds(592, 21, 99, 25);
		combo_webi.add("");
		combo_webi.add("instance");
		combo_webi.add("notInstance");
		
		
		/*
		 * *****************begin of Selection Criteria**************************
		 */
		Label lblCr = new Label(group, SWT.NONE);
		lblCr.setBounds(14, 60, 136, 19);
		lblCr.setText("Selection Criteria");
		
		Label lblId = new Label(group, SWT.NONE);
		lblId.setBounds(163, 61, 31, 15);
		lblId.setText("ID");
		
		text_ID = new Text(group, SWT.BORDER);
		text_ID.setBounds(214, 58, 124, 21);
		
		Label lblName = new Label(group, SWT.NONE);
		lblName.setBounds(368, 61, 38, 15);
		lblName.setText("Name");

		Combo combo_Name = new Combo(group, SWT.NONE);
		combo_Name.add("");
		combo_Name.add("is");
		combo_Name.add("like");
		combo_Name.setBounds(415, 57, 55, 16);
		
		text_Name = new Text(group, SWT.BORDER);
		text_Name.setBounds(503, 57, 124, 21);
		
		Label lblCuid = new Label(group, SWT.NONE);
		lblCuid.setText("CUID");
		lblCuid.setBounds(163, 88, 31, 15);
		
		text_CUID = new Text(group, SWT.BORDER);
		text_CUID.setBounds(214, 88, 124, 21);
		
		Label lblOwner = new Label(group, SWT.NONE);
		lblOwner.setText("Owner");
		lblOwner.setBounds(368, 91, 38, 15);
		
		Combo combo_Owner = new Combo(group, SWT.NONE);
		combo_Owner.add("");
		combo_Owner.add("is");
		combo_Owner.add("like");
		combo_Owner.setBounds(415, 88, 55, 25);
		
		text_Owner = new Text(group, SWT.BORDER);
		text_Owner.setBounds(503, 89, 124, 21);
//*************************end of Selection Criteria*****************************
		
		Label lblSearch = new Label(group, SWT.NONE);
		lblSearch.setBounds(14, 125, 68, 24);
		lblSearch.setText("Search In");
		
		/**
		 * ��һ��Ҷǩ��All
		 */
		Button allRadioButton = new Button(group, SWT.RADIO);
		allRadioButton.setText("All Folders");
		allRadioButton.setBounds(86, 128, 82, 16);
		
		/**
		 * ��һ��Ҷǩ��Public Folders
		 */
		Button publicRadioButton = new Button(group, SWT.RADIO);
		publicRadioButton.setBounds(175, 128, 104, 16);
		publicRadioButton.setText("Public Folders");
		
		Button personalRadioButton = new Button(group, SWT.RADIO);
		personalRadioButton.setText("Personal Folders");
		personalRadioButton.setBounds(285, 128, 124, 16);
		
		Button underSpecifiedRadio = new Button(group, SWT.RADIO);
		underSpecifiedRadio.setBounds(415, 128, 199, 16);
		underSpecifiedRadio.setText("UnderSpecified Folders(Name)");
		
		text_Folder = new Text(group, SWT.BORDER);
		text_Folder.setBounds(620, 125, 180, 21);
		
//////////////////////////////////��������������///////////////////////////////////////////////
		
//*******************************��һ��Ҷǩ��Table******************************************
		table_1 = new Table(group, SWT.BORDER | SWT.FULL_SELECTION);
		table_1.setBounds(10, 155, 790, 344);
		table_1.setHeaderVisible(true);
		table_1.setLinesVisible(true);
		
		
		TableColumn tblclmnNewColumn = new TableColumn(table_1, SWT.CENTER);
		tblclmnNewColumn.setWidth(77);
		tblclmnNewColumn.setText("ID");
		
		TableColumn tblclmnNewColumn_1 = new TableColumn(table_1, SWT.CENTER);
		tblclmnNewColumn_1.setWidth(107);
		tblclmnNewColumn_1.setText("CUID");
		
		TableColumn tblclmnName = new TableColumn(table_1, SWT.CENTER);
		tblclmnName.setWidth(107);
		tblclmnName.setText("Name");
		
		TableColumn tblclmnType = new TableColumn(table_1, SWT.CENTER);
		tblclmnType.setWidth(107);
		tblclmnType.setText("Type");
		
		TableColumn tblclmnNewColumn_2 = new TableColumn(table_1, SWT.CENTER);
		tblclmnNewColumn_2.setWidth(107);
		tblclmnNewColumn_2.setText("Size");
		
		TableColumn tblclmnNewColumn_3 = new TableColumn(table_1, SWT.CENTER);
		tblclmnNewColumn_3.setWidth(107);
		tblclmnNewColumn_3.setText("Folder");
		
		TableColumn tblclmnOwner = new TableColumn(table_1, SWT.CENTER);
		tblclmnOwner.setWidth(107);
		tblclmnOwner.setText("Owner");
		
		TableColumn tblclmnCreatedat = new TableColumn(table_1, SWT.CENTER);
		tblclmnCreatedat.setWidth(107);
		tblclmnCreatedat.setText("CreatedAt");
		
		TableColumn tblclmnModifiedat = new TableColumn(table_1, SWT.CENTER);
		tblclmnModifiedat.setWidth(107);
		tblclmnModifiedat.setText("ModifiedAt");
		
		TableColumn tblclmnDescription = new TableColumn(table_1, SWT.CENTER);
		tblclmnDescription.setWidth(117);
		tblclmnDescription.setText("Description");
		
		TableColumn tblclmnNewColumn_4 = new TableColumn(table_1, SWT.CENTER);
		tblclmnNewColumn_4.setWidth(107);
		tblclmnNewColumn_4.setText("Instance");
		
		TableColumn tblclmnNewColumn_5 = new TableColumn(table_1, SWT.CENTER);
		tblclmnNewColumn_5.setWidth(107);
		tblclmnNewColumn_5.setText("Universe");
		
		/*
		 * ��ʾ�ļ��ܹ���С
		 */
		Label totalSizeLabel = new Label(group, SWT.NONE);
		totalSizeLabel.setBounds(25, 520, 213, 15);
		totalSizeLabel.setText("Total 0 ");
		
		/**
		 * ���label����ʾ���ж��ٸ�item
		 */
		Label totalItemLabel = new Label(group, SWT.NONE);
		totalItemLabel.setBounds(319, 520, 124, 15);
		totalItemLabel.setText("0 items found");
		
		Button btnNewButton = new Button(group, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			/**
			 * ������ť
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				ExportExcelUtil<List> ex = new ExportExcelUtil<List>();
				// ID CUID Name Size Folder Owner Created at Modified at Description isInstance Universe
				String[] header = { "id", "cuid", "name", "type", "size", "folder", "owner",
						"created at", "modified at", "description", "isInstance",
						"universe" };
				/*
				 * Testing code
				 */
				List dataset;
				
				/*
				 * ѡ�񵼳�·��
				 */
				DirectoryDialog dialog = new DirectoryDialog(getShell());
				dialog.setText("	BusinessObjects Locatator");
				dialog.setMessage("  Please select the location you want to export");
				dialog.setFilterPath("C://");
				String saveFile = dialog.open();
				
			
				//export to excel
				/*try {
					dataset = RetriveUtil.allRowDetail(enterpriseSession);
					if (saveFile != null) {
						File directiory = new File(saveFile);
						System.out.println(directiory.getPath());
						OutputStream out = new FileOutputStream(directiory.getPath()+"//BueinessObject XI 3.1 Data.xls");
						ex.exportExcel(header, dataset, out);
						out.close();
					}
				} catch (SDKException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}*/
				MessageDialog.openInformation(getShell(), "Export View","Export Completed");
				System.out.println("�����ɹ���");
			}
		});
		btnNewButton.setBounds(686, 515, 114, 25);
		btnNewButton.setText("ExportToCSV");
		
		/*
		 * Search button
		 */
		Button btnNewButton_2 = new Button(group, SWT.NONE);
		btnNewButton_2.addSelectionListener(new SelectionAdapter() {
			/**
			 * Search����֮��Ķ���
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				// Type : webi universe connection
				// connectionRadioButton universeRadioButton
				// webiRadioButton.getSelection()
				List<?> rowDetail = null;
				
				int columns = table_1.getColumns().length;
				for (int i = 0; i < columns; i++) {
					table_1.removeAll();
				}		
               //***********************************************
				String type = (webiRadioButton.getSelection()? "webi":(universeRadioButton.getSelection()? "universe":(conncectionRadioButton.getSelection()? "connection":"")));
				String instance = combo_webi.getText();
				String id = text_ID.getText();
				String name = text_Name.getText();
				String nameCombo = combo_Name.getText();
				String cuid = text_CUID.getText();
				String owner = text_Owner.getText();
				String ownerCombo = combo_Owner.getText();
				String folder = (personalRadioButton.getSelection()? "personal":(publicRadioButton.getSelection()? "public":(underSpecifiedRadio.getSelection()? text_Folder.getText():"")));
				
				
				
				
				
				/*
				 * 
				 * ��ʾ�ļ��ܹ���С
				 */
				/**
				 * ���label����ʾ���ж��ٸ�item
				 */
			}
		});
		btnNewButton_2.setBounds(667, 60, 114, 49);
		btnNewButton_2.setText("Search");

		return container;
	}

	private void setText(int i, String string) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
	}

	/**
	 * Create the status line manager.
	 * @return the status line manager
	 */
	@Override
	protected StatusLineManager createStatusLineManager() {
		StatusLineManager statusLineManager = new StatusLineManager();
		return statusLineManager;
	}

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			MainUI window = new MainUI();
			window.setBlockOnOpen(true);
			window.open();
			Display.getCurrent().dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Configure the shell.
	 * @param newShell
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("SAP DataServices Schedule Tool");
	}

	/**
	 * Return the initial size of the window.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(843, 652);
	}
}
