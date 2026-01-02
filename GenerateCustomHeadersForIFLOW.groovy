/* Refer the link below to learn more about the use cases of script.
https: //help.sap.com/viewer/368c481cd6954bdfa5d0435479fd4eaf/Cloud/en/148851bf8192412cba1f9d2c17f4bd25.html

If you want to know more about the SCRIPT APIs,  refer the link below
https: //help.sap.com/doc/a56f52e1a58e4e2bac7f7adbf45b2e26/Cloud/en/index.html */
import com.sap.gateway.ip.core.customdev.util.Message;

/**
 * Create by David Reed and Abhiraj Singh
 * February 10,  2025
 * A collection of functions to add custom headers to 
 * IFLOWS and trim leading zeros.
 */

/**
 * Removes leading zeros from document number stored in message properties
 * @param message The message object containing document number property
 * @return Modified message with trimmed document number
 */
def Message removeLeadingZerosFromDocNumber(Message message) {
    def docnbr = message.getProperty("documentnbr") as String
    docnbr = docnbr.replaceFirst("^0+", "")
    message.setProperty("documentnbr",  docnbr)
    return message
}

/**
 * Adds custom headers for document number, IDOC number and filename to message log
 * @param message The message object containing properties to be logged
 * @return Original message after logging
 */
def Message LogCustomHeader(Message message) {
    def docnbr = message.getProperty("documentnbr") as String
    def idocnbr = message.getProperty("idocnbr") as String
    def filenm = message.getProperty("filename") as String

    def messageLog = messageLogFactory.getMessageLog(message)
    if (docnbr != null) messageLog.addCustomHeaderProperty("DOCUMENT NBR",  docnbr)
    if (idocnbr != null) messageLog.addCustomHeaderProperty("IDOC NBR",  idocnbr)
    if (filenm != null) messageLog.addCustomHeaderProperty("FILE NAME",  filenm)
    
    return message
}

/**
 * Logs SAP IDOC Database ID from message headers
 * @param message The message object containing SAP IDOC DB ID header
 * @return Original message after logging
 */
def Message LogSAPIDOCDbId(Message message) {
    def headers = message.getHeaders()
    def value = headers.get("SapIDocDbId")
    def messageLog = messageLogFactory.getMessageLog(message)
    if (value != null) messageLog.addCustomHeaderProperty("SAP IDOC DB ID",  value)
    return message
}

/**
 * Removes leading zeros from IDOC number stored in message properties
 * @param message The message object containing IDOC number property
 * @return Modified message with trimmed IDOC number
 */
def Message removeLeadingZerosFromIDOCNumber(Message message) {
    def matnbr = message.getProperty("idocnbr") as String
    matnbr = matnbr.replaceFirst("^0+", "")
    message.setProperty("idocnbr",  matnbr)
    return message
}

/**
 * Logs only the filename from message properties
 * @param message The message object containing filename property
 * @return Original message after logging
 */
def Message LogFileNameONLY(Message message) {
    def filename = message.getProperty("filename") as String 
    def messageLog = messageLogFactory.getMessageLog(message)
    messageLog.addCustomHeaderProperty("FILE NAME",  filename)
    return message
}

/**
 * Logs text name and value from message properties
 * @param message The message object containing text properties
 * @return Original message after logging
 */
def Message LogText(Message message) {
    def messageLog = messageLogFactory.getMessageLog(message)
    
    // Text Property 1
    def text1 = message.getProperty("text1") as String 
    if (text1 != null && !text1.trim().isEmpty()) {
        messageLog.addCustomHeaderProperty("TEXT1",  text1)
    }
    
    // Text Property 2
    def text2 = message.getProperty("text2") as String 
    if (text2 != null && !text2.trim().isEmpty()) {
        messageLog.addCustomHeaderProperty("TEXT2",  text2)
    }
    
    // Text Property 3
    def text3 = message.getProperty("text3") as String 
    if (text3 != null && !text3.trim().isEmpty()) {
        messageLog.addCustomHeaderProperty("TEXT3",  text3)
    }
    
    // Text Property 4
    def text4 = message.getProperty("text4") as String 
    if (text4 != null && !text4.trim().isEmpty()) {
        messageLog.addCustomHeaderProperty("TEXT4",  text4)
    }
    
    // Text Property 5
    def text5 = message.getProperty("text5") as String 
    if (text5 != null && !text5.trim().isEmpty()) {
        messageLog.addCustomHeaderProperty("TEXT5",  text5)
    }
    
    // Text Property 6
    def text6 = message.getProperty("text6") as String 
    if (text6 != null && !text6.trim().isEmpty()) {
        messageLog.addCustomHeaderProperty("TEXT6",  text6)
    }
    
    // Text Property 7
    def text7 = message.getProperty("text7") as String 
    if (text7 != null && !text7.trim().isEmpty()) {
        messageLog.addCustomHeaderProperty("TEXT7",  text7)
    }
    
    // Text Property 8
    def text8 = message.getProperty("text8") as String 
    if (text8 != null && !text8.trim().isEmpty()) {
        messageLog.addCustomHeaderProperty("TEXT8",  text8)
    }
    
    // Text Property 9
    def text9 = message.getProperty("text9") as String 
    if (text9 != null && !text9.trim().isEmpty()) {
        messageLog.addCustomHeaderProperty("TEXT9",  text9)
    }
    
    // Text Property 10
    def text10 = message.getProperty("text10") as String 
    if (text10 != null && !text10.trim().isEmpty()) {
        messageLog.addCustomHeaderProperty("TEXT10",  text10)
    }
    
    return message
}


/**
 * Logs SFTP receiver file and folder information
 * @param message The message object containing filename and foldername properties
 * @return Original message after logging
 */
def Message LogSFTPReceiverFileFolder(Message message) {
    def filename = message.getProperty("filename") as String
    def foldername = message.getProperty("foldername") as String 
    def messageLog = messageLogFactory.getMessageLog(message)
    messageLog.addCustomHeaderProperty("FILE NAME",  filename)
    messageLog.addCustomHeaderProperty("FOLDER NAME",  foldername)
    return message
}

/**
 * Logs email recipient information and logical destination
 * @param message The message object containing email properties
 * @return Original message after logging
 */
def Message LogEmailTo(Message message) {
    map = message.getHeaders()
    String sourcevalue = map.get("logical")
    def prop = message.getProperties()
    def Email = prop.get("emailto")
    def messageLog = messageLogFactory.getMessageLog(message)
    if (messageLog != null) {
        messageLog.addCustomHeaderProperty("Email_To",  Email)
        messageLog.addCustomHeaderProperty("Logical_To",  sourcevalue)
    }
    return message
}
