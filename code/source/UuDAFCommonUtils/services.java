package UuDAFCommonUtils;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.util.*;
import java.io.*;
import java.text.*;
// --- <<IS-END-IMPORTS>> ---

public final class services

{
	// ---( internal utility methods )---

	final static services _instance = new services();

	static services _newInstance() { return new services(); }

	static services _cast(Object o) { return (services)o; }

	// ---( server methods )---




	public static final void throwError (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(throwError)>> ---
		// @sigtype java 3.5
		// [i] field:0:required errorMessage
		IDataCursor idcPipeline = pipeline.getCursor();
		
		String strErrorMessage = null;
		if (idcPipeline.first("errorMessage"))
		{
			strErrorMessage = (String)idcPipeline.getValue();
		}
		
				
		idcPipeline.destroy();
		
		
		throw new ServiceException(strErrorMessage);
			
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	private static java.util.Date w3cTimestampToDateObj(String iW3CDateTimeString)
		    throws java.text.ParseException
		{
		 String[] strList = splitTimeZone(iW3CDateTimeString);
		    String dtString = strList[0];
		    String tzString = strList[1];
		    
		    // handle datetime part
		    String dateTimePattern = null;
		    int idxDot = dtString.indexOf(".");        
		    if ( idxDot > -1 ) // handle subsecond
		    {
		        String subsecond = dtString.substring(idxDot);
		        float subSec = Float.parseFloat(subsecond);
		        int millisecond = Math.round(subSec * 1000);
		        // Java can handle only millisecond precision
		        dtString = dtString.substring(0,idxDot) + "." + millisecond;
		        dateTimePattern = "yyyy-MM-dd'T'HH:mm:ss.SSS";
		    }
		    else 
		    {
		        dateTimePattern = "yyyy-MM-dd'T'HH:mm:ss";
		    }
		    
		    // handle time zone part
		    String timeZonePattern = "Z";
		    if ( tzString.equals("Z") )
		    {
		        tzString = "-0000";  // set to UTC
		    } 
		    else if ( ! tzString.equals("") )  // (+|-)hh:mm
		    {
		        tzString = tzString.replaceAll(":", "");  // change hh:mm to hhmm
		    }
		    else // no time zone
		    {
		        timeZonePattern = "";
		    }
		    
		    // parse to Date obj
		    SimpleDateFormat sdf = new SimpleDateFormat(dateTimePattern + timeZonePattern);
		    return sdf.parse(dtString + tzString);
		}
		
		private static String[] splitTimeZone(String iW3CDateTimeString)
		{
			 iW3CDateTimeString = iW3CDateTimeString.trim();
			    int idxTZ = getIndexOfTimeZone(iW3CDateTimeString);
			    String[] res = new String[2];
			    if ( idxTZ > -1 )
			    {
			        res[0] = iW3CDateTimeString.substring(0,idxTZ);
			        res[1] = iW3CDateTimeString.substring(idxTZ);
			    }
			    else
			    {
			        res[0] = iW3CDateTimeString;
			        res[1] = "";
			    }
			    return res;
		}
		
		private static int getIndexOfTimeZone(String iW3CDateTimeString)
		{
	
		    int idxTZ = -1;
		    int idxT = iW3CDateTimeString.indexOf("T");
		    int idxPlus = iW3CDateTimeString.indexOf("+", idxT);
		    int idxMinus = iW3CDateTimeString.indexOf("-", idxT);
		    int idxZ = iW3CDateTimeString.indexOf("Z", idxT);
		    
		    if ( idxPlus > -1 )
		    {
		        idxTZ = idxPlus;
		    }
		    else if ( idxMinus > -1 )
		    {
		        idxTZ = idxMinus;
		    }
		    else if ( idxZ > -1 )
		    {
		        idxTZ = idxZ;
		    }
		    return idxTZ;
		}
		
	// --- <<IS-END-SHARED>> ---
}

