package com.tika.barcode.constants;

public class QueryConstant {
	
	/*START- QUERY - INVENTORY SERVICE */
	public static final String CHECK_INVREC_INITIATE_INCERT_REQ =" select a.TRN_INV_REC_ID \r\n" + "from TRN_INVENTORY_RECONCILE a \r\n"
			+ "where a.ACCOUNT_ID=?1 and a.CREATED_USER=?2 and a.RECON_STATUS=?3 ";
	public static final String CHECK_INVREC_DET__ADD_OR_MOD_REQ ="select a.TRN_INV_REC_DETAIL_ID "
			+ "from TRN_INVENTORY_RECONCILE_DETAIL a \r\n"
			+ "where a.ITEM_CODE=?1 AND a.LOT_NO=?2 AND a.BATCH_NO=?3 AND a.EXPIRY_DATE=?4 AND a.QTY_IN_HAND=?5";
	public static final String RETRIVE_INVREC_INITIATE_PK ="select TOP 1 a.TRN_INV_REC_ID from TRN_INVENTORY_RECONCILE a "
			+ "	where a.ACCOUNT_ID=?1 AND a.CREATED_USER=?2  ORDER BY a.CREATED_DATE DESC ";
	public static final String RETRIVE_INVREC_DET_ADD_PK ="select TOP 1 b.TRN_INV_REC_DETAIL_ID from TRN_INVENTORY_RECONCILE_DETAIL b "
			+ "	where b.CREATED_USER=?1 AND b.TRN_INV_REC_ID=?2  ORDER BY b.CREATED_DATE DESC ";
	public static final String GET_INVREC_BY_ACCID_AND_USER ="select a.TRN_INV_REC_ID,a.ACCOUNT_ID,a.RECON_STATUS,a.RECON_NOTES,a.CREATED_USER,"
			+ " a.CREATED_DATE,a.UPDATED_USER,a.UPDATED_DATE from TRN_INVENTORY_RECONCILE a "
			+ "	where a.CREATED_USER=?1 ";
	public static final String GET_INVREC_DET_BY_STATUS_CLOSED_AND_SUBMIT_AND_CREATED_USER =" select distinct b.ACCOUNT_ID,c.ACCOUNT_NAME,b.TRN_INV_REC_ID,"
			+ "b.REC_CYCLE_ID,b.CREATED_DATE,b.RECON_CLOSED_DATE,b.RECON_STATUS\r\n"
			+ "from TRN_INVENTORY_RECONCILE b \r\n"
			+ "left join TRN_INVENTORY_RECONCILE_DETAIL a on (a.TRN_INV_REC_ID=b.TRN_INV_REC_ID)\r\n"
			+ "join DIM_ACCOUNT c on (b.ACCOUNT_ID=c.ACCOUNT_ID)\r\n"
			+ "where b.RECON_STATUS IN ('Submit','Closed') and b.CREATED_USER=?1";
	public static final String SELECT_INREC_DET =" select  b.ACCOUNT_ID,c.ACCOUNT_NAME,"
			+ "a.TRN_INV_REC_ID,12345 TRN_INV_REC_DETAIL_ID,a.BATCH BATCH_NO,"
			+ "a.ITEM_ID,a.MATERIAL_KEY ITEM_CODE,a.LOT_NO,b.REC_CYCLE_ID,b.CREATED_DATE,a.Total_Stock QTY_IN_HAND,B.RECON_CLOSED_DATE,b.RECON_STATUS\r\n"
			+ "from FCT_CONSIGNMENT_INVENTORY a \r\n"
			+ "join TRN_INVENTORY_RECONCILE b on (a.TRN_INV_REC_ID=b.TRN_INV_REC_ID)\r\n"
			+ "join DIM_ACCOUNT c on (b.ACCOUNT_ID=c.ACCOUNT_ID)\r\n";
	public static final String PDF_INVREC_DET_BY_STATUS_CLOSED_AND_TRN_INVREC_ID =" select  b.ACCOUNT_ID,c.ACCOUNT_NAME,\r\n"
			+ "	a.MATERIAL_KEY ITEM_CODE,d.ITEM_DESC1, a.LOT_NO,b.CREATED_DATE,b.RECON_CLOSED_DATE,\r\n"
			+ "	a.EXPIRY_DATE ,a.Total_Stock QTY_IN_HAND\r\n"
			+ "	from FCT_CONSIGNMENT_INVENTORY a \r\n"
			+ "	join TRN_INVENTORY_RECONCILE b on (a.TRN_INV_REC_ID=b.TRN_INV_REC_ID)\r\n"
			+ "	join DIM_ACCOUNT c on (b.ACCOUNT_ID=c.ACCOUNT_ID)\r\n"
			+ "	left join DIM_ITEM d on (a.ITEM_ID = d.ITEM_ID)\r\n"
			+ "	where b.RECON_STATUS IN ('Closed') AND a.TRN_INV_REC_ID =?1";
	
	public static final String INVREC_DET_BY_STATUS_CLOSED_AND_TRN_INVREC_ID =" select  b.ACCOUNT_ID,c.ACCOUNT_NAME,a.TRN_INV_REC_ID,'',a.BATCH_NO,\r\n"
			+ "	a.MATERIAL_KEY ITEM_CODE,d.ITEM_DESC1, a.LOT_NO,b.CREATED_DATE,b.RECON_CLOSED_DATE,\r\n"
			+ "	a.EXPIRY_DATE ,a.Total_Stock QTY_IN_HAND\r\n"
			+ "	from FCT_CONSIGNMENT_INVENTORY a \r\n"
			+ "	join TRN_INVENTORY_RECONCILE b on (a.TRN_INV_REC_ID=b.TRN_INV_REC_ID)\r\n"
			+ "	join DIM_ACCOUNT c on (b.ACCOUNT_ID=c.ACCOUNT_ID)\r\n"
			+ "	left join DIM_ITEM d on (a.ITEM_ID = d.ITEM_ID)\r\n"
			+ "	where b.RECON_STATUS IN ('Closed') AND a.TRN_INV_REC_ID =?1";
	
	public static final String PDF_INVREC_MISSED_DETAILS_FCT =" select  b.ACCOUNT_ID,c.ACCOUNT_NAME,b.MATERIAL_KEY ITEM_NUMBER,d.ITEM_DESC1 ITEM_NAME,\r\n"
			+ "	b.LOT_NO,b.Total_Stock from \r\n"
			+ "	FCT_CONSIGNMENT_INVENTORY b\r\n"
			+ "	join DIM_ACCOUNT c on (b.ACCOUNT_ID=c.ACCOUNT_ID) \r\n"
			+ "	left join DIM_ITEM d on (b.ITEM_ID = d.ITEM_ID)\r\n"
			+ " where TRN_INV_REC_ID IS NULL and b.ACCOUNT_ID=?1";

	public static final String SELECT_MAIL_DIM_USER_BY_NAME ="select USER_EMAIL \r\n"
			+ "from DIM_USER \r\n"
			+ "where USER_NAME=?1";
	public static final String SELECT_INV_REC_BY_INV_REC_ID =" select b.ACCOUNT_NAME\r\n" 
			+ " from TRN_INVENTORY_RECONCILE a \r\n"
			+ "	join DIM_ACCOUNT b on (a.ACCOUNT_ID=b.ACCOUNT_ID)\r\n"
			+ " where a.TRN_INV_REC_ID=?1 ";
	/*END- QUERY - INVENTORY SERVICE */
	
	/*START- QUERY - ACCOUNT SERVICE */
	public static final String SELECT_ACC_LIST_BY_USERNAME ="select b.ACCOUNT_ID,b.ACCOUNT_NAME,b.ADDRESS1,"
			+ "b.ADDRESS2,b.CITY,b.[STATE],b.ZIP,\r\n"
			+ "c.TERRITORY_ID,c.TERRITORY_CD,c.TERRITORY_NAME,\r\n"
			+ "concat(FIRST_NAME,' ',LAST_NAME) REP_NAME,u.USER_NAME USER_LOGIN,b.type\r\n"
			+ "from DIM_ACCOUNT b \r\n"
			+ "join XREF_TERR_ALIGNMNT x on (b.ACCOUNT_ID=x.ACCOUNT_ID)\r\n"
			+ "join DIM_TERRITORY c on (x.TERRITORY_ID=c.TERRITORY_ID)\r\n"
			+ "left join DIM_USER u on (c.USER_ID=u.USER_ID)\r\n"
			+ "where u.USER_NAME=?1";
	public static final String SELECT_ACC_DET_LIST_BY_USERNAME ="select b.ACCOUNT_ID,b.ACCOUNT_NAME,b.ADDRESS1,b.ADDRESS2,b.CITY,b.[STATE],"
			+ "b.ZIP,d.ITEM_ID,d.ITEM_NUMBER MATERIAL_KEY,d.ITEM_DESC1,a.Batch,a.LOT_NO,"
			+ "a.[Expiry_Date],a.Total_Stock QTY_IN_HAND\r\n"
			+ ",concat(FIRST_NAME,' ',LAST_NAME) REP_NAME,u.USER_NAME USER_LOGIN\r\n"
			+ "from FCT_CONSIGNMENT_INVENTORY a\r\n"
			+ "join DIM_ACCOUNT b on (a.ACCOUNT_ID=b.ACCOUNT_ID)\r\n"
			+ "join DIM_TERRITORY c on (a.TERRITORY_ID=c.TERRITORY_ID)\r\n"
			+ "join DIM_ITEM d on (a.ITEM_ID=d.ITEM_ID)\r\n"
			+ "left join DIM_USER u on (c.USER_ID=u.USER_ID)\r\n"
			+ "where u.USER_NAME=?1 and a.LOT_NO is not null";
	
	public static final String SELECT_ACC_LIST_NBEW_IOS ="select distinct b.ACCOUNT_ID,b.ACCOUNT_NAME,b.ADDRESS1,"
			+ "b.ADDRESS2,b.CITY,b.[STATE],b.ZIP,\r\n"
			+ "c.TERRITORY_ID,c.TERRITORY_CD,c.TERRITORY_NAME,\r\n"
			+ "concat(FIRST_NAME,' ',LAST_NAME) REP_NAME,u.USER_NAME USER_LOGIN \r\n"
			+ "from DIM_ACCOUNT b \r\n"
			+ "join FCT_CONSIGNMENT_INVENTORY FCT on b.ACCOUNT_ID = FCT.ACCOUNT_ID \r\n"
			+ "join DIM_TERRITORY c on (FCT.TERRITORY_ID=c.TERRITORY_ID)\r\n"
			+ "left join DIM_USER u on (c.USER_ID=u.USER_ID)\r\n"
			+" where u.USER_NAME is not null AND c.TERRITORY_NAME not like '% RO %'";
	
	public static final String SELECT_ACC_DET_BY_ACCID ="select b.ACCOUNT_ID,b.ACCOUNT_NAME,b.ADDRESS1,b.ADDRESS2,"
			+ "b.CITY,b.STATE,b.ZIP,\r\n"
			+ "d.ITEM_ID,d.ITEM_NUMBER MATERIAL_KEY,d.ITEM_DESC1,a.Batch,'AB1235' LOT_NO,a.Expiry_Date,"
			+ "a.Total_Stock QTY_IN_HAND\r\n" + "from FCT_CONSIGNMENT_INVENTORY a \r\n"
			+ "join DIM_ACCOUNT b on (a.ACCOUNT_ID=b.ACCOUNT_ID)\r\n"
			+ "join DIM_TERRITORY c on (a.TERRITORY_ID=c.TERRITORY_ID)\r\n"
			+ "join DIM_ITEM d on (a.ITEM_ID=d.ITEM_ID)\r\n" + "where b.ACCOUNT_ID=?1";
	
	public static final String PAG_SELECT_ACC_LIST_BY_TER_ID_943 ="select b.ACCOUNT_ID,b.ACCOUNT_NAME,b.ADDRESS1,b.ADDRESS2,b.CITY,b.[STATE],b.ZIP,"
			+ "c.TERRITORY_ID,c.TERRITORY_CD,c.TERRITORY_NAME"
			+ "from DIM_ACCOUNT b "
			+ "join XREF_TERR_ALIGNMNT x on (b.ACCOUNT_ID=x.ACCOUNT_ID) "
			+ "join DIM_TERRITORY c on (x.TERRITORY_ID=c.TERRITORY_ID) "
			+ "where c.TERRITORY_ID=943";
	
	public static final String PAG_SELECT_COUNT_ACC_LIST_BY_TER_ID_943 ="select COUNT(*) "
			+ "from DIM_ACCOUNT b "
			+ "join XREF_TERR_ALIGNMNT x on (b.ACCOUNT_ID=x.ACCOUNT_ID) "
			+ "join DIM_TERRITORY c on (x.TERRITORY_ID=c.TERRITORY_ID) "
			+ "where c.TERRITORY_ID=943";
	public static final String PAG_WHERE_ACC_NAME_LIKE =" WHERE b.ACCOUNT_NAME LIKE ";
	
	public static final String PAG_SELECT_ACC_DET_BY_ACC_ID ="select b.ACCOUNT_ID,b.ACCOUNT_NAME,b.ADDRESS1,b.ADDRESS2,"
			+ "b.CITY,b.STATE,b.ZIP,\r\n"
			+ "d.ITEM_ID,d.ITEM_NUMBER MATERIAL_KEY,d.ITEM_DESC1,a.Batch,'AB1235' LOT_NO,a.Expiry_Date,"
			+ "a.Total_Stock QTY_IN_HAND\r\n" + "from FCT_CONSIGNMENT_INVENTORY a \r\n"
			+ "join DIM_ACCOUNT b on (a.ACCOUNT_ID=b.ACCOUNT_ID)\r\n"
			+ "join DIM_TERRITORY c on (a.TERRITORY_ID=c.TERRITORY_ID)\r\n"
			+ "join DIM_ITEM d on (a.ITEM_ID=d.ITEM_ID)\r\n" + "where b.ACCOUNT_ID=?1";
	public static final String PAG_SELECT_COUNT_ACC_DET_BY_ACC_ID ="select COUNT(*) from FCT_CONSIGNMENT_INVENTORY a \r\n"
			+ "join DIM_ACCOUNT b on (a.ACCOUNT_ID=b.ACCOUNT_ID)\r\n"
			+ "join DIM_TERRITORY c on (a.TERRITORY_ID=c.TERRITORY_ID)\r\n"
			+ "join DIM_ITEM d on (a.ITEM_ID=d.ITEM_ID)\r\n" + "where b.ACCOUNT_ID=?1";
	
	/*END- QUERY - ACCOUNT SERVICE */
	
	
	/*START- QUERY - CONSIGNMENT SERVICE */
	
	public static final String PAG_SELECT_FCT_CONS_INV ="SELECT fci.ACCOUNT_ID,\r\n"
			+ "      fci.TERRITORY_ID\r\n"
			+ "      ,fci.ITEM_ID\r\n"
			+ "      ,fci.Batch\r\n"
			+ "      ,fci.Expiry_Date\r\n"
			+ "      ,fci.Total_Stock\r\n"
			+ "      ,fci.MATERIAL_KEY\r\n"
			+ "      ,fci.CUSTOMER\r\n"
			+ "      ,fci.CUSTOMER_NAME\r\n"
			+ "      ,fci.RFRSH_DATE\r\n"
			+ "  FROM FCT_CONSIGNMENT_INVENTORY fci";
	public static final String PAG_SELECT_COUNT_FCT_CONS_INV ="SELECT COUNT(*) "
			+ "  FROM FCT_CONSIGNMENT_INVENTORY fci";
	public static final String PAG_WHERE_FCT_CONS_INV ="  WHERE fci.ACCOUNT_ID=?1";
	
	public static final String GET_ALL_FCT_CONSIGNMENT_INVENTORY ="SELECT fci.MONTH_ID\r\n"
			+ "      ,fci.ACCOUNT_ID\r\n"
			+ "      ,fci.TERRITORY_ID\r\n"
			+ "      ,fci.ITEM_ID\r\n"
			+ "      ,fci.Batch\r\n"
			+ "      ,fci.Expiry_Date\r\n"
			+ "      ,fci.Total_Stock\r\n"
			+ "      ,fci.MATERIAL_KEY\r\n"
			+ "      ,fci.CUSTOMER\r\n"
			+ "      ,fci.CUSTOMER_NAME\r\n"
			+ "      ,fci.LOT_NO\r\n"
			+ "      ,fci.RFRSH_DATE\r\n"
			+ "      ,fci.TRN_INV_REC_ID\r\n"
			+ "      ,fci.CREATED_USER\r\n"
			+ "      ,fci.CREATED_DATE\r\n"
			+ "  FROM FCT_CONSIGNMENT_INVENTORY fci";
	public static final String PAG_SELECT_COUNT_FCT_CONSIGNMENT_INVENTORY ="SELECT COUNT(*) "
			+ "  FROM FCT_CONSIGNMENT_INVENTORY fci";
	public static final String PAG_WHERE_FCT_CONSIGNMENT_INVENTORY ="  WHERE fci.MONTH_ID=?1";
	
	/*END- QUERY - CONSIGNMENT SERVICE */
	
	/*START- QUERY - ITEMDETAILS SERVICE */
	
	public static final String SELECT_ITEM_DETAILS_DIM_ITEM ="select ITEM_ID,ITEM_NUMBER, "
			+ "ITEM_DESC1  ITEM_NAME \r\n" + "from DIM_ITEM where ITEM_GROUP_NAME = 'Minerva' or \r\n"
					+ " PRODUCT_GROUP_NAME = 'Minerva'";
	
	public static final String GET_ALL_ITEM_DETAILS_DIM_ITEM ="select a.ITEM_ID, \r\n"
			+ "      a.ITEM_CODE, a.ITEM_NUMBER, a.ITEM_DESC1, a.ITEM_DESC2, a.ITEM_TYPE,  \r\n "
			+ "		 a.PROD_LINE, a.ITEM_GROUP_ID, a.ITEM_GROUP_NAME, \r\n"
			+ "		 a. PRODUCT_GROUP_ID, a.PRODUCT_GROUP_NAME, a.IMPLANT_TYPE, a.RFRSH_DATE \r\n"
			+ " 	from DIM_ITEM a ORDER BY a.ITEM_ID ASC";
	
	/*END- QUERY - ITEMDETAILS SERVICE */
	
	/*START- QUERY - SCAN SUPPORT SERVICE */
	
	public static final String GET_ALL_SCAN_SUPPORT ="select a.SUPPORT_ID,a.USER_EMAIL,"
			+ "a.ISSUE_DETAILS,a.ISSUE_STATUS,a.CREATE_USER,a.CREATE_DATE,a.UPDATE_USER,a.UPDATE_DATE"
			+ " from DIM_SCAN_SUPPORT a WHERE a.CREATE_USER=?1 ";
	
	public static final String GET_ALL_SCANSUPPORT ="select a.SUPPORT_ID,a.USER_EMAIL,"
			+ "a.ISSUE_DETAILS,a.ISSUE_STATUS,a.CREATE_USER,a.CREATE_DATE,a.UPDATE_USER,a.UPDATE_DATE"
			+ " from DIM_SCAN_SUPPORT a ORDER BY a.SUPPORT_ID ASC";
	
	public static final String RETRIVE_SCAN_SUPPORT_PK ="select TOP 1 a.SUPPORT_ID from DIM_SCAN_SUPPORT a "
			+ "	where a.CREATE_USER=?1 AND a.USER_EMAIL=?2 ORDER BY a.CREATED_DATE DESC ";
	
	/*END- QUERY - SCAN SUPPORT SERVICE */
	
	/*START- QUERY - DIM NOTIFICATION SERVICE */
	
	public static final String SELECT_NOTICATION_CONF_BY_NAME ="select n.NOTIFICATION_ID,n.NOTIFICATION_NAME,n.NOTIFICATION_SUBJECT,"
			+ "n.NOTIFICATION_BODY,n.NOTIFICATION_STATUS from DIM_NOTIFICATION_CONFIG n "
			+ "	where n.NOTIFICATION_NAME=?1 ";
	
	/*END- QUERY - DIM NOTIFICATION SERVICE */
	

	
	/*START- QUERY - */
		
	public static final String GET_ALL_XREF_TERR_ALIGNMNT ="SELECT xta.TERRITORY_ID\r\n"
			+ "      ,xta.ACCOUNT_ID\r\n"
			+ "      ,xta.RFRSH_DATE\r\n"
			+ "      ,xta.CREATE_USER\r\n"
			+ "      ,xta.OLD_TERR_ID\r\n"
			+ "      ,xta.UPDATE_DATE\r\n"
			+ "      ,xta.UPDATE_USER\r\n"
			+ "  FROM XREF_TERR_ALIGNMNT xta";
	

	public static final String GEL_ALL_COLLECTIVE_TRN_RECONCILE_LIST = 
		    "SELECT b.TRN_INV_REC_ID, c.TRN_INV_REC_DETAIL_ID, b.ACCOUNT_ID, c.ITEM_ID, c.ITEM_CODE, "
		    + "c.BATCH_NO, c.LOT_NO, c.EXPIRY_DATE, c.QTY_IN_HAND, c.MONTH_ID, b.RECON_STATUS, "
		    + "b.RECON_NOTES, b.REC_CYCLE_ID, b.RECON_CLOSED_DATE, b.CREATED_USER, b.CREATED_DATE, "
		    + "COALESCE(c.UPDATED_USER, b.UPDATED_USER) AS UPDATED_USER, "
		    + "COALESCE(c.UPDATED_DATE, b.UPDATED_DATE) AS UPDATED_DATE "
		    + "FROM TRN_INVENTORY_RECONCILE b "
		    + "LEFT JOIN TRN_INVENTORY_RECONCILE_DETAIL c ON b.TRN_INV_REC_ID = c.TRN_INV_REC_ID \r\n";
		
	public static final String GET_ALL_TERRITORY ="SELECT t.TERRITORY_ID \r\n"
			+ " 	,t.TERRITORY_CD, t.TERRITORY_NAME, t.REGION_ID, t.REGION_CD, t.REGION_NAME \r\n"
			+ "  ,t.AREA_ID, t.AREA_CD, t.AREA_NAME, t.COMPANY_ID, t.COMPANY_CD, t.COMPANY_NAME \r\n"
			+ "  ,t.ACTIVE_IND, t.USER_ID, t.CREATE_USER, t.CREATE_DT, t.UPDATE_USER, t.UPDATE_DT"
			+ "	,t.COUNTRY \r\n"
			+ "  FROM DIM_TERRITORY t ";
	
	public static final String SELECT_TOTAL_USER_ID_COUNT_DIM_USER = "SELECT COUNT(USER_ID) FROM DIM_USER";
	
	public static final String SELECT_TOTAL_ITEM_ID_COUNT_DIM_ITEM = "SELECT COUNT(ITEM_ID) FROM DIM_ITEM";
	
	public static final String TOTAL_ACC_ID_COUNT_FCT_CONSIGNMENT_INVENTORY = 
			"SELECT COUNT(ACCOUNT_ID) FROM FCT_CONSIGNMENT_INVENTORY";
	
	public static final String TOTAL_SUPPORT_ID_COUNT_DIM_SCAN_SUPPORT = 
			"SELECT COUNT(SUPPORT_ID) FROM DIM_SCAN_SUPPORT";
			
	

	/*END- QUERY -  */

}
