package org.bvic23.wifi;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.uimanager.IllegalViewOperationException;

import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;

import java.util.List;

public class RNWifiModule extends ReactContextBaseJavaModule {

	WifiManager wifi;
	ReactApplicationContext context;

	//Constructor
	public RNWifiModule(ReactApplicationContext reactContext) {
		super(reactContext);
		wifi = (WifiManager)reactContext.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
		context = getReactApplicationContext();
	}

	@Override
	public String getName() {
		return "RNWifi";
	}

	@ReactMethod
	public void getWifiList(final Callback callback) {
		try {
			final List < ScanResult > results = wifi.getScanResults();
			final WritableArray wifiArray = new WritableNativeArray();

			for (ScanResult result: results) {
				final WritableMap wifiObject = new WritableNativeMap();
				if(!result.SSID.equals("")){
        			wifiObject.putString("SSID", result.SSID);
		            wifiObject.putString("BSSID", result.BSSID);
		            wifiObject.putBoolean("isSecure", !result.capabilities.equals("[ESS]"));
        			wifiObject.putInt("level", result.level);
					wifiArray.pushMap(wifiObject);
				}
			}

			callback.invoke(null, wifiArray);
		} catch (IllegalViewOperationException e) {
			callback.invoke(e.getMessage());
		}
	}
}
