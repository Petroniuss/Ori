package com.softy.ori.customization;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.softy.ori.customization.Config.SHARED_PREF_KEY;

public class SkinProvider {
    @SuppressLint("StaticFieldLeak")
    private static SkinProvider instance;

    private static final String THEME_KEY = "THEME_";

    private final Context context;

    private SkinProvider(Context context) {
        this.context = context;
    }

    public static SkinProvider getInstance(Context context) {
        if (instance == null)
            instance = new SkinProvider(context);

        return instance;
    }

    public Skin fromName(String skinName) {
        return Skin.create(SkinDrawable.valueOf(skinName), context);
    }

    public List<Skin> getSkins() {
        return Arrays.stream(SkinDrawable.values())
                .map(skinDrawable -> Skin.create(skinDrawable, context))
                .collect(Collectors.toList());
    }

    public void choose(String skin) {
        Config.getInstance(context).setSkin(SkinDrawable.valueOf(skin));
    }

    public SkinDrawable chosen() {
        return SkinDrawable.valueOf(Config.getInstance(context).getSkinName());
    }

    public boolean isAvailable(Theme theme) {
        if (theme == Theme.FREE)
            return true;
        final SharedPreferences pref = getPreferences();

        return pref.getBoolean(getThemeKey(theme), false);
    }

    public void unlockTheme(Theme theme) {
        final SharedPreferences pref = getPreferences();
        final SharedPreferences.Editor editor = pref.edit();

        editor.putBoolean(getThemeKey(theme), true);

        editor.apply();
    }

    private SharedPreferences getPreferences() {
        return context.getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE);
    }

    private static String getThemeKey(Theme theme) {
        return THEME_KEY + theme.name();
    }

}
