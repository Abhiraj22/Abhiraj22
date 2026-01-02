import com.sap.it.api.ITApi.ValueMappingApi; // Import ValueMappingApi interface from SAP IT API
import com.sap.it.api.ITApiFactory; // Import ITApiFactory for creating API instances
import com.sap.it.api.mapping.ValueMappingApi; // Import ValueMappingApi interface for value mapping operations
import com.sap.gateway.ip.core.customdev.util.Message; // Import Message class for handling integration messages

/* This code is a message processing script that uses the SAP Integration Suite's Value Mapping API to map logical email addresses to physical ones.
 author: David Reed
 */
 
def Message physicalEmailLookup(Message message) { // Define the processData method that takes a Message object and returns a Message object

    String sourcevalue = message.getProperty("logical") // Get the value associated with the "logical" key from the headers
    
    // Get ValueMapping API
    def api = ITApiFactory.getApi(ValueMappingApi.class,  null); // Create an instance of ValueMappingApi
    
    if (api == null) { // Check if the API instance was created successfully
        throw new Exception("Could not retrieve ValueMappingApi."); // Throw an exception if API retrieval failed
    }
    
    // Get the value from value mapping
    String value = api.getMappedValue("logicalemail",  "12",  sourcevalue,  "physicalemail", "12" ); // Perform value mapping

    if (value == null ) { // Check if the mapped value is null
        message.setProperty("subject_extension",  // Set a property on the message
            " - recipients not found. " + sourcevalue + " not_found in the VMG named \'VM_Email_PhysicalEmail\'. Check if a)entry exists,  b)entry misspelled or c)VMG is deployed.");
        message.setProperty("emailto",  "sapinterfacesteam@sunchemical.com"); // Set the email recipient to a default address
        //throw new Exception(sourcevalue + " not_found in the VMG named \'VM_Email_PhysicalEmail\'. Check if entry exists or VMG is deployed.");
    } else { // If a mapped value was found
        //Set Process Direct Address as Header
        message.setProperty("subject_extension",  ""); // Clear the subject extension
        message.setProperty("emailto",  value); // Set the email recipient to the mapped value	
    }
    return message; // Return the modified message
}
