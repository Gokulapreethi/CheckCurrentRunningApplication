package com.example.preethi.checkcurrentrunningapplication;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.jaredrummler.android.processes.AndroidProcesses;
import com.jaredrummler.android.processes.models.AndroidAppProcess;

import static com.example.preethi.checkcurrentrunningapplication.R.id.top;

public class CheckRunningApplicationReceiver extends BroadcastReceiver {
    public final String TAG = "CRAR"; // CheckRunningApplicationReceiver
    ActivityManager am;

    @Override
    public void onReceive(Context aContext, Intent intent1) {
        // TODO Auto-generated method stub

        try {

            Log.i("locker124", "OnReceive Fired");

            am = (ActivityManager) aContext
                    .getSystemService(Context.ACTIVITY_SERVICE);

            List<ActivityManager.RunningTaskInfo> alltasks = am
                    .getRunningTasks(1);

            ActivityManager.RunningTaskInfo ar = alltasks.get(0);

            String[] activePackages;
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
                activePackages = getActivePackages();
            } else {
                activePackages = getActivePackagesCompat();
            }

            if (activePackages != null) {
                for (String activePackage : activePackages) {
                    if (activePackage.equals("com.google.android.calendar")) {
                        // Calendar app is launched, do something
                    }
                }
            }


            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            ResolveInfo defaultLauncher = aContext.getPackageManager()
                    .resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
            String nameOfLauncherPkg = defaultLauncher.activityInfo.packageName;


            ConnectivityManager connManager = (ConnectivityManager) aContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo networkInfo = connManager.getActiveNetworkInfo();

            if (mWifi.isConnected() && networkInfo != null && networkInfo.isConnected()) {
                // Do whatever
                Log.i("locker1234", "mobile data Connected ");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    List<AndroidAppProcess> MyAppList = getProcessNew(aContext);
                    for (int i = 0; i < MyAppList.size(); i++) {
                        String packageNameList = MyAppList.get(i).getPackageName();

                        if (!packageNameList.equals("com.example.preethi.checkcurrentrunningapplication")
                                && !packageNameList.contains("com.android.launcher")
                                && !packageNameList.contains("com.sec.android.app.launcher")
                                && !packageNameList.contains("com.estrongs.android.pop")
                                && !packageNameList.equals(nameOfLauncherPkg)
                                && !ar.topActivity.toString().contains(
                                "recent.RecentsActivity")) {
                            Log.i("locker124", "package Name to close above 5.0 "+packageNameList);
//                            ( MainActivity.getInstance()).killApp(0);
                            Intent startHomescreen = new Intent(Intent.ACTION_MAIN);
                            startHomescreen.addCategory(Intent.CATEGORY_HOME);
                            startHomescreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startHomescreen.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            // startHomescreen.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            aContext.startActivity(startHomescreen);
//                            ActivityManager mActivityManager = (ActivityManager)aContext.getSystemService(Context.ACTIVITY_SERVICE);
//                            mActivityManager.killBackgroundProcesses(packageNameList);

                        }
                       /* if (packageNameList.equals(" com.android.chrome")) {

                        }else if(packageNameList.equals("com.android.vending")){

                        }else if(packageNameList.equals("com.google.android.youtube")){

                        }else if(packageNameList.equals("com.android.vending")){

                        }*/
                    }
                } else {
                    String packageNameList = am.getRunningAppProcesses().get(0).processName;
                    if (!packageNameList.equals("com.example.preethi.checkcurrentrunningapplication")
                            && !packageNameList.contains("com.android.launcher")
                            && !packageNameList.contains("com.sec.android.app.launcher")
                            && !packageNameList.contains("com.estrongs.android.pop")
                            && !packageNameList.equals(nameOfLauncherPkg)
                            && !ar.topActivity.toString().contains(
                            "recent.RecentsActivity")) {
                        Log.i("locker124", "package Name to close below 5.0 "+packageNameList);
                        Intent startHomescreen = new Intent(Intent.ACTION_MAIN);
                        startHomescreen.addCategory(Intent.CATEGORY_HOME);
                        startHomescreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startHomescreen.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        // startHomescreen.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        aContext.startActivity(startHomescreen);

                    }

                }
            }
        if (mWifi.isConnected()) {
            Log.i("locker1234", "mWifi Connected ");
        }
        if (networkInfo == null) {
            Log.i("locker1234", "No Network Connected ");

        }


        String mPackageName = "";
        if (Build.VERSION.SDK_INT > 20) {
            mPackageName = am.getRunningAppProcesses().get(0).processName;
        } else {
            mPackageName = am.getRunningTasks(1).get(0).topActivity
                    .getPackageName();
        }


        Log.i("locker123", "@@@nameOfLauncherPkg --------->"
                + nameOfLauncherPkg);

        Log.i("locker123", "mPackageName " + mPackageName);
//            Log.i("locker1234", "mPackageName" + am.getRunningTasks(1).get(0).topActivity.getPackageName());
        Log.i("locker1234", "> 20 mPackageName=> " +
                "" + am.getRunningAppProcesses().get(0).processName);

        if (!mPackageName.equals("com.example.preethi.checkcurrentrunningapplication")
                && !mPackageName.contains("com.android.launcher")
                && !mPackageName.contains("com.sec.android.app.launcher")
                && !mPackageName.equals(nameOfLauncherPkg)
                && !ar.topActivity.toString().contains(
                "recent.RecentsActivity")) {

            Log.i("locker123" +
                    "", "nameOfLauncherPkg 1 " + mPackageName);
            Log.i("locker1234", "nameOfLauncherPkg 1 " + am.getRunningTasks(1).get(0).topActivity.getPackageName());
              /*  ((AppMainActivity) AppLocker.context).killApp(0);
                if (SingleInstance.contextTable.containsKey("MAIN")) {
                    Log.i("locker123", "nameOfLauncherPkg 2" + mPackageName);
                    ((AppMainActivity) SingleInstance.contextTable.get("MAIN"))
                            .killApp(0);

                }*/

        }

    } catch(Exception t){
        Log.i(TAG, "Throwable caught: " + t.getMessage(), t);
            Log.i("locker1234", "Exception 1 " + t.getMessage());

            // Intent startHomescreen=new Intent(aContext,Main.class);
        //
        // aContext.startActivity(startHomescreen);
    }

}


    String[] getActivePackagesCompat() {
        final List<ActivityManager.RunningTaskInfo> taskInfo = am
                .getRunningTasks(1);
        final ComponentName componentName = taskInfo.get(0).topActivity;
        final String[] activePackages = new String[1];
        activePackages[0] = componentName.getPackageName();
        return activePackages;
    }

    String[] getActivePackages() {
        final Set<String> activePackages = new HashSet<String>();
        final List<ActivityManager.RunningAppProcessInfo> processInfos = am
                .getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo processInfo : processInfos) {
            if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                activePackages.addAll(Arrays.asList(processInfo.pkgList));
            }
        }
        return activePackages.toArray(new String[activePackages.size()]);
    }

    //API 21 and above
    private List<AndroidAppProcess> getProcessNew(Context aContext) throws Exception {
        String topApp = "Not Exist";
        List<AndroidAppProcess> processes = AndroidProcesses.getRunningForegroundApps(aContext);
        Collections.sort(processes, new AndroidProcesses.ProcessComparator());
        Log.i("locker1234", "> 20 TOP APP=> " +
                "" + processes.get(0).getPackageName());
        for (int i = 0; i <= processes.size() - 1; i++) {
            if (processes.get(i).name.equalsIgnoreCase("com.google.android.gms")) { //always the package name above/below this package is the top app
                if ((i + 1) <= processes.size() - 1) { //If processes.get(i+1) available, then that app is the top app
                    topApp = processes.get(i + 1).name;
                } else if (i != 0) { //If the last package name is "com.google.android.gms" then the package name above this is the top app
                    topApp = processes.get(i - 1).name;
                } else {
                    if (i == processes.size() - 1) { //If only one package name available
                        topApp = processes.get(i).name;
                    }
                }
                Log.v(TAG, "top app = " + top);
                Log.i("locker1234", "> 20 TOP APP=> " +
                        "" + topApp);
            }
        }
        return processes;
    }


}
