/**
 * SAP Cloud Integration - RFC Execution Script
 * Author: David Reed
 * Date: August 13, 2025
 */

// Import required classes for SAP Cloud Integration message processing
import com.sap.gateway.ip.core.customdev.util.Message
// Import JCo (Java Connector) classes for SAP RFC communication
// Import JCo (Java Connector) classes for SAP RFC communication
import com.sap.conn.jco.JCoDestination
import com.sap.conn.jco.JCoDestinationManager
import com.sap.conn.jco.JCoException
import com.sap.conn.jco.JCoFunction


/**
 * Executes an RFC (Remote Function Call) in SAP system
 * @param message The integration message containing RFC parameters and configuration
 * @return Modified message with RFC execution results
 */
def Message processRFCCall(Message message) {
    // Extract RFC destination and function name from message properties
    def destinationName = message.getProperty("RFCDestination")
    def rfcName = message.getProperty("RFCName")

    try {
        // Get the JCo destination representing the SAP system connection
        JCoDestination jcoDestination = JCoDestinationManager.getDestination(destinationName)
        // Retrieve the RFC function metadata from the SAP system
        JCoFunction function = jcoDestination.getRepository().getFunction(rfcName)

        if (function != null) {
            // Get the import parameter list (input parameters) for the RFC
            def parameterList = function.getImportParameterList()

            // Iterate through message properties to find and set RFC input parameters
            message.getProperties().each { key,  value ->
                // Look for properties prefixed with "InputParameter_"
                if (key.startsWith("InputParameter_")) {
                    // Extract the actual parameter name by removing the prefix
                    def paramName = key.substring("InputParameter_".length())
                    try {
                        // Set the RFC parameter value
                        parameterList.setValue(paramName,  value)
                    } catch (Exception e) {
                        // Throw exception if parameter setting fails
                        throw new Exception("Error setting parameter '${paramName}':  ${e.getMessage()}")
                    }
                }
            }

            // Execute the RFC function in the SAP system
            function.execute(jcoDestination)

            // Convert the RFC result to XML format
            def result = function.toXML()
            // Set the XML result as the message body
            message.setBody(result)
        } else {
            // Throw exception if RFC function is not found in SAP system
            throw new Exception("ERROR:  RFC function '${rfcName}' not found")
        }

    } catch (JCoException e) {
        // Handle SAP-specific exceptions
        throw new Exception("ERROR executing RFC function:  ${e.getMessage()}")
    } catch (Exception e) {
        // Handle all other unexpected exceptions
        throw new Exception("Unexpected error:  ${e.getMessage()}")
    }

    // Return the modified message containing the RFC result
    return message
}
