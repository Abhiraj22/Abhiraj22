/* Refer the link below to learn more about the use cases of script.
https://help.sap.com/viewer/368c481cd6954bdfa5d0435479fd4eaf/Cloud/en/148851bf8192412cba1f9d2c17f4bd25.html

If you want to know more about the SCRIPT APIs, refer the link below
https://help.sap.com/doc/a56f52e1a58e4e2bac7f7adbf45b2e26/Cloud/en/index.html */
import com.sap.gateway.ip.core.customdev.util.Message;

def Message addAttachmentAsString(Message message) {
    // Extract the message body as String type from the incoming message object
    def body = message.getBody(java.lang.String)
    
    // Get message logging instance using factory pattern for the current message
    // This is typically used for monitoring and debugging purposes
    def messageLog = messageLogFactory.getMessageLog(message)
    
    // Check if messageLog instance was successfully created
    if (messageLog != null) {
        // Add the message body as a text attachment
        // Parameters:
        //   1. 'My Attachment' - name of the attachment
        //   2. body - the content (message body) to attach
        //   3. 'text/plain' - MIME type of the attachment
        messageLog.addAttachmentAsString('Last Message',  body,  'text/plain')
    }
    
    // Return the original message unchanged
    // This allows for further processing in the integration flow
    return message
}

/* Write all properties having Log to the MPL attachment*
 */
def Message writeLog(Message message) {
    // Get all properties from the message as a map
    def map = message.getProperties()
    
    // Get message logging instance for monitoring
    def messageLog = messageLogFactory.getMessageLog(message)
 
    // If logging is available, process the properties
    if(messageLog != null) {
        // Iterate through each property in the map
        map.each{ entry ->
            // Check if the property key contains "Log"
            if (entry.key.contains("Log")) {
                // Add property as attachment with key as name and value as content
                messageLog.addAttachmentAsString(
                    entry.key.toString(),    // Attachment name
                    entry.value.toString(),  // Attachment content
                    "text/plain"            // MIME type
                )
            }
        }
    }
    
    // Return the original message unchanged
    return message
}
