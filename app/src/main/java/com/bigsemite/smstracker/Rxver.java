package com.bigsemite.smstracker;

import java.util.List;
import java.util.Locale;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class Rxver extends BroadcastReceiver implements LocationListener {

	double lat, lon;
	Location loc;
	Geocoder gc;
	SharedPreferences ssp;
	String searchText;
	SmsManager sender;
	@Override
	public void onReceive(Context arg0, Intent intent) {
		// TODO Auto-generated method stub
		
		LocationManager lm = (LocationManager) arg0.getSystemService(Context.LOCATION_SERVICE);
		Boolean isGPS, isNtwk;
		isGPS = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
		isNtwk = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		if (isNtwk){
			lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 30000, 1, this);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (isGPS){
			lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30000, 1, this);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	//	TelephonyManager tm = (TelephonyManager) arg0.getSystemService(Context.TELEPHONY_SERVICE);
		
		ssp = arg0.getSharedPreferences("bigsemiteTracker", Context.MODE_PRIVATE);
		searchText = ssp.getString("passwd", ".");
		sender = SmsManager.getDefault();
		
		if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
			Bundle bd = intent.getExtras();
			SmsMessage[] msg = null;
			String msg_from, msg_body;
			if(bd != null){
				try{
					Object [] obj = (Object[])bd.get("pdus");
					msg = new SmsMessage[obj.length];
					
					for(int i=0; i<msg.length; i++){
						
						msg[i] = SmsMessage.createFromPdu((byte[]) obj[i]);
						msg_from =msg[i].getOriginatingAddress();
						msg_body = msg[i].getMessageBody();
						
					
						if (searchText.equals(".")){
						
						if((msg_body.contains("SMSTRACKER"))||(msg_body.contains("Smstracker"))||(msg_body.contains("smstracker"))||(msg_body.contains("SmsTracker"))){
							
							
						
							
							loc = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
							if(loc == null){
								loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
							}
							if(loc == null){
								//String ph = tm.getDeviceId();
								String mw = "The Location of the device cannot be detected.\n No internet network on the device or GPS may be turned-off or Location sharing is disabled";
								sender.sendTextMessage(msg_from, null, mw, null, null);
							}
							gc = new Geocoder(arg0, Locale.getDefault());
							
						
							lat = loc.getLatitude();
							lon = loc.getLongitude();
							List<Address> addr =  gc.getFromLocation(lat, lon, 2);
							
							String myCurrLoc = addr.get(0).getAddressLine(0);
							String myCurrLoc2 = addr.get(0).getSubLocality();
							String myCurrLoc3 = addr.get(0).getLocality(); 
							String where = "My Current Location \n Latitude, Longitude: http://maps.google.com/maps?z=12&t=m&q=loc:" +  lat + "+" +lon + "\nAddress: " + myCurrLoc +"\n" + myCurrLoc2 + "\n"+ myCurrLoc3;
							Toast.makeText(arg0,  "Message from: " + msg_from + "\n "+ where, Toast.LENGTH_LONG).show();
							
							sender.sendTextMessage(msg_from, null, where, null, null);
							//onLocationChanged(loc);
						
							
						}// end if
						}
						else{
							if(msg_body.contains(searchText)){
								
								
								
								loc = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
								if(loc == null){
									loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
								}
								if(loc == null){
									//String ph = tm.getDeviceId();
									String mw = "The Location of the device cannot be detected.\n No internet network on the device or GPS may be turned-off or Location sharing is disabled";
									sender.sendTextMessage(msg_from, null, mw, null, null);
								}
								gc = new Geocoder(arg0, Locale.getDefault());
								
							
								lat = loc.getLatitude();
								lon = loc.getLongitude();
								List<Address> addr =  gc.getFromLocation(lat, lon, 2);
								
								String myCurrLoc = addr.get(0).getAddressLine(0);
								String myCurrLoc2 = addr.get(0).getSubLocality();
								String myCurrLoc3 = addr.get(0).getLocality(); 
								String where = "My Current Location \n Latitude, Longitude: http://maps.google.com/maps?z=12&t=m&q=loc:" +  lat + "+" +lon + "\nAddress: " + myCurrLoc +"\n" + myCurrLoc2 + "\n"+ myCurrLoc3;
								Toast.makeText(arg0,  "Message from: " + msg_from + "\n "+ where, Toast.LENGTH_LONG).show();
								
								sender.sendTextMessage(msg_from, null, where, null, null);
							}
						}
						
					}
					
				}
				catch(Exception e){}
			}
		}
		
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		loc = location;
		//lat = location.getLatitude();
		//lon = location.getLongitude();
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

}
