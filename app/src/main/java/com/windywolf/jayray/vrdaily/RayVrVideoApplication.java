package com.windywolf.jayray.vrdaily;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

/**
 * Created by JayRay on 15/03/2017.
 * Info: ApplicationManifest 中注册此 Application
 */

public class RayVrVideoApplication extends Application {
    private static RayVrVideoApplication mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        Stetho.initialize(
                Stetho.newInitializerBuilder(mContext)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(mContext))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(mContext))
                        .build());
    }

    public static Context getContext() {
        return mContext;
    }
}
