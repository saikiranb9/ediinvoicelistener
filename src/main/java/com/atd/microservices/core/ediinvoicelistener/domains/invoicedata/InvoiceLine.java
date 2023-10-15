package com.atd.microservices.core.ediinvoicelistener.domains.invoicedata;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@Document(collection = "810_LINE")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InvoiceLine {
 	@Field("EDI_LINE_ID")
    private Long ediLineId;

 	@Field("ATTRIBUTE1")
    public String attribute1;
    
    @Field("ATTRIBUTE2")
    public String attribute2;
    
    @Field("ATTRIBUTE3")
    public String attribute3;
    
    @Field("ATTRIBUTE4")
    public String attribute4;
    
    @Field("ATTRIBUTE5")
    public String attribute5;
    
    @Field("CUST_ACCOUNT_ID")
    private Long custAccountId;
    
    @Field("DOC_TYPE")
    private String docType;
    
    @Field("EDI_HEADER_ID")
    private Long ediHeaderId;
    
    @Field("FET")
    private String fet;
    
    @Field("GL_CODE")
    private String glCode;
    
    @Field("ITEM_DESCRIPTION")
    private String itemDescription;
    
    @Field("ITEM_UNIT_FET")
    private String itemUnitFet;
    
    @Field("LINE_AMOUNT")
    private String lineAmount;
    
    @Field("LINE_ATTRIBUTE1")
    private String lineAttribute1;
    
    @Field("LINE_ATTRIBUTE10_1")
    private String lineAttribute10_1;
    
    @Field("LINE_ATTRIBUTE10_2")
    private String lineAttribute10_2;
    
    @Field("LINE_ATTRIBUTE10_3")
    private String lineAttribute10_3;
    
    @Field("LINE_ATTRIBUTE11_1")
    private String lineAttribute11_1;
    
    @Field("LINE_ATTRIBUTE11_2")
    private String lineAttribute11_2;
    
    @Field("LINE_ATTRIBUTE11_3")
    private String lineAttribute11_3;
    
    @Field("LINE_ATTRIBUTE12_1")
    private String lineAttribute12_1;
    
    @Field("LINE_ATTRIBUTE12_2")
    private String lineAttribute12_2;
    
    @Field("LINE_ATTRIBUTE8")
    private String lineAttribute8;
    
    @Field("LINE_ATTRIBUTE9_1")
    private String lineAttribute9_1;
    
    @Field("LINE_ATTRIBUTE9_2")
    private String lineAttribute9_2;
    
    @Field("LINE_ATTRIBUTE9_3")
    private String lineAttribute9_3;
    
    @Field("LINE_ATTRIBUTE9_4")
    private String lineAttribute9_4;
    
    @Field("LINE_ATTRIBUTE9_5")
    private String lineAttribute9_5;
    
    @Field("LINE_ATTRIBUTE9_6")
    private String lineAttribute9_6;
    
    @Field("LINE_ATTRIBUTE9_7")
    private String lineAttribute9_7;
    
    @Field("LINE_ATTRIBUTE9_8")
    private String lineAttribute9_8;
    
    @Field("LINE_ATTRIBUTE9_9")
    private String lineAttribute9_9;
    
    @Field("LINE_ITEM_ATTRIBUTE1")
    private String LineItemAttribute1;
    
    @Field("LINE_ITEM_NUMBER")
    private String lineItemNumber;
    
    @Field("LINE_NUMBER")
    private String lineNumber;
    
    @Field("LIST_PRICE")
    private String listPrice;
    
    @Field("ORDER_NUMBER")
    private String orderNumber;
    
    @Field("ORIG_PURCHASE_ORDER")
    private String origPurchaseOrder;
    
    @Field("PLANNED_ARRIVAL")
    private String plannedArrival;
    
    @Field("PO_LINE_NUMBER")
    private String poLineNumber;
    
    @Field("PROCESS_FLAG")
    private String processFlag;
    
    @Field("PROCESS_MESSAGE")
    private String processMessage;
    
    @Field("QTY_DELIVERED")
    private String qtyDelivered;
    
    @Field("QUANTITY_ORDERED")
    private String quantityOrdered;
    
    @Field("RECORD_TYPE")
    private String recordType;
    
    @Field("REM_LINE_AMOUNT")
    private String remLineAmount;
    
    @Field("SIGNEE")
    private String signee;
    
    @Field("SKU_BRAND_NAME")
    private String skuBrandName;
    
    @Field("SO_CREATION_DATE")
    private String soCreationDate;
    
    @Field("SO_CREATION_DATE_1")
    private String soCreationDate1;
    
    @Field("SUPPLIER_ITEM_DESC")
    private String supplierItemDesc;
    
    @Field("SUPPLIER_ITEM_NUM")
    private String supplierItemNum;
    
    @Field("TIRE_DIAMETER")
    private String tireDiameter;
    
    @Field("TIRE_SIZE")
    private String tireSize;
    
    @Field("TP_TRANSLATOR_CODE")
    private String tpTranslatorCode;
    
    @Field("TRX_NUMBER")
    private String trxNumber;
    
    @Field("UNIT_SELLING_PRICE")
    private String unitSellingPrice;
    
    @Field("UNIT_SELLING_PRICE_1")
    private String unitSellingPrice1;
    
    @Field("UOM_CODE")
    private String uomCode;
    
    private List<InvoiceTax> invoiceTaxes;
	
	private List<InvoiceAllowance> invoiceAllowances;
    
}
