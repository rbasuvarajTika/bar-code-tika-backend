package com.tika.barcode.constants;

public class InventoryConstant {
	
	public static final String INV_COMMONAPI ="api/v1/inventory";
	public static final String INV_INITIATE ="/initiate";
	public static final String INV_ADD ="/add";
	public static final String INV_MOD ="/modify";
	public static final String INV_SUBMIT ="/submit";
	public static final String INV_INSERT ="/insert";
	public static final String INV_REC_BY_USER ="/get/reconcile";
	public static final String INV_REC_DETAIL ="/get/reconcile/detail";
	public static final String INV_REC_CLOSE_DETAIL ="/get/reconcile/closeDetails";
	public static final String INV_CLOSE ="/close";
	public static final String INV_CLOSE_DET_PDF ="/generate-pdf";
	public static final String SEND_PDF_EMAIL ="/send-pdf-email";
	public static final String INV_REC_COLLECTIVE_LIST ="/get/reconcile/collective/list";
	public static final String INV_UPDATE = "/update/inventory/details/{trnInvRecId}";

}
