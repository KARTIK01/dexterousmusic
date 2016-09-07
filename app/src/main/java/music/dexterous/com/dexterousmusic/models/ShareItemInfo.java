package music.dexterous.com.dexterousmusic.models;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;

/**
 * Represents an item in the picker grid
 */
public class ShareItemInfo {
    public final String        label;
    public final ComponentName componentName;
    public final ResolveInfo   resolveInfo;
    public       Drawable      icon;

    public ShareItemInfo(Drawable icon, String label, Context context, Class<?> clazz) {
        this.icon = icon;
        resolveInfo = null;
        this.label = label;
        this.componentName = new ComponentName(context, clazz.getName());
    }

    public ShareItemInfo(ResolveInfo resolveInfo, CharSequence label, ComponentName componentName) {
        this.resolveInfo = resolveInfo;
        this.label = label.toString();
        this.componentName = componentName;
    }

    public Intent getConcreteIntent(Intent intent) {
        Intent concreteIntent = new Intent(intent);
        concreteIntent.setComponent(componentName);
        return concreteIntent;
    }
}