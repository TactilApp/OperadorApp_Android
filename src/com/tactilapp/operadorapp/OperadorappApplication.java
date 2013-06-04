package com.tactilapp.operadorapp;

import com.urbanairship.AirshipConfigOptions;
import com.urbanairship.UAirship;
import com.urbanairship.push.PushManager;

public class OperadorappApplication extends android.app.Application {

	@Override
	public void onCreate() {
		final AirshipConfigOptions options = AirshipConfigOptions
				.loadDefaultOptions(this);
		if (!"ejemplo".equalsIgnoreCase(options.developmentAppKey)) {
			UAirship.takeOff(this, options);
			PushManager.enablePush();
		}
	}

}
