package com.softy.ori.customization;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.softy.ori.multiplayer.model.PlayerJoinMessage;

import java.util.UUID;

public class Config {

    @SuppressLint("StaticFieldLeak")
    private static Config instance;

    private final Context context;

    static final String SHARED_PREF_KEY = "ORI_SHARED_PREFERENCE_FILE_KEY";

    private static final String THEME_KEY = "THEME_";
    private static final String NAME_KEY = "NAME_";
    private static final String SKIN_KEY = "SKIN_";

    private String playerId;
    private String name;
    private String skinName;
    private GameTheme theme;

    private Config(Context context) {
        this.context = context;
        final SharedPreferences pref = getPreferences();

        playerId = UUID.randomUUID().toString();
        name = pref.getString(NAME_KEY, "TeSteR");
        skinName = pref.getString(SKIN_KEY, SkinDrawable.TRUMP.name());
        theme = GameTheme.valueOf(pref.getString(THEME_KEY, GameTheme.DARK.name()));
    }

    public static Config getInstance(Context context) {
        if (instance == null)
            instance = new Config(context);

        return instance;
    }

    public PlayerJoinMessage constructMessage() {
        return new PlayerJoinMessage(playerId, name, skinName);
    }

    public void setSkin(SkinDrawable skin) {
        this.skinName = skin.name();

        getPreferences()
                .edit()
                .putString(SKIN_KEY, skin.name())
                .apply();
    }

    public void setName(String name) {
        this.name = name;

        getPreferences()
                .edit()
                .putString(NAME_KEY, name)
                .apply();
    }

    public void setTheme(GameTheme theme) {
        this.theme = theme;

        getPreferences()
                .edit()
                .putString(THEME_KEY, theme.name())
                .apply();
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getName() {
        return name;
    }

    public String getSkinName() {
        return skinName;
    }

    public GameTheme getTheme() {
        return theme;
    }

    public enum GameTheme {
        DARK,
        LIGHT
    }

    private SharedPreferences getPreferences() {
        return context.getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE);
    }
}
