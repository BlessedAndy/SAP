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
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
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
	private Table table_2;
	private Text text_8;
	private Text text_11;
	private Table table_3;
	
	//****************** For duplicate page *******************
	
	public Button MD5CheckBox;
	public ProgressBar progressBar;
	public Button searchButton_Duplicate;
//	JProgressBar jProgressBar1 = new JProgressBar();
	
	CalculateHash calculateHashThread;
	
	//**********************************************************
	
	static IEnterpriseSession enterpriseSession;
	
	private Text text_Name;
	private Text text_Owner;
	private Text text_CUID;
	
	/**
	 * Create the application window.
	 */
	public MainUI(IEnterpriseSession enterpriseSession) {
		super(null);
		
		/**
		 * 仅作为测试代码使用，注意删除测试代码并打�?下面代码
		 */
		try {
			MainUI.enterpriseSession = Logon.testLogon();
		} catch (SDKException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
		 * 设置Window窗口Icon，如出现问题请删�?
		 */
		getShell().setImage(SWTResourceManager.getImage(MainUI.class, "/lib/icons/icon.png"));
	
		container.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));

		container.setForeground(SWTResourceManager.getColor(0, 0, 0));
		container.setLayout(null);
		
		
		
		CTabFolder tabFolder = new CTabFolder(container, SWT.BORDER);
		tabFolder.setBounds(0, 0, 826, 585);
		tabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));

//**************************************************************************************
//                                 第一个叶签开�?
//**************************************************************************************
		
		CTabItem tbtmNewItem = new CTabItem(tabFolder, SWT.NONE);
		tbtmNewItem.setText("ObjectList");
		//tbtmNewItem.setControl(tabFolder);
		
		Group group = new Group(tabFolder, SWT.NONE);
		tbtmNewItem.setControl(group);
		
		Label lblObjectType = new Label(group, SWT.NONE);
		lblObjectType.setForeground(SWTResourceManager.getColor(0, 191, 255));
		lblObjectType.setFont(SWTResourceManager.getFont("Arial", 11, SWT.BOLD));
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
		lblCr.setFont(SWTResourceManager.getFont("Arial", 11, SWT.BOLD));
		lblCr.setForeground(SWTResourceManager.getColor(0, 191, 255));
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
		lblSearch.setForeground(SWTResourceManager.getColor(0, 191, 255));
		lblSearch.setFont(SWTResourceManager.getFont("Arial", 11, SWT.BOLD));
		lblSearch.setBounds(14, 125, 68, 24);
		lblSearch.setText("Search In");
		
		/**
		 * 第一个叶签的All
		 */
		Button allRadioButton = new Button(group, SWT.RADIO);
		allRadioButton.setText("All Folders");
		allRadioButton.setBounds(86, 128, 82, 16);
		
		/**
		 * 第一个叶签的Public Folders
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
		
//////////////////////////////////以上是搜索条�?///////////////////////////////////////////////
		
//*******************************第一个叶签的Table******************************************
		table_1 = new Table(group, SWT.BORDER | SWT.FULL_SELECTION);
		table_1.setBackground(SWTResourceManager.getColor(255, 255, 255));
		table_1.setFont(SWTResourceManager.getFont("Courier New", 11, SWT.NORMAL));
		table_1.setForeground(SWTResourceManager.getColor(0, 0, 0));
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
		 * 显示文件总共大小
		 */
		Label totalSizeLabel = new Label(group, SWT.NONE);
		totalSizeLabel.setBounds(25, 520, 213, 15);
		totalSizeLabel.setText("Total 0 ");
		
		/**
		 * 这个label是显示共有多少个item
		 */
		Label totalItemLabel = new Label(group, SWT.NONE);
		totalItemLabel.setBounds(319, 520, 124, 15);
		totalItemLabel.setText("0 items found");
		
		Button btnNewButton = new Button(group, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			/**
			 * 导出按钮
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
				 * 选择导出路径
				 */
				DirectoryDialog dialog = new DirectoryDialog(getShell());
				dialog.setText("	BusinessObjects Locatator");
				dialog.setMessage("  Please select the location you want to export");
				dialog.setFilterPath("C://");
				String saveFile = dialog.open();
				
				try {
				dataset = RetriveUtil.allRowDetail(enterpriseSession);
				if (saveFile != null) {
					File directiory = new File(saveFile);
					System.out.println(directiory.getPath());
					ExportCsvUtil.exportToCsv(directiory.getPath()+"//BueinessObject XI 3.1 Data.csv", header, dataset);
				}
			} catch (SDKException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
				System.out.println("导出成功�?");
			}
		});
		btnNewButton.setBounds(686, 515, 114, 25);
		btnNewButton.setText("ExportToCSV");
		
		/*
		 * Search button
		 */
		Button btnNewButton_2 = new Button(group, SWT.NONE);
		btnNewButton_2.setForeground(SWTResourceManager.getColor(30, 144, 255));
		btnNewButton_2.setFont(SWTResourceManager.getFont("Courier New", 12, SWT.BOLD));
		btnNewButton_2.addSelectionListener(new SelectionAdapter() {
			/**
			 * Search按下之后的动�?
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				// Type : webi universe connection
				// connectionRadioButton universeRadioButton
				// webiRadioButton.getSelection()
				List<?> rowDetail = null;
				
				//*************清理工作****************************
				/**
				 * 每次查询要清空，不然会累�?
				 */
				RetriveUtil.totalSize = 0;
				RetriveUtil.count = 0;

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
				
				if(type.equals("")&&id.equals("")&&name.equals("")&&cuid.equals("")&&owner.equals("")&&folder.equals("")){
					try {
						rowDetail = RetriveUtil.allRowDetail(enterpriseSession);
					} catch (SDKException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else{
					rowDetail = (List<?>)RetriveUtil.retriveWithCriteria(enterpriseSession, 
							type, 
							instance,
							id, 
							name, nameCombo, 
							cuid, 
							owner, ownerCombo, 
							folder);
				}
				
				
				String[] oneRow;
				Iterator it = ((java.util.List) rowDetail).iterator();
				while (it.hasNext()) {
					oneRow = (String[]) it.next();

					/**
					 * 这里添加�?table里添加数据的代码，还没测导出excel数据可不可以用String[]
					 */
					TableItem tableItem = new TableItem(table_1, SWT.NONE);
					tableItem.setText(oneRow);
					RetriveUtil.count ++;
				}
				/*
				 * 
				 * 显示文件总共大小
				 */
				long size = RetriveUtil.totalSize;
				totalSizeLabel.setText("Total Size: " + size );
				/**
				 * 这个label是显示共有多少个item
				 */
				totalItemLabel.setText(RetriveUtil.count + " items found");
			}
		});
		btnNewButton_2.setBounds(667, 60, 114, 49);
		btnNewButton_2.setText("Search");
		
		
		
//**************************************************************************************
//                                第二个叶签开�?
//**************************************************************************************
		
		CTabItem tbtmNewItemOfPage2 = new CTabItem(tabFolder, SWT.NONE);
		tbtmNewItemOfPage2.setText("DuplicateFinder");
		
		Group group1OfPage2 = new Group(tabFolder, SWT.NONE);
		tbtmNewItemOfPage2.setControl(group1OfPage2);
		
/////////////////////////////////////////////////////////////////////

		
		
		
/////////////////////////////////////////////////////////////////////
		
		Label lblObjectType_2 = new Label(group1OfPage2, SWT.NONE);
		lblObjectType_2.setForeground(SWTResourceManager.getColor(0, 191, 255));
		lblObjectType_2.setFont(SWTResourceManager.getFont("Arial", 11, SWT.BOLD));
		lblObjectType_2.setBounds(14, 23, 99, 28);
		lblObjectType_2.setText("Object Type");
		
//		allTypeRadion.setBounds(175, 24, 55, 16);
		
		
		/////////////////////////////////////////////////////////////////////
		Button allTypeRadion_Page2 = new Button(group1OfPage2, SWT.RADIO);
		allTypeRadion_Page2.setText("All");
		allTypeRadion_Page2.setBounds(175, 24, 55, 16);
		
		Button conncectionRadioButton_Page2 = new Button(group1OfPage2, SWT.RADIO);
		conncectionRadioButton_Page2.setText("Connection");
		conncectionRadioButton_Page2.setBounds(285, 24, 90, 16);
		
		Button universeRadioButton_Page2 = new Button(group1OfPage2, SWT.RADIO);
		universeRadioButton_Page2.setText("Universe");
		universeRadioButton_Page2.setBounds(415, 24, 90, 16);
		
		Button webiRadioButton_Page2 = new Button(group1OfPage2, SWT.RADIO);
		webiRadioButton_Page2.setBounds(531, 24, 55, 16);
		webiRadioButton_Page2.setText("Webi");
		
		Combo combo_webi_Page2 = new Combo(group1OfPage2, SWT.NONE);
		combo_webi_Page2.setBounds(592, 21, 99, 25);
		combo_webi_Page2.add("");
		combo_webi_Page2.add("instance");
		combo_webi_Page2.add("notInstance");
		////////////////////////////////////////////////////////////////////
		
	/*	Button webiRadioButtonOfPage2 = new Button(group1OfPage2, SWT.RADIO);
		webiRadioButtonOfPage2.setBounds(326, 24, 90, 16);
		webiRadioButtonOfPage2.setText("Webi");
		
		Button btnUniverse_2 = new Button(group1OfPage2, SWT.RADIO);
		btnUniverse_2.setText("Universe");
		btnUniverse_2.setBounds(209, 24, 90, 16);
		
		Button btnConnection = new Button(group1OfPage2, SWT.RADIO);
		btnConnection.setText("Connection");
		btnConnection.setBounds(113, 22, 90, 16);*/
		
		/*
		 * *****************begin of Selection Criteria**************************
		 */
		Label lblCrOfPage2 = new Label(group1OfPage2, SWT.NONE);
		lblCrOfPage2.setFont(SWTResourceManager.getFont("Arial", 11, SWT.BOLD));
		lblCrOfPage2.setForeground(SWTResourceManager.getColor(0, 191, 255));
		lblCrOfPage2.setBounds(14, 60, 136, 19);
		lblCrOfPage2.setText("Selection Criteria");
		
		Button btnCheckButton = new Button(group1OfPage2, SWT.CHECK);
		btnCheckButton.setBounds(174, 61, 93, 16);
		btnCheckButton.setText("File Name");
		
		MD5CheckBox = new Button(group1OfPage2, SWT.CHECK);
		MD5CheckBox.setBounds(285, 61, 93, 16);
		MD5CheckBox.setText("MD5");
		
		Button btnCheckButton_1 = new Button(group1OfPage2, SWT.CHECK);
		btnCheckButton_1.setBounds(415, 61, 93, 16);
		btnCheckButton_1.setText("SHA");
		
		Button OpenDirButton = new Button(group1OfPage2, SWT.NONE);
		OpenDirButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				/*
				 * 选择扫描路径
				 */
				DirectoryDialog dialog = new DirectoryDialog(getShell());
				dialog.setText("	BusinessObjects Locatator");
				dialog.setMessage("  Please select the location you want to scan");
				dialog.setFilterPath("C://");
				String saveFile = dialog.open();
				
				File directiory = new File(saveFile);
				System.out.println(directiory.getAbsoluteFile());
				
				globals.selFile = directiory;
				
				globals.mainFramOfDup = MainUI.this;
				
				globalsChangeBack.selFile = directiory.getAbsoluteFile();
//		        setTitle(globalsChangeBack.selFile.getAbsolutePath());
//		        globalsChangeBack.settings.put("LastDirectory", globalsChangeBack.selFile);
			}
		});
		OpenDirButton.setImage(SWTResourceManager.getImage(MainUI.class, "/lib/icons/openFile.png"));
		OpenDirButton.setBounds(136, 85, 25, 25);
		OpenDirButton.setText("Open Dir");
		
		progressBar = new ProgressBar(group1OfPage2, SWT.HORIZONTAL | SWT.SMOOTH);
		progressBar.setBounds(175, 94, 411, 17);
		
//*************************end of Selection Criteria*****************************
		
		Label lblNewLabel = new Label(group1OfPage2, SWT.NONE);
		lblNewLabel.setForeground(SWTResourceManager.getColor(0, 191, 255));
		lblNewLabel.setFont(SWTResourceManager.getFont("Arial", 11, SWT.BOLD));
		lblNewLabel.setBounds(14, 125, 68, 24);
		lblNewLabel.setText("Search In");
		
		/**
		 * 第二个叶签的All
		 */
		Button allRadioButton_Page2 = new Button(group1OfPage2, SWT.RADIO);
		allRadioButton_Page2.setText("All Folders");
		allRadioButton_Page2.setBounds(86, 128, 82, 16);
		
		Button btnRadioButton_3 = new Button(group1OfPage2, SWT.RADIO);
		btnRadioButton_3.setBounds(175, 128, 104, 16);
		btnRadioButton_3.setText("Public Folders");
		
		Button btnPersonalFolder_1 = new Button(group1OfPage2, SWT.RADIO);
		btnPersonalFolder_1.setText("Personal Folders");
		btnPersonalFolder_1.setBounds(285, 128, 124, 16);
		
		Button btnUnderspecifiedFolder = new Button(group1OfPage2, SWT.RADIO);
		btnUnderspecifiedFolder.setText("UnderSpecified Folders(Name)");
		btnUnderspecifiedFolder.setBounds(415, 128, 199, 16);
		
		text_8 = new Text(group1OfPage2, SWT.BORDER);
		text_8.setBounds(620, 125, 180, 21);
		
		
		
		/*
		 * begin of table (Page 2)
		 */
		
		/*************************************************************************/
		table_2 = new Table(group1OfPage2, SWT.BORDER | SWT.FULL_SELECTION);
		table_2.setBackground(SWTResourceManager.getColor(255, 255, 255));
		table_2.setFont(SWTResourceManager.getFont("Courier New", 11, SWT.NORMAL));
		table_2.setForeground(SWTResourceManager.getColor(0, 0, 0));
		table_2.setBounds(10, 155, 790, 344);
		table_2.setHeaderVisible(true);
		table_2.setLinesVisible(true);
		/*************************************************************************/
		
		TableColumn tableColumn = new TableColumn(table_2, SWT.CENTER);
		tableColumn.setWidth(77);
		tableColumn.setText("ID");
		
		TableColumn tableColumn_1 = new TableColumn(table_2, SWT.CENTER);
		tableColumn_1.setWidth(107);
		tableColumn_1.setText("CUID");
		
		TableColumn tableColumn_2 = new TableColumn(table_2, SWT.CENTER);
		tableColumn_2.setWidth(107);
		tableColumn_2.setText("Name");
		
		TableColumn typeColumnOfTable2 = new TableColumn(table_2, SWT.CENTER);
		typeColumnOfTable2.setWidth(107);
		typeColumnOfTable2.setText("Type");
		
		TableColumn tableColumn_3 = new TableColumn(table_2, SWT.CENTER);
		tableColumn_3.setWidth(107);
		tableColumn_3.setText("Size");
		
		TableColumn tableColumn_4 = new TableColumn(table_2, SWT.CENTER);
		tableColumn_4.setWidth(107);
		tableColumn_4.setText("Folder");
		
		TableColumn tblclmnOwner_1 = new TableColumn(table_2, SWT.CENTER);
		tblclmnOwner_1.setWidth(107);
		tblclmnOwner_1.setText("Owner");
		
		TableColumn tableColumn_5 = new TableColumn(table_2, SWT.CENTER);
		tableColumn_5.setWidth(107);
		tableColumn_5.setText("CreatedAt");
		
		TableColumn tableColumn_6 = new TableColumn(table_2, SWT.CENTER);
		tableColumn_6.setWidth(107);
		tableColumn_6.setText("ModifiedAt");
		
		TableColumn tableColumn_7 = new TableColumn(table_2, SWT.CENTER);
		tableColumn_7.setWidth(117);
		tableColumn_7.setText("Description");
		
		TableColumn tableColumn_8 = new TableColumn(table_2, SWT.CENTER);
		tableColumn_8.setWidth(107);
		tableColumn_8.setText("Instance");
		
		TableColumn tableColumn_9 = new TableColumn(table_2, SWT.CENTER);
		tableColumn_9.setWidth(107);
		tableColumn_9.setText("Universe");
		
		
		/*
		 * 显示文件总共大小
		 */
		Label lblTotalSize_1 = new Label(group1OfPage2, SWT.NONE);
		lblTotalSize_1.setBounds(25, 520, 213, 15);
		lblTotalSize_1.setText("Total 0 ");
		
		/**
		 * 这个label是显示共有多少个item
		 */
		Label lblItemsFound_1 = new Label(group1OfPage2, SWT.NONE);
		lblItemsFound_1.setBounds(319, 520, 124, 15);
		lblItemsFound_1.setText("0 items found");
		
		Button btnExportascsv = new Button(group1OfPage2, SWT.NONE);
		btnExportascsv.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

			calculateHashThread = new CalculateHash(MainUI.this);
			calculateHashThread.setDisplay(Display.getDefault());
			
			calculateHashThread.start();
			btnExportascsv.setEnabled(false);
				
			}
		});
		btnExportascsv.setBounds(686, 515, 114, 25);
		btnExportascsv.setText("ExportToHTML");
		
		
		// 上面是export按钮，先不用管，下面是search按钮�?     Search button of second leaf sign
		searchButton_Duplicate = new Button(group1OfPage2, SWT.NONE);
		searchButton_Duplicate.setForeground(SWTResourceManager.getColor(30, 144, 255));
		searchButton_Duplicate.setFont(SWTResourceManager.getFont("Courier New", 12, SWT.BOLD));
		/*
		 * Search action of page2
		 */
		searchButton_Duplicate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

			calculateHashThread = new CalculateHash(MainUI.this);
			calculateHashThread.setDisplay(Display.getDefault());
			calculateHashThread.start();
			searchButton_Duplicate.setEnabled(false);
					
		//********************************************************************************************************************
		//TODO
					
			/**
			 * Search按下之后的动�?
			 */
			// Type : webi universe connection
			// connectionRadioButton universeRadioButton
			// webiRadioButton.getSelection()
			List<?> rowDetail = null;
			
			//*************清理工作****************************
			/**
			 * 每次查询要清空，不然会累�?
			 */
			RetriveUtil.totalSize = 0;
			RetriveUtil.count = 0;

			int columns = table_2.getColumns().length;
			for (int i = 0; i < columns; i++) {
				table_2.removeAll();
			}		
           //***********************************************
			String type = (webiRadioButton_Page2.getSelection()? "webi":(universeRadioButton_Page2.getSelection()? "universe":(conncectionRadioButton_Page2.getSelection()? "connection":"")));
			String instance = combo_webi_Page2.getText();
			String folder = (personalRadioButton.getSelection()? "personal":(publicRadioButton.getSelection()? "public":(underSpecifiedRadio.getSelection()? text_Folder.getText():"")));
			
			try {
				//�? calculateHashThread 线程执行完再�?下执�?
				calculateHashThread.join();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			Vector frsFileNames = calculateHashThread.getDuplicatedFiles();
			Iterator frsFileNamesIterator = frsFileNames.iterator();
			
			while(frsFileNamesIterator.hasNext()){
				System.out.println(frsFileNamesIterator.next());
			}
			
			/*if(type.equals("")&&folder.equals("")){
				try {
					rowDetail = RetriveUtil.allRowDetail(enterpriseSession);
				} catch (SDKException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}else{
				rowDetail = (List<?>)RetriveUtil.retriveDuplicatedWithCriteria(enterpriseSession,type,instance,folder,frsFileNamesIterator.next().toString());
			}*/
			
			/*String[] oneRow;
			Iterator it = ((java.util.List) rowDetail).iterator();
			
			
			while (it.hasNext()) {
				oneRow = (String[]) it.next();

				*//**
				 * 这里添加�?table里添加数据的代码，还没测导出excel数据可不可以用String[]
				 *//*
				TableItem tableItem = new TableItem(table_2, SWT.NONE);
				tableItem.setText(oneRow);
				RetriveUtil.count ++;
			}*/
			/*
			 * 
			 * 显示文件总共大小
			 */
			/*long size = RetriveUtil.totalSize;
			lblTotalSize_1.setText("Total Size: " + size );
			*//**
			 * 这个label是显示共有多少个item
			 *//*
			lblItemsFound_1.setText(RetriveUtil.count + " items found");*/
			
//********************************************************************************************************************
				
			}
		});
		
		searchButton_Duplicate.setBounds(667, 60, 114, 49);
		searchButton_Duplicate.setText("Search");
		
		
//**************************************************************************************
//                                    第三个叶签开�?
//**************************************************************************************

		CTabItem tbtmNewItem_2 = new CTabItem(tabFolder, SWT.NONE);
		tbtmNewItem_2.setText("SiloFinder");
		
		Group group1OfPage3 = new Group(tabFolder, SWT.NONE);
		group1OfPage3.setTouchEnabled(true);
		tbtmNewItem_2.setControl(group1OfPage3);
		
		Label objectTypeOfPage3 = new Label(group1OfPage3, SWT.NONE);
		objectTypeOfPage3.setForeground(SWTResourceManager.getColor(0, 191, 255));
		objectTypeOfPage3.setFont(SWTResourceManager.getFont("Arial", 11, SWT.BOLD));
		objectTypeOfPage3.setBounds(14, 23, 99, 28);
		objectTypeOfPage3.setText("Object Type");
		
		Button allTypeRadioOf3 = new Button(group1OfPage3, SWT.RADIO);
		allTypeRadioOf3.setText("All");
		allTypeRadioOf3.setBounds(175, 24, 55, 16);
		
		Button connectionRadioButtonOf3 = new Button(group1OfPage3, SWT.RADIO);
		connectionRadioButtonOf3.setText("Connection");
		connectionRadioButtonOf3.setBounds(285, 24, 90, 16);
		
		Button universeRadioButtonOf3 = new Button(group1OfPage3, SWT.RADIO);
		universeRadioButtonOf3.setText("Universe");
		universeRadioButtonOf3.setBounds(415, 24, 90, 16);
		
		/*
		 * *****************begin of Selection Criteria**************************
		 */
		Label lblCrOfPage3 = new Label(group1OfPage3, SWT.NONE);
		lblCrOfPage3.setFont(SWTResourceManager.getFont("Arial", 11, SWT.BOLD));
		lblCrOfPage3.setForeground(SWTResourceManager.getColor(0, 191, 255));
		lblCrOfPage3.setBounds(14, 60, 136, 19);
		lblCrOfPage3.setText("Selection Criteria");
		
		Button checkButtonOfPage3 = new Button(group1OfPage3, SWT.CHECK);
		checkButtonOfPage3.setEnabled(false);
		checkButtonOfPage3.setSelection(true);
		checkButtonOfPage3.setTouchEnabled(true);
		checkButtonOfPage3.setBounds(174, 61, 93, 16);
		checkButtonOfPage3.setText("Reference");
		
		Label searchInOf3 = new Label(group1OfPage3, SWT.NONE);
		searchInOf3.setForeground(SWTResourceManager.getColor(0, 191, 255));
		searchInOf3.setFont(SWTResourceManager.getFont("Arial", 11, SWT.BOLD));
		searchInOf3.setBounds(14, 125, 68, 24);
		searchInOf3.setText("Search In");
		
		
//**********************************************************************************
//**********************************************************************************
		
		/**
		 * 第三个叶签的All
		 */
		Button allFolderRadioButtonOf3 = new Button(group1OfPage3, SWT.RADIO);
		allFolderRadioButtonOf3.setText("All Folders");
		allFolderRadioButtonOf3.setBounds(86, 128, 82, 16);
	
		Button publicFolderRadioButtonOf3 = new Button(group1OfPage3, SWT.RADIO);
		publicFolderRadioButtonOf3.setBounds(175, 128, 104, 16);
		publicFolderRadioButtonOf3.setText("Public Folders");
		
		Button personalFolderRadioButtonOf3 = new Button(group1OfPage3, SWT.RADIO);
		personalFolderRadioButtonOf3.setText("Personal Folders");
		personalFolderRadioButtonOf3.setBounds(285, 128, 124, 16);
		
		Button specifiedFolderOf3 = new Button(group1OfPage3, SWT.RADIO);
		specifiedFolderOf3.setText("UnderSpecified Folders(Name)");
		specifiedFolderOf3.setBounds(415, 128, 199, 16);
		
		text_11 = new Text(group1OfPage3, SWT.BORDER);
		text_11.setBounds(620, 125, 180, 21);
		
		table_3 = new Table(group1OfPage3, SWT.BORDER | SWT.FULL_SELECTION);
		table_3.setBackground(SWTResourceManager.getColor(255, 255, 255));
		table_3.setFont(SWTResourceManager.getFont("Courier New", 11, SWT.NORMAL));
		table_3.setForeground(SWTResourceManager.getColor(0, 0, 0));
		table_3.setBounds(10, 155, 790, 344);
		table_3.setHeaderVisible(true);
		table_3.setLinesVisible(true);
		
		
		TableColumn tableColumn_10 = new TableColumn(table_3, SWT.CENTER);
		tableColumn_10.setWidth(77);
		tableColumn_10.setText("ID");
		
		TableColumn tableColumn_11 = new TableColumn(table_3, SWT.CENTER);
		tableColumn_11.setWidth(107);
		tableColumn_11.setText("CUID");
		
		TableColumn tableColumn_12 = new TableColumn(table_3, SWT.CENTER);
		tableColumn_12.setWidth(107);
		tableColumn_12.setText("Name");
		
		TableColumn tableColumn_13 = new TableColumn(table_3, SWT.CENTER);
		tableColumn_13.setWidth(107);
		tableColumn_13.setText("Type");
		
		TableColumn tableColumn_14 = new TableColumn(table_3, SWT.CENTER);
		tableColumn_14.setWidth(107);
		tableColumn_14.setText("Size");
		
		TableColumn tableColumn_15 = new TableColumn(table_3, SWT.CENTER);
		tableColumn_15.setWidth(107);
		tableColumn_15.setText("Folder");
		
		TableColumn tableColumn_16 = new TableColumn(table_3, SWT.CENTER);
		tableColumn_16.setWidth(107);
		tableColumn_16.setText("Owner");
		
		TableColumn tableColumn_17 = new TableColumn(table_3, SWT.CENTER);
		tableColumn_17.setWidth(107);
		tableColumn_17.setText("CreatedAt");
		
		TableColumn tableColumn_18 = new TableColumn(table_3, SWT.CENTER);
		tableColumn_18.setWidth(107);
		tableColumn_18.setText("ModifiedAt");
		
		TableColumn tableColumn_19 = new TableColumn(table_3, SWT.LEFT);
		tableColumn_19.setWidth(117);
		tableColumn_19.setText("Description");
		
		TableColumn tableColumn_20 = new TableColumn(table_3, SWT.LEFT);
		tableColumn_20.setWidth(107);
		tableColumn_20.setText("Instance");
		
		TableColumn tableColumn_21 = new TableColumn(table_3, SWT.LEFT);
		tableColumn_21.setWidth(107);
		tableColumn_21.setText("Universe");
		
		
		
		Label totalSizeLabelOf3 = new Label(group1OfPage3, SWT.NONE);
		totalSizeLabelOf3.setBounds(33, 526, 197, 15);
		totalSizeLabelOf3.setText("Total Size:");
		
		Label totalItemsOf3 = new Label(group1OfPage3, SWT.NONE);
		totalItemsOf3.setBounds(336, 526, 152, 15);
		totalItemsOf3.setText("Items Found");
		
		Button exportButtonOf3 = new Button(group1OfPage3, SWT.NONE);
		exportButtonOf3.addSelectionListener(new SelectionAdapter() {
			/**
			 * 导出按钮
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				
				//****************************************************
					
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
						 * 选择导出路径
						 */
						DirectoryDialog dialog = new DirectoryDialog(getShell());
						dialog.setText("	BusinessObjects Locatator");
						dialog.setMessage("  Please select the location you want to export");
						dialog.setFilterPath("C://");
						String saveFile = dialog.open();
						
						dataset = RetriveUtil.retriveAllUnusedWithCriteria(enterpriseSession,"");
						if (saveFile != null) {
							File directiory = new File(saveFile);
							System.out.println(directiory.getPath());
							ExportCsvUtil.exportToCsv(directiory.getPath()+"//BueinessObject XI 3.1 Data.csv", header, dataset);
						}
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
						System.out.println("导出成功�?");
				//***********************************************
				
			}
		});
		exportButtonOf3.setBounds(661, 521, 111, 25);
		exportButtonOf3.setText("ExportAsCSV");
		
		Label lblNewLabel_6 = new Label(group1OfPage3, SWT.NONE);
		lblNewLabel_6.setBounds(94, 526, 55, 15);
		
		Button btnNewButton_3 = new Button(group1OfPage3, SWT.NONE);
		btnNewButton_3.setForeground(SWTResourceManager.getColor(30, 144, 255));
		btnNewButton_3.setFont(SWTResourceManager.getFont("Courier New", 12, SWT.BOLD));
		btnNewButton_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//第三个叶签的search
				System.out.println("jinlaile");

				// Type : webi universe connection
				// connectionRadioButton universeRadioButton
				// webiRadioButton.getSelection()
				List<?> rowDetail = null;
				
				//*************清理工作****************************
				/**
				 * 每次查询要清空，不然会累�?
				 */
				RetriveUtil.totalSize = 0;
				RetriveUtil.count = 0;

				int columns = table_3.getColumns().length;
				for (int i = 0; i < columns; i++) {
					table_3.removeAll();
				}		
               //***********************************************
				//要和第三个叶签的radiobutton对应
				//connectionRadioButtonOf3  universeRadioButtonOf3
				String type = (connectionRadioButtonOf3.getSelection()? "connection":(universeRadioButtonOf3.getSelection()? "universe":""));
				
				//publicFolderRadioButtonOf3  personalFolderRadioButtonOf3 specifiedFolderOf3
				String folder = (personalFolderRadioButtonOf3.getSelection()? "personal":(publicFolderRadioButtonOf3.getSelection()? "public":(specifiedFolderOf3.getSelection()? text_Folder.getText():"")));
				
				if(type.equals("universe")){
					rowDetail = (List<?>)RetriveUtil.retriveUnusedUniverseWithCriteria(enterpriseSession, folder);
				}else if(type.equals("connection")){
					rowDetail = (List<?>)RetriveUtil.retriveUnusedConnectionWithCriteria(enterpriseSession, folder);
				}else{
					rowDetail = (List<?>)RetriveUtil.retriveUnusedUniverseWithCriteria(enterpriseSession, folder);
					List connectioinRowDetail = (List<?>)RetriveUtil.retriveUnusedConnectionWithCriteria(enterpriseSession, folder);
					rowDetail.addAll(connectioinRowDetail);
				}
				
				String[] oneRow;
				Iterator it = ((java.util.List) rowDetail).iterator();
				while (it.hasNext()) {
					oneRow = (String[]) it.next();
					System.out.println(oneRow.toString());

					/**
					 * 这里添加�?table里添加数据的代码，还没测导出excel数据可不可以用String[]
					 */
					TableItem tableItem = new TableItem(table_3, SWT.NONE);
					tableItem.setText(oneRow);
					RetriveUtil.count ++;
				}
				/*
				 * 
				 * 显示文件总共大小
				 */
				long size = RetriveUtil.totalSize;
				totalSizeLabelOf3.setText("Total Size: " + size );
				/**
				 * 这个label是显示共有多少个item
				 */
				totalItemsOf3.setText(RetriveUtil.count + " items found");
				
				
			}
		});
		btnNewButton_3.setBounds(667, 60, 114, 49);
		btnNewButton_3.setText("Search");

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
			MainUI window = new MainUI(enterpriseSession);
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
		newShell.setText("SAP Business Objects Upgrade Tool");
	}

	/**
	 * Return the initial size of the window.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(843, 652);
	}
}
