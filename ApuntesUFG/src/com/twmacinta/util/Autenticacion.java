package com.twmacinta.util;

import java.io.IOException;

import net.rim.device.api.servicebook.ServiceBook;
import net.rim.device.api.servicebook.ServiceRecord;
import net.rim.device.api.system.CoverageInfo;
import net.rim.device.api.system.DeviceInfo;
import net.rim.device.api.system.WLANInfo;
import net.rim.device.api.ui.component.Dialog;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransport;
import org.xmlpull.v1.XmlPullParserException;

public class Autenticacion {
	
	String token   = "";
	String sid     = "";
	String usspwd  = "";
	String sidusspwd = "";
	String MD5usspwd = "";
	String MD5hash  = "";
	String matanga = getConnectionString();
	String ServiceUrl = "https://servicios.ufg.edu.sv/ws/apps.php"; 
	String ServiceNamespace = "http://servicios.ufg.edu.sv"; 
	String methodName = "getSID";
	
	
	
	public String validar(String usuario, String password){
		
				
				ServiceUrl = ServiceUrl+matanga;
			
		SoapObject rpc = new SoapObject(ServiceNamespace, methodName);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.bodyOut = rpc;  
        envelope.dotNet = true;
        envelope.encodingStyle = SoapSerializationEnvelope.ENC;
        HttpTransport ht = new HttpTransport(ServiceUrl);
        ht.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        ht.debug = true;
        
		try {
			ht.call(ServiceUrl + "/" + methodName, envelope);
			sid =  envelope.getResponse().toString();
			
			usspwd = usuario + password;
			byte plain[] = usspwd.getBytes();
			MD5 md5 = new MD5(plain);
			byte[] primeroDIOS = md5.doFinal();
			MD5usspwd = md5.toHex(primeroDIOS);
			
			sidusspwd = sid + MD5usspwd;
			byte plain2[] = sidusspwd.getBytes();
			MD5 md52 = new MD5(plain2);
			byte[] primeroDIOS2 = md52.doFinal();
			MD5hash = md52.toHex(primeroDIOS2);
			
			rpc 	   = null;
			envelope   = null;
			ht 		   = null;
			methodName = "";
			methodName = "getTokenMovil";
			
			rpc = new SoapObject(ServiceNamespace, methodName);
			rpc.addProperty("sid", sid);
	        rpc.addProperty("usuario", usuario);
	        rpc.addProperty("hash", MD5hash);
	        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	        envelope.bodyOut = rpc;  
	        envelope.dotNet = true;
	        envelope.encodingStyle = SoapSerializationEnvelope.ENC;
	        ht = new HttpTransport(ServiceUrl);
	        ht.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
	        ht.debug = true;
		
	        ht.call(ServiceUrl + "/" + methodName, envelope);
			token =  envelope.getResponse().toString();
			
		} catch (SoapFault e) {
			// TODO Auto-generated catch block
			Dialog.alert("Error: "+e);
			//e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Dialog.alert("Error: "+e);
			//e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			Dialog.alert("Error: "+e);
			//e.printStackTrace();
		}
		
		return token;
		
		}
	
	
	public static String getConnectionString() {
        String connectionString = null;                
                        
        // Simulator behavior is controlled by the USE_MDS_IN_SIMULATOR variable.
        if(DeviceInfo.isSimulator()){
            connectionString = ";deviceside=true";
        }                                                
        // Wifi is the preferred transmission method
        else if(WLANInfo.getWLANState() == WLANInfo.WLAN_STATE_CONNECTED){
            //logMessage("Device is connected via Wifi.");
            connectionString = ";interface=wifi";
        }                    
        // Is the carrier network the only way to connect?
        else if((CoverageInfo.getCoverageStatus() & CoverageInfo.COVERAGE_DIRECT) == CoverageInfo.COVERAGE_DIRECT)
        {             
        	String carrierUid = getCarrierBIBSUid();
            if(carrierUid == null) {
                // Has carrier coverage, but not BIBS.  So use the carrier's TCP network
                 connectionString = ";deviceside=true";
            } else {
                // otherwise, use the Uid to construct a valid carrier BIBS request
                connectionString = ";deviceside=false;connectionUID="+carrierUid + ";ConnectionType=mds-public";
            }
        }                
        // Check for an MDS connection instead (BlackBerry Enterprise Server)
        else if((CoverageInfo.getCoverageStatus() & CoverageInfo.COVERAGE_MDS) == CoverageInfo.COVERAGE_MDS){
            connectionString = ";deviceside=false";
        }
        // If there is no connection available abort to avoid bugging the user unnecssarily.
        else if(CoverageInfo.getCoverageStatus() == CoverageInfo.COVERAGE_NONE){
           // ServerException exception = new ServerException("No connection found");
           return "error";
        }
        // In theory, all bases are covered so this shouldn't be reachable.
        else{
            connectionString = ";deviceside=true";
        }        
        return connectionString;
    }
	
	private static String getCarrierBIBSUid(){
        ServiceRecord[] records = ServiceBook.getSB().getRecords();
        int currentRecord;
        
        for(currentRecord = 0; currentRecord < records.length; currentRecord++){
            if(records[currentRecord].getCid().toLowerCase().equals("ippp")){
                if(records[currentRecord].getName().toLowerCase().indexOf("bibs") >= 0){
                    return records[currentRecord].getUid();
                }
            }
        }
        
        return null;
    }
}